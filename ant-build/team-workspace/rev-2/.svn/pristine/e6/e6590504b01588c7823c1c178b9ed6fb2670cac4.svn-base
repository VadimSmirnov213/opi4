package org.example.service;

import org.example.dto.PointRequestDto;
import org.example.entity.PointEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PointFactory {

    @Inject
    private AreaChecker areaChecker;

    public PointEntity createFromDto(PointRequestDto requestDto) {
        boolean hit = areaChecker.checkHit(
            requestDto.getX() != null ? requestDto.getX() : 0.0,
            requestDto.getY() != null ? requestDto.getY() : 0.0,
            requestDto.getR() != null ? requestDto.getR() : 0.0
        );

        return new PointEntity(
            requestDto.getX(),
            requestDto.getY(),
            requestDto.getR(),
            hit
        );
    }
}
