package pf.json;


public class Link {

    private final long source;
    private final long target;

    public Link(final int source, final int target) {
	this.source = source;
	this.target = target;
    }

    public long getSource() {
	return source;
    }

    public long getTarget() {
	return target;
    }

}
