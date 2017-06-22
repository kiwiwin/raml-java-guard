package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.ObjectNode;

import static com.github.kiwiwin.raml.guard.utils.JsonUtils.dumpChildren;

public class ObjectNodeDumper implements NodeJsonDumper {
    @Override
    public boolean canDump(Node node) {
        return node instanceof ObjectNode;
    }

    @Override
    public String dump(Node node) {
        return String.format("{%s}", dumpChildren(node.getChildren()));
    }
}