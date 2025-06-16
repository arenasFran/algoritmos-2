package sistemaAutogestion;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


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

    @Test
    public void testCrearSistemaDeGestion() {
        Retorno ret = sistema.crearSistemaDeGestion();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarSala() {
        Retorno ret = sistema.registrarSala("Sala A", 50);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

 	 ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarSala_ERROR1() {
	Retorno ret = sistema.registrarSala("Sala A", 50);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

 	ret = sistema.registrarSala("Sala B", 10);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarSala("Sala A", 100);
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }

    @Test
    public void testRegistrarSala_ERROR2() {

	Retorno ret = sistema.registrarSala("Sala A", 50);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.registrarSala("Sala B", 0);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);

	ret = sistema.registrarSala("Sala B", -10);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testEliminarSala() {
        Retorno ret = sistema.registrarSala("Sala A", 50);
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        ret = sistema.eliminarSala("Sala A");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarEvento() {
        Retorno ret = sistema.registrarSala("Sala A", 100);
	assertEquals(Retorno.Resultado.OK, ret.resultado);

        LocalDate fecha = LocalDate.of(2025, 5, 10);
        ret = sistema.registrarEvento("EVT01", "Concierto", 80, fecha);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarCliente() {
        Retorno ret = sistema.registrarCliente("12345678", "Juan Pérez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

	ret = sistema.registrarCliente("12345444", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
    }

    @Test
    public void testRegistrarCliente_ERROR1() {
        Retorno ret = sistema.registrarCliente("1234", "Juan Pérez");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

	ret = sistema.registrarCliente("AA345444", "Martina Gutierrez");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
    }
   @Test
    public void testRegistrarCliente_Error2() {
    Retorno ret1 = sistema.registrarCliente("12345678", "Juan Pérez");
    assertEquals(Retorno.Resultado.OK, ret1.resultado);

    Retorno ret2 = sistema.registrarCliente("12345678", "Otro Nombre");
    assertEquals(Retorno.Resultado.ERROR_2, ret2.resultado); // Mismo número de cédula
}

   
    @Test
    public void testListarSalas() {
        sistema.registrarSala("Sala A", 50);
        sistema.registrarSala("Sala B", 70);
        sistema.registrarSala("Sala C", 100);
        Retorno ret = sistema.listarSalas();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("Sala C-100#Sala B-70#Sala A-50", ret.valorString);
    }

    @Test
    public void testListarEventos() {        
        sistema.registrarSala("Sala F", 14);
        sistema.registrarSala("Sala A", 16);
        sistema.registrarSala("Sala B", 18);
        sistema.registrarEvento("CUC22", "Tango Azul", 13, LocalDate.of(2025, 10, 7)); // Sala B
        sistema.registrarEvento("TEC43", "Seminario de Tecnología", 17, LocalDate.of(2025, 10, 7)); // Sala B
        sistema.registrarEvento("KAK34", "Noche de Rock", 15, LocalDate.of(2025, 10, 7)); // Sala B
        Retorno ret = sistema.listarEventos();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("CUC22-Tango Azul-Sala F-14-0#KAK34-Noche de Rock-Sala A-16-0#TEC43-Seminario de Tecnología-Sala B-18-0", ret.valorString);

    }

    @Test
    public void testListarClientes() {
        sistema.registrarCliente("45678992", "Micaela Ferrez");
    	sistema.registrarCliente("23331111", "Martina Rodríguez");
    	sistema.registrarCliente("35679992", "Ramiro Perez");

    	Retorno ret = sistema.listarClientes();
    	assertEquals(Retorno.Resultado.OK, ret.resultado);
    	assertEquals("23331111-Martina Rodríguez#35679992-Ramiro Perez#45678992-Micaela Ferrez", ret.valorString);
    }

    @Test
    public void testEsSalaOptima() {
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
public void testSalaNoOptima() {
    String[][] vistaSala = {
        {"#", "#", "#", "#", "#"},
        {"#", "O", "X", "X", "#"},
        {"#", "O", "O", "X", "#"},
        {"#", "O", "O", "X", "#"},
        {"#", "O", "X", "X", "#"},
        {"#", "X", "X", "X", "#"},
        {"#", "#", "#", "#", "#"}
    };

    Retorno ret = sistema.esSalaOptima(vistaSala);
    assertEquals(Retorno.Resultado.OK, ret.resultado);
    assertEquals("No es óptimo", ret.valorString);
}

    @Test
    public void testComprarEntrada() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarCliente("12345678", "Juan Pérez");

        // Test successful purchase
        Retorno ret = sistema.comprarEntrada("12345678", "EVT01");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        // Test non-existent client
        ret = sistema.comprarEntrada("99999999", "EVT01");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        // Test non-existent event
        ret = sistema.comprarEntrada("12345678", "EVT99");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testDevolverEntrada() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.comprarEntrada("12345678", "EVT01");

        // Test successful return
        Retorno ret = sistema.devolverEntrada("12345678", "EVT01");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        // Test non-existent client
        ret = sistema.devolverEntrada("99999999", "EVT01");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        // Test non-existent event
        ret = sistema.devolverEntrada("12345678", "EVT99");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testCalificarEvento() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.comprarEntrada("12345678", "EVT01");

        // Test successful rating
        Retorno ret = sistema.calificarEvento("12345678", "EVT01", 8, "Muy bueno");
        assertEquals(Retorno.Resultado.OK, ret.resultado);

        // Test invalid rating
        ret = sistema.calificarEvento("12345678", "EVT01", 11, "Muy bueno");
        assertEquals(Retorno.Resultado.ERROR_3, ret.resultado);

        // Test non-existent client
        ret = sistema.calificarEvento("99999999", "EVT01", 8, "Muy bueno");
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        // Test non-existent event
        ret = sistema.calificarEvento("12345678", "EVT99", 8, "Muy bueno");
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testListarClientesDeEvento() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarCliente("87654321", "María López");
        sistema.comprarEntrada("12345678", "EVT01");
        sistema.comprarEntrada("87654321", "EVT01");

        // Test listing last 2 clients
        Retorno ret = sistema.listarClientesDeEvento("EVT01", 2);
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("12345678-Juan Pérez#87654321-María López", ret.valorString);

        // Test non-existent event
        ret = sistema.listarClientesDeEvento("EVT99", 2);
        assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);

        // Test invalid n
        ret = sistema.listarClientesDeEvento("EVT01", 0);
        assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
    }

    @Test
    public void testListarEsperaEvento() {
        // Setup
        sistema.registrarSala("Sala A", 2); // Small capacity to force waiting list
        sistema.registrarEvento("EVT01", "Concierto", 2, LocalDate.of(2025, 5, 10));
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarCliente("87654321", "María López");
        sistema.registrarCliente("11111111", "Ana García");
        
        // Fill the event
        sistema.comprarEntrada("12345678", "EVT01");
        sistema.comprarEntrada("87654321", "EVT01");
        // This should go to waiting list
        sistema.comprarEntrada("11111111", "EVT01");

        Retorno ret = sistema.listarEsperaEvento();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-11111111", ret.valorString);
    }

    @Test
    public void testEventoMejorPuntuado() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarEvento("EVT02", "Teatro", 40, LocalDate.of(2025, 5, 11));
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarCliente("87654321", "María López");
        
        // Buy tickets and rate events
        sistema.comprarEntrada("12345678", "EVT01");
        sistema.comprarEntrada("87654321", "EVT01");
        sistema.comprarEntrada("12345678", "EVT02");
        sistema.comprarEntrada("87654321", "EVT02");
        
        sistema.calificarEvento("12345678", "EVT01", 8, "Muy bueno");
        sistema.calificarEvento("87654321", "EVT01", 9, "Excelente");
        sistema.calificarEvento("12345678", "EVT02", 7, "Bueno");
        sistema.calificarEvento("87654321", "EVT02", 8, "Muy bueno");

        Retorno ret = sistema.eventoMejorPuntuado();
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-8", ret.valorString);
    }

    @Test
    public void testComprasDeCliente() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarEvento("EVT02", "Teatro", 40, LocalDate.of(2025, 5, 11));
        sistema.registrarCliente("12345678", "Juan Pérez");
        
        // Buy tickets
        sistema.comprarEntrada("12345678", "EVT01");
        sistema.comprarEntrada("12345678", "EVT02");

        Retorno ret = sistema.comprasDeCliente("12345678");
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("EVT01-Concierto-10/05/2025#EVT02-Teatro-11/05/2025", ret.valorString);
    }

    @Test
    public void testComprasXDia() {
        // Setup
        sistema.registrarSala("Sala A", 50);
        sistema.registrarEvento("EVT01", "Concierto", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarEvento("EVT02", "Teatro", 40, LocalDate.of(2025, 5, 10));
        sistema.registrarCliente("12345678", "Juan Pérez");
        sistema.registrarCliente("87654321", "María López");
        
        // Buy tickets
        sistema.comprarEntrada("12345678", "EVT01");
        sistema.comprarEntrada("87654321", "EVT02");

        Retorno ret = sistema.comprasXDia(5); // May
        assertEquals(Retorno.Resultado.OK, ret.resultado);
        assertEquals("10/05/2025-2", ret.valorString);
    }
}
