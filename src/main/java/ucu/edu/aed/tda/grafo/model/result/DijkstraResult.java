package ucu.edu.aed.tda.grafo.model.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DijkstraResult<V> implements IDijkstraResult<V> {
    private Map<V, Double> distancias;
    private Map<V, V> predecesores;
    private V origen;

    public DijkstraResult (Map<V, Double> distancias, Map<V, V> predecesores, V origen){
        this.distancias = distancias;
        this.predecesores = predecesores;
        this.origen = origen;
    }

    @Override
    public double getCost(V otherVertex) {
        return distancias.get(otherVertex);
    }

    @Override
    public List<V> getPath(V otherVertex) {
        Stack<V> pila = new Stack<>();
        List<V> camino = new ArrayList<>();

        V actual = otherVertex;
        while(!actual.equals(origen)){
            pila.push(actual);
            actual = predecesores.get(actual);
        }
        pila.push(origen);

        while (!pila.isEmpty()){
            camino.add(pila.pop());
        }

        return camino;
    }
}
