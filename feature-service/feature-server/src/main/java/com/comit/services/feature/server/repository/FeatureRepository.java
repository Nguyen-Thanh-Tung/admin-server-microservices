package com.comit.services.feature.server.repository;

import com.comit.services.feature.server.model.Feature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, Integer> {
    List<Feature> findAllByOrderByIdDesc();

    Feature findById(int id);

    Feature findByName(String name);
}
