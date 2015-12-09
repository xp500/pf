package pf.generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Traverser;

public class Main {

	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private void createGraph() {
		final String[] teamNames = { "Alfa", "Beta", "Gamma", "Delta", "Epsilon", "Omega", "Eta", "Ro", "Teta",
				"Zeta" };
		final String[] playerNames = { "Rico Espinoza", "Chori Dominguez", "Gato Gonzales", "Rodrigo Pereira",
				"Martin Curita", "Francisco Pascualichi", "Bruno Calani", "Pablo Rabinovich", "Pablo Caval",
				"Labio Camargo", "Denis Cobani", "Facundo Vencela", "Juan Barullo", "Juan Champo", "Tiki tiki",
				"Raul Corralez", "Deigo Cayan", "Hernan Venga", "Rodrigo Lopez Chato", "Facundo Cansancio",
				"Mauricio Arqui", "Alfonso Zapato", "Robinson Cruzo" };
		final String[] positionNames = { "Delantero", "Mediocampista", "Defensor" };

		final String[] stadiumNames = { "Alfa Stadium", "Beta Stadium", "Gamma Stadium", "Delta Stadium",
				"Epsilon Stadium", "Omega Stadium", "Eta Stadium", "Ro Stadium", "Teta Stadium", "Zeta Stadium" };

		final Map<String, int[]> posToNum = new HashMap<>();
		posToNum.put("Delantero", new int[] { 8, 9, 10, 11 });
		posToNum.put("Mediocampista", new int[] { 6, 7 });
		posToNum.put("Defensor", new int[] { 2, 3, 4, 5 });

		final List<String> teams = Arrays.asList(teamNames);
		final List<String> players = Arrays.asList(playerNames);
		final List<String> positions = Arrays.asList(positionNames);

		final Map<String, Node> playedMap = new HashMap<>();

		final Map<String, Node> teamMap = new HashMap<>();

		final Set<Node> stadiumSet = new HashSet<>();
		final Set<Node> playerSet = new HashSet<>();

		try (final Transaction tx = gds.beginTx()) {
			// int x = 0;
			for (final String player : players) {
				// if (x == 1) {
				// tx.success();
				// return;
				// }
				// x++;
				int yearsPlayed = randInt(7, 15);
				int yearStarted = randInt(1980, 1990);
				final Node playerObjectNode = createPlayerNode(player, yearStarted, yearsPlayed);

				playerSet.add(playerObjectNode);

				int teamsPlayed = randInt(1, yearsPlayed - 1);
				int years = 0;
				String lastTeam = null;
				for (int i = 0; i < teamsPlayed; i++) {
					int yearsInTeam;
					if (i == teamsPlayed - 1) {
						yearsInTeam = yearsPlayed - years;
					} else {
						yearsInTeam = randInt(1, yearsPlayed / teamsPlayed);
						years += yearsInTeam;
					}

					String currentTeam;
					do {
						currentTeam = teamNames[randInt(0, teamNames.length - 1)];
					} while (lastTeam == currentTeam);

					lastTeam = currentTeam;

					Node teamNode = teamMap.get(currentTeam);
					if (teamNode == null) {
						final int startYear = randInt(1900, 1950);
						teamNode = createTeamNode(currentTeam, startYear);
						teamMap.put(currentTeam, teamNode);
						final Node stadiumNode = createStadiumNode(currentTeam + " Stadium", randInt(30000, 100000),
								startYear);

						stadiumSet.add(stadiumNode);
						
						final String format = "[%d, inf]";
						final Map<String, Object> prop = new HashMap<>();
						prop.put("interval", String.format(format, startYear));
						prop.put("title", "Dueno");
						final Node arista = createNode(NodeType.ARISTA, prop);

						createRelationship(teamNode, arista);
						createRelationship(arista, stadiumNode);
					}
					Node playedNode = playedMap.get(player + currentTeam);

					final String position = positionNames[randInt(0, positionNames.length - 1)];
					final int[] posibleNumbers = posToNum.get(position);
					final int num = posibleNumbers[randInt(0, posibleNumbers.length - 1)];

					if (playedNode == null) {
						playedNode = createPlayedNode(yearStarted + years, yearsInTeam, position, num);
						playedMap.put(player + currentTeam, playedNode);
						createRelationship(playerObjectNode, playedNode);
						createRelationship(playedNode, teamNode);
					} else {
						updatePlayedNode(playedNode, yearStarted + years, yearsInTeam, position, num);
					}
				}

				int teamsWantedToPlay = randInt(1, teamNames.length/4);
				Set<String> teamsWantedToPlayAdded = new HashSet<>();
				for (int i = 0; i < teamsWantedToPlay; i++) {

					String currentTeam;
					do {
						currentTeam = teamNames[randInt(0, teamNames.length - 1)];
					} while (teamsWantedToPlayAdded.contains(currentTeam));
					teamsWantedToPlayAdded.add(currentTeam);
					Node teamNode = teamMap.get(currentTeam);
					if (teamNode == null) {
						final int startYear = randInt(1900, 1950);
						teamNode = createTeamNode(currentTeam, startYear);
						teamMap.put(currentTeam, teamNode);
						final Node stadiumNode = createStadiumNode(currentTeam + " Stadium", randInt(30000, 100000),
								startYear);

						stadiumSet.add(stadiumNode);

						final String format = "[%d, inf]";
						final Map<String, Object> prop = new HashMap<>();
						prop.put("interval", String.format(format, startYear));
						prop.put("title", "Dueno");
						final Node arista = createNode(NodeType.ARISTA, prop);

						createRelationship(teamNode, arista);
						createRelationship(arista, stadiumNode);
					}

					Node wantedToPlayeNode = createWantedToPlayeNode(yearStarted + years);
					createRelationship(playerObjectNode, wantedToPlayeNode);
					createRelationship(wantedToPlayeNode, teamNode);
				}

			}

			// for (final Node playerNode : playerSet) {
			// for (fin)
			// }

			tx.success();
		}
	}

