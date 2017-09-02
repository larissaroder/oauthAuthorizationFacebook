package br.com.db1.exercicio5.util;

import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;

import java.util.Objects;

public final class ResponseUtils {

    private ResponseUtils() {
    }

    public static boolean isNotValidResponse(HttpEntity<String> response) {
        return (Objects.isNull(response) || StringUtils.isEmpty(response));
    }
}
