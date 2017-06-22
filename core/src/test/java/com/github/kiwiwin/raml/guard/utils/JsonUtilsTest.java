package com.github.kiwiwin.raml.guard.utils;

import org.junit.Test;
import org.raml.v2.api.loader.DefaultResourceLoader;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.jackson.JNodeParser;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonUtilsTest {
    @Test
    public void should_convert_node_to_json() throws Exception {
        assertJson("{\"name\":\"hello\",\"email\":\"hell\",\"age\":\"30\"}");
    }

    @Test
    public void should_convert_nested_node_to_json() throws Exception {
        assertJson("{\"name\":\"hello\",\"email\":\"hell\",\"age\":\"30\",\"nested\":{\"nested_key\":\"nested_value\"}}");
    }

    @Test
    public void should_convert_array_node_to_json() throws Exception {
        assertJson("[{\"name\":\"hello\",\"email\":\"hell\",\"age\":\"30\"}]");
    }

    @Test
    public void should_convert_nested_array_node_to_json() throws Exception {
        assertJson("{\"name\":\"hello\",\"email\":\"hell\",\"age\":\"30\",\"nested\":[{\"nested_key\":\"nested_value\"}]}");
        assertJson("[{\"name\":\"hello\",\"email\":\"hell\",\"age\":\"30\",\"nested\":{\"nested_key\":\"nested_value\"}}]");
    }

    private void assertJson(String content) {
        String json = JsonUtils.toJson(parse(content));
        assertThat(json, is(content));
    }

    private Node parse(String content) {
        return JNodeParser.parse(new DefaultResourceLoader(), "", content);
    }
}