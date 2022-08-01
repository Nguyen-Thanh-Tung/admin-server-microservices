package com.comit.services.userLog.model.dto;

import com.comit.services.userLog.model.entity.UserLog;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter
@Setter
public class UserLogDto extends BaseModelDto {
    private String content;
    private Date time;

    public static UserLogDto convertUserLogToUserLogDto(UserLog userLog) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userLog, UserLogDto.class);
    }
}
