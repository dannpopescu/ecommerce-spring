package com.danpopescu.shop.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.json.JsonMergePatch;
import javax.json.JsonValue;

@Component
@RequiredArgsConstructor
public class PatchHelper {

    private final ObjectMapper objectMapper;

    public <T> T mergePatch(JsonMergePatch mergePatch, T bean, Class<T> beanClass) {
        JsonValue target = objectMapper.convertValue(bean, JsonValue.class);
        JsonValue patched = mergePatch.apply(target);
        return objectMapper.convertValue(patched, beanClass);
    }
}
