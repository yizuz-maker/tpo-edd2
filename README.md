# ¿Como haría para que el grafo sea dirigido?
El grafo puede configurarse como dirigido o no dirigido desde su constructor:

// Grafo no dirigido
```java
IGrafo<Persona> grafoNoDirigido = new Grafo<>(false);
```

// Grafo dirigido
```java
IGrafo<Persona> grafoDirigido = new Grafo<>(true);
```

Cuando el grafo es dirigido, las conexiones solo se crean en un sentido
Por ejemplo:

```java
grafoDirigido.conectar(Pedro, Gema, 1);
```

creará un enlace Pedro → Gema,
pero no el inverso (Gema → Pedro)

En cambio, en un grafo no dirigido, esa conexión es bidireccional

# ¿Se puede recorrer igual?
Los recorridos BFS, DFS, DIJKSTRA y A* funcionan igual.
La diferencia está en el propósito y el uso de los pesos (costos) de las aristas.

- 1. BFS y DFS (Recorrido)
     Propósito: Su objetivo es visitar todos los nodos alcanzables. No les importa el costo del camino.

Pesos: Ignoran por completo los pesos de las aristas. Para ellos, todos los caminos (aristas) son iguales.

Estructura de Datos: BFS usa una Cola (Queue) para explorar nivel por nivel. DFS usa una Pila (Stack) para explorar en profundidad.

Pregunta que responden: "¿Se puede llegar de A a B?" (BFS además responde a: "¿Cuál es el camino con menos saltos?").

- 2. Dijkstra y A* (Optimización)
     Propósito: Su objetivo es encontrar el camino más barato (de menor costo total) entre dos nodos.

Pesos: Son la base para tomar decisiones. Constantemente suman los pesos y eligen el camino que acumula menos costo.

Estructura de Datos: Ambos usan una Cola de Prioridad (Priority Queue) para explorar siempre el nodo más prometedor (el de menor costo acumulado hasta ahora).

Pregunta que responden: "¿Cuál es el camino con el menor costo total de A a B?"

Por lo tanto, la estructura del grafo (dirigido o no) afecta qué nodos pueden visitarse,
pero no el funcionamiento de los algoritmos