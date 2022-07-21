package com.comit.services.account.controller;

import com.comit.services.account.business.UserBusiness;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.controller.request.UpdateRoleForUserRequest;
import com.comit.services.account.controller.response.*;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.model.dto.LocationDto;
import com.comit.services.account.model.dto.OrganizationDto;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.constant.UserErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    /**
     * Get all user with permission
     * Super admin get all admin
     * Admin get all user of them
     *
     * @return BaseResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllUser() {
        List<UserDto> userDtos = userBusiness.getAllUser();
        return new ResponseEntity<>(new UserListResponse(UserErrorCode.SUCCESS, userDtos), HttpStatus.OK);
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
    public ResponseEntity<UserResponse> getUser(@PathVariable int id) throws IOException {
        UserDto userDto = userBusiness.getUser(id);
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
    public ResponseEntity<BaseResponse> addUser(@Valid @RequestBody AddUserRequest addUserRequest) throws IOException {
        UserDto userDto = userBusiness.addUser(addUserRequest);

        if (userDto != null) {
            return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserResponse(UserErrorCode.CAN_NOT_ADD_USER, null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update role for user
     * Super admin update role for admin
     * Admin update role for user
     *
     * @param id                       user id
     * @param updateRoleForUserRequest user info
     * @return UserResponse
     */
    @PutMapping(value = "/{id}/roles")
    public ResponseEntity<UserResponse> updateRoleUser(@PathVariable int id, @RequestBody UpdateRoleForUserRequest updateRoleForUserRequest) throws IOException {
        UserDto userDto = userBusiness.updateRoleUser(id, updateRoleForUserRequest);
        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
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
    public ResponseEntity<BaseResponse> lockOrUnlockUser(@PathVariable int id, @RequestBody LockOrUnlockRequest lockOrUnlockRequest) throws IOException {
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
    public ResponseEntity<BaseResponse> uploadAvatar(@PathVariable int id, HttpServletRequest servletRequest) throws IOException {
        UserDto userDto = userBusiness.uploadAvatar(id, servletRequest);

        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable int id, HttpServletRequest servletRequest) throws IOException {
        UserDto userDto = userBusiness.updateUser(id, servletRequest);

        return new ResponseEntity<>(new UserResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<BaseResponse> getUsersByOrganizationId(@PathVariable(name = "organizationId") int organizationId) {
        List<UserDto> userDtos = userBusiness.getUsersByOrganizationId(organizationId);
        return new ResponseEntity<>(new UserListResponse(UserErrorCode.SUCCESS, userDtos), HttpStatus.OK);
    }

    @GetMapping("/current/location")
    ResponseEntity<LocationResponse> getLocationOfCurrentUser() {
        LocationDto locationDto = userBusiness.getLocationOfCurrentUser();
        if (locationDto == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return new ResponseEntity<>(new LocationResponse(UserErrorCode.SUCCESS, locationDto), HttpStatus.OK);
    }

    @GetMapping("/current/organization")
    ResponseEntity<OrganizationResponse> getOrganizationOfCurrentUser() {
        OrganizationDto organizationDto = userBusiness.getOrganizationOfCurrentUser();
        return new ResponseEntity<>(new OrganizationResponse(UserErrorCode.SUCCESS, organizationDto), HttpStatus.OK);
    }
}
