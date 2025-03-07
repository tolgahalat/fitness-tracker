package tolga.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "workout_detail") // "User" yerine "users" kullan
public class WorkoutDetail extends PanacheEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    public WorkoutSession session;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Exercise exercise;

    public int sets;
    public int reps;
    public double weight;

    @Column(nullable = false)
    public int rpe; // 1-10 arası RPE değeri

    @Override
    public String toString() {
        return "{id=" + id + ", session=" + session + ", exercise=" + exercise +
                ", sets=" + sets + ", reps=" + reps + ", weight=" + weight + ", rpe=" + rpe + "}";
    }
}

