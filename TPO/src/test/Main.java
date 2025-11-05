package test;

import models.Grafo;
import interfaces.IGrafo;
import models.Persona;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Crear personas
        Persona juan = new Persona("Juan", 25);
        Persona maria = new Persona("María", 28);
        Persona pedro = new Persona("Pedro", 30);
        Persona ana = new Persona("Ana", 22);
        Persona lucas = new Persona("Lucas", 27);

        // Crear el grafo (no dirigido)
        IGrafo<Persona> redSocial = new Grafo<>(false);

        redSocial.agregarNodo(juan);
        redSocial.agregarNodo(maria);
        redSocial.agregarNodo(pedro);
        redSocial.agregarNodo(ana);
        redSocial.agregarNodo(lucas);

        // Conectar los nodos (amistades)
        redSocial.conectar(juan, maria, 1);
        redSocial.conectar(maria, pedro, 1);
        redSocial.conectar(pedro, ana, 1);
        redSocial.conectar(ana, lucas, 1);
        redSocial.conectar(lucas, juan, 1);

        // Recorridos
        System.out.println("Recorrido BFS desde Juan:");
        List<Persona> bfs = redSocial.BFS(juan);
        for (Persona persona : bfs) {
            System.out.println(persona);
        }

        System.out.println("\nRecorrido DFS desde Juan:");
        List<Persona> dfs = redSocial.DFS(juan);
        for (Persona persona : dfs) {
            System.out.println(persona);
        }

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
}
