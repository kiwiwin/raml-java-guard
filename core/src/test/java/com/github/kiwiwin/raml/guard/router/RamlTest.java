package com.github.kiwiwin.raml.guard.router;

import org.junit.Before;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.v10.api.Api;

class RamlTest {

    Api api;

    @Before
    public void setUp() throws Exception {
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi("input.raml");
        this.api = ramlModelResult.getApiV10();
    }
}
