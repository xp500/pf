package pf.json;

import org.neo4j.graphdb.Node;

public class JsonExpandedNode {

    private final Object name;
    private final String label;
    private final String interval;
    private final long id;

    public JsonExpandedNode(final Node node) {
	name = node.getProperty("title");
	interval = (String) node.getProperty("interval");
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
