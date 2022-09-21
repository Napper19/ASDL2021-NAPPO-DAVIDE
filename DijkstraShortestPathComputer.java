package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gli oggetti di questa classe sono calcolatori di cammini minimi con sorgente
 * singola su un certo grafo orientato e pesato dato. Il grafo su cui lavorare
 * deve essere passato quando l'oggetto calcolatore viene costruito e non puÃ²
 * contenere archi con pesi negativi. Il calcolatore implementa il classico
 * algoritmo di Dijkstra per i cammini minimi con sorgente singola utilizzando
 * una coda con prioritÃ  che estrae l'elemento con prioritÃ  minima e aggiorna le
 * prioritÃ  con l'operazione decreasePriority in tempo logaritmico (coda
 * realizzata con uno heap binario). In questo caso il tempo di esecuzione
 * dell'algoritmo di Dijkstra Ã¨ {@code O(n log m)} dove {@code n} Ã¨ il numero di
 * nodi del grafo e {@code m} Ã¨ il numero di archi.
 * 
 * @author Template: Luca Tesei
 *
 * @param <L>
 *                il tipo delle etichette dei nodi del grafo
 */
public class DijkstraShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {

    
    private GraphNode<L> source;
    private final Graph<L> grafo;
    private boolean isComputed = false;

    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * diretto e pesato privo di pesi negativi.
     * 
     * @param graph
     *                  il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException
     *                                      se il grafo passato Ã¨ nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato Ã¨ vuoto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non Ã¨ orientato
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non Ã¨ pesato,
     *                                      cioÃ¨ esiste almeno un arco il cui
     *                                      peso Ã¨ {@code Double.NaN}
     * @throws IllegalArgumentException
     *                                      se il grafo passato contiene almeno
     *                                      un peso negativo
     */
    public DijkstraShortestPathComputer(Graph<L> graph) {
    	 if(graph == null){
             throw new NullPointerException("Il grafo passato è nullo");
         }
         if(graph.isEmpty()){
             throw new IllegalArgumentException("Il grafo passato è vuoto");
         }
         if(!graph.isDirected()){
             throw new IllegalArgumentException("Il grafo passato non è orientato");
         }
         for (GraphEdge<L> edge : graph.getEdges()) {
             if (!edge.hasWeight() || edge.getWeight() < 0) {
                 throw new IllegalArgumentException("Invalid Graph passed");
             }
         }
         this.grafo = graph;
         isComputed=false;
         source=null;
         
    }
    
    
    /**
     * Inizializza le informazioni necessarie associate ai nodi del grafo
     * associato a questo calcolatore ed esegue un algoritmo per il calcolo dei
     * cammini minimi a partire da una sorgente data.
     *
     * @param sourceNode
     *                       il nodo sorgente da cui calcolare i cammini minimi
     *                       verso tutti gli altri nodi del grafo
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     *
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste nel
     *                                      grafo associato a questo calcolatore
     * 
     *                                      
     */
    
    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        
    	if(sourceNode == null){
            throw new NullPointerException("Il nodo passato è nullo");
        }
        if(!this.grafo.containsNode(sourceNode)){
            throw new IllegalArgumentException("Il nodo passato non è contenuto nel grafo");
        }
        //inizializzazione
        BinaryHeapMinPriorityQueue queue = new BinaryHeapMinPriorityQueue();
        for (GraphNode<L> node : grafo.getNodes()) {
            if (sourceNode.equals(node)) {
                node.setFloatingPointDistance(0.0);
                queue.insert(node);
            } else {
            	//distanza iniziale sconosciuta
            	//Imposto tutte le distanze dei nodi con un valore che non potrà mai assumere
                node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
            }
        }
        while (!queue.isEmpty()) {
            
            @SuppressWarnings("unchecked")
			GraphNode<L> nodeCurrent = (GraphNode<L>) queue.extractMinimum();
            //per ogni arco che fa parte degli archi connessi al nodo corrente
            for (GraphEdge<L> edge : grafo.getEdgesOf(nodeCurrent)) {
            	//pongo il valore della distanza = distanza nodo corrente + peso dell'arco
                double distance = nodeCurrent.getFloatingPointDistance() + edge.getWeight();
                //relax
                if (distance < edge.getNode2().getFloatingPointDistance()) {
                    edge.getNode2().setFloatingPointDistance(distance);
                    edge.getNode2().setPrevious(nodeCurrent);
                    queue.insert(edge.getNode2());
                }
            }
        }
        this.source = sourceNode;
        this.isComputed = true;

    }
    /**
    * Determina se è stata invocata almeno una volta la procedura di calcolo
    * dei cammini minimi a partire da un certo nodo sorgente specificato.
    *
    * @return true, se i cammini minimi da un certo nodo sorgente sono stati
    *         calcolati almeno una volta da questo calcolatore
    */
    @Override
    public boolean isComputed() {
        
        return isComputed;
    }
    
    
    /**
     * Restituisce il nodo sorgente specificato nell'ultima chiamata effettuata
     * a {@code computeShortestPathsFrom(GraphNode<L>)}.
     *
     * @return il nodo sorgente specificato nell'ultimo calcolo dei cammini
     *         minimi effettuato
     *
     * @throws IllegalStateException
     *                                   se non è stato eseguito nemmeno una
     *                                   volta il calcolo dei cammini minimi a
     *                                   partire da un nodo sorgente
     */
    @Override
    public GraphNode<L> getLastSource() {
        
    	 if(this.source == null){
             throw new IllegalStateException("Non è mai stato eseguito questo calcolo");
         }
         return this.source;
    }

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     *
     * @return il grafo su cui opera questo calcolatore
     */
    @Override
    public Graph<L> getGraph() {
        
        return grafo;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        
    	 // In caso di parametro null
        if(targetNode == null){
            throw new NullPointerException("Parametro null!");
        }
        // Nodo non appartenente al grafo
        if(!grafo.containsNode(targetNode)){
            throw new IllegalArgumentException("Il nodo non esiste!");
        }
        // Se il grafo non è stato computato
        if(!isComputed()){
            throw new IllegalStateException("Grafo non computato!");
        }
        // Se il nodo non può essere raggiunto dalla sorgente e non è la sorgente
        if(targetNode!=getLastSource()&&targetNode.getPrevious()==null){
            return null;
        }
        List<GraphEdge<L>> shortestPath = new ArrayList<>();
        // Se il nodo in riferimento ha un nodo precedente
        if (targetNode.getPrevious() != null) {
            // Finché ha un precedente
            while (targetNode.getPrevious() != null) {
                // Aggiungo l'arco che lo collega al precedente
            	shortestPath.add(grafo.getEdge(targetNode.getPrevious(), targetNode));
                // Il targetNode diventa il suo previous e continuo finché non ne ha più
                targetNode = targetNode.getPrevious();
            }
        }
        /*
         * sono dovuto partire dal nodo di arrivo ed andare a ritroso fino al
           nodo di partenza attraverso il metodo getPrevious() dei nodi, aggiungere ogni arco
           attraversato in una List ed una volta arrivato al nodo di partenza fare il reverse() della
           lista in modo da avere il percorso in ordine
         *
         */
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    
}
