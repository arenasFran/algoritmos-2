package tads;

import tads.InterfacesTads.IListaDoble;

public class ListaDoble<T extends Comparable<T>> implements IListaDoble<T> {

    Nodo<T> primero;
    Nodo<T> ultimo;
    int cantidadnodos;
    int cantidadmaximaaceptada;

    public ListaDoble(int cantidadmaximaaceptada) {
        this.primero = null;
        this.ultimo = null;
    
        this.cantidadmaximaaceptada = cantidadmaximaaceptada;
    }

    public Nodo<T> getPrimero() {
        return primero;
    }

    public void setPrimero(Nodo<T> primero) {
        this.primero = primero;
    }

    public Nodo<T> getUltimo() {
        return ultimo;
    }

    public void setUltimo(Nodo<T> ultimo) {
        this.ultimo = ultimo;
    }

    public int getCantidadnodos() {
        return cantidadnodos;
    }

    public void setCantidadnodos(int cantidadnodos) {
        this.cantidadnodos = cantidadnodos;
    }

    public int getCantidadmaximaaceptada() {
        return cantidadmaximaaceptada;
    }

    public void setCantidadmaximaaceptada(int cantidadmaximaaceptada) {
        this.cantidadmaximaaceptada = cantidadmaximaaceptada;
    }

// metodos abstractos    
    @Override
    public boolean esVacia() {
        return this.cantidadnodos == 0;
    }

    @Override
    public boolean esLLena() {
        return this.cantidadmaximaaceptada == this.cantidadnodos;
    }

    @Override
    public void agregarInicio(T dato) {
        if (!this.esLLena()) {
            Nodo<T> nuevo = new Nodo<>(dato);
            if (this.esVacia()) {
                this.setPrimero(nuevo);
                this.setUltimo(nuevo);
            } else {
                this.getPrimero().setAnterior(nuevo);
                nuevo.setSiguiente(this.getPrimero());
                this.setPrimero(nuevo);
            }
            this.cantidadnodos++;
        } else {
            System.out.println("Lista llena, no se puede agregar elemento " + dato);
        }
    }

    @Override
    public void agregarFinal(T dato) {
        if (!this.esLLena()) {
            Nodo<T> nuevo = new Nodo<>(dato);
            if (this.esVacia()) {
                this.setPrimero(nuevo);
                this.setUltimo(nuevo);
            } else {
                this.getUltimo().setSiguiente(nuevo);
                nuevo.setAnterior(this.getUltimo());
                this.setUltimo(nuevo);
            }
            this.cantidadnodos++;
        } else {
            System.out.println("Lista llena, no se puede agregar elemento " + dato);
        }
    }

    @Override
    public void agregarOrd(T dato) {
        if (!this.esLLena()) {
            if (this.esVacia() || dato.compareTo(this.getPrimero().getDato()) < 0) {
                this.agregarInicio(dato);
            } else {
                if (dato.compareTo(this.getUltimo().getDato()) > 0) {
                    this.agregarFinal(dato);
                } else {
                    Nodo<T> nuevo = new Nodo<>(dato);
                    Nodo<T> actual = this.getPrimero();
                    while (dato.compareTo(actual.getDato()) > 0) {
                        actual = actual.getSiguiente();
                    }
                    nuevo.setSiguiente(actual);
                    nuevo.setAnterior(actual.getAnterior());
                    actual.getAnterior().setSiguiente(nuevo);
                    actual.setAnterior(nuevo);
                    this.cantidadnodos++;
                }
            }
        } else {
            System.out.println("Lista llena, no se puede agregar elemento " + dato);
        }
    }

    @Override
    public void borrarInicio() {
        if (!this.esVacia()) {
            if (this.cantidadnodos == 1) {
                this.setPrimero(null);
                this.setUltimo(null);
            } else {
                this.setPrimero(this.primero.getSiguiente());
                this.primero.setAnterior(null);
            }
            this.cantidadnodos--;
        } else {
            System.out.println("Lista vacia, no hay elementos a borrar");
        }
    }

    @Override
    public void borrarFin() {
        if (!this.esVacia()) {
            if (this.cantidadnodos == 1) {
                this.setPrimero(null);
                this.setUltimo(null);
            } else {
                this.setUltimo(this.getUltimo().getAnterior());
                this.getUltimo().setSiguiente(null);
            }
            this.cantidadnodos--;
        } else {
            System.out.println("Lista vacia no hay nada para borrar");
        }
    }

    @Override
    public T obtenerPorIndice(int indice) {

        if (indice < 0 || indice >= cantidadnodos) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        Nodo<T> actual;
        // Optimización: decidir si recorrer desde el inicio o el final
        if (indice <= cantidadnodos / 2) {

            actual = primero;
            for (int i = 0; i < indice; i++) {
                actual = actual.getSiguiente();
            }
        } else {

            actual = ultimo;
            for (int i = cantidadnodos - 1; i > indice; i--) {
                actual = actual.getAnterior();
            }
        }
        return actual.getDato();
    }

    @Override
    public void borrarElemento(T dato) {
        if (this.esVacia()) {
            System.out.println("Lista vacía, no se puede borrar.");
            return;
        }

        Nodo<T> actual = this.primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                // Caso 1: Es el primer nodo
                if (actual == this.primero) {
                    this.borrarInicio();
                } // Caso 2: Es el último nodo
                else if (actual == this.ultimo) {
                    this.borrarFin();
                } // Caso 3: Nodo intermedio
                else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                    this.cantidadnodos--;
                }
                return; // Elemento borrado, salir del método
            }
            actual = actual.getSiguiente();
        }
        System.out.println("Elemento no encontrado: " + dato);
    }

    @Override
    public boolean buscarelemento(T dato) {
        Nodo<T> actual = this.primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public Nodo<T> obtenerElemento(T dato) {
        Nodo<T> actual = this.primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null; // Si no se encuentra el elemento
    }

    @Override
    public void vaciar() {
        this.primero = null;
        this.ultimo = null;
        this.cantidadnodos = 0;
    }

    @Override
    public void mostrar() {
        if (this.esVacia()) {
            System.out.println("Lista vacía.");
            return;
        }

        Nodo<T> actual = this.primero;
        System.out.print("Lista: [ ");
        while (actual != null) {
            System.out.print(actual.getDato() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println("]");
    }

    @Override
    public int cantElementos() {
        return this.cantidadnodos;
    }

}
