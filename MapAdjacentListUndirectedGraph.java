/**
 * 
 */
package it.unicam.cs.asdl2021.totalproject2;

import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Implementazione della classe astratta {@code Graph<L>} che realizza un grafo
 * non orientato. Non sono accettate etichette dei nodi null e non sono
 * accettate etichette duplicate nei nodi (che in quel caso sono lo stesso
 * nodo).
 * 
 * Per la rappresentazione viene usata una variante della rappresentazione con
 * liste di adiacenza. A differenza della rappresentazione standard si usano
 * strutture dati più efficienti per quanto riguarda la complessità in tempo
 * della ricerca se un nodo è presente (pseudocostante, con tabella hash) e se
 * un arco è presente (pseudocostante, con tabella hash). Lo spazio occupato per
 * la rappresentazione risulta tuttavia più grande di quello che servirebbe con
 * la rappresentazione standard.
 * 
 * Le liste di adiacenza sono rappresentate con una mappa (implementata con
 * tabelle hash) che associa ad ogni nodo del grafo i nodi adiacenti. In questo
 * modo il dominio delle chiavi della mappa è l'insieme dei nodi, su cui è
 * possibile chiamare il metodo contains per testare la presenza o meno di un
 * nodo. Ad ogni chiave della mappa, cioè ad ogni nodo del grafo, non è
 * associata una lista concatenata dei nodi collegati, ma un set di oggetti
 * della classe GraphEdge<L> che rappresentano gli archi collegati al nodo: in
 * questo modo la rappresentazione riesce a contenere anche l'eventuale peso
 * dell'arco (memorizzato nell'oggetto della classe GraphEdge<L>). Per
 * controllare se un arco è presente basta richiamare il metodo contains in
 * questo set. I test di presenza si basano sui metodi equals ridefiniti per
 * nodi e archi nelle classi GraphNode<L> e GraphEdge<L>.
 * 
 * Questa classe non supporta le operazioni indicizzate di ricerca di nodi e
 * archi.
 * 
 * @author Template: Luca Tesei
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class MapAdjacentListUndirectedGraph<L> extends Graph<L> {

    /*
     * Le liste di adiacenza sono rappresentate con una mappa. Ogni nodo viene
     * associato con l'insieme degli archi collegati. Nel caso in cui un nodo
     * non abbia archi collegati è associato con un insieme vuoto. La variabile
     * istanza è protected solo per scopi di test JUnit.
     */
    protected final Map<GraphNode<L>, Set<GraphEdge<L>>> adjacentLists;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public MapAdjacentListUndirectedGraph() {
        // Inizializza la mappa con la mappa vuota
        this.adjacentLists = new HashMap<GraphNode<L>, Set<GraphEdge<L>>>();
    }

    @Override
    public int nodeCount() {
        
        return this.adjacentLists.size();
    }

    @Override
    public int edgeCount() {
      return this.getEdges().size();
    }

    @Override
    public void clear() {
        this.adjacentLists.clear();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa grafi non orientati
        return false;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        
        return this.adjacentLists.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        
            if(node == null) throw new NullPointerException("Nodi null non supportati");
            if(adjacentLists.containsKey(node)) {
            	return false;
            }
             this.adjacentLists.putIfAbsent(node, new HashSet<GraphEdge<L>>());
            return true;


    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        
    	  if (node == null) {
              throw new NullPointerException("Parametro nullo!");
          }
    	  if(!this.containsNode(node)) {
    		  return false;
    	  }
    	  return this.adjacentLists.remove(node) !=null;
    	  
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
           if(node==null) {
        	   throw new NullPointerException();
           }
           else {
        	    return this.adjacentLists.containsKey(node);   
           }
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        
    	//il metodo restituisce il nodo avente la label passata
    	if(label==null) {
    		throw new NullPointerException();
    	}
    	//n=nodo
    	for(GraphNode<L> n : this.adjacentLists.keySet()) {
    		if(n.getLabel().equals(label)) {
    			return n;
    		}
    			}
    	
    	return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if (label == null)
            throw new NullPointerException(
                    "Tentativo di ricercare un nodo con etichetta null");
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        
    	if(node==null) {
    		throw new NullPointerException();
    	}
    	if(!this.adjacentLists.containsKey(node)) {
    		throw new IllegalArgumentException("Il nodo passato non esiste");
    	}
    	Set<GraphNode<L>> nodeSet = new HashSet<GraphNode<L>>();
		for(Set<GraphEdge<L>> objSet : this.adjacentLists.values()) {
			for(GraphEdge<L> obj : objSet) {
				if(obj.getNode2().equals(node)) nodeSet.add(obj.getNode1());
				if(obj.getNode1().equals(node)) nodeSet.add(obj.getNode2());
			}
		}
		return nodeSet;
        
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Ricerca dei nodi predecessori non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        
    	//creo un set di appoggio dove aggiungo man mano tutti gli archi del grafo,e poi lo restituisco
        Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();
        for(Set<GraphEdge<L>> edgeArray : this.adjacentLists.values()) {
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
        
    	if(edge==null) {
    		throw new NullPointerException();
    	}
    	if(!adjacentLists.containsKey(edge.getNode1()) || !adjacentLists.containsKey(edge.getNode2())) {
    		throw new IllegalArgumentException();
    	}
    	if(edge.isDirected()!=this.isDirected()) {
    		throw new IllegalArgumentException();
    	}
    	GraphEdge<L> inverseEdge = new GraphEdge<L>(edge.getNode2(), edge.getNode1(), false, edge.getWeight());
		if(this.containsEdge(edge) || this.containsEdge(inverseEdge)) return false;

		Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>(this.adjacentLists.get(edge.getNode1()));
		edgeSet.add(edge);
		//edgeSet.add(inverseEdge);
		this.adjacentLists.put(edge.getNode1(), edgeSet);

		return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        
    	if(edge==null) {
    		throw new NullPointerException();
    	}
    	if(!adjacentLists.containsKey(edge.getNode1()) || !adjacentLists.containsKey(edge.getNode2())) {
    		throw new IllegalArgumentException();
    	}
    	return adjacentLists.get(edge.getNode1()).remove(edge) || adjacentLists.get(edge.getNode2()).remove(edge);
        
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
         
    	if(edge==null) {
    		throw new NullPointerException();
    	}
    	if(!adjacentLists.containsKey(edge.getNode1()) || !adjacentLists.containsKey(edge.getNode2())) {
    		throw new IllegalArgumentException();
    	}
    	for (Map.Entry<GraphNode<L>, Set<GraphEdge<L>>> entry : adjacentLists.entrySet()) {
            if (entry.getValue().contains(edge)) {
                return true;
            }
        }

        
        return false;
    	
        
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        
    	if(node==null) {
    		throw new NullPointerException();
    	}
    	if(!adjacentLists.containsKey(node)) {
    		throw new IllegalArgumentException();
    		}
    	//grafo indiretto,quindi dobbiamo avere un arco e il suo inverso
    	Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();
		for(Set<GraphEdge<L>> edgeCollection : this.adjacentLists.values()) {
			for(GraphEdge<L> edge : edgeCollection) {
				//qui controllo se per ogni arco  esiste il suo inverso 
				if(edge.getNode2().equals(node)) edgeSet.add(edge);
				else {
				if(edge.getNode1().equals(node)) edgeSet.add(edge);
				}
			}
		}
		return edgeSet;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Ricerca degli archi entranti non supportata in un grafo non orientato");
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
    	 if (node1 == null || node2 == null) {
             throw new NullPointerException();
         }
         if (!adjacentLists.containsKey(node1) || !adjacentLists.containsKey(node2)) {
             throw new IllegalArgumentException();
         }
         //e=edge
         for (GraphEdge<L> e : adjacentLists.get(node1)) {
             if (e.getNode2().equals(node2)) {
                 return e;
             }
         }
         for (GraphEdge<L> e : adjacentLists.get(node2)) {
             if (e.getNode2().equals(node1)) {
                 return e;
             }
         }
         return null;
        
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
        throw new UnsupportedOperationException(
                "Operazioni con indici non supportate");
    }

}
