package com.comit.services.metadata.repository;

import com.comit.services.metadata.model.entity.Metadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends CrudRepository<Metadata, Integer> {
    boolean existsByMetadataId(String metadataId);

    Metadata findByMetadataId(String metadataId);

    Metadata findByMd5(String md5);

    Metadata findById(int id);
}
