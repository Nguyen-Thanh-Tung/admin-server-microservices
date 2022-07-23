package com.comit.services.metadata.service;

import com.comit.services.metadata.constant.MetadataErrorCode;
import com.comit.services.metadata.exception.RestApiException;
import com.comit.services.metadata.model.entity.Metadata;
import com.comit.services.metadata.repository.MetadataRepository;
import com.comit.services.metadata.util.IDGeneratorUtil;
import com.comit.services.metadata.util.TimeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class MetadataServicesImpl implements MetadataServices {

    @Autowired
    MetadataRepository metadataRepository;
    @Autowired
    Environment env;

    @Override
    public boolean existMetadataByMetadataId(String metadataId) {
        return metadataRepository.existsByMetadataId(metadataId);
    }

    @Override
    public Metadata saveMetadata(MultipartFile inFile) {
        try {
            // copy file stream to reuse
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(inFile.getInputStream(), baos);
            String md5 = DigestUtils.md5Hex(baos.toByteArray());
            Metadata metadata = getMetadataByMd5(md5);
            if (metadata != null) {
                return metadata;
            }

            // md5 not exist => save image
            // done check null before
            String[] tmp = Objects.requireNonNull(inFile.getOriginalFilename()).split("\\.");
            String extension = tmp[tmp.length - 1];
            String id = genID();
            String currentDate = TimeUtil.getCurrentDateStr();
            String direction = env.getProperty("storage.folder") + "/" + currentDate;
            String direction2 = direction + "/" + extension;
            String path = direction2 + "/" + id + "." + extension;
            File director = new File(direction);
            if (!director.exists()) {
                if (director.mkdir()) {
                    File director2 = new File(direction2);
                    director2.mkdir();
                }
            } else {
                File director2 = new File(direction2);
                if (!director2.exists()) {
                    director2.mkdir();
                }
            }
            File file = new File(path);
            Files.copy(inFile.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            metadata = new Metadata(id, md5, env.getProperty("storage.host") + path, extension);
            metadataRepository.save(metadata);
            return metadata;
        } catch (Exception e) {
            throw new RestApiException(MetadataErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public Metadata saveMetadata(String path, String md5, String type) {
        Metadata metadata = md5 == null ? null : getMetadataByMd5(md5);
        if (metadata != null) {
            return metadata;
        } else {
            String id = null;
            try {
                id = genID();
            } catch (IOException e) {
                e.printStackTrace();
            }
            metadata = new Metadata();
            metadata.setMetadataId(id);
            metadata.setPath(path);
            metadata.setMd5(md5);
            metadata.setType(type);
            return metadataRepository.save(metadata);
        }
    }

    @Override
    public Metadata getMetadata(int id) {
        return metadataRepository.findById(id);
    }

    // ======================== GenId Util ========================
    private String genID() throws IOException {
        String id = IDGeneratorUtil.gen();
        int i = 0;
        while (existMetadataByMetadataId(id)) {
            if (i == 1000) throw new RestApiException(MetadataErrorCode.CANT_GEN_ID);
            id = IDGeneratorUtil.gen();
            i++;
        }
        return id;
    }

    public Metadata getMetadataByMetadataId(String metadataId) {
        return metadataRepository.findByMetadataId(metadataId);
    }


    public Metadata getMetadataByMd5(String md5) {
        return metadataRepository.findByMd5(md5);
    }
}
