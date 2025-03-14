package br.com.empresa.api_comercio.validations;

import br.com.empresa.api_comercio.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailConstraintImpl implements ConstraintValidator<EmailConstraint, String> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        boolean existsByEmail = repository.existsByEmail(email);
        return !existsByEmail;
    }
}
