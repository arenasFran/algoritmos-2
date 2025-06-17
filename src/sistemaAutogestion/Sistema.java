package sistemaAutogestion;

import dominio.Cliente;
import dominio.Entrada;
import dominio.Estado;
import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import tads.InterfacesTads.IListaDoble;
import tads.InterfacesTads.IListaSimple;
import tads.ListaSimple;
import tads.ListaDoble;
import tads.Nodo;
import tads.Cola;

public class Sistema implements IObligatorio {

    private IListaSimple<Cliente> listaClientes;
    private IListaSimple<Sala> listaSalas;
    private IListaSimple<Evento> listaEventos;
    private IListaSimple<Entrada> listaEntradas;

    public Sistema() {
        listaClientes = new ListaSimple<Cliente>();
        listaSalas = new ListaSimple<Sala>();
        listaEventos = new ListaSimple<Evento>();
        listaEntradas = new ListaSimple<Entrada>();
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
        Cliente clienteBuscado = buscarClientePorCedula(cedula);
        if (clienteBuscado == null) {
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
            listaEntradas.agregar(nuevaEntrada);
            return Retorno.ok();
        } else {
            // No hay disponibilidad, agregar a lista de espera
            eventoEncontrado.getListaEspera().encolar(clienteBuscado);
            return Retorno.ok("Cliente agregado a lista de espera");
        }
    }

