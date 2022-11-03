package com.comit.services.history.client.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoClient {
    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private String status;

    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("avatar_id")
    private Integer avatarId;
    @JsonProperty("organization_id")
    private Integer organizationId;
    @JsonProperty("location_id")
    private Integer locationId;
}