	private Node createWantedToPlayeNode(int startYear) {
		final String format = "[%d, inf]";
		final Map<String, Object> playerProperties = new HashMap<>();
		playerProperties.put("title", "QuiereJugar");
		playerProperties.put("interval", String.format(format, startYear));
		final Node wantedToPlayNode = createNode(NodeType.ARISTA, playerProperties);
		return wantedToPlayNode;
	}

	private Node createStadiumNode(String stadiumName, int capacity, final int startYear) {
		final String format = "[%d, inf]";
		final Map<String, Object> stadiumProperties = new HashMap<>();
		stadiumProperties.put("title", "Estadio");
		stadiumProperties.put("interval", String.format(format, startYear));
		final Node stadiumNode = createNode(NodeType.OBJETO, stadiumProperties);
		{
			final Map<String, Object> attributeProperties = new HashMap<>();
			attributeProperties.put("title", "Capacidad");
			attributeProperties.put("interval", String.format(format, startYear));
			final Node attributeNode = createNode(NodeType.ATRIBUTO, attributeProperties);
			final Map<String, Object> valueProperties = new HashMap<>();
			valueProperties.put("title", capacity);
			valueProperties.put("interval", String.format(format, startYear));
			final Node valueNode = createNode(NodeType.VALOR, valueProperties);

			createRelationship(stadiumNode, attributeNode);
			createRelationship(attributeNode, valueNode);
		}
		{
			final Map<String, Object> attributeProperties = new HashMap<>();
			attributeProperties.put("title", "Nombre");
			attributeProperties.put("interval", String.format(format, startYear));
			final Node attributeNode = createNode(NodeType.ATRIBUTO, attributeProperties);
			final Map<String, Object> valueProperties = new HashMap<>();
			valueProperties.put("title", stadiumName);
			valueProperties.put("interval", String.format(format, startYear));
			final Node valueNode = createNode(NodeType.VALOR, valueProperties);

			createRelationship(stadiumNode, attributeNode);
			createRelationship(attributeNode, valueNode);
		}

		return stadiumNode;
	}

