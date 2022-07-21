package com.comit.services.employee.business;

import com.comit.services.employee.constant.Const;
import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.controller.request.SendQrCodeRequest;
import com.comit.services.employee.exception.RestApiException;
import com.comit.services.employee.middleware.EmployeeVerifyRequestServices;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.comit.services.employee.model.entity.*;
import com.comit.services.employee.service.EmployeeServices;
import com.comit.services.employee.util.ExcelUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class EmployeeBusinessImpl implements EmployeeBusiness {
    @Autowired
    private EmployeeVerifyRequestServices employeeVerifyRequestServices;
    @Autowired
    private EmployeeServices employeeServices;
    @Autowired
    private Environment env;

    @Override
    public Page<Employee> getEmployeePage(String status, int page, int size, String search) {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageEmployee();

        Pageable paging = PageRequest.of(page, size);
        Location location = employeeServices.getLocationOfCurrentUser();

        return employeeServices.getEmployeePage(location.getId(), status, search, paging);
    }

    @Override
    public List<EmployeeDto> getAllEmployee(List<Employee> employees) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employees.forEach(employee -> {
            employeeDtos.add(EmployeeDto.convertEmployeeToEmployeeDto(employee));
        });
        return employeeDtos;
    }

    @Override
    public EmployeeDto saveEmployee(HttpServletRequest request) {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageEmployee();

        // Check content type
        String contentType = request.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            throw new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }

        // Verify data and save employee
        if (request instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            MultipartFile file = multipartHttpServletRequest.getFile("image");
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            String managerId = request.getParameter("manager_id");
            String shiftIds = request.getParameter("shift_ids");
            String forceUpdate = request.getParameter("force_update");

            Location location = employeeServices.getLocationOfCurrentUser();
            employeeVerifyRequestServices.verifyAddEmployeeRequest(file, name, code, email, phone, managerId, shiftIds, Objects.equals(location.getType(), Const.TIME_KEEPING_TYPE));

            // Prevent add multi employee have same code to one location
            Employee employee = employeeServices.getEmployeeByCodeAndLocation(code, location.getId());

            if (employee != null && (forceUpdate == null || !forceUpdate.equals("true"))) {
                throw new RestApiException(EmployeeErrorCode.EMPLOYEE_CODE_EXISTED);
            } else if (employee == null) {
                employee = new Employee();
            }

            // Check face exist (call core ai)
            String addFaceResponse = employeeServices.getSaveEmployeeImageResponse(file, location.getId());
            if (addFaceResponse != null) {
                try {
                    JsonObject obj = new JsonParser().parse(addFaceResponse).getAsJsonObject();
                    if (obj.has("data")) {
                        JsonObject dataObj = obj.getAsJsonObject("data");
                        employee.setName(name);
                        employee.setCode(code);
                        employee.setEmail(email);
                        employee.setPhone(phone);
                        employee.setStatus(Const.ACTIVE);
                        employee.setLocationId(location.getId());
                        if (dataObj.has("embedding_id")) {
                            if (!dataObj.get("embedding_id").isJsonNull()) {
                                String embeddingIdStr = dataObj.get("embedding_id").getAsString();
                                try {
                                    int embeddingId = Integer.parseInt(embeddingIdStr);
                                    employee.setEmbeddingId(embeddingId);
                                    if (managerId != null && !managerId.trim().isEmpty()) {
                                        Employee manager = employeeServices.getEmployee(Integer.parseInt(managerId), location.getId());
                                        if (manager == null) {
                                            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
                                        }
                                        employee.setManagerId(manager.getId());
                                    }

                                    // Add image
                                    Metadata metadata = employeeServices.saveMetadata(dataObj.get("image_path").getAsString());
                                    employee.setImageId(metadata.getId());

                                    if (Objects.equals(location.getType(), Const.TIME_KEEPING_TYPE)) {
                                        employee.setShiftIds(shiftIds);
                                    }

                                    Employee newEmployee = employeeServices.saveEmployee(employee);
                                    if (employeeServices.isAreaRestrictionModule() || employeeServices.isBehaviorModule()) {
                                        String areaEmployees = request.getParameter("area_employees");
                                        if (areaEmployees != null && !areaEmployees.trim().isEmpty()) {
                                            List<AreaEmployeeTime> employeeAreaRestrictions = employeeServices.saveEmployeeAreaRestrictionList(areaEmployees, newEmployee.getId());
                                        }
                                    }

                                    return EmployeeDto.convertEmployeeToEmployeeDto(newEmployee);
                                } catch (Exception e) {
                                    if (e instanceof RestApiException) {
                                        throw e;
                                    } else {
                                        throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
                                    }
                                }
                            }
                        }
                    } else {
                        throw new RestApiException(EmployeeErrorCode.EMPLOYEE_IMAGE_IS_EXISTED);
                    }
                } catch (Exception e) {
                    if (e instanceof RestApiException) {
                        throw e;
                    } else {
                        throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
                    }
                }
            }

        }
        return null;
    }

    @Override
    public EmployeeDto updateEmployee(int id, HttpServletRequest request) {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageEmployee();

        // Get employee of location
        Location location = employeeServices.getLocationOfCurrentUser();
        Employee employee = employeeServices.getEmployee(id, location.getId());

        if (employee == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        // Check content type
        String contentType = request.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            throw new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }

        // Verify data and update employee
        if (request instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            MultipartFile file = multipartHttpServletRequest.getFile("image");
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String managerId = request.getParameter("manager_id");
            String shiftIds = request.getParameter("shift_ids");

            employeeVerifyRequestServices.verifyUpdateEmployeeRequest(file, name, code, email, phone, managerId, shiftIds);

            if (!Objects.equals(employee.getCode(), code)) {
                Employee tmp = employeeServices.getEmployeeByCodeAndLocation(code, location.getId());
                if (tmp != null) {
                    throw new RestApiException(EmployeeErrorCode.EMPLOYEE_CODE_OF_OTHER_EMPLOYEE);
                }
            }

            // Add image
            if (file != null) {
                String updateFaceResponse = employeeServices.getUpdateEmployeeImageResponse(employee.getEmbeddingId(), file);
                if (updateFaceResponse != null) {
                    try {
                        JsonObject obj = new JsonParser().parse(updateFaceResponse).getAsJsonObject();
                        if (obj.has("data")) {
                            JsonObject dataObj = obj.getAsJsonObject("data");
                            Metadata metadata = employeeServices.saveMetadata(dataObj.get("image_path").getAsString());
                            employee.setImageId(metadata.getId());

                            employee.setEmbeddingId(dataObj.get("embedding_id").getAsInt());
                        }
                    } catch (Exception e) {
                        throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
                    }
                }
            }
            employee.setName(name);
            employee.setCode(code);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setLocationId(location.getId());
            if (managerId != null) {
                if (!managerId.trim().isEmpty()) {
                    if (Integer.parseInt(managerId) == employee.getId()) {
                        throw new RestApiException(EmployeeErrorCode.MANAGER_ID_IS_SAME_EMPLOYEE);
                    }
                    Employee manager = employeeServices.getEmployee(Integer.parseInt(managerId), location.getId());
                    if (manager == null) {
                        throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
                    }
                    employee.setManagerId(manager.getId());
                } else {
                    employee.setManagerId(null);
                }
            }

            if (Objects.equals(location.getType(), Const.TIME_KEEPING_TYPE)) {
                employee.setShiftIds(shiftIds);
            }

            if (employeeServices.isAreaRestrictionModule() || employeeServices.isBehaviorModule()) {
                String areaEmployees = request.getParameter("area_employees");
                employeeServices.deleteEmployeeAreaRestrictionList(employee.getId());
                if (areaEmployees != null && !areaEmployees.trim().isEmpty()) {
                    employeeServices.saveEmployeeAreaRestrictionList(areaEmployees, employee.getId());
                }
            }

            Employee newEmployee = employeeServices.saveEmployee(employee);

            return EmployeeDto.convertEmployeeToEmployeeDto(newEmployee);
        }
        return null;
    }

    @Override
    public EmployeeDto getEmployee(int id) {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageEmployee();

        // Get employee in location
        Location location = employeeServices.getLocationOfCurrentUser();
        Employee employee = employeeServices.getEmployee(id, location.getId());
        if (employee == null) {
//            throw new TimeKeepingCommonException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
            return null;
        }

        return EmployeeDto.convertEmployeeToEmployeeDto(employee);
    }

    @Override
    public boolean deleteEmployee(int id) {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageEmployee();

        // Get employee in location
        Location location = employeeServices.getLocationOfCurrentUser();
        Employee employee = employeeServices.getEmployee(id, location.getId());
        if (employee == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        employeeServices.getDeleteEmployeeImageResponse(employee.getEmbeddingId(), employee.getLocationId());
        // Update employees and manager and status of employee
        employee.setManagerId(null);
        List<Employee> employees = employeeServices.getEmployeeOfManager(employee.getId());
        for (Employee item : employees) {
            item.setManagerId(null);
            employeeServices.saveEmployee(item);
        }
        employee.setEmbeddingId(null);
        employee.setStatus(Const.DELETED);

        // Delete employee from area restriction (remove manager)
        employeeServices.getAreaRestrictions(employee.getId()).forEach(areaRestriction -> {
            Set<Employee> managers = areaRestriction.getManagers();
            managers.remove(employee);
        });

        // Delete all area restriction and time which employee are allowed
        employeeServices.deleteEmployeeAreaRestrictionList(employee.getId());

        // Delete all area restriction manager notification
        employeeServices.deleteAreaRestrictionManagerNotificationList(employee.getId());

        employeeServices.saveEmployee(employee);
        return true;
    }

    @Override
    public EmployeeDto changeManager(int oldManagerId, int newManagerId) {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageEmployee();

        // Get old manager and new manager
        Location location = employeeServices.getLocationOfCurrentUser();
        Employee oldManager = employeeServices.getEmployee(oldManagerId, location.getId());
        Employee newManager = employeeServices.getEmployee(newManagerId, location.getId());
        if (oldManager == null || newManager == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        // Update list employee for new manager
        List<Employee> oldManagerEmployees = employeeServices.getEmployeeOfManager(oldManagerId);
        List<Employee> newManagerEmployees = employeeServices.getEmployeeOfManager(newManagerId);

        List<Employee> newManagerEmployeesCopy = new ArrayList<>(newManagerEmployees);
        newManagerEmployeesCopy.removeAll(oldManagerEmployees);
        oldManagerEmployees.addAll(newManagerEmployeesCopy);
        if (oldManagerEmployees.contains(newManager)) {
            oldManagerEmployees.remove(newManager); // When make employee is manager
            newManager.setManagerId(oldManager.getManagerId());
        }
        for (Employee employee : oldManagerEmployees) {
            employee.setManagerId(newManager.getId());
            employeeServices.saveEmployee(employee);
        }
        return EmployeeDto.convertEmployeeToEmployeeDto(newManager);
    }

    private void permissionManageEmployee() {
        // Check role for employee
        Location location = employeeServices.getLocationOfCurrentUser();

        if (!employeeServices.hasPermissionManageEmployee(location != null ? location.getType() : null)) {
            throw new RestApiException(EmployeeErrorCode.PERMISSION_DENIED);
        }
    }

    @Override
    public List<String> importEmployee(HttpServletRequest httpServletRequest) throws IOException {
        Location location = employeeServices.getLocationOfCurrentUser();
        String contentType = httpServletRequest.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            throw new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }
        if (httpServletRequest instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");

            MultipartFile exelFile = multipartHttpServletRequest.getFile("excelFile");

            employeeVerifyRequestServices.verifyUploadEmployee(exelFile);
            ExcelUtil excelUtil = new ExcelUtil();
            Map<Integer, List<String>> employeeData = excelUtil.readExcel(Objects.requireNonNull(exelFile));
            List<String> employeeCodeErrors = new ArrayList<>();
            employeeData.forEach((integer, strings) -> {
                if (integer > 0) {
                    Employee employee = new Employee();
                    employee.setName(strings.get(0));
                    employee.setCode(strings.get(1));
                    employee.setEmail(strings.get(2));
                    employee.setPhone(strings.get(3));
                    employee.setStatus(Const.ACTIVE);
                    employee.setLocationId(location.getId());
                    // Check face exist (call core ai)
                    MultipartFile file = files.stream().filter(multipartFile -> {
                        String[] names = multipartFile.getOriginalFilename().split("/");
                        String name = names[names.length - 1];
                        String employeeCodeFile = name.split("\\.")[0];
                        return Objects.equals(employeeCodeFile, employee.getCode());
                    }).findFirst().orElse(null);
                    if (file != null) {
                        String addFaceResponse = employeeServices.getSaveEmployeeImageResponse(file, location.getId());
                        if (addFaceResponse != null) {
                            try {
                                JsonObject obj = new JsonParser().parse(addFaceResponse).getAsJsonObject();
                                if (obj.has("data")) {
                                    JsonObject dataObj = obj.getAsJsonObject("data");
                                    if (dataObj.has("embedding_id") && !dataObj.get("embedding_id").isJsonNull()) {
                                        String embeddingIdStr = dataObj.get("embedding_id").getAsString();
                                        int embeddingId = Integer.parseInt(embeddingIdStr);
                                        employee.setEmbeddingId(embeddingId);
                                        // Add image
                                        Metadata metadata = employeeServices.saveMetadata(dataObj.get("image_path").getAsString());
                                        employee.setImageId(metadata.getId());
                                        employeeServices.saveEmployee(employee);
                                    } else {
                                        employeeCodeErrors.add(employee.getCode());
                                    }
                                } else {
                                    throw new RestApiException(EmployeeErrorCode.EMPLOYEE_IMAGE_IS_EXISTED);
                                }
                            } catch (Exception e) {
                                employeeCodeErrors.add(employee.getCode());
                            }
                        } else {
                            employeeCodeErrors.add(employee.getCode());
                        }
                    } else {
                        employeeCodeErrors.add(employee.getCode());
                    }
                }
            });
            return employeeCodeErrors;
        }
        return null;
    }

    @Override
    public boolean sendQrCode(SendQrCodeRequest request) {
        List<Integer> employeeIds = request.getEmployeeIds();
        Location location = employeeServices.getLocationOfCurrentUser();
        Organization organization = employeeServices.getOrganizationOfCurrentUser();
        Boolean checkAll = request.getCheckAll();
        Pageable paging = PageRequest.of(0, Integer.parseInt(Const.DEFAULT_SIZE_PAGE));
        List<Employee> employees = new ArrayList<>();
        if (checkAll) {
            Page<Employee> employeePage = employeeServices.getEmployeePage(location.getId(), Const.ACTIVE, "", paging);
            employees = employeePage.getContent();
        } else {
            employees = employeeServices.getEmployeeListById(employeeIds);
        }
        employees.forEach(employee -> {
            if (employee.getEmail() != null) {
                sendMailQrCode(employee.getEmail(), employee.getName(), employee.getCode(), organization.getName(), location.getName(), location.getCode());
            }
        });
        return true;
    }

    private void sendMailQrCode(String mailTo, String fullname, String employeeCode, String organizationName, String locationName, String locationCode) {
        employeeServices.sendQrCodeEmail(mailTo, fullname, employeeCode, organizationName, locationName, locationCode);
    }
}
