package com.comit.services.account.business;

import com.comit.services.account.constant.CommonConstant;
import com.comit.services.account.jwt.JwtProvider;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class CommonBusinessImpl implements CommonBusiness {
    @Autowired
    JwtProvider tokenProvider;
    @Autowired
    private UserServices userServices;
    @Autowired
    private HttpServletRequest httpServletRequest;

    public User getCurrentUser() {
        String currentUsername = tokenProvider.getUserNameFromJwtToken(httpServletRequest.getHeader("token"));
        return userServices.getUser(currentUsername);
    }

    @Override
    public boolean isTimeKeepingModule() {
        String moduleName = httpServletRequest.getHeader(CommonConstant.HEADER_MODULE);
        return Objects.equals(moduleName, CommonConstant.TIME_KEEPING_MODULE);
    }

    @Override
    public boolean isAreaRestrictionModule() {
        String moduleName = httpServletRequest.getHeader(CommonConstant.HEADER_MODULE);
        return Objects.equals(moduleName, CommonConstant.AREA_RESTRICTION_MODULE);
    }
}
