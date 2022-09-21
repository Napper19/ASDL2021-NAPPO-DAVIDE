/**
 * 
 */
package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * Classe che implementa un grafo orientato tramite matrice di adiacenza. Non
 * sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * I nodi sono indicizzati da 0 a nodeCoount() - 1 seguendo l'ordine del loro
 * inserimento (0 è l'indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 * 
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 * 
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco orientato e contiene un oggetto della classe
 * GraphEdge<L> se lo sono. Tale oggetto rappresenta l'arco.
 * 
 * Questa classe non supporta la cancellazione di nodi, ma supporta la
 * cancellazione di archi e tutti i metodi che usano indici, utilizzando
 * l'indice assegnato a ogni nodo in fase di inserimento.
 * 
 * @author Template: Luca Tesei
 *
 */
public class AdjacencyMatrixDirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    // Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
    // matrice di adiacenza
    protected Map<GraphNode<L>, Integer> nodesIndex;

    // Matrice di adiacenza, gli elementi sono null o oggetti della classe
    // GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
    // dimensione gradualmente ad ogni inserimento di un nuovo nodo.
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public int nodeCount() {
        return nodesIndex.size();
    }


    @Override
    public int edgeCount() {
    	//getEdges restituisce l'insieme degli archi
       return this.getEdges().size();
     }


    @Override
    public void clear() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa un grafo orientato
        return true;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        
        return this.nodesIndex.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        //controllo che il param. non sia nullo
        if(node==null){
            throw new NullPointerException();
        }
        
        //controllo che il nodo non sia stato inserito gia' in precedenza
        if(this.nodesIndex.containsKey(node)) {
            return false;
        }else {
            //riempie di spazi nulli il nodesindex fino all'indice dell'elemento da inserire
            for(int i = 0; i < node.getHandle(); i++) {
                this.nodesIndex.put(null,i);
            }
            return this.nodesIndex.putIfAbsent(node, this.nodeCount()) != null;
        }


    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Remove di nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        
        if (node == null) {
            throw new NullPointerException("Il nodo passato � nullo");
        }
        return nodesIndex.containsKey(node);
    }


    @Override
    public GraphNode<L> getNodeOf(L label) {
        
    	if (label == null) {
            throw new NullPointerException();
        }
        for (GraphNode<L> n : nodesIndex.keySet()) {
            if (n.getLabel().equals(label)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        
        if(label == null) throw new NullPointerException();

        //scorre i nodi nel grafo fino a trovare quello con la label specificata
        for(GraphNode<L> n : nodesIndex.keySet()) {
            if(n.getLabel().equals(label)) return  nodesIndex.get(n);
        }
         //se il nodo non esiste
        throw new IllegalArgumentException("Nodo non presente");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
    	if (i < 0 || i >= nodesIndex.size()) {
            throw new IndexOutOfBoundsException(); 
        }
        for (GraphNode<L> n : nodesIndex.keySet()) {
            if (nodesIndex.get(n) == i) {
                return n;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        
    	if (node == null) {
            throw new NullPointerException("");
        }
        if (!nodesIndex.containsKey(node)) {
            throw new IllegalArgumentException("");
        }
         //il grafo � orientato
        //prende gli archi uscenti dalla sorgente e inserisce nel set di ritorno il nodo 2 di ciascuno
        Set<GraphNode<L>> adjacentNodes = new HashSet<GraphNode<L>>();
        for(ArrayList<GraphEdge<L>> edgeArray : this.matrix) {
            for(GraphEdge<L> edge : edgeArray) {
                if(edge.getNode1().equals(node)) adjacentNodes.add(edge.getNode2());
            }
        }
        return adjacentNodes;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        
    	if (node == null) {
            throw new NullPointerException("Passed parameter must be not null");
        }
        if (!nodesIndex.containsKey(node)) {
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }

        //prende gli archi entranti alla sorgente e inserisce nel set di ritorno il nodo 2 di ciascuno
        Set<GraphNode<L>> adjacentNodes = new HashSet<GraphNode<L>>();
        for(ArrayList<GraphEdge<L>> edgeArray : this.matrix) {
            for(GraphEdge<L> edge : edgeArray) {
                if(edge.getNode2().equals(node)) adjacentNodes.add(edge.getNode1());
            }
        }
        return adjacentNodes;
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        
    	//creo un set di appoggio dove aggiungo man mano tutti gli archi del grafo,e poi lo restituisco
        Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();

        for(ArrayList<GraphEdge<L>> edgeArray : this.matrix) {
            for(GraphEdge<L> edge : edgeArray) {
            	if(edge!=null) {
                edgeSet.add(edge);
                }
            }
        }

        return edgeSet;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        
    	if(edge == null) throw new NullPointerException();
    	if(!this.nodesIndex.containsKey(edge.getNode1()) || !this.nodesIndex.containsKey(edge.getNode2()))
    		throw new IllegalArgumentException();
    	if(edge.getNode1() == null || edge.getNode2() == null) throw new NullPointerException();
    	if(edge.isDirected() != this.isDirected()) throw new IllegalArgumentException();
    	if(this.containsEdge(edge)) return false;
        
        //e=edges
        ArrayList<GraphEdge<L>> e = new ArrayList<GraphEdge<L>>();
    	e.add(edge);
    	this.matrix.add(e);
    	
    	return true;
        
    }

    
	@SuppressWarnings("unlikely-arg-type")
	@Override
    public boolean removeEdge(GraphEdge<L> edge) {
        
        if(edge == null) throw new NullPointerException();
        if(!this.nodesIndex.containsKey(edge.getNode1()) || !this.nodesIndex.containsKey(edge.getNode2())) throw new IllegalArgumentException();

        return this.matrix.remove(edge);
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        
        if(edge == null) throw new NullPointerException();
        if(!this.nodesIndex.containsKey(edge.getNode1()) || !this.nodesIndex.containsKey(edge.getNode2())) throw new IllegalArgumentException();

        for(ArrayList<GraphEdge<L>> edgeArray : this.matrix) {
            for(GraphEdge<L> obj : edgeArray) {
                if(obj.equals(edge)) return true;
            }
        }
        return false;
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
    	 if (node == null) {
             throw new NullPointerException();
         }
         if (!nodesIndex.containsKey(node)) {
             throw new IllegalArgumentException();
         }
         //restituisce gli archi del nodo passato
         HashSet<GraphEdge<L>> o = new HashSet<>();
         for (GraphEdge<L> e : matrix.get(nodesIndex.get(node))) {
             if (e != null) {
                 o.add(e);
             }
         }
         return o;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        
   	     if (node == null) {
         throw new NullPointerException();
         }
         if (!nodesIndex.containsKey(node)) {
         throw new IllegalArgumentException();
         }
        
        
        Set<GraphEdge<L>> edgesSet = new HashSet<GraphEdge<L>>();
        for(ArrayList<GraphEdge<L>> edgeArray : this.matrix) {
            for(GraphEdge<L> edge : edgeArray) {
                if(edge.getNode1().equals(node)) edgesSet.add(edge);
            }
        }
        return edgesSet;
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        
        if(node1 == null || node2 == null) throw new NullPointerException();
        if(!this.nodesIndex.containsKey(node1) || !this.nodesIndex.containsKey(node1)) throw new IllegalArgumentException();
        //in caso di arco orientato viene ricercato l'arco che connette il primo nodo specificato al secondo
        //con il ciclo for viene restituito l'arco che collega node1 e node2
        for(ArrayList<GraphEdge<L>> arrayEdge : this.matrix) {
        	for(GraphEdge<L> e : arrayEdge) {
        		if(e.getNode1().equals(node1) && e.getNode2().equals(node2)) return e;
        	}
        }
        return null;
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
    	 if ((i < 0 || i >= matrix.size()) || (j < 0 || j >= matrix.size())) {
             throw new IndexOutOfBoundsException();
         }

         
         GraphNode<L> node1 = getNodeAtIndex(i);
         GraphNode<L> node2 = getNodeAtIndex(j);

         return getEdge(node1, node2);
    }

    
}
