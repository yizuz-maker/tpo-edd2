package interfaces;
import java.util.List;

public interface INodo<T> {

    T getDato();

    void setDato(T dato);

    void agregarVecino(INodo<T> nodo,int peso);

    void eliminarVecino(INodo<T> nodo);

    List<INodo<T>> getVecinos();

    List<Integer> getPesos();

}