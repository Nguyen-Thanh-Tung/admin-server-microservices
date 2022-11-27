package com.comit.services.account.business;

import com.comit.services.account.constant.CommonConstant;
import com.comit.services.account.constant.Const;
import com.comit.services.account.jwt.JwtProvider;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;

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

    @Override
    public boolean isBehaviorModule() {
        String moduleName = httpServletRequest.getHeader(CommonConstant.HEADER_MODULE);
        return Objects.equals(moduleName, CommonConstant.BEHAVIOR_MODULE);
    }

    @Override
    public String findCadreRoleFromModule() {
        User currentUser = getCurrentUser();
        if (userServices.isAdmin(currentUser)) {
            if (isTimeKeepingModule()) {
                return Const.ROLE_TIME_KEEPING_USER;
            } else if (isAreaRestrictionModule()) {
                return Const.ROLE_AREA_RESTRICTION_CONTROL_USER;
            } else if (isBehaviorModule()) {
                return Const.ROLE_BEHAVIOR_CONTROL_USER;
            }
        }
        return null;
    }
}
