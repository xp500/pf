package pf;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import pf.HelloParser.ArgContext;
import pf.HelloParser.Attribute_listContext;
import pf.HelloParser.Attribute_list_or_allContext;
import pf.HelloParser.Attribute_representationContext;
import pf.HelloParser.ConditionContext;
import pf.HelloParser.Element_or_aliasContext;
import pf.HelloParser.Enhanced_elementContext;
import pf.HelloParser.Enhanced_list_of_pathsContext;
import pf.HelloParser.Enhanced_list_of_paths_or_allContext;
import pf.HelloParser.Enhanced_pathContext;
import pf.HelloParser.ExprContext;
import pf.HelloParser.Inbound_enhanced_pathContext;
import pf.HelloParser.Inbound_pathContext;
import pf.HelloParser.IntervalContext;
import pf.HelloParser.List_of_pathContext;
import pf.HelloParser.MomentContext;
import pf.HelloParser.Outbound_enhanced_pathContext;
import pf.HelloParser.Outbound_pathContext;
import pf.HelloParser.PathContext;
import pf.HelloParser.RContext;
import pf.HelloParser.ResContext;
import pf.HelloParser.Temp_modifierContext;
import pf.common.IncremetalStringGenerator;
import pf.query.Path;
import pf.query.Path.AllAttributesNode;
import pf.query.Path.AttributeNode;
import pf.query.Path.Edge;
import pf.query.Path.EdgeOrAlias;
import pf.query.Path.ElementOrAlias;
import pf.query.Path.Node;
import pf.query.Path.NodeOrAlias;
import pf.query.Path.ReferenceNode;
import pf.query.Path.ValueNode;
import pf.query.condition.AndCondition;
import pf.query.condition.Condition;
import pf.query.condition.Expression;
import pf.query.condition.NotCondition;
import pf.query.condition.OrCondition;
import pf.temporal.Interval;
import pf.temporal.Snapshot;

public class Test {

	public static void main(String[] args) throws IOException {

		Test test = new Test(
				"select * from Person-Friend->Person as P2 where Person.Name = 'E K'");
		System.out.println(test.getResultsAsString());

	}

	private final RContext root;
	private final List<Path> fromElement = new ArrayList<>();
	private final List<Path> whereElemnt = new ArrayList<>();
	private static IncremetalStringGenerator generator;

	private final Map<String, String> nameToAlias = new HashMap<>();
	private final Map<String, String> aliasToName = new HashMap<>();

	public Test(final String query) throws IOException {
		// query =
		ANTLRInputStream input = new ANTLRInputStream(new StringReader(query));

		HelloLexer lexer = new HelloLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		HelloParser parser = new HelloParser(tokens);
		RContext tree = parser.r();
		this.root = tree;
	}
	
	public static IncremetalStringGenerator getGenerator() {
		return generator;
	}

	public String getResultsAsString() {
	    	final long start = System.currentTimeMillis();
		parseListOfPath(root.list_of_path());
		generator = new IncremetalStringGenerator(aliasToName.keySet());

		parseSelect(root.enhanced_list_of_paths_or_all());

		final ConditionContext conditionCtx = root.condition();
		final Condition condition;
		if (conditionCtx == null) {
			condition = null;
		} else {
			condition = parseCondition(conditionCtx);
		}

		final Temp_modifierContext temporal = root.temp_modifier();
		final Predicate<org.neo4j.graphdb.Node> filter;
		if (temporal == null) {
			filter = new Predicate<org.neo4j.graphdb.Node>() {
				@Override
				public boolean test(org.neo4j.graphdb.Node t) {
					return true;
				}
			};
		} else {
			filter = parseTemporal(temporal);
		}

		final List<Path> finalFrom = new ArrayList<>(fromElement.size() + extraFrom.size() + whereElemnt.size());
		finalFrom.addAll(fromElement);
//		finalFrom.addAll(extraFrom);
		finalFrom.addAll(whereElemnt);

		final StringBuilder finalQuery = new StringBuilder();
		finalQuery.append("MATCH ");
		joinAndAppend(finalFrom, " MATCH ", finalQuery);
		if (condition != null) {
			finalQuery.append(" WHERE ");
			finalQuery.append(condition);
		}
		finalQuery.append(" OPTIONAL MATCH ");
		joinAndAppend(extraFrom, " OPTIONAL MATCH ", finalQuery);

		finalQuery.append(" WITH ");
		
		final List<String> collect;
		if (returnSelectOnlyFrom.isEmpty()) {
			collect = new ArrayList<>(returnSelect.size());
			returnSelect.forEach(rs -> collect.add("collect(" + rs + ")"));
		} else {
			collect = new ArrayList<>(returnSelectOnlyFrom.size());
			returnSelectOnlyFrom.forEach(rs -> collect.add("collect(" + rs + ")"));
		}

		joinAndAppend(new ArrayList<>(collect), " + ", finalQuery);
		
		finalQuery.append(" AS result0 UNWIND result0 AS result RETURN DISTINCT result");
		
		System.out.println(finalQuery);
		System.out.println("TRANSLATION TIME " + (System.currentTimeMillis() - start));
		return Dao.query(finalQuery.toString(), filter);
	}

