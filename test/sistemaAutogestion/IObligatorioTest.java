/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
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

}
