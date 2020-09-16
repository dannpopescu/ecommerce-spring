package com.danpopescu.shop.util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorsHttpMessageConverter implements HttpMessageConverter<Errors> {

    private final ObjectMapper objectMapper;
    private final MessageSourceAccessor messageSourceAccessor;

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return Errors.class.isAssignableFrom(clazz);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON);
    }

    @Override
    public Errors read(Class<? extends Errors> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void write(Errors errors, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        objectMapper.writeValue(outputMessage.getBody(), new ErrorsJson(errors));

    }

    @RequiredArgsConstructor
    private class ErrorsJson {
        private final Errors errors;

        @JsonAnyGetter
        Map<String, String> toMap() {
            Map<String, String> fields = new HashMap<>();

            errors.getFieldErrors().forEach(fieldError -> {
                try {
                    fields.put(fieldError.getField(), messageSourceAccessor.getMessage(fieldError));
                } catch (NoSuchMessageException ex) {
                    log.warn(ex.getMessage());
                }
            });

            return fields;
        }
    }
}
