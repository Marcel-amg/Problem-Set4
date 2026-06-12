package ucu.edu.aed.tda.grafo.model.impl;

import ucu.edu.aed.tda.grafo.IDirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.*;

import javax.swing.*;
import java.util.*;
import java.util.function.Consumer;

public class DirectedGraphAlgorithms implements IDirectedGraphAlgorithms {



    @Override
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo) {
        // Guarda la distancia mínima conocida desde el origen hacia cada vértice
        Map<V, Double> distancias = new HashMap<>();
        // Guarda el vértice anterior en el camino mínimo hacia cada vértice
        Map<V, V> predecesores = new HashMap<>();
        // Vértices que todavía no fueron procesados
        Set<V> noVisitados = new HashSet<>();

        // Inicializamos todas las distancias en infinito y predecesores en null
        for (V vertice : grafo.vertices()){
            distancias.put(vertice, Double.MAX_VALUE);
            predecesores.put(vertice, null);
        }

        // La distancia al origen es 0 y cargamos todos los vértices como no visitados
        V origen = grafo.buscarVertice(source);
        distancias.put(origen, 0.0);
        noVisitados.addAll(grafo.vertices());

        while (!noVisitados.isEmpty()){
            // Elegimos el vértice no visitado con menor distancia conocida
            V w = null;
            for(V v : noVisitados){
                if(w == null || distancias.get(v) < distancias.get(w)){
                    w = v;
                }
            }
            // Lo removemos de noVisitados — ya fue procesado
            noVisitados.remove(w);

            // Revisamos todos los vecinos no visitados de w
            for (V v : noVisitados){
                if (grafo.existeArista(grafo.construirComparable(w), grafo.construirComparable(v))){
                    D arista = (D) grafo.obtenerArista(grafo.construirComparable(w), grafo.construirComparable(v));
                    // Calculamos la distancia pasando por w
                    double nuevaArista = distancias.get(w) + arista.getWeight();
                    // Si es menor que la distancia actual, actualizamos
                    if(nuevaArista < distancias.get(v)){
                        distancias.put(v, nuevaArista);
                        predecesores.put(v, w);
                    }
                }
            }
        }

        return new DijkstraResult<>(distancias, predecesores, origen);
    }

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo) {
        int n = grafo.cantidadDeVertices();
        // Mapeamos cada vértice a un índice numérico para poder usarlo en las matrices
        Map<V, Integer> indices = new HashMap<>();
        int x = 0;
        // Matriz de distancias NxN — guarda el costo mínimo entre cada par de vértices
        double[][] distancias = new double[n][n];
        // Matriz de predecesores NxN — guarda por dónde pasamos para llegar al mínimo
        int[][] predecesores = new int[n][n];

        for (V vertice : grafo.vertices()){
            indices.put(vertice, x);
            x++;
        }


        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                // Inicializamos en infinito — todavía no conocemos ningún camino
                distancias[i][j] = Double.MAX_VALUE;
            }
        }

        // La distancia de un vértice a sí mismo es 0
        for (int i = 0; i < n; i++){
            distancias[i][i] = 0;
        }

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                // -1 indica que no hay predecesor todavía
                predecesores[i][j] = -1;
            }
        }

        // Cargamos las aristas existentes en las matrices
        for (Edge<V, D> arista : grafo.aristas()){
            int i = indices.get(arista.source());
            int j = indices.get(arista.target());
            distancias[i][j] = arista.dato().getWeight();
            // El predecesor directo de j viniendo de i es i mismo
            predecesores[i][j] = i;
        }

        // Algoritmo de Floyd — para cada vértice intermedio k pregunta:
        // "¿Conviene ir de i a j pasando por k?"
        for (int k = 0; k < n; k++){
            for (int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]){
                        // Encontramos un camino más corto pasando por k
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        predecesores[i][j] = predecesores[k][j];
                    }
                }
            }
        }

        // Retornamos el resultado con las matrices calculadas
        return new FloydWarshallResult<>(distancias, predecesores, indices);
    }

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo) {

        int n = grafo.cantidadDeVertices();
        // Mapeamos cada vértice a un índice numérico para poder usarlo en las matrices
        Map<V, Integer> indices = new HashMap<>();
        int x = 0;
        for (V vertice : grafo.vertices()){
            indices.put(vertice, x);
            x++;
        }

        //Matriz de distancias NxN
        boolean[][] distancias = new boolean[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                // Inicializamos en false — todavía no conocemos ningún camino
                distancias[i][j] = false;
            }
        }

        // La distancia de un vértice a sí mismo es true
        for (int i = 0; i < n; i++){
            distancias[i][i] = true;
        }

        // Cargamos las conexiones
        for (Edge<V, D> arista : grafo.aristas()){
            int i = indices.get(arista.source());
            int j = indices.get(arista.target());
            distancias[i][j] = true;  // existe conexión directa
        }

        for (int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    if(distancias[i][k] && distancias[k][j]){
                        distancias[i][j] = true;
                    }
                }
            }
        }

        return new WarshallResult<>(distancias, indices);
    }

    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo) {
        return null;
    }

    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo, Comparable<V> vertexCriteria) {
        return 0;
    }

    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(Comparable<V> source, Comparable<V> target, IGraph<V, D> grafo) {
        return List.of();
    }

    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        Set<V> visitados = new HashSet<>();
        dfs(grafo, sourceCriteria, consumer, visitados);
    }

    private <V, D> void dfs(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer, Set<V> visitados) {
        // Buscamos el vértice actual en el grafo usando el criterio
        V actual = grafo.buscarVertice(sourceCriteria);

        // Si no existe o ya fue visitado, no hacemos nada y salimos
        if (actual == null || visitados.contains(actual)) return;

        // Marcamos el vértice como visitado para no procesarlo de nuevo
        visitados.add(actual);
        // Procesamos el vértice — consumer decide qué hacer con él (imprimir, agregar a lista, etc.)
        consumer.accept(actual);

        // Recorremos todas las aristas salientes del vértice actual
        for (Edge<V, D> arista : grafo.adyacencias(sourceCriteria)) {
            // Para cada arista, llamamos recursivamente a dfs con el vértice destino
            // Así seguimos el recorrido en profundidad hacia los sucesores
            dfs(grafo, grafo.construirComparable(arista.target()), consumer, visitados);
        }
    }

    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {

    }

    @Override
    public <V, D> List<V> calcularClasificacionTopologica(IDirectedIGraph<V, D> grafo) {
        return List.of();
    }
}
