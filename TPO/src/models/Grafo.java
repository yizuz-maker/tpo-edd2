package models;

import interfaces.IGrafo;
import interfaces.INodo;
import java.util.*;

public class Grafo<T> implements IGrafo<T> {

    private final Map<T, INodo<T>> mapaNodos;
    private final boolean esOrientado;

    public Grafo(boolean esOrientado) {
        this.mapaNodos = new HashMap<>();
        this.esOrientado = esOrientado;
    }


    // Metodos de acceso
    @Override
    public Map<T, INodo<T>> getNodos() {
        return this.mapaNodos;
    }

    @Override
    public INodo<T> getNodo(T valor) {
        return this.mapaNodos.get(valor);
    }

    @Override
    public boolean esDirigido() {
        return this.esOrientado;
    }

    // Metodos de modificacion
    @Override
    public void agregarNodo(T valor) {
        if (!this.mapaNodos.containsKey(valor)) {
            this.mapaNodos.put(valor, new Nodo<>(valor));
        }
    }

    @Override
    public void conectar(T origenValor, T destinoValor, int peso) {
        INodo<T> nodoOrigen = getNodo(origenValor);
        INodo<T> nodoDestino = getNodo(destinoValor);

        if (nodoOrigen != null && nodoDestino != null) {
            nodoOrigen.agregarVecino(nodoDestino, peso);

            if (!this.esOrientado) {
                nodoDestino.agregarVecino(nodoOrigen, peso);
            }
        }
    }


    // Representacion del grafo
    @Override
    public int[][] obtenerMatrizAdyacencia() {
        List<INodo<T>> lista = new ArrayList<>(this.mapaNodos.values());
        Map<INodo<T>, Integer> indices = new HashMap<>();

        for (int i = 0; i < lista.size(); i++) {
            indices.put(lista.get(i), i);
        }

        int n = lista.size();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            INodo<T> origen = lista.get(i);
            for (INodo<T> vecino : origen.getVecinos()) {
                Integer j = indices.get(vecino);
                if (j != null) {
                    matriz[i][j] = 1;
                }
            }
        }

        return matriz;
    }

    // Algoritmos para recorrer
    @Override
    public List<T> BFS(T inicioValor) {
        List<T> recorrido = new ArrayList<>();
        INodo<T> nodoInicio = getNodo(inicioValor);

        if (nodoInicio == null) return recorrido;

        Set<INodo<T>> vistos = new HashSet<>();
        Queue<INodo<T>> cola = new LinkedList<>();

        cola.add(nodoInicio);
        vistos.add(nodoInicio);

        while (!cola.isEmpty()) {
            INodo<T> actual = cola.poll();
            recorrido.add(actual.getDato());

            for (INodo<T> vecino : actual.getVecinos()) {
                if (!vistos.contains(vecino)) {
                    vistos.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return recorrido;
    }

    @Override
    public List<T> DFS(T inicioValor) {
        List<T> recorrido = new ArrayList<>();
        INodo<T> nodoInicio = getNodo(inicioValor);

        if (nodoInicio == null) return recorrido;

        Set<INodo<T>> marcados = new HashSet<>();
        Stack<INodo<T>> pila = new Stack<>();

        pila.push(nodoInicio);

        while (!pila.isEmpty()) {
            INodo<T> actual = pila.pop();

            if (!marcados.contains(actual)) {
                marcados.add(actual);
                recorrido.add(actual.getDato());

                List<INodo<T>> adyacentes = new ArrayList<>(actual.getVecinos());
                Collections.reverse(adyacentes);

                for (INodo<T> vecino : adyacentes) {
                    if (!marcados.contains(vecino)) {
                        pila.push(vecino);
                    }
                }
            }
        }

        return recorrido;
    }
}
