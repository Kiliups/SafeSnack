package com.safesnack.backend.container;

import lombok.Data;

@Data
public class PasswordResetContainer {
    private String oldPassword;

    private String token;

    private String newPassword;
}
