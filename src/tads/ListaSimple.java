package tads;

import tads.InterfacesTads.IListaSimple;

public class ListaSimple<T> implements IListaSimple<T> {

    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int tamaño;

    public int getTamaño() {
        return tamaño;
    }

    public ListaSimple() {
        this.inicio = null;
        this.fin = null;
        this.tamaño = 0;
    }

    @Override
    public T obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        Nodo<T> actual = inicio;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    @Override
public T eliminarFinal() {
    if (tamaño == 0) return null;
    if (tamaño == 1) {
        T dato = inicio.getDato();
        inicio = fin = null;
        tamaño = 0;
        return dato;
    }

    Nodo<T> actual = inicio;
    while (actual.getSiguiente() != fin) {
        actual = actual.getSiguiente();
    }

    T dato = fin.getDato();
    actual.setSiguiente(null);
    fin = actual;
    tamaño--;
    return dato;
}

    @Override
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
        tamaño++;
    }

    @Override
    public void agregarInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(inicio);
            inicio = nuevoNodo;
        }
        tamaño++;
    }

    @Override
    public void agregarFin(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (inicio == null) {
            inicio = fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
        tamaño++;
    }

    @Override
    public boolean eliminar(T dato) {
        if (inicio == null) {
            return false;
        }

        // Caso especial: eliminar el primer nodo
        if (inicio.getDato().equals(dato)) {
            inicio = inicio.getSiguiente();
            tamaño--;
            if (inicio == null) { // Si era el único nodo
                fin = null;
            }
            return true;
        }

        // Búsqueda del nodo a eliminar
        Nodo<T> actual = inicio;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                // Actualizar fin si se elimina el último nodo
                if (actual.getSiguiente() == fin) {
                    fin = actual;
                }
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }

    @Override
    public boolean contiene(T cliente) {
        Nodo<T> nodoActual = inicio;
        while (nodoActual != null) {
            if (nodoActual.getDato().equals(cliente)) {
                return true;
            }
            nodoActual = nodoActual.getSiguiente();
        }
        return false;
    }

    @Override
    public int tamaño() {
        return tamaño;
    }

    @Override
    public Nodo<T> getInicio() {
        return inicio;
    }

    @Override
    public void insertarEn(int indice, T dato) {
        if (indice < 0 || indice > tamaño) {
            return;
        }

        Nodo<T> nuevo = new Nodo<>(dato);

        if (indice == 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            if (tamaño == 0) {
                fin = nuevo;
            }
        } else {
            Nodo<T> actual = inicio;
            for (int i = 0; i < indice - 1; i++) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
            if (nuevo.getSiguiente() == null) {
                fin = nuevo;
            }
        }

        tamaño++;
    }

    @Override
    public void bubbleSort(java.util.Comparator<T> comparator) {
        if (tamaño <= 1) return;

        boolean swapped;
        do {
            swapped = false;
            Nodo<T> actual = inicio;
            Nodo<T> siguiente = inicio.getSiguiente();

            while (siguiente != null) {
                if (comparator.compare(actual.getDato(), siguiente.getDato()) > 0) {
                    // Swap data
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    swapped = true;
                }
                actual = siguiente;
                siguiente = siguiente.getSiguiente();
            }
        } while (swapped);
    }

    public void modificarElemento(int indice, T elemento) {
    if (indice < 0 || indice >= tamaño()) {
        throw new IndexOutOfBoundsException("Índice fuera de rango");
    }
    
    Nodo<T> actual = inicio;
    for (int i = 0; i < indice; i++) {
        actual = actual.getSiguiente();
    }
    actual.setDato(elemento);
}
}
