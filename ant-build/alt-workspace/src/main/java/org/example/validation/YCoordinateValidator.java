package org.example.validation;

import org.example.i18n.Messages;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class YCoordinateValidator implements CoordinateValidator<Double> {
    private static final double MIN_Y = -2.0;
    private static final double MAX_Y = 2.0;

    @Override
    public void validate(Double value) throws ValidationException {
        if (value == null) {
            throw new ValidationException(Messages.get("validation.y.null"));
        }

        if (value.isNaN() || value.isInfinite()) {
            throw new ValidationException(Messages.get("validation.y.nan"));
        }

        if (value < MIN_Y || value > MAX_Y) {
            throw new ValidationException(
                Messages.get("validation.y.range", MIN_Y, MAX_Y)
            );
        }

        if (value % 1 != 0.0) {
            throw new ValidationException(Messages.get("validation.y.integer"));
        }
    }
}
