package com.github.kiwiwin.raml.guard.router;

import org.raml.v2.api.model.v10.resources.Resource;

import java.net.URI;

class ExactMatcher implements Matcher {
    public boolean match(Resource resource, String url) {
        return resource.resourcePath().equals(URI.create(url).getPath());
    }
}
