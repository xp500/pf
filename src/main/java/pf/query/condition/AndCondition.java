package pf.query.condition;

public class AndCondition extends BinaryCondition {

    public AndCondition(final Condition c1, final Condition c2) {
	super(c1, "AND", c2);
    }

}
