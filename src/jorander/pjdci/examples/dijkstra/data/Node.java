package jorander.pjdci.examples.dijkstra.data;

import jorander.pjdci.core.AbstractRolePlayer;

/**
 * Simple data class representing an intersection in a
 * Manhattan grid.
 */
public class Node extends AbstractRolePlayer {
    public final char name;

    public Node(char name) {
        this.name = name;
    }

    @Override
    public boolean objectEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return name == node.name;
    }

    @Override
    public int hashCode() {
        return (int) name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name=" + name +
                '}';
    }
}
