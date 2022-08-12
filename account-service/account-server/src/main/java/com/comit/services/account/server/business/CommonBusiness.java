package com.comit.services.account.server.business;

import com.comit.services.account.server.model.User;

public interface CommonBusiness {
    User getCurrentUser();

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();
}
