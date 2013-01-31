package pjdci.examples.dijkstra.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static pjdci.examples.dijkstra.GlobalBoilerplate.INFINITY;
import static pjdci.examples.dijkstra.GlobalBoilerplate.Pair;

public class ManhattanGeometryTest {

    public static final int DISTANCE_BETWEEN_A_AND_B = 2;
    public static final int DISTANCE_BETWEEN_A_AND_D = 1;

    @Test
    public void returnsCorrectNeighborsAndDistances() {

        // a - 2 - b
        // |
        // 1
        // |
        // d

        ManhattanGeometry instance = new ManhattanGeometry() {
            private Node a;
            private Node b;
            private Node d;


            @Override
            protected List<Node> createNodes() {
                a = new Node('a');
                b = new Node('b');
                d = new Node('d');
                return new ArrayList<Node>() {
                    {
                        add(a);
                        add(b);
                        add(d);
                    }
                };
            }

            @Override
            protected Map<Pair, Integer> getFiniteDistances() {
                return new Hashtable<Pair, Integer>() {{
                    put(new Pair(a, b), DISTANCE_BETWEEN_A_AND_B);
                    put(new Pair(a, d), DISTANCE_BETWEEN_A_AND_D);
                }};
            }

            @Override
            protected Map<Node, Node> createNextDownTheStreetFrom() {
                return new Hashtable<Node, Node>() {{
                    put(a, b);
                }};
            }

            @Override
            protected Map<Node, Node> createNextAlongTheAvenueFrom() {
                return new Hashtable<Node, Node>() {{
                    put(a, d);
                }};
            }
        };

        assertEquals("East neighbor of 'a'", new Node('b'), instance.eastNeighborOf(new Node('a')));
        assertEquals("South neighbor of 'a", new Node('d'), instance.southNeighborOf((new Node('a'))));
        assertEquals("Distance between 'a' and 'b'", DISTANCE_BETWEEN_A_AND_B, instance.distances.get(new Pair(new Node('a'), new Node('b'))).intValue());
        assertEquals("Distance between 'a' and 'd'", DISTANCE_BETWEEN_A_AND_D, instance.distances.get(new Pair(new Node('a'), new Node('d'))).intValue());
        assertEquals("Distance between 'a' and 'a'", INFINITY, instance.distances.get(new Pair(new Node('a'), new Node('a'))).intValue());
    }
}