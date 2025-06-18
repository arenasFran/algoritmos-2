package sistemaAutogestion;

import dominio.Evento;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import sistemaAutogestion.Retorno;
import sistemaAutogestion.Retorno.Resultado;
import sistemaAutogestion.Sistema;
import tads.ListaSimple;

public class DeshacerUltimasComprasTestPersonalizado {

    private Sistema sistema;

    public DeshacerUltimasComprasTestPersonalizado() {
        sistema = new Sistema();
    }

    @Before
    public void setUp() {
        sistema = new Sistema();
        sistema.crearSistemaDeGestion();

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

        Retorno retorno = sistema.deshacerUtimasCompras(0);
        assertEquals(Resultado.ERROR_1, retorno.resultado);

        retorno = sistema.deshacerUtimasCompras(-5);
        assertEquals(Resultado.ERROR_1, retorno.resultado);
    }

    @Test
    public void testDeshacerUltimasCompras_PilaVacia() {

        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("No hay entradas vendidas para deshacer.", retorno.valorString);
    }

    @Test
    public void testDeshacerUltimasCompras_UnaSolaCompra() {
        sistema.comprarEntrada("11111111", "EV1");

        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV1-11111111", retorno.valorString);

        Evento ev1 = sistema.buscarEvento("EV1");
        assertEquals(0, ev1.getEntradasVendidas().cantElementos());
        assertTrue(sistema.pilaEntradas.esVacia());
    }

    @Test
    public void testDeshacerUltimasCompras_MultiplesCompras_N_igual_1() {
        sistema.comprarEntrada("11111111", "EV1");
        sistema.comprarEntrada("22222222", "EV2");

        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV2-22222222", retorno.valorString);

        Evento ev1 = sistema.buscarEvento("EV1");
        Evento ev2 = sistema.buscarEvento("EV2");
        assertEquals(1, ev1.getEntradasVendidas().cantElementos());
        assertEquals(0, ev2.getEntradasVendidas().cantElementos());

        assertEquals("11111111", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals(1, sistema.pilaEntradas.getCantidad());
    }

    @Test
    public void testDeshacerUltimasCompras_MultiplesCompras_N_mayor_1() {
        sistema.comprarEntrada("11111111", "EV1");
        sistema.comprarEntrada("22222222", "EV2");
        sistema.comprarEntrada("33333333", "EV1");

        Retorno retorno = sistema.deshacerUtimasCompras(2);
        assertEquals(Resultado.OK, retorno.resultado);

        assertEquals("EV1-33333333#EV2-22222222", retorno.valorString);

        Evento ev1 = sistema.buscarEvento("EV1");
        Evento ev2 = sistema.buscarEvento("EV2");
        assertEquals(1, ev1.getEntradasVendidas().cantElementos());
        assertEquals(0, ev2.getEntradasVendidas().cantElementos());

        assertEquals("11111111", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals(1, sistema.pilaEntradas.getCantidad());
    }

    @Test
    public void testDeshacerUltimasCompras_ConListaEspera() {
        System.out.println("FLAG 1");
        for (int i = 0; i < 50; i++) {
            String cedula = "111111" + String.format("%02d", i);

            System.out.println("Intentando registrar/comprar para: " + cedula);
            sistema.registrarCliente(cedula, "Cliente" + i);
            sistema.comprarEntrada(cedula, "EV3");
        }

        System.out.println("Después de las 50 compras de EV3.");
        sistema.comprarEntrada("44444444", "EV3");
        System.out.println("Después de la compra de David.");

        Evento ev3 = sistema.buscarEvento("EV3");
        System.out.println("Evento EV3 encontrado: " + (ev3 != null ? ev3.getCodigo() : "null"));

        if (ev3 != null) {

            System.out.println("Lista de espera de EV3 es null? " + (ev3.getListaEspera() == null));

            System.out.println("Lista clientes en espera de EV3" + ev3.getListaEspera().verPrimero().getName());

            if (ev3.getListaEspera() != null) {
                assertFalse(ev3.getListaEspera().esVacia());
                assertEquals("44444444", ev3.getListaEspera().verPrimero().getCedula());
            } else {
                System.out.println("ERROR: La lista de espera de EV3 es NULL.");
            }

        }

        System.out.println("FLAG 2");

        sistema.comprarEntrada("33333333", "EV1");

        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV1-33333333", retorno.valorString);
        System.out.println("FLAG 3");

        retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);

        assertEquals("EV3-11111149", retorno.valorString);
        System.out.println("FLAG 4");

        assertTrue(ev3.getListaEspera().esVacia());
        boolean davidHasTicket = false;
        for (int i = 0; i < ev3.getEntradasVendidas().cantElementos(); i++) {
            if (ev3.getEntradasVendidas().obtenerPorIndice(i).getCliente().getCedula().equals("44444444")) {
                davidHasTicket = true;
                break;
            }
        }
        assertTrue("David should have a ticket for EV3", davidHasTicket);

        assertEquals("44444444", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals("EV3", sistema.pilaEntradas.verCima().getEvento().getCodigo());
        retorno = sistema.deshacerUtimasCompras(1);

    }

    @Test
    public void testDeshacerUltimasCompras_N_MayorQueComprasExistentes() {
        sistema.comprarEntrada("11111111", "EV1");
        sistema.comprarEntrada("22222222", "EV2");

        Retorno retorno = sistema.deshacerUtimasCompras(5);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("EV1-11111111#EV2-22222222", retorno.valorString);
        assertTrue(sistema.pilaEntradas.esVacia());
    }

    @Test
    public void testDeshacerUltimasCompras_MultiplesEventosConMismaCedula() {
        sistema.comprarEntrada("11111111", "EV1");
        sistema.comprarEntrada("11111111", "EV2");
        sistema.comprarEntrada("22222222", "EV1");

        Retorno retorno = sistema.deshacerUtimasCompras(2);
        assertEquals(Resultado.OK, retorno.resultado);

        assertEquals("EV1-22222222#EV2-11111111", retorno.valorString);

        Evento ev1 = sistema.buscarEvento("EV1");
        Evento ev2 = sistema.buscarEvento("EV2");

        assertEquals(1, ev1.getEntradasVendidas().cantElementos());
        assertEquals("11111111", ev1.getEntradasVendidas().obtenerPorIndice(0).getCliente().getCedula());

        assertEquals(0, ev2.getEntradasVendidas().cantElementos());

        assertEquals(1, sistema.pilaEntradas.getCantidad());
        assertEquals("11111111", sistema.pilaEntradas.verCima().getCliente().getCedula());
        assertEquals("EV1", sistema.pilaEntradas.verCima().getEvento().getCodigo());
    }
}
