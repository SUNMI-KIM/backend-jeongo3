package kr.kookmin.jeongo3.User;

import lombok.Getter;

@Getter
public enum UserRole {
    HIGH("ROLE_HIGH"), UNIV("ROLE_UNIV"), ADMIN("ROLE_ADMIN");

    private String roleName;
    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
