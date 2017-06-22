package com.github.kiwiwin.raml.guard.utils;

import org.raml.yagi.framework.nodes.Node;

public interface NodeJsonDumper {

    boolean canDump(Node node);

    String dump(Node node);
}
