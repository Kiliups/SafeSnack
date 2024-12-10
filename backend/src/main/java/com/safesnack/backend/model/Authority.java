package com.safesnack.backend.model;

import com.safesnack.backend.model.absctractModel.AbstractIdEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authority extends AbstractIdEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthorityEnum authority;

    public String getAuthority() {
        // this method needs to return a string
        // as per the GrantedAuthority interface,
        // therefore name() is called on the enum.
        return authority.name();
    }
}