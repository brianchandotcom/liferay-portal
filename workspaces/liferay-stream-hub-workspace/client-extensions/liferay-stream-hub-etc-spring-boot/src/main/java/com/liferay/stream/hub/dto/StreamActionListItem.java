package com.liferay.stream.hub.dto;

import java.io.Serializable;
import java.util.List;

public class StreamActionListItem implements Serializable {
    private Boolean enableOfflineMessageQueue;
    private List<String> fields;
    private List<String> roles;
    private String name;
    private String type;

    public StreamActionListItem(String name,String type,List<String> roles,List<String> fields, Boolean enableOfflineMessageQueue) {
        this.enableOfflineMessageQueue = enableOfflineMessageQueue;
        this.fields = fields;
        this.name = name;
        this.roles = roles;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    public List<String> getFields() {
        return fields;
    }
    public void setFields(List<String> fields) {
        this.fields = fields;
    }
    public Boolean getEnableOfflineMessageQueue() {
        return enableOfflineMessageQueue;
    }
    public void setEnableOfflineMessageQueue(Boolean enableOfflineMessageQueue) {
        this.enableOfflineMessageQueue = enableOfflineMessageQueue;
    }
}
