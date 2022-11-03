package com.comit.services.organization.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseModelDto implements Serializable {
    private int id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("created_at")
    private Date createAt;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("updated_at")
    private Date updateAt;
}
