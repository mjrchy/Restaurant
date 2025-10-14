package ku.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

//auto gen get and set
@Data
@Entity
public class Restaurant {
//    secure but not efficient
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private double rating;
    private String location;
    private Instant createdAt;
}
