package sistemaAutogestion;

import dominio.Evento;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import sistemaAutogestion.Retorno;
import sistemaAutogestion.Retorno.Resultado;
import sistemaAutogestion.Sistema;

public class DeshacerUltimasComprasTestPersonalizado {
     private Sistema sistema;

    public DeshacerUltimasComprasTestPersonalizado () {
 	sistema = new Sistema();
    }
@Before
    public void setUp() {
        sistema = new Sistema();
        sistema.crearSistemaDeGestion();
    }

    
    @Test
public void testDeshacerUltimasCompras_CasoNormal() {
    
    // Registrar eventos y clientes
    sistema.registrarSala("Sala1", 100);
    sistema.registrarEvento("EV1", "Concierto", 50, LocalDate.now());
    sistema.registrarEvento("EV2", "Teatro", 50, LocalDate.now());
    sistema.registrarCliente("11111111", "Cliente1");
    sistema.registrarCliente("22222222", "Cliente2");
    
    // Realizar compras (orden importante para el test)
    sistema.comprarEntrada("11111111", "EV1"); // Primera compra
    sistema.comprarEntrada("22222222", "EV2"); // Última compra
    
    // Deshacer 1 compra (debería deshacer EV2)
    Retorno retorno = sistema.deshacerUtimasCompras(1);
    
    // Verificar resultado
    assertEquals(Resultado.OK, retorno.resultado);
    assertEquals("EV2-22222222", retorno.valorString); // Exact match
}
    @Test
public void testDeshacerUltimasCompras_ConListaEspera() {

    
    // Configuración inicial
    sistema.registrarSala("Sala1", 1); // Capacidad para 1 solo
    sistema.registrarEvento("EV1", "Concierto", 1, LocalDate.now());
    sistema.registrarCliente("11111111", "Cliente1");
    sistema.registrarCliente("22222222", "Cliente2");
    
    // Llenar capacidad
    sistema.comprarEntrada("11111111", "EV1"); // Entrada vendida
    sistema.comprarEntrada("22222222", "EV1"); // Debe ir a lista de espera
    
    // Deshacer 1 compra (debería reasignar al cliente en espera)
    Retorno retorno = sistema.deshacerUtimasCompras(1);
    
    // Verificar
    assertEquals(Resultado.OK, retorno.resultado);
    assertEquals("EV1-11111111", retorno.valorString);
    
    // Verificar que el cliente en espera ahora tiene entrada
    Evento ev = sistema.buscarEvento("EV1");
    boolean clienteTieneEntrada = false;
    for (int i = 0; i < ev.getEntradasVendidas().cantElementos(); i++) {
        if (ev.getEntradasVendidas().obtenerPorIndice(i).getCliente().getCedula().equals("22222222")) {
            clienteTieneEntrada = true;
            break;
        }
    }
    assertTrue(clienteTieneEntrada);
}



    @Test
    public void testDeshacerUltimasCompras_NInvalido() {
    
        Retorno retorno = sistema.deshacerUtimasCompras(0);
        assertEquals(Resultado.ERROR_1, retorno.resultado); // n inválido
    }

    @Test
    public void testDeshacerUltimasCompras_SinEntradas() {

        Retorno retorno = sistema.deshacerUtimasCompras(1);
        assertEquals(Resultado.OK, retorno.resultado);
        assertEquals("No hay entradas vendidas para deshacer.", retorno.valorString);
    }
}