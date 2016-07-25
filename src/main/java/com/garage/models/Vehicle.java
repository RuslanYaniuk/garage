package com.garage.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@Entity
public class Vehicle implements Serializable {

    private static final long serialVersionUID = -4108350885768318202L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 191)
    private String license;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @OneToOne(mappedBy = "vehicle")
    private ParkingLot parkingLot;

    public Vehicle() {
    }

    public Vehicle(String license, VehicleType type) {
        this.license = license;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
