package pf.temporal;

import java.util.function.Predicate;

import org.neo4j.graphdb.Node;

public class Snapshot implements Predicate<Node> {

    private final String snapshot;

    public Snapshot(final String snapshot) {
	this.snapshot = snapshot;
    }

    @Override
    public boolean test(final Node t) {
	final String prop = ((String) t.getProperty("interval")).replace("[", "").replace("]", "");
	final String[] limits = prop.split(", ");
	final String from = limits[0];
	final String to = limits[1];
	
	return (snapshot.compareTo(from) >= 0 || from.equals("inf"))
		&& (snapshot.compareTo(to) <= 0 || to.equals("inf"));
    }

}
