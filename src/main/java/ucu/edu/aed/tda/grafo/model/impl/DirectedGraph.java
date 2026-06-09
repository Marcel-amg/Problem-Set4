package ucu.edu.aed.tda.grafo.model.impl;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

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
        Set<V> puntosDePartida = new HashSet<>();

        for (Edge<V, D> arista : aristas() ){
            if (criteria.compareTo(arista.source()) == 0){
                puntosDePartida.add(arista.target());
            }
        }
        return puntosDePartida;
    }

    @Override
    public Set<V> predecessors(Comparable<V> criteria) {
        Set<V> puntosDeLlegada = new HashSet<>();

        for (Edge<V, D> arista : aristas()){
            if (criteria.compareTo(arista.target()) == 0){
                puntosDeLlegada.add(arista.source());
            }
        }
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
    public Object buscarVertice(Comparable criterio) {
        return null;
    }

    @Override
    public boolean agregarArista(V source, V target, D dato) {
        if(existeVertice(construirComparable(source))){
            if(existeVertice(construirComparable(target))){
                if(!existeArista(construirComparable(source), construirComparable(target))){
                    aristas.add(new DirectedEdge<>(source, target, dato));
                    return true;
                }
            }
        }
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
    public boolean existeArista(Comparable sourceCriteria, Comparable targetCriteria) {
        return false;
    }

    @Override
    public Edge obtenerArista(Comparable sourceCriteria, Comparable targetCriteria) {
        return null;
    }

    @Override
    public List<Edge> adyacencias(Comparable verticeCriteria) {
        return List.of();
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
        return false;
    }
}
