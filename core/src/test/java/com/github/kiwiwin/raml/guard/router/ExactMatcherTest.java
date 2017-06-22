package com.github.kiwiwin.raml.guard.router;

import org.junit.Test;
import org.raml.v2.api.model.v10.resources.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExactMatcherTest {
    @Test
    public void should_match_when_exactly_match() throws Exception {
        ExactMatcher exactMatcher = new ExactMatcher();
        Resource resource = mock(Resource.class);
        when(resource.resourcePath()).thenReturn("/resources/subresource");

        assertTrue(exactMatcher.match(resource, "http://testapi.com/resources/subresource"));
    }

    @Test
    public void should_not_match_when_not_exactly_match() throws Exception {
        ExactMatcher exactMatcher = new ExactMatcher();
        Resource resource = mock(Resource.class);
        when(resource.resourcePath()).thenReturn("/resources/subresources");

        assertFalse(exactMatcher.match(resource, "http://testapi.com/resources/subresource"));
    }
}