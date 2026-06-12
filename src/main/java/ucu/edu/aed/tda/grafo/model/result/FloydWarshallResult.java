package ucu.edu.aed.tda.grafo.model.result;

import java.util.*;

public class FloydWarshallResult<V> implements IFloydWarshallResult<V> {
    private double distancias[][];
    private int predecesores[][];
    // Mapea cada vértice a su índice numérico para acceder a las matrices
    private Map<V, Integer> indice;

    // Recibe las matrices calculadas por Floyd y el mapa de índices
    public FloydWarshallResult(double[][] distancias, int[][] predecesores, Map<V, Integer> indices){
        this.distancias = distancias;
        this.predecesores = predecesores;
        this.indice = indices;
    }

    @Override
    public List<V> getPath(V source, V target) {
        // Invertimos el mapa para poder convertir índice → vértice
        Map<Integer, V> invertido = new HashMap<>();
        for (Map.Entry<V, Integer> entry : indice.entrySet()){
            invertido.put(entry.getValue(), entry.getKey());
        }

        // Obtenemos los índices numéricos de origen y destino
        int i = indice.get(source);
        int j = indice.get(target);

        Stack<V> pila = new Stack<>();
        List<V> camino = new ArrayList<>();

        // Recorremos los predecesores desde el destino hacia el origen
        // apilando cada vértice para después invertir el orden
        while (j != i){
            pila.push(invertido.get(j));
            j = predecesores[i][j];
        }
        // Apilamos el origen al final
        pila.push(source);

        // Desapilamos para obtener el camino en orden correcto
        while (!pila.isEmpty()){
            camino.add(pila.pop());
        }

        return camino;
    }

    @Override
    public double getCost(V source, V target) {
        // Convertimos los vértices a índices para acceder a la matriz de distancias
        return distancias[indice.get(source)][indice.get(target)];
    }

    @Override
    public boolean connected(V source, V target) {
        // Si la distancia es MAX_VALUE significa que no hay camino entre los vértices
        return getCost(source, target) != Double.MAX_VALUE;
    }
}