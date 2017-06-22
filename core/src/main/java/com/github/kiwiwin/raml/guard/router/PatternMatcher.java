package com.github.kiwiwin.raml.guard.router;

import org.raml.v2.api.model.v10.resources.Resource;

import java.net.URI;

class PatternMatcher implements Matcher {

    public static final String URT_TEMPLATE = "\\{(.*?)\\}";

    @Override
    public boolean match(Resource resource, String url) {
        return URI.create(url).getPath().matches(regex(resource));
    }

    private String regex(Resource resource) {
        return String.format("^%s$", resource.resourcePath().replaceAll(URT_TEMPLATE, ".+"));
    }
}
