package pe.edu.upeu.asistencia.componente.validacion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CODIGOValidator implements ConstraintValidator<CODIGO, String> {
    public CODIGOValidator() {
        // Un constructor público sin argumentos es requerido por el framework
    }
    @Override
    public void initialize(CODIGO constraintAnnotation) {
        // Opcional: para inicializar el validador con valores de la anotación
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // La validación @NotBlank se encargará de esto
        }
        return value.matches("\\d{3}");
    }
}