	private static Predicate<org.neo4j.graphdb.Node> parseTemporal(final Temp_modifierContext tmc) {
		final MomentContext moment = tmc.moment();
		if (moment == null) {
			return parseInterval(tmc.interval());
		} else {
			return parseSnapshot(tmc.moment());
		}
	}

	private static Predicate<org.neo4j.graphdb.Node> parseSnapshot(final MomentContext snapshot) {
		return new Snapshot(snapshot.getText());
	}

	private static Predicate<org.neo4j.graphdb.Node> parseInterval(final IntervalContext interval) {
		return new Interval(interval.moment(0).getText(), interval.moment(1).getText());
	}

	private static void joinAndAppend(final List<?> l, final String separator, final StringBuilder strBuilder) {
		strBuilder.append(l.get(0));
		for (int i = 1; i < l.size(); i++) {
			strBuilder.append(separator + l.get(i));
		}
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
		if (condition.BINARY_BOOLEAN_OPERATOR().getText().equals("AND")) {
			return new AndCondition(parseCondition(conditions.get(0)), parseCondition(conditions.get(1)));
		}

		if (condition.BINARY_BOOLEAN_OPERATOR().getText().equals("OR")) {
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

	private String addPathToWhereElements(final Attribute_representationContext attributeRepresentation) {
		final String nameOrAlias = attributeRepresentation.ID(0).getText();
		final String attribute = attributeRepresentation.ID(1).getText();
		final String realName = aliasToName.get(nameOrAlias);
		if (realName == null) {
			// TODO: Throw propper error
			throw new AssertionError("No esta el alias en la lista");
		}
		final Node nodeOrAlias = new ReferenceNode(nameOrAlias);
		final String attrNodeName = generator.getNext();
		final AttributeNode attributeNode = new AttributeNode(attribute, attrNodeName);
		final String valueNodeAlias = generator.getNext();
		final Path valuePath = Path.singleElementPath(new ValueNode(valueNodeAlias));
		final Path wherePath = Path.singleElementPath(nodeOrAlias).append(attributeNode, valuePath);
		whereElemnt.add(wherePath);
		returnSelect.add(attrNodeName);
		returnSelect.add(valueNodeAlias);
		return String.format("%s.%s", valueNodeAlias, "title");
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
	private Set<String> returnSelectOnlyFrom = new HashSet<>();

	private void parseSelect(final Enhanced_list_of_paths_or_allContext pathOrAll) {
		if (pathOrAll.getText().equals("*")) {
			insertAllAttributesToFrom();
			return;
		}
		insertAttributesToFrom(pathOrAll.enhanced_list_of_paths());
	}

	private void insertAttributesToFrom(final Enhanced_list_of_pathsContext paths) {
		final Path enhancedPath = parseEnhancedPath(paths.enhanced_path());
		boolean isInFrom = false;
		for(Path p : fromElement) {
			if (p.simpleToString().contains(enhancedPath.simpleToString())) {
				isInFrom = true;
			}
		}
		if (!isInFrom) {
			throw new AssertionError("The path: " + enhancedPath.simpleToString() + " no esta en el from!");
		}

		for (final Node n : enhancedPath.getNodes()) {
			final EnhancedElement ee = (EnhancedElement) n;
			extraFrom.addAll(ee.getAttributePaths(returnSelectOnlyFrom));
		}

		for (final Edge e : enhancedPath.getEdges()) {
			final EnhancedElement ee = (EnhancedElement) e;
			extraFrom.addAll(ee.getAttributePaths(returnSelectOnlyFrom));
		}

		final Enhanced_list_of_pathsContext elopc = paths.enhanced_list_of_paths();
		if (elopc != null) {
			insertAttributesToFrom(elopc);
		}
	}

	private Path parseEnhancedPath(final Enhanced_pathContext enhancedPath) {
		final Enhanced_elementContext element = enhancedPath.enhanced_element();
		if (element != null) {
			return Path.singleElementPath(parseEnhancedElement(element));
		}

		final Outbound_enhanced_pathContext outboundPath = enhancedPath.outbound_enhanced_path();
		if (outboundPath != null) {
			return parseOutboundPath(outboundPath);
		}

		return parseInboundPath(enhancedPath.inbound_enhanced_path());
	}

	private Path parseOutboundPath(final Outbound_enhanced_pathContext outboundPath) {
		final EnhancedElement ee0 = parseEnhancedElement(outboundPath.enhanced_element(0));
		final EnhancedElement ee1 = parseEnhancedElement(outboundPath.enhanced_element(1));
		final Path path = parseEnhancedPath(outboundPath.enhanced_path());
		// TODO: Improve interface for edge
		return Path.singleElementPath(ee0).append(ee1, path);
	}

	private Path parseInboundPath(final Inbound_enhanced_pathContext inboundPath) {
		final EnhancedElement ee0 = parseEnhancedElement(inboundPath.enhanced_element(0));
		final EnhancedElement ee1 = parseEnhancedElement(inboundPath.enhanced_element(1));
		final Path path = parseEnhancedPath(inboundPath.enhanced_path());
		// TODO: Improve interface for edge
		return Path.singleElementPath(ee0).append(ee1, path);
	}

	private EnhancedElement parseEnhancedElement(final Enhanced_elementContext eec) {
		final Attribute_list_or_allContext aloac = eec.attribute_list_or_all();
		if (aloac == null) {
			return new EnhancedElement(eec.ID().getText());
		}

		return new EnhancedElement(eec.ID().getText(), parseAttributeListOrAll(aloac));
	}

	private AttributeListOrAll parseAttributeListOrAll(final Attribute_list_or_allContext aloac) {
		final Attribute_listContext alc = aloac.attribute_list();
		if (alc == null) {
			return new AttributeListAll();
		}

		return parseAttributeList(alc);
	}

	private AttributeList parseAttributeList(final Attribute_listContext alc) {
		final Attribute_listContext innerAlc = alc.attribute_list();
		if (innerAlc == null) {
			return new AttributeList(alc.ID().getText());
		}

		return new AttributeList(alc.ID().getText(), parseAttributeList(innerAlc));
	}

	private void insertAllAttributesToFrom() {
		for (final Path path : fromElement) {
			for (final Node node : path.getNodes()) {
				final Path newPath = Path.singleElementPath(new ReferenceNode(((NodeOrAlias) node).getAlias()));
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
				final Path newPath = Path.singleElementPath(new ReferenceNode(((EdgeOrAlias) edge).getAlias()));
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
		final TerminalNode multiplePath = outboundPath.MULTIPLE_PATH();
		if (multiplePath != null) {
		    edge.setMultiple(parseMultiple(multiplePath.getText()));
		}
		
		return Path.singleElementPath(node).append(edge, path);
	}
	
	private Path parseInboundPath(final Inbound_pathContext inboundPath) {
		final NodeOrAlias node = parseNodeOrAlias(inboundPath.element_or_alias(0));
		final EdgeOrAlias edge = parseEdgeOrAlias(inboundPath.element_or_alias(1), EdgeOrAlias.Direction.LEFT);
		final Path path = parsePath(inboundPath.path());
		checkAliases(node, edge);
		final TerminalNode multiplePath = inboundPath.MULTIPLE_PATH();
		if (multiplePath != null) {
		    edge.setMultiple(parseMultiple(multiplePath.getText()));
		}
		
		return Path.singleElementPath(node).append(edge, path);
	}
	
	private static String parseMultiple(final String multiple) {
	    final String[] split = multiple.replace("[", "").replace("]", "").split("\\.\\.");
	    final int from = Integer.parseInt(split[0].trim());
	    final int to = Integer.parseInt(split[1].trim());
	    
	    return String.format("[* %d..%d {title: '%%s'}]", from * 2 - 1, to * 2 - 1);
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
