package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountResponse extends BaseResponse {
    private int number;

    public CountResponse(AreaRestrictionErrorCode errorCode, int number) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.number = number;
    }
}
