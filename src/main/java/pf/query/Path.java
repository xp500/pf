package pf.query;

import java.util.ArrayList;
import java.util.List;

import pf.query.Path.EdgeOrAlias.Direction;

public class Path {

    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    public interface Edge {

	Direction getDirection();
    }

    public interface Node {

    }

    public final static class EdgeOrAlias extends ElementOrAlias implements Edge, Node {

	public enum Direction {
	    LEFT, RIGHT;
	}

	private final static String TYPE = "ARISTA";

	private final Direction direction;

	public EdgeOrAlias(final String edge, final Direction direction) {
	    this(edge, edge, direction);
	}

	public EdgeOrAlias(final String edge, final String alias, final Direction direction) {
	    super(edge, alias, TYPE);
	    this.direction = direction;
	}

	@Override
	public Direction getDirection() {
	    return direction;
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
	    return String.format("(%s:%s {title: %s})", alias, type, name);
	}
    }

    public static class NodeOrAlias extends ElementOrAlias implements Node {

	private static String TYPE = "OBJETO";

	public NodeOrAlias(final String name) {
	    super(name, TYPE);
	}

	public NodeOrAlias(final String name, final String alias) {
	    super(name, alias, TYPE);
	}

    }

    public static class AttributeNode extends ElementOrAlias implements Edge {

	private static final String TYPE = "ATRIBUTO";

	public AttributeNode(final String name) {
	    super(name, "", TYPE);
	}

	@Override
	public Direction getDirection() {
	    return Direction.RIGHT;
	}

    }

    public static class AllAttributesNode extends AttributeNode {

	public AllAttributesNode() {
	    super("");
	}

	@Override
	public String toString() {
	    return "{:ATRIBUTO}";
	}
    }

    public static class ValueNode implements Node {

	private static final String TYPE = "VALOR";

	private final String name;

	public ValueNode(final String name) {
	    this.name = name;
	}

	@Override
	public String toString() {
	    return String.format("(%s:VALOR)", name);
	}

    }

    public static Path singleElementPath(final Node node) {
	return new Path(node);
    }

    private Path(final Node singleNode) {
	nodes.add(singleNode);
    }

    public Path append(final Edge edge, final Path path) {
	edges.add(edge);
	for (final Node node : path.nodes) {
	    nodes.add(node);
	}

	for (final Edge otherEdge : path.edges) {
	    edges.add(otherEdge);
	}

	return this;
    }

    public List<Node> getNodes() {
	return nodes;
    }

    public List<Edge> getEdges() {
	return edges;
    }

    @Override
    public String toString() {
	final StringBuilder str = new StringBuilder(nodes.get(0).toString());
	for (int i = 1; i < nodes.size(); i++) {
	    final Edge edge = edges.get(i - 1);
	    final Node node = nodes.get(i);
	    final String direction;
	    if (edge.getDirection() == Direction.RIGHT) {
		direction = "-->";
	    } else {
		direction = "<--";
	    }

	    str.append(direction).append(edge.toString()).append(direction).append(node.toString());
	}

	return str.toString();
    }
}
