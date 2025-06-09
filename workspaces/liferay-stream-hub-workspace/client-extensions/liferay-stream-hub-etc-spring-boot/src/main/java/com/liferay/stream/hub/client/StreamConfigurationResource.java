package com.liferay.stream.hub.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.stream.hub.dto.ObjectsPage;
import com.liferay.stream.hub.dto.StreamActionListItem;
import com.liferay.stream.hub.dto.StreamConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StreamConfigurationResource {

    private final WebClient.Builder webClientBuilder;

    public StreamConfigurationResource(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<StreamActionListItem> getConfigurationEntryByObjectDefinitionId(String objectDefinitionId) throws JsonProcessingException {

        String token = _liferayOAuth2AccessTokenManager.getAuthorization(_mainOauthServerApplicationName);

        WebClient client = webClientBuilder
                .baseUrl(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain)
                .defaultHeader("Authorization", token)
                .build();

        Mono<ObjectsPage<StreamConfiguration>> responseMono = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/o/c/streamhubconfigurations")
                        .queryParam("filter", "objectDefinitionId eq '" + objectDefinitionId + "'")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ObjectsPage<StreamConfiguration>>() {});

        ObjectsPage<StreamConfiguration> objectsPage = responseMono.block();
        if (objectsPage.getTotalCount() > 0) {
            StreamConfiguration streamConfiguration = objectsPage.getItems().get(0);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> configuration =objectMapper.readValue(streamConfiguration.getConfiguration(),Map.class) ;

            List<Map<String,Object>> actionsListMap  = (List<Map<String, Object>>) configuration.get("actionsList");

            List<StreamActionListItem> StreamActionsConfigurations  = new ArrayList<>();

            actionsListMap.forEach(action -> {
                StreamActionListItem item = new StreamActionListItem(
                        action.get("name").toString(),
                        action.get("type").toString(),
                        (List<String>) action.get("roles"),
                        (List<String>)action.get("fields"),
                        action.get("enableOfflineMessageQueue") != null ?(Boolean) action.get("enableOfflineMessageQueue"):false
                );
                StreamActionsConfigurations.add(item);
            });

            return StreamActionsConfigurations;
        }else{
            return null;
        }
    }


    @Autowired
    private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

    @Value("${com.liferay.lxc.dxp.mainDomain}")
    private String _lxcDXPMainDomain;

    @Value("${com.liferay.lxc.dxp.server.protocol}")
    private String _lxcDXPServerProtocol;

    @Value("${main.liferay.server.oauth.application}")
    private String _mainOauthServerApplicationName;


}
