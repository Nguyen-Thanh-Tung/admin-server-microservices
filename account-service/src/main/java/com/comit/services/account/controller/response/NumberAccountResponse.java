package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberAccountResponse extends BaseResponse {
    @JsonProperty(value = "number_account")
    private int numberAccount;

    public NumberAccountResponse(UserErrorCode errorCode, int numberAccount) {
        super(errorCode);
        this.numberAccount = numberAccount;
    }
}
