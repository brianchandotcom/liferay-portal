package com.liferay.stream.hub.client;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.Status;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectDefinitionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ObjectDefinitionUtils {


    public List<Map<String, Object>> getObjectDefinitionsItems() throws Exception {

        String authorization = _liferayOAuth2AccessTokenManager.getAuthorization(_mainOauthServerApplicationName);

        ObjectDefinitionResource objectDefinitionResource = ObjectDefinitionResource.builder().header(
                "Authorization", authorization
        ).endpoint(_lxcDXPMainDomain,_lxcDXPServerProtocol).build();

        Page<ObjectDefinition> objectDefinitionPage =
                objectDefinitionResource.getObjectDefinitionsPage
                        ("",null,"",  Pagination.of(0,0),"");
        List<Map<String, Object>> result = objectDefinitionPage.getItems().stream().filter(field-> !field.getSystem() && !Objects.equals(field.getScope(), "site")).map(item -> {

            Map<String, Object> objectDefinition = new HashMap<>();

            List<Map<String, Object>> objectFields = Arrays.stream(item.getObjectFields()).filter(field -> !field.getSystem()).map(field -> {
                Map<String, Object> objectField = new HashMap<>();
                objectField.put("businessType", field.getBusinessType());
                objectField.put("externalReferenceCode", field.getExternalReferenceCode());
                objectField.put("id", field.getId());
                objectField.put("name", field.getName());
                return objectField;

            }).collect(Collectors.toList());

            objectDefinition.put("id",item.getId());
            objectDefinition.put("name",item.getName());
            objectDefinition.put("externalReferenceCode",item.getExternalReferenceCode());
            objectDefinition.put("objectFields",objectFields);

            return objectDefinition;
        }).collect(Collectors.toList());

        return result;

    }

    public void removeConfigObjectActions(String ObjectDefinitionID) throws Exception {

        String authorization = _liferayOAuth2AccessTokenManager.getAuthorization(_mainOauthServerApplicationName);

        ObjectActionResource objectActionResource = ObjectActionResource.builder().header(
                "Authorization", authorization
        ).endpoint(_lxcDXPMainDomain,_lxcDXPServerProtocol).build();

        Page<ObjectAction> objectActionPage =
                objectActionResource
                        .getObjectDefinitionObjectActionsPage(Long.valueOf(ObjectDefinitionID)
                                ,"",Pagination.of(0,0),"");

        List<Long> toBeDeletedActions = objectActionPage.getItems().stream()
                .filter(action -> action.getExternalReferenceCode().startsWith("STREAM_")).map(objectAction -> {
            return objectAction.getId();
        }).collect(Collectors.toList());

        toBeDeletedActions.forEach(actionId -> {
            try {
                objectActionResource.deleteObjectAction(actionId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }}
        );

    }

    public void configureStreamEventsActions(String objectDefinitionId, List<String> actions) throws Exception {
        String authorization = _liferayOAuth2AccessTokenManager.getAuthorization(_mainOauthServerApplicationName);

        ObjectActionResource objectActionResource = ObjectActionResource.builder()
                .header("Authorization", authorization)
                .endpoint(_lxcDXPMainDomain, _lxcDXPServerProtocol)
                .build();

        Status status = new Status();
        status.setLabel("Never Ran");
        status.setCode(0);

        // actionKey -> {executorKey, label, optional errorMessage}
        Map<String, String[]> actionConfigs = Map.of(
                "onAfterAdd", new String[] {
                        "function#liferay-stream-hub-event-add-etc-spring-boot", "Stream Events - On Add", ""
                },
                "onAfterUpdate", new String[] {
                        "function#liferay-stream-hub-event-update-etc-spring-boot", "Stream Events - On Update", ""
                },
                "onAfterDelete", new String[] {
                        "function#liferay-stream-hub-event-delete-etc-spring-boot", "Stream Events - On Delete", ""
                },
                "standalone", new String[] {
                        "function#liferay-stream-hub-event-standalone-etc-spring-boot", "Stream Events - Standalone",
                        "Error while executing Event Streaming for Standalone"
                }
        );

        for (String action : actions) {
            if (!actionConfigs.containsKey(action)) continue;

            String[] config = actionConfigs.get(action);

            ObjectAction objectAction = new ObjectAction();
            objectAction.setObjectActionExecutorKey(config[0]);
            objectAction.setExternalReferenceCode("STREAM_" + objectDefinitionId + "_" + action);
            objectAction.setLabel(Map.of("en_US", config[1]));
            objectAction.setObjectActionTriggerKey(action);
            objectAction.setName("stream" + objectDefinitionId + action);
            objectAction.setActive(true);
            objectAction.setParameters(Collections.emptyMap());
            objectAction.setStatus(status);

            if (!config[2].isEmpty()) {
                objectAction.setErrorMessage(Map.of("en_US", config[2]));
            }

            objectActionResource.postObjectDefinitionObjectAction(Long.valueOf(objectDefinitionId), objectAction);
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

