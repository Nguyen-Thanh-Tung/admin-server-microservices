package com.comit.services.employee.service;

import com.comit.services.employee.client.*;
import com.comit.services.employee.client.data.*;
import com.comit.services.employee.client.request.AreaEmployeeTimeListRequestClient;
import com.comit.services.employee.client.request.MailRequestClient;
import com.comit.services.employee.client.request.MetadataRequestClient;
import com.comit.services.employee.client.response.*;
import com.comit.services.employee.constant.Const;
import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.controller.response.BaseResponse;
import com.comit.services.employee.exception.RestApiException;
import com.comit.services.employee.model.entity.Employee;
import com.comit.services.employee.repository.EmployeeRepository;
import com.comit.services.employee.util.ConvertFileUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServicesImpl implements EmployeeServices {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    AccountClient accountClient;
    @Autowired
    AreaRestrictionClient areaRestrictionClient;
    @Autowired
    MetadataClient metadataClient;
    @Autowired
    MailClient mailClient;
    @Autowired
    TimeKeepingClient timeKeepingClient;
    @Autowired
    LocationClient locationClient;
    @Autowired
    OrganizationClient organizationClient;

    @Value("${core.api.add-employee-image}")
    private String addEmployeeImageUrl;

    @Value("${core.api.update-employee-image}")
    private String updateEmployeeUrl;

    @Value("${core.api.delete-employee-image}")
    private String deleteEmployeeUrl;

    @Override
    public Page<Employee> getEmployeePage(Integer locationId, String status, String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            if (status != null) {
                return employeeRepository.findByLocationIdAndStatusAndNameContainingOrLocationIdAndCodeContainingOrderByIdDescIdDesc(locationId, status, search, locationId, search, pageable);
            }
            return employeeRepository.findByLocationIdAndNameContainingOrLocationIdAndCodeContainingOrderByStatusAscIdDescIdDesc(locationId, search, locationId, search, pageable);
        } else {
            if (status != null) {
                return employeeRepository.findByLocationIdAndStatusOrderByIdDescIdDesc(locationId, status, pageable);
            }
            return employeeRepository.findByLocationIdOrderByStatusAscIdDescIdDesc(locationId, pageable);
        }
    }

    @Override
    public Employee getEmployee(int employeeId, Integer locationId) {
        return employeeRepository.findByIdAndLocationId(employeeId, locationId);
    }

    @Override
    public Employee getEmployee(String employeeCode, Integer locationId) {
        return employeeRepository.findByCodeAndLocationId(employeeCode, locationId);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeByCodeAndLocation(String code, Integer location) {
        return employeeRepository.findByCodeAndLocationId(code, location);
    }

    @Override
    public List<Employee> saveAllEmployee(List<Employee> oldManagerEmployees) {
        return employeeRepository.saveAll(oldManagerEmployees);
    }

    @Override
    public String getSaveEmployeeImageResponse(MultipartFile file, Integer locationId) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String addEmployeeImageBody = "{\"location_id\":" +
                locationId +
                ",\"image\":\"" +
                ConvertFileUtil.convertFileToBase64(file) +
                "\"}";
        RequestBody body = RequestBody.create(JSON, addEmployeeImageBody);
        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder
                .url(addEmployeeImageUrl)
                .post(body)
                .build();

        try {
//            Response response = client.newCall(request).execute();
//            if (response.body() != null) {
//                return response.body().string();
//            }
            return "{\"code\": 200, \"data\": {\"embedding_id\":1, \"location_id\": 2, \"image_path\": \"https://picsum.photos/200\"}}";
//            return null;
        } catch (Exception e) {
            throw new RestApiException(EmployeeErrorCode.CAN_NOT_ADD_EMPLOYEE);
        }
    }

    @Override
    public String getUpdateEmployeeImageResponse(int id, MultipartFile file) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String updateEmployeeImageBody = "{\"image\":\"" +
                ConvertFileUtil.convertFileToBase64(file) +
                "\"}";
        RequestBody body = RequestBody.create(JSON, updateEmployeeImageBody);
        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder
                .url(updateEmployeeUrl + "/" + id)
                .put(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
//            return "{\"code\": 200, \"data\": {\"embedding_id\":5, \"location_id\": 5, \"image_path\": \"test\"}}";

            return null;
        } catch (Exception e) {
            throw new RestApiException(EmployeeErrorCode.CAN_NOT_UPDATE_EMPLOYEE);
        }
    }

    @Override
    public String getDeleteEmployeeImageResponse(Integer id, Integer locationId) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

