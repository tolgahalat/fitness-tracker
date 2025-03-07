package tolga.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "exercises") // "User" yerine "users" kullan
public class Exercise extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    public String description;
    public String muscleGroup;

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + "', description='" + description + "', muscleGroup='" + muscleGroup + "'}";
    }
}
