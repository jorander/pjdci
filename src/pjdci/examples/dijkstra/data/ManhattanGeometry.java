package pjdci.examples.dijkstra.data;

import pjdci.core.AbstractRolePlayer;
import pjdci.examples.dijkstra.GlobalBoilerplate;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Abstract data class that enables test cases to construct
 * their specific Manhattan grids. See e.g. the
 *  <code>CalculateShortestPathTest</code>.
 */
public abstract class ManhattanGeometry extends AbstractRolePlayer {
    public final List<Node> nodes;
    public final Map<GlobalBoilerplate.Pair, Integer> distances;
    public final Map<Node, Node> nextDownTheStreetFrom;
    public final Map<Node, Node> nextAlongTheAvenueFrom;

    public ManhattanGeometry() {
        nodes = Collections.unmodifiableList(createNodes());
        distances = Collections.unmodifiableMap(createDistances());
        nextDownTheStreetFrom = Collections.unmodifiableMap(createNextDownTheStreetFrom());
        nextAlongTheAvenueFrom = Collections.unmodifiableMap(createNextAlongTheAvenueFrom());
    }

    protected abstract List<Node> createNodes();

    private Map<GlobalBoilerplate.Pair, Integer> createDistances() {
        final Map<GlobalBoilerplate.Pair, Integer> finiteDistances = getFiniteDistances();
        return new Hashtable<GlobalBoilerplate.Pair, Integer>(){{
            for(final Node outer : nodes){
                for(final Node inner : nodes){
                    final GlobalBoilerplate.Pair pairOfNodes = new GlobalBoilerplate.Pair(outer, inner);
                    if (finiteDistances.containsKey(pairOfNodes)){
                        put(pairOfNodes, finiteDistances.get(pairOfNodes));
                    }
                    else put(pairOfNodes, GlobalBoilerplate.INFINITY);
                }
            }
        }};
    }

    protected abstract Map<GlobalBoilerplate.Pair, Integer> getFiniteDistances();

    protected abstract Map<Node, Node> createNextDownTheStreetFrom();

    protected abstract Map<Node, Node> createNextAlongTheAvenueFrom();

    public Node eastNeighborOf(Node node) {
        return nextDownTheStreetFrom.get(node);
    }

    public Node southNeighborOf(Node node) {
        return nextAlongTheAvenueFrom.get(node);
    }

    @Override
    public boolean objectEquals(Object o){
        throw new UnsupportedOperationException("Not implemented");
    }
}
