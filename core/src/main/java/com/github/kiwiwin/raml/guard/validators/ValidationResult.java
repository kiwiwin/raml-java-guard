package com.github.kiwiwin.raml.guard.validators;

import com.github.kiwiwin.raml.guard.utils.JsonUtils;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.raml.yagi.framework.nodes.ErrorNode;
import org.raml.yagi.framework.nodes.KeyValueNodeImpl;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.StringNodeImpl;

import java.util.ArrayList;
import java.util.List;

class ValidationResult {

    private List<Error> errors = new ArrayList<>();

    private final String source;

    public ValidationResult(Node parse) {
        source = JsonUtils.toJson(parse);
    }

    List<Error> getErrors() {
        return errors;
    }

    boolean hasError() {
        return this.errors.size() > 0;
    }

    public String path(String jsonPath) {
        return JsonPath.using(Configuration.defaultConfiguration()).parse(source).read(jsonPath);
    }

    static class Error {

        private String path;
        private final String message;

        Error(ErrorNode errorNode) {
            this.path = errorNode.getPath();
            this.message = errorNode.getErrorMessage();
        }

        String getPath() {
            return path;
        }

        String getMessage() {
            return message;
        }

    }

    private KeyValueNodeImpl node(String key, String value) {
        return new KeyValueNodeImpl(
                new StringNodeImpl(key),
                new StringNodeImpl(value)
        );
    }
}
