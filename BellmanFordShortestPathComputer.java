package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione dell'algoritmo di Bellman-Ford per il calcolo di cammini
 * minimi a sorgente singola in un grafo pesato che può contenere anche pesi
 * negativi, ma non cicli di peso negativo.
 * 
 * @author Template: Luca Tesei
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class BellmanFordShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {

    
	private Graph<L> grafo;
    private boolean isComputed;
    private GraphNode<L> lastSource = null;
    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * orientato e pesato.
     * 
     * @param graph
     *                  il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException
     *                                      se il grafo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato è vuoto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è diretto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è pesato,
     *                                      cioè esiste almeno un arco il cui
     *                                      peso è {@code Double.NaN}.
     */
    public BellmanFordShortestPathComputer(Graph<L> graph) {
        
    	if (graph == null) {
            throw new NullPointerException();
        }
        
        if (graph.isEmpty()) {
            throw new IllegalArgumentException();
        }
        
        if (!graph.isDirected()) {
            throw new IllegalArgumentException();
        }
        
        for (GraphEdge<L> e : graph.getEdges()) {
            if (!e.hasWeight()) {
                throw new IllegalArgumentException("il grafo passato non � pesato");
            }
        }
        this.grafo = graph;
        this.isComputed = false;
        this.lastSource = null;
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
     * @throws IllegalStateException
     *                                      se il calcolo non può essere
     *                                      effettuato per via dei valori dei
     *                                      pesi del grafo, ad esempio se il
     *                                      grafo contiene cicli di peso
     *                                      negativo.
     */
    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        
    	if (sourceNode == null) {
            throw new NullPointerException();
        }
        
        if (!grafo.containsNode(sourceNode)) {
            throw new IllegalArgumentException();
        }

        initSingleSource(sourceNode);
        //processo gli archi ripetutamente
        for (int i = 0; i < grafo.getNodes().size(); ++i) {
            for (GraphEdge<L> edge : grafo.getEdges()) {
                GraphNode<L> source = edge.getNode1();
                GraphNode<L> dest = edge.getNode2();
                //relax
                if (dest.getFloatingPointDistance() > source.getFloatingPointDistance() + edge.getWeight()) {
                    dest.setFloatingPointDistance(source.getFloatingPointDistance() + edge.getWeight());
                    dest.setPrevious(source);
                }
            }
        }
        // Mi accerto che non ci siano cicli negativi
        for (GraphEdge<L> e : grafo.getEdges()) {
            if (e.getNode2().getFloatingPointDistance() > e.getNode1().getFloatingPointDistance() + e.getWeight()) {
                throw new IllegalStateException(" ciclo negativo");
            }
        }
        /* Inizializzo le informazioni necessarie associate ai nodi del grafo
        associato a questo calcolatore*/
        this.lastSource = sourceNode;
        this.isComputed = true;
      }

   

	@Override
    public boolean isComputed() {
        
        return this.isComputed;
    }
	
	/**
     * Restituisce il nodo sorgente specificato nell'ultima chiamata effettuata
     * a {@code computeShortestPathsFrom(GraphNode<V>)}.
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
        
    	if (!isComputed()) {
            throw new IllegalStateException("Non � stato mai eseguito il calcolo dei cammini minimi");
        }
        return this.lastSource;
    }

    @Override
    public Graph<L> getGraph() {
       
        return this.grafo;
    }
    /**
     * Restituisce una lista di archi dal nodo sorgente dell'ultimo calcolo di
     * cammini minimi al nodo passato. Tale lista corrisponde a un cammino
     * minimo tra il nodo sorgente e il nodo target passato.
     * 
     * @param targetNode
     *                       il nodo verso cui restituire il cammino minimo
     *                       dalla sorgente
     * @return la lista di archi corrispondente al cammino minimo; la lista è
     *         vuota se il nodo passato è il nodo sorgente. Viene restituito
     *         {@code null} se il nodo passato non è raggiungibile dalla
     *         sorgente
     * 
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste
     * 
     * @throws IllegalStateException
     *                                      se non è stato eseguito nemmeno una
     *                                      volta il calcolo dei cammini minimi
     *                                      a partire da un nodo sorgente
     * 
     */
    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        
    	 if (targetNode == null) {
             throw new NullPointerException();
         }
         
         if (!grafo.containsNode(targetNode)) {
             throw new IllegalArgumentException();
         }
         
         if (!isComputed()) {
             throw new IllegalStateException("Grafo non computato");
         }

         List<GraphEdge<L>> shortestPath = new ArrayList<>();
         // Se il nodo in riferimento ha un nodo precedente
         if (targetNode.getPrevious() != null) {
             // Finch� ha un precedente
             while (targetNode.getPrevious() != null) {
                 // Aggiungo l'arco che lo collega al precedente
                 shortestPath.add(grafo.getEdge(targetNode.getPrevious(), targetNode));
                 // Il targetNode diventa il suo previous e continuo finch� non ha pi� precedenti(targetNode.getPrevious() != null)
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

    // metodi aggiunti per l'implementazione
    /*
     * il metodo inizializza le stime dei cammini minimi e i predecessori
     */
    private void initSingleSource(GraphNode<L> n) {
    	// TODO Auto-generated method stub
        if (n == null) {
            throw new NullPointerException("Parametro null!");
        }
        for (GraphNode<L> nodo : grafo.getNodes()) {
            if (!nodo.equals(n)) {
                nodo.setFloatingPointDistance(Double.MAX_VALUE);
                nodo.setPrevious(null);
            } else {
                nodo.setFloatingPointDistance(0);
            }
        }
    }

	}
    

