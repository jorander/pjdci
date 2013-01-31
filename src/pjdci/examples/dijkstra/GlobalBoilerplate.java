package pjdci.examples.dijkstra;

import pjdci.examples.dijkstra.data.Node;

public class GlobalBoilerplate {
    public static class Pair{
        public final Node n1;
        public final Node n2;

        public Pair(Node n1, Node n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            return n1.equals(pair.n1) && n2.equals(pair.n2);
        }

        @Override
        public int hashCode() {
            int result = n1 != null ? n1.hashCode() : 0;
            result = 31 * result + (n2 != null ? n2.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "n1=" + n1 +
                    ", n2=" + n2 +
                    '}';
        }
    }

    public static final int INFINITY = Integer.MAX_VALUE / 4;
}
