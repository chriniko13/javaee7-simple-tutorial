package com.chriniko.example.customers.control;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SurnameOnlyLettersAndNumsValidator implements ConstraintValidator<SurnameOnlyLettersAndNums, String> {

    private final Pattern pattern;

    @Override
    public void initialize(SurnameOnlyLettersAndNums constraintAnnotation) {
        //Note: add your initialization here if needed.
    }

    public SurnameOnlyLettersAndNumsValidator() {
        pattern = Pattern.compile("[a-zA-Z0-9]{5,50}");
    }


    @Override
    public boolean isValid(String surname, ConstraintValidatorContext context) {
        return pattern.matcher(surname).matches();
    }
}
