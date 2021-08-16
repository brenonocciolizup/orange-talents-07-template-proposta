package br.com.zupacademy.brenonoccioli.proposta.validacao.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DocumentoValidoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentoValido {

    String message() default "Documento inválido!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}