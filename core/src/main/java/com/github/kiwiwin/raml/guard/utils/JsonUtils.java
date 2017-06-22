package com.github.kiwiwin.raml.guard.utils;

import org.apache.commons.lang.StringUtils;
import org.raml.yagi.framework.nodes.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class JsonUtils {

    public static String toJson(Node node) {
        return getDumper(node)
                .map(nodeJsonDumper -> nodeJsonDumper.dump(node))
                .orElse(StringUtils.EMPTY);
    }

    static String dumpChildren(List<Node> children) {
        return children.stream()
                .map(JsonUtils::toJson)
                .collect(Collectors.joining(","));
    }

    private static Optional<NodeJsonDumper> getDumper(Node node) {
        List<NodeJsonDumper> dumpers = newArrayList(
                new KeyValueNodeDumper(),
                new ObjectNodeDumper(),
                new StringNodeDumper(),
                new ArrayNodeDumper(),
                new ErrorNodeDumper()
        );
        return dumpers.stream()
                .filter(dumper -> dumper.canDump(node))
                .findFirst();
    }
}
