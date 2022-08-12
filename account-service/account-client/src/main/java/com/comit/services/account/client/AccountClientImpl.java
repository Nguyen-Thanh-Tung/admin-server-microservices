package com.comit.services.account.client;

import com.comit.services.account.client.response.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AccountClientImpl implements AccountClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://account-service";

    public AccountClientImpl(WebClient.Builder webClient) {

        this.webClientBuilder = webClient;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public UserResponse getCurrentUser(String token) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/users/current/user")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> UserResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CheckRoleResponse isCurrentUserSuperAdmin(String token) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/roles/is-super-admin")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CheckRoleResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public RoleListResponse getRolesOfCurrentUser(String token) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/users/current/roles")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> RoleListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public UserListResponse getUsersOfRole(String token, Integer roleId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/roles/" + roleId + "/users")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> UserListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public RoleResponse getRoleByName(String token, String roleName) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/roles/name/" + roleName)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> RoleResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CheckRoleResponse hasPermissionManageOrganization(String token) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/roles/organization")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CheckRoleResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public UserListResponse getUsersByOrganizationId(String token, int organizationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/users/organization/" + organizationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> UserListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public UserListResponse getAllUsersOfCurrentUser(String token) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/users/current/users")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> UserListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public UserResponse getUserById(String token, Integer userId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/users/" + userId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> UserResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CheckRoleResponse hasPermissionManagerLocation(String token, String type) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/roles/location/type/" + type)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CheckRoleResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CountUserResponse getNumberUserOfLocation(String token, Integer locationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/users/location/" + locationId + "/number-user")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CountUserResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
