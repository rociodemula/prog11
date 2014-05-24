/*
 * Tarea PROG07: 3ª Modificación de la clase ES
 * Operaciones generales de entrada y salida de datos y helpers asociados.
 */
package basicos;
// Paquete que guardará las clases basicas para PROG.

// Importamos los paquetes necesarios.
import java.text.ParseException; //gestión de excepciones de coincidencias
import java.text.ParsePosition;      //gestión de coincidencias
import java.text.SimpleDateFormat;  //gestión de formatos de fechas
import java.util.Calendar;          //gestión de datos tipo Calendar
import java.util.Date;             // gestión de datos tipo Date
import java.util.InputMismatchException; // Control de excepciones de Scanner
import java.util.Scanner; // Clase Scanner, para gestión de teclado.

/**
 * Clase para gestión de entrada y salida de datos. Toma valores de teclado,
 * controlando excepciones, y lanza mensajes a pantalla con y sin retorno de
 * carro al final.
 *
 * @since version 1.0 13/12/2013 (Tarea PROG04)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.3 12/01/2014 (Tarea PROG07)
 */
public class ES {
    // Todos sus métodos son public static. Podrán ser llamados desde cualquier
    // clase, sin precisar ser instanciados.

    /**
     * Método que recoge de teclado un número entero tipo int.
     *
     * @since version 1.0 13/12/2013
     * @return int Entero recogido de teclado.
     */
    public static int leeEntero() {
        // Aprovechamos con un casting, el método ya elaborado para long.
        return (int) leeEnteroLargo();
    }

    /**
     * Método que vuelca un mensaje en pantalla, añadiéndole ": ", y devuelve un
     * número entero int que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @return int Entero recogido de teclado.
     */
    public static int leeEntero(String mensaje) {
        // Aprovechamos con un casting, el método ya elaborado para long.
        return (int) leeEnteroLargo(mensaje);
    }

    /**
     * Método que vuelca un mensaje en pantalla, añadiéndole ": ", y devuelve un
     * número entero int que recoge de teclado, siendo éste siempre mayor o
     * igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, mas ": "
     * @param minimo int Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @return int Entero recogido de teclado.
     */
    public static int leeEntero(String mensaje, int minimo) {
        // Aprovechamos con un casting, el método ya elaborado para long.
        return (int) leeEnteroLargo(mensaje, minimo);
    }

    /**
     * Método que vuelca un mensaje en pantalla, añadiéndole ": " y devuelve un
     * número entero int que recoge de teclado, siendo éste siempre mayor o
     * igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, mas ": "
     * @param minimo int Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @param maximo int Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @return int Entero recogido por teclado.
     */
    public static int leeEntero(String mensaje, int minimo, int maximo) {
        // Aprovechamos con un casting, el método ya elaborado para long.
        return (int) leeEnteroLargo(mensaje, minimo, maximo);
    }

    /**
     * Método que recoge de teclado un número entero tipo long.
     *
     * @since version 1.0 13/12/2013
     * @return long Número entero recogido de teclado.
     */
    public static long leeEnteroLargo() {
        // Instanciamos objeto clase Scanner, para entradas de teclado.
        Scanner teclado = new Scanner(System.in);
        long enteroLargo = 0;  // Variable para guardar el dato de teclado.
        boolean datoIncorrecto = true;  // Booleano para marcar salida
        do {
            try {
                // Intentamos recoger de teclado un número entero largo
                enteroLargo = teclado.nextLong();
                // Si la entrada es del tipo correcto, cambiamos el booleano.
                datoIncorrecto = false;
            } catch (InputMismatchException e) {
                // Controlamos la excepción general de dato incorrecto.
                // y mandamos a pantalla el mensaje de error.
                ES.msg("\n¡Advertencia!: El programa espera un número entero.\n"
                        + " Introdúzcalo de nuevo, por favor: ");
                // Limpiamos el buffer de teclado, para evitar problemas.
                teclado.next();
            } // Se repetirá el bucle hasta que el dato sea el esperado.
        } while (datoIncorrecto);
        return enteroLargo; // Devolvemos el numero tecleado por el usuario.
    }

