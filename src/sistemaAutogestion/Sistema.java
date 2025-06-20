package sistemaAutogestion;

import dominio.Cliente;
import dominio.Entrada;
import dominio.Estado;
import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;
import tads.IListaSimple;
import tads.ListaSimple;
import tads.Nodo;

public class Sistema implements IObligatorio {

    private IListaSimple<Cliente> listaClientes;
    private IListaSimple<Sala> listaSalas;
    private IListaSimple<Evento> listaEventos;

    public Sistema() {
        listaClientes = new ListaSimple<Cliente>();
        listaSalas = new ListaSimple<Sala>();
        listaEventos = new ListaSimple<Evento>();
    }

    @Override
    public Retorno crearSistemaDeGestion() {
        listaClientes = new ListaSimple<Cliente>();
        listaSalas = new ListaSimple<Sala>();
        listaEventos = new ListaSimple<Evento>();
        return Retorno.ok();
    }

    @Override
    public Retorno registrarSala(String nombre, int capacidad) {
        if (!esCapacidadValida(capacidad)) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        if (!esNombreValido(nombre)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        String nombreLimpio = nombre.trim();
        if (existeSalaConNombre(nombreLimpio)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        agregarSala(nombreLimpio, capacidad);
        return new Retorno(Retorno.Resultado.OK);
    }

    private boolean esCapacidadValida(int capacidad) {
        return capacidad > 0;
    }

    private boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    private boolean existeSalaConNombre(String nombre) {
        Sala salaComparacion = new Sala(nombre, 0);
        return listaSalas.contiene(salaComparacion);
    }

    private void agregarSala(String nombre, int capacidad) {
        Sala nuevaSala = new Sala(nombre, capacidad);
        listaSalas.agregarInicio(nuevaSala);
    }

    @Override
    public Retorno eliminarSala(String nombre) {
        if (!esNombreValido(nombre)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        String nombreLimpio = nombre.trim();
        if (!existeSalaConNombre(nombreLimpio)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        eliminarSalaDeLista(nombreLimpio);
        return new Retorno(Retorno.Resultado.OK);
    }

    private void eliminarSalaDeLista(String nombre) {
        Sala salaAEliminar = new Sala(nombre, 0);
        listaSalas.eliminar(salaAEliminar);
    }

    @Override
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        if (!esAforoValido(aforoNecesario)) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        if (!esFechaValida(fecha)) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        if (existeEventoConCodigo(codigo)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Sala salaAsignada = buscarSalaDisponible(aforoNecesario, fecha);

        if (salaAsignada == null) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        salaAsignada.getFechasOcupadas().agregar(fecha);
        registrarNuevoEvento(codigo, descripcion, aforoNecesario, fecha, salaAsignada);

        return new Retorno(Retorno.Resultado.OK);
    }

    private boolean esAforoValido(int aforo) {
        return aforo > 0;
    }

    private boolean esFechaValida(LocalDate fecha) {
        return fecha.getDayOfMonth() >= 1 && fecha.getDayOfMonth() <= 30
                && fecha.getMonthValue() >= 1 && fecha.getMonthValue() <= 12;
    }

    private boolean existeEventoConCodigo(String codigo) {
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    private Sala buscarSalaDisponible(int aforoNecesario, LocalDate fecha) {
        int menorCapacidad = obtenerCapacidadTotalSalas() + 1;
        Sala salaAsignada = null;

        for (int i = 0; i < listaSalas.tamaño(); i++) {
            Sala sala = listaSalas.obtenerPorIndice(i);
            if (sala.getCapacidad() >= aforoNecesario && !sala.getFechasOcupadas().contiene(fecha)) {
                if (sala.getCapacidad() < menorCapacidad) {
                    salaAsignada = sala;
                    menorCapacidad = sala.getCapacidad();
                }
            }
        }

        return salaAsignada;
    }

    private int obtenerCapacidadTotalSalas() {
        int total = 0;
        for (int i = 0; i < listaSalas.tamaño(); i++) {
            total += listaSalas.obtenerPorIndice(i).getCapacidad();
        }
        return total;
    }

    private void registrarNuevoEvento(String codigo, String descripcion, int aforo, LocalDate fecha, Sala sala) {
        Evento nuevoEvento = new Evento(codigo, descripcion, aforo, fecha, sala);
        listaEventos.agregarFin(nuevoEvento);
    }

    @Override
    public Retorno registrarCliente(String cedula, String nombre) {
        if (!cedulaEsValida(cedula)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        if (clienteYaExiste(nombre, cedula)) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        agregarNuevoCliente(nombre, cedula);
        return new Retorno(Retorno.Resultado.OK);
    }

    private boolean clienteYaExiste(String nombre, String cedula) {
        return listaClientes.contiene(new Cliente(nombre, cedula));
    }

    private void agregarNuevoCliente(String nombre, String cedula) {
        Cliente nuevoCliente = new Cliente(nombre, cedula);
        listaClientes.agregar(nuevoCliente);
    }

    public boolean cedulaEsValida(String cedula) {
        // Verifica si la cédula tiene exactamente 8 caracteres numéricos
        if (cedula == null || cedula.length() != 8) {
            return false;  // Longitud incorrecta
        }

        // Verificar que todos los caracteres sean dígitos numéricos
        for (int i = 0; i < cedula.length(); i++) {
            char c = cedula.charAt(i);
            if (c < '0' || c > '9') {
                return false;  // No es un número
            }
        }

        return true;  // La cédula es válida
    }

    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        // Buscar el cliente
        Cliente clienteBuscado = new Cliente("", cedula);
        if (!listaClientes.contiene(clienteBuscado)) {
            return Retorno.error1();
        }

        // Buscar el evento
        Evento eventoEncontrado = null;
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigoEvento)) {
                eventoEncontrado = evento;
                break;
            }
        }

        if (eventoEncontrado == null) {
            return Retorno.error2();
        }

        // Verificar disponibilidad
        int entradasVendidas = eventoEncontrado.getEntradasVendidas().getCantidadnodos();
        int capacidadSala = eventoEncontrado.getSalaAsignada().getCapacidad();

        if (entradasVendidas < capacidadSala) {
            // Hay disponibilidad, crear y asignar entrada
            Entrada nuevaEntrada = new Entrada(eventoEncontrado, clienteBuscado, Estado.ACTIVA);
            eventoEncontrado.getEntradasVendidas().agregarFinal(nuevaEntrada);
            return Retorno.ok();
        } else {
            // No hay disponibilidad, agregar a lista de espera
            eventoEncontrado.getListaEspera().encolar(clienteBuscado);
            return Retorno.ok("Cliente agregado a lista de espera");
        }
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarSalas() {
        if (listaSalas.tamaño() == 0) {
            return new Retorno(Retorno.Resultado.OK, "No hay salas registradas.");
        }

        ListaSimple<Sala> salasOrdenadas = copiarSalas();

        String salida = construirSalidaSalas(salasOrdenadas);

        return new Retorno(Retorno.Resultado.OK, salida);
    }

    private ListaSimple<Sala> copiarSalas() {
        ListaSimple<Sala> copia = new ListaSimple<>();
        for (int i = 0; i < listaSalas.tamaño(); i++) {
            copia.agregar(listaSalas.obtenerPorIndice(i));
        }
        return copia;
    }

    private String construirSalidaSalas(ListaSimple<Sala> salas) {
        String salida = "";
        for (int i = 0; i < salas.tamaño(); i++) {
            Sala sala = salas.obtenerPorIndice(i);
            if (i > 0) {
                salida += "#";
            }
            salida += sala.getNombre() + "-" + sala.getCapacidad();
        }
        return salida;
    }

    @Override
    public Retorno listarEventos() {
        if (listaEventos == null || listaEventos.tamaño() == 0) {
            return Retorno.ok();
        }

        ListaSimple<Evento> eventosOrdenados = obtenerEventosOrdenadosPorCodigo();
        String resultado = construirSalidaEventos(eventosOrdenados);

        return Retorno.ok(resultado);
    }

    private ListaSimple<Evento> obtenerEventosOrdenadosPorCodigo() {
        ListaSimple<Evento> ordenados = new ListaSimple<>();

        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento actual = listaEventos.obtenerPorIndice(i);
            boolean insertado = false;

            for (int j = 0; j < ordenados.tamaño(); j++) {
                Evento comparado = ordenados.obtenerPorIndice(j);
                if (actual.getCodigo().compareTo(comparado.getCodigo()) < 0) {
                    ordenados.insertarEn(j, actual);
                    insertado = true;
                    break;
                }
            }

            if (!insertado) {
                ordenados.agregarFin(actual);
            }
        }

        return ordenados;
    }

    private String construirSalidaEventos(ListaSimple<Evento> eventos) {
        String resultado = "";

        for (int i = 0; i < eventos.tamaño(); i++) {
            Evento e = eventos.obtenerPorIndice(i);
            int capacidad = e.getSalaAsignada().getCapacidad();
            String nombreSala = e.getSalaAsignada().getNombre();
            int vendidas = e.getEntradasVendidas().getCantidadnodos();
            int disponibles = capacidad - vendidas;

            String eventoString = e.getCodigo() + "-"
                    + e.getDescripcion() + "-"
                    + nombreSala + "-"
                    + disponibles + "-"
                    + vendidas;

            if (resultado.equals("")) {
                resultado = eventoString;
            } else {
                resultado += "#" + eventoString;
            }
        }

        return resultado;
    }

    @Override
    public Retorno listarClientes() {
        if (listaClientes.getInicio() == null) {
            return new Retorno(Retorno.Resultado.OK, "No hay clientes registrados.");
        }

        ListaSimple<Cliente> ordenada = obtenerClientesOrdenadosPorCedula();
        String salida = construirSalidaClientes(ordenada);

        return new Retorno(Retorno.Resultado.OK, salida);
    }

    private ListaSimple<Cliente> obtenerClientesOrdenadosPorCedula() {
        ListaSimple<Cliente> ordenada = new ListaSimple<>();

        for (int i = 0; i < listaClientes.tamaño(); i++) {
            Cliente clienteActual = listaClientes.obtenerPorIndice(i);

            int pos = 0;
            while (pos < ordenada.getTamaño()
                    && ordenada.obtenerPorIndice(pos).getCedula().compareTo(clienteActual.getCedula()) < 0) {
                pos++;
            }

            ordenada.insertarEn(pos, clienteActual);
        }

        return ordenada;
    }

    private String construirSalidaClientes(ListaSimple<Cliente> clientesOrdenados) {
        String salida = "";

        for (int i = 0; i < clientesOrdenados.getTamaño(); i++) {
            Cliente c = clientesOrdenados.obtenerPorIndice(i);
            salida += c.getCedula() + "-" + c.getName();
            if (i < clientesOrdenados.getTamaño() - 1) {
                salida += "#";
            }
        }

        return salida;
    }

    @Override

    public Retorno esSalaOptima(String vistaSala[][]) {
        if (!esVistaValida(vistaSala)) {
            return Retorno.error1();
        }

        int columnasOptimas = contarColumnasOptimas(vistaSala);

        if (columnasOptimas >= 2) {
            return Retorno.ok("Es óptimo");
        } else {
            return Retorno.ok("No es óptimo");
        }
    }

    private boolean esVistaValida(String[][] vistaSala) {
        return vistaSala != null && vistaSala.length > 0 && vistaSala[0].length > 0;
    }

    private int contarColumnasOptimas(String[][] vistaSala) {
        int filas = vistaSala.length;
        int columnas = vistaSala[0].length;
        int columnasOptimas = 0;

        for (int c = 0; c < columnas; c++) {
            if (esColumnaOptima(vistaSala, filas, c)) {
                columnasOptimas++;
            }
        }

        return columnasOptimas;
    }

    private boolean esColumnaOptima(String[][] vistaSala, int filas, int columna) {
        int maxOcupadosConsecutivos = 0;
        int ocupadosConsecutivosActual = 0;
        int libres = 0;

        for (int f = 0; f < filas; f++) {
            String celda = vistaSala[f][columna];

            if (celda.equals("#")) {
                ocupadosConsecutivosActual = 0;
            } else if (celda.equals("O")) {
                ocupadosConsecutivosActual++;
                maxOcupadosConsecutivos = Math.max(maxOcupadosConsecutivos, ocupadosConsecutivosActual);
            } else if (celda.equals("X")) {
                libres++;
                ocupadosConsecutivosActual = 0;
            }
        }

        return maxOcupadosConsecutivos > libres;
    }

    @Override
    public Retorno listarClientesDeEvento(String código, int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarEsperaEvento() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasXDia(int mes) {
        return Retorno.noImplementada();
    }

}
