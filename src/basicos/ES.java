/*
 * Tarea PROG07: 3� Modificaci�n de la clase ES
 * Operaciones generales de entrada y salida de datos y helpers asociados.
 */
package basicos;
// Paquete que guardar� las clases basicas para PROG.

// Importamos los paquetes necesarios.
import java.text.ParseException; //gesti�n de excepciones de coincidencias
import java.text.ParsePosition;      //gesti�n de coincidencias
import java.text.SimpleDateFormat;  //gesti�n de formatos de fechas
import java.util.Calendar;          //gesti�n de datos tipo Calendar
import java.util.Date;             // gesti�n de datos tipo Date
import java.util.InputMismatchException; // Control de excepciones de Scanner
import java.util.Scanner; // Clase Scanner, para gesti�n de teclado.

/**
 * Clase para gesti�n de entrada y salida de datos. Toma valores de teclado,
 * controlando excepciones, y lanza mensajes a pantalla con y sin retorno de
 * carro al final.
 *
 * @since version 1.0 13/12/2013 (Tarea PROG04)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.3 12/01/2014 (Tarea PROG07)
 */
public class ES {
    // Todos sus m�todos son public static. Podr�n ser llamados desde cualquier
    // clase, sin precisar ser instanciados.

    /**
     * M�todo que recoge de teclado un n�mero entero tipo int.
     *
     * @since version 1.0 13/12/2013
     * @return int Entero recogido de teclado.
     */
    public static int leeEntero() {
        // Aprovechamos con un casting, el m�todo ya elaborado para long.
        return (int) leeEnteroLargo();
    }

    /**
     * M�todo que vuelca un mensaje en pantalla, a�adi�ndole ": ", y devuelve un
     * n�mero entero int que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @return int Entero recogido de teclado.
     */
    public static int leeEntero(String mensaje) {
        // Aprovechamos con un casting, el m�todo ya elaborado para long.
        return (int) leeEnteroLargo(mensaje);
    }

    /**
     * M�todo que vuelca un mensaje en pantalla, a�adi�ndole ": ", y devuelve un
     * n�mero entero int que recoge de teclado, siendo �ste siempre mayor o
     * igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, mas ": "
     * @param minimo int Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @return int Entero recogido de teclado.
     */
    public static int leeEntero(String mensaje, int minimo) {
        // Aprovechamos con un casting, el m�todo ya elaborado para long.
        return (int) leeEnteroLargo(mensaje, minimo);
    }

    /**
     * M�todo que vuelca un mensaje en pantalla, a�adi�ndole ": " y devuelve un
     * n�mero entero int que recoge de teclado, siendo �ste siempre mayor o
     * igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, mas ": "
     * @param minimo int Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @param maximo int Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @return int Entero recogido por teclado.
     */
    public static int leeEntero(String mensaje, int minimo, int maximo) {
        // Aprovechamos con un casting, el m�todo ya elaborado para long.
        return (int) leeEnteroLargo(mensaje, minimo, maximo);
    }

    /**
     * M�todo que recoge de teclado un n�mero entero tipo long.
     *
     * @since version 1.0 13/12/2013
     * @return long N�mero entero recogido de teclado.
     */
    public static long leeEnteroLargo() {
        // Instanciamos objeto clase Scanner, para entradas de teclado.
        Scanner teclado = new Scanner(System.in);
        long enteroLargo = 0;  // Variable para guardar el dato de teclado.
        boolean datoIncorrecto = true;  // Booleano para marcar salida
        do {
            try {
                // Intentamos recoger de teclado un n�mero entero largo
                enteroLargo = teclado.nextLong();
                // Si la entrada es del tipo correcto, cambiamos el booleano.
                datoIncorrecto = false;
            } catch (InputMismatchException e) {
                // Controlamos la excepci�n general de dato incorrecto.
                // y mandamos a pantalla el mensaje de error.
                ES.msg("\n�Advertencia!: El programa espera un n�mero entero.\n"
                        + " Introd�zcalo de nuevo, por favor: ");
                // Limpiamos el buffer de teclado, para evitar problemas.
                teclado.next();
            } // Se repetir� el bucle hasta que el dato sea el esperado.
        } while (datoIncorrecto);
        return enteroLargo; // Devolvemos el numero tecleado por el usuario.
    }

