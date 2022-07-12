package com.comit.location.service;
import com.comit.location.model.entity.Location;
import com.comit.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServicesImpl implements LocationServices {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location getLocation(Integer organizationId, int locationId) {
        return locationRepository.findByIdAndOrganizationId(locationId, organizationId);
    }

    @Override
    public Location getLocation(int locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public Page<Location> getLocationPage(Integer organizationId, String type, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return locationRepository.findByOrganizationIdAndTypeAndNameContainingOrOrganizationIdAndTypeAndCodeContainingOrderByUpdateAtDesc(organizationId, type, search, organizationId, type, search, paging);
        }
        return locationRepository.findByOrganizationIdAndTypeOrderByUpdateAtDesc(organizationId, type, paging);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public boolean deleteLocation(int id) {
        try {
            locationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existLocation(String code, Integer organizationId) {
        return locationRepository.existsByCodeAndOrganizationId(code, organizationId);
    }

    @Override
    public List<Location> getAllLocationByOrganizationId(int organizationId) {
        return locationRepository.findAllByOrganizationId(organizationId);
    }
}
