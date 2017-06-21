package com.github.kiwiwin.raml.guard.validators;

import org.raml.v2.api.loader.DefaultResourceLoader;
import org.raml.v2.internal.impl.commons.nodes.TypeDeclarationNode;
import org.raml.v2.internal.impl.v10.type.TypeToRuleVisitor;
import org.raml.yagi.framework.grammar.rule.Rule;
import org.raml.yagi.framework.nodes.ErrorNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.jackson.JNodeParser;

import java.util.List;
import java.util.stream.Collectors;

class JsonValidator {

    private DefaultResourceLoader resourceLoader;

    JsonValidator(DefaultResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    ValidationResult validate(TypeDeclarationNode type, String payload) {
        final Rule rule = type.getResolvedType().visit(new TypeToRuleVisitor(resourceLoader));
        final Node parse = JNodeParser.parse(resourceLoader, "", payload);
        return validationResult(rule.apply(parse));
    }

    private ValidationResult validationResult(Node apply) {
        ValidationResult result = new ValidationResult();
        result.addErrors(getErrors(apply));
        return result;
    }

    private List<ValidationResult.Error> getErrors(Node apply) {
        return apply.findDescendantsWith(ErrorNode.class).stream()
                .map(ValidationResult.Error::new)
                .collect(Collectors.toList());
    }
}
