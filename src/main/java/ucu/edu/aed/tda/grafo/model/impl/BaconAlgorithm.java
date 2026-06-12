package ucu.edu.aed.tda.grafo.model.impl;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BaconAlgorithm {

    public <V, D> int numBacon(IGraph<V, D> grafo, V actor, V kevinBacon) {
        Queue<V> verticesPendientes = new LinkedList<>();
        // Usamos el Map para guardar distancias y también como registro de visitados
        Map<V, Integer> distancias = new HashMap<>();

        // Buscamos el vértice origen
        V actual = grafo.buscarVertice(grafo.construirComparable(actor));

        // Si el actor no existe en el grafo retornamos -1
        if (actual == null) return -1;

        // Agregamos el actor a la cola y al Map con distancia 0
        verticesPendientes.add(actual);
        distancias.put(actual, 0);

        while (!verticesPendientes.isEmpty()){
            // Sacamos el primer vértice pendiente
            V vertice = verticesPendientes.poll();

            // Si encontramos a Kevin Bacon retornamos su distancia al actor
            if (vertice.equals(kevinBacon)) {
                return distancias.get(vertice);
            }

            // Recorremos todas las aristas del vértice actual
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(vertice))){
                // Si el sucesor no fue visitado lo agregamos con distancia + 1
                if (!distancias.containsKey(arista.target())) {
                    distancias.put(arista.target(), distancias.get(vertice) + 1);
                    verticesPendientes.add(arista.target());
                }
            }
        }

        // Si terminó el BFS sin encontrar a Kevin Bacon no hay conexión
        return -1;
    }

}
