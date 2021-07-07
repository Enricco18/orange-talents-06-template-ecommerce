package br.com.zupacademy.enricco.mercadolivre.exception;

import br.com.zupacademy.enricco.mercadolivre.exception.dto.NotValidArgumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class NotValidArgumentHandler {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<NotValidArgumentDTO> handle(MethodArgumentNotValidException e){
        List<NotValidArgumentDTO> notValidArgumentList = new ArrayList<>();

        e.getBindingResult().getFieldErrors().stream().forEach(objectError -> {
            String message =  messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            NotValidArgumentDTO argumentDTO = new NotValidArgumentDTO(objectError.getField(),message);
            notValidArgumentList.add(argumentDTO);
        });

        e.getBindingResult().getGlobalErrors().stream().forEach(objectError -> {
            String message =  messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            NotValidArgumentDTO argumentDTO = new NotValidArgumentDTO(objectError.getObjectName(),message);
            notValidArgumentList.add(argumentDTO);
        });

        return  notValidArgumentList;
    }
}
