package br.com.zupacademy.brenonoccioli.proposta.validacao;

import br.com.zupacademy.brenonoccioli.proposta.validacao.annotations.ErroPadronizadoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ErrosFormHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroFormDto>> erroDeValidacaoHandler(MethodArgumentNotValidException exception){

        List<ErroFormDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String msg = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormDto erro = new ErroFormDto(e.getField(), msg);
            dto.add(erro);
        });

        return ResponseEntity.status(400).body(dto);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroPadronizadoApi> apiExceptionHandler(ApiException apiException){
        Collection<String> messages = new ArrayList<>();
        messages.add(apiException.getReason());

        ErroPadronizadoApi erros = new ErroPadronizadoApi(messages);

        return ResponseEntity.status(apiException.getHttpStatus()).body(erros);
    }
}
