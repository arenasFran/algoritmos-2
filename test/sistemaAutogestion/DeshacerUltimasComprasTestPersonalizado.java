package sistemaAutogestion;

import dominio.Evento;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import sistemaAutogestion.Retorno;
import sistemaAutogestion.Retorno.Resultado;
import sistemaAutogestion.Sistema;
import tads.ListaSimple; // Assuming ListaSimple is accessible for assertions if needed

public class DeshacerUltimasComprasTestPersonalizado {

    private Sistema sistema;
 public DeshacerUltimasComprasTestPersonalizado () {
 	sistema = new Sistema();
    }
    @Before
    public void setUp() {
        sistema = new Sistema();
        sistema.crearSistemaDeGestion();

        // Common setup for most tests:
        sistema.registrarSala("SalaA", 100);
        sistema.registrarSala("SalaB", 50);
        sistema.registrarEvento("EV1", "Concierto Rock", 40, LocalDate.of(2025, 7, 10));
        sistema.registrarEvento("EV2", "Obra de Teatro", 30, LocalDate.of(2025, 7, 11));
        sistema.registrarEvento("EV3", "Festival Jazz", 50, LocalDate.of(2025, 7, 12));

        sistema.registrarCliente("11111111", "Alice");
        sistema.registrarCliente("22222222", "Bob");
        sistema.registrarCliente("33333333", "Charlie");
        sistema.registrarCliente("44444444", "David");
    }

    @Test
    public void testDeshacerUltimasCompras_N_Invalido() {
        // n = 0
        Retorno retorno = sistema.deshacerUtimasCompras(0);
        assertEquals(Resultado.ERROR_1, retorno.resultado);

        // n = -5
        retorno = sistema.deshacerUtimasCompras(-5);
        assertEquals(Resultado.ERROR_1, retorno.resultado);
    }

    @Test
    public void testDeshacerUltimasCompras_PilaVacia() {
        // No se han realizado compras, la pila está vacía
        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("No hay entradas vendidas para deshacer.", retorno.valorString);
    }

    @Test
    public void testDeshacerUltimasCompras_UnaSolaCompra() {
        sistema.comprarEntrada("11111111", "EV1"); // Primera y única compra

        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV1-11111111", retorno.valorString); // Expect the last undone to be returned

        // Verificar que la entrada fue realmente deshecha
        Evento ev1 = sistema.buscarEvento("EV1");
        assertEquals(0, ev1.getEntradasVendidas().cantElementos());
        assertTrue(sistema.pilaEntradas.esVacia()); // pilaEntradas should be empty
    }

