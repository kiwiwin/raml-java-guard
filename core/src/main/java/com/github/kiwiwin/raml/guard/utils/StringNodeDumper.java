package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.StringNode;

public class StringNodeDumper implements NodeJsonDumper {
    @Override
    public boolean canDump(Node node) {
        return node instanceof StringNode;
    }

    @Override
    public String dump(Node node) {
        return String.format("\"%s\"", ((StringNode) node).getValue());
    }
}
