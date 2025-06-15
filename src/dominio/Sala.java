package dominio;

import java.time.LocalDate;
import tads.ListaSimple;


public class Sala {

    private String Nombre;
    private int Capacidad;
    private ListaSimple<LocalDate> fechasOcupadas;

    public void setFechasOcupadas(ListaSimple<LocalDate> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }

    public ListaSimple<LocalDate> getFechasOcupadas() {
        return fechasOcupadas;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int Capacidad) {
        this.Capacidad = Capacidad;
    }

    public Sala(String nombre, int capacidad) {
        this.setNombre(nombre);
        this.setCapacidad(capacidad);
        this.fechasOcupadas = new ListaSimple<>();

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Sala sala = (Sala) obj;
        if (this.getNombre() == null || sala.getNombre() == null) {
            return false;
        }

        boolean iguales = this.getNombre().trim().equalsIgnoreCase(sala.getNombre().trim());

        return iguales;
    }

    @Override
    public String toString() {
        return Nombre + "-" + Capacidad;
    }

    @Override
    public int hashCode() {
        return Nombre != null ? Nombre.toLowerCase().hashCode() : 0;
    }
}
