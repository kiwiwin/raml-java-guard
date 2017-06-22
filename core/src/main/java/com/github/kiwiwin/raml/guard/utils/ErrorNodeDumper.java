package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.ErrorNode;
import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;

public class ErrorNodeDumper implements NodeJsonDumper {
    @Override
    public boolean canDump(Node node) {
        return node instanceof ErrorNode;
    }

    @Override
    public String dump(Node node) {
        return buildError((ErrorNode) node);
    }

    private String buildError(ErrorNode node) {
        StringBuilder builder = new StringBuilder();

        if (node.getParent() instanceof KeyValueNode) {
            builder.append("{");
        }

        builder.append(error(node));

        if (node.getParent() instanceof KeyValueNode) {
            builder.append("}");
        }

        return builder.toString();
    }

    private String error(ErrorNode node) {
        return String.format("\"error\":{\"message\":\"%s\"}", washMessage(node));
    }

    private static String washMessage(ErrorNode child) {
        return child.getErrorMessage()
                .replace("\"", "'")
                .replace("\\", "\\\\");
    }
}
