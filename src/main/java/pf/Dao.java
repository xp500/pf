package pf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import pf.json.JsonGraph;
import pf.json.JsonNode;
import pf.json.Link;

import com.google.gson.Gson;

public class Dao {

    private static File DB_FILE = new File("C:\\Users\\Jorge\\Desktop\\db.db");
    private static GraphDatabaseService DB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_FILE);
    private static Gson gson = new Gson();

    public static String query(final String q, final Predicate<Node> filter) {
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
		r -> graph.nodes.containsKey(r.getStartNode().getId())
			&& graph.nodes.containsKey(r.getEndNode().getId())).filter(
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
		r -> graph.nodes.containsKey(r.getStartNode().getId())
			&& graph.nodes.containsKey(r.getEndNode().getId())).filter(
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
	    final List<JsonNode> nodes = new ArrayList<>();
	    final List<Link> links = new ArrayList<>();

	    graph.nodes.forEach((l, n) -> {
		nodes.add(new JsonNode(n));
		nodePositions.put(l, nodePositions.size());
	    });

	    relationships.stream().filter(
		r -> nodePositions.containsKey(r.getStartNode().getId())
			&& nodePositions.containsKey(r.getEndNode().getId())).forEach(
		rel -> links.add(new Link(nodePositions.get(rel.getStartNode().getId()), nodePositions.get(rel
		    .getEndNode().getId()))));

	    final JsonGraph g = new JsonGraph(nodes, links);
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

    private static class Graph {

	private final Map<Long, Node> nodes = new HashMap<>();

	private final Map<Long, NodoObjeto> nodosObjeto = new HashMap<>();

	private final Map<Long, NodoArista> nodosArista = new HashMap<>();

	private class Nodo {

	    protected final long id;

	    Nodo(final long id) {
		this.id = id;
	    }

	    public void clean() {
		nodes.remove(id);
	    }
	}

	private class NodoObjeto extends Nodo {

	    protected final Map<Long, NodoAtributo> atributos = new HashMap<>();

	    public NodoObjeto(long id) {
		super(id);
	    }

	    @Override
	    public void clean() {
		super.clean();
		final Iterator<Long> it0 = atributos.keySet().iterator();
		while (it0.hasNext()) {
		    final Long id = it0.next();
		    atributos.get(id).clean();
		    it0.remove();
		    nodes.remove(id);
		}
	    }

	}

	private class NodoArista extends NodoObjeto {

	    private final NodoObjeto from;
	    private final NodoObjeto to;

	    public NodoArista(final long id, final NodoObjeto from, final NodoObjeto to) {
		super(id);
		this.from = from;
		this.to = to;
	    }

	    @Override
	    public void clean() {
		super.clean();
		from.clean();
	    }

	}

	private class NodoAtributo extends Nodo {

	    private final List<Long> nodosValor = new ArrayList<>();

	    public NodoAtributo(long id) {
		super(id);
	    }

	    @Override
	    public void clean() {
		super.clean();
		nodosValor.forEach(n -> nodes.remove(n));
	    }

	}

	public void addNodoObjeto(final long id) {
	    nodosObjeto.put(id, new NodoObjeto(id));
	}

	public void addNodo(final Node node) {
	    nodes.put(node.getId(), node);
	}

	public void clean(final Predicate<Node> filter) {
	    final Iterator<Entry<Long, NodoArista>> it0 = nodosArista.entrySet().iterator();
	    while (it0.hasNext()) {
		final Entry<Long, NodoArista> entry = it0.next();
		final long id = entry.getKey();
		final Node n = nodes.get(id);
		if (!filter.test(n)) {
		    entry.getValue().clean();
		    it0.remove();
		    nodes.remove(id);
		}
	    }
	    
	    final Iterator<Entry<Long, NodoObjeto>> it1 = nodosObjeto.entrySet().iterator();
	    while (it1.hasNext()) {
		final Entry<Long, NodoObjeto> entry = it1.next();
		final Long id = entry.getKey();
		final Node n = nodes.get(id);
		if (n == null) {
		    it1.remove();
		} else if (!filter.test(n)) {
		    entry.getValue().clean();
		    it1.remove();
		    nodes.remove(id);
		}
	    }

	}

	public void addNodoValor(long nodoObjetoOrArista, long nodoAtributo, long nodoValor) {
	    if (nodosObjeto.containsKey(nodoObjetoOrArista)) {
		final Map<Long, NodoAtributo> attributes = nodosObjeto.get(nodoObjetoOrArista).atributos;
		final NodoAtributo na = attributes.getOrDefault(nodoAtributo, new NodoAtributo(nodoAtributo));
		na.nodosValor.add(nodoValor);
		attributes.put(nodoAtributo, na);
	    } else if (nodosArista.containsKey(nodoObjetoOrArista)) {
		final Map<Long, NodoAtributo> attributes = nodosArista.get(nodoObjetoOrArista).atributos;
		final NodoAtributo na = attributes.getOrDefault(nodoAtributo, new NodoAtributo(nodoAtributo));
		na.nodosValor.add(nodoValor);
		attributes.put(nodoAtributo, na);
	    } else {
		throw new AssertionError("ERROR!");
	    }

	}

	public void addNodoArista(final long id, final long from, final long to) {
	    if (!nodosObjeto.containsKey(from) || !nodosObjeto.containsKey(from)) {
		throw new AssertionError("ERROR!");
	    }

	    nodosArista.put(id, new NodoArista(id, nodosObjeto.get(from), nodosObjeto.get(to)));
	}

    }

}
