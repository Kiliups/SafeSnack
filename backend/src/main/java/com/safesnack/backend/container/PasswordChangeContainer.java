package com.safesnack.backend.container;

import lombok.Data;

@Data
public class PasswordChangeContainer {
    private String oldPassword;
    private String newPassword;
}
