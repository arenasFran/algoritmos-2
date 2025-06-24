package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class IObligatorioTest {

    private Sistema sistema;

    public IObligatorioTest() {
        sistema = new Sistema();
    }

    @Before
    public void setUp() {
        sistema = new Sistema();
        sistema.crearSistemaDeGestion();
    }

    //PRUEBAS DE PRIMERA PARTE//
    @Test
    public void testCrearSistemaDeGestion() {
        Retorno ret = sistema.crearSistemaDeGestion();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    // ----- SALAS -----
    @Test
    public void testRegistrarSala_OK() {
        Retorno ret = sistema.registrarSala("Sala A", 50);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarSala_ERROR1_Duplicada() {
        sistema.registrarSala("Sala A", 50);

        Retorno ret = sistema.registrarSala("Sala A", 100);
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarSala_ERROR2_CapacidadInvalida() {
        Retorno ret = sistema.registrarSala("Sala B", 0);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

        ret = sistema.registrarSala("Sala C", -10);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

        ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testEliminarSala_OK() {
        sistema.registrarSala("Sala A", 50);
        Retorno ret = sistema.eliminarSala("Sala A");

        ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.eliminarSala("Sala B");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

    }

    @Test
    public void testEliminarSala_ERROR1() {
        sistema.registrarSala("Sala A", 50);
        sistema.registrarSala("Sala C", 50);
        sistema.registrarSala("Sala H", 10);
        Retorno ret = sistema.eliminarSala("Sala B");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        ret = sistema.eliminarSala("Sala A");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        ret = sistema.eliminarSala("Sala H");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testListarSalas_OK() {
        sistema.registrarSala("Sala A", 50);
        sistema.registrarSala("Sala B", 70);
        sistema.registrarSala("Sala C", 100);
        Retorno ret = sistema.listarSalas();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("Sala C-100#Sala B-70#Sala A-50", ret.valorString);
    }

    @Test
    public void testListarSalas_Vacio() {
        Retorno ret = sistema.listarSalas();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    // ----- CLIENTES -----
    @Test
    public void testRegistrarCliente_OK() {
        Retorno ret = sistema.registrarCliente("12345678", "Juan Pérez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarCliente_ERROR1_CedulaInvalida() {
        Retorno ret = sistema.registrarCliente("1234", "Juan Pérez");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("AA345444", "Martina Perez");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testRegistrarCliente_ERROR2_Duplicado() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        Retorno ret = sistema.registrarCliente("12345678", "Otro Nombre");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("22222222", "Rodolfo Perez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarCliente("11111111", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testListarClientes_OK() {
        sistema.registrarCliente("45678992", "Micaela Ferrez");
        sistema.registrarCliente("23331111", "Martina Rodríguez");
        sistema.registrarCliente("35679992", "Ramiro Perez");

        Retorno ret = sistema.listarClientes();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("23331111-Martina Rodríguez#35679992-Ramiro Perez#45678992-Micaela Ferrez", ret.valorString);
    }

    @Test
    public void testListarClientes_Vacio() {
        Retorno ret = sistema.listarClientes();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    // ----- EVENTOS -----
    @Test
    public void testRegistrarEvento_OK() {
        sistema.registrarSala("Sala A", 75);
        sistema.registrarSala("Sala B", 100);
        sistema.registrarSala("Sala C", 70);

        LocalDate fecha1 = LocalDate.of(2025, 5, 10);
        LocalDate fecha2 = LocalDate.of(2025, 5, 4);

        Retorno ret = sistema.registrarEvento("EVT01", "Concierto", 80, fecha1);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT02", "Arte", 74, fecha1);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarEvento_Error1() {

        sistema.registrarSala("Sala A", 50);
        LocalDate fecha = LocalDate.of(2025, 5, 10);

        sistema.registrarSala("Sala B", 20);

        Retorno ret = sistema.registrarEvento("EVT02", "Musica", 40, LocalDate.of(2025, 5, 12));
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT07", "Otro", 10, LocalDate.of(2025, 5, 12));
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT02", "Duplicado", 40, LocalDate.of(2025, 5, 1));
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testRegistrarEvento_Error2() {

        sistema.registrarSala("Sala A", 50);
        LocalDate fecha = LocalDate.of(2025, 5, 10);

        Retorno ret = sistema.registrarEvento("EVT01", "Concierto", 20, fecha);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT03", "Otro", -4, fecha);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testRegistrarEvento_Error3() {

        sistema.registrarSala("Sala A", 50);
        sistema.registrarSala("Sala B", 32);
        sistema.registrarSala("Sala T", 10);
        LocalDate fecha1 = LocalDate.of(2025, 5, 10);
        LocalDate fecha2 = LocalDate.of(2025, 5, 5);

        Retorno ret = sistema.registrarEvento("EVT01", "Concierto", 40, fecha1);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarEvento("EVT02", "Concierto", 38, fecha1);
        assertEquals(Retorno.Resultado.ERROR_3, ret.resultado);

        ret = sistema.registrarEvento("EVT06", "Deportes", 40, fecha2);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testListarEventos_OK() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("TEC43", "Seminario de Tecnología", 45, LocalDate.of(2025, 5, 4));
        sistema.registrarEvento("KAK34", "Noche de Rock", 45, LocalDate.of(2025, 5, 6));
        sistema.registrarEvento("CUC22", "Tango Azul", 11, LocalDate.of(2025, 5, 8));

        Retorno ret = sistema.listarEventos();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("CUC22-Tango Azul-Sala A-11-0#KAK34-Noche de Rock-Sala A-45-0#TEC43-Seminario de Tecnología-Sala A-45-0", ret.valorString);
    }

    // ----- SALA ÓPTIMA -----
    @Test
    public void testEsSalaOptima_EsOptimo() {
        String[][] vistaSala = {
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "O", "O", "X", "O", "O", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "O", "O", "X"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };

        Retorno ret = sistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("Es óptimo", ret.valorString);
    }

    @Test
    public void testEsSalaOptima_NoEsOptimo() {
        String[][] vistaSala = {
            {"#", "#", "#", "#"},
            {"#", "X", "O", "#"},
            {"#", "X", "X", "#"},
            {"#", "O", "X", "#"}
        };

        Retorno ret = sistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("No es óptimo", ret.valorString);
    }

    //////////////////////////////PRUEBAS DE SEGUNDA PARTE////////////////////////////////////////////////////////////////////////////////////////////////
    //COMPRA DE ENTRADAS//
    @Test
    public void testComprarEntrada_OK_CompraDirecta() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarSala("Sala A", 2);
        sistema.registrarEvento("EVT01", "Teatro", 2, LocalDate.of(2025, 6, 15));

        Retorno ret = sistema.comprarEntrada("12345678", "EVT01");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testComprarEntrada_OK_MultiplesClientes() {
        // Crear 5 clientes
        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");
        sistema.registrarCliente("10000003", "Cliente 3");
        sistema.registrarCliente("10000004", "Cliente 4");
        sistema.registrarCliente("10000005", "Cliente 5");

        // Crear sala y evento con 5 lugares
        sistema.registrarSala("Sala Principal", 5);
        sistema.registrarEvento("EVT100", "Show Musical", 5, LocalDate.of(2025, 6, 22));

        // Comprar entradas para todos
        for (int i = 1; i <= 5; i++) {
            String cedula = String.format("1000000%d", i);
            Retorno ret = sistema.comprarEntrada(cedula, "EVT100");
            assertEquals("Fallo para el cliente " + i, Retorno.Resultado.OK, ret.resultado);
        }

        // Ahora agregar uno más → debería ir a lista de espera (a menos que validen duplicados o cupo exacto)
        sistema.registrarCliente("10000006", "Cliente 6");
        Retorno retEspera = sistema.comprarEntrada("10000006", "EVT100");
        assertEquals(Retorno.Resultado.OK, retEspera.resultado); // asumimos que OK = agregado a espera

        // Podés luego verificar con listarEsperaEvento() que esté en la lista
    }

    @Test
    public void testComprarEntrada_OK_ListaDeEspera() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarCliente("87654321", "Ana Torres");
        sistema.registrarSala("Sala A", 1);
        sistema.registrarEvento("EVT01", "Teatro", 1, LocalDate.of(2025, 6, 15));

        sistema.comprarEntrada("12345678", "EVT01"); // se la lleva
        Retorno ret = sistema.comprarEntrada("87654321", "EVT01"); // va a lista de espera
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        //Verificar que esté en lista de espera con listarEsperaEvento()
    }

    @Test
    public void testComprarEntrada_ERROR1_ClienteNoExiste() {
        sistema.registrarSala("Sala A", 10);
        sistema.registrarEvento("EVT01", "Charla", 10, LocalDate.of(2025, 6, 10));

        Retorno ret = sistema.comprarEntrada("99999999", "EVT01");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testComprarEntrada_ERROR2_EventoNoExiste() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        Retorno ret = sistema.comprarEntrada("12345678", "XXX99");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testComprarEntrada_RepetidaEnListaDeEspera() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarSala("Sala A", 1);
        sistema.registrarEvento("EVT01", "Teatro", 1, LocalDate.of(2025, 6, 10));

        sistema.comprarEntrada("12345678", "EVT01"); // se la lleva
        sistema.registrarCliente("87654321", "Ana Torres");
        sistema.comprarEntrada("87654321", "EVT01"); // va a espera

        // intento que el mismo cliente vuelva a comprar (ya está en espera)
        Retorno ret = sistema.comprarEntrada("87654321", "EVT01");
        //mismo cliente
        assertEquals(Retorno.Resultado.OK, ret.resultado); // si decidís validar eso
    }

    //ELIMINAR EVENTO
    @Test
    public void testEliminarEvento_OK_SinEntradas() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Charla Libre", 50, LocalDate.of(2025, 6, 20));

        Retorno ret = sistema.eliminarEvento("EVT01");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testEliminarEvento_LiberaSala() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Evento 1", 50, LocalDate.of(2025, 6, 15));
        sistema.eliminarEvento("EVT01");

        // Ahora debería poder registrarse otro evento ese mismo día con misma sala
        Retorno ret = sistema.registrarEvento("EVT02", "Evento 2", 50, LocalDate.of(2025, 6, 15));
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testEliminarEvento_ERROR1_NoExiste() {
        Retorno ret = sistema.eliminarEvento("EVT999");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testEliminarEvento_ERROR2_ConEntradasVendidas() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarEvento("EVT01", "Concierto", 80, LocalDate.of(2025, 6, 25));
        sistema.comprarEntrada("12345678", "EVT01");

        Retorno ret = sistema.eliminarEvento("EVT01");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    // DEVOLVER ENTRADA//
    @Test
    public void testDevolverEntrada_OK_SinEspera() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Evento Libre", 50, LocalDate.of(2025, 6, 20));
        sistema.comprarEntrada("12345678", "EVT01");

        Retorno ret = sistema.devolverEntrada("12345678", "EVT01");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testDevolverEntrada_ReasignaAListaEspera() {
        // Registro de clientes
        sistema.registrarCliente("11111111", "Cliente1");
        sistema.registrarCliente("22222222", "Cliente2");
        sistema.registrarCliente("33333333", "Cliente3");

        // Sala y evento con solo una entrada
        sistema.registrarSala("Sala B", 1);
        sistema.registrarEvento("EVT02", "Cupo limitado", 1, LocalDate.of(2025, 6, 21));

        // Cliente1 compra la única entrada
        sistema.comprarEntrada("11111111", "EVT02");

        // Cliente2 y Cliente3 intentan comprar: van a lista de espera
        sistema.comprarEntrada("22222222", "EVT02");
        sistema.comprarEntrada("33333333", "EVT02");

        // Cliente1 devuelve su entrada → debería ser reasignada a Cliente2
        Retorno ret = sistema.devolverEntrada("11111111", "EVT02");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        // Verificamos que Cliente2 ya no está en espera, y Cliente3 es ahora el primero
        Retorno espera = sistema.listarEsperaEvento();
        assertTrue(espera.valorString.contains("EVT02-33333333"));
        assertFalse(espera.valorString.contains("22222222"));
    }

    @Test
    public void testDevolverEntrada_ERROR1_ClienteNoExiste() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Evento", 50, LocalDate.of(2025, 6, 20));
        Retorno ret = sistema.devolverEntrada("99999999", "EVT01");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testDevolverEntrada_ERROR2_EventoNoExiste() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        Retorno ret = sistema.devolverEntrada("12345678", "XXX99");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    // CALIFICAR EVENTO//
    @Test
    public void testCalificarEvento_OK() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Conferencia", 50, LocalDate.of(2025, 6, 25));
        sistema.comprarEntrada("12345678", "EVT01");

        Retorno ret = sistema.calificarEvento("12345678", "EVT01", 8, "Muy bueno");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testCalificarEvento_OK_MultiplesClientes() {
        // Registrar clientes
        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");
        sistema.registrarCliente("10000003", "Cliente 3");
        sistema.registrarCliente("10000004", "Cliente 4");
        sistema.registrarCliente("10000005", "Cliente 5");

        // Registrar sala y evento
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Ciclo de Charlas", 5, LocalDate.of(2025, 6, 30));

        // Todos compran entrada
        for (int i = 1; i <= 5; i++) {
            String cedula = String.format("1000000%d", i);
            sistema.comprarEntrada(cedula, "EVT01");
        }

        // Todos califican distinto
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("10000001", "EVT01", 9, "Excelente").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("10000002", "EVT01", 8, "Muy bueno").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("10000003", "EVT01", 7, "Bueno").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("10000004", "EVT01", 10, "Perfecto").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("10000005", "EVT01", 6, "Aceptable").resultado);
    }

    @Test
    public void testCalificarEvento_ERROR1_ClienteNoExiste() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Conferencia", 50, LocalDate.of(2025, 6, 25));

        Retorno ret = sistema.calificarEvento("99999999", "EVT01", 7, "Bueno");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testCalificarEvento_ERROR2_EventoNoExiste() {
        sistema.registrarCliente("12345678", "Juan Pérez");

        Retorno ret = sistema.calificarEvento("12345678", "XXX99", 7, "Bueno");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testCalificarEvento_ERROR3_PuntajeInvalido() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Conferencia", 50, LocalDate.of(2025, 6, 25));
        sistema.comprarEntrada("12345678", "EVT01");

        Retorno ret = sistema.calificarEvento("12345678", "EVT01", 0, "Malo");
        assertEquals(Retorno.Resultado.ERROR_3, ret.resultado);

        ret = sistema.calificarEvento("12345678", "EVT01", 11, "Excelente");
        assertEquals(Retorno.Resultado.ERROR_3, ret.resultado);
    }

    @Test
    public void testCalificarEvento_ERROR4_YaCalificado() {
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Conferencia", 50, LocalDate.of(2025, 6, 25));
        sistema.comprarEntrada("12345678", "EVT01");

        sistema.calificarEvento("12345678", "EVT01", 8, "Muy bueno");

        Retorno ret = sistema.calificarEvento("12345678", "EVT01", 9, "Mejor aún");
        assertEquals(Retorno.Resultado.ERROR_4, ret.resultado);
    }

    //REPORTES//
    // CLIENTES DE EVENTO//
    @Test
    public void testListarClientesDeEvento_OK_Simple() {
        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");
        sistema.registrarSala("Sala A", 10);
        sistema.registrarEvento("EVT01", "Charla", 10, LocalDate.of(2025, 6, 10));

        sistema.comprarEntrada("10000001", "EVT01");
        sistema.comprarEntrada("10000002", "EVT01");

        Retorno ret = sistema.listarClientesDeEvento("EVT01", 2);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("10000002-Cliente 2#10000001-Cliente 1", ret.valorString);
    }

    @Test
    public void testListarClientesDeEvento_OK_MultipleEventos() {
        sistema.registrarSala("Sala B", 100);
        sistema.registrarEvento("TEC10", "Tecnología", 100, LocalDate.of(2025, 6, 15));
        sistema.registrarEvento("ART20", "Arte Moderno", 100, LocalDate.of(2025, 6, 18));

        // Clientes y entradas para TEC10
        for (int i = 1; i <= 5; i++) {
            String cedula = "2000000" + i;
            sistema.registrarCliente(cedula, "TecCliente " + i);
            sistema.comprarEntrada(cedula, "TEC10");
        }

        // Clientes y entradas para ART20
        for (int i = 1; i <= 3; i++) {
            String cedula = "3000000" + i;
            sistema.registrarCliente(cedula, "ArtCliente " + i);
            sistema.comprarEntrada(cedula, "ART20");
        }

        // TEC10, últimos 3 clientes
        Retorno retTec = sistema.listarClientesDeEvento("TEC10", 3);
        assertEquals(Retorno.Resultado.OK, retTec.resultado);
        assertEquals("20000005-TecCliente 5#20000004-TecCliente 4#20000003-TecCliente 3", retTec.valorString);

        // ART20, todos (n mayor que cantidad)
        Retorno retArt = sistema.listarClientesDeEvento("ART20", 5);
        assertEquals(Retorno.Resultado.OK, retArt.resultado);
        assertEquals("30000003-ArtCliente 3#30000002-ArtCliente 2#30000001-ArtCliente 1", retArt.valorString);
    }

    @Test
    public void testListarClientesDeEvento_ERROR1_EventoNoExiste() {
        Retorno ret = sistema.listarClientesDeEvento("ZZZ99", 3);
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testListarClientesDeEvento_ERROR2_NInvalido() {
        sistema.registrarSala("Sala A", 10);
        sistema.registrarEvento("EVT01", "Evento", 10, LocalDate.of(2025, 6, 10));
        Retorno ret = sistema.listarClientesDeEvento("EVT01", 0);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    // LISTA DE ESPERA POR EVENTO//
    @Test
    public void testListarEsperaEvento_OK_Simple() {
        sistema.registrarCliente("10000001", "Cliente A");
        sistema.registrarCliente("10000002", "Cliente B");
        sistema.registrarCliente("10000003", "Cliente C");

        sistema.registrarSala("Sala A", 1);
        sistema.registrarEvento("EVT01", "Evento A", 1, LocalDate.of(2025, 6, 10));

        sistema.comprarEntrada("10000001", "EVT01"); // la obtiene
        sistema.comprarEntrada("10000002", "EVT01"); // espera
        sistema.comprarEntrada("10000003", "EVT01"); // espera

        Retorno ret = sistema.listarEsperaEvento();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-10000002#EVT01-10000003", ret.valorString);
    }

    @Test
    public void testListarEsperaEvento_OK_MultipleEventos() {
        // Registro de clientes
        for (int i = 1; i <= 6; i++) {
            sistema.registrarCliente("2000000" + i, "Cliente " + i);
        }

        // Dos salas
        sistema.registrarSala("Sala A", 1);
        sistema.registrarSala("Sala B", 1);

        // Dos eventos
        sistema.registrarEvento("ART01", "Arte", 1, LocalDate.of(2025, 6, 20));
        sistema.registrarEvento("TEC02", "Tecnología", 1, LocalDate.of(2025, 6, 21));

        // ART01: 1 compra, 2 esperan
        sistema.comprarEntrada("20000001", "ART01");
        sistema.comprarEntrada("20000002", "ART01");
        sistema.comprarEntrada("20000003", "ART01");

        // TEC02: 1 compra, 2 esperan
        sistema.comprarEntrada("20000004", "TEC02");
        sistema.comprarEntrada("20000005", "TEC02");
        sistema.comprarEntrada("20000006", "TEC02");

        // Esperados:
        // ART01-20000002#ART01-20000003
        // TEC02-20000005#TEC02-20000006
        // Orden por evento y dentro de evento por cédula
        Retorno ret = sistema.listarEsperaEvento();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("ART01-20000002#ART01-20000003#TEC02-20000005#TEC02-20000006", ret.valorString);
    }

    @Test
    public void testListarEsperaEvento_SinClientesEnEspera() {
        sistema.registrarSala("Sala C", 5);
        sistema.registrarEvento("EVT99", "Sin Espera", 5, LocalDate.of(2025, 6, 10));
        sistema.registrarCliente("30000001", "Cliente Solo");
        sistema.comprarEntrada("30000001", "EVT99");

        Retorno ret = sistema.listarEsperaEvento();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    //DESHACER COMPRAS//
    @Test
    public void testDeshacerUltimasCompras_OK_Simple() {
        sistema.registrarSala("Sala A", 5);
        sistema.registrarEvento("EVT01", "Evento", 5, LocalDate.of(2025, 6, 10));

        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");
        sistema.comprarEntrada("10000001", "EVT01");
        sistema.comprarEntrada("10000002", "EVT01");

        Retorno ret = sistema.deshacerUtimasCompras(2);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-10000001#EVT01-10000002", ret.valorString);
    }

    @Test
    public void testDeshacerUltimasCompras_OK_MultipleEventos() {
        sistema.registrarSala("Sala A", 10);
        sistema.registrarEvento("TEC01", "Tecnología", 5, LocalDate.of(2025, 6, 12));
        sistema.registrarEvento("ART02", "Arte", 5, LocalDate.of(2025, 6, 13));

        sistema.registrarCliente("20000001", "Cliente A");
        sistema.registrarCliente("20000002", "Cliente B");
        sistema.registrarCliente("20000003", "Cliente C");
        sistema.registrarCliente("20000004", "Cliente D");

        sistema.comprarEntrada("20000001", "TEC01"); // 1
        sistema.comprarEntrada("20000002", "TEC01"); // 2
        sistema.comprarEntrada("20000003", "ART02"); // 3
        sistema.comprarEntrada("20000004", "ART02"); // 4

        Retorno ret = sistema.deshacerUtimasCompras(3);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        // Últimos 3 deshechos: 20000002 (TEC01), 20000003 (ART02), 20000004 (ART02)
        // Ordenados por evento y cédula:
        assertEquals("ART02-20000003#ART02-20000004#TEC01-20000002", ret.valorString);
    }

    @Test
    public void testDeshacerUltimasCompras_SinCompras() {
        Retorno ret = sistema.deshacerUtimasCompras(2);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    //EVENTO MEJOR PUNTUADO//
    @Test
    public void testEventoMejorPuntuado_OK_UnoSolo() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Charlas", 100, LocalDate.of(2025, 6, 20));
        sistema.registrarCliente("10000001", "Cliente 1");

        sistema.comprarEntrada("10000001", "EVT01");
        sistema.calificarEvento("10000001", "EVT01", 9, "Muy bueno");

        Retorno ret = sistema.eventoMejorPuntuado();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-9", ret.valorString);
    }

    @Test
    public void testEventoMejorPuntuado_OK_Multiple() {
        sistema.registrarSala("Sala B", 100);
        sistema.registrarEvento("TEC01", "Tecnología", 100, LocalDate.of(2025, 6, 10));
        sistema.registrarEvento("ART02", "Arte", 100, LocalDate.of(2025, 6, 11));

        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");

        sistema.comprarEntrada("10000001", "TEC01");
        sistema.comprarEntrada("10000002", "ART02");

        sistema.calificarEvento("10000001", "TEC01", 8, "Bien");
        sistema.calificarEvento("10000002", "ART02", 9, "Me encantó");

        Retorno ret = sistema.eventoMejorPuntuado();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("ART02-9", ret.valorString);
    }

    @Test
    public void testEventoMejorPuntuado_OK_Empate() {
        sistema.registrarSala("Sala C", 100);
        sistema.registrarEvento("AAA10", "Evento A", 100, LocalDate.of(2025, 6, 5));
        sistema.registrarEvento("ZZZ99", "Evento Z", 100, LocalDate.of(2025, 6, 6));

        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");

        sistema.comprarEntrada("10000001", "AAA10");
        sistema.comprarEntrada("10000002", "ZZZ99");

        sistema.calificarEvento("10000001", "AAA10", 9, "Muy bueno");
        sistema.calificarEvento("10000002", "ZZZ99", 9, "Igual");

        Retorno ret = sistema.eventoMejorPuntuado();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("AAA10-9#ZZZ99-9", ret.valorString);
    }

    @Test
    public void testEventoMejorPuntuado_OK_PromedioReal() {
        sistema.registrarSala("Sala A", 100);
        sistema.registrarEvento("EVT01", "Show", 100, LocalDate.of(2025, 6, 30));

        sistema.registrarCliente("10000001", "Cliente 1");
        sistema.registrarCliente("10000002", "Cliente 2");
        sistema.registrarCliente("10000003", "Cliente 3");

        sistema.comprarEntrada("10000001", "EVT01");
        sistema.comprarEntrada("10000002", "EVT01");
        sistema.comprarEntrada("10000003", "EVT01");

        sistema.calificarEvento("10000001", "EVT01", 8, "");
        sistema.calificarEvento("10000002", "EVT01", 10, "");
        sistema.calificarEvento("10000003", "EVT01", 7, "");

        Retorno ret = sistema.eventoMejorPuntuado();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-8", ret.valorString); // (8+10+7)/3 = 8.33 → se trunca o redondea según implementación
    }

    //COMPRAS DE CLIENTE//
    @Test
    public void testComprasDeCliente_OK_UnaCompra() {
        sistema.registrarSala("Sala A", 5);
        sistema.registrarEvento("EVT01", "Evento 1", 5, LocalDate.of(2025, 6, 10));
        sistema.registrarCliente("10000001", "Cliente 1");

        sistema.comprarEntrada("10000001", "EVT01");

        Retorno ret = sistema.comprasDeCliente("10000001");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-N", ret.valorString);
    }

    @Test
    public void testComprasDeCliente_OK_MultiplesConDevoluciones() {
        sistema.registrarSala("Sala B", 10);
        sistema.registrarEvento("TEC01", "Tecnología", 10, LocalDate.of(2025, 6, 15));
        sistema.registrarEvento("ART02", "Arte", 10, LocalDate.of(2025, 6, 16));
        sistema.registrarEvento("HIS03", "Historia", 10, LocalDate.of(2025, 6, 17));
        sistema.registrarCliente("20000001", "Cliente A");

        sistema.comprarEntrada("20000001", "TEC01"); // no se devuelve
        sistema.comprarEntrada("20000001", "ART02"); // será devuelta
        sistema.comprarEntrada("20000001", "HIS03"); // no se devuelve

        sistema.devolverEntrada("20000001", "ART02");

        Retorno ret = sistema.comprasDeCliente("20000001");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("TEC01-N#ART02-D#HIS03-N", ret.valorString);
    }

    @Test
    public void testComprasDeCliente_ERROR1_ClienteNoExiste() {
        Retorno ret = sistema.comprasDeCliente("99999999");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testComprasDeCliente_OK_SinCompras() {
        sistema.registrarCliente("30000001", "Cliente X");

        Retorno ret = sistema.comprasDeCliente("30000001");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }

    //COMPRAS POR DÍA//
    @Test
    public void testComprasXDia_OK_Simple() {
        sistema.registrarSala("Sala A", 10);
        sistema.registrarEvento("EVT01", "Charla", 10, LocalDate.of(2025, 6, 15));

        sistema.registrarCliente("10000001", "Cliente A");
        sistema.registrarCliente("10000002", "Cliente B");

        sistema.comprarEntrada("10000001", "EVT01");
        sistema.comprarEntrada("10000002", "EVT01");

        Retorno ret = sistema.comprasXDia(6); // junio
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("15-2", ret.valorString);
    }

    @Test
    public void testComprasXDia_OK_MultiplesDias() {
        sistema.registrarSala("Sala B", 20);

        sistema.registrarEvento("E01", "Evento 1", 10, LocalDate.of(2025, 6, 5));   // 2 compras
        sistema.registrarEvento("E02", "Evento 2", 10, LocalDate.of(2025, 6, 5));   // 1 compra
        sistema.registrarEvento("E03", "Evento 3", 10, LocalDate.of(2025, 6, 10));  // 3 compras
        sistema.registrarEvento("E04", "Evento 4", 10, LocalDate.of(2025, 6, 25));  // 1 compra

        // Registrar clientes y comprar entradas
        for (int i = 1; i <= 7; i++) {
            String cedula = String.format("2000000%d", i);
            sistema.registrarCliente(cedula, "Cliente " + i);
        }

        sistema.comprarEntrada("20000001", "E01");
        sistema.comprarEntrada("20000002", "E01");

        sistema.comprarEntrada("20000003", "E02");

        sistema.comprarEntrada("20000004", "E03");
        sistema.comprarEntrada("20000005", "E03");
        sistema.comprarEntrada("20000006", "E03");

        sistema.comprarEntrada("20000007", "E04");

        Retorno ret = sistema.comprasXDia(6);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("5-3#10-3#25-1", ret.valorString);
    }

    @Test
    public void testComprasXDia_OK_SinComprasEnEseMes() {
        sistema.registrarSala("Sala A", 10);
        sistema.registrarEvento("EVT01", "Sin Ventas", 10, LocalDate.of(2025, 7, 1)); // julio

        sistema.registrarCliente("30000001", "Cliente X");
        sistema.comprarEntrada("30000001", "EVT01");

        Retorno ret = sistema.comprasXDia(6); // junio
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("", ret.valorString);
    }
}
