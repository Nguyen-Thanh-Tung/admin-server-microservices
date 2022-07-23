package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.client.AccountClient;
import com.comit.services.areaRestriction.client.EmployeeClient;
import com.comit.services.areaRestriction.client.response.EmployeeResponse;
import com.comit.services.areaRestriction.client.response.LocationResponse;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.model.entity.Employee;
import com.comit.services.areaRestriction.model.entity.Location;
import com.comit.services.areaRestriction.repository.AreaRestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Service
public class AreaRestrictionServicesImpl implements AreaRestrictionServices {
    @Autowired
    private AreaRestrictionRepository areaRestrictionRepository;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private EmployeeClient employeeClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Page<AreaRestriction> getAreaRestrictionPage(Integer locationId, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return areaRestrictionRepository.findByLocationIdAndNameContainingOrLocationIdAndCodeContainingOrderByUpdateAtDesc(locationId, search, locationId, search, paging);
        }
        return areaRestrictionRepository.findByLocationIdOrderByUpdateAtDesc(locationId, paging);
    }

    @Override
    public AreaRestriction getAreaRestriction(Integer locationId, Integer id) {
        return areaRestrictionRepository.findByLocationIdAndId(locationId, id);
    }

    @Override
    public AreaRestriction getAreaRestriction(Integer locationId, String name) {
        return areaRestrictionRepository.findByLocationIdAndName(locationId, name);
    }

    @Override
    public AreaRestriction updateAreaRestriction(AreaRestriction areaRestriction) {
        return areaRestrictionRepository.save(areaRestriction);
    }

    @Override
    public boolean deleteAreaRestriction(AreaRestriction areaRestriction) {
        try {
            areaRestrictionRepository.delete(areaRestriction);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Location getLocationOfCurrentUser() {
        LocationResponse locationResponse = accountClient.getLocationOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (locationResponse == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(AreaRestriction areaRestriction, Date startDay, Date toDate) {
        return 0;
    }

    @Override
    public Employee getEmployee(Integer managerId, Integer locationId) {
        EmployeeResponse employeeResponse = employeeClient.getEmployee(httpServletRequest.getHeader("token"), managerId).getBody();
        if (employeeResponse == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return Objects.equals(employeeResponse.getEmployee().getLocationId(), locationId) ? employeeResponse.getEmployee() : null;
    }
}
