package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NumberAccountResponse extends BaseResponse {
    @JsonProperty(value = "number_account")
    private int numberAccount;

    public NumberAccountResponse(UserErrorCode errorCode, int numberAccount) {
        super(errorCode);
        this.numberAccount = numberAccount;
    }
}
