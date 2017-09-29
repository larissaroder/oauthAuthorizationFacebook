package br.com.db1.exercicio5.service;

import br.com.db1.exercicio5.exception.ResponseException;
import br.com.db1.exercicio5.util.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * obter o token por meio do código informado na URL
 */
@Service
public class TokenService {

    private static final String ERROR_TOKEN = "Obteu um erro ao obter o token de acesso";
    private static final String ERROR_REST = "Ocorreu um erro ao acessar a url responsável pelo token, contacte a administração.";

    @Autowired
    private AboutMeService aboutMeService;

    @Value("${url.token}")
    private String url;

    @Value("${client.id}")
    private String clientId;

    @Value("${secret.id}")
    private String secretId;

    @Value("${url.redirect}")
    private String redirectUri;

    public String getAccessToken(String code) throws ResponseException, IOException {

        try {
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("client_id", clientId);
            parts.add("client_secret", secretId);
            parts.add("redirect_uri", redirectUri);
            parts.add("code", code);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts);

            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (ResponseUtils.isNotValidResponse(response)) {
                throw new ResponseException(ERROR_TOKEN);
            }

            return aboutMeService.getAboutMe(getAccessToken(response));
        } catch (HttpClientErrorException e) {
            throw new ResponseException(ERROR_REST);
        }

    }

    private String getAccessToken(ResponseEntity<String> response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.getBody()).get("access_token").asText();
    }
}
