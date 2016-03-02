package pf.temporal;

import java.util.function.Predicate;

import org.neo4j.graphdb.Node;

public class Snapshot implements Predicate<Node> {

    private final String snapshotFrom;
    private final String snapshotTo;

    public Snapshot(final String snapshot) {
	final int snapshotSeps = snapshot.length() - snapshot.replace("-", "").length();
	switch (snapshotSeps) {
	    case 0:
		this.snapshotTo = snapshot + "-01-01";
		this.snapshotFrom = snapshot + "-12-31";
		break;
	    case 1:
		this.snapshotTo = snapshot + "-01";
		this.snapshotFrom = snapshot + "-31";
		break;
	    default:
		this.snapshotFrom = this.snapshotTo = snapshot;
	}
    }

    @Override
    public boolean test(final Node t) {
	final String[] intervals = ((String) t.getProperty("interval")).split("\\],\\s*\\[");
	for (final String interval : intervals) {
	    final String prop = interval.replace("[", "").replace("]", "");
	    final String[] limits = prop.split(",\\s*");
	    final String from = limits[0];
	    final String to = limits[1];

	    if (snapshotFrom.compareTo(from) >= 0 && (snapshotTo.compareTo(to) <= 0 || to.equals("inf"))) {
		return true;
	    }
	}

	return false;
    }

}
