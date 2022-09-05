package com.comit.services.employee.business;

import com.comit.services.employee.client.data.*;
import com.comit.services.employee.constant.Const;
import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.controller.request.SendQrCodeRequest;
import com.comit.services.employee.exception.RestApiException;
import com.comit.services.employee.middleware.EmployeeVerifyRequestServices;
import com.comit.services.employee.model.dto.*;
import com.comit.services.employee.model.entity.Employee;
import com.comit.services.employee.service.EmployeeServices;
import com.comit.services.employee.service.KafkaServices;
import com.comit.services.employee.util.ExcelUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EmployeeBusinessImpl implements EmployeeBusiness {
    @Autowired
    private EmployeeVerifyRequestServices employeeVerifyRequestServices;
    @Autowired
    private EmployeeServices employeeServices;
    @Autowired
    private KafkaServices kafkaServices;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public Page<Employee> getEmployeePage(Integer locationId, String status, int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);
        if (locationId == null) {
            LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
            locationId = locationDtoClient.getId();
        }

        return employeeServices.getEmployeePage(locationId, status, search, paging);
    }

    @Override
    public List<EmployeeDto> getAllEmployee(List<Employee> employees) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employees.forEach(employee -> {
            employeeDtos.add(convertEmployeeToEmployeeDto(employee));
        });
        return employeeDtos;
    }

    @Override
    public List<BaseEmployeeDto> getAllEmployeeBase(List<Employee> employees) {
        List<BaseEmployeeDto> employeeDtos = new ArrayList<>();
        employees.forEach(employee -> {
            employeeDtos.add(convertEmployeeToBaseEmployeeDto(employee));
        });
        return employeeDtos;
    }

    @Override
    public EmployeeDto saveEmployee(HttpServletRequest request) {
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

            LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
            employeeVerifyRequestServices.verifyAddEmployeeRequest(file, name, code, email, phone, managerId, shiftIds, Objects.equals(locationDtoClient.getType(), Const.TIME_KEEPING_TYPE));

            // Prevent add multi employee have same code to one location
            Employee employee = employeeServices.getEmployeeByCodeAndLocation(code, locationDtoClient.getId());

            if (employee != null && (forceUpdate == null || !forceUpdate.equals("true"))) {
                throw new RestApiException(EmployeeErrorCode.EMPLOYEE_CODE_EXISTED);
            } else if (employee == null) {
                employee = new Employee();
            }

            // Check face exist (call core ai)
            String addFaceResponse = employeeServices.getSaveEmployeeImageResponse(file, locationDtoClient.getId());
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
                        employee.setLocationId(locationDtoClient.getId());
                        if (dataObj.has("embedding_id")) {
                            if (!dataObj.get("embedding_id").isJsonNull()) {
                                String embeddingIdStr = dataObj.get("embedding_id").getAsString();
                                try {
                                    int embeddingId = Integer.parseInt(embeddingIdStr);
                                    employee.setEmbeddingId(embeddingId);
                                    if (managerId != null && !managerId.trim().isEmpty()) {
                                        Employee manager = employeeServices.getEmployee(Integer.parseInt(managerId), locationDtoClient.getId());
                                        if (manager == null) {
                                            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
                                        }
                                        employee.setManagerId(manager.getId());
                                    }

                                    // Add image
                                    MetadataDtoClient metadataDtoClient = employeeServices.saveMetadata(dataObj.get("image_path").getAsString());
                                    employee.setImageId(metadataDtoClient.getId());

                                    if (Objects.equals(locationDtoClient.getType(), Const.TIME_KEEPING_TYPE)) {
                                        employee.setShiftIds(shiftIds.substring(1, shiftIds.length() - 1));
                                    }

                                    Employee newEmployee = employeeServices.saveEmployee(employee);
                                    if (employeeServices.isAreaRestrictionModule() || employeeServices.isBehaviorModule()) {
                                        String areaEmployees = request.getParameter("area_employees");
                                        if (areaEmployees != null && !areaEmployees.trim().isEmpty()) {
                                            employeeServices.saveEmployeeAreaRestrictionList(areaEmployees, newEmployee.getId());
                                        }
                                    }

                                    return convertEmployeeToEmployeeDto(newEmployee);
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
        // Get employee of location
        LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
        Employee employee = employeeServices.getEmployee(id, locationDtoClient.getId());

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
                Employee tmp = employeeServices.getEmployeeByCodeAndLocation(code, locationDtoClient.getId());
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
                            MetadataDtoClient metadataDtoClient = employeeServices.saveMetadata(dataObj.get("image_path").getAsString());
                            employee.setImageId(metadataDtoClient.getId());

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
            employee.setLocationId(locationDtoClient.getId());
            if (managerId != null) {
                if (!managerId.trim().isEmpty()) {
                    if (Integer.parseInt(managerId) == employee.getId()) {
                        throw new RestApiException(EmployeeErrorCode.MANAGER_ID_IS_SAME_EMPLOYEE);
                    }
                    Employee manager = employeeServices.getEmployee(Integer.parseInt(managerId), locationDtoClient.getId());
                    if (manager == null) {
                        throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
                    }
                    if (manager.getManagerId() != null && manager.getManagerId() == employee.getId()) {
                        throw new RestApiException(EmployeeErrorCode.MANAGER_IS_EMPLOYEE);
                    }
                    employee.setManagerId(manager.getId());
                } else {
                    employee.setManagerId(null);
                }
            }

            if (Objects.equals(locationDtoClient.getType(), Const.TIME_KEEPING_TYPE)) {
                employee.setShiftIds(shiftIds.substring(1, shiftIds.length() - 1));
            }

            if (employeeServices.isAreaRestrictionModule() || employeeServices.isBehaviorModule()) {
                String areaEmployees = request.getParameter("area_employees");
                employeeServices.deleteEmployeeAreaRestrictionList(employee.getId());
                if (areaEmployees != null && !areaEmployees.trim().isEmpty()) {
                    employeeServices.saveEmployeeAreaRestrictionList(areaEmployees, employee.getId());
                }
            }

            Employee newEmployee = employeeServices.saveEmployee(employee);

            return convertEmployeeToEmployeeDto(newEmployee);
        }
        return null;
    }

    @Override
    public EmployeeDto getEmployee(int id) {
        // Get employee in location
        LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
        Employee employee = employeeServices.getEmployee(id, locationDtoClient.getId());
        if (employee == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        return convertEmployeeToEmployeeDto(employee);
    }

    @Override
    public BaseEmployeeDto getEmployeeBase(int id) {
        Employee employee = employeeServices.getEmployee(id);
        if (employee == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        return convertEmployeeToBaseEmployeeDto(employee);
    }

    @Override
    public BaseEmployeeDto getEmployeeBase(String code) {
        Employee employee = employeeServices.getEmployee(code);
        if (employee == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        return convertEmployeeToBaseEmployeeDto(employee);
    }

    @Override
    public boolean deleteEmployee(int id) {
        // Get employee in location
        LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
        Employee employee = employeeServices.getEmployee(id, locationDtoClient.getId());
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
        employeeServices.deleteManagerOnAllAreaRestriction(employee.getId());

        // Delete all area restriction and time which employee are allowed
        employeeServices.deleteEmployeeAreaRestrictionList(employee.getId());

        // Delete all area restriction manager notification
        employeeServices.deleteAreaRestrictionManagerNotificationList(employee.getId());

        employeeServices.saveEmployee(employee);
        return true;
    }

    @Override
    public EmployeeDto changeManager(int oldManagerId, int newManagerId) {
        // Get old manager and new manager
        LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
        Employee oldManager = employeeServices.getEmployee(oldManagerId, locationDtoClient.getId());
        Employee newManager = employeeServices.getEmployee(newManagerId, locationDtoClient.getId());
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
        return convertEmployeeToEmployeeDto(newManager);
    }

    @Override
    public List<String> importEmployee(HttpServletRequest httpServletRequest) throws IOException {
        LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
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
                    employee.setLocationId(locationDtoClient.getId());
                    // Check face exist (call core ai)
                    MultipartFile file = files.stream().filter(multipartFile -> {
                        String[] names = multipartFile.getOriginalFilename().split("/");
                        String name = names[names.length - 1];
                        String employeeCodeFile = name.split("\\.")[0];
                        return Objects.equals(employeeCodeFile, employee.getCode());
                    }).findFirst().orElse(null);
                    if (file != null) {
                        String addFaceResponse = employeeServices.getSaveEmployeeImageResponse(file, locationDtoClient.getId());
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
                                        MetadataDtoClient metadataDtoClient = employeeServices.saveMetadata(dataObj.get("image_path").getAsString());
                                        employee.setImageId(metadataDtoClient.getId());
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
        LocationDtoClient locationDtoClient = employeeServices.getLocationOfCurrentUser();
        OrganizationDtoClient organizationDtoClient = employeeServices.getOrganizationOfCurrentUser();
        Boolean checkAll = request.getCheckAll();
        Pageable paging = PageRequest.of(0, Integer.parseInt(Const.DEFAULT_SIZE_PAGE));
        List<Employee> employees = new ArrayList<>();
        if (checkAll) {
            Page<Employee> employeePage = employeeServices.getEmployeePage(locationDtoClient.getId(), Const.ACTIVE, "", paging);
            employees = employeePage.getContent();
        } else {
            employees = employeeServices.getEmployeeListById(employeeIds);
        }
        employees.forEach(employee -> {
            if (employee.getEmail() != null) {
                sendMailQrCode(employee.getEmail(), employee.getName(), employee.getCode(), organizationDtoClient.getName(), locationDtoClient.getName(), locationDtoClient.getCode());
            }
        });
        return true;
    }

    @Override
    public int getNumberEmployeeOfLocation(Integer locationId) {
        return employeeServices.getNumberEmployeeOfLocation(locationId);
    }

    @Override
    public BaseEmployeeDto getEmployeeBaseByEmbeddingId(int embeddingId) {
        Employee employee = employeeServices.getEmployeeByEmbeddingId(embeddingId);
        if (employee == null) {
            throw new RestApiException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST);
        }

        return convertEmployeeToBaseEmployeeDto(employee);
    }

    private void sendMailQrCode(String mailTo, String fullname, String employeeCode, String organizationName, String locationName, String locationCode) {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        kafkaServices.sendMessage("qrCode", "{\"email\":\"" + mailTo + "\",\"fullname\":\"" + fullname + "\",\"employee_code\":\"" + employeeCode + "\",\"organization_name\":\"" + organizationName + "\",\"location_name\":\"" + locationName + "\",\"location_code\":\"" + locationCode + "\",\"type\":\"" + moduleName + "\"}");
    }

    public BaseEmployeeDto convertEmployeeToBaseEmployeeDto(Employee employee) {
        if (employee == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            BaseEmployeeDto employeeDto = modelMapper.map(employee, BaseEmployeeDto.class);
            // Manager of employee
            if (employee.getManagerId() != null && employee.getLocationId() != null) {
                Employee manager = employeeServices.getEmployee(employee.getManagerId(), employee.getLocationId());
                if (manager != null) {
                    employeeDto.setManager(modelMapper.map(manager, BaseEmployeeDto.class));
                }
            }
            return employeeDto;
        } catch (Exception e) {
            return null;
        }
    }

    public EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        if (employee == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            // Location of employee
            if (employee.getLocationId() != null) {
                LocationDtoClient locationDtoClient = employeeServices.getLocationById(employee.getLocationId());
                employeeDto.setLocation(convertLocationFromClient(locationDtoClient));
            }
            // Manager of employee
            if (employee.getManagerId() != null && employee.getLocationId() != null) {
                Employee manager = employeeServices.getEmployee(employee.getManagerId(), employee.getLocationId());
                if (manager != null) {
                    employeeDto.setManager(modelMapper.map(manager, EmployeeDto.class));
                }
            }
            // Employees of employee
            List<Employee> employeesOfEmployee = employeeServices.getEmployeeOfManager(employee.getId());
            List<EmployeeDto> employeesOfEmployeeDtos = new ArrayList<>();
            employeesOfEmployee.forEach(tmp -> {
                employeesOfEmployeeDtos.add(convertEmployeeToEmployeeDto(tmp));
            });
            employeeDto.setEmployees(employeesOfEmployeeDtos);
            if (employee.getShiftIds() != null) {
                // Shift of employee
                String[] tmp = employee.getShiftIds().split(",");
                List<ShiftDto> shiftDtos = new ArrayList<>();
                for (String shiftId : tmp) {
                    try {
                        ShiftDtoClient shiftDtoClient = employeeServices.getShift(Integer.parseInt(shiftId));
                        if (shiftDtoClient != null) {
                            shiftDtos.add(convertShiftDtoFromClient(shiftDtoClient));
                        }
                    } catch (Exception e) {

                    }
                }
                employeeDto.setShifts(shiftDtos);
            }
            // Image of employee
            if (employee.getImageId() != null) {
                MetadataDtoClient metadataDtoClient = employeeServices.getMetadata(employee.getImageId());
                if (metadataDtoClient != null) {
                    employeeDto.setImage(convertMetadataDtoFromClient(metadataDtoClient));
                }
            }

            //  Area Employee Time list of employee
            List<AreaEmployeeTimeDtoClient> areaEmployeeTimeDtoClients = employeeServices.getAreaEmployeeTimesOfEmployee(employee.getId());
            List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
            areaEmployeeTimeDtoClients.forEach(areaEmployeeTimeDtoClient -> {
                areaEmployeeTimeDtos.add(convertAreaEmployeeTimeFromClient(areaEmployeeTimeDtoClient));
            });
            employeeDto.setAreaEmployeeTimes(areaEmployeeTimeDtos);

            return employeeDto;
        } catch (Exception e) {
            return null;
        }
    }

    public LocationDto convertLocationFromClient(LocationDtoClient locationDtoClient) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationDtoClient.getId());
        locationDto.setName(locationDtoClient.getName());
        locationDto.setCode(locationDtoClient.getCode());
        locationDto.setType(locationDtoClient.getType());
        return locationDto;
    }

    public AreaEmployeeTimeDto convertAreaEmployeeTimeFromClient(AreaEmployeeTimeDtoClient areaEmployeeTimeDtoClient) {
        AreaEmployeeTimeDto areaEmployeeTimeDto = new AreaEmployeeTimeDto();
        areaEmployeeTimeDto.setId(areaEmployeeTimeDtoClient.getId());
        areaEmployeeTimeDto.setEmployeeDto(areaEmployeeTimeDtoClient.getEmployee());
        areaEmployeeTimeDto.setAreaRestriction(convertAreaRestrictionDtoFromClient(areaEmployeeTimeDtoClient.getAreaRestriction()));
        areaEmployeeTimeDto.setTimeStart(areaEmployeeTimeDtoClient.getTimeStart());
        areaEmployeeTimeDto.setTimeEnd(areaEmployeeTimeDtoClient.getTimeEnd());
        return areaEmployeeTimeDto;
    }

    public AreaRestrictionDto convertAreaRestrictionDtoFromClient(AreaRestrictionDtoClient areaRestrictionDtoClient) {
        AreaRestrictionDto areaRestrictionDto = new AreaRestrictionDto();
        areaRestrictionDto.setId(areaRestrictionDtoClient.getId());
        areaRestrictionDto.setName(areaRestrictionDtoClient.getName());
        areaRestrictionDto.setCode(areaRestrictionDtoClient.getCode());
        areaRestrictionDto.setTimeStart(areaRestrictionDtoClient.getTimeStart());
        areaRestrictionDto.setTimeEnd(areaRestrictionDtoClient.getTimeEnd());
        return areaRestrictionDto;
    }

    public ShiftDto convertShiftDtoFromClient(ShiftDtoClient shiftDtoClient) {
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setId(shiftDtoClient.getId());
        shiftDto.setName(shiftDtoClient.getName());
        shiftDto.setTimeStart(shiftDtoClient.getTimeStart());
        shiftDto.setTimeEnd(shiftDtoClient.getTimeEnd());
        return shiftDto;
    }

    public MetadataDto convertMetadataDtoFromClient(MetadataDtoClient metadataDtoClient) {
        MetadataDto metadataDto = new MetadataDto();
        metadataDto.setId(metadataDtoClient.getId());
        metadataDto.setPath(metadataDtoClient.getPath());
        metadataDto.setMd5(metadataDtoClient.getMd5());
        metadataDto.setType(metadataDtoClient.getType());
        return metadataDto;
    }
}
