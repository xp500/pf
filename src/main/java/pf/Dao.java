package pf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	final Set<Long> visitedNodes = new HashSet<>();
	final List<JsonNode> nodes = new ArrayList<>();
	final List<Relationship> relationships = new ArrayList<>();
	final List<Link> links = new ArrayList<>();
	try (final Transaction ignored = DB.beginTx(); final Result result = DB.execute(q)) {
	    result.forEachRemaining(r -> {
		r.entrySet().forEach(
		    e -> {
			final Node node = (Node) e.getValue();
			if (filter.test(node)) {
        			nodes.add(new JsonNode(node));
        			visitedNodes.add(node.getId());
        			relationships.addAll(StreamSupport.stream(node.getRelationships().spliterator(), false)
        			    .collect(Collectors.toList()));
			}
		    });
	    });

	    relationships.stream().filter(
		r -> visitedNodes.contains(r.getStartNode().getId()) && 
			visitedNodes.contains(r.getEndNode().getId()))
		.forEach(r -> links.add(new Link(r)));
	    
	    final JsonGraph g = new JsonGraph(nodes, links);
	    return gson.toJson(g);
	}
    }

}
