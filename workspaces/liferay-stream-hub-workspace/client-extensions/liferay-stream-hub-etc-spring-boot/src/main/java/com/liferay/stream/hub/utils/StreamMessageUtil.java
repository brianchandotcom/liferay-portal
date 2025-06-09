package com.liferay.stream.hub.utils;


import com.liferay.stream.hub.dto.StreamMessage;
import com.liferay.stream.hub.types.MessageType;

public class StreamMessageUtil {

    public static <T> StreamMessage<T> create(MessageType type, String name, T data) {
        return new StreamMessage<T>(type, name, data);
    }

}
