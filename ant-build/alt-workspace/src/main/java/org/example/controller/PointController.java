package org.example.controller;

import org.example.dto.PointRequestDto;
import org.example.entity.PointEntity;
import org.example.exception.ExceptionHandler;
import org.example.service.PointServiceAlt;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("pointController")
@RequestScoped
public class PointController {

    @Inject
    private PointServiceAlt pointService;

    @Inject
    private PointRequestDto pointRequestDtoAltRegex;

    @Inject
    private ResultsController resultsController;

    @Inject
    private ExceptionHandler exceptionHandler;

    public String checkPoint() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext != null) {
            facesContext.addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Debug inputs",
                    String.format("X=%s, Y=%s, R=%s", pointRequestDtoAltRegex.getX(), pointRequestDtoAltRegex.getY(), pointRequestDtoAltRegex.getR())
                )
            );
        }

        try {
            PointEntity entity = pointService.processPoint(pointRequestDtoAltRegex);
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
        }

        return null;
    }
}
