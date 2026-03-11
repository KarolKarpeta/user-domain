package pl.karpeta.userdomain.user.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = true, unique = false)
    public String name;

    @Column(nullable = false)
    public String password;

    public boolean active;

    public UserEntity() {}

    public UserEntity(Long id, String email, String name, String password, boolean active) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
