package pf.json;

import org.neo4j.graphdb.Relationship;

public class Link {

    private final long source;
    private final long target;

    public Link(final Relationship relationship) {
	source = relationship.getStartNode().getId();
	target = relationship.getEndNode().getId();
    }

    public long getSource() {
	return source;
    }

    public long getTarget() {
	return target;
    }

}
