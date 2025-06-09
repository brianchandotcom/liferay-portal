package com.liferay.stream.hub.controller;

import com.liferay.stream.hub.client.ObjectDefinitionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ui/object/definition")
public class ObjectController {

    private ObjectDefinitionUtils _objectDefinitionUtils;

    public ObjectController(ObjectDefinitionUtils objectDefinitionUtils) {
        _objectDefinitionUtils = objectDefinitionUtils;
    }

    @GetMapping("/list")
    List<Map<String, Object>> getObjectDefinitions() throws Exception {

       return _objectDefinitionUtils.getObjectDefinitionsItems();

    }


}