    @Test
    public void testDeshacerUltimasCompras_MultiplesCompras_N_igual_1() {
        sistema.comprarEntrada("11111111", "EV1"); // C1-EV1 (Oldest)
        sistema.comprarEntrada("22222222", "EV2"); // C2-EV2 (Newest)

        // Deshacer la última compra (n=1)
        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV2-22222222", retorno.valorString); // Should undo the most recent

        // Verify remaining purchases
        Evento ev1 = sistema.buscarEvento("EV1");
        Evento ev2 = sistema.buscarEvento("EV2");
        assertEquals(1, ev1.getEntradasVendidas().cantElementos()); // EV1 purchase remains
        assertEquals(0, ev2.getEntradasVendidas().cantElementos()); // EV2 purchase is undone

        // The remaining purchase (C1-EV1) should still be on top of the stack if it was the only one before E2
        assertEquals("11111111", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals(1, sistema.pilaEntradas.getCantidad());
    }

    @Test
    public void testDeshacerUltimasCompras_MultiplesCompras_N_mayor_1() {
        sistema.comprarEntrada("11111111", "EV1"); // E1: C1-EV1
        sistema.comprarEntrada("22222222", "EV2"); // E2: C2-EV2
        sistema.comprarEntrada("33333333", "EV1"); // E3: C3-EV1 (Most Recent)

        // Deshacer las últimas 2 compras (E3 y E2)
        Retorno retorno = sistema.deshacerUtimasCompras(2);
        assertEquals(Resultado.OK, retorno.resultado);
        // Expected order: sorted by event code, then by cedula
        assertEquals("EV1-33333333#EV2-22222222", retorno.valorString);

        // Verify remaining purchases
        Evento ev1 = sistema.buscarEvento("EV1");
        Evento ev2 = sistema.buscarEvento("EV2");
        assertEquals(1, ev1.getEntradasVendidas().cantElementos()); // C1-EV1 remains
        assertEquals(0, ev2.getEntradasVendidas().cantElementos()); // C2-EV2 undone

        assertEquals("11111111", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals(1, sistema.pilaEntradas.getCantidad());
    }

    @Test
    public void testDeshacerUltimasCompras_ConListaEspera() {
 System.out.println("FLAG 1");
    for (int i = 0; i < 50; i++) {
        String cedula = "111111" + String.format("%02d", i);
        // Puedes imprimir aquí para ver cada cliente que se intenta registrar/comprar
        System.out.println("Intentando registrar/comprar para: " + cedula);
        sistema.registrarCliente(cedula, "Cliente" + i);
        sistema.comprarEntrada(cedula, "EV3");
    }

    System.out.println("Después de las 50 compras de EV3."); // Añade este para ver si se llega
    sistema.comprarEntrada("44444444", "EV3"); // David va a lista de espera
    System.out.println("Después de la compra de David."); // Añade este

    Evento ev3 = sistema.buscarEvento("EV3");
    System.out.println("Evento EV3 encontrado: " + (ev3 != null ? ev3.getCodigo() : "null")); // Verifica si ev3 es null

    // Si ev3 es null, la siguiente línea fallará.
    // Añade una verificación explícita para saber si la lista de espera es null.
    if (ev3 != null) {
        
        
        
        System.out.println("Lista de espera de EV3 es null? " + (ev3.getListaEspera() == null));
        
        System.out.println("Lista clientes en espera de EV3"+ ev3.getListaEspera().verPrimero().getName());
        
      ///ACA SE RROMPE 
        if (ev3.getListaEspera() != null) { // Solo llama a métodos si no es null
            assertFalse(ev3.getListaEspera().esVacia());
            assertEquals("44444444", ev3.getListaEspera().verPrimero().getCedula()) ;
        } else {
            System.out.println("ERROR: La lista de espera de EV3 es NULL."); // Mensaje de error personalizado
        }

    }
    
    
    
    System.out.println("FLAG 2");
        // Cliente Charlie (33333333) compra para EV1 (last purchase on overall stack)
        sistema.comprarEntrada("33333333", "EV1"); // Most recent overall purchase

        // Deshacer la última compra (Charlie for EV1)
        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV1-33333333", retorno.valorString); // Charlie's purchase undone
System.out.println("FLAG 3");
        // Now, undo one more purchase (from EV3). This should trigger waitlist assignment.
        // The last purchase on pilaEntradas before Charlie was one of the "111111xx" for EV3.
        retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        // The purchase undone was for EV3. After undoing, David gets the spot.
        // So the output should be about the *original* undone purchase, sorted.
        // Assuming the last purchase before Charlie was 11111149
        assertEquals("EV3-11111149", retorno.valorString); // This needs to be precise for the last client sold
System.out.println("FLAG 4");
        // Verify David now has a ticket for EV3
        assertTrue(ev3.getListaEspera().esVacia()); // David should be off waitlist
        boolean davidHasTicket = false;
        for (int i = 0; i < ev3.getEntradasVendidas().cantElementos(); i++) {
            if (ev3.getEntradasVendidas().obtenerPorIndice(i).getCliente().getCedula().equals("44444444")) {
                davidHasTicket = true;
                break;
            }
        }
        assertTrue("David should have a ticket for EV3", davidHasTicket);

        // Verify David's new ticket is on top of pilaEntradas
        assertEquals("44444444", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals("EV3", sistema.pilaEntradas.verCima().getEvento().getCodigo());
          retorno = sistema.deshacerUtimasCompras(1);
   
    }

    @Test
    public void testDeshacerUltimasCompras_N_MayorQueComprasExistentes() {
        sistema.comprarEntrada("11111111", "EV1"); // E1
        sistema.comprarEntrada("22222222", "EV2"); // E2

        // Try to undo 5 purchases when only 2 exist
        Retorno retorno = sistema.deshacerUtimasCompras(5);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV1-11111111#EV2-22222222", retorno.valorString); // Sorted output
        assertTrue(sistema.pilaEntradas.esVacia()); // All purchases undone
    }

    @Test
    public void testDeshacerUltimasCompras_MultiplesEventosConMismaCedula() {
        sistema.comprarEntrada("11111111", "EV1"); // P1: C1-EV1
        sistema.comprarEntrada("11111111", "EV2"); // P2: C1-EV2
        sistema.comprarEntrada("22222222", "EV1"); // P3: C2-EV1 (Most recent)

        // Deshacer 2 compras (P3 and P2)
        Retorno retorno = sistema.deshacerUtimasCompras(2);
        assertEquals(Resultado.OK, retorno.resultado);
        // Expected: sorted by Event code, then Cédula
        assertEquals("EV1-22222222#EV2-11111111", retorno.valorString);

        Evento ev1 = sistema.buscarEvento("EV1");
        Evento ev2 = sistema.buscarEvento("EV2");

        // Verify EV1 has 1 entry (C1-EV1 remains)
        assertEquals(1, ev1.getEntradasVendidas().cantElementos());
        assertEquals("11111111", ev1.getEntradasVendidas().obtenerPorIndice(0).getCliente().getCedula());

        // Verify EV2 has 0 entries (C1-EV2 undone)
        assertEquals(0, ev2.getEntradasVendidas().cantElementos());

        // Verify pilaEntradas contains only P1
        assertEquals(1, sistema.pilaEntradas.getCantidad());
        assertEquals("11111111", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals("EV1", sistema.pilaEntradas.verCima().getEvento().getCodigo());
    }
}