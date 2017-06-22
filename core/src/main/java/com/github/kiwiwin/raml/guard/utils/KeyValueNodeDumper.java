package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;

import static com.github.kiwiwin.raml.guard.utils.JsonUtils.dumpJson;

public class KeyValueNodeDumper implements NodeJsonDumper {

    @Override
    public boolean canDump(Node node) {
        return node instanceof KeyValueNode;
    }


    @Override
    public String dump(Node node) {
        KeyValueNode keyValueNode = (KeyValueNode) node;
        return String.format("%s:%s",
                dumpJson(keyValueNode.getKey()), dumpJson(keyValueNode.getValue()));
    }
}
