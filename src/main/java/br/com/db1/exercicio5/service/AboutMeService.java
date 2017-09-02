package br.com.db1.exercicio5.service;

import br.com.db1.exercicio5.exception.ResponseException;
import br.com.db1.exercicio5.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * utilizando o token fazer a chamada para obter os dados do usuário
 */
@Service
class AboutMeService {

    private static final String ERROR_USER = "Obteu um erro ao obter os dados do usuário";

    @Value("${url.about.me}")
    private String url;

    String getListUser(String accessToken) throws ResponseException {

        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_token", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        URI url = builder.build().encode().toUri();
        HttpEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        if (ResponseUtils.isNotValidResponse(response)) {
            throw new ResponseException(ERROR_USER);
        }
        return response.getBody();
    }
}
