package pf.query.condition;

public class OrCondition extends BinaryCondition {

    public OrCondition(final Condition c1, final Condition c2) {
	super(c1, "OR", c2);
    }

}
