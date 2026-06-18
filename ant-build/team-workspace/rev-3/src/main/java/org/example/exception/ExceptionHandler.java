package org.example.exception;

import org.example.validation.ValidationException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class ExceptionHandler {

    public FacesMessage handleValidationException(ValidationException e) {
        return new FacesMessage(
            FacesMessage.SEVERITY_ERROR,
            "Ошибка валидации",
            e.getMessage()
        );
    }

    public FacesMessage handleRepositoryException(RepositoryException e) {
        return new FacesMessage(
            FacesMessage.SEVERITY_ERROR,
            "Ошибка базы данных",
            e.getMessage() != null ? e.getMessage() : "Произошла ошибка при работе с базой данных"
        );
    }

    public FacesMessage handleException(Exception e) {
        if (e instanceof ValidationException) {
            return handleValidationException((ValidationException) e);
        }
        if (e instanceof RepositoryException) {
            return handleRepositoryException((RepositoryException) e);
        }
        return new FacesMessage(
            FacesMessage.SEVERITY_ERROR,
            "Ошибка",
            "Произошла ошибка при обработке запроса: " + e.getMessage()
        );
    }

    public void addMessageToContext(FacesMessage message) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            facesContext.addMessage(null, message);
        }
    }

    public void handleAndAddToContext(Exception e) {
        FacesMessage message = handleException(e);
        addMessageToContext(message);
    }
}
