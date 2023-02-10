package com.liferay.partner.portal.salesforce.sync.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sforce.async.*;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@RequestMapping("/")
@RestController
public class HealthResource {

  @GetMapping("/")
  public String ready(@AuthenticationPrincipal Jwt jwt) throws Exception {
    System.out.println("============================\n");
    System.out.println("JWT: " + jwt);
    System.out.println("\n============================");

    return "READY";
  }
}
