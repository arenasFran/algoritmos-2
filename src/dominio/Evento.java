package dominio;

import java.time.LocalDate;
import tads.ListaDoble;
import tads.Cola;

public class Evento {

    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private Sala salaAsignada;
    private ListaDoble<Entrada> entradasVendidas;
    private Cola<Cliente> listaEspera;

    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha, Sala salaAsignada) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.salaAsignada = salaAsignada;
        this.entradasVendidas = new ListaDoble<Entrada>(aforoNecesario);
        this.listaEspera = new Cola<Cliente>();
    }

    public void setEntradasVendidas(ListaDoble<Entrada> entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAforoNecesario() {
        return aforoNecesario;
    }

    public void setAforoNecesario(int aforoNecesario) {
        this.aforoNecesario = aforoNecesario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Sala getSalaAsignada() {
        return salaAsignada;
    }

    public void setSalaAsignada(Sala salaAsignada) {
        this.salaAsignada = salaAsignada;
    }

    public ListaDoble<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    public Cola<Cliente> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(Cola<Cliente> listaEspera) {
        this.listaEspera = listaEspera;
    }
}
