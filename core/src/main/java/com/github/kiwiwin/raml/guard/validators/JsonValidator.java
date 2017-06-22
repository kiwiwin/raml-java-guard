package com.github.kiwiwin.raml.guard.validators;

import org.raml.v2.api.loader.DefaultResourceLoader;
import org.raml.v2.internal.impl.commons.nodes.TypeDeclarationNode;
import org.raml.v2.internal.impl.v10.type.TypeToRuleVisitor;
import org.raml.yagi.framework.grammar.rule.Rule;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.jackson.JNodeParser;

class JsonValidator {

    private DefaultResourceLoader resourceLoader;

    JsonValidator(DefaultResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    ValidationResult validate(TypeDeclarationNode type, String payload) {
        final Rule rule = type.getResolvedType().visit(new TypeToRuleVisitor(resourceLoader));
        final Node parse = JNodeParser.parse(resourceLoader, "", payload);
        return new ValidationResult(rule.apply(parse));
    }
}
