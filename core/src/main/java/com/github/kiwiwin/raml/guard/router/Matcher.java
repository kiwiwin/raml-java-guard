package com.github.kiwiwin.raml.guard.router;

import org.raml.v2.api.model.v10.resources.Resource;

public interface Matcher {
    boolean match(Resource resource, String url);
}