    /**
     * Método que vuelca un mensaje en pantalla, añadiéndole ": " y devuelve un
     * número entero long que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrara por pantalla más ": "
     * @return long Entero recogido por teclado.
     */
    public static long leeEnteroLargo(String mensaje) {
        ES.msg(mensaje + ": "); // Completamos el parámetro mensaje 
        // Aprovechamos el método ya elaborado para long sin mensaje.
        return leeEnteroLargo();
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiéndole ": ", y devuelve un
     * número entero long que recoge de teclado, siendo éste siempre mayor o
     * igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, mas ": "
     * @param minimo long Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @return long Entero recogido de teclado.
     */
    public static long leeEnteroLargo(String mensaje, long minimo) {
        long enteroLargo = 0; // Variable que guardará el dato introducido.
        boolean datoIncorrecto = true; // Booleano para marcar entrada correcta.
        do {
            // Aprocvechamos el método que pide un entero largo con mensaje.
            enteroLargo = leeEnteroLargo(mensaje);
            // Condición que controla si enteroLargo está en el rango deseado.
            if (enteroLargo < minimo) {
                ES.msgln("\n¡Advertencia!: El dato debe ser mayor o igual que "
                        + minimo + "."); // Mensaje aclaratorio al usuario.
            } else {
                datoIncorrecto = false; // Si estamos en rango, cambia booleano.
            } // Se repetirá el bucle hasta que el usuario meta el dato correcto.
        } while (datoIncorrecto);
        return enteroLargo; // Devolvemos el dato recogido por teclado.
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiéndole ": " y devuelve un
     * número entero long que recoge de teclado, siendo éste siempre mayor o
     * igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, mas ": "
     * @param minimo long Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @param maximo long Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @return long Entero recogido por teclado.
     */
    public static long leeEnteroLargo(String mensaje, long minimo, long maximo) {
        long enteroLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // booleano para marcar la salida.
        do {
            // Aprovechamos el método que pide en entero largo con mensaje.
            enteroLargo = leeEnteroLargo(mensaje, minimo);
            // Árbol de condiciones para ver si enteroLargo está en rango o no.
            if (enteroLargo > maximo) {
                ES.msgln("\n¡Advertencia!: El dato debe ser menor o igual que"
                        + " " + maximo + "."); //Mensaje informativo.
            } else {
                datoIncorrecto = false; // Cambia booleano si es correcto.
            } // Se repetirá el bucle hasta que el usuario de un entero en rango.
        } while (datoIncorrecto);
        return enteroLargo; // Devolvemos en número recogido por teclado.
    }

    /**
     * Método que recoge de teclado un número real tipo float.
     *
     * @since version 1.0 13/12/2013
     * @return float Real recogido de teclado.
     */
    public static float leeReal() {
        // Aprovechamos con un casting el método ya escrito para double.
        return (float) leeRealLargo();
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiéndole ": ", y devuelve un
     * número real float que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @return float Real recogido de teclado.
     */
    public static float leeReal(String mensaje) {
        // Aprovechamos con un casting el método ya elaborado para double.
        return (float) leeRealLargo(mensaje);
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiéndole ": ", y devuelve un
     * número real float que recoge de teclado, siendo éste siempre mayor o
     * igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, mas ": "
     * @param minimo float Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @return float Real recogido de teclado.
     */
    public static float leeReal(String mensaje, float minimo) {
        // Aprovechamos, con un casting, el método ya elaborado para double.
        return (float) leeRealLargo(mensaje, minimo);
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiendole ": " y devuelve un
     * número real tipo float que recoge de teclado, siendo éste siempre mayor o
     * igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @param minimo float Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @param maximo float Pedirá valores al usuario, hasta que teclee un número
     * mayor o igual que éste.
     * @return float Real recogido de teclado.
     */
    public static float leeReal(String mensaje, float minimo, float maximo) {
        // Aprovechamos, con un casting, el método ya elaborado para double.
        return (float) leeRealLargo(mensaje, minimo, maximo);
    }

    /**
     * Método que recoge de teclado un número real tipo double.
     *
     * @since version 1.0 13/12/2013
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo() {
        // Instanciamos un objeto de la clase Scanner, para entradas de teclado.
        Scanner teclado = new Scanner(System.in);
        double realLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; //Booleano para marcar dato correcto.
        do {
            try {
                // Intentamos recoger un dato real de teclado.
                realLargo = teclado.nextDouble();
                datoIncorrecto = false; // Si es del tipo esperado, cambia booleano.
            } catch (InputMismatchException e) {
                // Controlamos la excepción de dato incorrecto.
                ES.msg("\n¡Advertencia!: El programa espera un número. "
                        + "Si introduce decimales,\n  use la coma para separarlos"
                        + " de la parte entera, no el punto.\n  Por favor, "
                        + "introdúzcalo de nuevo: "); // Mensaje de error.
                teclado.next(); // Limpiamos buffer de teclado.
            } //Se repetirá el bucle hasta que el usuario meta el dato esperado.
        } while (datoIncorrecto);
        return realLargo; // Devolvemos el dato recogido por teclado.
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiéndole ": " y devuelve un
     * número real tipo double que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo(String mensaje) {
        ES.msg(mensaje + ": "); // Añadimos espacio a la cadena mensaje.
        // Aprovechamos el método ya elaborado para double sin mensaje.
        return leeRealLargo();
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiéndole ": " y devuelve un
     * número real tipo double que recoge de teclado, siendo éste siempre mayor
     * o igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @param minimo double Pedirá valores al usuario, hasta que teclee un
     * número mayor o igual que éste.
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo(String mensaje, double minimo) {
        double realLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // bolleano para controlar dato correcto.
        do {
            // Aprovechamos el método ya escrito para double con mensaje
            realLargo = leeRealLargo(mensaje);
            if (realLargo < minimo) { // Miramos si el número está en rango.
                ES.msgln("\n¡Advertencia!: El dato debe ser mayor o igual que "
                        + minimo + ".");
            } else { // Si lo está cambiamos el booleano para salir del bucle.
                datoIncorrecto = false;
            } // Pedirá de nuevo el número hasta que esté en el rango esperado.
        } while (datoIncorrecto);
        return realLargo; //Devolvemos el dato recogido por teclado.
    }

    /**
     * Método que vuelca un mensaje en pantalla añadiendole ": " y devuelve un
     * número real tipo double que recoge de teclado, siendo éste siempre mayor
     * o igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @param minimo double Pedirá valores al usuario, hasta que teclee un
     * número mayor o igual que éste.
     * @param maximo double Pedirá valores al usuario, hasta que teclee un
     * número mayor o igual que éste.
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo(String mensaje, double minimo,
            double maximo) {
        double realLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // booleano para controlar dato correcto.
        do {
            // Aprovechamos método ya escrito para double con mensaje.
            realLargo = leeRealLargo(mensaje, minimo);
            // Árbol de decisiones para comprobar si el dato está en rango.
            if (realLargo > maximo) {
                ES.msgln("\n¡Advertencia!: El dato debe ser menor o igual que"
                        + " " + maximo + "."); // Mensaje de error.
            } else {
                datoIncorrecto = false; // Si está en rango, cambia el booleano.
            } // Seguiremos pidiendo el dato haata que sea correcto.
        } while (datoIncorrecto);
        return realLargo; // Devolvemos el dato recogido de teclado.
    }

    /**
     * Método que devuelve una cadena recogida por teclado, que será siempre un
     * carácter "S", "s", "N" o "n".
     *
     * @since version 1.0 13/12/2013
     * @return String Devuelve el dato recogido por teclado.
     */
    public static String leeRespuesta() {
        // Instanciamos objeto clase Scanner, para la gestión de teclado.
        Scanner teclado = new Scanner(System.in);
        String respuesta; // Declaramos variable necesaria.
        boolean datoIncorrecto = true; // booleano para contrelar dato correcto.
        do {
            // Recogemos un dato de teclado, en minúsculas.
            respuesta = teclado.next().toLowerCase();
            // Si es una s o una n, cambiamos el booleano.
            if ("s".equals(respuesta) || "n".equals(respuesta)) {
                datoIncorrecto = false;
            } else { // Mensaje de error, si el dato no es S, s, N o n.
                ES.msgln("\n¡Advertencia!: El dato teleado debe ser una 's' "
                        + "o una 'n' (mayúscula o minúscula).\n  Por favor, "
                        + "introdúzcalo de nuevo: ");
            }
            // Pedirá el dato hasta que sea el esperado (S, s, N o n)
        } while (datoIncorrecto);
        return respuesta; // Devolvemos el dato recogido por teclado.
    }

    /**
     * Método que vuelca un mensaje a pantalla, seguido de "(s/n): ", y devuelve
     * una cadena recogida por teclado, que será siempre un carácter "S", "s",
     * "N" o "n".
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @return String Devuelve respuesta dada por el usuario.
     */
    public static String leeRespuesta(String mensaje) {
        ES.msg(mensaje + "(s/n): "); // Completamos la cadena mensaje.
        // Aprovechamos el método ya escrito leeRespuesta().
        return leeRespuesta();
    }

    /**
     * Método que devuelve una cadena recogida por teclado.
     *
     * @since version 1.0 13/12/2013
     * @return String Devuelve la cadena tecleada por el usuario.
     */
    public static String leeCadena() {
        // Instanciamos objeto clase Scanner, para entradas de teclado.
        Scanner teclado = new Scanner(System.in);
        String cadena = ""; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // booleano para control dato correcto.
        do {
            try {
                // Intentamos recoger un dato de teclado.
                cadena = teclado.nextLine();
                datoIncorrecto = false; // Si es correcto, cambia booleano
            } catch (InputMismatchException e) {
                // Se intenta controlar posible excepción, (no saltará nunca).
                ES.msgln("\n¡Advertencia!: El dato teleado debe ser una "
                        + "cadena de caracteres y será almacenado respetando "
                        + "espacios, mayúsculas y minúsculas.");
                teclado.next(); // limpiamos buffer.
            } // Salvo casos que no puedo imaginar, se ejecutará una sola vez.
        } while (datoIncorrecto);
        return cadena; // Devolvemos dato recogido por teclado.
    }

    /**
     * Método que vuelca un mensaje a pantalla, seguido de ": " y devuelve una
     * cadena recogida por teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @return String Devuelve la cadena tecleada por el usuario.
     */
    public static String leeCadena(String mensaje) {
        ES.msg(mensaje + ": "); // Completamos cadena mensaje.
        // Aprovechamos método ya escrito para cadena sin mensaje.
        return leeCadena();
    }

    /**
     * Método que recoge fecha válida de teclado formato dd-mm-aaaa
     *
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @see basicos.Validar#validaFecha(java.lang.String)
     * @since version 1.1 28/12/2013
     * @return String validado en formato dd-mm-aaaa
     */
    public static String leeFecha() {
        String fecha = null; //variable que guardará la fecha recogida.
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // métodos auxiliares que arrojan excepciones
            try {
            //Guardamos una cadena sin validar que cuadre con la expresión
                //regular previa a dd-mm-aaaa (2dígitos-2digitos-4digitos)
                fecha = Validar.filtraPatron("[0-9]{2}-[0-9]{2}-[0-9]{4}");
                validado = Validar.validaFecha(fecha);
            } catch (ParseException e) {
                ES.msg("\n¡Advertencia!: La fecha tecleada no es válida\n  Por "
                        + "favor, introdúzcala de nuevo: ");
            } catch (InputMismatchException e) {
                ES.msg("\n¡Advertencia!: La fecha debe estar en formato dd-mm-aaaa\n"
                        + "  Por favor, introdúzcala de nuevo: ");
            }
            // A continuación comprobaremos si la cadena cuadra con una fecha 
            // válida, y mientras no cuadre, seguirá pidiendola al usuario.
        } while (!validado);
        return fecha; //Devolvemos la cadena con fecha validada.
    }

    /**
     * Método que visualiza un mensaje en pantalla, seguido de ": " y recoge
     * fecha válida de teclado en formato dd-mm-aaaa
     *
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se visualizará, más ": "
     * @return String validado en formato dd-mm-aaaa
     */
    public static String leeFecha(String mensaje) {
        // Añadimos, por comodidad, el ": " al mensaje indicado.
        ES.msg(mensaje + ": ");
        // Aprovechamos el método ya escrito y devlvemos la fecha válida.
        return leeFecha();
    }
    
    /**
     * Método que compara 2 fechas. Devuelve -1 si la primera es anterior a la
     * segunda, 0 si son iguales, y +1 si la segunda es anterior a la primera
     * 
     * PREMISA: ambas fechas deben estar en formato dd-mm-aaaa y ser válidas.
     * 
     * @since version 1.3 15/02/2014 (PROG07)
     * @param cadena1 String con primera fecha validada y en formato dd-mm-aaaa
     * @param cadena2 String con segunda fecha validada y en formato dd-mm-aaaa
     * @return int con los valores:
     *      -1 si cadena1 < cadena2
     *       0 si cadena1 = cadena2
     *      +1 si cadena1 > cadena2 
     */
    public static int compararFecha(String cadena1, String cadena2){
        // se inicializa la variable int para devolver el resultado
        int compara = 0;
        // inicializamos variable para el formato de fecha deseado
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        // definimos 2 variables tipo Date para alojar cadenas a comparar
        Date fecha1 = formato.parse(cadena1, new ParsePosition(0));
        Date fecha2 = formato.parse(cadena2, new ParsePosition(0));
        // comparamos la fecha1 con la fecha2...,
            if (fecha1.before(fecha2)) {
                compara = -1;
            }
            if (fecha1.after(fecha2)) {
                compara = 1;            
            }
            //devolvemos el resultado correspondiente.
        return compara;        
    }

    /**
     * Método que visualiza un mensje en pantalla, seguido de ": " y recoge una
     * fecha valida de teclado, y mayor o igual a la facilitada en minimo.
     *
     * PREMISA: La fecha facilitada en mínimo debe estar en formato dd-mm-aaaa
     *
     * @see basicos.ES#compararFecha(java.lang.String, java.lang.String) 
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se mostrará al usuario.
     * @param minimo String con fecha en formato dd-mm-aaaa .
     * @return String con fecha válida mayor o igual a la facilitada.
     */
    public static String leeFecha(String mensaje, String minimo) {
        String fechaTeclado; // Variable que alojará la fecha tecleada
        boolean fechaIncorrecta = true; //booleano para marcar objetivo
        do {
            // Pedimos al usuario la fecha con el método ya escrito
            fechaTeclado = leeFecha(mensaje);
            // comparamos la fecha del usuario con el minimo,
            if (compararFecha(fechaTeclado, minimo) < 0) {
                // si está fuera de rango, sacamos mensaje de error
                ES.msgln("\n¡Advertencia!: La fecha debe ser mayor o igual que"
                        + " " + minimo + "."); // Mensaje de error.
            } else {
                // Si no, cambiamos el booleano.
                fechaIncorrecta = false;
            }
        } while (fechaIncorrecta); //pediremos fechas mientras no estén en rango
        return fechaTeclado; //devolvemos el String formateada.
    }

    /**
     * Método que visualiza un mensje en pantalla, seguido de ": " y recoge una
     * fecha valida de teclado, mayor o igual a la facilitada en minimo y menor
     * o igual a la facilitada en maximo.
     *
     * PREMISA: Las fechas facilitadas en mínimo/máximo, debe estar en formato
     * dd-mm-aaaa
     *
     * @see basicos.ES#compararFecha(java.lang.String, java.lang.String) 
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se mostrará al usuario.
     * @param minimo String con fecha en formato dd-mm-aaaa.
     * @param maximo String con fecha en formato dd-mm-aaaa.
     * @return String con fecha válida mayor o igual a la facilitada como 2º
     * parámetro, y menor o igual a la facilitada como tercer parámetro.
     */
    public static String leeFecha(String mensaje, String minimo, String maximo) {
        String fechaTeclado;  // Variable que alojará la fecha tecleada
        boolean fechaIncorrecta = true; //booleano para marcar objetivo
        do {
            // Pedimos al usuario la fecha con el método ya escrito
            fechaTeclado = leeFecha(mensaje, minimo);
            if (compararFecha(fechaTeclado, maximo) > 0) {
                // si no, mensaje de error
                ES.msgln("\n¡Advertencia!: La fecha debe ser menor o igual que"
                        + " " + maximo + ".");
            } else {
                // Si está en rango, cambiamos el booleano
                fechaIncorrecta = false;
            }
        } while (fechaIncorrecta); // Pediremos fechas mientras no esté en rango.
        return fechaTeclado; //Devolvemos el String formateado
    }

    /**
     * Método que pide al usuario la fecha de nacimiento de una persona, previo
     * mensaje de petición, y la valida si la fecha se ajusta a un arco temporal
     * desde 120 años antes de ahora, hasta el momento presente. Seguirá
     * pidiendo fechas, hasta que la fecha facilitada esté en rango.
     *
     * @since version 1.1 28/12/2013 (PROG05)
     * @param mensaje String con el mensaje al usuario, más ": "
     * @return String con fecha dentro del espacio de tiempo desde hace 120
     * años, hasta el momento presente.
     */
    public static String leeFechaNacimiento(String mensaje) {
        // Obtenemos el momento actual con una variable tipo Calendar.
        Calendar ahora = Calendar.getInstance();
        // Separamos día, mes y año, en diferentes String
        String dia = Integer.toString(ahora.get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(ahora.get(Calendar.MONTH) + 1);
        String agno = Integer.toString(ahora.get(Calendar.YEAR));
        // Montamos dos String con el formato dd-mm-aaaa, para un rango
        // minimo de hace 120 años, y maximo de hoy.
        String minimo = dia + "-" + mes + "-" + (Integer.parseInt(agno) - 120);
        String maximo = dia + "-" + mes + "-" + agno;
        //retornaremos una fecha válida recogida de teclado, perteneciente
        //a ese rango de fechas.
        return leeFecha(mensaje, minimo, maximo);
    }

    /**
     * Método que recoge NIF/NIE válido de teclado.
     *
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.1 28/12/2013
     * @return String NIF/NIE validado
     */
    public static String leeNifONie() {
        String id = null; //variabla para recoger número de identificación de persona,
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // métodos auxiliares que arrojan excepciones
            try {
                //Guardamos una cadena sin validar que cuadre con la expresión
                //regular necesaria para un NIF/NIE.
                id = Validar.filtraPatron("[0-9XxYyZz]{1}[0-9]{7}[a-zA-Z]");
                try {
                    Validar.validaNifONie(id);
                    validado = true;
                }catch (Exception ex){                
                    // Si no, mostramos un mensaje al usuario de error.
                    ES.msg("\n¡Advertencia!: El NIF o NIE introducido no es válido."
                            + "\n  Por favor, introdúzcalo de nuevo: ");
                }
            } catch (InputMismatchException e) {
                ES.msg("\n¡Advertencia!: El dato introducido debe ser un NIF o un NIE."
                        + "\n  Por favor, introdúzcalo de nuevo: "); // mensaje de error.
            }
            // A continuación comprobaremos si la cadena cuadra con un NIF/NIE 
            // válido, y mientras no cuadre, seguirá pidiendolo al usuario.
        } while (!validado);
        return id; //Devolvemos la cadena con el NIF/NIE válido.
    }

    /**
     * Método que visualiza un mensaje en pantalla, seguido de ": " y recoge un
     * NIF/NIE válido de teclado.
     *
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se visualizará, más ": ".
     * @return String cadena con el NIF/NIE válido.
     */
    public static String leeNifONie(String mensaje) {
        // Añadimos, por comodidad, el ": " al mensaje indicado.
        ES.msg(mensaje + ": ");
        // Aprovechamos el método ya escrito para devolver el NIF/NIE válido
        return leeNifONie();
    }

    /**
     * Método que devuelve una cadena recogida por teclado, siendo ésta siempre
     * una cadena de 9 dígitos, cuyo primer número será necesariamente, un
     * número del 6 al 9.
     *
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @since version 1.0 13/12/2013
     * @return String Devuelve telefono validado tecleada por el usuario.
     */
    public static String leeTelefono() {
        String telefono = null; //variabla para recoger número de identificación de persona,
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // métodos auxiliares que arrojan excepciones
            try {
                //Guardamos una cadena que cuadre con la expresión
                //regular necesaria para un telefono.
                telefono = Validar.filtraPatron("[6-9]{1}[0-9]{8}");
                validado = true;
            } catch (InputMismatchException e) {
                ES.msg("\n¡Advertencia!: El teléfono debe ser un número de 9 "
                        + "cifras\n  que empiece por 6, 7, 8 ó 9.\n  Por favor,"
                        + " introdúzcalo de nuevo: "); // mensaje de error.
            }
            // Mientras la cadena no sea un teléfono válido, seguirá 
            // pidiendolo al usuario.
        } while (!validado);
        return telefono;

    }

    /**
     * Método que vuelca un mensaje en pantalla, seguido de ": " y devuelve una
     * cadena recogida por teclado, siendo ésta siempre una cadena de 9 dígitos,
     * cuyo primer dígito, será necesariamente, un número del 6 al 9.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrará al usuario, más ": "
     * @return String Devuelve cadena tecleada por el usuario.
     */
    public static String leeTelefono(String mensaje) {
        ES.msg(mensaje + ": "); // Completamos la cadena mensaje.
        // Aprovechamos el método ya escrito leeTelefono().
        return leeTelefono();
    }

    /**
     * Método que visualiza el mensaje por pantalla, añadiendo ": " y recoge
     * altura de persona válida tecleada por el usuario, o sea entre 0.00 y 3.00
     *
     * @since version 1.1 28/12/2013
     * @param mensaje String Cadena que se visualizará , más ": "
     * @return String altura en metros con 2 decimales de precisión
     */
    public static String leeAltura(String mensaje) {
        // Usamos un método que lee un float de teclado, y lo devolvemos como
        // String con 2 decimales de precisión.
        return String.format("%.2f", leeReal(mensaje, 0, 3));
    }

    /**
     * Método que recoge de teclado una cuenta bancaria de forma que los 20
     * dígitos sean correctos según la comprobación de los 2 dígitos de control.
     * 
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @see basicos.Validar#validaDigitoControl(java.lang.String) 
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @return String con el CCC válido.
     */
    public static String leeCuentaBancaria() {
        //Declaración de variables
        String ccc = null;
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // métodos auxiliares que arrojan excepciones
            try {
                //Pedir la cadena respetando el patron CCC
                //Guardamos una cadena sin validar que cuadre con la expresión
                //regular necesaria para una CCC.
                ccc = Validar.filtraPatron("[0-9]{20}");
                //Comprobar que el DC introducido por el usuario es el válido
                if (Validar.validaDigitoControl(ccc)) {
                    validado = true;
                } else {
                    // Si no, mostramos un mensaje al usuario de error.
                    ES.msg("\n¡Advertencia!: El dígito de control introducido "
                            + "no es válido.\n  Por favor, introduzca el CCC de nuevo: ");
                }
            } catch (InputMismatchException e) {
                ES.msg("\n¡Advertencia!: El dato introducido debe ser un CCC "
                        + "bancario.\n  Por favor, introdúzcalo de nuevo, sin "
                        + "espacios ni guiones de separación: "); // mensaje de error.
            }
            // A continuación comprobaremos si la cadena cuadra con un CCC 
            // válido, y mientras no cuadre, seguirá pidiendolo al usuario.
        } while (!validado);
        // Devolver String con CCC validado.
        return ccc;
    }
    
    /**
     * Método que visualiza el mensaje por pantalla, añadiendo ": " y recoge
     * una cuenta bancaria de forma que los 20 dígitos sean corretos acordes
     * con la comprobación de los 2 dígitos de control del ccc.
     * 
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param mensaje String con el mensaje indicativo para introducir el dato.
     * @return String con una cade de 20 dígitos de un CCC válido.
     */
    public static String leeCuentaBancaria(String mensaje){
        ES.msg(mensaje + ": "); // Completamos la cadena mensaje.
        // Aprovechamos el método ya escrito leeCuentaBancaria(), para devolver
        // el ccc validado.
        return leeCuentaBancaria();
    }

    /**
     * Método que vuelca un mensaje dado por pantalla.
     *
     * @since version 1.0 13/12/2013
     * @param cadena String Cadena que se volcará en pantalla.
     */
    public static void msg(String cadena) {
        // Hacemos un System.out.print() con la cadena que nos han pasado.
        System.out.print(cadena);
    }

    /**
     * Método que vuelca un mensaje dado por pantalla, y un retorno de carro
     * después.
     *
     * @since version 1.0 13/12/2013
     * @param cadena String Cadena que se volcará en pantalla.
     */
    public static void msgln(String cadena) {
        // Hacemos un System.out.print() con la cadena que nos han pasaado.
        System.out.println(cadena);
    }
}
