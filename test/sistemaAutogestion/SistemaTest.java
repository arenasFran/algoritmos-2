package sistemaAutogestion;

import java.time.LocalDate;
import sistemaAutogestion.Retorno;
import sistemaAutogestion.Sistema;

public class SistemaTest extends Sistema {

    public static void main(String[] args) {
        Sistema o = new Sistema();
        Prueba p = new Prueba();

//       juegodepruebaCompleto(o, p); 
//         
       juegodeprueba1_1(o,p); 
//             
       juegodeprueba1_2(o, p);    // Registrar salas
       juegodeprueba1_3(o, p);    // Eliminar salas
       juegodeprueba1_4(o, p);    // Registrar Eventos
       juegodeprueba1_5(o, p);    // Registrar Cliente
       juegodeprueba1_6(o, p);    // Comprar Entrada  
       juegodeprueba1_7(o, p);    // Eliminar Evento     
       juegodeprueba1_8(o, p);    // Devolver Entrada
       juegodeprueba1_9(o, p);    // Calificar Evento

// REPORTES Y LISTADOS      
       juegodeprueba2_1(o, p);
       juegodeprueba2_2(o, p);
       juegodeprueba2_3(o, p);
       juegodeprueba2_4(o, p);
       juegodeprueba2_5(o, p);
       juegodeprueba2_6(o, p);
       juegodeprueba2_7(o, p);       
       juegodeprueba2_8(o, p);       
       juegodeprueba2_9(o, p);
       juegodeprueba2_10(o, p); 
       juegodepruebaresultadoprueba(o,p);

    }

    public static void juegodeprueba1_1(Sistema o,Prueba p){
        // 1.1 - Crear Sistema
        p.ver(o.crearSistemaDeGestion().resultado, Retorno.Resultado.OK, "1.1 - Se crea sistema");

    }
    