    /**
     * M�todo que vuelca un mensaje en pantalla, a�adi�ndole ": " y devuelve un
     * n�mero entero long que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrara por pantalla m�s ": "
     * @return long Entero recogido por teclado.
     */
    public static long leeEnteroLargo(String mensaje) {
        ES.msg(mensaje + ": "); // Completamos el par�metro mensaje 
        // Aprovechamos el m�todo ya elaborado para long sin mensaje.
        return leeEnteroLargo();
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adi�ndole ": ", y devuelve un
     * n�mero entero long que recoge de teclado, siendo �ste siempre mayor o
     * igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, mas ": "
     * @param minimo long Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @return long Entero recogido de teclado.
     */
    public static long leeEnteroLargo(String mensaje, long minimo) {
        long enteroLargo = 0; // Variable que guardar� el dato introducido.
        boolean datoIncorrecto = true; // Booleano para marcar entrada correcta.
        do {
            // Aprocvechamos el m�todo que pide un entero largo con mensaje.
            enteroLargo = leeEnteroLargo(mensaje);
            // Condici�n que controla si enteroLargo est� en el rango deseado.
            if (enteroLargo < minimo) {
                ES.msgln("\n�Advertencia!: El dato debe ser mayor o igual que "
                        + minimo + "."); // Mensaje aclaratorio al usuario.
            } else {
                datoIncorrecto = false; // Si estamos en rango, cambia booleano.
            } // Se repetir� el bucle hasta que el usuario meta el dato correcto.
        } while (datoIncorrecto);
        return enteroLargo; // Devolvemos el dato recogido por teclado.
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adi�ndole ": " y devuelve un
     * n�mero entero long que recoge de teclado, siendo �ste siempre mayor o
     * igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, mas ": "
     * @param minimo long Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @param maximo long Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @return long Entero recogido por teclado.
     */
    public static long leeEnteroLargo(String mensaje, long minimo, long maximo) {
        long enteroLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // booleano para marcar la salida.
        do {
            // Aprovechamos el m�todo que pide en entero largo con mensaje.
            enteroLargo = leeEnteroLargo(mensaje, minimo);
            // �rbol de condiciones para ver si enteroLargo est� en rango o no.
            if (enteroLargo > maximo) {
                ES.msgln("\n�Advertencia!: El dato debe ser menor o igual que"
                        + " " + maximo + "."); //Mensaje informativo.
            } else {
                datoIncorrecto = false; // Cambia booleano si es correcto.
            } // Se repetir� el bucle hasta que el usuario de un entero en rango.
        } while (datoIncorrecto);
        return enteroLargo; // Devolvemos en n�mero recogido por teclado.
    }

    /**
     * M�todo que recoge de teclado un n�mero real tipo float.
     *
     * @since version 1.0 13/12/2013
     * @return float Real recogido de teclado.
     */
    public static float leeReal() {
        // Aprovechamos con un casting el m�todo ya escrito para double.
        return (float) leeRealLargo();
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adi�ndole ": ", y devuelve un
     * n�mero real float que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @return float Real recogido de teclado.
     */
    public static float leeReal(String mensaje) {
        // Aprovechamos con un casting el m�todo ya elaborado para double.
        return (float) leeRealLargo(mensaje);
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adi�ndole ": ", y devuelve un
     * n�mero real float que recoge de teclado, siendo �ste siempre mayor o
     * igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, mas ": "
     * @param minimo float Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @return float Real recogido de teclado.
     */
    public static float leeReal(String mensaje, float minimo) {
        // Aprovechamos, con un casting, el m�todo ya elaborado para double.
        return (float) leeRealLargo(mensaje, minimo);
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adiendole ": " y devuelve un
     * n�mero real tipo float que recoge de teclado, siendo �ste siempre mayor o
     * igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @param minimo float Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @param maximo float Pedir� valores al usuario, hasta que teclee un n�mero
     * mayor o igual que �ste.
     * @return float Real recogido de teclado.
     */
    public static float leeReal(String mensaje, float minimo, float maximo) {
        // Aprovechamos, con un casting, el m�todo ya elaborado para double.
        return (float) leeRealLargo(mensaje, minimo, maximo);
    }

    /**
     * M�todo que recoge de teclado un n�mero real tipo double.
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
                // Controlamos la excepci�n de dato incorrecto.
                ES.msg("\n�Advertencia!: El programa espera un n�mero. "
                        + "Si introduce decimales,\n  use la coma para separarlos"
                        + " de la parte entera, no el punto.\n  Por favor, "
                        + "introd�zcalo de nuevo: "); // Mensaje de error.
                teclado.next(); // Limpiamos buffer de teclado.
            } //Se repetir� el bucle hasta que el usuario meta el dato esperado.
        } while (datoIncorrecto);
        return realLargo; // Devolvemos el dato recogido por teclado.
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adi�ndole ": " y devuelve un
     * n�mero real tipo double que recoge de teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo(String mensaje) {
        ES.msg(mensaje + ": "); // A�adimos espacio a la cadena mensaje.
        // Aprovechamos el m�todo ya elaborado para double sin mensaje.
        return leeRealLargo();
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adi�ndole ": " y devuelve un
     * n�mero real tipo double que recoge de teclado, siendo �ste siempre mayor
     * o igual a un minimo dado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @param minimo double Pedir� valores al usuario, hasta que teclee un
     * n�mero mayor o igual que �ste.
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo(String mensaje, double minimo) {
        double realLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // bolleano para controlar dato correcto.
        do {
            // Aprovechamos el m�todo ya escrito para double con mensaje
            realLargo = leeRealLargo(mensaje);
            if (realLargo < minimo) { // Miramos si el n�mero est� en rango.
                ES.msgln("\n�Advertencia!: El dato debe ser mayor o igual que "
                        + minimo + ".");
            } else { // Si lo est� cambiamos el booleano para salir del bucle.
                datoIncorrecto = false;
            } // Pedir� de nuevo el n�mero hasta que est� en el rango esperado.
        } while (datoIncorrecto);
        return realLargo; //Devolvemos el dato recogido por teclado.
    }

    /**
     * M�todo que vuelca un mensaje en pantalla a�adiendole ": " y devuelve un
     * n�mero real tipo double que recoge de teclado, siendo �ste siempre mayor
     * o igual a un minimo, y menor o igual a un maximo dados.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @param minimo double Pedir� valores al usuario, hasta que teclee un
     * n�mero mayor o igual que �ste.
     * @param maximo double Pedir� valores al usuario, hasta que teclee un
     * n�mero mayor o igual que �ste.
     * @return double Real recogido de teclado.
     */
    public static double leeRealLargo(String mensaje, double minimo,
            double maximo) {
        double realLargo = 0; // Declaramos e inicializamos variable necesaria.
        boolean datoIncorrecto = true; // booleano para controlar dato correcto.
        do {
            // Aprovechamos m�todo ya escrito para double con mensaje.
            realLargo = leeRealLargo(mensaje, minimo);
            // �rbol de decisiones para comprobar si el dato est� en rango.
            if (realLargo > maximo) {
                ES.msgln("\n�Advertencia!: El dato debe ser menor o igual que"
                        + " " + maximo + "."); // Mensaje de error.
            } else {
                datoIncorrecto = false; // Si est� en rango, cambia el booleano.
            } // Seguiremos pidiendo el dato haata que sea correcto.
        } while (datoIncorrecto);
        return realLargo; // Devolvemos el dato recogido de teclado.
    }

    /**
     * M�todo que devuelve una cadena recogida por teclado, que ser� siempre un
     * car�cter "S", "s", "N" o "n".
     *
     * @since version 1.0 13/12/2013
     * @return String Devuelve el dato recogido por teclado.
     */
    public static String leeRespuesta() {
        // Instanciamos objeto clase Scanner, para la gesti�n de teclado.
        Scanner teclado = new Scanner(System.in);
        String respuesta; // Declaramos variable necesaria.
        boolean datoIncorrecto = true; // booleano para contrelar dato correcto.
        do {
            // Recogemos un dato de teclado, en min�sculas.
            respuesta = teclado.next().toLowerCase();
            // Si es una s o una n, cambiamos el booleano.
            if ("s".equals(respuesta) || "n".equals(respuesta)) {
                datoIncorrecto = false;
            } else { // Mensaje de error, si el dato no es S, s, N o n.
                ES.msgln("\n�Advertencia!: El dato teleado debe ser una 's' "
                        + "o una 'n' (may�scula o min�scula).\n  Por favor, "
                        + "introd�zcalo de nuevo: ");
            }
            // Pedir� el dato hasta que sea el esperado (S, s, N o n)
        } while (datoIncorrecto);
        return respuesta; // Devolvemos el dato recogido por teclado.
    }

    /**
     * M�todo que vuelca un mensaje a pantalla, seguido de "(s/n): ", y devuelve
     * una cadena recogida por teclado, que ser� siempre un car�cter "S", "s",
     * "N" o "n".
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @return String Devuelve respuesta dada por el usuario.
     */
    public static String leeRespuesta(String mensaje) {
        ES.msg(mensaje + "(s/n): "); // Completamos la cadena mensaje.
        // Aprovechamos el m�todo ya escrito leeRespuesta().
        return leeRespuesta();
    }

    /**
     * M�todo que devuelve una cadena recogida por teclado.
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
                // Se intenta controlar posible excepci�n, (no saltar� nunca).
                ES.msgln("\n�Advertencia!: El dato teleado debe ser una "
                        + "cadena de caracteres y ser� almacenado respetando "
                        + "espacios, may�sculas y min�sculas.");
                teclado.next(); // limpiamos buffer.
            } // Salvo casos que no puedo imaginar, se ejecutar� una sola vez.
        } while (datoIncorrecto);
        return cadena; // Devolvemos dato recogido por teclado.
    }

    /**
     * M�todo que vuelca un mensaje a pantalla, seguido de ": " y devuelve una
     * cadena recogida por teclado.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @return String Devuelve la cadena tecleada por el usuario.
     */
    public static String leeCadena(String mensaje) {
        ES.msg(mensaje + ": "); // Completamos cadena mensaje.
        // Aprovechamos m�todo ya escrito para cadena sin mensaje.
        return leeCadena();
    }

    /**
     * M�todo que recoge fecha v�lida de teclado formato dd-mm-aaaa
     *
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @see basicos.Validar#validaFecha(java.lang.String)
     * @since version 1.1 28/12/2013
     * @return String validado en formato dd-mm-aaaa
     */
    public static String leeFecha() {
        String fecha = null; //variable que guardar� la fecha recogida.
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // m�todos auxiliares que arrojan excepciones
            try {
            //Guardamos una cadena sin validar que cuadre con la expresi�n
                //regular previa a dd-mm-aaaa (2d�gitos-2digitos-4digitos)
                fecha = Validar.filtraPatron("[0-9]{2}-[0-9]{2}-[0-9]{4}");
                validado = Validar.validaFecha(fecha);
            } catch (ParseException e) {
                ES.msg("\n�Advertencia!: La fecha tecleada no es v�lida\n  Por "
                        + "favor, introd�zcala de nuevo: ");
            } catch (InputMismatchException e) {
                ES.msg("\n�Advertencia!: La fecha debe estar en formato dd-mm-aaaa\n"
                        + "  Por favor, introd�zcala de nuevo: ");
            }
            // A continuaci�n comprobaremos si la cadena cuadra con una fecha 
            // v�lida, y mientras no cuadre, seguir� pidiendola al usuario.
        } while (!validado);
        return fecha; //Devolvemos la cadena con fecha validada.
    }

    /**
     * M�todo que visualiza un mensaje en pantalla, seguido de ": " y recoge
     * fecha v�lida de teclado en formato dd-mm-aaaa
     *
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se visualizar�, m�s ": "
     * @return String validado en formato dd-mm-aaaa
     */
    public static String leeFecha(String mensaje) {
        // A�adimos, por comodidad, el ": " al mensaje indicado.
        ES.msg(mensaje + ": ");
        // Aprovechamos el m�todo ya escrito y devlvemos la fecha v�lida.
        return leeFecha();
    }
    
    /**
     * M�todo que compara 2 fechas. Devuelve -1 si la primera es anterior a la
     * segunda, 0 si son iguales, y +1 si la segunda es anterior a la primera
     * 
     * PREMISA: ambas fechas deben estar en formato dd-mm-aaaa y ser v�lidas.
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
     * M�todo que visualiza un mensje en pantalla, seguido de ": " y recoge una
     * fecha valida de teclado, y mayor o igual a la facilitada en minimo.
     *
     * PREMISA: La fecha facilitada en m�nimo debe estar en formato dd-mm-aaaa
     *
     * @see basicos.ES#compararFecha(java.lang.String, java.lang.String) 
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se mostrar� al usuario.
     * @param minimo String con fecha en formato dd-mm-aaaa .
     * @return String con fecha v�lida mayor o igual a la facilitada.
     */
    public static String leeFecha(String mensaje, String minimo) {
        String fechaTeclado; // Variable que alojar� la fecha tecleada
        boolean fechaIncorrecta = true; //booleano para marcar objetivo
        do {
            // Pedimos al usuario la fecha con el m�todo ya escrito
            fechaTeclado = leeFecha(mensaje);
            // comparamos la fecha del usuario con el minimo,
            if (compararFecha(fechaTeclado, minimo) < 0) {
                // si est� fuera de rango, sacamos mensaje de error
                ES.msgln("\n�Advertencia!: La fecha debe ser mayor o igual que"
                        + " " + minimo + "."); // Mensaje de error.
            } else {
                // Si no, cambiamos el booleano.
                fechaIncorrecta = false;
            }
        } while (fechaIncorrecta); //pediremos fechas mientras no est�n en rango
        return fechaTeclado; //devolvemos el String formateada.
    }

    /**
     * M�todo que visualiza un mensje en pantalla, seguido de ": " y recoge una
     * fecha valida de teclado, mayor o igual a la facilitada en minimo y menor
     * o igual a la facilitada en maximo.
     *
     * PREMISA: Las fechas facilitadas en m�nimo/m�ximo, debe estar en formato
     * dd-mm-aaaa
     *
     * @see basicos.ES#compararFecha(java.lang.String, java.lang.String) 
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se mostrar� al usuario.
     * @param minimo String con fecha en formato dd-mm-aaaa.
     * @param maximo String con fecha en formato dd-mm-aaaa.
     * @return String con fecha v�lida mayor o igual a la facilitada como 2�
     * par�metro, y menor o igual a la facilitada como tercer par�metro.
     */
    public static String leeFecha(String mensaje, String minimo, String maximo) {
        String fechaTeclado;  // Variable que alojar� la fecha tecleada
        boolean fechaIncorrecta = true; //booleano para marcar objetivo
        do {
            // Pedimos al usuario la fecha con el m�todo ya escrito
            fechaTeclado = leeFecha(mensaje, minimo);
            if (compararFecha(fechaTeclado, maximo) > 0) {
                // si no, mensaje de error
                ES.msgln("\n�Advertencia!: La fecha debe ser menor o igual que"
                        + " " + maximo + ".");
            } else {
                // Si est� en rango, cambiamos el booleano
                fechaIncorrecta = false;
            }
        } while (fechaIncorrecta); // Pediremos fechas mientras no est� en rango.
        return fechaTeclado; //Devolvemos el String formateado
    }

    /**
     * M�todo que pide al usuario la fecha de nacimiento de una persona, previo
     * mensaje de petici�n, y la valida si la fecha se ajusta a un arco temporal
     * desde 120 a�os antes de ahora, hasta el momento presente. Seguir�
     * pidiendo fechas, hasta que la fecha facilitada est� en rango.
     *
     * @since version 1.1 28/12/2013 (PROG05)
     * @param mensaje String con el mensaje al usuario, m�s ": "
     * @return String con fecha dentro del espacio de tiempo desde hace 120
     * a�os, hasta el momento presente.
     */
    public static String leeFechaNacimiento(String mensaje) {
        // Obtenemos el momento actual con una variable tipo Calendar.
        Calendar ahora = Calendar.getInstance();
        // Separamos d�a, mes y a�o, en diferentes String
        String dia = Integer.toString(ahora.get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(ahora.get(Calendar.MONTH) + 1);
        String agno = Integer.toString(ahora.get(Calendar.YEAR));
        // Montamos dos String con el formato dd-mm-aaaa, para un rango
        // minimo de hace 120 a�os, y maximo de hoy.
        String minimo = dia + "-" + mes + "-" + (Integer.parseInt(agno) - 120);
        String maximo = dia + "-" + mes + "-" + agno;
        //retornaremos una fecha v�lida recogida de teclado, perteneciente
        //a ese rango de fechas.
        return leeFecha(mensaje, minimo, maximo);
    }

    /**
     * M�todo que recoge NIF/NIE v�lido de teclado.
     *
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.1 28/12/2013
     * @return String NIF/NIE validado
     */
    public static String leeNifONie() {
        String id = null; //variabla para recoger n�mero de identificaci�n de persona,
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // m�todos auxiliares que arrojan excepciones
            try {
                //Guardamos una cadena sin validar que cuadre con la expresi�n
                //regular necesaria para un NIF/NIE.
                id = Validar.filtraPatron("[0-9XxYyZz]{1}[0-9]{7}[a-zA-Z]");
                try {
                    Validar.validaNifONie(id);
                    validado = true;
                }catch (Exception ex){                
                    // Si no, mostramos un mensaje al usuario de error.
                    ES.msg("\n�Advertencia!: El NIF o NIE introducido no es v�lido."
                            + "\n  Por favor, introd�zcalo de nuevo: ");
                }
            } catch (InputMismatchException e) {
                ES.msg("\n�Advertencia!: El dato introducido debe ser un NIF o un NIE."
                        + "\n  Por favor, introd�zcalo de nuevo: "); // mensaje de error.
            }
            // A continuaci�n comprobaremos si la cadena cuadra con un NIF/NIE 
            // v�lido, y mientras no cuadre, seguir� pidiendolo al usuario.
        } while (!validado);
        return id; //Devolvemos la cadena con el NIF/NIE v�lido.
    }

    /**
     * M�todo que visualiza un mensaje en pantalla, seguido de ": " y recoge un
     * NIF/NIE v�lido de teclado.
     *
     * @since version 1.1 28/12/2013
     * @param mensaje String mensaje que se visualizar�, m�s ": ".
     * @return String cadena con el NIF/NIE v�lido.
     */
    public static String leeNifONie(String mensaje) {
        // A�adimos, por comodidad, el ": " al mensaje indicado.
        ES.msg(mensaje + ": ");
        // Aprovechamos el m�todo ya escrito para devolver el NIF/NIE v�lido
        return leeNifONie();
    }

    /**
     * M�todo que devuelve una cadena recogida por teclado, siendo �sta siempre
     * una cadena de 9 d�gitos, cuyo primer n�mero ser� necesariamente, un
     * n�mero del 6 al 9.
     *
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @since version 1.0 13/12/2013
     * @return String Devuelve telefono validado tecleada por el usuario.
     */
    public static String leeTelefono() {
        String telefono = null; //variabla para recoger n�mero de identificaci�n de persona,
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // m�todos auxiliares que arrojan excepciones
            try {
                //Guardamos una cadena que cuadre con la expresi�n
                //regular necesaria para un telefono.
                telefono = Validar.filtraPatron("[6-9]{1}[0-9]{8}");
                validado = true;
            } catch (InputMismatchException e) {
                ES.msg("\n�Advertencia!: El tel�fono debe ser un n�mero de 9 "
                        + "cifras\n  que empiece por 6, 7, 8 � 9.\n  Por favor,"
                        + " introd�zcalo de nuevo: "); // mensaje de error.
            }
            // Mientras la cadena no sea un tel�fono v�lido, seguir� 
            // pidiendolo al usuario.
        } while (!validado);
        return telefono;

    }

    /**
     * M�todo que vuelca un mensaje en pantalla, seguido de ": " y devuelve una
     * cadena recogida por teclado, siendo �sta siempre una cadena de 9 d�gitos,
     * cuyo primer d�gito, ser� necesariamente, un n�mero del 6 al 9.
     *
     * @since version 1.0 13/12/2013
     * @param mensaje String Mensaje que se mostrar� al usuario, m�s ": "
     * @return String Devuelve cadena tecleada por el usuario.
     */
    public static String leeTelefono(String mensaje) {
        ES.msg(mensaje + ": "); // Completamos la cadena mensaje.
        // Aprovechamos el m�todo ya escrito leeTelefono().
        return leeTelefono();
    }

    /**
     * M�todo que visualiza el mensaje por pantalla, a�adiendo ": " y recoge
     * altura de persona v�lida tecleada por el usuario, o sea entre 0.00 y 3.00
     *
     * @since version 1.1 28/12/2013
     * @param mensaje String Cadena que se visualizar� , m�s ": "
     * @return String altura en metros con 2 decimales de precisi�n
     */
    public static String leeAltura(String mensaje) {
        // Usamos un m�todo que lee un float de teclado, y lo devolvemos como
        // String con 2 decimales de precisi�n.
        return String.format("%.2f", leeReal(mensaje, 0, 3));
    }

    /**
     * M�todo que recoge de teclado una cuenta bancaria de forma que los 20
     * d�gitos sean correctos seg�n la comprobaci�n de los 2 d�gitos de control.
     * 
     * @see basicos.Validar#filtraPatron(java.lang.String) 
     * @see basicos.Validar#validaDigitoControl(java.lang.String) 
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @return String con el CCC v�lido.
     */
    public static String leeCuentaBancaria() {
        //Declaraci�n de variables
        String ccc = null;
        boolean validado = false;
        do {
            //Usamos la estructura try/catch para gestionar la llamada a
            // m�todos auxiliares que arrojan excepciones
            try {
                //Pedir la cadena respetando el patron CCC
                //Guardamos una cadena sin validar que cuadre con la expresi�n
                //regular necesaria para una CCC.
                ccc = Validar.filtraPatron("[0-9]{20}");
                //Comprobar que el DC introducido por el usuario es el v�lido
                if (Validar.validaDigitoControl(ccc)) {
                    validado = true;
                } else {
                    // Si no, mostramos un mensaje al usuario de error.
                    ES.msg("\n�Advertencia!: El d�gito de control introducido "
                            + "no es v�lido.\n  Por favor, introduzca el CCC de nuevo: ");
                }
            } catch (InputMismatchException e) {
                ES.msg("\n�Advertencia!: El dato introducido debe ser un CCC "
                        + "bancario.\n  Por favor, introd�zcalo de nuevo, sin "
                        + "espacios ni guiones de separaci�n: "); // mensaje de error.
            }
            // A continuaci�n comprobaremos si la cadena cuadra con un CCC 
            // v�lido, y mientras no cuadre, seguir� pidiendolo al usuario.
        } while (!validado);
        // Devolver String con CCC validado.
        return ccc;
    }
    
    /**
     * M�todo que visualiza el mensaje por pantalla, a�adiendo ": " y recoge
     * una cuenta bancaria de forma que los 20 d�gitos sean corretos acordes
     * con la comprobaci�n de los 2 d�gitos de control del ccc.
     * 
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param mensaje String con el mensaje indicativo para introducir el dato.
     * @return String con una cade de 20 d�gitos de un CCC v�lido.
     */
    public static String leeCuentaBancaria(String mensaje){
        ES.msg(mensaje + ": "); // Completamos la cadena mensaje.
        // Aprovechamos el m�todo ya escrito leeCuentaBancaria(), para devolver
        // el ccc validado.
        return leeCuentaBancaria();
    }

    /**
     * M�todo que vuelca un mensaje dado por pantalla.
     *
     * @since version 1.0 13/12/2013
     * @param cadena String Cadena que se volcar� en pantalla.
     */
    public static void msg(String cadena) {
        // Hacemos un System.out.print() con la cadena que nos han pasado.
        System.out.print(cadena);
    }

    /**
     * M�todo que vuelca un mensaje dado por pantalla, y un retorno de carro
     * despu�s.
     *
     * @since version 1.0 13/12/2013
     * @param cadena String Cadena que se volcar� en pantalla.
     */
    public static void msgln(String cadena) {
        // Hacemos un System.out.print() con la cadena que nos han pasaado.
        System.out.println(cadena);
    }
}
