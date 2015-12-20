package pf.json;

public class JsonResponse {

    private final JsonGraph<JsonExpandedNode> collapsed;
    private final JsonGraph<JsonExpandedNode> expanded;

    public JsonResponse(final JsonGraph<JsonExpandedNode> collapsed, final JsonGraph<JsonExpandedNode> expanded) {
	this.collapsed = collapsed;
	this.expanded = expanded;
    }

}
