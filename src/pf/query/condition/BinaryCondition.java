package pf.query.condition;

public abstract class BinaryCondition implements Condition {

    private final Condition c1;
    private final Condition c2;
    private final String operator;

    public BinaryCondition(final Condition c1, final String operator, final Condition c2) {
	this.c1 = c1;
	this.c2 = c2;
	this.operator = operator;
    }

    @Override
    public String toString() {
	return String.format("%s %s %s", c1.toString(), operator, c2.toString());
    }

}
