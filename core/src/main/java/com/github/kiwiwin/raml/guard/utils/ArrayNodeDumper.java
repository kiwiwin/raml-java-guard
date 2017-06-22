package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.ArrayNode;
import org.raml.yagi.framework.nodes.Node;

import static com.github.kiwiwin.raml.guard.utils.JsonUtils.dumpChildren;

public class ArrayNodeDumper implements NodeJsonDumper {
    @Override
    public boolean canDump(Node node) {
        return node instanceof ArrayNode;
    }

    @Override
    public String dump(Node node) {
        return String.format("[%s]", dumpChildren(node.getChildren()));
    }
}
