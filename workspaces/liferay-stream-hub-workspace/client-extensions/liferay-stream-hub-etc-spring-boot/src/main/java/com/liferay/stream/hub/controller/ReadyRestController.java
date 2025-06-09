package com.liferay.stream.hub.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/ready")
@RestController
public class ReadyRestController extends BaseRestController {

    @GetMapping
    public  ResponseEntity<Map<String, Object>> get() {

        Map<String, Object> json = new HashMap<>();
        json.put("groups", new String[]{"liveness", "readiness"});
        json.put("status", "UP");

        return ResponseEntity.ok(json);
    }

}
