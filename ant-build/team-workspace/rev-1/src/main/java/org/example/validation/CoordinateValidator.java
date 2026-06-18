package org.example.validation;

public interface CoordinateValidator<T> {
    void validate(T value) throws ValidationException;
}
