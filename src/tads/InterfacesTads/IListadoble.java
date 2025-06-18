package tads.InterfacesTads;

import tads.Nodo;

public interface IListaDoble<T> {

    public boolean esVacia();

    public boolean esLLena();

    public void agregarInicio(T dato);

    public void agregarFinal(T dato);

    public void agregarOrd(T dato);

    public void borrarInicio();

    public void borrarFin();

    public void borrarElemento(T dato);

    public boolean buscarelemento(T dato);

    public Nodo<T> obtenerElemento(T dato);

    public T obtenerPorIndice(int indice);

    public void vaciar();

    public void mostrar();

    public int cantElementos();
}
