package sistemaAutogestion;

import SistemaAutogestion.Prueba;
import java.time.LocalDate;

public class SistemaTest extends Sistema {

    public static void main(String[] args) {
        Sistema o = new Sistema();
        Prueba p = new Prueba();
//        juegodeprueba2(o, p);
//        juegodeprueba3(o, p);
        juegodeprueba4(o, p);
    }

    public static void juegodeprueba2(Sistema o, Prueba p) {
        // operaciones 1.1, 1.2, 1.3, 1.4, 1.5, 2.1, 2.2, 2.3, 2.4. - parte 1

        // 1.1 - Crear Sistema
        p.ver(o.crearSistemaDeGestion().resultado, Retorno.Resultado.OK, "1.1 - Se crea sistema");

        //1.2 - Registrar Salas
        p.ver(o.registrarSala("Sala 1", 10).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 1 valida");
        p.ver(o.registrarSala("Sala 1", 10).resultado, Retorno.Resultado.ERROR_1, "1.2 - Error_1 - Se intenta registrar Sala 1 que ya existe");
        p.ver(o.registrarSala("sala 2", -10).resultado, Retorno.Resultado.ERROR_2, "1.2 - Error_2 - Se intenta registrar sala 2 con capacidad negativa");

        p.ver(o.registrarSala("Sala 2", 20).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 2 valida");
        p.ver(o.registrarSala("Sala 3", 30).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 3 valida");
        p.ver(o.registrarSala("Sala 4", 40).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 4 valida");
        p.ver(o.registrarSala("Sala 5", 50).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 5 valida");
        p.ver(o.registrarSala("Sala 6", 50).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 6 valida");
        p.ver(o.registrarSala("Sala 7", 50).resultado, Retorno.Resultado.OK, "1.2 - ok - Se registra Sala 7 valida");

        //Mostrar Salas
        // 2.1 - Listar salas
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listado de salas \n" + o.listarSalas().valorString);

        // 1.3 - Eliminar Sala
        p.ver(o.eliminarSala("Sala 4").resultado, Retorno.Resultado.OK, "1.3 - ok - Se elimina sala 4");
        p.ver(o.eliminarSala("Sala 4").resultado, Retorno.Resultado.ERROR_1, "1.3 - Error_1 - Se intenta eliminar sala 4 que ya no existe");

        //Mostrar Salas luego de eliminar sala 4
        // 2.1 - Listar salas
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listado de salas luego de eliminar sala 4 \n" + o.listarSalas().valorString);

        //1.4 - Registrar Evento
        LocalDate fecha = LocalDate.of(2025, 6, 10);
        p.ver(o.registrarEvento("E001", "Evento A", 30, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E001");
        p.ver(o.registrarEvento("E001", "Evento A", 30, fecha).resultado, Retorno.Resultado.ERROR_1, "1.4 Error 1 - Se intenta registrar Evento E001 que existe");
        p.ver(o.registrarEvento("E002", "Evento B", -10, fecha).resultado, Retorno.Resultado.ERROR_2, "1.4 - Error 2 - Se registra Evento E002 con aforo negativo");
        p.ver(o.registrarEvento("E003", "Evento C", 3000, fecha).resultado, Retorno.Resultado.ERROR_3, "1.4 Error 3 - Se registra Evento E003 pero no hay sala disponible para ese aforo");

        p.ver(o.registrarEvento("E004", "Evento D", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E004");
        p.ver(o.registrarEvento("E005", "Evento E", 30, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E005");
        p.ver(o.registrarEvento("E006", "Evento F", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E006");
        p.ver(o.registrarEvento("E007", "Evento G", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E007");

        // Mostrar Eventos
        //2.2 - Listar Eventos
        p.ver(o.listarEventos().resultado, Retorno.Resultado.OK, "2.2 ok Listado de eventos \n " + o.listarEventos().valorString);

        // 1.5 - Registrar Cliente
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra juan");
        p.ver(o.registrarCliente("123456", "Pedro").resultado, Retorno.Resultado.ERROR_1, "1.5 Error_1 - Se intenta regisrar pedro con cedula invalida");
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.ERROR_2, "1.5 Error_2 - Se intenta registrar juan que ya existe");

        p.ver(o.registrarCliente("12345123", "Ana").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Ana");
        p.ver(o.registrarCliente("12345456", "Maria").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Maria");
        p.ver(o.registrarCliente("12345789", "Carlos").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Carlos");

        //mostrar clientes
        //2.3 - Listar Clientes
        p.ver(o.listarClientes().resultado, Retorno.Resultado.OK, "2.3 - Listar Clientes \n " + o.listarClientes().valorString);

        //1.6  - Comprar entrada
        p.ver(o.comprarEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.6 - Juan compro entrada en evento E001");
        p.ver(o.comprarEntrada("12345999", "E001").resultado, Retorno.Resultado.ERROR_1, "1.6 - cliente no existe intenta comprar entrada en evento E001");
        p.ver(o.comprarEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.6 - Juan intenta comprar entrada en evento E000 que no existe");

        p.ver(o.comprarEntrada("12345123", "E001").resultado, Retorno.Resultado.OK, "1.6 - Ana compra entrada en E001");
        p.ver(o.comprarEntrada("12345456", "E001").resultado, Retorno.Resultado.OK, "1.6 - Maria compra entrada en E001");
        p.ver(o.comprarEntrada("12345789", "E001").resultado, Retorno.Resultado.OK, "1.6 - Carlos compra entrada en E001");

        p.ver(o.comprarEntrada("12345123", "E004").resultado, Retorno.Resultado.OK, "1.6 - Ana compra entrada en E004");

        //1.7 Eliminar evento
        p.ver(o.eliminarEvento("E007").resultado, Retorno.Resultado.OK, "1.7 - Se elimina E007 ");

        p.ver(o.eliminarEvento("E999").resultado, Retorno.Resultado.ERROR_1, "1.7 - Se intenta eliminar E999 que no exixte");
        p.ver(o.eliminarEvento("E001").resultado, Retorno.Resultado.ERROR_2, "1.7 - Se intenta eliminar E001 que tiene entradas vendidas");

        //1.8 Devolver entrada
        p.ver(o.devolverEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.8 - Se devuelve entrada de cliente 12345678 para evento E001 inexistente");

        p.ver(o.devolverEntrada("99999999", "E001").resultado, Retorno.Resultado.ERROR_1, "1.8 - Se intenta devolver entrada de cliente 99999999 inexistente");
        p.ver(o.devolverEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.8 - Se intenta devolver entrada de evento E999 inexistente");

        //1.9 calificar evento
        p.ver(o.calificarEvento("12345456", "E001", 5, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Maria califica evento E001");
        p.ver(o.calificarEvento("12345123", "E001", 7, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Ana califica evento E001");
        p.ver(o.calificarEvento("12345789", "E001", 8, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Carlos califica evento E001");

        p.ver(o.calificarEvento("99999999", "E001", 8, "Me gusto").resultado, Retorno.Resultado.ERROR_1, "1.9 - cliene 99999999 que no existe califica evento E001");
        p.ver(o.calificarEvento("12345789", "E999", 8, "Me gusto").resultado, Retorno.Resultado.ERROR_2, "1.9 - Carlos califica evento E999 que no existe");
        p.ver(o.calificarEvento("12345678", "E001", 20, "Me gusto").resultado, Retorno.Resultado.ERROR_3, "1.9 - Juan califica evento E001 con valor fuera de rango");
        p.ver(o.calificarEvento("12345123", "E001", 7, "Me gusto").resultado, Retorno.Resultado.ERROR_4, "1.9 - Ana califica evento E001 que ya habia calificado");

        //----------------------------------------
        //REPORTES
        //-----------------------------------------
        //Mostrar Salas
        // 2.1 - Listar salas
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listado de salas \n" + o.listarSalas().valorString);

        //2.2 - Listar Eventos
        p.ver(o.listarEventos().resultado, Retorno.Resultado.OK, "2.2 ok Listado de eventos \n " + o.listarEventos().valorString);

        //2.3 - Listar Clientes
        p.ver(o.listarClientes().resultado, Retorno.Resultado.OK, "2.3 - Listar Clientes \n " + o.listarClientes().valorString);

        // 2.4 - Evaluar sala optima - matriz optima
        String[][] vistaOptima = {
            {"#", "#", "#", "#", "#"},
            {"#", "O", "O", "X", "#"},
            {"#", "O", "O", "X", "#"},
            {"#", "O", "O", "X", "#"},
            {"#", "#", "#", "#", "#"}
        };

        p.ver(o.esSalaOptima(vistaOptima).resultado, Retorno.Resultado.OK, "2.4 - Evaluar sala optima (esperado: Es optimo)");

        // 2.4 - Evaluar sala optima - matriz no optima
        String[][] vistaNoOptima = {
            {"#", "#", "#", "#"},
            {"#", "O", "X", "#"},
            {"#", "O", "X", "#"},
            {"#", "X", "X", "#"},
            {"#", "#", "#", "#"}
        };
        p.ver(o.esSalaOptima(vistaNoOptima).resultado, Retorno.Resultado.ERROR_1, "2.4 - Evaluar sala no optima (esperado: No es optimo)");

        //2.5 - Listar Clientes Evento
        p.ver(o.listarClientesDeEvento("E001", 3).resultado, Retorno.Resultado.OK, "2.5 - Listo los 3 clientes del evento E001");
        p.ver(o.listarClientesDeEvento("E001", 20).resultado, Retorno.Resultado.OK, "2.5 - Listo los 20 clientes del evento E001");

        p.ver(o.listarClientesDeEvento("E999", 3).resultado, Retorno.Resultado.ERROR_1, "2.5 - Listo los 3 clientes del evento E999 que no existe");
        p.ver(o.listarClientesDeEvento("E001", -20).resultado, Retorno.Resultado.OK, "2.5 - Listo clientes con cantidad negativa");

        //2.6 Lista Espera Evento
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "2.6 - Lista de espera por evento");

        //2.7 Deshacer compra entrada
        p.ver(o.deshacerUtimasCompras(2).resultado, Retorno.Resultado.OK, "2.7 - ultimas 2 compras");

        //2.8 Evento mejor puntuado
        p.ver(o.eventoMejorPuntuado().resultado, Retorno.Resultado.OK, "2.8 - Evento mejor puntuado");

        //2.9 Compras de cliente
        p.ver(o.comprasDeCliente("12345678").resultado, Retorno.Resultado.OK, "2.9 - Compras de Juan");
        p.ver(o.comprasDeCliente("99999999").resultado, Retorno.Resultado.ERROR_1, "2.9 - Compras de 99999999 que no existe");

        //2.10 Cantidad de compras por dia
        p.ver(o.comprasXDia(1).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 1");
        p.ver(o.comprasXDia(2).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 2");
        p.ver(o.comprasXDia(3).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 3");
        p.ver(o.comprasXDia(4).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 4");
        p.ver(o.comprasXDia(5).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 5");
        p.ver(o.comprasXDia(6).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 6");
        p.ver(o.comprasXDia(7).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 7");
        p.ver(o.comprasXDia(8).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 8");
        p.ver(o.comprasXDia(9).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 9");
        p.ver(o.comprasXDia(10).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 10");
        p.ver(o.comprasXDia(11).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 11");
        p.ver(o.comprasXDia(12).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 12");
        p.ver(o.comprasXDia(14).resultado, Retorno.Resultado.ERROR_1, "2.10 - compras por dia del mes 14 que no existe");

        p.imprimirResultadosPrueba();

    }

//----------------------------------------------------------------------
    public static void juegodeprueba3(Sistema o, Prueba p) {
        // 1.1 Crear sistema
        p.ver(o.crearSistemaDeGestion().resultado, Retorno.Resultado.OK, "1.1 - Se crea sistema");

        // 1.2 Registrar salas
        p.ver(o.registrarSala("Sala 1", 10).resultado, Retorno.Resultado.OK, "1.2 - Se registra Sala 1");
        p.ver(o.registrarSala("Sala 1", 10).resultado, Retorno.Resultado.ERROR_1, "1.2 - Sala duplicada");
        p.ver(o.registrarSala("Sala 2", -5).resultado, Retorno.Resultado.ERROR_2, "1.2 - Capacidad negativa");
        p.ver(o.registrarSala("Sala 2", 20).resultado, Retorno.Resultado.OK, "1.2 - Se registra Sala 2");
        p.ver(o.registrarSala("Sala 3", 30).resultado, Retorno.Resultado.OK, "1.2 - Se registra Sala 3");

        // 1.3 Eliminar sala
        p.ver(o.eliminarSala("Sala 2").resultado, Retorno.Resultado.OK, "1.3 - Se elimina Sala 2");
        p.ver(o.eliminarSala("Sala 2").resultado, Retorno.Resultado.ERROR_1, "1.3 - Sala no existe");

        // 1.4 Registrar eventos
        LocalDate fecha = LocalDate.of(2025, 6, 10);
        p.ver(o.registrarEvento("E001", "Evento 1", 10, fecha).resultado, Retorno.Resultado.OK, "1.4 - Evento 1");
        p.ver(o.registrarEvento("E001", "Evento 1", 10, fecha).resultado, Retorno.Resultado.ERROR_1, "1.4 - Evento duplicado");
        p.ver(o.registrarEvento("E002", "Evento 2", -5, fecha).resultado, Retorno.Resultado.ERROR_2, "1.4 - Aforo negativo");
        p.ver(o.registrarEvento("E003", "Evento 3", 3000, fecha).resultado, Retorno.Resultado.ERROR_3, "1.4 - Aforo sin sala");

        // 1.5 Registrar clientes
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.OK, "1.5 - Cliente Juan");
        p.ver(o.registrarCliente("123", "Pedro").resultado, Retorno.Resultado.ERROR_1, "1.5 - Cédula inválida");
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.ERROR_2, "1.5 - Cliente duplicado");

        p.ver(o.registrarCliente("87654321", "Ana").resultado, Retorno.Resultado.OK, "1.5 - Cliente Ana");

        // 1.6 Comprar entradas
        p.ver(o.comprarEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.6 - Juan compra en E001");
        p.ver(o.comprarEntrada("99999999", "E001").resultado, Retorno.Resultado.ERROR_1, "1.6 - Cliente inexistente");
        p.ver(o.comprarEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.6 - Evento inexistente");

        // 1.7 Eliminar evento
        p.ver(o.eliminarEvento("E002").resultado, Retorno.Resultado.OK, "1.7 - Se elimina E002");
        p.ver(o.eliminarEvento("E999").resultado, Retorno.Resultado.ERROR_1, "1.7 - Evento no existe");
        p.ver(o.eliminarEvento("E001").resultado, Retorno.Resultado.ERROR_2, "1.7 - Tiene entradas vendidas");

        // 1.8 Devolver entrada
        p.ver(o.devolverEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.8 - Devolución válida");
        p.ver(o.devolverEntrada("00000000", "E001").resultado, Retorno.Resultado.ERROR_1, "1.8 - Cliente no existe");
        p.ver(o.devolverEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.8 - Evento no existe");

        // 1.9 Calificar evento
        p.ver(o.comprarEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "");
        p.ver(o.calificarEvento("12345678", "E001", 7, "Buen evento").resultado, Retorno.Resultado.OK, "1.9 - Calificación OK");
        p.ver(o.calificarEvento("00000000", "E001", 5, "").resultado, Retorno.Resultado.ERROR_1, "1.9 - Cliente no existe");
        p.ver(o.calificarEvento("12345678", "E999", 5, "").resultado, Retorno.Resultado.ERROR_2, "1.9 - Evento no existe");
        p.ver(o.calificarEvento("12345678", "E001", 20, "").resultado, Retorno.Resultado.ERROR_3, "1.9 - Calificación inválida");
        p.ver(o.calificarEvento("12345678", "E001", 5, "").resultado, Retorno.Resultado.ERROR_4, "1.9 - Doble calificación");

        // 2.1 Listar salas
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listar salas");

        // 2.2 Listar eventos
        p.ver(o.listarEventos().resultado, Retorno.Resultado.OK, "2.2 - Listar eventos");

        // 2.3 Listar clientes
        p.ver(o.listarClientes().resultado, Retorno.Resultado.OK, "2.3 - Listar clientes");

        // 2.4 Sala óptima
        String[][] optima = {
            {"#", "#", "#"},
            {"#", "O", "#"},
            {"#", "#", "#"}
        };
        p.ver(o.esSalaOptima(optima).resultado, Retorno.Resultado.OK, "2.4 - Sala óptima");

        String[][] noOptima = {
            {"#", "#", "#"},
            {"#", "X", "#"},
            {"#", "#", "#"}
        };
        p.ver(o.esSalaOptima(noOptima).resultado, Retorno.Resultado.ERROR_1, "2.4 - Sala no óptima");

        // 2.5 Listar clientes de evento
        p.ver(o.listarClientesDeEvento("E001", 2).resultado, Retorno.Resultado.OK, "2.5 - Clientes de E001");
        p.ver(o.listarClientesDeEvento("E999", 2).resultado, Retorno.Resultado.ERROR_1, "2.5 - Evento no existe");

        // 2.6 Lista de espera
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "2.6 - Lista espera");

        // 2.7 Deshacer últimas compras
        p.ver(o.deshacerUtimasCompras(1).resultado, Retorno.Resultado.OK, "2.7 - Deshacer compras");

        // 2.8 Mejor puntuado
        p.ver(o.eventoMejorPuntuado().resultado, Retorno.Resultado.OK, "2.8 - Evento mejor puntuado");

        // 2.9 Compras por cliente
        p.ver(o.comprasDeCliente("12345678").resultado, Retorno.Resultado.OK, "2.9 - Compras Juan");
        p.ver(o.comprasDeCliente("00000000").resultado, Retorno.Resultado.ERROR_1, "2.9 - Cliente no existe");

        // 2.10 Compras por día del mes
        for (int i = 1; i <= 12; i++) {
            p.ver(o.comprasXDia(i).resultado, Retorno.Resultado.OK, "2.10 - Compras mes " + i);
        }
        p.ver(o.comprasXDia(13).resultado, Retorno.Resultado.ERROR_1, "2.10 - Mes inválido");
        /*
    // 3.1 Eliminar cliente
    p.ver(o.eliminarCliente("12345678").resultado, Retorno.Resultado.OK, "3.1 - Eliminar Juan");
    p.ver(o.eliminarCliente("12345678").resultado, Retorno.Resultado.ERROR_1, "3.1 - Ya eliminado");

    // 3.2 Modificar calificación
    p.ver(o.modificarCalificacion("87654321", "E001").resultado, Retorno.Resultado.ERROR_3, "3.2 - No ha calificado");
    p.ver(o.calificarEvento("87654321", "E001", 8, "Excelente").resultado, Retorno.Resultado.OK, "");
    p.ver(o.modificarCalificacion("87654321", "E001").resultado, Retorno.Resultado.OK, "3.2 - Modifica calificación");
    p.ver(o.modificarCalificacion("00000000", "E001").resultado, Retorno.Resultado.ERROR_1, "3.2 - Cliente no");
         */
        p.imprimirResultadosPrueba();
//-----------------------------------------------------------------------    

    }

//-----------------------------------
// juego de prueba 4
    public static void juegodeprueba4(Sistema o, Prueba p) {
        p.ver(o.crearSistemaDeGestion().resultado, Retorno.Resultado.OK, "1.1 - Se crea sistema");

        // Registrar salas
        p.ver(o.registrarSala("Sala 1", 10).resultado, Retorno.Resultado.OK, "Se registra Sala 1");
        p.ver(o.registrarSala("Sala 2", 20).resultado, Retorno.Resultado.OK, "Se registra Sala 2");
        p.ver(o.registrarSala("Sala 3", 30).resultado, Retorno.Resultado.OK, "Se registra Sala 3");

        // Eliminar una sala
        p.ver(o.eliminarSala("Sala 2").resultado, Retorno.Resultado.OK, "Se elimina Sala 2");

        // Registrar eventos
        LocalDate fecha = LocalDate.of(2025, 6, 10);
        p.ver(o.registrarEvento("E001", "Evento 1", 10, fecha).resultado, Retorno.Resultado.OK, "Evento E001 registrado");
        p.ver(o.registrarEvento("E002", "Evento 2", 30, fecha).resultado, Retorno.Resultado.OK, "Evento E002 registrado");

        // Registrar clientes
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.OK, "Cliente Juan registrado");
        p.ver(o.registrarCliente("87654321", "Ana").resultado, Retorno.Resultado.OK, "Cliente Ana registrado");

        // Comprar entradas
        p.ver(o.comprarEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "Juan compra entrada E001");
        p.ver(o.comprarEntrada("87654321", "E001").resultado, Retorno.Resultado.OK, "Ana compra entrada E001");

        // Calificar evento
        p.ver(o.calificarEvento("12345678", "E001", 8, "Muy bueno").resultado, Retorno.Resultado.OK, "Juan califica E001");
        p.ver(o.calificarEvento("87654321", "E001", 6, "Bien").resultado, Retorno.Resultado.OK, "Ana califica E001");

        // ------------------ LISTADOS --------------------
        // Listar Salas
        Retorno rSalas = o.listarSalas();
        p.ver(rSalas.resultado, Retorno.Resultado.OK, "2.1 - Listar salas:\n" + rSalas.valorString);

        // Listar Eventos
        Retorno rEventos = o.listarEventos();
        p.ver(rEventos.resultado, Retorno.Resultado.OK, "2.2 - Listar eventos:\n" + rEventos.valorString);

        // Listar Clientes
        Retorno rClientes = o.listarClientes();
        p.ver(rClientes.resultado, Retorno.Resultado.OK, "2.3 - Listar clientes:\n" + rClientes.valorString);

        // Listar Clientes del evento E001
        Retorno rClientesEvento = o.listarClientesDeEvento("E001", 5);
        p.ver(rClientesEvento.resultado, Retorno.Resultado.OK, "2.5 - Clientes de evento E001:\n" + rClientesEvento.valorString);

        // Lista de espera evento (no se llenó el evento, debería estar vacía)
        Retorno rEspera = o.listarEsperaEvento();
        p.ver(rEspera.resultado, Retorno.Resultado.OK, "2.6 - Lista de espera:\n" + rEspera.valorString);

        // Mejor puntuado
        Retorno rMejor = o.eventoMejorPuntuado();
        p.ver(rMejor.resultado, Retorno.Resultado.OK, "2.8 - Evento mejor puntuado:\n" + rMejor.valorString);

        // Compras del cliente Juan
        Retorno rComprasJuan = o.comprasDeCliente("12345678");
        p.ver(rComprasJuan.resultado, Retorno.Resultado.OK, "2.9 - Compras de Juan:\n" + rComprasJuan.valorString);

        // Compras por día del mes 6 (junio)
        Retorno rComprasMes = o.comprasXDia(6);
        p.ver(rComprasMes.resultado, Retorno.Resultado.OK, "2.10 - Compras por día (junio):\n" + rComprasMes.valorString);

        // ------------------ FUNCIONALIDADES AGREGADAS ------------------
/*
    // Eliminar cliente
    p.ver(o.eliminarCliente("12345678").resultado, Retorno.Resultado.OK, "3.1 - Se elimina cliente Juan");
    p.ver(o.eliminarCliente("12345678").resultado, Retorno.Resultado.ERROR_1, "3.1 - Cliente ya eliminado");

    // Modificar calificación
    Retorno rMod = o.modificarCalificacion("87654321", "E001");
    p.ver(rMod.resultado, Retorno.Resultado.OK, "3.2 - Ana modifica su calificación en E001");

    // Dar sala con mayor capacidad
    Retorno rSalaMayor = o.darSalaMayor();
    p.ver(rSalaMayor.resultado, Retorno.Resultado.OK, "3.3 - Sala con mayor capacidad:\n" + rSalaMayor.valorString);

    // Eventos con más de 1 asistente
    Retorno rEventosConMas = o.eventosConAsistentesMayoresA(1);
    p.ver(rEventosConMas.resultado, Retorno.Resultado.OK, "3.4 - Eventos con más de 1 asistente:\n" + rEventosConMas.valorString);
         */
        // Finaliza prueba
        p.imprimirResultadosPrueba();
    }

//------------------------------------
}
