package com.liferay.stream.hub.dto;

import java.io.Serializable;

public class StreamConfiguration  implements Serializable {

    private String configuration;
    private String name;
    private String objectDefinitionId;

    public String getObjectDefinitionId() {
        return objectDefinitionId;
    }
    public void setObjectDefinitionId(String objectDefinitionId) {
        this.objectDefinitionId = objectDefinitionId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getConfiguration() {
        return configuration;
    }
    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

 }
