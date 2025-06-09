package com.liferay.stream.hub;

import com.liferay.client.extension.util.spring.boot3.ClientExtensionUtilSpringBootComponentScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
@EnableCaching
public class LiferayStreamHubEtcSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiferayStreamHubEtcSpringBootApplication.class, args);
    }

}
