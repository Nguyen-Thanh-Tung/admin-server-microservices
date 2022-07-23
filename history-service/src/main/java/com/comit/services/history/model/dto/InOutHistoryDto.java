package com.comit.services.history.model.dto;

import com.comit.services.history.model.entity.InOutHistory;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter
@Setter
public class InOutHistoryDto extends BaseModelDto {
    private String type;

    private Date time;

    public static InOutHistoryDto convertInOutHistoryToInOutHistoryDto(InOutHistory inOutHistory) {
        if (inOutHistory == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(inOutHistory, InOutHistoryDto.class);
    }
}
