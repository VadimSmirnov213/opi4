package org.example.validation;

import org.example.i18n.Messages;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RadiusValidator implements CoordinateValidator<Double> {
    private static final double MIN_R = 1.0;
    private static final double MAX_R = 4.0;
    private static final double STEP = 0.25;
    private static final double EPSILON = 0.001;

    @Override
    public void validate(Double value) throws ValidationException {
        if (value == null) {
            throw new ValidationException(Messages.get("validation.r.null"));
        }

        if (value.isNaN() || value.isInfinite()) {
            throw new ValidationException(Messages.get("validation.r.nan"));
        }

        if (value < MIN_R || value > MAX_R) {
            throw new ValidationException(
                Messages.get("validation.r.range", MIN_R, MAX_R)
            );
        }

        double remainder = (value - MIN_R) % STEP;
        if (Math.abs(remainder) > EPSILON && Math.abs(remainder - STEP) > EPSILON) {
            throw new ValidationException(
                Messages.get("validation.r.step", STEP)
            );
        }
    }
}
