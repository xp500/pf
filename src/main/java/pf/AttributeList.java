package pf;

import java.util.ArrayList;
import java.util.List;

import pf.query.Path;
import pf.query.Path.AttributeNode;
import pf.query.Path.Node;
import pf.query.Path.ValueNode;

public class AttributeList implements AttributeListOrAll {

    private final String id;
    private final AttributeList followingAttributes;

    public AttributeList(final String id) {
	this(id, null);
    }

    public AttributeList(final String id, final AttributeList followingAttributes) {
	this.id = id;
	this.followingAttributes = followingAttributes;
    }

    @Override
    public List<Path> getPaths(final Node node) {
	final List<Path> list = new ArrayList<>();
	getPaths(node, list);
	return list;
    }

    private void getPaths(final Node node, final List<Path> ans) {
	// TODO: Add name parameter?
	ans.add(Path.singleElementPath(node)
	    .append(new AttributeNode(id, id), Path.singleElementPath(new ValueNode("ASD"))));
	if (followingAttributes != null) {
	    followingAttributes.getPaths(node, ans);
	}
    }

}
