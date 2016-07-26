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
public class Garage implements Serializable {

    private static final long serialVersionUID = -62410633667895800L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer numberOfLevels;

    private Integer lotsOnLevel;

    @OneToMany(mappedBy = "garage")
    private List<Level> levels = new ArrayList<>();

    public Garage() {
    }

    public Garage(Integer numberOfLevels, Integer lotsOnLevel) {
        this.numberOfLevels = numberOfLevels;
        this.lotsOnLevel = lotsOnLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(Integer numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }

    public Integer getLotsOnLevel() {
        return lotsOnLevel;
    }

    public void setLotsOnLevel(Integer lotsOnLevel) {
        this.lotsOnLevel = lotsOnLevel;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Garage garage = (Garage) o;

        return !(id != null ? !id.equals(garage.id) : garage.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
