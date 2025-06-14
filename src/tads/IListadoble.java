/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

/**
 *
 * @author Rafael
 */
public interface IListadoble<T> {
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
    public void vaciar();
    public void mostrar();    
    public int cantElementos();
}
