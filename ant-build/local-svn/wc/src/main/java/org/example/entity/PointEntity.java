package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "POINT_RESULTS")
public class PointEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_seq")
    @SequenceGenerator(name = "point_seq", sequenceName = "POINT_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "X_COORD", nullable = false)
    private Double x;

    @Column(name = "Y_COORD", nullable = false)
    private Double y;

    @Column(name = "R_VALUE", nullable = false)
    private Double r;

    @Column(name = "HIT", nullable = false)
    private Boolean hit;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;

    public PointEntity() {
        this.timestamp = LocalDateTime.now();
    }

    public PointEntity(Double x, Double y, Double r, Boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.timestamp = LocalDateTime.now();
    }

    public String getFormattedTime() {
        return timestamp != null ? timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";
    }

    public String getHitText() {
        if (hit == null) {
            return "Мимо";
        }
        return hit.equals(true) ? "Попадание" : "Мимо";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        PointEntity that = (PointEntity) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PointEntity(id=" + id + ", x=" + x + ", y=" + y + ", r=" + r + ", hit=" + hit + ", timestamp=" + timestamp + ")";
    }
}