	private void updatePlayedNode(final Node playedNode, int startYear, int yearsInTeam, String position, int num) {
		final String format = ", [%d, %d]";
		final String cleanFormat = "[%d, %d]";
		playedNode.setProperty("interval",
				playedNode.getProperty("interval") + String.format(format, startYear, startYear + yearsInTeam));

		final Traverser traverser = gds.traversalDescription().relationships(RelType.ARISTA, Direction.OUTGOING)
				.evaluator(Evaluators.excludeStartPosition()).evaluator(Evaluators.atDepth(1)).traverse(playedNode);

		final List<Node> list = StreamSupport.stream(traverser.nodes().spliterator(), false)
				.filter(node -> node.hasLabel(NodeType.ATRIBUTO)).collect(Collectors.toList());

		list.forEach(node -> {
			node.setProperty("interval",
					node.getProperty("interval") + String.format(format, startYear, startYear + yearsInTeam));
		});

		list.forEach(node -> {
			if (node.getProperty("title").equals("Posicion")) {
				final Traverser traverser2 = gds.traversalDescription()
						.relationships(RelType.ARISTA, Direction.OUTGOING).evaluator(Evaluators.excludeStartPosition())
						.traverse(playedNode);
				final List<Node> l2 = StreamSupport.stream(traverser2.nodes().spliterator(), false)
						.filter(n -> n.getProperty("title").equals(position)).collect(Collectors.toList());

				if (l2.size() > 1) {
					throw new AssertionError("Boom");
				}

				if (l2.isEmpty()) {
					final Map<String, Object> valueProperties = new HashMap<>();
					valueProperties.put("title", position);
					valueProperties.put("interval", String.format(cleanFormat, startYear, startYear + yearsInTeam));
					final Node positionNode = createNode(NodeType.VALOR, valueProperties);

					createRelationship(node, positionNode);
				} else {
					final Node n = l2.get(0);
					n.setProperty("interval",
							n.getProperty("interval") + String.format(format, startYear, startYear + yearsInTeam));
				}

			} else {
				final Traverser traverser2 = gds.traversalDescription()
						.relationships(RelType.ARISTA, Direction.OUTGOING).evaluator(Evaluators.excludeStartPosition())
						.traverse(playedNode);
				final List<Node> l2 = StreamSupport.stream(traverser2.nodes().spliterator(), false)
						.filter(n -> n.getProperty("title").equals(num)).collect(Collectors.toList());

				if (l2.size() > 1) {
					throw new AssertionError("Boom");
				}

				if (l2.isEmpty()) {
					final Map<String, Object> valueProperties = new HashMap<>();
					valueProperties.put("title", num);
					valueProperties.put("interval", String.format(cleanFormat, startYear, startYear + yearsInTeam));
					final Node positionNode = createNode(NodeType.VALOR, valueProperties);

					createRelationship(node, positionNode);
				} else {
					final Node n = l2.get(0);
					n.setProperty("interval",
							n.getProperty("interval") + String.format(format, startYear, startYear + yearsInTeam));
				}

			}
		});

	}

	private Node createPlayedNode(int startYear, int yearsInTeam, String position, int num) {
		final String format = "[%d, %d]";
		final Map<String, Object> playerProperties = new HashMap<>();
		playerProperties.put("title", "Jugo");
		playerProperties.put("interval", String.format(format, startYear, startYear + yearsInTeam));
		final Node playedNode = createNode(NodeType.ARISTA, playerProperties);
		final Map<String, Object> attributeProperties = new HashMap<>();
		attributeProperties.put("title", "Posicion");
		attributeProperties.put("interval", String.format(format, startYear, startYear + yearsInTeam));
		final Node attributeNode = createNode(NodeType.ATRIBUTO, attributeProperties);
		final Map<String, Object> valueProperties = new HashMap<>();
		valueProperties.put("title", position);
		valueProperties.put("interval", String.format(format, startYear, startYear + yearsInTeam));
		final Node valueNode = createNode(NodeType.VALOR, valueProperties);

		createRelationship(playedNode, attributeNode);
		createRelationship(attributeNode, valueNode);

		final Map<String, Object> attribute2Properties = new HashMap<>();
		attribute2Properties.put("title", "Numero");
		attribute2Properties.put("interval", String.format(format, startYear, startYear + yearsInTeam));
		final Node attribute2Node = createNode(NodeType.ATRIBUTO, attribute2Properties);
		final Map<String, Object> value2Properties = new HashMap<>();
		value2Properties.put("title", num);
		value2Properties.put("interval", String.format(format, startYear, startYear + yearsInTeam));
		final Node value2Node = createNode(NodeType.VALOR, value2Properties);

		createRelationship(playedNode, attribute2Node);
		createRelationship(attribute2Node, value2Node);

		return playedNode;
	}

