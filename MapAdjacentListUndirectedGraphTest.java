package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class MapAdjacentListUndirectedGraphTest {

    

    @Test
    final void testNodeCount() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> ab = new GraphNode<String>("a");
		
		g.addNode(ab);
		assertTrue(g.nodeCount() == 1);
        
    }

    @Test
    final void testEdgeCount() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);

		assertTrue(g.edgeCount() == 1);
    }

    @Test
    final void testClear() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);
		g.clear();
		assertTrue(g.isEmpty());
    }

    @Test
    final void testIsDirected() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertFalse(g.isDirected());
    }

    @Test
    final void testGetNodes() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		assertTrue(g.getNodes().contains(a));
		assertTrue(g.getNodes().contains(b));
    }

    @Test
    final void testAddNode() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		g.addNode(a);
		g.addNode(b);
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		g.addEdge(ab);

		assertThrows(NullPointerException.class, () -> { g.addNode(null); });
		assertFalse(g.addNode(a));
		assertTrue(g.containsNode(a) && g.containsNode(b));
    }

    @Test
    final void testRemoveNode() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		assertFalse(g.removeNode(a));
		assertThrows(NullPointerException.class, () -> { g.removeNode(null); });
		g.addNode(a);
		assertTrue(g.removeNode(a));
    }

    @Test
    final void testContainsNode() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		g.addNode(a);
		assertThrows(NullPointerException.class, () -> { g.containsNode(null); });
		assertTrue(g.containsNode(a));
		assertFalse(g.containsNode(new GraphNode<String>("no")));

    }

    @Test
    final void testGetNodeOf() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		g.addNode(a);
		assertThrows(NullPointerException.class, () -> { g.getNodeOf(null); });
		assertEquals(null, g.getNodeOf("bo"));
		assertEquals(a, g.getNodeOf("a"));
		
    }

    @Test
    final void testGetNodeIndexOf() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertThrows(NullPointerException.class, () -> { g.getNodeIndexOf(null); });
		assertThrows(UnsupportedOperationException.class, () -> { g.getNodeIndexOf("b"); });
    }

    @Test
    final void testGetNodeAtIndex() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertThrows(UnsupportedOperationException.class, () -> { g.getNodeAtIndex(3); });
    }

    @Test
	final void testGetEdge() {
		Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("a");
		g.addNode(a);
		g.addNode(b);

		assertThrows(NullPointerException.class, () -> { g.getEdge(null,null); });
		assertThrows(IllegalArgumentException.class, () -> { g.getEdge(new GraphNode<String>("aaa"),a); });
		assertEquals(null,g.getEdge(a,b));


		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		g.addEdge(ab);

		assertEquals(ab, g.getEdge(a,b));

	}


    @Test
    final void testGetEdgeAtNodeIndexes() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertThrows(UnsupportedOperationException.class, () -> { g.getEdgeAtNodeIndexes(10,11); 
		});
    }

    @Test
    final void testGetAdjacentNodesOf() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertThrows(UnsupportedOperationException.class, () -> { g.getPredecessorNodesOf(null); });
		
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);
		assertTrue(g.getAdjacentNodesOf(a).contains(b));
		
    }

    @Test
    final void testGetPredecessorNodesOf() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertThrows(UnsupportedOperationException.class, () -> { g.getPredecessorNodesOf(null); });
	}

    @Test
    final void testGetEdges() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);
		

		assertTrue(g.getEdges().contains(ab));
    }

    @Test
    final void testAddEdge() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);
		assertThrows(NullPointerException.class, () -> { g.addEdge(null); });
		assertThrows(NullPointerException.class, () -> { g.addEdge(new GraphEdge<String>(null, null, true)); });
		assertThrows(IllegalArgumentException.class, () -> { g.addEdge(new GraphEdge<String>(new GraphNode<String>("abba"), a, false)); });
		assertThrows(IllegalArgumentException.class, () -> { g.addEdge(new GraphEdge<String>(a, b, true)); });
		assertFalse(g.addEdge(ab));
		assertTrue(g.containsEdge(ab));
    }

    @Test
    final void testRemoveEdge() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		assertThrows(NullPointerException.class, () -> { g.removeEdge(null); });
		assertThrows(IllegalArgumentException.class, () -> { g.removeEdge(new GraphEdge<String>(new GraphNode<String>("aa"), b, false)); });

		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);
		
		assertTrue(g.containsEdge(ab));
		g.removeEdge(ab);
		assertFalse(g.containsEdge(ab));
		
    }

    @Test
    final void testContainsEdge() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		assertFalse(g.containsEdge(ab));

		g.addEdge(ab);

		assertTrue(g.containsEdge(ab));

		assertThrows(NullPointerException.class, () -> { g.containsEdge(null); });
		assertThrows(IllegalArgumentException.class, () -> { g.removeEdge(new GraphEdge<String>(new GraphNode<String>("aaa"), b, false)); });
    }

    @Test
    final void testGetEdgesOf() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		GraphNode<String> a = new GraphNode<String>("a");
		GraphNode<String> b = new GraphNode<String>("b");
		
		g.addNode(a);
		g.addNode(b);
		
		GraphEdge<String> ab = new GraphEdge<String>(a, b, false);
		
		g.addEdge(ab);
		assertThrows(NullPointerException.class, () -> { g.getEdgesOf(null); });
		assertThrows(IllegalArgumentException.class, () -> { g.getEdgesOf(new GraphNode<String>("aa")); });

		assertTrue(g.getEdgesOf(a).contains(ab));
    }

    @Test
    final void testGetIngoingEdgesOf() {
    	Graph<String> g = new MapAdjacentListUndirectedGraph<String>();
		assertThrows(UnsupportedOperationException.class, () -> { g.getIngoingEdgesOf(null); });
    }

    @Test
    final void testMapAdjacentListUndirectedGraph() {
    	new MapAdjacentListUndirectedGraph<String>();
        
    }

}
