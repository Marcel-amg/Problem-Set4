package ucu.edu.aed.tda.grafo.model.impl;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectedGraph <V, D> implements IDirectedIGraph <V,D> {


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
    public boolean agregarVertice(Object vertex) {
        return false;
    }

    @Override
    public Object buscarVertice(Comparable criterio) {
        return null;
    }

    @Override
    public boolean agregarArista(Object source, Object target, Object dato) {
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
    public Set vertices() {
        return Set.of();
    }

    @Override
    public Set<Edge> aristas() {
        return Set.of();
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
