package dominio;

import java.time.LocalDate;
import tads.Listadoble;
import tads.Cola;

/**
 *
 * @author frana
 */
public class Evento {

    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private Sala salaAsignada;
    private Listadoble<Entrada> entradasVendidas;
    private Cola<Cliente> listaEspera;

    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha, Sala salaAsignada) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.salaAsignada = salaAsignada;
        this.entradasVendidas = new Listadoble<Entrada>(10);
        this.listaEspera = new Cola<Cliente>();
    }

    public void setEntradasVendidas(Listadoble<Entrada> entradasVendidas) {
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

    public Listadoble<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    public Cola<Cliente> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(Cola<Cliente> listaEspera) {
        this.listaEspera = listaEspera;
    }
}