//        Request.Builder requestBuilder = new Request.Builder();

        RequestBody body = RequestBody.create(JSON, "{\"location_id\":" + locationId + "}");
        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder
                .url(deleteEmployeeUrl + "/" + id)
                .delete(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
//            return "{\"code\": 200}";
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Employee> addEmployeeList(List<Employee> employees) {
        try {
            return employeeRepository.saveAll(employees);
        } catch (Exception e) {
            throw new RestApiException(EmployeeErrorCode.CAN_NOT_IMPORT_DATA);
        }
    }

    @Override
    public List<Employee> getEmployeeListById(List<Integer> employeeIds) {
        return employeeRepository.findAllByIdIn(employeeIds);
    }

    @Override
    public LocationDtoClient getLocationOfCurrentUser() {
        UserResponseClient userResponseClient = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        if (userResponseClient.getUser() == null && userResponseClient.getCode() != EmployeeErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(userResponseClient.getCode(), userResponseClient.getMessage());
        }
        if (userResponseClient.getUser().getLocationId() == null) {
            throw  new RestApiException(EmployeeErrorCode.PERMISSION_DENIED);
        }
        LocationResponseClient locationResponseClient = locationClient.getLocationById(httpServletRequest.getHeader("token"), userResponseClient.getUser().getLocationId()).getBody();
        if (locationResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocation();
    }

    @Override
    public MetadataDtoClient saveMetadata(String imagePath) {
        MetadataResponseClient metadataResponseClient = metadataClient.saveMetadata(httpServletRequest.getHeader("token"), new MetadataRequestClient(imagePath)).getBody();
        if (metadataResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return metadataResponseClient.getMetadata();
    }

    @Override
    public boolean isTimeKeepingModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.TIME_KEEPING_HEADER_MODULE);
    }

    @Override
    public boolean isAreaRestrictionModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.AREA_RESTRICTION_HEADER_MODULE);
    }

    @Override
    public boolean isBehaviorModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.BEHAVIOR_HEADER_MODULE);
    }

    @Override
    public List<AreaEmployeeTimeDtoClient> saveEmployeeAreaRestrictionList(String areaEmployees, Integer newEmployeeId) {
        AreaEmployeeTimeListResponseClient areaEmployeeTimeListResponseClient = areaRestrictionClient.saveAreaEmployeeTimeList(httpServletRequest.getHeader("token"), new AreaEmployeeTimeListRequestClient(areaEmployees, newEmployeeId)).getBody();

        if (areaEmployeeTimeListResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return areaEmployeeTimeListResponseClient.getAreaEmployeeTimes();
    }

    @Override
    public void deleteEmployeeAreaRestrictionList(Integer employeeId) {
        BaseResponse baseResponse = areaRestrictionClient.deleteAreaEmployeeTimeList(httpServletRequest.getHeader("token"), employeeId).getBody();
        if (baseResponse == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public List<Employee> getEmployeeOfManager(int managerId) {
        return employeeRepository.getByManagerId(managerId);
    }

    @Override
    public void deleteManagerOnAllAreaRestriction(int managerId) {
        BaseResponse baseResponse = areaRestrictionClient.deleteManagerOnAllAreaRestriction(httpServletRequest.getHeader("token"), managerId).getBody();
        if (baseResponse == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public void deleteAreaRestrictionManagerNotificationList(Integer employeeId) {
        BaseResponse baseResponse = areaRestrictionClient.deleteAreaRestrictionManagerNotificationList(httpServletRequest.getHeader("token"), employeeId).getBody();
        if (baseResponse == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public OrganizationDtoClient getOrganizationOfCurrentUser() {
        UserResponseClient userResponseClient = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        if (userResponseClient.getUser() == null && userResponseClient.getCode() != EmployeeErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(userResponseClient.getCode(), userResponseClient.getMessage());
        }
        if (userResponseClient.getUser().getOrganizationId() == null) {
            return null;
        }
        OrganizationResponseClient organizationResponseClient = organizationClient.getOrganizationById(httpServletRequest.getHeader("token"), userResponseClient.getUser().getOrganizationId()).getBody();
        if (organizationResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }

        return organizationResponseClient.getOrganization();
    }

    @Override
    public void sendQrCodeEmail(String mailTo, String fullname, String employeeCode, String organizationName, String locationName, String locationCode) {
        BaseResponse baseResponse = mailClient.sendQrCodeMail(httpServletRequest.getHeader("token"), new MailRequestClient(mailTo, fullname, employeeCode, organizationName, locationName, locationCode)).getBody();
        if (baseResponse == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public int getNumberEmployeeOfLocation(Integer locationId) {
        return employeeRepository.countEmployeeByLocationIdAndStatus(locationId, Const.ACTIVE);
    }

    @Override
    public ShiftDtoClient getShift(int shiftId) {
        ShiftResponseClient shiftResponseClient = timeKeepingClient.getShift(httpServletRequest.getHeader("token"), shiftId).getBody();
        if (shiftResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return shiftResponseClient.getShift();
    }

    @Override
    public MetadataDtoClient getMetadata(Integer imageId) {
        MetadataResponseClient metadataResponseClient = metadataClient.getMetadata(httpServletRequest.getHeader("token"), imageId).getBody();
        if (metadataResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return metadataResponseClient.getMetadata();
    }

    @Override
    public List<AreaEmployeeTimeDtoClient> getAreaEmployeeTimesOfEmployee(int employeeId) {
        AreaEmployeeTimeListResponseClient areaEmployeeTimeListResponseClient = areaRestrictionClient.getAreaEmployeeTimesOfEmployee(httpServletRequest.getHeader("token"), employeeId).getBody();
        if (areaEmployeeTimeListResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return areaEmployeeTimeListResponseClient.getAreaEmployeeTimes();
    }

    @Override
    public LocationDtoClient getLocationById(Integer locationId) {
        LocationResponseClient locationResponseClient = locationClient.getLocationById(httpServletRequest.getHeader("token"), locationId).getBody();
        if (locationResponseClient == null) {
            throw new RestApiException(EmployeeErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocation();
    }
}
