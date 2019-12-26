package com.thebest.model;

import com.thebest.app.Constants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="user",
        uniqueConstraints=@UniqueConstraint(columnNames={"id"}))
@Data
public class User extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    private String descriptionProfile;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private UserType userType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection c = new ArrayList();
        if(UserType.ADMIN.equals(getUserType())){
            c.add((GrantedAuthority) () -> Constants.ADMIN);
        } else if(UserType.USER.equals(getUserType())){
            c.add((GrantedAuthority) () -> Constants.USER);
        }
        return c;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !super.isDeleted();
    }

    public enum UserType {
        USER, ADMIN
    }

}