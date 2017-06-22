package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.ArrayNode;
import org.raml.yagi.framework.nodes.ErrorNode;
import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.ObjectNode;
import org.raml.yagi.framework.nodes.StringNode;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtils {
    public static String toJson(Node node) {
        return dumpJson(node);
    }

    private static String dumpJson(Node child) {
        StringBuilder builder = new StringBuilder();
        if (child instanceof KeyValueNode) {
            builder.append(dumpJson(((KeyValueNode) child).getKey()))
                    .append(":")
                    .append(dumpJson(((KeyValueNode) child).getValue()));
        } else if (child instanceof ObjectNode) {
            builder.append("{")
                    .append(dumpChildren(child.getChildren()))
                    .append("}");
        } else if (child instanceof StringNode) {
            builder.append("\"")
                    .append(((StringNode) child).getValue())
                    .append("\"");
        } else if (child instanceof ArrayNode) {
            builder.append("[")
                    .append(dumpChildren(child.getChildren()))
                    .append("]");
        } else if (child instanceof ErrorNode) {
            buildError((ErrorNode) child, builder);
        } else {
            builder.append(child.toString());
        }
        return builder.toString();
    }

    private static void buildError(ErrorNode child, StringBuilder builder) {
        if (child.getParent() instanceof KeyValueNode) {
            builder.append("{");
        }
        error(child, builder);

        if (child.getParent() instanceof KeyValueNode) {
            builder.append("}");
        }
    }

    private static void error(ErrorNode child, StringBuilder builder) {
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
                .replace("\\", "\\\\")
                ;
    }

    private static String dumpChildren(List<Node> children) {
        return children.stream()
                .map(JsonUtils::dumpJson)
                .collect(Collectors.joining(","));
    }
}