	private Node createTeamNode(String team, int yearStarted) {
		final String format = "[%d, inf]";
		final Map<String, Object> teamProperties = new HashMap<>();
		teamProperties.put("title", "Equipo");
		teamProperties.put("interval", String.format(format, yearStarted));
		final Node teamNode = createNode(NodeType.OBJETO, teamProperties);
		final Map<String, Object> attributeProperties = new HashMap<>();
		attributeProperties.put("title", "Nombre");
		attributeProperties.put("interval", String.format(format, yearStarted));
		final Node attributeNode = createNode(NodeType.ATRIBUTO, attributeProperties);
		final Map<String, Object> valueProperties = new HashMap<>();
		valueProperties.put("title", team);
		valueProperties.put("interval", String.format(format, yearStarted));
		final Node valueNode = createNode(NodeType.VALOR, valueProperties);

		createRelationship(teamNode, attributeNode);
		createRelationship(attributeNode, valueNode);

		final Map<String, Object> attribute2Properties = new HashMap<>();
		attribute2Properties.put("title", "Socios");
		attribute2Properties.put("interval", String.format(format, yearStarted));
		final Node attribute2Node = createNode(NodeType.ATRIBUTO, attribute2Properties);

		// TODO: Agregar mas socios por bache.
		final Map<String, Object> value2Properties = new HashMap<>();
		value2Properties.put("title", randInt(1000, 10000));
		value2Properties.put("interval", String.format(format, yearStarted));
		final Node value2Node = createNode(NodeType.VALOR, value2Properties);

		createRelationship(teamNode, attribute2Node);
		createRelationship(attribute2Node, value2Node);

		return teamNode;

	}

	private Node createPlayerNode(final String playerName, int yearStarted, int yearsPlayed) {
		final String format = "[%d, %d]";
		final Map<String, Object> playerProperties = new HashMap<>();
		playerProperties.put("title", "Jugador");
		playerProperties.put("interval", String.format(format, yearStarted, yearStarted + yearsPlayed));
		final Node playerNode = createNode(NodeType.OBJETO, playerProperties);
		final Map<String, Object> attributeProperties = new HashMap<>();
		attributeProperties.put("title", "Nombre");
		attributeProperties.put("interval", String.format(format, yearStarted, yearStarted + yearsPlayed));
		final Node attributeNode = createNode(NodeType.ATRIBUTO, attributeProperties);
		final Map<String, Object> valueProperties = new HashMap<>();
		valueProperties.put("title", playerName);
		valueProperties.put("interval", String.format(format, yearStarted, yearStarted + yearsPlayed));
		final Node valueNode = createNode(NodeType.VALOR, valueProperties);

		createRelationship(playerNode, attributeNode);
		createRelationship(attributeNode, valueNode);

		return playerNode;
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Falta nombre de archivo");
			return;
		}
		final Main m = new Main(args[0]);

		m.createGraph();
	}

	public static GraphDatabaseService createEmbeddedGraphDatabaseService(final String storeDir) {
		final GraphDatabaseService gds = new GraphDatabaseFactory().newEmbeddedDatabase(storeDir);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> gds.shutdown()));
		return gds;
	}

	private static enum RelType implements RelationshipType {
		ARISTA
	}

	private static enum NodeType implements Label {
		OBJETO, ARISTA, ATRIBUTO, VALOR
	}

	private final GraphDatabaseService gds;

	public Main(final String path) {
		gds = createEmbeddedGraphDatabaseService(path);
	}

	private Node createNode(final NodeType type, final Map<String, Object> properties) {
		final Node n = gds.createNode(type);
		for (final Entry<String, Object> p : properties.entrySet()) {
			n.setProperty(p.getKey(), p.getValue());
		}
		return n;
	}

	private static Relationship createRelationship(final Node from, Node to) {
		final Relationship rel = from.createRelationshipTo(to, RelType.ARISTA);
		return rel;
	}
}