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
import tads.InterfacesTads.IPila;
import tads.Pila;

public class Sistema implements IObligatorio {

    private IListaSimple<Cliente> listaClientes;
    private IListaSimple<Sala> listaSalas;
    private IListaSimple<Evento> listaEventos;
    public IPila<Entrada> pilaEntradas;

    public Sistema() {
        listaClientes = new ListaSimple<Cliente>();
        listaSalas = new ListaSimple<Sala>();
        listaEventos = new ListaSimple<Evento>();
        pilaEntradas = new Pila<Entrada>();
    }

    /*
    Pre-condiciones: Ninguna.
    Post-condiciones:
    Se inicializan las listas listaClientes, listaSalas, listaEventos como nuevas ListaSimple vacías.
    Se inicializa la pila pilaEntradas como una nueva Pila vacía.
    Retorna Retorno.ok().
     */
    @Override
    public Retorno crearSistemaDeGestion() {
        listaClientes = new ListaSimple<Cliente>();
        listaSalas = new ListaSimple<Sala>();
        listaEventos = new ListaSimple<Evento>();
        return Retorno.ok();
    }

    /*
    Pre-condiciones:
    nombre no debe ser nulo ni una cadena vacía (después de recortar espacios en blanco).
    capacidad debe ser mayor que 0.
    No debe existir ya una sala con el mismo nombre (insensible a mayúsculas/minúsculas o espacios en blanco al inicio/final).
    
    Post-condiciones:
    Si capacidad no es válida, retorna Retorno.ERROR_2.
    Si nombre no es válido o ya existe una sala con ese nombre, retorna Retorno.ERROR_1.
    Si las condiciones anteriores se cumplen, se crea una nueva Sala con el nombre y capacidad proporcionados.
    La nueva sala se agrega al inicio de listaSalas.
    Retorna Retorno.OK.
     */
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

    /*
    Pre-condiciones:
    nombre no debe ser nulo ni una cadena vacía (después de recortar espacios en blanco).
    Debe existir una sala con el nombre proporcionado en listaSalas.
    
    Post-condiciones:
    Si nombre no es válido o no existe una sala con ese nombre, retorna Retorno.ERROR_1.
    Si la sala existe, se elimina de listaSalas.
    Retorna Retorno.OK.
     */
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

    /*
    
    Pre-condiciones:
    aforoNecesario debe ser mayor que 0.
    fecha debe ser una fecha válida (día entre 1 y 30, mes entre 1 y 12).
    No debe existir un evento con el mismo codigo.
    Debe haber una Sala disponible con capacidad suficiente (aforoNecesario) y que no esté ocupada en la fecha especificada.

    Post-condiciones:
    Si aforoNecesario no es válido, retorna Retorno.ERROR_2.
    Si fecha no es válida o no se encuentra una sala disponible, retorna Retorno.ERROR_3.
    Si ya existe un evento con el codigo, retorna Retorno.ERROR_1.
    Si todas las condiciones se cumplen, se busca la sala disponible con la menor capacidad que cumpla los requisitos.
    La fecha del evento se marca como ocupada en la sala asignada.
    Se crea un nuevo Evento y se agrega al final de listaEventos.
    Retorna Retorno.OK.
     */
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

    /*
    Pre-condiciones:
    cedula debe tener exactamente 8 caracteres y ser todos dígitos numéricos.
    No debe existir ya un cliente con la misma cedula.
    
    Post-condiciones:
    Si la cedula no es válida, retorna Retorno.ERROR_1.
    Si ya existe un cliente con la misma cedula, retorna Retorno.ERROR_2.
    Si las condiciones se cumplen, se crea un nuevo Cliente con el nombre y cedula proporcionados.
    El nuevo cliente se agrega a listaClientes.
    Retorna Retorno.OK.
    
     */
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

        if (cedula == null || cedula.length() != 8) {
            return false;
        }

