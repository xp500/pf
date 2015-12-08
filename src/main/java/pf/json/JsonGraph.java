package pf.json;

import java.util.List;

public class JsonGraph<N> {

    private final List<N> nodes;
    private final List<Link> links;

    public JsonGraph(final List<N> nodes, final List<Link> links) {
	this.nodes = nodes;
	this.links = links;
    }

    public List<N> getNodes() {
	return nodes;
    }

    public List<Link> getLinks() {
	return links;
    }

}
