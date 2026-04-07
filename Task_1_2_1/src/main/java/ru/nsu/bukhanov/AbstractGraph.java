package ru.nsu.bukhanov;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractGraph implements Graph {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String v : getVertices()) {
            sb.append(v)
              .append(" -> ")
              .append(getNeighbors(v))
              .append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;
        Graph other = (Graph) o;

        Set<String> vertices = new LinkedHashSet<>(getVertices());
        if (!vertices.equals(other.getVertices())) {
            return false;
        }

        for (String v : vertices) {
            Set<String> n1 = new LinkedHashSet<>(getNeighbors(v));
            Set<String> n2 = new LinkedHashSet<>(other.getNeighbors(v));
            if (!n1.equals(n2)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (String v : getVertices()) {
            result = 31 * result + v.hashCode();
            result = 31 * result + new LinkedHashSet<>(getNeighbors(v)).hashCode();
        }
        return result;
    }
}
