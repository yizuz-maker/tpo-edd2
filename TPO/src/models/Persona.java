package models;

public class Persona {
    private String nombre;
    private int dni;

    public Persona(String nombre, int dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getDni() {
        return this.dni;
    }

    @Override
    public String toString() {
        return this.nombre + "(" + this.dni + ")";
    }

}