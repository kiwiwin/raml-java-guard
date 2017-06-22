package com.github.kiwiwin.raml.guard.validators;

import com.github.kiwiwin.raml.guard.utils.JsonUtils;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.raml.yagi.framework.nodes.Node;

class ValidationResult {

    private final String source;

    public ValidationResult(Node parse) {
        source = JsonUtils.toJson(parse);
    }

    public String path(String jsonPath) {
        return JsonPath.using(Configuration.defaultConfiguration()).parse(source).read(jsonPath);
    }
}
