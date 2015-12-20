package pf.query;

import java.util.ArrayList;
import java.util.List;

import pf.query.Path.EdgeOrAlias.Direction;

public class Path {

	private final List<Node> nodes = new ArrayList<>();
	private final List<Edge> edges = new ArrayList<>();

	public interface Edge {

		Direction getDirection();

		String simpleToString();
	}

	public interface Node {

		String simpleToString();

	}

	public final static class EdgeOrAlias extends ElementOrAlias implements Edge, Node {

		public enum Direction {
			LEFT, RIGHT;
		}

		private final static String TYPE = "ARISTA";

		private final Direction direction;
		
		private String multiple;

		public EdgeOrAlias(final String edge, final Direction direction) {
			this(edge, edge, direction);
		}

		public EdgeOrAlias(final String edge, final String alias, final Direction direction) {
			super(edge, alias, TYPE);
			this.direction = direction;
		}
		
		public void setMultiple(String multiple) {
		    this.multiple = String.format(multiple, alias);
		}

		@Override
		public Direction getDirection() {
			return direction;
		}
	}

	public static abstract class ElementOrAlias {

		protected final String name;
		protected final String alias;
		protected final String type;

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

		public String simpleToString() {
			return alias;
		}

		@Override
		public String toString() {
			return String.format("(%s:%s {title: '%s'})", alias, type, name);
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

		public AttributeNode(final String name, final String alias) {
			super(name, alias, TYPE);
		}

		@Override
		public Direction getDirection() {
			return Direction.RIGHT;
		}

	}

	public static class ReferenceNode implements Node {

		private final String ref;

		public ReferenceNode(final String ref) {
			this.ref = ref;
		}

		@Override
		public String toString() {
			return String.format("(%s)", ref);
		}

		@Override
		public String simpleToString() {
			return ref;
		}
	}

	public static class AllAttributesNode extends AttributeNode {

		public AllAttributesNode(final String name) {
			super(name, name);
		}

		@Override
		public String toString() {
			return String.format("(%s:ATRIBUTO)", name);
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

		@Override
		public String simpleToString() {
			return name;
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

	public String simpleToString() {
		final StringBuilder str = new StringBuilder(nodes.get(0).simpleToString());
		for (int i = 1; i < nodes.size(); i++) {
			final Edge edge = edges.get(i - 1);
			final Node node = nodes.get(i);
			final String direction;
			if (edge.getDirection() == Direction.RIGHT) {
				direction = "-->";
			} else {
				direction = "<--";
			}

			str.append(direction).append(edge.simpleToString()).append(direction).append(node.simpleToString());
		}

		return str.toString();
	}

	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder(nodes.get(0).toString());
		for (int i = 1; i < nodes.size(); i++) {
			final Edge edge = edges.get(i - 1);
			final Node node = nodes.get(i);
			final String direction;
			final String maybeMultipleDirection;
			if (edge.getDirection() == Direction.RIGHT) {
			    direction = "-->";
			    if (edge instanceof EdgeOrAlias) {
				final EdgeOrAlias eoa = (EdgeOrAlias) edge;
				if (eoa.multiple == null) {
				    maybeMultipleDirection = direction;
				} else {
				    maybeMultipleDirection = "-" + eoa.multiple + "->";
				}
			    } else {
				maybeMultipleDirection = direction;
			    }
			} else {
			    direction = "<--";
			    if (edge instanceof EdgeOrAlias) {
				final EdgeOrAlias eoa = (EdgeOrAlias) edge;
				if (eoa.multiple == null) {
				    maybeMultipleDirection = direction;
				} else {
				    maybeMultipleDirection = "<-" + eoa.multiple + "-";
				}
			    } else {
				maybeMultipleDirection = direction;
			    }
			}

			str.append(direction).append(edge.toString()).append(maybeMultipleDirection).append(node.toString());
		}

		return str.toString();
	}
	
}
