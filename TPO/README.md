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
Los recorridos BFS y DFS funcionan igual en ambos tipos de grafos
La diferencia está en los nodos alcanzables:

- En un grafo no dirigido, los recorridos pueden moverse en ambos sentidos de las aristas

- En un grafo dirigido, los recorridos solo siguen el sentido de los arcos

Por lo tanto, la estructura del grafo (dirigido o no) afecta qué nodos pueden visitarse,
pero no el funcionamiento de los algoritmos