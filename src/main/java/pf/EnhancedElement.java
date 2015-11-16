package pf;

import java.util.Collections;
import java.util.List;

import pf.query.Path;
import pf.query.Path.Edge;
import pf.query.Path.EdgeOrAlias.Direction;
import pf.query.Path.Node;

public class EnhancedElement implements Node, Edge {

    private final String id;
    private final AttributeListOrAll attrs;

    public EnhancedElement(final String id) {
	this(id, null);
    }

    public EnhancedElement(final String id, final AttributeListOrAll attrs) {
	this.id = id;
	this.attrs = attrs;
    }

    public String getId() {
	return id;
    }

    public List<Path> getAttributePaths() {
	if (attrs == null) {
	    return Collections.emptyList();
	}

	return attrs.getPaths(this);
    }

    @Override
    public Direction getDirection() {
	return Direction.RIGHT;
    }

}
