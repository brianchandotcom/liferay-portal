package com.liferay.stream.hub.types;

public enum MessageType {
    Event("Event");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MessageType fromValue(String value) {
        for (MessageType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ChatMessageType: " + value);
    }
}
