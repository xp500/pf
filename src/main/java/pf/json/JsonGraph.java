package pf.json;

import java.util.List;

public class JsonGraph {

    private final List<JsonNode> nodes;
    private final List<Link> links;

    public JsonGraph(final List<JsonNode> nodes, final List<Link> links) {
	this.nodes = nodes;
	this.links = links;
    }

    public List<JsonNode> getNodes() {
	return nodes;
    }

    public List<Link> getLinks() {
	return links;
    }

}
