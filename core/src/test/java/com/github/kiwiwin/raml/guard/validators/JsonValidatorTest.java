package com.github.kiwiwin.raml.guard.validators;

import com.github.kiwiwin.raml.guard.helper.ResourceTypeHelper;
import com.github.kiwiwin.raml.guard.router.Router;
import org.junit.Test;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.DefaultResourceLoader;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.internal.impl.commons.nodes.TypeDeclarationNode;
import org.raml.yagi.framework.model.NodeModel;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonValidatorTest {

    private final JsonValidator jsonValidator = new JsonValidator(new DefaultResourceLoader());

    @Test
    public void should_validate_single_error() throws Exception {
        String payload = "{\"name\": \"hello\", \"email\": \"hell\", \"age\": \"30\"}";

        ValidationResult result = jsonValidator.validate(
                typeOf("http://test/orgs/1/persons", "post"), payload);

        assertThat(result.path("$.email.error.message"), is("Invalid value 'hell'. Expected ^.+@.+\\..+$"));
    }

    @Test
    public void should_validate_multiple_errors() throws Exception {
        String payload = "{\"name\": \"hello\", \"email\": \"hell\", \"age\": \"-30\"}";

        ValidationResult result = jsonValidator.validate(typeOf("http://test/orgs/1/persons", "post"), payload);

        assertThat(result.path("$.email.error.message"), is("Invalid value 'hell'. Expected ^.+@.+\\..+$"));
        assertThat(result.path("$.age.error.message"), is("Expected minimum value 0"));
    }

    @Test
    public void should_validate_missing_field_error() throws Exception {
        String payload = "{\"name\": \"hello\", \"email\": \"jian@gmail.com\"}";

        ValidationResult result = jsonValidator.validate(typeOf("http://test/orgs/1/persons", "post"), payload);

        assertThat(result.path("$.error.message"), is("Missing required field 'age'"));
    }

    @Test
    public void should_validate_request_in_array() throws Exception {
        String payload = "[{\"name\": \"hello\", \"email\": \"jian@gmail.com\"}]";

        ValidationResult result = jsonValidator.validate(typeOf("http://test/orgs", "post"), payload);

        assertThat(result.path("$.[0].error.message"), is("Missing required field 'age'"));
    }

    private TypeDeclarationNode typeOf(String url, String method) {
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi("input.raml");
        Resource resource = new Router(ramlModelResult.getApiV10()).route(url).get();
        Optional<TypeDeclaration> body = new ResourceTypeHelper(resource).type(method, "application/json");
        return (TypeDeclarationNode) ((NodeModel) body.get()).getNode();
    }
}