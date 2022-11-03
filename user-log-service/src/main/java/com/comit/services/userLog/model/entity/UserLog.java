package com.comit.services.userLog.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_logs")
@Getter
@Setter
public class UserLog extends BaseModel {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "time")
    private Date time;

    @Column(name = "content")
    private String content;
}
