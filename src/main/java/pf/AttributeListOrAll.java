package pf;

import java.util.List;
import java.util.Set;

import pf.query.Path;
import pf.query.Path.Node;

public interface AttributeListOrAll {
    
    List<Path> getPaths(Node node, Set<String> returnSelect);

}
