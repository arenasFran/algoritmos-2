package tads.InterfacesTads;

public interface ICola<T> {

    void encolar(T dato);

    T desencolar();

    boolean esVacia();

    int getCantidad();

    T verPrimero();
}
