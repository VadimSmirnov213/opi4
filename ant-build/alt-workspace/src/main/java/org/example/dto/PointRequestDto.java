package org.example.dto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("pointRequestDtoAltRegex")
@RequestScoped
public class PointRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double x = null;
    private Double y = null;
    private Double r = 2.0;

    public PointRequestDto() {
    }

    public PointRequestDto(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @PostConstruct
    public void init() {
        if (r == null) {
            r = 2.0;
        }
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

    @Override
    public String toString() {
        return "PointRequestDto(x=" + x + ", y=" + y + ", r=" + r + ")";
    }
}
