package br.com.db1.exercicio5.controller;

import br.com.db1.exercicio5.exception.ResponseException;
import br.com.db1.exercicio5.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * url para teste da abertura da tela de dialogo
 *
 * https://www.facebook.com/v2.10/dialog/oauth?
 * display=popup&client_id=839055946224652&redirect_uri=http://localhost:8080/about-me&scope=user_about_me
 */

@RestController
public class AboutMeController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/about-me", method = RequestMethod.GET)
    public ResponseEntity getCodeRequest(@RequestParam("code") String code) {
        try {
            return new ResponseEntity(tokenService.getAccessToken(code), HttpStatus.OK);
        } catch (ResponseException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
