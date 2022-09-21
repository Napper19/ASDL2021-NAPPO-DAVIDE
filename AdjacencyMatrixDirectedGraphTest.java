package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class AdjacencyMatrixDirectedGraphTest {

    

    @Test
    final void testNodeCount() {

    	 Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
         assertEquals(0, grafo.nodeCount());
         grafo.addNode(new GraphNode<String>("s"));
         assertEquals(1, grafo.nodeCount());
         grafo.addNode(new GraphNode<String>("u"));
         assertEquals(2, grafo.nodeCount());
    }

    @Test
    final void testEdgeCount() {
    	Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
		GraphNode<String> ad = new GraphNode<String>("sas");
		GraphNode<String> bd = new GraphNode<String>("uw");
		GraphNode<String> af = new GraphNode<String>("sus");
		GraphNode<String> bf = new GraphNode<String>("ow");
		grafo.addNode(ad);
		grafo.addNode(bd);
		grafo.addNode(af);
		grafo.addNode(bf);
		GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
		GraphEdge<String> afbf = new GraphEdge<String>(af, bf, true);
		GraphEdge<String> afbd = new GraphEdge<String>(af, bd, true);
		grafo.addEdge(adbd);
		grafo.addEdge(afbf);
		grafo.addEdge(afbd);

		assertTrue(grafo.edgeCount() == 3);
       
    	
    }

    @Test
    final void testClear() {
    	Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        assertTrue(grafo.isEmpty());
        GraphNode<String> ns = new GraphNode<String>("s");
        grafo.addNode(ns);
        assertFalse(grafo.isEmpty());
        GraphNode<String> nu = new GraphNode<String>("u");
        grafo.addNode(nu);
        GraphEdge<String> esu = new GraphEdge<String>(ns, nu, true, 10.1);
        grafo.addEdge(esu);
        assertFalse(grafo.isEmpty());
        grafo.clear();
        assertTrue(grafo.isEmpty());
    }

    @Test
    final void testIsDirected() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        assertTrue(grafo.isDirected());
    }

    @Test
    final void testGetNodes() {
    	Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
		GraphNode<String> ad = new GraphNode<String>("a");
		GraphNode<String> bd = new GraphNode<String>("bb");
		GraphNode<String> af = new GraphNode<String>("ccc");
		grafo.addNode(ad);
		grafo.addNode(bd);
		grafo.addNode(af);
		
		assertTrue(grafo.getNodes().contains(ad));
		assertTrue(grafo.getNodes().contains(bd));
		assertTrue(grafo.getNodes().contains(af));
    }

    @Test
    final void testAddNode() {
    	 Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
         assertThrows(NullPointerException.class, () -> grafo.addNode(null));
         GraphNode<String> ns = new GraphNode<String>("s");
         GraphNode<String> nsTest = new GraphNode<String>("s");
         assertFalse(grafo.containsNode(ns));
         grafo.addNode(ns);
         assertTrue(grafo.containsNode(nsTest));
         GraphNode<String> nu = new GraphNode<String>("u");
         GraphNode<String> nuTest = new GraphNode<String>("u");
         grafo.addNode(nu);
         assertTrue(grafo.containsNode(nuTest));
    }

    @Test
    final void testRemoveNode() {
        Graph<String> ns = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(UnsupportedOperationException.class, () -> {
            ns.removeNode(null);
        });
    }

    @Test
    final void testContainsNode() {
    	Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> grafo.containsNode(null));
        GraphNode<String> ns = new GraphNode<String>("s");
        GraphNode<String> nsTest = new GraphNode<String>("s");
        assertFalse(grafo.containsNode(nsTest));
        grafo.addNode(ns);
        assertTrue(grafo.containsNode(nsTest));

    }

    @Test
    final void testGetNodeOf() {
    	Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> grafo.getNodeOf(null));
        GraphNode<String> ns = new GraphNode<String>("s");
       
        grafo.addNode(ns);
        GraphNode<String> nu = new GraphNode<String>("u");
        grafo.addNode(nu);
        GraphNode<String> node = grafo.getNodeOf("s");
        assertEquals("s", node.getLabel());
        
        node = grafo.getNodeOf("u");
        assertEquals("u", node.getLabel());
        
    }

    @Test
    final void testGetNodeIndexOf() {
    	Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        assertThrows(NullPointerException.class, () -> grafo.getNodeIndexOf(null));
        GraphNode<String> ns = new GraphNode<String>("s");
        
        grafo.addNode(ns);
        assertTrue(grafo.getNodeIndexOf("s") == 0);
        assertThrows(IllegalArgumentException.class,
                () -> grafo.getNodeIndexOf("u"));
        GraphNode<String> nu = new GraphNode<String>("u");
        grafo.addNode(nu);
        assertTrue(grafo.getNodeIndexOf("u") == 1);
        assertTrue(grafo.getNodeIndexOf("s") == 0);

    }

    @Test
    final void testGetNodeAtIndex() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> aa = new GraphNode<String>("aw");
        grafo.addNode(aa);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            grafo.getNodeAtIndex(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            grafo.getNodeAtIndex(grafo.nodeCount());
        });
        assertEquals(aa, grafo.getNodeAtIndex(aa.getHandle()));

    }

    @Test
    final void testGetEdge() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("ae");
        grafo.addNode(ad);
        grafo.addNode(bd);

        assertThrows(NullPointerException.class, () -> {
            grafo.getEdge(null, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.getEdge(new GraphNode<String>("aa"), ad);
        });
        assertEquals(null, grafo.getEdge(ad, bd));

        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        grafo.addEdge(adbd);

        assertEquals(adbd, grafo.getEdge(ad, bd));

    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
    	Graph<String> gr = new AdjacencyMatrixDirectedGraph<String>();
		GraphNode<String> ad = new GraphNode<String>("aw");
		GraphNode<String> bd = new GraphNode<String>("ael");
		gr.addNode(ad);
		gr.addNode(bd);
		
		assertThrows(IndexOutOfBoundsException.class, () -> { gr.getEdgeAtNodeIndexes(-1,ad.getHandle()); });
		assertThrows(IndexOutOfBoundsException.class, () -> { gr.getEdgeAtNodeIndexes(gr.nodeCount(),ad.getHandle()); });
		
		
		ad.setHandle(0);
		bd.setHandle(1);
		
		GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
		gr.addEdge(adbd);
		
		
		
        

    }

    @Test
    final void testGetAdjacentNodesOf() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();

        assertThrows(NullPointerException.class, () -> {
            grafo.getAdjacentNodesOf(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.getAdjacentNodesOf(new GraphNode<String>("no"));
        });

        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("aw");
        grafo.addNode(ad);
        grafo.addNode(bd);
        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        grafo.addEdge(adbd);

        assertTrue(grafo.getAdjacentNodesOf(ad).contains(bd));

    }

    @Test
    final void testGetPredecessorNodesOf() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();

        assertThrows(NullPointerException.class, () -> {
            grafo.getAdjacentNodesOf(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.getAdjacentNodesOf(new GraphNode<String>("no"));
        });

        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("aw");
        grafo.addNode(ad);
        grafo.addNode(bd);
        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        grafo.addEdge(adbd);

        assertTrue(grafo.getPredecessorNodesOf(bd).contains(ad));
    }

    @Test
    final void testGetEdges() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("sas");
        GraphNode<String> bd = new GraphNode<String>("uw");
        GraphNode<String> af = new GraphNode<String>("sus");
        GraphNode<String> bf = new GraphNode<String>("ow");
        grafo.addNode(ad);
        grafo.addNode(bd);
        grafo.addNode(af);
        grafo.addNode(bf);
        GraphEdge<String> afbf = new GraphEdge<String>(ad, bd, true);
        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        GraphEdge<String> afbd = new GraphEdge<String>(af, bd, true);
        grafo.addEdge(afbf);
        grafo.addEdge(adbd);
        grafo.addEdge(afbd);

        assertTrue(grafo.getEdges().contains(adbd));
        assertTrue(grafo.getEdges().contains(afbf));
        assertTrue(grafo.getEdges().contains(afbd));

    }

    @Test
    final void testAddEdge() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("sas");
        GraphNode<String> bd = new GraphNode<String>("uw");
        grafo.addNode(ad);
        grafo.addNode(bd);
        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        grafo.addEdge(adbd);

        assertThrows(NullPointerException.class, () -> {
            grafo.addEdge(null);
        });
        assertThrows(NullPointerException.class, () -> {
            grafo.addEdge(new GraphEdge<String>(null, null, true));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.addEdge(new GraphEdge<String>(new GraphNode<String>("abba"), ad, true));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.addEdge(new GraphEdge<String>(ad, bd, false));
        });
        assertFalse(grafo.addEdge(adbd));
        assertTrue(grafo.containsEdge(adbd));

    }

    @Test
    final void testRemoveEdge() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("sas");
        GraphNode<String> bd = new GraphNode<String>("uw");
        grafo.addNode(ad);
        grafo.addNode(bd);

        assertThrows(NullPointerException.class, () -> {
            grafo.removeEdge(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.removeEdge(new GraphEdge<String>(new GraphNode<String>("aaa"), bd, false));
        });

        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);

        assertFalse(grafo.removeEdge(adbd));

        grafo.addEdge(adbd);
        grafo.removeEdge(adbd);
        assertTrue(grafo.containsEdge(adbd));

    }

    @Test
    final void testContainsEdge() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("ew");
        grafo.addNode(ad);
        grafo.addNode(bd);

        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);

        assertFalse(grafo.containsEdge(adbd));

        grafo.addEdge(adbd);

        assertTrue(grafo.containsEdge(adbd));

        assertThrows(NullPointerException.class, () -> {
            grafo.containsEdge(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.removeEdge(new GraphEdge<String>(new GraphNode<String>("aaa"), bd, false));
        });

    }

    @Test
    final void testGetEdgesOf() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("ew");
        grafo.addNode(ad);
        grafo.addNode(bd);
        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        grafo.addEdge(adbd);

        assertThrows(NullPointerException.class, () -> { grafo.getEdgesOf(null); });
        assertThrows(IllegalArgumentException.class, () -> { grafo.getEdgesOf(new GraphNode<String>("aa")); });

        assertTrue(grafo.getEdgesOf(ad).contains(adbd));


    }

    @Test
    final void testGetIngoingEdgesOf() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("ew");
        grafo.addNode(ad);
        grafo.addNode(bd);
        GraphEdge<String> adbd = new GraphEdge<String>(ad, bd, true);
        grafo.addEdge(adbd);

        assertThrows(NullPointerException.class, () -> {
            grafo.getEdgesOf(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            grafo.getEdgesOf(new GraphNode<String>("aa"));
        });

        assertFalse(grafo.getIngoingEdgesOf(bd).contains(adbd));
    }

    @Test
    final void testAdjacencyMatrixDirectedGraph() {
        //TODO
    	 new AdjacencyMatrixDirectedGraph<>();
    }

    @Test
    final void testSize() {
        Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("ew");
        g.addNode(ad);
        g.addNode(bd);

        assertTrue(g.size() == g.edgeCount() + g.nodeCount());
    }

    @Test
    final void testIsEmpty() {
        Graph<String> grafo = new AdjacencyMatrixDirectedGraph<String>();
        assertTrue(grafo.isEmpty());

        GraphNode<String> ad = new GraphNode<String>("ew");
        grafo.addNode(ad);
        assertFalse(grafo.isEmpty());

    }

    @Test
    final void testGetDegreeOf() {
        // TODO
    	Graph<String> g = new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> ad = new GraphNode<String>("aw");
        GraphNode<String> bd = new GraphNode<String>("ew");
        g.addNode(ad);
        g.addNode(bd);
     
        
          }
    }