        for (int i = 0; i < cedula.length(); i++) {
            char c = cedula.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    /*
    Pre-condiciones:
    Debe existir un cliente con la cedula proporcionada.
    Debe existir un evento con el codigoEvento proporcionado.
    
    Post-condiciones:
    Si el cliente no existe, retorna Retorno.error1().
    Si el evento no existe, retorna Retorno.error2().
    Si hay disponibilidad en la sala del evento (entradas vendidas < capacidad de la sala):
    Se crea una nueva Entrada con el Evento, Cliente y Estado.ACTIVA.
    La nuevaEntrada se agrega al final de la lista de entradas vendidas del evento.
    La nuevaEntrada se apila en pilaEntradas.
    Retorna Retorno.ok().
    Si no hay disponibilidad en la sala del evento:
    El Cliente se encola en la lista de espera del Evento.
    Retorna Retorno.ok("Cliente agregado a lista de espera")
     */
    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {

        Cliente clienteBuscado = buscarClientePorCedula(cedula);
        if (clienteBuscado == null) {
            return Retorno.error1();
        }

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

        int entradasVendidas = eventoEncontrado.getEntradasVendidas().getCantidadnodos();
        int capacidadSala = eventoEncontrado.getSalaAsignada().getCapacidad();

        if (entradasVendidas < capacidadSala) {

            Entrada nuevaEntrada = new Entrada(eventoEncontrado, clienteBuscado, Estado.ACTIVA);
            eventoEncontrado.getEntradasVendidas().agregarFinal(nuevaEntrada);
            pilaEntradas.apilar(nuevaEntrada);
            return Retorno.ok();
        } else {

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

    /*
    Pre-condiciones:
    Debe existir un evento con el codigo proporcionado.
    El evento no debe tener entradas vendidas.
    
    Post-condiciones:
    Si el evento no existe, retorna Retorno.error1().
    Si el evento tiene entradas vendidas, retorna Retorno.error2().
    Si las condiciones se cumplen:
    Se libera la fecha ocupada en la sala asignada al evento.
    El evento se elimina de listaEventos.
    Retorna Retorno.ok().
    
     */
    @Override
    public Retorno eliminarEvento(String codigo) {

        Evento eventoAEliminar = null;
        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            if (evento.getCodigo().equals(codigo)) {
                eventoAEliminar = evento;
                break;
            }
        }

        if (eventoAEliminar == null) {
            return Retorno.error1();
        }

        if (eventoAEliminar.getEntradasVendidas().cantElementos() > 0) {
            return Retorno.error2();
        }

        Sala salaAsignada = eventoAEliminar.getSalaAsignada();
        if (salaAsignada != null) {
            salaAsignada.getFechasOcupadas().eliminar(eventoAEliminar.getFecha());
        }

        listaEventos.eliminar(eventoAEliminar);

        return Retorno.ok();
    }

    /*
    Pre-condiciones:
    Debe existir un cliente con la cedula proporcionada.
    Debe existir un evento con el codigoEvento proporcionado.
    El cliente debe tener al menos una entrada activa para el evento especificado.
    
    Post-condiciones:
    Si el cliente no existe, retorna Retorno.error1().
    Si el evento no existe, retorna Retorno.error2().
    Si el cliente no tiene una entrada activa para el evento, retorna Retorno.error1().
    Si una entrada activa del cliente para el evento es encontrada y eliminada:
    Si la lista de espera del evento no está vacía, el primer cliente en la cola es desencolado.
    Se crea una nueva Entrada para este cliente de la lista de espera, se le asigna el evento y Estado.ACTIVA.
    La nueva entrada se agrega al final de las entradasVendidas del evento.
    Retorna Retorno.ok().
    
     */
    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {

        Cliente clienteBuscado = buscarClientePorCedula(cedula);
        if (clienteBuscado == null) {
            return Retorno.error1();
        }

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
            return Retorno.error1();
        }

        if (!eventoEncontrado.getListaEspera().esVacia()) {
            Cliente primerEnEspera = eventoEncontrado.getListaEspera().desencolar();
            Entrada nuevaEntrada = new Entrada(eventoEncontrado, primerEnEspera, Estado.ACTIVA);
            eventoEncontrado.getEntradasVendidas().agregarFinal(nuevaEntrada);
        }

        return Retorno.ok();
    }

    /*
    Pre-condiciones:
    Debe existir un cliente con la cedula proporcionada.
    Debe existir un evento con el codigoEvento proporcionado.
    puntaje debe ser un valor entre 1 y 10 (ambos inclusive).
    El cliente no debe haber calificado previamente el evento.
    
    Post-condiciones:
    Si el cliente no existe, retorna Retorno.error1().
    Si el evento no existe, retorna Retorno.error2().
    Si el puntaje no es válido, retorna Retorno.error3().
    Si el cliente ya calificó el evento, retorna Retorno.error4().
    Si las condiciones se cumplen, se busca la entrada del cliente para el evento.
    Se establece el puntaje y el comentario en la entrada encontrada.
    Retorna Retorno.ok().
     */
    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {

        Cliente clienteBuscado = buscarClientePorCedula(cedula);
        if (clienteBuscado == null) {
            return Retorno.error1();
        }

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

        if (puntaje < 1 || puntaje > 10) {
            return Retorno.error3();
        }

        IListaDoble<Entrada> entradas = eventoEncontrado.getEntradasVendidas();
        for (int i = 0; i < entradas.cantElementos(); i++) {
            Entrada entrada = entradas.obtenerPorIndice(i);
            if (entrada.getCliente().getCedula().equals(cedula) && entrada.getCalificacion() != null) {
                return Retorno.error4();
            }
        }

        for (int i = 0; i < entradas.cantElementos(); i++) {
            Entrada entrada = entradas.obtenerPorIndice(i);
            if (entrada.getCliente().getCedula().equals(cedula)) {
                entrada.setCalificacion(puntaje);
                entrada.setComentario(comentario);
                break;
            }
        }

        return Retorno.ok();
    }

    /*
    Pre-condiciones:Ninguna.

    Post-condiciones:
    Si no hay salas registradas (listaSalas está vacía), retorna Retorno.OK con el mensaje "No hay salas registradas.".
    Si hay salas, retorna Retorno.OK con una cadena que contiene las salas en el formato "nombre-capacidad" separadas por "#". El orden de las salas en la cadena de salida no está garantizado de forma específica (depende del orden en que se agregaron a la lista y cómo se copian/procesan internamente si no hay un ordenamiento explícito).
     */
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

    /*
    Pre-condiciones:Ninguna.
    
    Post-condiciones:
    Si listaEventos es nula o está vacía, retorna Retorno.ok() (sin mensaje o con mensaje vacío, dependiendo de la implementación de Retorno.ok()).
    Si hay eventos, retorna Retorno.ok() con una cadena que contiene la información de los eventos, ordenada por código de evento de forma ascendente. El formato de cada evento en la cadena es "codigo-descripcion-nombreSala-disponibles-vendidas", separados por "#".
     */
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

    /*
    Pre-condiciones:Ninguna.
    
    Post-condiciones:
    Si no hay clientes registrados (listaClientes está vacía), retorna Retorno.OK con el mensaje "No hay clientes registrados.".
    Si hay clientes, retorna Retorno.OK con una cadena que contiene los clientes en el formato "cedula-nombre" separados por "#", ordenados por cédula de forma ascendente.
     */
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

    /*
    Pre-condiciones:
    vistaSala no debe ser nulo.
    vistaSala debe tener al menos una fila y al menos una columna.
    
    Post-condiciones:
    Si vistaSala no es válida (nula, o sin filas/columnas), retorna Retorno.error1().
    Si es válida, analiza la matriz vistaSala para determinar si es "óptima".
    Una sala es considerada "óptima" si tiene al menos 2 columnas "óptimas".
    Una columna es "óptima" si el máximo número de asientos "O" (ocupados) consecutivos en esa columna es mayor que el número de asientos "X" (libres) en esa misma columna.
    Retorna Retorno.ok("Es óptimo") si la sala cumple el criterio de ser óptima.
    Retorna Retorno.ok("No es óptimo") si la sala no cumple el criterio de ser óptima.
     */
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

    /*
    Pre-condiciones:
    n debe ser mayor o igual a 1.
    Debe existir un evento con el codigo proporcionado.
    
    Post-condiciones:
    Si n es menor que 1, retorna Retorno.error2().
    Si no se encuentra un evento con el codigo proporcionado, retorna Retorno.error1().
    Si el evento existe pero no tiene entradas vendidas, retorna Retorno.OK con una cadena vacía.
    Si hay entradas vendidas, retorna Retorno.OK con una cadena que lista los últimos n clientes que compraron entradas para el evento. El formato es "cedula-nombre" y los clientes están separados por "#". Si el número total de entradas vendidas es menor que n, se listan todos los clientes. Los clientes se listan en el orden en que se agregaron a la lista de entradas vendidas del evento (es decir, los más recientes primero, hasta n).
     */
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
    /*
    Pre-condiciones:Ninguna.
    
    Post-condiciones:
    Si listaEventos está vacía, retorna Retorno.OK con una cadena vacía.
    Recorre todos los eventos y filtra aquellos que tienen clientes en su lista de espera.
    Ordena los eventos con clientes en espera por su código de evento de forma ascendente.
    Para cada evento en espera, desencola y vuelve a encolar a los clientes para copiarlos en una lista simple.
    Ordena los clientes en la lista de espera de cada evento por su cédula de forma ascendente.
    Retorna Retorno.OK con una cadena que lista los eventos y sus clientes en espera. El formato es "codigoEvento-cedulaCliente" y los pares están separados por "#". Si hay múltiples clientes para un evento, cada uno aparece como un par separado por "#".
     */
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

            listaEspera.bubbleSort((c1, c2) -> c1.getCedula().compareTo(c2.getCedula()));

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

    /*
    Pre-condiciones:
    n debe ser mayor que 0.
    
    Post-condiciones:
    Si n es menor o igual a 0, retorna Retorno.error1().
    Si pilaEntradas está vacía, retorna Retorno.ok("No hay entradas vendidas para deshacer.").
    Desapila hasta n entradas de pilaEntradas (o menos si la pila tiene menos de n elementos).
    Para cada entrada desapilada:
    Se elimina la entrada de la lista de entradas vendidas del evento correspondiente.
    Si la lista de espera del evento no está vacía, el primer cliente en espera es desencolado, se crea una nueva entrada para él, se agrega a las entradas vendidas del evento y se apila en pilaEntradas.
    Retorna Retorno.ok() con una cadena que lista los códigos de evento y las cédulas de los clientes cuyas compras fueron deshechas (o reasignadas). El formato es "codigoEvento-cedulaCliente" y los pares están separados por "#". La lista de resultados está ordenada primero por codigoEvento ascendente, y luego por cedulaCliente ascendente si los códigos de evento son iguales.
     */
    @Override
    public Retorno deshacerUtimasCompras(int n) {
        if (n <= 0) {
            return Retorno.error1();
        }

        if (pilaEntradas.esVacia()) {
            return Retorno.ok("No hay entradas vendidas para deshacer.");
        }

        ListaSimple<Entrada> entradasADeshacer = new ListaSimple<>();
        int contador = 0;
        while (contador < n && !pilaEntradas.esVacia()) {
            entradasADeshacer.agregarFin(pilaEntradas.desapilar());
            contador++;
        }

        ListaSimple<String> resultados = new ListaSimple<>();
        for (int i = 0; i < entradasADeshacer.tamaño(); i++) {
            Entrada entrada = entradasADeshacer.obtenerPorIndice(i);
            Evento evento = entrada.getEvento();

            evento.getEntradasVendidas().borrarElemento(entrada);

            if (!evento.getListaEspera().esVacia()) {
                Cliente clienteEnEspera = evento.getListaEspera().desencolar();
                Entrada nuevaEntrada = new Entrada(evento, clienteEnEspera, Estado.ACTIVA);
                evento.getEntradasVendidas().agregarFinal(nuevaEntrada);
                pilaEntradas.apilar(nuevaEntrada);
            }

            resultados.agregar(evento.getCodigo() + "-" + entrada.getCliente().getCedula());
        }

        for (int i = 0; i < resultados.tamaño() - 1; i++) {
            for (int j = 0; j < resultados.tamaño() - 1 - i; j++) {
                String s1 = resultados.obtenerPorIndice(j);
                String s2 = resultados.obtenerPorIndice(j + 1);

                String[] partesA = s1.split("-");
                String[] partesB = s2.split("-");

                int cmpCodigo = partesA[0].compareTo(partesB[0]);

                if (cmpCodigo > 0) {

                    resultados.modificarElemento(j, s2);
                    resultados.modificarElemento(j + 1, s1);
                } else if (cmpCodigo == 0) {
                    int cmpCedula = partesA[1].compareTo(partesB[1]);
                    if (cmpCedula > 0) {

                        resultados.modificarElemento(j, s2);
                        resultados.modificarElemento(j + 1, s1);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultados.tamaño(); i++) {
            if (i > 0) {
                sb.append("#");
            }
            sb.append(resultados.obtenerPorIndice(i));
        }

        return Retorno.ok(sb.toString());
    }

    /*
    Pre-condiciones:Ninguna.
    
    Post-condiciones:
    Si no hay eventos registrados (listaEventos está vacía) o si ningún evento tiene calificaciones, retorna Retorno.OK con una cadena vacía.
    Calcula el promedio de las calificaciones para cada evento.
    Identifica el (o los) evento(s) con el promedio de calificación más alto.
    Si hay múltiples eventos con el mismo mejor promedio, se incluyen todos.
    Los eventos con el mejor promedio se ordenan por su código de evento de forma ascendente.
    Retorna Retorno.OK con una cadena que lista los códigos de los eventos mejor puntuados y su puntaje promedio (como entero). El formato es "codigoEvento-puntaje" y los pares están separados por "#".
     */
    @Override
    public Retorno eventoMejorPuntuado() {
        if (listaEventos.tamaño() == 0) {
            return new Retorno(Retorno.Resultado.OK, "");
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
                    eventosMejorPuntaje = new ListaSimple<>();
                    eventosMejorPuntaje.agregar(evento);
                } else if (promedio == mejorPromedio) {
                    eventosMejorPuntaje.agregar(evento);
                }
            }
        }

        if (eventosMejorPuntaje.tamaño() == 0) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

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

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eventosOrdenados.tamaño(); i++) {
            Evento e = eventosOrdenados.obtenerPorIndice(i);
            if (i > 0) {
                sb.append("#");
            }
            sb.append(e.getCodigo()).append("-").append((int) mejorPromedio);
        }

        return new Retorno(Retorno.Resultado.OK, sb.toString());
    }

    /*
    Pre-condiciones:
    Debe existir un cliente con la cedula proporcionada.
    
    Post-condiciones:
    Si el cliente no existe, retorna Retorno.error1().
    Si el cliente existe pero no tiene compras de entradas, retorna Retorno.ok("").
    Si el cliente tiene compras, retorna Retorno.ok() con una cadena que lista todas las compras del cliente. Las compras se ordenan cronológicamente por la fecha del evento (más antigua primero). El formato de cada compra en la cadena es "codigoEvento-descripcionEvento-fechaEvento (dd/MM/yyyy)", y las compras están separadas por "#".
     */
    @Override
    public Retorno comprasDeCliente(String cedula) {

        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente == null) {
            return Retorno.error1();
        }

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

        entradasCliente.bubbleSort((e1, e2)
                -> e1.getEvento().getFecha().compareTo(e2.getEvento().getFecha())
        );

        StringBuilder sb = new StringBuilder();

        if (entradasCliente.tamaño() > 0) {
            Entrada e0 = entradasCliente.obtenerPorIndice(0);
            Evento ev0 = e0.getEvento();

            sb.append(ev0.getCodigo());
            sb.append("-");

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

    /*
    Pre-condiciones:
    mes debe ser un valor entre 1 y 12 (ambos inclusive).
    
    Post-condiciones:
    Si mes no está en el rango válido (1-12), retorna Retorno.error1().
    Recopila todas las entradas vendidas para eventos que ocurren en el mes especificado.
    Agrupa las entradas por fecha y cuenta la cantidad de entradas vendidas para cada día.
    Ordena los resultados por fecha cronológicamente.
    Retorna Retorno.ok() con una cadena que lista la cantidad de entradas vendidas por día para el mes dado. El formato es "dd/MM/yyyy-cantidadEntradas", y los pares están separados por "#". La cantidad de entradas se incrementa en 1, según la implementación del código.
     */
    @Override
    public Retorno comprasXDia(int mes) {

        if (mes < 1 || mes > 12) {
            return Retorno.error1();
        }

        ListaSimple<String> fechas = new ListaSimple<>();
        ListaSimple<Integer> cantidades = new ListaSimple<>();

        for (int i = 0; i < listaEventos.tamaño(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i);
            LocalDate fechaEvento = evento.getFecha();

            if (fechaEvento.getMonthValue() == mes) {
                String fechaStr = fechaEvento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int entradasVendidas = evento.getEntradasVendidas().cantElementos();

                boolean encontrada = false;
                for (int j = 0; j < fechas.tamaño(); j++) {
                    if (fechas.obtenerPorIndice(j).equals(fechaStr)) {

                        int cantidadActual = cantidades.obtenerPorIndice(j);
                        cantidades.modificarElemento(j, cantidadActual + entradasVendidas);
                        encontrada = true;
                        break;
                    }
                }

                if (!encontrada) {
                    fechas.agregar(fechaStr);
                    cantidades.agregar(entradasVendidas);
                }
            }
        }

        ordenarFechasYCantidades(fechas, cantidades);

        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < fechas.tamaño(); i++) {
            if (i > 0) {
                resultado.append("#");
            }
            resultado.append(fechas.obtenerPorIndice(i))
                    .append("-")
                    .append(cantidades.obtenerPorIndice(i) + 1);
        }

        return Retorno.ok(resultado.toString());
    }

    private void ordenarFechasYCantidades(ListaSimple<String> fechas, ListaSimple<Integer> cantidades) {
        for (int i = 0; i < fechas.tamaño() - 1; i++) {
            for (int j = 0; j < fechas.tamaño() - i - 1; j++) {
                String fechaActual = fechas.obtenerPorIndice(j);
                String fechaSiguiente = fechas.obtenerPorIndice(j + 1);

                if (fechaActual.compareTo(fechaSiguiente) > 0) {

                    String tempFecha = fechaActual;
                    fechas.modificarElemento(j, fechaSiguiente);
                    fechas.modificarElemento(j + 1, tempFecha);

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
