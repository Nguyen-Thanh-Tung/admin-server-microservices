package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import com.comit.services.areaRestriction.repository.AreaEmployeeTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
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

    @Override
    public int getNumberAreaEmployeeTimeOfAreaRestriction(int areaRestrictionId) {
        return areaEmployeeTimeRepository.countByAreaRestrictionId(areaRestrictionId);
    }

    @Override
    public List<AreaEmployeeTime> getAreaEmployeeTimeListOfEmployee(Integer employeeId) {
        return areaEmployeeTimeRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<AreaEmployeeTime> getAreaEmployeeTimeListOfAreaRestriction(Integer areaRestrictionId) {
        return areaEmployeeTimeRepository.findByAreaRestrictionId(areaRestrictionId);
    }
}
