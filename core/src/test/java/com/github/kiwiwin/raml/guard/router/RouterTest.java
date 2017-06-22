package com.github.kiwiwin.raml.guard.router;

import org.junit.Test;
import org.raml.v2.api.model.v10.resources.Resource;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RouterTest extends RamlTest {

    @Test
    public void should_find_the_resource_that_exactly_match_for_the_resources() throws Exception {
        Optional<Resource> resource = new Router(api).route("http://testapi.com/offices/regions/seats");
        assertTrue(resource.isPresent());
        assertThat(resource.get().resourcePath(), is("/offices/regions/seats"));
    }

    @Test
    public void should_match_url_with_same_pattern() throws Exception {
        Optional<Resource> resource = new Router(api)
                .route("http://testapi.com/orgs/thoughtworks/persons/1234");

        assertTrue(resource.isPresent());
        assertThat(resource.get().resourcePath(), is("/orgs/{orgId}/persons/{personId}"));
    }
}