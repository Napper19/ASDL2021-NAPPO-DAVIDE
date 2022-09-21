package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class BinaryHeapMinPriorityQueueTest {

    

    @Test
    final void testBinaryHeapMinPriorityQueue() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node0 = new GraphNode<>(13);
        PriorityQueueElement node1 = new GraphNode<>(7);
        PriorityQueueElement node2 = new GraphNode<>(8);
        PriorityQueueElement node3 = new GraphNode<>(9);
        PriorityQueueElement node4 = new GraphNode<>(20);
        PriorityQueueElement node5 = new GraphNode<>(21);
        PriorityQueueElement node6 = new GraphNode<>(44);
        PriorityQueueElement node7 = new GraphNode<>(1);
        assertTrue(heap.isEmpty());
        node0.setPriority(13);
        heap.insert(node0);
        assertFalse(heap.isEmpty());
        node1.setPriority(7);
        heap.insert(node1);
        node2.setPriority(8);
        heap.insert(node2);
        node3.setPriority(9);
        heap.insert(node3);
        node4.setPriority(20);
        heap.insert(node4);
        node5.setPriority(21);
        heap.insert(node5);
        node6.setPriority(44);
        heap.insert(node6);
        node7.setPriority(1);
        heap.insert(node7);
        heap.clear();
        assertTrue(heap.isEmpty());
    }

    @Test
    final void testInsert() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);

         // Check delle Exceptions
         assertThrows(NullPointerException.class, ()-> heap.insert(null));
         heap.insert(node0);
         assertEquals(1, heap.size());
         heap.insert(node1);
         assertEquals(2, heap.size());
         heap.insert(node2);
         assertEquals(3, heap.size());
    }

    @Test
    final void testMinimum() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);
         PriorityQueueElement node3 = new GraphNode<>(9);
         node3.setPriority(9);
         PriorityQueueElement node4 = new GraphNode<>(20);
         node4.setPriority(20);
         PriorityQueueElement node5 = new GraphNode<>(21);
         node5.setPriority(21);
         PriorityQueueElement node6 = new GraphNode<>(44);
         node6.setPriority(44);
         PriorityQueueElement node7 = new GraphNode<>(1);
         node7.setPriority(1);

         heap.insert(node0);
         assertEquals(node0, heap.minimum());
         heap.insert(node1);
         assertEquals(node1, heap.minimum());
         heap.insert(node2);
         assertEquals(node1, heap.minimum());
         heap.insert(node3);
         assertEquals(node1, heap.minimum());
         heap.insert(node4);
         assertEquals(node1, heap.minimum());
         heap.insert(node5);
         assertEquals(node1, heap.minimum());
         heap.insert(node6);
         assertEquals(node1, heap.minimum());
         heap.insert(node7);
         assertEquals(node7, heap.minimum());
    }

    @Test
    final void testExtractMinimum() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);
         PriorityQueueElement node3 = new GraphNode<>(9);
         node3.setPriority(9);
         PriorityQueueElement node4 = new GraphNode<>(20);
         node4.setPriority(20);
         PriorityQueueElement node5 = new GraphNode<>(21);
         node5.setPriority(21);
         PriorityQueueElement node6 = new GraphNode<>(44);
         node6.setPriority(44);
         PriorityQueueElement node7 = new GraphNode<>(1);
         node7.setPriority(1);

         heap.insert(node0);
         heap.insert(node1);
         heap.insert(node2);
         heap.insert(node3);
         heap.insert(node4);
         heap.insert(node5);
         heap.insert(node6);
         heap.insert(node7);
         assertEquals(node7, heap.extractMinimum());
         assertEquals(node1, heap.extractMinimum());
         assertEquals(node2, heap.extractMinimum());
         assertEquals(node3, heap.extractMinimum());
         assertEquals(node0, heap.extractMinimum());
         assertEquals(node4, heap.extractMinimum());
         assertEquals(node5, heap.extractMinimum());
         assertEquals(node6, heap.extractMinimum());
    }

    @Test
    final void testDecreasePriority() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node0 = new GraphNode<>(13);
        node0.setPriority(13);
        heap.insert(node0);
        PriorityQueueElement node1 = new GraphNode<>(7);
        node1.setPriority(7);
        heap.insert(node1);
        PriorityQueueElement node2 = new GraphNode<>(8);
        node2.setPriority(8);
        heap.insert(node2);
        PriorityQueueElement node3 = new GraphNode<>(9);
        node3.setPriority(9);
        heap.insert(node3);
        PriorityQueueElement node4 = new GraphNode<>(20);
        node4.setPriority(20);
        heap.insert(node4);
        PriorityQueueElement node5 = new GraphNode<>(21);
        node5.setPriority(21);
        heap.insert(node5);
        PriorityQueueElement node6 = new GraphNode<>(44);
        node6.setPriority(44);
        heap.insert(node6);
        PriorityQueueElement node7 = new GraphNode<>(1);
        node7.setPriority(1);
        heap.insert(node7);

        // Check delle Exceptions
        PriorityQueueElement nodeX = new GraphNode<>(111);
        
        assertThrows(IllegalArgumentException.class, ()-> heap.decreasePriority(node0, 14));

        // Essendo che decreasePriority non ha ritorno non posso fare confronti
    }

    @Test
    final void testIsEmpty() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        assertTrue(heap.isEmpty());

        PriorityQueueElement node0 = new GraphNode<>(13);
        node0.setPriority(13);
        heap.insert(node0);
        PriorityQueueElement node1 = new GraphNode<>(7);
        node1.setPriority(7);
        heap.insert(node1);
        PriorityQueueElement node2 = new GraphNode<>(8);
        node2.setPriority(8);
        heap.insert(node2);

        assertFalse(heap.isEmpty());
    }

    @Test
    final void testSize() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node0 = new GraphNode<>(13);
        node0.setPriority(13);
        heap.insert(node0);
        assertEquals(1, heap.size());
        PriorityQueueElement node1 = new GraphNode<>(7);
        node1.setPriority(7);
        heap.insert(node1);
        assertEquals(2, heap.size());
        PriorityQueueElement node2 = new GraphNode<>(8);
        node2.setPriority(8);
        heap.insert(node2);
        assertEquals(3, heap.size());
        PriorityQueueElement node3 = new GraphNode<>(9);
        node3.setPriority(9);
        heap.insert(node3);
        assertEquals(4, heap.size());
        PriorityQueueElement node4 = new GraphNode<>(20);
        node4.setPriority(20);
        heap.insert(node4);
        assertEquals(5, heap.size());
        PriorityQueueElement node5 = new GraphNode<>(21);
        node5.setPriority(21);
        heap.insert(node5);
        assertEquals(6, heap.size());
        PriorityQueueElement node6 = new GraphNode<>(44);
        node6.setPriority(44);
        heap.insert(node6);
        assertEquals(7, heap.size());
        PriorityQueueElement node7 = new GraphNode<>(1);
        node7.setPriority(1);
        heap.insert(node7);
        assertEquals(8, heap.size());
        heap.extractMinimum();
        assertEquals(7, heap.size());
        heap.extractMinimum();
        assertEquals(6, heap.size());
        heap.extractMinimum();
        assertEquals(5, heap.size());
        heap.extractMinimum();
        assertEquals(4, heap.size());
        heap.extractMinimum();
        assertEquals(3, heap.size());
        heap.extractMinimum();
        assertEquals(2, heap.size());
        heap.extractMinimum();
        assertEquals(1, heap.size());
        heap.extractMinimum();
        assertEquals(0, heap.size());
    }

    @Test
    final void testClear() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();

         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         heap.insert(node0);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         heap.insert(node1);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);
         heap.insert(node2);

         assertFalse(heap.isEmpty());
         heap.clear();
         assertTrue(heap.isEmpty());
    }

}
