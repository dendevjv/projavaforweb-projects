package projava4webbook.custom_constraint.site.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence> {

    @Override
    public void initialize(NotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        String trimmed;
        if (value instanceof String) {
            trimmed = ((String) value).trim();
        } else {
            trimmed = value.toString().trim();
        }
        boolean valid = trimmed.length() > 0;
        return valid;
    }

}
