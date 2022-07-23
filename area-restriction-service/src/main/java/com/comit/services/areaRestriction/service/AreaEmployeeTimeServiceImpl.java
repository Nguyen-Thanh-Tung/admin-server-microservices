package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import com.comit.services.areaRestriction.repository.AreaEmployeeTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AreaEmployeeTimeServiceImpl implements AreaEmployeeTimeService {

    @Autowired
    private AreaEmployeeTimeRepository areaEmployeeTimeRepository;

    @Override
    public List<AreaEmployeeTime> saveAllAreaEmployeeTime(List<AreaEmployeeTime> areaEmployeeTimes) {
        return areaEmployeeTimeRepository.saveAll(areaEmployeeTimes);
    }

    @Override
    public boolean deleteAreaEmployeeTime(Integer employeeId) {
        try {
            areaEmployeeTimeRepository.deleteByEmployeeId(employeeId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
