package com.github.kiwiwin.raml.guard.router;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.resources.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Router {
    private Api api;
    private List<Matcher> matchers = new ArrayList<>();

    public Router(Api api) {
        this.api = api;
        this.matchers.add(new ExactMatcher());
        this.matchers.add(new PatternMatcher());
    }

    public Optional<Resource> route(String url) {
        return matchers.stream()
                .map(matcher -> route(api.resources(), url, matcher))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .sorted((r1, r2) -> r2.resourcePath().length() - r1.resourcePath().length())
                .findFirst();
    }

    private List<Resource> route(List<Resource> resources, String url, Matcher matcher) {
        List<Resource> matchedResources = new ArrayList<>();
        for (Resource resource : resources) {

            if (matcher.match(resource, url)) {
                matchedResources.add(resource);
            }

            if (resource.resources().size() == 0) {
                continue;
            }

            List<Resource> matched = route(resource.resources(), url, matcher);
            if (matched != null) {
                matchedResources.addAll(matched);
            }
        }
        return matchedResources;
    }

}
