package pf.json;

public class JsonResponse {

    private final JsonGraph collapsed;
    private final JsonGraph expanded;

    public JsonResponse(final JsonGraph collapsed, final JsonGraph expanded) {
	this.collapsed = collapsed;
	this.expanded = expanded;
    }

}
