package tolga.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // "User" yerine "users" kullan
public class User extends PanacheEntity {
    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    public LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
