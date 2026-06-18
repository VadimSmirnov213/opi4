package org.example.service;

import org.example.dto.PointRequestDto;
import org.example.entity.PointEntity;
import org.example.repository.ResultsRepository;
import org.example.validation.RadiusValidator;
import org.example.validation.ValidationException;
import org.example.validation.XCoordinateValidator;
import org.example.validation.YCoordinateValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PointService {

    @Inject
    private XCoordinateValidator xValidator;

    @Inject
    private YCoordinateValidator yValidator;

    @Inject
    private RadiusValidator rValidator;

    @Inject
    private PointFactory pointFactory;

    @Inject
    private ResultsRepository resultsRepository;

    public PointEntity processPoint(PointRequestDto requestDto) throws ValidationException {
        xValidator.validate(requestDto.getX());
        yValidator.validate(requestDto.getY());
        rValidator.validate(requestDto.getR());

        PointEntity entity = pointFactory.createFromDto(requestDto);
        return resultsRepository.save(entity);
    }
}
