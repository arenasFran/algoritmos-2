package dominio;

public class Entrada implements Comparable<Entrada> {

    private Evento evento;
    private Cliente cliente;
    private Estado estado;
    private Integer calificacion;
    private String comentario;

    public Entrada(Evento evento, Cliente cliente, Estado estado) {
        this.evento = evento;
        this.cliente = cliente;
        this.estado = estado;
        this.calificacion = null;
        this.comentario = null;
    }

    @Override
    public int compareTo(Entrada otra) {
        return this.cliente.getCedula().compareTo(otra.cliente.getCedula());
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
