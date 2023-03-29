package com.ankitacodes.Ecom.helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import java.util.regex.Matcher;

public class ImageValidator implements ConstraintValidator<ValidImage, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value.isBlank()){

            return false;
        }else{

            return true;
        }
    }
}
