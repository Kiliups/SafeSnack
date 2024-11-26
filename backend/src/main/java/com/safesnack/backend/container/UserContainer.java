package com.safesnack.backend.container;

import com.safesnack.backend.model.Authority;
import com.safesnack.backend.model.UserMeta;
import lombok.Data;

import java.util.List;

@Data
public class UserContainer {
    private UserMeta user;
    private List<Authority> roles;
}
