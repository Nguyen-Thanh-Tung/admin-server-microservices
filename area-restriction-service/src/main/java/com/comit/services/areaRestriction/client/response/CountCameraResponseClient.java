package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountCameraResponseClient extends BaseResponseClient {
    private int number;

    public CountCameraResponseClient(AreaRestrictionErrorCode errorCode, int number) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.number = number;
    }
}
