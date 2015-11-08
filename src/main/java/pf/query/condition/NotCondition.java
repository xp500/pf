package pf.query.condition;

public class NotCondition implements Condition {

    private final Condition c;

    public NotCondition(final Condition c) {
	this.c = c;
    }

    @Override
    public String toString() {
	return String.format("NOT %s", c);
    }

}
