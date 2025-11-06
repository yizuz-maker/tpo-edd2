package models;

import interfaces.IGrafo;
import interfaces.INodo;
import java.util.*;
import java.util.function.Function;

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
            List<INodo<T>> vecinos = origen.getVecinos();
            for (INodo<T> vecino : vecinos) {
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
    // IMPLEMENTACIÓN DE DIJKSTRA Y A*

    // Clase auxiliar para la PriorityQueue de Dijkstra.
    // Compara nodos basado en su distancia acumulada.

    private static class NodoDistancia<T> implements Comparable<NodoDistancia<T>> {
        INodo<T> nodo;
        int distancia;

        NodoDistancia(INodo<T> nodo, int distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(NodoDistancia<T> otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }

    @Override
    public List<T> Dijkstra(T inicioValor, T finValor) {
        INodo<T> nodoInicio = getNodo(inicioValor);
        INodo<T> nodoFin = getNodo(finValor);

        if (nodoInicio == null || nodoFin == null) {
            return new ArrayList<>(); // Retorna lista vacía si los nodos no existen
        }

        Map<INodo<T>, Integer> distancias = new HashMap<>();
        Map<INodo<T>, INodo<T>> padres = new HashMap<>();
        PriorityQueue<NodoDistancia<T>> pq = new PriorityQueue<>();

        // Inicializar distancias con infinito
        for (INodo<T> nodo : this.mapaNodos.values()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }

        // Distancia al nodo inicial es 0
        distancias.put(nodoInicio, 0);
        pq.add(new NodoDistancia<>(nodoInicio, 0));

        while (!pq.isEmpty()) {
            NodoDistancia<T> wrapper = pq.poll();
            INodo<T> actual = wrapper.nodo;

            // Si es el destino, termina
            if (actual.equals(nodoFin)) {
                return reconstruirCamino(padres, nodoFin);
            }

            // Si la distancia en la cola es mayor que la ya conocida, es una entrada "vieja"
            if (wrapper.distancia > distancias.get(actual)) {
                continue;
            }

            // Iterar sobre vecinos y pesos (usando la estructura de listas paralelas)
            List<INodo<T>> vecinos = actual.getVecinos();
            List<Integer> pesos = actual.getPesos();

            for (int i = 0; i < vecinos.size(); i++) {
                INodo<T> vecino = vecinos.get(i);
                int pesoArista = pesos.get(i);

                // Calcular nueva distancia
                int nuevaDistancia = distancias.get(actual) + pesoArista;

                // Si encontramos un camino mas corto hacia 'vecino'
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    padres.put(vecino, actual);
                    pq.add(new NodoDistancia<>(vecino, nuevaDistancia));
                }
            }
        }

        return new ArrayList<>(); // No se encontró camino
    }

    // Clase auxiliar para la PriorityQueue de A*.
    // Compara nodos basado en su F-Score (g + h).

    private static class NodoFScore<T> implements Comparable<NodoFScore<T>> {
        INodo<T> nodo;
        int fScore; // f = g + h

        NodoFScore(INodo<T> nodo, int fScore) {
            this.nodo = nodo;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(NodoFScore<T> otro) {
            return Integer.compare(this.fScore, otro.fScore);
        }
    }

    @Override
    public List<T> AStar(T inicioValor, T finValor, Function<T, Integer> heuristica) {
        INodo<T> nodoInicio = getNodo(inicioValor);
        INodo<T> nodoFin = getNodo(finValor);

        if (nodoInicio == null || nodoFin == null) {
            return new ArrayList<>();
        }

        // gScore: costo real desde el inicio hasta el nodo
        Map<INodo<T>, Integer> gScore = new HashMap<>();
        // fScore: costo estimado total (g + h)
        Map<INodo<T>, Integer> fScore = new HashMap<>();

        Map<INodo<T>, INodo<T>> padres = new HashMap<>();
        PriorityQueue<NodoFScore<T>> openSet = new PriorityQueue<>();

        // Inicializar puntajes con infinito
        for (INodo<T> nodo : this.mapaNodos.values()) {
            gScore.put(nodo, Integer.MAX_VALUE);
            fScore.put(nodo, Integer.MAX_VALUE);
        }

        // Puntajes del nodo inicial
        gScore.put(nodoInicio, 0);
        fScore.put(nodoInicio, heuristica.apply(nodoInicio.getDato()));
        openSet.add(new NodoFScore<>(nodoInicio, fScore.get(nodoInicio)));

        while (!openSet.isEmpty()) {
            INodo<T> actual = openSet.poll().nodo;

            // Si es el destino, termina
            if (actual.equals(nodoFin)) {
                return reconstruirCamino(padres, nodoFin);
            }

            // Iterar sobre vecinos y pesos
            List<INodo<T>> vecinos = actual.getVecinos();
            List<Integer> pesos = actual.getPesos();

            for (int i = 0; i < vecinos.size(); i++) {
                INodo<T> vecino = vecinos.get(i);
                int pesoArista = pesos.get(i);

                // gScore tentativo (costo para llegar a 'vecino' a través de 'actual')
                int gScoreTentativo = gScore.get(actual) + pesoArista;

                // Si este camino es mejor que el anterior conocido
                if (gScoreTentativo < gScore.get(vecino)) {
                    padres.put(vecino, actual);
                    gScore.put(vecino, gScoreTentativo);

                    int fScoreNuevo = gScoreTentativo + heuristica.apply(vecino.getDato());
                    fScore.put(vecino, fScoreNuevo);

                    // Añadir a la cola de prioridad para ser explorado
                    openSet.add(new NodoFScore<>(vecino, fScoreNuevo));
                }
            }
        }

        return new ArrayList<>(); // No se encontró camino
    }

    // Met0do de utilidad para reconstruir el camino desde el nodo final
    //  usando el mapa de 'padres' generado por los algoritmos.

    private List<T> reconstruirCamino(Map<INodo<T>, INodo<T>> padres, INodo<T> nodoFin) {
        LinkedList<T> camino = new LinkedList<>();
        INodo<T> actual = nodoFin;

        while (actual != null) {
            camino.addFirst(actual.getDato());
            actual = padres.get(actual);
        }

        // El bucle while termina cuando actual es null (después de procesar el nodo de inicio,
        // ya que el nodo de inicio no tiene padre en el mapa).

        return new ArrayList<>(camino);
    }
}
