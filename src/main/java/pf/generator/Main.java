package pf.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {

    public static long randLong(long min, long max) {
	return (long) (Math.random() * (max - min)) + min;
    }

    private void createGraph() {

	final String[] firstNames = {
		"A", "B", "C", "D", "E", "F", "G",
	};
	final String[] lastNames = {
		"H", "I", "J", "K", "L", "M", "N",
	};

	final String[] friendCategory = {
		"Family", "Work", "Best", "Acquaintance"
	};

	final int PEOPLE = 1000;
	final int MAX_FRIENDS = 5;
	final int MAX_BUILDINGS = 10;
	final int BUILDINGS = 100;

	final List<Node> people = new ArrayList<>(PEOPLE);
	final List<Node> buildings = new ArrayList<>(BUILDINGS);
	final Map<Node, Set<Node>> peopleFriends = new HashMap<>();
	Transaction tx = null;
	try {
	    tx = gds.beginTx();
	    System.out.println("CREATING PEOPLE");
	    for (int i = 0; i < PEOPLE; i++) {
		final String name = firstNames[randInt(0, firstNames.length)];
		final String surname = lastNames[randInt(0, lastNames.length)];
		final String fullName = String.format("%s %s", name, surname);
		people.add(createPersonNode(fullName));
		System.out.println("PERSON " + i + " CREATED");
		if (i % 1000 == 0) {
		    tx.success();
		    tx.close();
		    tx = gds.beginTx();
		}
	    }
	    tx.success();
	    tx.close();
	    tx = gds.beginTx();
	    System.out.println("DONE CREATING PEOPLE");
	    System.out.println("CREATING BUILDINGS");
	    for (int i = 0; i < BUILDINGS; i++) {
		final String street = "Calle falsa";
		final String number = "123";
		buildings.add(createBuildingNode(street, number));
		System.out.println("BUILDING " + i + " CREATED");
		if (i % 1000 == 0) {
		    tx.success();
		    tx.close();
		    tx = gds.beginTx();
		}
	    }
	    tx.success();
	    tx.close();
	    tx = gds.beginTx();
	    System.out.println("DONE CREATING BUILDINGS");
	    for (int i = 0; i < people.size(); i++) {
		final int current = i;
		final Node personNode = people.get(i);
		final Set<Node> friends = peopleFriends.getOrDefault(personNode, new HashSet<>());
		if (friends.size() >= MAX_FRIENDS) {
		    continue;
		}
		final int extraFriends = randInt(0, MAX_FRIENDS - friends.size());

		System.out.println("PERSON " + i + " WILL HAVE " + extraFriends + " FRIENDS");

		for (int j = 0; j < extraFriends; j++) {
		    final int newFriend = randInt(0, people.size());
		    final Node newFriendNode = people.get(newFriend);
		    if (!peopleFriends.getOrDefault(newFriendNode, new HashSet<>()).contains(personNode)
			    && !friends.contains(newFriendNode) && newFriend != current) {
			if (makeFriends(personNode, newFriendNode)) {
			    friends.add(people.get(newFriend));
			}
		    }
		}
		peopleFriends.put(people.get(current), friends);
		System.out.println("FINISHED PERSON " + current);
		if (current % 10 == 0) {
		    tx.success();
		    tx.close();
		    tx = gds.beginTx();
		}
	    }

	    tx.success();
	    tx.close();
	    tx = gds.beginTx();
	    System.out.println("DONE CREATING PEOPLE");
	    for (int i = 0; i < people.size(); i++) {
		final Node personNode = people.get(i);
		final LocalDate birth = LocalDate.parse("1990-10-10", DateTimeFormatter.ISO_DATE);
		final LocalDate death = LocalDate.parse("2015-10-10", DateTimeFormatter.ISO_DATE);
		final int daysAlive = (int) birth.until(death, ChronoUnit.DAYS);
		final int buildingsAmount = Math.min(randInt(1, MAX_BUILDINGS), daysAlive);

		final List<Node> tmpBuildings = new ArrayList<>(buildings);

		System.out.println("PERSON " + i + " WILL HAVE " + buildingsAmount + " BUILDINGS");

		int remainingDays = daysAlive;

		LocalDate nextStart = birth;
		for (int j = 0; j < buildingsAmount; j++) {
		    final int daysInBuilding;
		    final Node building = tmpBuildings.remove(randInt(0, tmpBuildings.size()));
		    if (j == buildingsAmount - 1) {
			daysInBuilding = remainingDays;
		    } else {
			daysInBuilding = randInt(1, remainingDays - buildingsAmount + j + 1);
		    }
		    final LocalDate endDate = nextStart.plusDays(daysInBuilding);
		    makeLiving(personNode, building, nextStart.format(DateTimeFormatter.ISO_DATE), endDate
			.format(DateTimeFormatter.ISO_DATE));
		    remainingDays -= daysInBuilding;
		    nextStart = endDate.plusDays(1);
		}

		System.out.println("FINISHED PERSON BUILDING " + i);
		if (i % 10 == 0) {
		    tx.success();
		    tx.close();
		    tx = gds.beginTx();
		}
	    }

	    tx.success();
	} finally {
	    if (tx != null) {
		tx.close();
	    }
	}
    }

    private static int randInt(final int min, final int max) {
	return (int) (Math.random() * (max - min)) + min;
    }

    private Node createBuildingNode(final String street, final String number) {
	final int daysOfLife = randInt(0, 100 * 365);
	final String buildDate;
	final LocalDate ldt = LocalDate.now().minusDays(daysOfLife).minusDays(randInt(0, 50 * 365));
	buildDate = ldt.format(DateTimeFormatter.ISO_DATE);

	final String interval = String.format("[%s,%s]", buildDate, "inf");

	final Node buildingNode = createNode(NodeType.OBJETO, "Building", interval);

	final Node streetAttributeNode = createNode(NodeType.ATRIBUTO, "Street", interval);
	final Node numberAttributeNode = createNode(NodeType.ATRIBUTO, "Number", interval);

	final Node streetValueNode = createNode(NodeType.VALOR, street, interval);
	final Node numberValueNode = createNode(NodeType.VALOR, number, interval);

	createRelationship(buildingNode, streetAttributeNode);
	createRelationship(buildingNode, numberAttributeNode);
	createRelationship(streetAttributeNode, streetValueNode);
	createRelationship(numberAttributeNode, numberValueNode);

	return buildingNode;
    }

    private Node createPersonNode(final String name) {
	final boolean alive = Math.random() < 0.8;
	final int daysOfLife = randInt(0, 100 * 365);
	final String birthDate;
	final String deathDate;
	if (alive) {
	    final LocalDate ldt = LocalDate.now().minusDays(daysOfLife);
	    birthDate = ldt.format(DateTimeFormatter.ISO_DATE);
	    deathDate = "inf";
	} else {
	    final LocalDate ldt = LocalDate.now().minusDays(daysOfLife).minusDays(randInt(0, 50 * 365));
	    birthDate = ldt.format(DateTimeFormatter.ISO_DATE);
	    deathDate = ldt.plusDays(daysOfLife).format(DateTimeFormatter.ISO_DATE);
	}

	final String interval = String.format("[%s,%s]", birthDate, deathDate);

	final Node personNode = createNode(NodeType.OBJETO, "Person", interval);

	final Node nameAttributeNode = createNode(NodeType.ATRIBUTO, "Name", interval);
	final Node ageAttributeNode = createNode(NodeType.ATRIBUTO, "Age", interval);

	final Node nameValueNode = createNode(NodeType.VALOR, name, interval);
	final Node ageValueNode = createNode(NodeType.VALOR, Integer.toString(daysOfLife / 365), interval);

	createRelationship(personNode, nameAttributeNode);
	createRelationship(personNode, ageAttributeNode);
	createRelationship(nameAttributeNode, nameValueNode);
	createRelationship(ageAttributeNode, ageValueNode);

	return personNode;
    }

    private void makeLiving(final Node person, final Node building, final String start, final String end) {
	final Node livedIn = createNode(NodeType.ARISTA, "LivedIn", String.format("[%s,%s]", start, end));
	createRelationship(person, livedIn);
	createRelationship(livedIn, building);
    }

    private boolean makeFriends(final Node person1, final Node person2) {
	final String[] person1Interval = ((String) person1.getProperty("interval")).replace("[", "").replace("]", "")
	    .split(",");
	final String person1Birth = person1Interval[0];
	final String person1Death = person1Interval[1];
	final String[] person2Interval = ((String) person2.getProperty("interval")).replace("[", "").replace("]", "")
	    .split(",");
	final String person2Birth = person2Interval[0];
	final String person2Death = person2Interval[1];

	if (!person2Death.equals("inf") && person1Birth.compareTo(person2Death) > 0) {
	    return false;
	}
	if (!person1Death.equals("inf") && person2Birth.compareTo(person1Death) > 0) {
	    return false;
	}

	final LocalDate possibleStart;
	if (person1Birth.compareTo(person2Birth) < 0) {
	    possibleStart = LocalDate.parse(person2Birth, DateTimeFormatter.ISO_DATE);
	} else {
	    possibleStart = LocalDate.parse(person1Birth, DateTimeFormatter.ISO_DATE);
	}

	final LocalDate possibleEnd;

	if (person1Death.equals("inf") && person2Death.equals("inf")) {
	    possibleEnd = null;
	} else if (person1Death.equals("inf")) {
	    possibleEnd = LocalDate.parse(person2Death, DateTimeFormatter.ISO_DATE);
	} else {
	    possibleEnd = LocalDate.parse(person1Death, DateTimeFormatter.ISO_DATE);
	}

	final String start;
	final String end;
	if (possibleEnd == null) {
	    start = possibleStart.plusDays(randLong(1, possibleStart.until(LocalDate.now(), ChronoUnit.DAYS) - 1))
		.format(DateTimeFormatter.ISO_DATE);
	    end = "inf";
	} else {
	    start = possibleStart.plusDays(randLong(1, possibleStart.until(possibleEnd, ChronoUnit.DAYS) - 1)).format(
		DateTimeFormatter.ISO_DATE);
	    end = possibleEnd.format(DateTimeFormatter.ISO_DATE);
	}

	final Node friendship = createNode(NodeType.ARISTA, "Friend", String.format("[%s,%s]", start, end));
	createRelationship(person1, friendship);
	createRelationship(friendship, person2);
	createRelationship(person2, friendship);
	createRelationship(friendship, person1);

	return true;
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

    private Node createNode(final NodeType type, final String title, final String interval) {
	final Node n = gds.createNode(type);
	n.setProperty("title", title);
	n.setProperty("interval", interval);
	return n;
    }

    private static Relationship createRelationship(final Node from, Node to) {
	final Relationship rel = from.createRelationshipTo(to, RelType.ARISTA);

	final Label fromLabel = from.getLabels().iterator().next();
	final Label toLabel = to.getLabels().iterator().next();

	if (fromLabel.equals(NodeType.ARISTA) && toLabel.equals(NodeType.OBJETO)) {
	    rel.setProperty("title", from.getProperty("title"));
	} else if (toLabel.equals(NodeType.ARISTA) && fromLabel.equals(NodeType.OBJETO)) {
	    rel.setProperty("title", to.getProperty("title"));
	}

	return rel;
    }
}