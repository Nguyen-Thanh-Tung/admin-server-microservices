package com.comit.services.feature.repository;

import com.comit.services.feature.model.entity.Feature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, Integer> {
    Feature findById(int id);

    Feature findByName(String name);
}
