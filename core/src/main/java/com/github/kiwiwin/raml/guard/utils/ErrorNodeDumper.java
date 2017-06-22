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

    private String buildError(ErrorNode child) {
        StringBuilder builder = new StringBuilder();

        if (child.getParent() instanceof KeyValueNode) {
            builder.append("{");
        }

        error(child, builder);

        if (child.getParent() instanceof KeyValueNode) {
            builder.append("}");
        }

        return builder.toString();
    }

    private void error(ErrorNode child, StringBuilder builder) {
        builder.append("\"error\":{");
        builder.append("\"message\":");
        builder.append("\"");
        builder.append(washMessage(child));
        builder.append("\"");
        builder.append("}");
    }

    private static String washMessage(ErrorNode child) {
        return child.getErrorMessage()
                .replace("\"", "'")
                .replace("\\", "\\\\");
    }
}
