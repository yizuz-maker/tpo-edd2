package models;

import interfaces.INodo;
import java.util.ArrayList;
import java.util.List;

public class Nodo<T> implements INodo<T> {

    private T valor;
    private final List<INodo<T>> adyacentes;
    private final List<Integer> pesosAristas;

    public Nodo(T valor) {
        this.valor = valor;
        this.adyacentes = new ArrayList<>();
        this.pesosAristas = new ArrayList<>();
    }

    @Override
    public T getDato() {
        return this.valor;
    }

    @Override
    public void setDato(T valor) {
        this.valor = valor;
    }

    @Override
    public List<INodo<T>> getVecinos() {
        return this.adyacentes;
    }

    @Override
    public List<Integer> getPesos() {
        return this.pesosAristas;
    }

    @Override
    public void agregarVecino(INodo<T> otroNodo, int peso) {
        if (!this.adyacentes.contains(otroNodo)) {
            this.adyacentes.add(otroNodo);
            this.pesosAristas.add(peso);
        }
    }

    @Override
    public void eliminarVecino(INodo<T> otroNodo) {
        int indice = this.adyacentes.indexOf(otroNodo);
        if (indice != -1) {
            this.adyacentes.remove(indice);
            this.pesosAristas.remove(indice);
        }
    }

    @Override
    public String toString() {
        return this.valor.toString();
    }
}
