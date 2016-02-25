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
	final String[] intervals = ((String) t.getProperty("interval")).split("\\],\\s*\\[");
	for (final String interval : intervals) {
	    final String prop = interval.replace("[", "").replace("]", "");
	    final String[] limits = prop.split(",\\s*");
	    final String from = limits[0];
	    if (limits.length == 1) {
		System.out.println("STAHP!");
	    } 
	    final String to = limits[1];

	    if (snapshot.compareTo(from) >= 0 && (snapshot.compareTo(to) <= 0 || to.equals("inf"))) {
		return true;
	    }
	}

	return false;
    }

}
