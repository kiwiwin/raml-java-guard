package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.Node;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.EMPTY;

public class JsonUtils {

    public static String toJson(Node node) {
        return dumpJson(node);
    }

    static String dumpJson(Node child) {

        List<NodeJsonDumper> dumpers = newArrayList(
                new KeyValueNodeDumper(),
                new ObjectNodeDumper(),
                new StringNodeDumper(),
                new ArrayNodeDumper(),
                new ErrorNodeDumper()
        );

        return dumpers.stream()
                .map(dumper -> {
                            if (dumper.canDump(child)) {
                                return dumper.dump(child);
                            }
                            return EMPTY;
                        }
                ).collect(Collectors.joining());
    }

    static String dumpChildren(List<Node> children) {
        return children.stream()
                .map(JsonUtils::dumpJson)
                .collect(Collectors.joining(","));
    }
}
