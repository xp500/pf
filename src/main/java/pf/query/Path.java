package pf.query;

import java.util.ArrayList;
import java.util.List;

import pf.query.Path.EdgeOrAlias.Direction;

public class Path {

    private final List<NodeOrAlias> nodes = new ArrayList<>();
    private final List<EdgeOrAlias> edges = new ArrayList<>();

    public final static class EdgeOrAlias extends ElementOrAlias {

	public enum Direction {
	    LEFT, RIGHT;
	}

	private final static String TYPE = "EDGE";

	private final Direction direction;

	public EdgeOrAlias(final String edge, final Direction direction) {
	    this(edge, edge, direction);
	}

	public EdgeOrAlias(final String edge, final String alias, final Direction direction) {
	    super(edge, alias, TYPE);
	    this.direction = direction;
	}
    }

    public static abstract class ElementOrAlias {

	private final String name;
	private final String alias;
	private final String type;

	public ElementOrAlias(final String name, final String type) {
	    this(name, name, type);
	}

	public ElementOrAlias(final String name, final String alias, final String type) {
	    this.name = name;
	    this.alias = alias;
	    this.type = type;
	}

	public String getAlias() {
	    return alias;
	}

	public String getName() {
	    return name;
	}

	@Override
	public String toString() {
	    final String queryAlias;
	    if (alias.equals(name)) {
		queryAlias = name;
	    } else {
		queryAlias = alias;
	    }

	    return String.format("(%s:%s {title: %s})", queryAlias, type, name);
	}
    }

    public static class NodeOrAlias extends ElementOrAlias {

	private static String TYPE = "OBJETO";

	public NodeOrAlias(final String name) {
	    super(name, TYPE);
	}

	public NodeOrAlias(final String name, final String alias) {
	    super(name, alias, TYPE);
	}

    }

    public static Path singleElementPath(final NodeOrAlias node) {
	return new Path(node);
    }

    private Path(final NodeOrAlias singleNode) {
	nodes.add(singleNode);
    }

    public Path append(final EdgeOrAlias edge, final Path path) {
	edges.add(edge);
	for (final NodeOrAlias node : path.nodes) {
	    nodes.add(node);
	}

	for (final EdgeOrAlias otherEdge : path.edges) {
	    edges.add(otherEdge);
	}

	return this;
    }

    @Override
    public String toString() {
	final StringBuilder str = new StringBuilder(nodes.get(0).toString());
	for (int i = 1; i < nodes.size(); i++) {
	    final EdgeOrAlias edge = edges.get(i - 1);
	    final NodeOrAlias node = nodes.get(i);
	    final String direction;
	    if (edge.direction == Direction.RIGHT) {
		direction = "-->";
	    } else {
		direction = "<--";
	    }

	    str.append(direction).append(edge.toString()).append(direction).append(node.toString());
	}

	return str.toString();
    }
}
