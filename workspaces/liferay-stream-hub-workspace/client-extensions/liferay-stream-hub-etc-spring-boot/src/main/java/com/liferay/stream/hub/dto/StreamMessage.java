package com.liferay.stream.hub.dto;


import com.liferay.stream.hub.types.MessageType;

import java.io.Serializable;

public class StreamMessage<T> implements Serializable {

    private MessageType type;
    private String name;
    private T data;

    public StreamMessage(MessageType type, String name, T data) {
        this.data = data;
        this.name = name;
        this.type = type;
    }
    public MessageType getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public T getData() {
        return data;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setData(T data) {
        this.data = data;
    }

}
