package tads;

import tads.InterfacesTads.ICola;

public class Cola<T> implements ICola<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int cantidad;

    public Cola() {
        this.primero = null;
        this.ultimo = null;
        this.cantidad = 0;
    }

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (this.esVacia()) {
            this.primero = nuevo;
            this.ultimo = nuevo;
        } else {
            this.ultimo.setSiguiente(nuevo);
            this.ultimo = nuevo;
        }
        this.cantidad++;
    }

    public T desencolar() {
        if (this.esVacia()) {
            return null;
        }
        T dato = this.primero.getDato();
        this.primero = this.primero.getSiguiente();
        if (this.primero == null) {
            this.ultimo = null;
        }
        this.cantidad--;
        return dato;
    }

    public boolean esVacia() {
        return this.cantidad == 0;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public T verPrimero() {
        if (this.esVacia()) {
            return null;
        }
        return this.primero.getDato();
    }
} 