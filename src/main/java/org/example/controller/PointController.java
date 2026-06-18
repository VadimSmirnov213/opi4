package org.example.controller;

import org.example.dto.PointRequestDto;
import org.example.entity.PointEntity;
import org.example.exception.ExceptionHandler;
import org.example.monitoring.MBeanRegistrar;
import org.example.monitoring.PointMetricsService;
import org.example.service.PointService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("pointController")
@RequestScoped
public class PointController {

    @Inject
    private PointService pointService;

    @Inject
    private PointRequestDto pointRequestDto;

    @Inject
    private ResultsController resultsController;

    @Inject
    private ExceptionHandler exceptionHandler;

    @Inject
    private PointMetricsService pointMetricsService;

    @Inject
    private MBeanRegistrar mBeanRegistrar;

    public String checkPoint() {
        mBeanRegistrar.ensureRegistered();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Double x = pointRequestDto.getX();
        Double y = pointRequestDto.getY();

        if (Boolean.TRUE.equals(pointRequestDto.getGraphClick())) {
            pointMetricsService.recordGraphClick();
        }
        if (pointMetricsService.isOutsideDisplay(x, y) && x != null && y != null) {
            pointMetricsService.recordOutOfBounds(x, y);
        }

        if (facesContext != null) {
            facesContext.addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Debug inputs",
                    String.format("X=%s, Y=%s, R=%s", pointRequestDto.getX(), pointRequestDto.getY(), pointRequestDto.getR())
                )
            );
        }

        try {
            PointEntity entity = pointService.processPoint(pointRequestDto);
            pointMetricsService.recordSavedPoint(Boolean.TRUE.equals(entity.getHit()));
            resultsController.loadResults();

            if (facesContext != null) {
                facesContext.addMessage(
                    null,
                    new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Точка проверена",
                        String.format(
                            "X=%.2f, Y=%.0f, R=%.2f - %s",
                            entity.getX() != null ? entity.getX() : 0.0,
                            entity.getY() != null ? entity.getY() : 0.0,
                            entity.getR() != null ? entity.getR() : 0.0,
                            Boolean.TRUE.equals(entity.getHit()) ? "Попадание" : "Мимо"
                        )
                    )
                );
            }
        } catch (Exception e) {
            exceptionHandler.handleAndAddToContext(e);
        } finally {
            pointRequestDto.setGraphClick(false);
        }

        return null;
    }
}
