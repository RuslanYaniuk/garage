package com.garage.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@Entity
public class Level implements Serializable {

    private static final long serialVersionUID = 6159127928126491245L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer number;

    @ManyToOne
    private Garage garage;

    @OneToMany(mappedBy = "level", fetch = FetchType.EAGER)
    private List<ParkingLot> parkingLots = new ArrayList<>();

    public Level() {
    }

    public Level(Integer number, Garage garage) {
        this.number = number;
        this.garage = garage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;

        if (number != null ? !number.equals(level.number) : level.number != null) return false;
        return !(garage != null ? !garage.equals(level.garage) : level.garage != null);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (garage != null ? garage.hashCode() : 0);
        return result;
    }
}
