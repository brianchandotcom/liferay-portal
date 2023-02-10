package com.liferay.partner.portal.salesforce.sync.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class SaleforceTrigger {

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/salesforce/trigger")
  public ResponseEntity<String> trigger(
      @AuthenticationPrincipal Jwt jwt)
    throws JsonMappingException, JsonProcessingException {

    System.out.println("JWT ID: " + jwt.getId());
    System.out.println("JWT SUBJECT: " + jwt.getSubject());
    System.out.println("JWT CLAIMS: " + jwt.getClaims());

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