    public static void juegodeprueba1_2(Sistema o, Prueba p){
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
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listado de salas \n" );

    }    
   public static void juegodeprueba1_3(Sistema o, Prueba p){
        // 1.3 - Eliminar Sala
        p.ver(o.eliminarSala("Sala 4").resultado, Retorno.Resultado.OK, "1.3 - ok - Se elimina sala 4");
        p.ver(o.eliminarSala("Sala 4").resultado, Retorno.Resultado.ERROR_1, "1.3 - Error_1 - Se intenta eliminar sala 4 que ya no existe");

        //Mostrar Salas luego de eliminar sala 4
        // 2.1 - Listar salas
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listado de salas luego de eliminar sala 4 \n");
 
   }    
   public static void juegodeprueba1_4(Sistema o, Prueba p){
        //1.4 - Registrar Evento
        LocalDate fecha = LocalDate.of(2025, 7, 10);
        p.ver(o.registrarEvento("E001", "Evento A", 2, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E001");
        p.ver(o.registrarEvento("E001", "Evento A", 2, fecha).resultado, Retorno.Resultado.ERROR_1, "1.4 Error 1 - Se intenta registrar Evento E001 que existe");
        p.ver(o.registrarEvento("E002", "Evento B", -10, fecha).resultado, Retorno.Resultado.ERROR_2, "1.4 - Error 2 - Se registra Evento E002 con aforo negativo");
        p.ver(o.registrarEvento("E003", "Evento C", 3000, fecha).resultado, Retorno.Resultado.ERROR_3, "1.4 Error 3 - Se registra Evento E003 pero no hay sala disponible para ese aforo");

        p.ver(o.registrarEvento("E004", "Evento D", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E004");
        p.ver(o.registrarEvento("E005", "Evento E", 3, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E005");
        p.ver(o.registrarEvento("E006", "Evento F", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E006");
        p.ver(o.registrarEvento("E007", "Evento G", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E007");
        p.ver(o.registrarEvento("Defensa", "Evento Defensa", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento Defensa");

        // Mostrar Eventos
        //2.2 - Listar Eventos
        p.ver(o.listarEventos().resultado, Retorno.Resultado.OK, "2.2 ok Listado de eventos \n " );
 
   }   
 
   public static void juegodeprueba1_5(Sistema o, Prueba p){
// 1.5 - Registrar Cliente
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra juan");
        p.ver(o.registrarCliente("123456", "Pedro").resultado, Retorno.Resultado.ERROR_1, "1.5 Error_1 - Se intenta regisrar pedro con cedula invalida");
        p.ver(o.registrarCliente("12345678", "Juan").resultado, Retorno.Resultado.ERROR_2, "1.5 Error_2 - Se intenta registrar juan que ya existe");

        
        p.ver(o.registrarCliente("12345123", "Ana").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Ana");
        p.ver(o.registrarCliente("12345456", "Maria").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Maria");
        p.ver(o.registrarCliente("12345789", "Carlos").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Carlos");
        p.ver(o.registrarCliente("44664018", "Rafael").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Rafael");

        //mostrar clientes
        //2.3 - Listar Clientes
        p.ver(o.listarClientes().resultado, Retorno.Resultado.OK, "2.3 - Listar Clientes \n " );

   }   
 
   public static void juegodeprueba1_6(Sistema o, Prueba p){
        //1.6  - Comprar entrada
        p.ver(o.comprarEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.6 - Juan compro entrada en evento E001");
        p.ver(o.comprarEntrada("12345999", "E001").resultado, Retorno.Resultado.ERROR_1, "1.6 - cliente no existe intenta comprar entrada en evento E001");
        p.ver(o.comprarEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.6 - Juan intenta comprar entrada en evento E000 que no existe");

        p.ver(o.comprarEntrada("12345123", "E001").resultado, Retorno.Resultado.OK, "1.6 - Ana compra entrada en E001");
        p.ver(o.comprarEntrada("12345456", "E001").resultado, Retorno.Resultado.OK, "1.6 - Maria compra entrada en E001");
        p.ver(o.comprarEntrada("12345789", "E001").resultado, Retorno.Resultado.OK, "1.6 - Carlos compra entrada en E001");
        
        p.ver(o.comprarEntrada("12345123", "E004").resultado, Retorno.Resultado.OK, "1.6 - Ana compra entrada en E004");
        p.ver(o.comprarEntrada("12345123", "Defensa").resultado, Retorno.Resultado.OK, "1.6 - Ana compra entrada en Defensa");
  
   }      
   public static void juegodeprueba1_7(Sistema o, Prueba p){
        //1.7 Eliminar evento
         p.ver(o.eliminarEvento("E007").resultado, Retorno.Resultado.OK, "1.7 - Se elimina E007 ");
       
        p.ver(o.eliminarEvento("E999").resultado, Retorno.Resultado.ERROR_1, "1.7 - Se intenta eliminar E999 que no exixte");
        p.ver(o.eliminarEvento("E001").resultado, Retorno.Resultado.ERROR_2, "1.7 - Se intenta eliminar E001 que tiene entradas vendidas");

        System.out.print("LISTA DE ENTRADAS Y COLA DE ESPERA EN EVENTO 001 ANTES DE DEVOLVER ENTRADAS \n");
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "Cliente en espera del evento");
        p.ver(o.listarClientesDeEvento("E001",10).resultado, Retorno.Resultado.OK, "Clientes del evento E001");
 
        //1.8 Devolver entrada
        p.ver(o.devolverEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.8 - Se devuelve entrada de cliente 12345678 Juan para evento E001");
        
        System.out.print("LISTA DE ENTRADAS Y COLA DE ESPERA EN EVENTO 001 DESPUES DE DEVOLVER ENTRADAS \n");  
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "Cliente en espera del evento");
        p.ver(o.listarClientesDeEvento("E001",10).resultado, Retorno.Resultado.OK, "Clientes del evento E001");
        
        p.ver(o.devolverEntrada("99999999", "E001").resultado, Retorno.Resultado.ERROR_1, "1.8 - Se intenta devolver entrada de cliente 99999999 inexistente");
        p.ver(o.devolverEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.8 - Se intenta devolver entrada de evento E999 inexistente");

   }     
   public static void juegodeprueba1_8(Sistema o, Prueba p){
//1.8 Devolver entrada
        p.ver(o.devolverEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.8 - Se devuelve entrada de cliente 12345678 para evento E001 inexistente");
        System.out.print("LISTA DE ENTRADAS Y COLA DE ESPERA EN EVENTO 001 DESPUES DE DEVOLVER ENTRADAS");
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "Lista de cliente en espera : ");
        p.ver(o.listarClientesDeEvento("E001",10).resultado, Retorno.Resultado.OK, "Se listan los clientes del evento E001" +o.listarEsperaEvento().valorString);
        
        p.ver(o.devolverEntrada("99999999", "E001").resultado, Retorno.Resultado.ERROR_1, "1.8 - Se intenta devolver entrada de cliente 99999999 inexistente");
        p.ver(o.devolverEntrada("12345678", "E999").resultado, Retorno.Resultado.ERROR_2, "1.8 - Se intenta devolver entrada de evento E999 inexistente");

   
   }     
   
   public static void juegodeprueba1_9(Sistema o, Prueba p){
        //1.9 calificar evento
        
        p.ver(o.calificarEvento("12345456", "E001", 5, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Maria califica evento E001");
        p.ver(o.calificarEvento("12345123", "E001", 7, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Ana califica evento E001");
        p.ver(o.calificarEvento("12345789", "E001", 8, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Carlos califica evento E001");
        
        p.ver(o.calificarEvento("99999999", "E001", 8, "Me gusto").resultado, Retorno.Resultado.ERROR_1, "1.9 - cliene 99999999 que no existe califica evento E001");
        p.ver(o.calificarEvento("12345789", "E999", 8, "Me gusto").resultado, Retorno.Resultado.ERROR_2, "1.9 - Carlos califica evento E999 que no existe");
        p.ver(o.calificarEvento("12345678", "E001", 20, "Me gusto").resultado, Retorno.Resultado.ERROR_3, "1.9 - Juan califica evento E001 con valor fuera de rango");
        p.ver(o.calificarEvento("12345123", "E001", 7, "Me gusto").resultado, Retorno.Resultado.ERROR_4, "1.9 - Ana califica evento E001 que ya habia calificado");
       
        p.ver(o.calificarEvento("12345123", "Defensa", 8, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Ana califica evento Defensa");
  
   }     

   public static void juegodeprueba2_1(Sistema o, Prueba p){
        // 2.1 - Listar salas
        p.ver(o.listarSalas().resultado, Retorno.Resultado.OK, "2.1 - Listado de salas \n" + o.listarSalas().valorString);

   }    

   public static void juegodeprueba2_2(Sistema o, Prueba p){
       //2.2 - Listar Eventos
        p.ver(o.listarEventos().resultado, Retorno.Resultado.OK, "2.2 ok Listado de eventos \n " + o.listarEventos().valorString);

   }    
   
   public static void juegodeprueba2_3(Sistema o, Prueba p){
        //2.3 - Listar Clientes
        p.ver(o.listarClientes().resultado, Retorno.Resultado.OK, "2.3 - Listar Clientes \n " + o.listarClientes().valorString);

   }    

   public static void juegodeprueba2_4(Sistema o, Prueba p){
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

   }       
  public static void juegodeprueba2_5(Sistema o, Prueba p){
        //2.5 - Listar Clientes Evento
        p.ver(o.listarClientesDeEvento("E001", 3).resultado, Retorno.Resultado.OK, "2.5 - Listo los 3 clientes del evento E001");
        p.ver(o.listarClientesDeEvento("E001",20 ).resultado, Retorno.Resultado.OK, "2.5 - Listo los 20 clientes del evento E001");        
  
        p.ver(o.listarClientesDeEvento("E999", 3).resultado, Retorno.Resultado.ERROR_1, "2.5 - Listo los 3 clientes del evento E999 que no existe");
        p.ver(o.listarClientesDeEvento("E001",-20 ).resultado, Retorno.Resultado.ERROR_2, "2.5 - Listo clientes con cantidad negativa");        
          
   
   }
  public static void juegodeprueba2_6(Sistema o, Prueba p){
        //2.6 Lista Espera Evento
        
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "2.6 - Lista de espera por evento");
        

   }

  public static void juegodeprueba2_7(Sistema o, Prueba p){
        //2.7 Deshacer compra entrada
        p.ver(o.deshacerUtimasCompras(2).resultado, Retorno.Resultado.OK, "2.7 - ultimas 2 compras");
        

   }  
  
  public static void juegodeprueba2_8(Sistema o, Prueba p){
        //2.8 Evento mejor puntuado
        p.ver(o.eventoMejorPuntuado().resultado, Retorno.Resultado.OK, "2.8 - Evento mejor puntuado: " + o.eventoMejorPuntuado());
        

   }  

  public static void juegodeprueba2_9(Sistema o, Prueba p){
        //2.9 Compras de cliente
        p.ver(o.comprasDeCliente("12345678").resultado, Retorno.Resultado.OK, "2.9 - Compras de Juan");
        p.ver(o.comprasDeCliente("99999999").resultado, Retorno.Resultado.ERROR_1, "2.9 - Compras de 99999999 que no existe");
       
   }  
  
  public static void juegodeprueba2_10(Sistema o, Prueba p){
        //2.10 Cantidad de compras por dia
        
        p.ver(o.comprasXDia(1).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 1: " );
        p.ver(o.comprasXDia(2).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 2: " );
        p.ver(o.comprasXDia(3).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 3: " );
        p.ver(o.comprasXDia(4).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 4: " );
        p.ver(o.comprasXDia(5).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 5: " );
        p.ver(o.comprasXDia(6).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 6: " );
        p.ver(o.comprasXDia(7).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 7: ");
        p.ver(o.comprasXDia(8).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 8: " );
        p.ver(o.comprasXDia(9).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 9: " );
        p.ver(o.comprasXDia(10).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 10: " );
        p.ver(o.comprasXDia(11).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 11: " );
        p.ver(o.comprasXDia(12).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 12: ");
        p.ver(o.comprasXDia(14).resultado, Retorno.Resultado.ERROR_1, "2.10 - compras por dia del mes 14 que no existe ");

   }  
  
public static void juegodepruebaresultadoprueba(Sistema o, Prueba p){
        p.imprimirResultadosPrueba();

}  
  
   
    public static void juegodepruebaCompleto(Sistema o, Prueba p) {
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
        LocalDate fecha = LocalDate.of(2025, 7, 10);
        p.ver(o.registrarEvento("E001", "Evento A", 2, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E001");
        p.ver(o.registrarEvento("E001", "Evento A", 30, fecha).resultado, Retorno.Resultado.ERROR_1, "1.4 Error 1 - Se intenta registrar Evento E001 que existe");
        p.ver(o.registrarEvento("E002", "Evento B", -10, fecha).resultado, Retorno.Resultado.ERROR_2, "1.4 - Error 2 - Se registra Evento E002 con aforo negativo");
        p.ver(o.registrarEvento("E003", "Evento C", 3000, fecha).resultado, Retorno.Resultado.ERROR_3, "1.4 Error 3 - Se registra Evento E003 pero no hay sala disponible para ese aforo");

        p.ver(o.registrarEvento("E004", "Evento D", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E004");
        p.ver(o.registrarEvento("E005", "Evento E", 30, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E005");
        p.ver(o.registrarEvento("E006", "Evento F", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E006");
        p.ver(o.registrarEvento("E007", "Evento G", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento E007");
        p.ver(o.registrarEvento("Defensa", "Evento Defensa", 20, fecha).resultado, Retorno.Resultado.OK, "1.4 ok - Se registra Evento Defensa");

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
        p.ver(o.registrarCliente("44664018", "Rafael").resultado, Retorno.Resultado.OK, "1.5 ok - Se regisra Rafael");

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
        p.ver(o.comprarEntrada("12345123", "Defensa").resultado, Retorno.Resultado.OK, "1.6 - Ana compra entrada en Defensa");
        
        
        //1.7 Eliminar evento
         p.ver(o.eliminarEvento("E007").resultado, Retorno.Resultado.OK, "1.7 - Se elimina E007 ");
       
        p.ver(o.eliminarEvento("E999").resultado, Retorno.Resultado.ERROR_1, "1.7 - Se intenta eliminar E999 que no exixte");
        p.ver(o.eliminarEvento("E001").resultado, Retorno.Resultado.ERROR_2, "1.7 - Se intenta eliminar E001 que tiene entradas vendidas");

        System.out.print("---------LISTA DE ENTRADAS Y COLA DE ESPERA EN EVENTO 001 ANTES DE DEVOLVER ENTRADAS--------");
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "Lista de cliente en espera"+o.listarEsperaEvento().valorString);
        p.ver(o.listarClientesDeEvento("E001",10).resultado, Retorno.Resultado.OK, "Se listan los clientes del evento E001"+o.listarEsperaEvento().valorString);
 
        
//1.8 Devolver entrada
        p.ver(o.devolverEntrada("12345678", "E001").resultado, Retorno.Resultado.OK, "1.8 - Se devuelve entrada de cliente 12345678 para evento E001 inexistente");
        System.out.print("LISTA DE ENTRADAS Y COLA DE ESPERA EN EVENTO 001 DESPUES DE DEVOLVER ENTRADAS");
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "Lista de cliente en espera"+o.listarEsperaEvento().valorString);
        p.ver(o.listarClientesDeEvento("E001",10).resultado, Retorno.Resultado.OK, "Se listan los clientes del evento E001" +o.listarEsperaEvento().valorString);
        
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
       
        p.ver(o.calificarEvento("12345123", "Defensa", 2, "Me gusto").resultado, Retorno.Resultado.OK, "1.9 - Ana califica evento Defensa");
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
        p.ver(o.listarClientesDeEvento("E001",20 ).resultado, Retorno.Resultado.OK, "2.5 - Listo los 20 clientes del evento E001");        
  
        p.ver(o.listarClientesDeEvento("E999", 3).resultado, Retorno.Resultado.ERROR_1, "2.5 - Listo los 3 clientes del evento E999 que no existe");
        p.ver(o.listarClientesDeEvento("E001",-20 ).resultado, Retorno.Resultado.ERROR_2, "2.5 - Listo clientes con cantidad negativa");        
          
        
        //2.6 Lista Espera Evento
        
        p.ver(o.listarEsperaEvento().resultado, Retorno.Resultado.OK, "2.6 - Lista de espera por evento");
        
        //2.7 Deshacer compra entrada
        p.ver(o.deshacerUtimasCompras(2).resultado, Retorno.Resultado.OK, "2.7 - ultimas 2 compras");
        
        //2.8 Evento mejor puntuado
        p.ver(o.eventoMejorPuntuado().resultado, Retorno.Resultado.OK, "2.8 - Evento mejor puntuado" + o.eventoMejorPuntuado());
        
        //2.9 Compras de cliente
        p.ver(o.comprasDeCliente("12345678").resultado, Retorno.Resultado.OK, "2.9 - Compras de Juan");
        p.ver(o.comprasDeCliente("99999999").resultado, Retorno.Resultado.ERROR_1, "2.9 - Compras de 99999999 que no existe");
       
        
        //2.10 Cantidad de compras por dia
        
        p.ver(o.comprasXDia(1).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 1" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(2).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 2" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(3).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 3" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(4).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 4" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(5).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 5" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(6).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 6" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(7).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 7" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(8).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 8" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(9).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 9" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(10).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 10" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(11).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 11" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(12).resultado, Retorno.Resultado.OK, "2.10 - compras por dia del mes 12" + o.comprasXDia(6).valorString);
        p.ver(o.comprasXDia(14).resultado, Retorno.Resultado.ERROR_1, "2.10 - compras por dia del mes 14 que no existe");

        //ELIMINAR CLIENTE 
        o.listarClientes();
        o.eliminarCliente("44664018");
        o.eliminarCliente("12345789");
        o.eliminarCliente("55555555");
        o.eliminarCliente("55555");
        o.listarClientes();
        
        System.out.println();
        p.ver(o.eventoMejorPuntuado().resultado, Retorno.Resultado.OK, "Evento mejor puntuado" + o.eventoMejorPuntuado());
        o.modificarCalificacion("12345123", "Defensa", 10);
        p.ver(o.eventoMejorPuntuado().resultado, Retorno.Resultado.OK, "Evento mejor puntuado" + o.eventoMejorPuntuado());
        
        p.imprimirResultadosPrueba();
    }
}
