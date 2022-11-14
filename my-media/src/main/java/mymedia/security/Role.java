package mymedia.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appRoleId;
    @Size(max = 50, message = "Role name cannot be more than 50 characters")
    private String name;

    public int getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(int appRoleId) {
        this.appRoleId = appRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
