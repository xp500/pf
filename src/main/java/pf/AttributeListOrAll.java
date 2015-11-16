package pf;

import java.util.List;

import pf.query.Path;
import pf.query.Path.Node;

public interface AttributeListOrAll {
    
    List<Path> getPaths(Node node);

}
