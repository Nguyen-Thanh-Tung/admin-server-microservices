package com.comit.services.account.controller;

import com.comit.services.account.business.UserBusiness;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.controller.response.*;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.model.dto.BaseUserDto;
import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @Autowired
    private UserServices userServices;

    /**
     * Get all user with permission
     * Super admin get all admin
     * Admin get all user of them
     *
     * @return BaseResponse
     */

    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllUser(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false) String search
    ) {
        Page<User> users = userBusiness.getAllUser(size, page, search, status);
        List<UserDto> userDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (users != null) {
            currentPage = users.getNumber();
            totalItems = users.getTotalElements();
            totalPages = users.getTotalPages();
            userDtos = userBusiness.getAllUser(users.getContent());
        }
        return new ResponseEntity<>(new UserListResponse(UserErrorCode.SUCCESS, userDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }


    @GetMapping(value = "/account-number")
    public ResponseEntity<BaseResponse> getNumberAccount() {
        int numberAccount = userBusiness.getNumberAccount();
        return new ResponseEntity<>(new NumberAccountResponse(UserErrorCode.SUCCESS, numberAccount), HttpStatus.OK);
    }

    /**
     * Get info of user
     * Super admin only read admin of them
     * Admin only read user of them
     *
     * @param id user id
     * @return UserResponse
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable int id) {
        UserDto userDto = userBusiness.getUser(id);
        if (Objects.isNull(userDto)) {
            return new ResponseEntity<>(new UserResponse(UserErrorCode.PERMISSION_DENIED, userDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/base")
    public ResponseEntity<UserResponse> getUserBase(@PathVariable int id) {
        BaseUserDto userDto = userBusiness.getUserBase(id);
        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    /**
     * Add user
     * Super admin add admin
     * Admin add user
     *
     * @param addUserRequest Info user in request
     * @return BaseResponse
     */

    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        UserDto userDto = userBusiness.addUser(addUserRequest);

        if (userDto != null) {
            return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserResponse(UserErrorCode.CAN_NOT_ADD_USER, null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete user
     * Super admin delete admin
     * Admin delete user
     *
     * @param id user id
     * @return BaseResponse
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable int id) {
        boolean deleteSuccess = userBusiness.deleteUser(id);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(UserErrorCode.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BaseResponse(UserErrorCode.CAN_NOT_DELETE_USER), HttpStatus.OK);
        }
    }

    /**
     * Lock or unlock user
     *
     * @param id:                  user id
     * @param lockOrUnlockRequest: LockOrUnlockRequest
     * @return UserResponse
     */
    @PutMapping(value = "/{id}/lock")
    public ResponseEntity<BaseResponse> lockOrUnlockUser(@PathVariable int id, @RequestBody LockOrUnlockRequest lockOrUnlockRequest) {
        UserDto userDto = userBusiness.lockOrUnlockUser(id, lockOrUnlockRequest);
        return new ResponseEntity<>(new UserResponse(userDto != null ? UserErrorCode.SUCCESS : UserErrorCode.FAIL, userDto), HttpStatus.OK);
    }

    /**
     * @param id             : user id
     * @param servletRequest : HttpServletRequest
     * @return UserResponse
     * @throws IOException
     */
    @PutMapping(value = "/{id}/avatar")
    public ResponseEntity<BaseResponse> uploadAvatar(@PathVariable int id, HttpServletRequest servletRequest) {
        UserDto userDto = userBusiness.uploadAvatar(id, servletRequest);

        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable int id, HttpServletRequest servletRequest) {
        UserDto userDto = userBusiness.updateUser(id, servletRequest);

        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/number-user")
    public ResponseEntity<BaseResponse> getUsersByOrganizationId(@PathVariable(name = "organizationId") int organizationId) {
        // check organizationId exist
        OrganizationDtoClient organization = userServices.getOrganizationById(organizationId);
        if (organization == null) {
            throw new AccountRestApiException(UserErrorCode.ORGANIZATION_NOT_EXIST);
        }
        int numberUser = userBusiness.getNumberUserOfOrganization(organizationId);
        return new ResponseEntity<>(new CountResponse(UserErrorCode.SUCCESS, numberUser), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/number-all-user")
    public ResponseEntity<BaseResponse> getAllUsersByOrganizationId(@PathVariable(name = "organizationId") int organizationId) {
        int numberUser = userBusiness.getNumberAllUserOfOrganization(organizationId);
        return new ResponseEntity<>(new CountResponse(UserErrorCode.SUCCESS, numberUser), HttpStatus.OK);
    }

    @GetMapping("/current")
    ResponseEntity<UserResponse> getCurrentUser() {
        BaseUserDto userDto = userBusiness.getCurrentUser();
        if (userDto == null) {
            return new ResponseEntity<>(new UserResponse(UserErrorCode.USER_NOT_EXIST, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    @GetMapping("/current/users")
    ResponseEntity<UserListBaseResponse> getUsersOfCurrentUser() {
        List<BaseUserDto> userDtos = userBusiness.getUsersOfCurrentUser();
        return new ResponseEntity<>(new UserListBaseResponse(UserErrorCode.SUCCESS, userDtos), HttpStatus.OK);
    }

    @GetMapping("/current/roles")
    ResponseEntity<RoleListResponse> getRolesOfCurrentUser() {
        List<RoleDto> roleDtos = userBusiness.getRolesOfCurrentUser();
        return new ResponseEntity<>(new RoleListResponse(RoleErrorCode.SUCCESS, roleDtos), HttpStatus.OK);
    }

    @GetMapping("/location/{locationId}/number-user")
    ResponseEntity<BaseResponse> getNumberUserOfLocation(@PathVariable Integer locationId) {
        int numberUserOfLocation = userBusiness.getNumberUserOfLocation(locationId);
        return new ResponseEntity<>(new CountResponse(UserErrorCode.SUCCESS, numberUserOfLocation), HttpStatus.OK);
    }

    @GetMapping("/number-user-of-roles")
    ResponseEntity<CountResponse> getNumberUserOfRoles(@RequestParam("role_ids") String roleIds) {
        int numberUserOfRoles = userBusiness.getNumberUserOfRoles(roleIds);
        return new ResponseEntity<>(new CountResponse(UserErrorCode.SUCCESS, numberUserOfRoles), HttpStatus.OK);
    }

    @GetMapping("/number-organization-of-roles")
    ResponseEntity<CountResponse> getNumberOrganizationOfRoles(@RequestParam("role_ids") String roleIds) {
        int numberUserOfRoles = userBusiness.getNumberOrganizationOfRoles(roleIds);
        return new ResponseEntity<>(new CountResponse(UserErrorCode.SUCCESS, numberUserOfRoles), HttpStatus.OK);
    }

    @PostMapping("/{id}/resend-code")
    ResponseEntity<BaseResponse> resendCode(@PathVariable Integer id) {
        boolean resendCodeSuccess = userBusiness.resendCode(id);
        return new ResponseEntity<>(new BaseResponse(resendCodeSuccess ? UserErrorCode.SUCCESS : UserErrorCode.FAIL), HttpStatus.OK);
    }

    @GetMapping("/current/check-role")
    ResponseEntity<BaseResponse> getRealRoleOfCurrentUser(@RequestParam String roleNeedCheck) {
        boolean hasRole = userBusiness.checkRole(roleNeedCheck);
        return new ResponseEntity<>(new CheckRoleResponse(UserErrorCode.SUCCESS.getCode(), UserErrorCode.SUCCESS.getMessage(), hasRole), HttpStatus.OK);
    }
}
