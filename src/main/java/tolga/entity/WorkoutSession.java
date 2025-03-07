package tolga.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "workout_session") // "User" yerine "users" kullan
public class WorkoutSession extends PanacheEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    public User user;

    public LocalDate workoutDate;

    @Override
    public String toString() {
        return "{id=" + id + ", user=" + user + ", workoutDate=" + workoutDate + "}";
    }
}
