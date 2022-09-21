package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Classe singoletto che implementa l'algoritmo di Kruskal per trovare un
 * Minimum Spanning Tree di un grafo non orientato, pesato e con pesi non
 * negativi.
 * 
 * @author Template: Luca Tesei
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class KruskalMSP<L> {

    /*
     * Struttura dati per rappresentare gli insiemi disgiunti utilizzata
     * dall'algoritmo di Kruskal.
     */
    private ArrayList<HashSet<GraphNode<L>>> disjointSets;

    

    /**
     * Costruisce un calcolatore di un albero di copertura minimo che usa
     * l'algoritmo di Kruskal su un grafo non orientato e pesato.
     */
    public KruskalMSP() {
        this.disjointSets = new ArrayList<HashSet<GraphNode<L>>>();
        
    }

    /**
     * Utilizza l'algoritmo goloso di Kruskal per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. L'albero restituito non è radicato, quindi è rappresentato
     * semplicemente con un sottoinsieme degli archi del grafo.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @return l'insieme degli archi del grafo g che costituiscono l'albero di
     *         copertura minimo trovato
     * @throw NullPointerException se il grafo g è null
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public Set<GraphEdge<L>> computeMSP(Graph<L> g) {
        
    	 // In caso di parametro null
        if (g == null) {
            throw new NullPointerException("Parametro null!");
        }
        // Se il grafo � orientato
        if (g.isDirected()) {
            throw new IllegalArgumentException("Il grafo � orientato");
        }
        // Se il grafo ha archi non pesati o negativi
        for (GraphEdge<L> edge : g.getEdges()) {
            if ((!edge.hasWeight()) || (edge.getWeight() < 0)) {
                throw new IllegalArgumentException("Arco non pesato o negativo!");
            }
        }
        ArrayList<GraphEdge<L>> archiOrdinati = sortEdges(g.getEdges());
        Set<GraphEdge<L>> archiIdonei = new HashSet<>();
        
        //creo gli insiemi disgiunti,uno per ogni nodo
        for(GraphNode<L> node : g.getNodes()){
            HashSet<GraphNode<L>> lista = new HashSet<>();
            lista.add(node);
            this.disjointSets.add(lista);
        }
        //per ogni arco
        for(GraphEdge<L> arco : archiOrdinati){
        	//se i vertici non appartengono allo stesso insieme(quindi evito i cicli)
            if(findSet(arco.getNode1()) != findSet(arco.getNode2())){
            	// L'arco viene aggiunto all'insieme degli archi che effettuano il Kruskal
                archiIdonei.add(arco);
                //unisco i due insiemi
                unionSet(arco.getNode1(), arco.getNode2());
            }
        }
        return archiIdonei;
        
    }

    
    
 // Metodi inseriti al fine dell'implementazione

    /**
     * Unisce i due Set, all'interno dell'ArrayList disjointSets, che
     * contengono i nodi passati
     * @param u
     *          nodo contenuto in un Set della lista
     * @param v
     *          nodo contenuto in un Set della lista
     */
    private void unionSet(GraphNode<L> u, GraphNode<L> v){
        int uIndex = findSet(u);
        int vIndex = findSet(v);
        this.disjointSets.get(uIndex).addAll(this.disjointSets.get(vIndex));
        this.disjointSets.remove(vIndex);
    }

    /**
     * Trova l'indice del Set, all'interno dell'ArrayList disjointSets, che
     * contiene il nodo passato
     * @param nodo
     *              Il nodo da cercare all'interno dei Set della lista
     * @return L'indice del Set all'interno della lista
     */
    private int findSet(GraphNode<L> nodo){
        for(int i=0; i<this.disjointSets.size(); i++){
            if(this.disjointSets.get(i).contains(nodo)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Ordina gli archi in modo crescente in base al loro peso
     * @param archi
     *              la lista degli archi che si vuole ordinare
     * @return una lista degli archi ordinati
     */
    private ArrayList<GraphEdge<L>> sortEdges(Set<GraphEdge<L>> archi) {
        ArrayList<GraphEdge<L>> archi_ordinati = new ArrayList<GraphEdge<L>>(archi);
        for (int i=0; i < archi_ordinati.size(); i++) {
            GraphEdge<L> key = archi_ordinati.get(i);
            int j = i - 1;
            while (j >= 0 && archi_ordinati.get(j).getWeight() > key.getWeight()) {
            	//sostituisco l'elemento in posiz j+1 con archi_ordinati.get(j)
                archi_ordinati.set(j+1, archi_ordinati.get(j));
                j = j - 1;
            }
            archi_ordinati.set(j+1, key);
        }
        return archi_ordinati;
    }

   
}
