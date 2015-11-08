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
import pf.HelloParser.ExprContext;
import pf.HelloParser.Inbound_pathContext;
import pf.HelloParser.List_of_pathContext;
import pf.HelloParser.Outbound_pathContext;
import pf.HelloParser.PathContext;
import pf.HelloParser.RContext;
import pf.HelloParser.ResContext;
import pf.query.Path;
import pf.query.Path.EdgeOrAlias;
import pf.query.Path.ElementOrAlias;
import pf.query.Path.NodeOrAlias;
import pf.query.condition.AndCondition;
import pf.query.condition.Condition;
import pf.query.condition.Expression;
import pf.query.condition.NotCondition;
import pf.query.condition.OrCondition;

public class Test {

	public static void main(String[] args) throws IOException {

		ANTLRInputStream input = new ANTLRInputStream(new StringReader(
				"select a(attr1)-b->c from asd as QQ <-qwe-C, b-W->RR where 1asd + 1asd > 1a or 1b == 1b and 1a == 1a"));

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

	private final Map<String, String> aliasMap = new HashMap<>();
	private final Set<String> usedNames = new HashSet<>();

	public Test(final RContext root) {
		this.root = root;

		parseListOfPath(root.list_of_path());

		final ConditionContext condition = root.condition();
		if (condition != null) {
			System.out.println(parseCondition(condition));
		}

		for (final Path p : fromElement) {
			System.out.println(p);
		}
		
		System.out.println("Alias Map: " + aliasMap);
		System.out.println("Aliases: " + usedNames);
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
		analizeResContext(res1);
		analizeResContext(res2);
		return new Expression(res1.getText(), expr.COMP().getText(), res2.getText());
	}

	private void analizeResContext(final ResContext resContext) {
		final List<ArgContext> argsContext = resContext.arg();
		for (final ArgContext argContext : argsContext) {
			if (argContext.attribute_representation() != null) {
				addPathToWhereElements(argContext.attribute_representation());
			}
		}
	}

	// private String parseRes(final ResContext res) {
	// final List<ArgContext> args = res.arg();
	// if (args.size() == 1) {
	// return args.get(0).get
	// }
	// }

	private void addPathToWhereElements(final Attribute_representationContext attributeRepresentation) {
		
	}

	private void parseListOfPath(final List_of_pathContext listOfPath) {
		appendPath(listOfPath.path());
		final List_of_pathContext nextListOfPath = listOfPath.list_of_path();
		if (nextListOfPath != null) {
			parseListOfPath(nextListOfPath);
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
			if (usedNames.contains(eoa.getAlias())) {
				// TODO: BETTER ERROR
				throw new AssertionError("??");
			}
			usedNames.add(eoa.getAlias());
			aliasMap.put(eoa.getName(), eoa.getAlias());
		}
	}

	private Path parsePath(final PathContext pathContext) {
		final Element_or_aliasContext elementOrAlias = pathContext.element_or_alias();
		if (elementOrAlias != null) {
			return Path.singleElementPath(parseNodeOrAlias(elementOrAlias));
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
