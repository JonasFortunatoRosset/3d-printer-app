package br.com.bkd.apiprinterapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "printers")
public class Printer {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String model;
    @Column(name = "power_watts", nullable = false)
    private Integer powerWatts;
    @Column(name = "cost_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerHour;
    @Column(nullable = false)
    private Boolean active;

    public Printer() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPowerWatts() {
        return powerWatts;
    }

    public void setPowerWatts(Integer powerWatts) {
        this.powerWatts = powerWatts;
    }

    public BigDecimal getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(BigDecimal costPerHour) {
        this.costPerHour = costPerHour;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
