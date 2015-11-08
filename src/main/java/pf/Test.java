package pf;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import pf.HelloParser.ArgContext;
import pf.HelloParser.Attribute_representationContext;
import pf.HelloParser.ConditionContext;
import pf.HelloParser.Element_or_aliasContext;
import pf.HelloParser.Enhanced_list_of_paths_or_allContext;
import pf.HelloParser.ExprContext;
import pf.HelloParser.Inbound_pathContext;
import pf.HelloParser.List_of_pathContext;
import pf.HelloParser.Outbound_pathContext;
import pf.HelloParser.PathContext;
import pf.HelloParser.RContext;
import pf.HelloParser.ResContext;
import pf.common.IncremetalStringGenerator;
import pf.query.Path;
import pf.query.Path.AllAttributesNode;
import pf.query.Path.AttributeNode;
import pf.query.Path.Edge;
import pf.query.Path.EdgeOrAlias;
import pf.query.Path.ElementOrAlias;
import pf.query.Path.Node;
import pf.query.Path.NodeOrAlias;
import pf.query.Path.ValueNode;
import pf.query.condition.AndCondition;
import pf.query.condition.Condition;
import pf.query.condition.Expression;
import pf.query.condition.NotCondition;
import pf.query.condition.OrCondition;

public class Test {

	public static void main(String[] args) throws IOException {

		ANTLRInputStream input = new ANTLRInputStream(new StringReader(
				"select * from asd as QQ <-qwe-C, b-W->RR where 1asd + 1asd > 1a or 1b == 1b and 1a == 1a"));

		HelloLexer lexer = new HelloLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		HelloParser parser = new HelloParser(tokens);
		// parser.addParseListener(new Visitor());
		RContext tree = parser.r();

		new Test(tree);

	}

	private final RContext root;
	private final List<Path> fromElement = new ArrayList<>();
	private final List<Path> whereElemnt = new ArrayList<>();
	private IncremetalStringGenerator generator;

	private final Map<String, String> nameToAlias = new HashMap<>();
	private final Map<String, String> aliasToName = new HashMap<>();

	public Test(final RContext root) {
		this.root = root;
		parseListOfPath(root.list_of_path());
		generator = new IncremetalStringGenerator(aliasToName.keySet());

		parseSelect(root.enhanced_list_of_paths_or_all());

		System.out.println(returnSelect);
		System.out.println("SELECT elements path");
		for (final Path p : extraFrom) {
			System.out.println(p);
		}

		final ConditionContext condition = root.condition();
		if (condition != null) {
			System.out.println(parseCondition(condition));
		}
		System.out.println("FROM elements path");
		for (final Path p : fromElement) {
			System.out.println(p);
		}
		System.out.println("WHERE elements path");
		for (final Path p : whereElemnt) {
			System.out.println(p);
		}

		System.out.println("Alias Map: " + nameToAlias);
		System.out.println("Aliases: " + aliasToName);
	}

	private Condition parseCondition(final ConditionContext condition) {
		final ExprContext expr = condition.expr();
		if (expr != null) {
			return parseExpression(expr);
		}

		final List<ConditionContext> conditions = condition.condition();
		if (conditions.size() == 1) {
			return new NotCondition(parseCondition(conditions.get(0)));
		}
		if (condition.BINARY_BOOLEAN_OPERATOR().getText().equals("and")) {
			return new AndCondition(parseCondition(conditions.get(0)), parseCondition(conditions.get(1)));
		}

		if (condition.BINARY_BOOLEAN_OPERATOR().getText().equals("or")) {
			return new OrCondition(parseCondition(conditions.get(0)), parseCondition(conditions.get(1)));
		}

		throw new AssertionError("??");
	}

	private Condition parseExpression(final ExprContext expr) {
		final ResContext res1 = expr.res(0);
		final ResContext res2 = expr.res(1);
		return new Expression(analizeResContext(res1), expr.COMP().getText(), analizeResContext(res2));
	}

	private String analizeResContext(final ResContext resContext) {

		final List<ArgContext> argsContext = resContext.arg();
		if (argsContext.size() == 1) {
			final ArgContext argContext = argsContext.get(0);
			if (argContext.attribute_representation() != null) {
				return addPathToWhereElements(argContext.attribute_representation());
			}
			return argContext.getText();
		}
		final ArgContext argContext1 = argsContext.get(0);
		final ArgContext argContext2 = argsContext.get(1);
		final String oper = resContext.OPER().getText();

		String argContext1Text = argContext1.getText();
		String argContext2Text = argContext2.getText();
		if (argContext1.attribute_representation() != null) {
			argContext1Text = addPathToWhereElements(argContext1.attribute_representation());
		}
		if (argContext2.attribute_representation() != null) {
			argContext2Text = addPathToWhereElements(argContext2.attribute_representation());
		}
		return String.format("%s %s %s", argContext1Text, oper, argContext2Text);
	}

	// private String parseRes(final ResContext res) {
	// final List<ArgContext> args = res.arg();
	// if (args.size() == 1) {
	// return args.get(0).get
	// }
	// }

