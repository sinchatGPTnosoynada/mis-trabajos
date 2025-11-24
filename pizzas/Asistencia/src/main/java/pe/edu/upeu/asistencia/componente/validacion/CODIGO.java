package pe.edu.upeu.asistencia.componente.validacion;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

// Anotación personalizada para validar el codigo de 3 dígitos
@Documented
@Constraint(validatedBy = CODIGOValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CODIGO {
    String message() default "El odigo debe tener 3 dígitos numéricos.";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
