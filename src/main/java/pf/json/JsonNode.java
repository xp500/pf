package pf.json;

import org.neo4j.graphdb.Node;

public class JsonNode {

    private final Object name;
    private final String label;
    private final long id;

    public JsonNode(final Node node) {
	name = node.getProperty("title");
	label = node.getLabels().iterator().next().name();
	id = node.getId();
    }

    public Object getName() {
	return name;
    }

    public String getLabel() {
	return label;
    }

    public long getId() {
	return id;
    }

}
