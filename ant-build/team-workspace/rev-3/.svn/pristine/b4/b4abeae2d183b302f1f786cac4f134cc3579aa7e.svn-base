package org.example.validation;

import org.example.i18n.Messages;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class XCoordinateValidator implements CoordinateValidator<Double> {
    private static final double MIN_X = -3.0;
    private static final double MAX_X = 5.0;

    @Override
    public void validate(Double value) throws ValidationException {
        if (value == null) {
            throw new ValidationException(Messages.get("validation.x.null"));
        }

        if (value.isNaN() || value.isInfinite()) {
            throw new ValidationException(Messages.get("validation.x.nan"));
        }

        if (value < MIN_X || value > MAX_X) {
            throw new ValidationException(
                Messages.get("validation.x.range", MIN_X, MAX_X)
            );
        }
    }
}
