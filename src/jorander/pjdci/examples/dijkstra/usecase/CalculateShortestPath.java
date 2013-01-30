package jorander.pjdci.examples.dijkstra.usecase;

import jorander.pjdci.core.Role;
import jorander.pjdci.examples.dijkstra.data.ManhattanGeometry;
import jorander.pjdci.examples.dijkstra.data.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jorander.pjdci.examples.dijkstra.GlobalBoilerplate.INFINITY;
import static jorander.pjdci.examples.dijkstra.GlobalBoilerplate.Pair;

/**
 * The DCI context of the well known Dijkstra Manhattan example. A more involved
 * example used to demonstrate the capabilities (and weaknesses) of a DCI implementation.
 *
 * This implementation is based on Marc Grue's Scala-based solution found on
 * http://fullOO.info (2013-01-25). The solution is modified to be compiled in Java
 * and minor things are changed to increase clarity, but the algorithm and the
 * associated test case are the same.
 *
 * The money transfer example contains all basic comments to the syntax and conventions
 * used with this DCI-implementation. Please, read that example first since comments are
 * not duplicated here.
 */
@SuppressWarnings({"EqualsBetweenInconvertibleTypes", "SuspiciousMethodCalls"})  // Suppression of IntelliJ's warnings for collections
                                                                                 // being accessed by role and role player identifiers
                                                                                 // interchangeably. This is fine due to implementation
                                                                                 // found in classes in the core-package.
public class CalculateShortestPath {
    //Role identifiers
    private final Node Destination;
    private final CurrentIntersection CurrentIntersection;
    private final CartographyMap CartographyMap;
    private final Neighbor EastNeighbor;
    private final Neighbor SouthNeighbor;

    //Context state
    // In Java Collections the type declaration of the key could be either a specific type of a role or a role player
    // or left to "Object" to allow for objects of both types to be inserted as keys. In both cases role and role player
    // objects are seen as equal if they reference equal role player objects. So both identifiers can be used for
    // retrieval. The trade-off selecting the approach in each case depends on if the clarity of the implementation
    // benefits from Java's type checks or not. In this example I've used both approaches for different collections.
    private final Map<Object, Node> pathTo;
    private final Map<Node, Boolean> unvisited;
    private final Map<Object, Integer> tentDist;
    private final List<Node> path;

    public CalculateShortestPath(Node originNode, Node targetNode, ManhattanGeometry geometries) {
        this(originNode, targetNode, geometries, new ArrayList<Node>(), new HashMap<Node, Boolean>(), new HashMap<Object, Node>(), new HashMap<Object, Integer>());

        for (Node node : CartographyMap.nodes()) {
            if (CurrentIntersection.equals(node)) {
                tentDist.put(CurrentIntersection, 0);
            } else {
                unvisited.put(node, true);
                tentDist.put(node, INFINITY);
            }
        }
    }

    private CalculateShortestPath(Node originNode, Node targetNode, ManhattanGeometry geometries, List<Node> path, Map<Node, Boolean> unvisited, Map<Object, Node> pathTo, Map<Object, Integer> tentDist) {
        Destination = targetNode;
        CurrentIntersection = new CurrentIntersection(originNode);
        CartographyMap = new CartographyMap(geometries);
        EastNeighbor = CartographyMap.eastNeighborOf(originNode);
        SouthNeighbor = CartographyMap.southNeighborOf(originNode);
        this.unvisited = unvisited;
        this.path = path;
        this.pathTo = pathTo;
        this.tentDist = tentDist;
    }

    // Context entry point
    public void execute() {
        for (Neighbor Neighbor : CurrentIntersection.unvisitedNeighbors()) {
            final int distanceCoveredSoFar = tentDist.get(CurrentIntersection);
            final int distanceToNeighbor = CartographyMap.distanceFromCurrentIntersection(Neighbor);
            final boolean relableNodeAs = Neighbor.relableNodeAs(distanceCoveredSoFar + distanceToNeighbor);
            if (relableNodeAs) pathTo.put(Neighbor, CurrentIntersection.asNode()); // The .asNode() call here is to convert from the type of the role wrapper object to the role player type. Needed to work around Java's type checks in this case.
        }
        unvisited.remove(CurrentIntersection);

        if (unvisited.size() > 0) {
            final Node selection = nearestUnvisitedNodeToTarget();
            //Recursive call to context
            new CalculateShortestPath(selection, Destination, CartographyMap.asGeometries(), path, unvisited, pathTo, tentDist).execute();
        }
    }

    // Context private support method.
    private Node nearestUnvisitedNodeToTarget() {
        int minimalDistance = INFINITY;
        Node selectedNode = null;
        for (Node intersection : unvisited.keySet()) {
            if (unvisited.get(intersection)) {
                if (tentDist.get(intersection) <= minimalDistance) {
                    minimalDistance = tentDist.get(intersection);
                    selectedNode = intersection;
                }
            }
        }
        return selectedNode;
    }

    // Context entry point
    public List<Node> getPath() {
        return walkBackwards(new ArrayList<Node>(), Destination);
    }

    private List<Node> walkBackwards(List<Node> pathSoFar, Node node) {
        final int FIRST_IN_LIST = 0;
        pathSoFar.add(FIRST_IN_LIST, node);
        Node previousNode = pathTo.get(node);
        if (previousNode != null) {
            return walkBackwards(pathSoFar, previousNode);
        }
        return pathSoFar;
    }

    // Role definitions
    private class CurrentIntersection extends Role<Node> {

        protected CurrentIntersection(Node rolePlayer) {
            super(rolePlayer);
        }

        // Role method implementation
        public List<Neighbor> unvisitedNeighbors() {
            List<Neighbor> unvisitedNeighbors = new ArrayList<>();
            if (EastNeighbor != null) unvisitedNeighbors.add(EastNeighbor);
            if (SouthNeighbor != null) unvisitedNeighbors.add(SouthNeighbor);
            return unvisitedNeighbors;
        }

        // Conversion method to get to the type of the role player. Sometimes necessary to work around the Java type checks.
        public Node asNode() {
            return rolePlayer;
        }
    }

    private class CartographyMap extends Role<ManhattanGeometry> {

        protected CartographyMap(ManhattanGeometry rolePlayer) {
            super(rolePlayer);
        }

        public Neighbor eastNeighborOf(Node node) {
            return asNeighbor(rolePlayer.eastNeighborOf(node));
        }

        public Neighbor southNeighborOf(Node node) {
            return asNeighbor(rolePlayer.southNeighborOf(node));
        }

        private Neighbor asNeighbor(Node node) {
            return node != null ? new Neighbor(node) : null;
        }

        public int distanceFromCurrentIntersection(Neighbor neighbor) {
            return rolePlayer.distances.get(new Pair(CurrentIntersection.asNode(), neighbor.asNode()));
        }

        public ManhattanGeometry asGeometries() {
            return rolePlayer;
        }

        // Simple role method giving access to the role player's methods.
        // Gives cleaner code in the context implementation if it only uses methods defined by the roles
        // and no accesses the role player's methods directly.
        public List<Node> nodes() {
            return rolePlayer.nodes;
        }
    }

    private class Neighbor extends Role<Node> {

        protected Neighbor(Node rolePlayer) {
            super(rolePlayer);
        }

        public Node asNode() {
            return rolePlayer;
        }

        public boolean relableNodeAs(int x) {
            if (x < tentDist.get(rolePlayer)) {
                tentDist.put(rolePlayer, x);
                return true;
            } else {
                return false;
            }
        }
    }
}
