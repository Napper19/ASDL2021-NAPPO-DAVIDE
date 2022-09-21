package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione dell'algoritmo di Floyd-Warshall per il calcolo di cammini
 * minimi tra tutte le coppie di nodi in un grafo pesato che puÃ² contenere anche
 * pesi negativi, ma non cicli di peso negativo.
 * 
 * @author Template: Luca Tesei
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class FloydWarshallAllPairsShortestPathComputer<L> {

    /*
     * Il grafo su cui opera questo calcolatore.
     */
    private Graph<L> graph;

    /*
     * Matrice dei costi dei cammini minimi. L'elemento in posizione i,j
     * corrisponde al costo di un cammino minimo tra il nodo i e il nodo j, dove
     * i e j sono gli interi associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private double[][] costMatrix;

    /*
     * Matrice dei predecessori. L'elemento in posizione i,j Ã¨ -1 se non esiste
     * nessun cammino tra i e j oppure corrisponde all'indice di un nodo che
     * precede il nodo j in un qualche cammino minimo da i a j. Si intende che i
     * e j sono gli indici associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private int[][] predecessorMatrix;
    
    //dice se l'algoritmo è stato applicato già oppure no
    private boolean solved;

    /**
     * Crea un calcolatore di cammini minimi fra tutte le coppie di nodi per un
     * grafo orientato e pesato. Non esegue il calcolo, che viene eseguito
     * invocando successivamente il metodo computeShortestPaths().
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
     */
    public FloydWarshallAllPairsShortestPathComputer(Graph<L> g) {
    	if(g==null) {
    		throw new NullPointerException();
    	}
    	if(g.isEmpty()) {
    		throw new IllegalArgumentException();
    	}
    	if(!g.isDirected()) {
    		throw new IllegalArgumentException();
    	}
    	 for (GraphEdge<L> e : g.getEdges()) {
             if (!e.hasWeight()) {
                 throw new IllegalArgumentException("L'Arco non è pesato!");
             }
               }
        
    	  this.graph = g;
          this.costMatrix = new double[g.nodeCount()][g.nodeCount()];
          

          for (int i = 0; i < g.nodeCount(); i++) {
              for (int j = 0; j < g.nodeCount(); j++) {
            	// Se l'arco è diverso da null
                  if (g.getEdgeAtNodeIndexes(i, j) != null) {
                      // Assegno alla cella il suo peso
                      costMatrix[i][j] = g.getEdgeAtNodeIndexes(i, j).getWeight();
                  } else if (i == j) {
                      
                	  //Nella matrice dei costi sono posti a zero le celle della diagonale principale d(i,i)=0 cioè node1==node2
                      costMatrix[i][j] = 0;
                  } else {
                      // Le celle non associate a un arco sono di default pari a +∞.
                      costMatrix[i][j] = Double.MAX_VALUE;
              }
              }}
          this.predecessorMatrix = new int[g.nodeCount()][g.nodeCount()];
          for (int i = 0; i < predecessorMatrix.length; i++) {
              for (int j = 0; j < predecessorMatrix.length; j++) {
                  // Se l'arco è diverso da null
                  if (graph.getEdgeAtNodeIndexes(i, j) != null) {
                      // Inserisco nella cella l'indice di i che è il nodo precedente
                	  
                      predecessorMatrix[i][j] = i;
                  } else {
                      // Altrimenti
                      predecessorMatrix[i][j] = -1;
                  }
              }
          }
          //siamo ancora in una fase di default,non di elaborazione
          this.solved = false;
    }

    /**
     * Esegue il calcolo per la matrice dei costi dei cammini minimi e per la
     * matrice dei predecessori cosÃ¬ come specificato dall'algoritmo di
     * Floyd-Warshall.
     * 
     * @throws IllegalStateException
     *                                   se il calcolo non puÃ² essere effettuato
     *                                   per via dei valori dei pesi del grafo,
     *                                   ad esempio se il grafo contiene cicli
     *                                   di peso negativo.
     */
    public void computeShortestPaths() {
        
    	
    	for(int h=0; h<costMatrix.length;h++) {
    	for(int i =0;i<costMatrix.length;i++) {
    		for(int j=0;j<costMatrix.length;j++) {
    			//confronto la distanza attuale rispetto alla nuova
    			//se è maggiore rispetto alla nuova
    			if(costMatrix[i][j]>costMatrix[i][h]+ costMatrix[h][j]) {
    			//allora metto il nuovo valore della distanza
    			costMatrix[i][j] = costMatrix[i][h]+costMatrix[h][j];
    			//e aggiorno l'indice del nodo precedente
    			predecessorMatrix[i][j]=predecessorMatrix[h][j];
    			}
    			//se troviamo un ciclo negativo
    			if(costMatrix[h][h]<0) {
    				throw new IllegalStateException("se il calcolo non puÃ² essere effettuato\n"
    						+ "     *                                   per via dei valori dei pesi del grafo,\n"
    						+ "     *                                   ad esempio se il grafo contiene cicli\n"
    						+ "     *                                   di peso negativo");
    			}
    		}
    	}	
    	}
    	solved=true;
    }

    /**
     * Determina se Ã¨ stata invocatala procedura di calcolo dei cammini minimi.
     * 
     * @return true se i cammini minimi sono stati calcolati, false altrimenti
     */
    public boolean isComputed() {
       
        return solved;
    }

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     * 
     * @return il grafo su cui opera questo calcolatore
     */
    public Graph<L> getGraph() {
        return this.graph;
    }

    /**
     * Restituisce una lista di archi da un nodo sorgente a un nodo target. Tale
     * lista corrisponde a un cammino minimo tra i due nodi nel grafo gestito da
     * questo calcolatore.
     * 
     * @param sourceNode
     *                       il nodo di partenza del cammino minimo da
     *                       restituire
     * @param targetNode
     *                       il nodo di arrivo del cammino minimo da restituire
     * @return la lista di archi corrispondente al cammino minimo; la lista Ã¨
     *         vuota se il nodo sorgente Ã¨ il nodo target. Viene restituito
     *         {@code null} se il nodo target non Ã¨ raggiungibile dal nodo
     *         sorgente
     * 
     * @throws NullPointerException
     *                                      se almeno uno dei nodi passati Ã¨
     *                                      nullo
     * 
     * @throws IllegalArgumentException
     *                                      se almeno uno dei nodi passati non
     *                                      esiste
     * 
     * @throws IllegalStateException
     *                                      se non Ã¨ stato eseguito il calcolo
     *                                      dei cammini minimi
     * 
     * 
     */
    public List<GraphEdge<L>> getShortestPath(GraphNode<L> sourceNode,
            GraphNode<L> targetNode) {
        
    	if (sourceNode == null || targetNode == null) {
            throw new NullPointerException();}
        if (!graph.containsNode(targetNode) || !graph.containsNode(sourceNode)) {
            throw new IllegalArgumentException();}
        if (!isComputed()) {
            throw new IllegalStateException();}
        
        // i=indice del nodo di partenza
        int i = graph.getNodeIndexOf(sourceNode.getLabel());
        // j= indice del nodo di destinazione
        int j = graph.getNodeIndexOf(targetNode.getLabel());
        // target = indice del nodo precedente
        int target = predecessorMatrix[i][j];
        //  archi che formano il percorso
        List<GraphEdge<L>> archiPercorso = new ArrayList<>();
        // Il precedente del targetNode
        GraphNode<L> previousOfTargetNode = graph.getNodeAtIndex(target);
        // Fino a quando non arrivo al nodo di partenza
        while(!previousOfTargetNode.equals(sourceNode)){
            // Aggiungo l'arco all'insieme degli archi
            archiPercorso.add(graph.getEdge(previousOfTargetNode, targetNode));
            // Aggiorno l'indice del nodo precedente
            target = predecessorMatrix[i][graph.getNodeIndexOf(previousOfTargetNode.getLabel())];
            // Scorro il nodo, andando indietro
            targetNode = previousOfTargetNode;
            // Aggiorno con il nuovo precedente
            previousOfTargetNode = graph.getNodeAtIndex(target);
        }
        // Aggiungo l'ultimo arco che collega il sourceNode al nodo successivo
        archiPercorso.add(graph.getEdge(sourceNode, targetNode));
        // metto in ordine gli archi con il reverse
        Collections.reverse(archiPercorso);
        return archiPercorso;
          }

    /**
     * Restituisce il costo di un cammino minimo da un nodo sorgente a un nodo
     * target.
     * 
     * @param sourceNode
     *                       il nodo di partenza del cammino minimo
     * @param targetNode
     *                       il nodo di arrivo del cammino minimo
     * @return il costo di un cammino minimo tra il nodo sorgente e il nodo
     *         target. Viene restituito {@code Double.POSITIVE_INFINITY} se il
     *         nodo target non Ã¨ raggiungibile dal nodo sorgente, mentre viene
     *         restituito zero se il nodo sorgente Ã¨ il nodo target.
     * 
     * @throws NullPointerException
     *                                      se almeno uno dei nodi passati Ã¨
     *                                      nullo
     * 
     * @throws IllegalArgumentException
     *                                      se almeno uno dei nodi passati non
     *                                      esiste
     * 
     * @throws IllegalStateException
     *                                      se non Ã¨ stato eseguito il calcolo
     *                                      dei cammini minimi
     * 
     * 
     */
    public double getShortestPathCost(GraphNode<L> sourceNode,
            GraphNode<L> targetNode) {
        
    	if (sourceNode == null || targetNode == null)
            throw new NullPointerException();
        if (!graph.containsNode(targetNode) || !graph.containsNode(sourceNode))
            throw new IllegalArgumentException();
        if (!isComputed())
            throw new IllegalStateException();
        return costMatrix[graph.getNodeIndexOf(sourceNode.getLabel())][graph.getNodeIndexOf(targetNode.getLabel())];
    }

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     * 
     * @param path
     *                 un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException
     *                                  se il cammino passato Ã¨ nullo
     */
    public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Richiesta di stampare un path nullo");
        if (path.isEmpty())
            return "[ ]";
        // Costruisco la stringa
        StringBuffer s = new StringBuffer();
        s.append("[ " + path.get(0).getNode1().toString());
        for (int i = 0; i < path.size(); i++)
            s.append(" -- " + path.get(i).getWeight() + " --> "
                    + path.get(i).getNode2().toString());
        s.append(" ]");
        return s.toString();
    }

    /**
     * @return the costMatrix
     */
    public double[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * @return the predecessorMatrix
     */
    public int[][] getPredecessorMatrix() {
        return predecessorMatrix;
    }

    // TODO inserire eventuali metodi privati per fini di implementazione
}
