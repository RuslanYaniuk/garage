package com.garage.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@Entity
public class ParkingLot implements Serializable {

    private static final long serialVersionUID = -4808980810017101165L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer number;

    @OneToOne
    private Vehicle vehicle;

    @ManyToOne
    private Garage garage;

    @ManyToOne
    private Level level;

    public ParkingLot() {
    }

    public ParkingLot(Integer number, Level level) {
        this.number = number;
        this.level = level;
        this.garage = level.getGarage();
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingLot that = (ParkingLot) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        return !(level != null ? !level.equals(that.level) : that.level != null);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}
