/**
 * 
 */
package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementazione di una coda con priorità tramite heap binario. Gli oggetti
 * inseriti in coda implementano l'interface PriorityQueueElement che permette
 * di gestire la priorità e una handle dell'elemento. La handle è fondamentale
 * per realizzare in tempo logaritmico l'operazione di decreasePriority che,
 * senza la handle, dovrebbe cercare l'elemento all'interno dello heap e poi
 * aggiornare la sua posizione. Nel caso di heap binario rappresentato con una
 * ArrayList la handle è semplicemente l'indice dove si trova l'elemento
 * nell'ArrayList. Tale campo naturalmente va tenuto aggiornato se l'elemento
 * viene spostato in un'altra posizione.
 * 
 * @author Template: Luca Tesei
 * 
 * @param <E>
 *                il tipo degli elementi che vengono inseriti in coda.
 *
 */
public class BinaryHeapMinPriorityQueue {

    /*
     * ArrayList per la rappresentazione dello heap. Vengono usate tutte le
     * posizioni (la radice dello heap è quindi in posizione 0).
     */
    private ArrayList<PriorityQueueElement> heap;

    

    /**
     * Crea una coda con priorità vuota.
     * 
     */
    public 
    BinaryHeapMinPriorityQueue() {
        
    	this.heap = new ArrayList<>();
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the heap. The handle of the element will also be set
     * accordingly.
     * 
     * @param element
     *                    the new element to add
     * @throws NullPointerException
     *                                  if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        
    	if (element == null) {
            throw new NullPointerException();
        }
        heap.add(element);
        element.setHandle(heap.size());
        minHeap();
    }

    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the heap.
     * 
     * @return the current minimum element of this min-priority queue
     * 
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {
        
    	// Heap vuoto
        if (this.heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        //essendo un min heap restituisco il primo elemento
        return heap.get(0);
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * ternary heap will be updated accordingly.
     * 
     * @return the current minimum element
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        
    	if (this.heap.isEmpty()) {
            throw new NoSuchElementException();
        }

        PriorityQueueElement risultato = heap.remove(0);
        //rifaccio il minheap e poi restituisco il risultato
        minHeap();
        return risultato;
    }

    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     * 
     * @param element
     *                        the element whose priority will be decreased, it
     *                        must currently be inside this min-priority queue
     * @param newPriority
     *                        the new priority to assign to the element
     * 
     * @throws NoSuchElementException
     *                                      if the element is not currently
     *                                      present in this min-priority queue
     * @throws IllegalArgumentException
     *                                      if the specified newPriority is not
     *                                      strictly less than the current
     *                                      priority of the element
     */
    public void decreasePriority(PriorityQueueElement element,
            double newPriority) {
        
    	if (!heap.contains(element)) {
            throw new NoSuchElementException();
        }
        if (newPriority >= heap.get(element.getHandle()).getPriority()) {
            throw new IllegalArgumentException();
        }

        // Aggiorno il nuovo valore e rifaccio il minHeap
        heap.get(heap.indexOf(element)).setPriority(newPriority);
        minHeap();

    }

    /**
     * Determines if this priority queue is empty.
     * 
     * @return true if this priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        
        return heap.isEmpty();
    }

    /**
     * Return the current size of this queue.
     * 
     * @return the number of elements currently in this queue.
     */
    public int size() {
        
        return this.heap.size();
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        this.heap.clear();
    }

    // metodi inseriti per l'implementazione
    
 // Processo per riordinare gli elementi all'interno dell'albero
    private void minHeap() {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    // Restituisce l'indice del figlio sinistro
    private int figlioSinistro(int i) {
        return (i * 2) + 1;
    }
    // Restituisce l'indice del figlio destro
    private int figlioDestro(int i) {
        return (i * 2) + 2;
    }

    // Implementazione di heapSort
    private void heapify(int i) {
        int sinistra = figlioSinistro(i);
        int destra = figlioDestro(i);
         
        //variabile di appoggio per il min
        PriorityQueueElement min = heap.get(i);
        if (heap.size() > sinistra) {
            if (heap.get(sinistra).getPriority() < min.getPriority()) {
                min = heap.get(sinistra);
            }
        }
        if (heap.size() > destra) {
            if (heap.get(destra).getPriority() < min.getPriority()) {
                min = heap.get(destra);
            }
        }
        if (heap.get(i).getPriority() != min.getPriority()) {
            PriorityQueueElement tmp = heap.get(i);
            int lastIndexOf = heap.lastIndexOf(min);
            heap.set(i, min);
            heap.set(lastIndexOf, tmp);
            heapify(heap.indexOf(min));
        }
    }

    /**
     * Controlla se un elemento � contenuto nella lista
     * @param element
     *              L'elemento che si vuole sapere la presenza
     * @return  true se l'elemento � contenuto nella lista, false altrimenti
     */
    public boolean contains(PriorityQueueElement element){
        for(PriorityQueueElement el : getBinaryHeap() ){
            if(el.equals(element)){
                return true;
            }
        }
        return false;
    }

    protected ArrayList<PriorityQueueElement> getBinaryHeap() {
        return this.heap;
    }

    
}
