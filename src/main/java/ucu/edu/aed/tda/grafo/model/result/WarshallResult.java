package ucu.edu.aed.tda.grafo.model.result;

import java.util.List;
import java.util.Map;

public class WarshallResult<V> implements IFloydWarshallResult<V> {
    private boolean[][] alcanzable;
    private Map<V, Integer> indices;

    public WarshallResult(boolean[][] alcanzable, Map<V, Integer> indices){
        this.alcanzable = alcanzable;
        this.indices = indices;
    }

    @Override
    public List getPath(V source, V target) {
        return List.of();
    }

    @Override
    public double getCost(V source, V target) {
        return 0;
    }

    @Override
    public boolean connected(V source, V target) {
        return alcanzable[indices.get(source)][indices.get(target)];
    }
}
