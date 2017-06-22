package com.github.kiwiwin.raml.guard.helper;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.methods.MethodBase;
import org.raml.v2.api.model.v10.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResourceTypeHelper {
    private Resource resource;

    public ResourceTypeHelper(Resource resource) {
        this.resource = resource;
    }

    public Optional<TypeDeclaration> type(String method, String name) {
        List<TypeDeclaration> declarations = body(method);
        return declarations.stream()
                .filter(declaration -> declaration.name().equals(name))
                .findFirst();

    }

    private List<TypeDeclaration> body(String methodName) {
        Optional<Method> matchedMethod = this.resource.methods().stream()
                .filter(method -> method.method().equals(methodName))
                .findFirst();
        return matchedMethod.map(MethodBase::body).orElseGet(ArrayList::new);
    }
}
