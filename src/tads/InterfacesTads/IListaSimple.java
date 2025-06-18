package tads.InterfacesTads;

import tads.Nodo;

public interface IListaSimple<T> {

    public void agregar(T dato);

    public void agregarInicio(T dato);

    public T eliminarFinal();

    public void agregarFin(T dato);

    public boolean eliminar(T dato);

    public boolean contiene(T dato);

    public int tamaño();

    public Nodo<T> getInicio();

    public T obtenerPorIndice(int indice);

    public void insertarEn(int indice, T dato);

    public void bubbleSort(java.util.Comparator<T> comparator);

}
