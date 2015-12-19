package pf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<Path> getAttributePaths(final Set<String> returnSelect) {
	if (attrs == null) {
		final Path path = Path.singleElementPath(this);
		returnSelect.add(((EnhancedElement) this).getId());
		final List<Path> paths = new ArrayList<>(1);
		paths.add(path);
	    return paths;
	}

	return attrs.getPaths(this, returnSelect);
    }

    @Override
    public Direction getDirection() {
	return Direction.RIGHT;
    }
    
    @Override
    public String toString() {
    	return id;
    }

	@Override
	public String simpleToString() {
		return id;
	}

}
