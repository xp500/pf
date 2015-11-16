package pf;

import java.util.ArrayList;
import java.util.List;

import pf.query.Path;
import pf.query.Path.AllAttributesNode;
import pf.query.Path.Node;
import pf.query.Path.ValueNode;

public class AttributeListAll implements AttributeListOrAll {

    @Override
    public List<Path> getPaths(Node node) {
	final List<Path> list = new ArrayList<>(1);
	// TODO: Que nombre va?
	list.add(Path.singleElementPath(node).append(new AllAttributesNode("ASD"),
	    Path.singleElementPath(new ValueNode("ASD"))));
	return list;
    }

}
