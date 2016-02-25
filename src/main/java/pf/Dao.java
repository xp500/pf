package pf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import pf.json.JsonExpandedNode;
import pf.json.JsonGraph;
import pf.json.Link;

import com.google.gson.Gson;

public class Dao {

//     private static File DB_FILE = new File("/Users/alcampos/Downloads/db.db");
    private static File DB_FILE = new File("C:\\Users\\Jorge\\Desktop\\db.db");
    private static GraphDatabaseService DB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_FILE);
    private static Gson gson = new Gson();

    public static String query(final String q, final Predicate<Node> filter) {
	final long start = System.currentTimeMillis();
	try (final Transaction ignored = DB.beginTx(); final Result result = DB.execute(q)) {
	    final Graph graph = new Graph();
	    final Set<Relationship> relationships = new HashSet<>();
	    result.forEachRemaining(r -> {
		r.entrySet().forEach(
		    e -> {
			final Node node = (Node) e.getValue();
			relationships.addAll(StreamSupport.stream(node.getRelationships().spliterator(), false)
			    .collect(Collectors.toList()));
			graph.addNodo(node);
			if ("OBJETO".equals(getLabel(node))) {
			    graph.addNodoObjeto(node.getId());
			}
		    });
	    });

	    relationships.stream().filter(
		r -> graph.getNodes().containsKey(r.getStartNode().getId())
			&& graph.getNodes().containsKey(r.getEndNode().getId())).filter(
		rel -> getLabel(rel.getEndNode()).equals("ARISTA")).forEach(rel -> {
		final Node from = rel.getStartNode();
		final Node to = rel.getEndNode();

		assertNodoObjeto(from);

		final Iterable<Relationship> incomingRels = to.getRelationships(Direction.OUTGOING);
		incomingRels.forEach(incomingRel -> {
		    final Node otherNode = incomingRel.getOtherNode(to);
		    if (getLabel(otherNode).equals("OBJETO")) {
			graph.addNodoArista(to.getId(), from.getId(), otherNode.getId());
		    } else {
			assertNodoAtributo(otherNode);
		    }

		});
	    });

	    relationships.stream().filter(
		r -> graph.getNodes().containsKey(r.getStartNode().getId())
			&& graph.getNodes().containsKey(r.getEndNode().getId())).filter(
		rel -> getLabel(rel.getEndNode()).equals("VALOR")).forEach(rel -> {
		final Node nodoAtributo = rel.getStartNode();
		final Node to = rel.getEndNode();

		assertNodoAtributo(nodoAtributo);

		final Relationship objToAttr = assertOneRelationship(nodoAtributo, Direction.INCOMING);
		final Node nodoObjetoOrArista = objToAttr.getOtherNode(nodoAtributo);
		assertNodo(nodoObjetoOrArista, "OBJETO", "ARISTA");

		graph.addNodoValor(nodoObjetoOrArista.getId(), nodoAtributo.getId(), to.getId());
	    });

	    graph.clean(filter);

	    final Map<Long, Integer> nodePositions = new HashMap<>();
	    final List<JsonExpandedNode> nodes = new ArrayList<>();
	    final List<Link> links = new ArrayList<>();

	    graph.getNodes().forEach((l, n) -> {
		nodes.add(new JsonExpandedNode(n));
		nodePositions.put(l, nodePositions.size());
	    });

	    relationships.stream().filter(
		r -> nodePositions.containsKey(r.getStartNode().getId())
			&& nodePositions.containsKey(r.getEndNode().getId())).forEach(
		rel -> links.add(new Link(nodePositions.get(rel.getStartNode().getId()), nodePositions.get(rel
		    .getEndNode().getId()))));
	    
	    System.out.println("EXECUTION TIME " + (System.currentTimeMillis() - start));
	    final JsonGraph<JsonExpandedNode> g = new JsonGraph<>(nodes, links);
	    return gson.toJson(g);
	}
    }

    private static String getLabel(final Node n) {
	return n.getLabels().iterator().next().name();
    }

    private static void assertNodo(final Node n, final String... labels) {
	for (final String label : labels) {
	    if (getLabel(n).equals(label)) {
		return;
	    }
	}
	throw new IllegalStateException("GRAFO CORRUPTO!");
    }

    private static void assertNodoObjeto(final Node n) {
	assertNodo(n, "OBJETO");
    }

    private static void assertNodoAtributo(final Node n) {
	assertNodo(n, "ATRIBUTO");
    }

    private static Relationship assertOneRelationship(final Node n, final Direction dir) {
	final Iterator<Relationship> it = n.getRelationships(dir).iterator();
	if (!it.hasNext()) {
	    throw new IllegalStateException("GRAFO CORRUPTO!");
	}

	final Relationship rel = it.next();
	if (it.hasNext()) {
	    throw new IllegalStateException("GRAFO CORRUPTO!");
	}

	return rel;
    }

}
