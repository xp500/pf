package pf.query.condition;

public class Expression implements Condition {

    private final String res1, comp, res2;

    public Expression(final String res1, final String comp, final String res2) {
	this.res1 = res1;
	this.comp = comp;
	this.res2 = res2;
    }

    @Override
    public String toString() {
	return String.format("%s %s %s", res1, comp, res2);
    }

}