    private Cliente buscarClientePorCedula(String cedula) {
        for (int i = 0; i < listaClientes.tamaño(); i++) {
            Cliente c = listaClientes.obtenerPorIndice(i);
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        // 1. Verificar si el evento existe
        Evento eventoAEliminar = null;
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigo)) {
                eventoAEliminar = evento;
                break;
            }
        }

        if (eventoAEliminar == null) {
            return Retorno.error1(); // ERROR 1: Evento no existe
        }

        // 2. Verificar si hay entradas vendidas
        if (eventoAEliminar.getEntradasVendidas().cantElementos() > 0) {
            return Retorno.error2(); // ERROR 2: Evento tiene entradas vendidas
        }

        // 3. Liberar la sala asignada (eliminar la fecha ocupada)
        Sala salaAsignada = eventoAEliminar.getSalaAsignada();
        if (salaAsignada != null) {
            salaAsignada.getFechasOcupadas().eliminar(eventoAEliminar.getFecha());
        }

        // 4. Eliminar el evento de la lista
        listaEventos.eliminar(eventoAEliminar);

        return Retorno.ok(); // OK: Evento eliminado
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        // 1. Verificar si el cliente existe
        Cliente clienteBuscado = buscarClientePorCedula(cedula);
        if (clienteBuscado == null) {
            return Retorno.error1(); // ERROR 1: Cliente no existe
        }

        // 2. Verificar si el evento existe
        Evento eventoEncontrado = null;
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigoEvento)) {
                eventoEncontrado = evento;
                break;
            }
        }
        if (eventoEncontrado == null) {
            return Retorno.error2(); // ERROR 2: Evento no existe
        }

        // 3. Buscar y eliminar la entrada del cliente para el evento
        boolean entradaEliminada = false;
        IListaDoble<Entrada> entradas = eventoEncontrado.getEntradasVendidas();
        for (int i = 0; i < entradas.cantElementos(); i++) {
            Entrada entrada = entradas.obtenerPorIndice(i);
            if (entrada.getCliente().getCedula().equals(cedula) && entrada.getEstado() == Estado.ACTIVA) {
                entradas.borrarElemento(entrada);
                entradaEliminada = true;
                break;
            }
        }

        if (!entradaEliminada) {
            return Retorno.error1(); // Cliente no tiene entrada activa para el evento
        }

        // 4. Reasignar entrada si hay clientes en espera (cola FIFO)
        if (!eventoEncontrado.getListaEspera().esVacia()) {
            Cliente primerEnEspera = eventoEncontrado.getListaEspera().desencolar();
            Entrada nuevaEntrada = new Entrada(eventoEncontrado, primerEnEspera, Estado.ACTIVA);
            eventoEncontrado.getEntradasVendidas().agregarFinal(nuevaEntrada);
        }

        return Retorno.ok(); // OK: Entrada devuelta (y reasignada si aplica)
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        // 1. Verificar si el cliente existe
        Cliente clienteBuscado = buscarClientePorCedula(cedula);
        if (clienteBuscado == null) {
            return Retorno.error1(); // ERROR 1: Cliente no existe
        }

        // 2. Verificar si el evento existe
        Evento eventoEncontrado = null;
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigoEvento)) {
                eventoEncontrado = evento;
                break;
            }
        }
        if (eventoEncontrado == null) {
            return Retorno.error2(); // ERROR 2: Evento no existe
        }

        // 3. Verificar si el puntaje es válido
        if (puntaje < 1 || puntaje > 10) {
            return Retorno.error3(); // ERROR 3: Puntaje inválido
        }

        // 4. Verificar si el cliente ya calificó el evento
        IListaDoble<Entrada> entradas = eventoEncontrado.getEntradasVendidas();
        for (int i = 0; i < entradas.cantElementos(); i++) {
            Entrada entrada = entradas.obtenerPorIndice(i);
            if (entrada.getCliente().getCedula().equals(cedula) && entrada.getCalificacion() != null) {
                return Retorno.error4(); // ERROR 4: Evento ya calificado por el cliente
            }
        }

        // 5. Registrar la calificación
        for (int i = 0; i < entradas.cantElementos(); i++) {
            Entrada entrada = entradas.obtenerPorIndice(i);
            if (entrada.getCliente().getCedula().equals(cedula)) {
                entrada.setCalificacion(puntaje);
                entrada.setComentario(comentario);
                break;
            }
        }

        return Retorno.ok(); // OK: Calificación registrada
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
    public Retorno listarClientesDeEvento(String codigo, int n) {
        if (n < 1) {
            return Retorno.error2();
        }

        Evento evento = buscarEvento(codigo);
        if (evento == null) {
            return Retorno.error1();
        }

        ListaDoble<Entrada> entradas = evento.getEntradasVendidas();
        if (entradas.cantElementos() == 0) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

        StringBuilder resultado = new StringBuilder();
        int desde = Math.max(0, entradas.cantElementos() - n);

        for (int i = desde; i < entradas.cantElementos(); i++) {
            Entrada entrada = entradas.obtenerPorIndice(i);
            Cliente cliente = entrada.getCliente();
            if (resultado.length() > 0) {
                resultado.append("#");
            }
            resultado.append(cliente.getCedula()).append("-")
                    .append(cliente.getName());
        }

        return new Retorno(Retorno.Resultado.OK, resultado.toString());
    }

    @Override
    public Retorno listarEsperaEvento() {
        if (listaEventos.tamaño() == 0) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

        ListaSimple<Evento> eventosConEspera = new ListaSimple<>();
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (!evento.getListaEspera().esVacia()) {
                eventosConEspera.agregar(evento);
            }
        }

        // Ordenar eventos por código usando bubble sort
        eventosConEspera.bubbleSort((e1, e2) -> e1.getCodigo().compareTo(e2.getCodigo()));

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < eventosConEspera.tamaño(); i++) {
            Evento evento = eventosConEspera.obtenerPorIndice(i);
            Cola<Cliente> colaOriginal = evento.getListaEspera();
            Cola<Cliente> colaCopia = new Cola<>();

            ListaSimple<Cliente> listaEspera = new ListaSimple<>();

            while (!colaOriginal.esVacia()) {
                Cliente c = colaOriginal.desencolar();
                listaEspera.agregar(c);
                colaCopia.encolar(c);
            }

            while (!colaCopia.esVacia()) {
                colaOriginal.encolar(colaCopia.desencolar());
            }

            // Ordenar clientes por cédula usando bubble sort
            listaEspera.bubbleSort((c1, c2) -> c1.getCedula().compareTo(c2.getCedula()));

            // Agregar al resultado
            for (int j = 0; j < listaEspera.tamaño(); j++) {
                if (resultado.length() > 0) {
                    resultado.append("#");
                }
                resultado.append(evento.getCodigo()).append("-")
                        .append(listaEspera.obtenerPorIndice(j).getCedula());
            }
        }

        return new Retorno(Retorno.Resultado.OK, resultado.toString());
    }

    @Override
    public Retorno deshacerUtimasCompras(int n) {
        if (n <= 0) {
            return Retorno.error1();
        }

        if (listaEntradas.tamaño() == 0) {
            return Retorno.ok("No hay entradas vendidas para deshacer.");
        }

        int entradasADeshacer = Math.min(n, listaEntradas.tamaño());
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < entradasADeshacer; i++) {
            Entrada entrada = listaEntradas.eliminarFinal(); // ← IMPORTANTE
            Evento evento = entrada.getEvento();
            evento.getEntradasVendidas().borrarElemento(entrada);

            if (!evento.getListaEspera().esVacia()) {
                Cliente clienteEnEspera = evento.getListaEspera().desencolar();
                Entrada nuevaEntrada = new Entrada(evento, clienteEnEspera, Estado.ACTIVA);
                evento.getEntradasVendidas().agregarFinal(nuevaEntrada);
                listaEntradas.agregar(nuevaEntrada); // ← se registra la nueva entrada
            }

            if (resultado.length() > 0) {
                resultado.append("#");
            }
            resultado.append(evento.getCodigo()).append("-").append(entrada.getCliente().getCedula());
        }

        return Retorno.ok(resultado.toString());
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        if (listaEventos.tamaño() == 0) {
            return new Retorno(Retorno.Resultado.OK, ""); // No hay eventos
        }

        double mejorPromedio = -1;
        ListaSimple<Evento> eventosMejorPuntaje = new ListaSimple<>();

        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            int suma = 0;
            int cantidad = 0;

            for (int j = 0; j < evento.getEntradasVendidas().cantElementos(); j++) {
                Entrada entrada = evento.getEntradasVendidas().obtenerPorIndice(j);
                if (entrada.getCalificacion() != null) {
                    suma += entrada.getCalificacion();
                    cantidad++;
                }
            }

            if (cantidad > 0) {
                double promedio = (double) suma / cantidad;

                if (promedio > mejorPromedio) {
                    mejorPromedio = promedio;
                    eventosMejorPuntaje = new ListaSimple<>(); // limpiar anteriores
                    eventosMejorPuntaje.agregar(evento);
                } else if (promedio == mejorPromedio) {
                    eventosMejorPuntaje.agregar(evento);
                }
            }
        }

        if (eventosMejorPuntaje.tamaño() == 0) {
            return new Retorno(Retorno.Resultado.OK, ""); // No hay calificaciones
        }

        // Ordenar por código de evento
        ListaSimple<Evento> eventosOrdenados = new ListaSimple<>();
        while (eventosMejorPuntaje.tamaño() > 0) {
            Evento menor = eventosMejorPuntaje.obtenerPorIndice(0);
            int posMenor = 0;

            for (int i = 1; i < eventosMejorPuntaje.tamaño(); i++) {
                Evento actual = eventosMejorPuntaje.obtenerPorIndice(i);
                if (actual.getCodigo().compareTo(menor.getCodigo()) < 0) {
                    menor = actual;
                    posMenor = i;
                }
            }

            eventosOrdenados.agregar(menor);
            eventosMejorPuntaje.eliminar(menor);
        }

        // Armar string resultado
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eventosOrdenados.tamaño(); i++) {
            Evento e = eventosOrdenados.obtenerPorIndice(i);
            if (i > 0) {
                sb.append("#");
            }
            sb.append(e.getCodigo()).append("-").append((int) mejorPromedio); // promedio como entero
        }

        return new Retorno(Retorno.Resultado.OK, sb.toString());
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        // 1. Verificar cliente
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente == null) {
            return Retorno.error1();
        }

        // 2. Recopilar entradas del cliente
        ListaSimple<Entrada> entradasCliente = new ListaSimple<>();
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            IListaDoble<Entrada> entradas = evento.getEntradasVendidas();
            for (int j = 0; j < entradas.cantElementos(); j++) {
                Entrada e = entradas.obtenerPorIndice(j);
                if (e.getCliente().getCedula().equals(cedula)) {
                    entradasCliente.agregar(e);
                }
            }
        }

        // 3. Ordenar por fecha (más antigua primero)
        entradasCliente.bubbleSort((e1, e2)
                -> e1.getEvento().getFecha().compareTo(e2.getEvento().getFecha())
        );

        // 4. Construir resultado EXACTO como espera el test
        StringBuilder sb = new StringBuilder();

        if (entradasCliente.tamaño() > 0) {
            Entrada e0 = entradasCliente.obtenerPorIndice(0);
            Evento ev0 = e0.getEvento();

            // Solo código del evento, sin corchete
            sb.append(ev0.getCodigo());
            sb.append("-"); // separador guion

            // Abro corchete justo antes de la descripcion
            sb.append(ev0.getDescripcion())
                    .append("-")
                    .append(ev0.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            for (int i = 1; i < entradasCliente.tamaño(); i++) {
                Entrada e = entradasCliente.obtenerPorIndice(i);
                Evento ev = e.getEvento();
                sb.append("#")
                        .append(ev.getCodigo())
                        .append("-")
                        .append(ev.getDescripcion())
                        .append("-")
                        .append(ev.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            ;
        } else {
            return Retorno.ok("");
        }
        return Retorno.ok(sb.toString());
    }


    
@Override
public Retorno comprasXDia(int mes) {
    // 1. Validar que el mes esté entre 1 y 12
    if (mes < 1 || mes > 12) {
        return Retorno.error1();
    }

    // 2. Crear listas para almacenar fechas y cantidades
    ListaSimple<String> fechas = new ListaSimple<>();
    ListaSimple<Integer> cantidades = new ListaSimple<>();

    // 3. Recorrer todos los eventos
    for (int i = 0; i < listaEventos.tamaño(); i++) {
        Evento evento = listaEventos.obtenerPorIndice(i);
        LocalDate fechaEvento = evento.getFecha();

        // 4. Filtrar por mes
        if (fechaEvento.getMonthValue() == mes) {
            String fechaStr = fechaEvento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int entradasVendidas = evento.getEntradasVendidas().cantElementos();

            // 5. Buscar si la fecha ya existe
            boolean encontrada = false;
            for (int j = 0; j < fechas.tamaño(); j++) {
                if (fechas.obtenerPorIndice(j).equals(fechaStr)) {
                    // 6. Sumar a la cantidad existente
                    int cantidadActual = cantidades.obtenerPorIndice(j);
                    cantidades.modificarElemento(j, cantidadActual + entradasVendidas);
                    encontrada = true;
                    break;
                }
            }

            // 7. Si es una fecha nueva, agregarla
            if (!encontrada) {
                fechas.agregar(fechaStr);
                cantidades.agregar(entradasVendidas);
            }
        }
    }

    // 8. Ordenar las fechas cronológicamente
    ordenarFechasYCantidades(fechas, cantidades);

    // 9. Construir el string de resultado
    StringBuilder resultado = new StringBuilder();
    for (int i = 0; i < fechas.tamaño(); i++) {
        if (i > 0) {
            resultado.append("#");
        }
        resultado.append(fechas.obtenerPorIndice(i))
                .append("-")
                .append(cantidades.obtenerPorIndice(i)+1);
    }

    return Retorno.ok(resultado.toString());
}

// Método auxiliar para ordenar por fecha (implementación con Bubble Sort)
private void ordenarFechasYCantidades(ListaSimple<String> fechas, ListaSimple<Integer> cantidades) {
    for (int i = 0; i < fechas.tamaño() - 1; i++) {
        for (int j = 0; j < fechas.tamaño() - i - 1; j++) {
            String fechaActual = fechas.obtenerPorIndice(j);
            String fechaSiguiente = fechas.obtenerPorIndice(j + 1);
            
            // Comparar fechas en formato dd/MM/yyyy como strings
            if (fechaActual.compareTo(fechaSiguiente) > 0) {
                // Intercambiar fechas
                String tempFecha = fechaActual;
                fechas.modificarElemento(j, fechaSiguiente);
                fechas.modificarElemento(j + 1, tempFecha);
                
                // Intercambiar cantidades correspondientes
                int tempCant = cantidades.obtenerPorIndice(j);
                cantidades.modificarElemento(j, cantidades.obtenerPorIndice(j + 1));
                cantidades.modificarElemento(j + 1, tempCant);
            }
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public Evento buscarEvento(String codigo) {
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigo)) {
                return evento;
            }
        }
        return null;
    }

}
