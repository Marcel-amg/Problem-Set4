package ucu.edu.aed.tda.grafo.model.impl;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectedGraph <V, D> implements IDirectedIGraph <V,D> {
    private Set<V> vertices;
    private Set<Edge<V, D>> aristas;

    public DirectedGraph(){
        this.vertices = new HashSet<>();
        this.aristas = new HashSet<>();
    }

    @Override
    public Set<V> successors(Comparable<V> criteria) {
        // Set donde guardamos los vértices alcanzables desde el vértice buscado
        Set<V> puntosDePartida = new HashSet<>();
        // Recorremos todas las aristas del grafo
        for (Edge<V, D> arista : aristas()){
            // Si el origen de la arista coincide con el criterio, guardamos el destino
            // porque ese es un sucesor del vértice buscado
            if (criteria.compareTo(arista.source()) == 0){
                puntosDePartida.add(arista.target());
            }
        }
        // Retornamos todos los sucesores encontrados
        return puntosDePartida;
    }

    @Override
    public Set<V> predecessors(Comparable<V> criteria) {
        // Set donde guardamos los vértices que apuntan al vértice buscado
        Set<V> puntosDeLlegada = new HashSet<>();
        // Recorremos todas las aristas del grafo
        for (Edge<V, D> arista : aristas()){
            // Si el destino de la arista coincide con el criterio, guardamos el origen
            // porque ese es un predecesor del vértice buscado
            if (criteria.compareTo(arista.target()) == 0){
                puntosDeLlegada.add(arista.source());
            }
        }
        // Retornamos todos los predecesores encontrados
        return puntosDeLlegada;
    }

    @Override
    public boolean agregarVertice(V vertex) {
        //El metodo existeVertice está en la interface IGraph
        if (!existeVertice(construirComparable(vertex))){
            //Si no existe -> Lo agrego
            vertices.add(vertex);
            return true;
        }
        return false;
    }

    @Override
    public V buscarVertice(Comparable<V> criterio) {
        // Recorremos todos los vértices del grafo
        for (V vertice : vertices){
            // Si el criterio coincide con el vértice actual lo retornamos
            if (criterio.compareTo(vertice) == 0){
                return vertice;
            }
        }
        // Si no encontró ningún vértice que coincida retorna null
        return null;
    }

    @Override
    public boolean agregarArista(V source, V target, D dato) {
        // Verificamos que el vértice origen exista en el grafo
        if(existeVertice(construirComparable(source))){
            // Verificamos que el vértice destino exista en el grafo
            if(existeVertice(construirComparable(target))){
                // Verificamos que la arista no exista ya para evitar duplicados
                if(!existeArista(construirComparable(source), construirComparable(target))){
                    // Creamos la arista dirigida y la agregamos al Set
                    aristas.add(new DirectedEdge<>(source, target, dato));
                    return true;
                }
            }
        }
        // Retorna false si alguna condición no se cumple
        return false;
    }

    @Override
    public boolean eliminarArista(Comparable source, Comparable target) {
        return false;
    }

    @Override
    public boolean removerVertice(Comparable criteria) {
        return false;
    }

    @Override
    public Set<V> vertices() {
        return vertices;
    }

    @Override
    public Set<Edge<V, D>> aristas() {
        return aristas;
    }

    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        // Recorremos todas las aristas del grafo
        for (Edge<V, D> vertice : aristas){
            // Verificamos que tanto el origen como el destino coincidan con los criterios
            if (sourceCriteria.compareTo(vertice.source()) == 0 && targetCriteria.compareTo(vertice.target()) == 0){
                return true;
            }
        }
        // Si no encontró ninguna arista que coincida retorna false
        return false;
    }

    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        // Recorremos todas las aristas del grafo
        for (Edge<V, D> arista : aristas){
            // Si el origen y destino coinciden con los criterios, retornamos esa arista
            if (sourceCriteria.compareTo(arista.source()) == 0 && targetCriteria.compareTo(arista.target()) == 0){
                return arista;
            }
        }
        // Si no encontró ninguna arista retorna null
        return null;
    }

    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        // Lista donde guardamos las aristas salientes del vértice buscado
        List<Edge<V, D>> resultado = new ArrayList<>();
        // Recorremos todas las aristas del grafo
        for(Edge<V, D> arista : aristas){
            // Si el origen de la arista coincide con el criterio la agregamos
            if (verticeCriteria.compareTo(arista.source()) == 0){
                resultado.add(arista);
            }
        }
        // Retornamos todas las aristas salientes encontradas
        return resultado;
    }

    @Override
    public boolean esConexo() {
        return false;
    }

    @Override
    public void vaciar() {
    }

    @Override
    public boolean tieneCiclos() {
        Set<V> visitados = new HashSet<>();
        Set<V> enProceso = new HashSet<>();

        // Recorremos todos los vértices del grafo
        for(V vertice : vertices){
            // Solo procesamos los vértices que no fueron visitados todavía
            if(!visitados.contains(vertice)){
                // Si encontramos un ciclo retornamos true inmediatamente
                if(tieneCiclosAux(vertice, visitados, enProceso)){
                    return true;
                }
            }
        }
        // Si recorrimos todo el grafo sin encontrar ciclos retornamos false
        return false;
    }

    private boolean tieneCiclosAux(V vertice, Set<V> visitados, Set<V> enProceso) {
        // Si el vértice está siendo procesado actualmente significa que
        // encontramos un camino que vuelve a él → hay ciclo
        if (enProceso.contains(vertice)) return true;

        // Si ya fue procesado completamente en una iteración anterior → no hay ciclo por acá
        if (visitados.contains(vertice)) return false;

        // Marcamos el vértice como "en proceso" — está siendo visitado actualmente
        enProceso.add(vertice);

        // Recorremos todas las aristas salientes del vértice actual
        for (Edge<V, D> arista : aristas()) {
            // Verificamos que la arista salga del vértice actual
            if (construirComparable(vertice).compareTo(arista.source()) == 0) {
                // Llamamos recursivamente con el vértice destino
                // Si encontramos un ciclo lo propagamos hacia arriba
                if (tieneCiclosAux(arista.target(), visitados, enProceso)) {
                    return true;
                }
            }
        }

        // Terminamos de procesar este vértice — lo sacamos de enProceso
        // y lo marcamos como visitado completamente
        enProceso.remove(vertice);
        visitados.add(vertice);

        return false;
    }
}
