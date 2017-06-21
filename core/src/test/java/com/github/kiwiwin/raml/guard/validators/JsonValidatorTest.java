package com.github.kiwiwin.raml.guard.validators;

import org.junit.Test;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.DefaultResourceLoader;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.internal.impl.commons.nodes.TypeDeclarationNode;
import org.raml.yagi.framework.model.NodeModel;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonValidatorTest {

    private final JsonValidator jsonValidator = new JsonValidator(new DefaultResourceLoader());

    @Test
    public void should_validate_single_error() throws Exception {
        String payload = "{\"name\": \"hello\", \"email\": \"hell\", \"age\": \"30\"}";

        ValidationResult result = jsonValidator.validate(getTypeDeclarationNode(), payload);

        assertThat(result.hasError(), is(true));
        assertThat(result.getErrors().size(), is(1));
        assertThat(result.getErrors().get(0).getPath(), is("/email"));
        assertThat(result.getErrors().get(0).getMessage(), is("Invalid value 'hell'. Expected ^.+@.+\\..+$"));
    }

    @Test
    public void should_validate_multiple_errors() throws Exception {
        String payload = "{\"name\": \"hello\", \"email\": \"hell\", \"age\": \"-30\"}";

        ValidationResult result = jsonValidator.validate(getTypeDeclarationNode(), payload);

        assertThat(result.hasError(), is(true));
        assertThat(result.getErrors().size(), is(2));
        assertThat(result.getErrors().get(0).getPath(), is("/email"));
        assertThat(result.getErrors().get(0).getMessage(), is("Invalid value 'hell'. Expected ^.+@.+\\..+$"));
        assertThat(result.getErrors().get(1).getPath(), is("/age"));
        assertThat(result.getErrors().get(1).getMessage(), is("Expected minimum value 0"));
    }

    @Test
    public void should_validate_missing_field_error() throws Exception {
        String payload = "{\"name\": \"hello\", \"email\": \"jian@gmail.com\"}";

        ValidationResult result = jsonValidator.validate(getTypeDeclarationNode(), payload);

        assertThat(result.hasError(), is(true));
        assertThat(result.getErrors().size(), is(1));
        assertThat(result.getErrors().get(0).getPath(), is("/"));
        assertThat(result.getErrors().get(0).getMessage(), is("Missing required field \"age\""));
    }

    private TypeDeclarationNode getTypeDeclarationNode() {
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi("input.raml");
        Api apiV10 = ramlModelResult.getApiV10();
        List<TypeDeclaration> body = apiV10.resources().get(0)
                .resources().get(0)
                .resources().get(0)
                .methods().get(0).body();
        return (TypeDeclarationNode) ((NodeModel) body.get(0)).getNode();
    }
}