package test;

import models.Grafo;
import interfaces.IGrafo;
import models.Persona;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

public class Main {


    public static void main(String[] args) {

        // Crear personas
        Persona juan = new Persona("Juan", 25);
        Persona maria = new Persona("María", 28);
        Persona pedro = new Persona("Pedro", 30);
        Persona ana = new Persona("Ana", 22);
        Persona lucas = new Persona("Lucas", 27);
        Persona gema = new Persona("Gema", 35);

        // Crear el grafo (no dirigido)
        IGrafo<Persona> redSocial = new Grafo<>(false);

        redSocial.agregarNodo(juan);
        redSocial.agregarNodo(maria);
        redSocial.agregarNodo(pedro);
        redSocial.agregarNodo(ana);
        redSocial.agregarNodo(lucas);
        redSocial.agregarNodo(gema);

        // Conectar los nodos (amistades)
        redSocial.conectar(juan, maria, 2);
        redSocial.conectar(juan, pedro, 8);
        redSocial.conectar(juan, lucas, 1);
        redSocial.conectar(maria, pedro, 3);
        redSocial.conectar(pedro, ana, 2);
        redSocial.conectar(ana, lucas, 3);
        redSocial.conectar(ana, gema, 5);
        redSocial.conectar(lucas, gema, 10);

        // Recorridos
        System.out.println("\nRecorrido BFS desde Juan:");
        List<Persona> bfs = redSocial.BFS(juan);
        for (Persona persona : bfs) {
            System.out.println(persona);
        }

        System.out.println("\nRecorrido DFS desde Juan:");
        List<Persona> dfs = redSocial.DFS(juan);
        for (Persona persona : dfs) {
            System.out.println(persona);
        }

        // DIJKSTRA
        System.out.println("\n Camino más corto con Dijkstra (Juan a Gema)");
        List<Persona> dijkstraPath = redSocial.Dijkstra(juan, gema);
        imprimirCamino(dijkstraPath);

        // A*
        Map<Persona, Integer> heuristicaHaciaGema = new HashMap<>();
        heuristicaHaciaGema.put(juan, 8);
        heuristicaHaciaGema.put(maria, 9);
        heuristicaHaciaGema.put(pedro, 6);
        heuristicaHaciaGema.put(ana, 4);
        heuristicaHaciaGema.put(lucas, 7);
        heuristicaHaciaGema.put(gema, 0);

        Function<Persona, Integer> heuristica = (persona) -> {
            return heuristicaHaciaGema.getOrDefault(persona, Integer.MAX_VALUE);
        };

        System.out.println("\nCamino más corto con A* (Juan a Gema)");
        List<Persona> aStarPath = redSocial.AStar(juan, gema, heuristica);
        imprimirCamino(aStarPath);


        // Matriz de adyacencia
        System.out.println("\nMatriz de Adyacencia:");
        int[][] matriz = redSocial.obtenerMatrizAdyacencia();
        for (int[] fila : matriz) {
            for (int valor : fila) {
                System.out.print(valor + " ");
            }
            System.out.println();
        }

    }

    private static void imprimirCamino(List<Persona> camino) {
        if (camino.isEmpty()) {
            System.out.println("No se encontro un camino.");
            return;
        }

        for (int i = 0; i < camino.size(); i++) {
            Persona p = camino.get(i);
            System.out.print(p.getNombre());
            if (i < camino.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

}
