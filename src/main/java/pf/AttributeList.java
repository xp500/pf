package pf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pf.query.Path;
import pf.query.Path.AttributeNode;
import pf.query.Path.Node;
import pf.query.Path.NodeOrAlias;
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
	public List<Path> getPaths(final Node node, final Set<String> returnSelect) {
		final List<Path> list = new ArrayList<>();
		getPaths(node, list, returnSelect);
		return list;
	}

	private void getPaths(final Node node, final List<Path> ans, final Set<String> returnSelect) {
		final String str1 = Test.getGenerator().getNext();
		final AttributeNode attNode = new AttributeNode(id, id);
		ans.add(Path.singleElementPath(node).append(attNode,
				Path.singleElementPath(new ValueNode(str1))));
		returnSelect.add(str1);
		returnSelect.add(attNode.getAlias());
		returnSelect.add(((EnhancedElement) node).getId());
		if (followingAttributes != null) {
			followingAttributes.getPaths(node, ans, returnSelect);
		}
	}

}
