package it.unicam.cs.asdl2021.totalproject2;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * 
 * L'algoritmo usa una coda di min priorit√† tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poich√© la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell'esecuzione dell'algoritmo √® necessario utilizzare l'operazione di
 * decreasePriority.
 * 
 * @author Template: Luca Tesei
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class PrimMSP<L> {

    /*
     * Coda di priorit√† che va usata dall'algoritmo. La variabile istanza √®
     * protected solo per scopi di testing JUnit.
     */
    protected BinaryHeapMinPriorityQueue queue;

    /**
     * Crea un nuovo algoritmo e inizializza la coda di priorit√† con una coda
     * vuota.
     */
    public PrimMSP() {
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    /**
     * Utilizza l'algoritmo goloso di Prim per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non negativi.
     * Dopo l'esecuzione del metodo nei nodi del grafo il campo previous deve
     * contenere un puntatore a un nodo in accordo all'albero di copertura
     * minimo calcolato, la cui radice √® il nodo sorgente passato.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @param s
     *              il nodo del grafo g sorgente, cio√® da cui parte il calcolo
     *              dell'albero di copertura minimo. Tale nodo sar√† la radice
     *              dell'albero di copertura trovato
     * 
     * @throw NullPointerException se il grafo g o il nodo sorgente s sono nulli
     * @throw IllegalArgumentException se il nodo sorgente s non esiste in g
     * @throw IllegalArgumentException se il grafo g √® orientato, non pesato o
     *        con pesi negativi
     */
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
        
    	 //Controllo se gli elementi passati siano nulli
        if(g == null || s == null){
            throw new NullPointerException("Uno dei due valori passati Ë nullo");
        }
        //Controllo che il nodo sorgente passato sia contenuto nel grafo
        if(!g.containsNode(s)){
            throw new IllegalArgumentException("Il nodo passato non Ë nel grafo");
        }
        //Controllo se i dati degli elementi siano idonei
        if(g.isDirected() || !hasWeight(g)){
            throw new IllegalArgumentException("Il grafo ha archi con pesi non idonei oppure Ë orientato");
        }
        //Ciclo tutti i nodi del grafo
        for(GraphNode<L> nodo : g.getNodes()){
            //Imposto tutte le priorit‡ dei nodi ad infinito
            nodo.setPriority(Double.POSITIVE_INFINITY);
            //Imposto la priority del nodo sorgente con un valore pi˘ basso in modo che sar‡ il primo ad
            // essere preso durante l'estrazione dallo heap
            if(nodo.equals(s)){
                nodo.setPriority(0);
            }
            //Setto tutti i padri dei nodi a null
            nodo.setPrevious(null);
            //Inserisco i nodi all'interno dello heap
            this.queue.insert(nodo);
        }
        //Scorro lo heap fino a che non Ë vuoto
        while(this.queue.size() != 0){
            //Creo una variabile di appoggio
            GraphNode<L> appoggio = null;
            //Ciclo tutti i nodi del grafo
            int handle = this.queue.extractMinimum().getHandle();
            for(GraphNode<L> nodo : g.getNodes()) {
                //Cerco il nodo corrispondente all'handle del valore minimo dello heap
                if (nodo.getHandle() == handle) {
                    appoggio = nodo;
                    break;
                }
            }
            //Scorro tutti i nodi adiacenti al nodo precedentemente estratto dallo heap
            for(GraphNode<L> adj : g.getAdjacentNodesOf(appoggio)){
                //Controllo se lo heap contiene questo nuovo nodo
                if(!this.queue.contains(adj)) {
                    //In caso affermativo ciclo tutti gli archi uscenti del nodo estratto dallo heap
                    for (GraphEdge<L> edge : g.getEdgesOf(appoggio)) {
                        //Controllo se l' arco collega i due nodi interessati
                        if(correctEdge(edge, appoggio, adj)){
                            //Controllo se il nuovo nodo ha una priority p˘ alta del peso dell'arco o ancora non
                            // Ë stato visitato
                            if(adj.getPriority() > edge.getWeight()){
                                //In caso affermativo imposto il padre del nodo nuovo
                                // con il nodo estratto dallo heap
                                adj.setPrevious(appoggio);
                                //Imposto la nuova priority del nodo scoperto con il peso dell'arco
                                adj.setPriority(edge.getWeight());
                            }
                        }
                    }
                }
            }
        }
    }

    //Metodi aggiunti per l'implementazione
    /**
     * Controlla se il grafo ha tutti gli archi pesati e con i pesi positivi
     * @param g
     *          il grafo da controllare
     * @return  true se tutti gli archi sono pesati e con pesi positivi, false altrimenti
     */
    private boolean hasWeight(Graph<L> g){
        Set<GraphEdge<L>> archi = new HashSet<>(g.getEdges());
        for(GraphEdge<L> arco : archi){
            if(!arco.hasWeight() || arco.getWeight() < 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Controlla se l'arco passato colleghi effettivamente i due nodi
     * @param edge
     *              L'arco da controllare
     * @param node1
     *              Nodo da verificare
     * @param node2
     *              Nodo da verificare
     * @return  true se l'arco collega i due nodi, false altrimenti
     */
    private boolean correctEdge(GraphEdge<L> edge, GraphNode<L> node1, GraphNode<L> node2){
        if (edge.getNode1().equals(node1) || edge.getNode2().equals(node1)) {
            if (edge.getNode1().equals(node2) || edge.getNode2().equals(node2)) {
                return  !edge.getNode1().equals(edge.getNode2());
            }
        }
        return false;
    }
    
}
