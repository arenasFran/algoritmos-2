package dominio;

public class Entrada implements Comparable<Entrada> {

    private Evento evento;
    private Cliente cliente;
    private Estado estado;

    public Entrada(Evento evento, Cliente cliente, Estado estado) {
        this.evento = evento;
        this.cliente = cliente;
        this.estado = estado;
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

}
