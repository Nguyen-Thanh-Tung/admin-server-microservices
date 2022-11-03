package com.comit.services.userLog.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserLogDto extends BaseModelDto {
    private String content;
    private Date time;
    private String username;
}
