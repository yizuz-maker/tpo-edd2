package interfaces;

import java.util.*;

public interface IGrafo<T> {

    boolean esDirigido();

    Map<T, INodo<T>> getNodos();

    INodo<T> getNodo(T valor);

    // Modificacion de la estructra
    void agregarNodo(T valor);

    void conectar(T origenValor, T destinoValor, int peso);

    int[][] obtenerMatrizAdyacencia();

    List<T> BFS(T inicioValor);

    List<T> DFS(T inicioValor);
}