package com.github.kiwiwin.raml.guard.router;

import org.junit.Test;
import org.raml.v2.api.model.v10.resources.Resource;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatternMatcherTest {
    @Test
    public void should_match_when_pattern_match() throws Exception {
        PatternMatcher matcher = new PatternMatcher();
        Resource resource = mock(Resource.class);
        when(resource.resourcePath()).thenReturn("/resources/{resourceId}/subresources/{subId}");

        assertTrue(matcher.match(resource, "http://testapi.com/resources/1/subresource/subId"));
    }

    @Test
    public void should_not_match_when_pattern_not_match() throws Exception {
        PatternMatcher matcher = new PatternMatcher();
        Resource resource = mock(Resource.class);
        when(resource.resourcePath()).thenReturn("/resources/{resourceId}/subresources/{subId}");

        assertTrue(matcher.match(resource, "http://testapi.com/resources//subresource/subId"));
    }
}