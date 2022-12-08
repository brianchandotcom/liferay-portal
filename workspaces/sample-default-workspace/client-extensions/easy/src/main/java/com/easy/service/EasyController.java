package com.easy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/easy")
@RestController
public class EasyController {

  @GetMapping(
      produces = MediaType.TEXT_PLAIN_VALUE,
      value = "/joke")
  public ResponseEntity<String> joke(@AuthenticationPrincipal Jwt jwt) {
    return new ResponseEntity<>("This is a test", HttpStatus.OK);
  }

  private static final Logger _logger = LoggerFactory.getLogger(EasyController.class);
}