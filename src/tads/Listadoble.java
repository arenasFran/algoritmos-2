/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tads;

/**
 *
 * @author Rafael
 */
public class Listadoble<T extends Comparable<T>> implements IListadoble<T> {

    Nodo<T> primero;
    Nodo<T> ultimo;
    int cantidadnodos;
    int cantidadmaximaaceptada;

    public Listadoble(int cantidadmaximaaceptada) {
        this.primero = null;
        this.ultimo = null;
        this.cantidadnodos = 0;
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
        if (!this.esVacia()){
            if (this.cantidadnodos==1){
                this.setPrimero(null);
                this.setUltimo(null);
            }else{
                this.setPrimero(this.primero.getSiguiente());
                this.primero.setAnterior(null);                
            }
            this.cantidadnodos--;
        }else{
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
    public void borrarElemento(T dato) {


    }

    @Override
    public boolean buscarelemento(T dato) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Nodo<T> obtenerElemento(T dato) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int cantElementos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
