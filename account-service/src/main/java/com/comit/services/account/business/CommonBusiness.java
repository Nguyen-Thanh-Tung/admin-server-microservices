package com.comit.services.account.business;

import com.comit.services.account.model.entity.User;

public interface CommonBusiness {
    User getCurrentUser();

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();
}
