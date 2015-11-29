package pf.temporal;

import java.util.function.Predicate;

import org.neo4j.graphdb.Node;

public class Interval implements Predicate<Node> {

    private final String from;
    private final String to;

    public Interval(final String from, final String to) {
	this.from = from;
	this.to = to;
    }

    @Override
    public boolean test(Node t) {
	return new Snapshot(from).test(t) || new Snapshot(to).test(t);
    }
}
