package pf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.neo4j.graphdb.Node;

public class Graph {

    private final Map<Long, Node> nodes = new HashMap<>();

    private final Map<Long, NodoObjeto> nodosObjeto = new HashMap<>();

    private final Map<Long, NodoArista> nodosArista = new HashMap<>();
    
    public Map<Long, Node> getNodes() {
	return nodes;
    }

    private class Nodo {

	protected final long id;

	Nodo(final long id) {
	    this.id = id;
	}

	public void clean() {
	    nodes.remove(id);
	}
    }
    
    private class NodoObjeto extends Nodo {

	protected final Map<Long, NodoAtributo> atributos = new HashMap<>();
	private int connections = 0;
	private boolean connected = false;

	public NodoObjeto(long id) {
	    super(id);
	}
	
	
	public void incConnections() {
	    connected = true;
	    connections++;
	}
	
	public void decConnections() {
	    connections--;
	}

	@Override
	public void clean() {
	    super.clean();
	    final Iterator<Long> it0 = atributos.keySet().iterator();
	    while (it0.hasNext()) {
		final Long id = it0.next();
		atributos.get(id).clean();
		it0.remove();
		nodes.remove(id);
	    }
	}

    }

    private class NodoArista extends NodoObjeto {

	private final NodoObjeto from;
	private final NodoObjeto to;

	public NodoArista(final long id, final NodoObjeto from, final NodoObjeto to) {
	    super(id);
	    this.from = from;
	    this.to = to;
	}

	@Override
	public void clean() {
	    super.clean();
	    from.decConnections();
	    to.decConnections();
	}

    }

    private class NodoAtributo extends Nodo {

	private final List<Long> nodosValor = new ArrayList<>();

	public NodoAtributo(long id) {
	    super(id);
	}

	@Override
	public void clean() {
	    super.clean();
	    nodosValor.forEach(n -> nodes.remove(n));
	}

    }

    public void addNodoObjeto(final long id) {
	nodosObjeto.put(id, new NodoObjeto(id));
    }

    public void addNodo(final Node node) {
	nodes.put(node.getId(), node);
    }

    public void clean(final Predicate<Node> filter) {
	final Iterator<Entry<Long, NodoArista>> it0 = nodosArista.entrySet().iterator();
	while (it0.hasNext()) {
	    final Entry<Long, NodoArista> entry = it0.next();
	    final long id = entry.getKey();
	    final Node n = nodes.get(id);
	    if (!filter.test(n)) {
		entry.getValue().clean();
		it0.remove();
		nodes.remove(id);
	    }
	}

	final Iterator<Entry<Long, NodoObjeto>> it1 = nodosObjeto.entrySet().iterator();
	while (it1.hasNext()) {
	    final Entry<Long, NodoObjeto> entry = it1.next();
	    final Long id = entry.getKey();
	    final NodoObjeto no = entry.getValue();
	    final Node n = nodes.get(id);
	    if (n == null) {
		it1.remove();
	    } else if (!filter.test(n) || (no.connected && no.connections == 0)) {
		entry.getValue().clean();
		it1.remove();
		nodes.remove(id);
	    }
	}

    }

    public void addNodoValor(long nodoObjetoOrArista, long nodoAtributo, long nodoValor) {
	if (nodosObjeto.containsKey(nodoObjetoOrArista)) {
	    final Map<Long, NodoAtributo> attributes = nodosObjeto.get(nodoObjetoOrArista).atributos;
	    final NodoAtributo na = attributes.getOrDefault(nodoAtributo, new NodoAtributo(nodoAtributo));
	    na.nodosValor.add(nodoValor);
	    attributes.put(nodoAtributo, na);
	} else if (nodosArista.containsKey(nodoObjetoOrArista)) {
	    final Map<Long, NodoAtributo> attributes = nodosArista.get(nodoObjetoOrArista).atributos;
	    final NodoAtributo na = attributes.getOrDefault(nodoAtributo, new NodoAtributo(nodoAtributo));
	    na.nodosValor.add(nodoValor);
	    attributes.put(nodoAtributo, na);
	} else {
	    throw new AssertionError("ERROR!");
	}

    }

    public void addNodoArista(final long id, final long from, final long to) {
	if (!nodosObjeto.containsKey(from) || !nodosObjeto.containsKey(from)) {
	    throw new AssertionError("ERROR!");
	}

	nodosObjeto.get(from).incConnections();
	nodosObjeto.get(to).incConnections();
	nodosArista.put(id, new NodoArista(id, nodosObjeto.get(from), nodosObjeto.get(to)));
    }

}
