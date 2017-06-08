package com.github.kiwiwin.raml.guard;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import java.io.IOException;

@PreMatching
public class RamlGuardRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //TODO: logic goes here to read raml to test against request schema definition
    }
}
