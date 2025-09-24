package com.sayuru.Bodima_backend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Places")
public class Places {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int place_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    private String place_name;
    private Double latitude;
    private Double longitude;
    private String address;
    private double rent;
    private String description;
    private String type;
    private int rooms;
    private boolean furniture_availability;
    private String mobile;
    private int views;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;

    @ToString.Exclude
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Images> placeImages;



}
