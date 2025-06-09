package com.liferay.stream.hub.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.stream.hub.client.ObjectDefinitionUtils;
import com.liferay.stream.hub.client.StreamConfigurationResource;
import com.liferay.stream.hub.dto.StreamActionListItem;
import com.liferay.stream.hub.socket.SessionManager;
import com.liferay.stream.hub.socket.WebSocketHandler;
import com.liferay.stream.hub.types.MessageType;
import com.liferay.stream.hub.utils.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/actions/stream")
public class StreamObjectActionController {

    private final SessionManager sessionManager;
    private ObjectDefinitionUtils objectDefinitionUtils;
    private ObjectMapper objectMapper;
    private final StreamConfigurationResource streamConfigurationResource;
    private final WebSocketHandler webSocketHandler;

    public StreamObjectActionController(ObjectDefinitionUtils objectDefinitionUtils, StreamConfigurationResource streamConfigurationResource, WebSocketHandler webSocketHandler, SessionManager sessionManager){
        this.streamConfigurationResource = streamConfigurationResource;
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = new ObjectMapper();
        this.objectDefinitionUtils = objectDefinitionUtils;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/config/set")
    public ResponseEntity<String> addStreamConfig(@AuthenticationPrincipal Jwt jwt, @RequestBody String json) throws Exception {

        Map<String, Object> map = objectMapper.readValue(json, Map.class);

        Map<String, Object> objectEntry = (Map<String, Object>) map.get("objectEntry");

        Map<String, Object>  values =   (Map<String, Object>) objectEntry.get("values");

        Map<String, Object>  configuration  = objectMapper.readValue(values.get("configuration").toString(), Map.class);

        String objectDefinitionId = configuration.get("objectId").toString();

        ArrayList<Map<String, Object>> actionsList =(ArrayList<Map<String, Object>>) configuration.get("actionsList");

        List<String> actions = actionsList.stream().map(action -> action.get("type").toString()).collect(Collectors.toList());

        objectDefinitionUtils.removeConfigObjectActions(objectDefinitionId);

        objectDefinitionUtils.configureStreamEventsActions(objectDefinitionId,actions);

        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/config/remove")
    public ResponseEntity<String> removeStreamConfig(@AuthenticationPrincipal Jwt jwt, @RequestBody String json) throws Exception {


        Map<String, Object> map = objectMapper.readValue(json, Map.class);

        Map<String, Object> objectEntry = (Map<String, Object>) map.get("objectEntry");

        Map<String, Object>  values =   (Map<String, Object>) objectEntry.get("values");

        Map<String, Object>  configuration  = objectMapper.readValue(values.get("configuration").toString(), Map.class);

        String objectDefinitionId = configuration.get("objectId").toString();

        ArrayList<Map<String, Object>> actionsList =(ArrayList<Map<String, Object>>) configuration.get("actionsList");

        List<String> actions = actionsList.stream().map(action -> action.get("type").toString()).collect(Collectors.toList());

        objectDefinitionUtils.removeConfigObjectActions(objectDefinitionId);

        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/event/add")
    public ResponseEntity<String> onAfterAddAction(@AuthenticationPrincipal Jwt jwt, @RequestBody String json) throws JsonProcessingException {

        Map<String, Object> map = objectMapper.readValue(json, Map.class);

        Map<String, Object> objectEntry = (Map<String, Object>) map.get("objectEntry");

        Map<String, Object>  values =   (Map<String, Object>) objectEntry.get("values");

        String objectActionTriggerKey = map.get("objectActionTriggerKey").toString();

        String objectDefinitionId = map.get("objectDefinitionId").toString();

        List<StreamActionListItem> configuration = streamConfigurationResource.getConfigurationEntryByObjectDefinitionId(objectDefinitionId);

        Optional<StreamActionListItem> streamActionListItem = configuration.stream().filter(item -> item.getType().equals(objectActionTriggerKey)).findFirst();

        if (streamActionListItem.isPresent()){

            Map<String,Object> eventMessage = new HashMap<>();

            StreamActionListItem streamActionListItemValue = streamActionListItem.get();

            List<String> userIds = sessionManager.getUsersIfInRoles(streamActionListItemValue.getRoles());

            streamActionListItemValue.getFields().stream().forEach(field -> {
                if (objectEntry.get(field) != null){
                    eventMessage.put(field,objectEntry.get(field));
                }else if (values.get(field) != null){
                    eventMessage.put(field,values.get(field));
                }
            });

            webSocketHandler.sendMessage(MessageType.Event, JsonUtils.toJson(eventMessage),streamActionListItemValue.getName(),userIds,streamActionListItemValue.getEnableOfflineMessageQueue());
        }

        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/event/update")
    public ResponseEntity<String> onAfterUpdateAction(@AuthenticationPrincipal Jwt jwt, @RequestBody String json) throws JsonProcessingException {

        Map<String, Object> map = objectMapper.readValue(json, Map.class);

        Map<String, Object> objectEntry = (Map<String, Object>) map.get("objectEntry");

        Map<String, Object>  values =   (Map<String, Object>) objectEntry.get("values");

        String objectActionTriggerKey = map.get("objectActionTriggerKey").toString();

        String objectDefinitionId = map.get("objectDefinitionId").toString();

        List<StreamActionListItem> configuration = streamConfigurationResource.getConfigurationEntryByObjectDefinitionId(objectDefinitionId);

        Optional<StreamActionListItem> streamActionListItem = configuration.stream().filter(item -> item.getType().equals(objectActionTriggerKey)).findFirst();

        if (streamActionListItem.isPresent()){

            Map<String,Object> eventMessage = new HashMap<>();

            StreamActionListItem streamActionListItemValue = streamActionListItem.get();

            List<String> userIds = sessionManager.getUsersIfInRoles(streamActionListItemValue.getRoles());

            streamActionListItemValue.getFields().stream().forEach(field -> {
                if (objectEntry.get(field) != null){
                    eventMessage.put(field,objectEntry.get(field));
                }else if (values.get(field) != null){
                    eventMessage.put(field,values.get(field));
                }
            });

            webSocketHandler.sendMessage(MessageType.Event, JsonUtils.toJson(eventMessage),streamActionListItemValue.getName(),userIds,streamActionListItemValue.getEnableOfflineMessageQueue());
        }

        return ResponseEntity.ok("Ok");

    }

    @PostMapping("/event/delete")
    public ResponseEntity<String> onAfterDeleteAction(@AuthenticationPrincipal Jwt jwt, @RequestBody String json) throws JsonProcessingException {

        Map<String, Object> map = objectMapper.readValue(json, Map.class);

        Map<String, Object> objectEntry = (Map<String, Object>) map.get("objectEntry");

        Map<String, Object>  values =   (Map<String, Object>) objectEntry.get("values");

        String objectActionTriggerKey = map.get("objectActionTriggerKey").toString();

        String objectDefinitionId = map.get("objectDefinitionId").toString();

        List<StreamActionListItem> configuration = streamConfigurationResource.getConfigurationEntryByObjectDefinitionId(objectDefinitionId);

        Optional<StreamActionListItem> streamActionListItem = configuration.stream().filter(item -> item.getType().equals(objectActionTriggerKey)).findFirst();

        if (streamActionListItem.isPresent()){

            Map<String,Object> eventMessage = new HashMap<>();

            StreamActionListItem streamActionListItemValue = streamActionListItem.get();

            List<String> userIds = sessionManager.getUsersIfInRoles(streamActionListItemValue.getRoles());

            streamActionListItemValue.getFields().stream().forEach(field -> {
                if (objectEntry.get(field) != null){
                    eventMessage.put(field,objectEntry.get(field));
                }else if (values.get(field) != null){
                    eventMessage.put(field,values.get(field));
                }
            });

            webSocketHandler.sendMessage(MessageType.Event, JsonUtils.toJson(eventMessage),streamActionListItemValue.getName(),userIds,streamActionListItemValue.getEnableOfflineMessageQueue());
        }

        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/event/standalone")
    public ResponseEntity<String> standAloneAction(@AuthenticationPrincipal Jwt jwt, @RequestBody String json) throws JsonProcessingException {

        Map<String, Object> map = objectMapper.readValue(json, Map.class);

        Map<String, Object> objectEntry = (Map<String, Object>) map.get("objectEntry");

        Map<String, Object>  values =   (Map<String, Object>) objectEntry.get("values");

        String objectActionTriggerKey = "standalone";

        String objectDefinitionId = map.get("objectDefinitionId").toString();

        List<StreamActionListItem> configuration = streamConfigurationResource.getConfigurationEntryByObjectDefinitionId(objectDefinitionId);

        Optional<StreamActionListItem> streamActionListItem = configuration.stream().filter(item -> item.getType().equals(objectActionTriggerKey)).findFirst();

        if (streamActionListItem.isPresent()){

            Map<String,Object> eventMessage = new HashMap<>();

            StreamActionListItem streamActionListItemValue = streamActionListItem.get();

            List<String> userIds = sessionManager.getUsersIfInRoles(streamActionListItemValue.getRoles());

            streamActionListItemValue.getFields().stream().forEach(field -> {
                if (objectEntry.get(field) != null){
                    eventMessage.put(field,objectEntry.get(field));
                }else if (values.get(field) != null){
                    eventMessage.put(field,values.get(field));
                }
            });

            webSocketHandler.sendMessage(MessageType.Event, JsonUtils.toJson(eventMessage),streamActionListItemValue.getName(),userIds,streamActionListItemValue.getEnableOfflineMessageQueue());
        }

        return ResponseEntity.ok("Ok");
    }

}
