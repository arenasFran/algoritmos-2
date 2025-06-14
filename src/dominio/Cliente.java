package dominio;

public class Cliente implements Comparable<Cliente> {

    private String name;
    private String cedula;

    public Cliente(String name, String cedula) {
        this.name = name;
        this.cedula = cedula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

      @Override
    public int compareTo(Cliente otro) {
        return this.cedula.compareTo(otro.cedula);
    }

   @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Cliente otro = (Cliente) obj;
    return this.cedula.equals(otro.cedula);
    }


    @Override
    public int hashCode() {
        return cedula.hashCode();
    }

    @Override
        public String toString() {
        return cedula + "-" + name;
    }
}

