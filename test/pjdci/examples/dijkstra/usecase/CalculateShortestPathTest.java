package pjdci.examples.dijkstra.usecase;

import org.junit.Test;
import pjdci.examples.dijkstra.data.ManhattanGeometry;
import pjdci.examples.dijkstra.data.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static pjdci.examples.dijkstra.GlobalBoilerplate.Pair;

public class CalculateShortestPathTest {

    @Test
    public void calculatesShortestPath() {
        ManhattanGeometry geometries = new ManhattanGeometry() {

            // a - 2 - b - 3 - c
            // |       |       |
            // 1       2       1
            // |       |       |
            // d - 1 - e - 1 - f
            // |               |
            // 2               4
            // |               |
            // g - 1 - h - 2 - i

            private Node a;
            private Node b;
            private Node c;
            private Node d;
            private Node e;
            private Node f;
            private Node g;
            private Node h;
            private Node i;

            @Override
            protected List<Node> createNodes() {
                a = new Node('a');
                b = new Node('b');
                c = new Node('c');
                d = new Node('d');
                e = new Node('e');
                f = new Node('f');
                g = new Node('g');
                h = new Node('h');
                i = new Node('i');
                return new ArrayList<Node>() {
                    {
                        add(a);
                        add(b);
                        add(c);
                        add(d);
                        add(e);
                        add(f);
                        add(g);
                        add(h);
                        add(i);
                    }
                };
            }

            @Override
            protected Map<Pair, Integer> getFiniteDistances() {
                return new Hashtable<Pair, Integer>() {{
                   put(new Pair(a, b), 2);
                   put(new Pair(b, c), 3);
                   put(new Pair(c, f), 1);
                   put(new Pair(f, i), 4);
                   put(new Pair(b, e), 2);
                   put(new Pair(e, f), 1);
                   put(new Pair(a, d), 1);
                   put(new Pair(d, g), 2);
                   put(new Pair(g, h), 1);
                   put(new Pair(h, i), 2);
                   put(new Pair(d, e), 1);
                }};
            }

            @Override
            protected Map<Node, Node> createNextDownTheStreetFrom() {
                return new Hashtable<Node, Node>() {{
                    put(a, b);
                    put(b, c);
                    put(d, e);
                    put(e, f);
                    put(g, h);
                    put(h, i);
                }};
            }

            @Override
            protected Map<Node, Node> createNextAlongTheAvenueFrom() {
                return new Hashtable<Node, Node>() {{
                    put(a, d);
                    put(b, e);
                    put(c, f);
                    put(d, g);
                    put(f, i);
                }};
            }
        };

        final CalculateShortestPath instance = new CalculateShortestPath(new Node('a'), new Node('i'), geometries);
        instance.execute();

        List<Node> shortestPath = new ArrayList<Node>(){{
            // a -> d -> g -> h -> i
            add(new Node('a'));
            add(new Node('d'));
            add(new Node('g'));
            add(new Node('h'));
            add(new Node('i'));

        }};

        assertEquals("Shortest path returned", shortestPath, instance.getPath());
    }
}
