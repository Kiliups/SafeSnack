package com.safesnack.backend.container;

import com.safesnack.backend.model.Authority;
import com.safesnack.backend.model.UserMetaBase;
import lombok.Data;

import java.util.List;

@Data
public class UserContainer {
    private UserMetaBase user;
    private List<Authority> roles;
}
