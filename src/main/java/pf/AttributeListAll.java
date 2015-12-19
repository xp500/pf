package pf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pf.query.Path;
import pf.query.Path.AllAttributesNode;
import pf.query.Path.Node;
import pf.query.Path.NodeOrAlias;
import pf.query.Path.ValueNode;

public class AttributeListAll implements AttributeListOrAll {

	@Override
	public List<Path> getPaths(Node node, Set<String> returnSelect) {
		final List<Path> list = new ArrayList<>(1);
		final String str1 = Test.getGenerator().getNext();
		final String str2 = Test.getGenerator().getNext();
		list.add(Path.singleElementPath(node).append(new AllAttributesNode(str1),
				Path.singleElementPath(new ValueNode(str2))));
		returnSelect.add(str1);
		returnSelect.add(str2);
		returnSelect.add(((EnhancedElement) node).getId());
		return list;
	}

}
