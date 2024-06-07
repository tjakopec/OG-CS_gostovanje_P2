package hr.ogcs.springbootcrudpostgres.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "garage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "car_model")
    protected String carModel;
    @Column(name = "horse_power")
    protected Integer hp;
    @Column(name = "production_year")
    protected Integer year;
    @Column(name = "designer")
    protected String designer;
    @CreationTimestamp
    @Column(name = "created")
    protected Timestamp created;
    @UpdateTimestamp
    @Column(name = "updated")
    protected Timestamp updated;

}
