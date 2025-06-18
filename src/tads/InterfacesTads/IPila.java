package tads.InterfacesTads;

public interface IPila<T> {

    void apilar(T dato);

    T desapilar();

    boolean esVacia();

    int getCantidad();

    T verCima();
}
