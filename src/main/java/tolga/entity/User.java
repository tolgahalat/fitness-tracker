package tolga.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // "User" yerine "users" kullan
public class User extends PanacheEntity {

    @NotBlank
    @Column(nullable = false)
    public String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    public LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }

    // Yardımcı metot: email ile bul
    public static User findByEmail(String email){
        return find("email", email).firstResult();
    }
}