	private String addPathToWhereElements(final Attribute_representationContext attributeRepresentation) {
		final String nameOrAlias = attributeRepresentation.ID(0).getText();
		final String attribute = attributeRepresentation.ID(1).getText();
		final String realName = aliasToName.get(nameOrAlias);
		if (realName == null) {
			// TODO: Throw propper error
			throw new AssertionError("No esta el alias en la lista");
		}
		final NodeOrAlias nodeOrAlias = new NodeOrAlias(realName, nameOrAlias);
		final AttributeNode attributeNode = new AttributeNode(attribute);
		final String autoGenerated = generator.getNext();
		final Path valuePath = Path.singleElementPath(new ValueNode(autoGenerated));
		final Path wherePath = Path.singleElementPath(nodeOrAlias).append(attributeNode, valuePath);
		whereElemnt.add(wherePath);
		return String.format("%s.%s", autoGenerated, "title");
	}

	private void parseListOfPath(final List_of_pathContext listOfPath) {
		appendPath(listOfPath.path());
		final List_of_pathContext nextListOfPath = listOfPath.list_of_path();
		if (nextListOfPath != null) {
			parseListOfPath(nextListOfPath);
		}
	}

	private List<Path> extraFrom = new ArrayList<>();
	private Set<String> returnSelect = new HashSet<>();

	private void parseSelect(final Enhanced_list_of_paths_or_allContext pathOrAll) {
		if (pathOrAll.getText().equals("*")) {
			insertAllAttributesToFrom();
		}
	}

	private void insertAllAttributesToFrom() {
		for (final Path path : fromElement) {
			for (final Node node : path.getNodes()) {
				final Path newPath = Path.singleElementPath(node);
				final String valueNodeName = generator.getNext();
				final String attrNodeName = generator.getNext();
				newPath.append(new AllAttributesNode(attrNodeName),
						Path.singleElementPath(new ValueNode(valueNodeName)));
				extraFrom.add(newPath);
				returnSelect.add(valueNodeName);
				returnSelect.add(attrNodeName);
				returnSelect.add(((ElementOrAlias) node).getAlias());
			}

			for (final Edge edge : path.getEdges()) {
				final Path newPath = Path.singleElementPath((EdgeOrAlias) edge);
				final String valueNodeName = generator.getNext();
				final String attrNodeName = generator.getNext();
				newPath.append(new AllAttributesNode(attrNodeName),
						Path.singleElementPath(new ValueNode(valueNodeName)));
				extraFrom.add(newPath);
				returnSelect.add(valueNodeName);
				returnSelect.add(attrNodeName);
				returnSelect.add(((ElementOrAlias) edge).getAlias());
			}
		}
	}

	private void appendPath(final PathContext pathContext) {
		fromElement.add(parsePath(pathContext));
	}

	private Path parseOutboundPath(final Outbound_pathContext outboundPath) {
		final NodeOrAlias node = parseNodeOrAlias(outboundPath.element_or_alias(0));
		final EdgeOrAlias edge = parseEdgeOrAlias(outboundPath.element_or_alias(1), EdgeOrAlias.Direction.RIGHT);
		final Path path = parsePath(outboundPath.path());
		checkAliases(node, edge);
		return Path.singleElementPath(node).append(edge, path);
	}

	private Path parseInboundPath(final Inbound_pathContext inboundPath) {
		final NodeOrAlias node = parseNodeOrAlias(inboundPath.element_or_alias(0));
		final EdgeOrAlias edge = parseEdgeOrAlias(inboundPath.element_or_alias(1), EdgeOrAlias.Direction.LEFT);
		final Path path = parsePath(inboundPath.path());
		checkAliases(node, edge);
		return Path.singleElementPath(node).append(edge, path);
	}

	private void checkAliases(final ElementOrAlias... elementOrAlias) {
		for (final ElementOrAlias eoa : elementOrAlias) {
			if (aliasToName.containsKey(eoa.getAlias())) {
				// TODO: BETTER ERROR
				throw new AssertionError("The name is already used!");
			}
			aliasToName.put(eoa.getAlias(), eoa.getName());
			nameToAlias.put(eoa.getName(), eoa.getAlias());
		}
	}

	private Path parsePath(final PathContext pathContext) {
		final Element_or_aliasContext elementOrAlias = pathContext.element_or_alias();
		if (elementOrAlias != null) {
			final NodeOrAlias eoa = parseNodeOrAlias(elementOrAlias);
			checkAliases(eoa);
			return Path.singleElementPath(eoa);
		}

		final Outbound_pathContext outboundPath = pathContext.outbound_path();
		if (outboundPath != null) {
			return parseOutboundPath(outboundPath);
		}

		return parseInboundPath(pathContext.inbound_path());
	}

	private static EdgeOrAlias parseEdgeOrAlias(final Element_or_aliasContext elementOrAlias,
			final EdgeOrAlias.Direction direction) {
		final List<TerminalNode> ids = elementOrAlias.ID();
		final String name = ids.get(0).getText();
		if (ids.size() == 1) {
			return new EdgeOrAlias(name, direction);
		}

		return new EdgeOrAlias(name, ids.get(1).getText(), direction);
	}

	private static NodeOrAlias parseNodeOrAlias(final Element_or_aliasContext elementOrAlias) {
		final List<TerminalNode> ids = elementOrAlias.ID();
		final String name = ids.get(0).getText();
		if (ids.size() == 1) {
			return new NodeOrAlias(name);
		}

		return new NodeOrAlias(name, ids.get(1).getText());
	}

}
