package com.github.kiwiwin.raml.guard.validators;

import org.raml.yagi.framework.nodes.ErrorNode;

import java.util.ArrayList;
import java.util.List;

class ValidationResult {

    private List<Error> errors = new ArrayList<>();

    List<Error> getErrors() {
        return errors;
    }

    boolean hasError() {
        return this.errors.size() > 0;
    }

    void addErrors(List<Error> errors) {
        this.errors.addAll(errors);
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
}
