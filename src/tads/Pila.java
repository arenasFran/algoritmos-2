package tads;

import tads.InterfacesTads.IPila;

public class Pila<T> implements IPila<T> {

    private Nodo<T> cima;
    private int cantidad;

    public Pila() {
        this.cima = null;
        this.cantidad = 0;
    }

    @Override
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(this.cima);
        this.cima = nuevo;
        this.cantidad++;
    }

    @Override
    public T desapilar() {
        if (this.esVacia()) {
            return null;
        }
        T dato = this.cima.getDato();
        this.cima = this.cima.getSiguiente();
        this.cantidad--;
        return dato;
    }

    @Override
    public boolean esVacia() {
        return this.cantidad == 0;
    }

    @Override
    public int getCantidad() {
        return this.cantidad;
    }

    @Override
    public T verCima() {
        if (this.esVacia()) {
            return null;
        }
        return this.cima.getDato();
    }
}
