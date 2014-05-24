/*
 * Tarea PROG10: Ampliaci�n de la clase Validar
 * Validaci�n de datos mediante patrones regulares y formatos predefinidos.
 */
package basicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException; // Control de excepciones de Scanner
import java.util.Scanner; // Clase Scanner, para gesti�n de teclado.
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern; // Clase Pattern para patrones de entrada de datos.

/**
 * Clase para validaci�n de datos mediante expresiones regulares, patrones, y
 * formatos predefinidos.
 *
 * @since version 1.1 28/12/2013 (Tarea PROG05)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 2.0 13/04/2014 (Tarea PROG10)
 */
public class Validar {

    /**
     * M�todo que recoge una cadena de teclado y la compara con la expresion
     * regular indicada, en caso de que no cuadre, devuelve la excepcion. 
     *
     * @since versi�n 1.1 28/12/2013 (Tarea PROG05)
     * @param expresionRegular String que indice la expresi�n regular utilizada
     * @return String cadena que se devualve una vez que cuadra con la expresi�n
     * regular facilitada.
     * @throws java.util.InputMismatchException
     */
    public static String filtraPatron(String expresionRegular) throws
            InputMismatchException {
        // Instanciamos objeto clase Scanner, para lecturas de teclado.
        Scanner teclado = new Scanner(System.in);
        String cadena = ""; // Declaramos e inicializamos variable necesaria.
        // Instanciamos objeto clase Pattern, definiendo el patron necesario
        // El primer n�mero ser� de 6 a 9, y los 8 siguientes, de 0 a 9.
        Pattern patron = Pattern.compile(expresionRegular);
        boolean datoCorrecto; // booleano para control de dato correcto.
        do {
            // Probamos a recoger el dato de teclado, ajust�ndolo a patron.
            cadena = teclado.next(patron);
            // En caso de que el dato se ajuste, cambiar� el booleano.
            datoCorrecto = true;
            // El bucle se repetira hasta que el dato se ajuste al patron.
        } while (!datoCorrecto);
        return cadena; // Se devuelve el dato recogido por teclado.
    }

    /**
     * M�todo que cmprueba que una cadena cuandra con la expresion
     * regular indicada, en caso de que no cuadre, devuelve la excepcion.
     * Podemos a�adir true o false para quitar los espacios de la cadena facilita
     * o comparar con el patr�n tal cual. En el caso de que sea true, la cadena
     * devuelta ser� la filtrada una vez suprimidos los espacios (incluyendo los
     * intermedios).
     *
     * @since versi�n 1.1 28/12/2013 (Tarea PROG05)
     * @param expresionRegular String que indice la expresi�n regular utilizada
     * @param cadena String facilitado para su comparaci�n
     * @param quitarEspacios true o false, indica si se deben fltrar los espacios,
     * tanto los inciales, intermedios, como finales.
     * @return String cadena que se devualve una vez que cuadra con la expresi�n
     * regular facilitada. Si se especifica true para los espacios, la cadena la
     * devolver�a una vez filtrada, o sea, sin espacios.
     * @throws java.util.InputMismatchException
     * @throws java.lang.Exception
     */
    public static String filtraPatron(String expresionRegular, String cadena, 
            boolean quitarEspacios) throws InputMismatchException, Exception {
        Pattern patron = Pattern.compile(expresionRegular);
        if (quitarEspacios) {
            StringTokenizer tokenizer =  new StringTokenizer(cadena);
            String sinEspacios = "";
            while(tokenizer.hasMoreTokens()){
                sinEspacios = sinEspacios + tokenizer.nextToken();
            }
            cadena = sinEspacios;
        }
        Matcher cuadra = patron.matcher(cadena);
        if (!cuadra.matches()) {
            throw new Exception(cadena + " no v�lido");
        }
        return cadena; // Se devuelve el dato recogido por teclado.
    }

    /**
     * M�todo que determina si una cadena pasada como par�metro, es un email
     * correcto. En caso de que el mail no sea una cadena acorde con el patron
     * definido para �stos, saltar�a la excepci�n. El patr�n utilizado es:
     * "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")"
     * 
     * @see basicos.Validar#filtraPatron(java.lang.String, java.lang.String, boolean) 
     * @since version 2.0 13/04/2014 (PROG10)
     * @param email String con el mail a validar
     * @return true si el email se ha validaod, false, en caso contrario.
     * @throws Exception en caso de que no coincida con el patro definido.
     */
    public static boolean esEmailCorrecto(String email) throws Exception{
        boolean validado;
        //usamos nuestro m�todo validar, respetando los espacios en blanco.
        Validar.filtraPatron("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]"
                + "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email, false);
        validado = true; //si no ha saltado la excepcion, es que coincide con el patron.
        return validado; 
    }
    /**
     * M�todo para validar NIF/NIE, asegur�ndose de que el id facilitado cumple
     * la normativa, o sea, que su letra final es la que le corresponde seg�n la
     * operaci�n % 23 reglamentaria.
     *
     * @throws java.lang.Exception
     * @see basicos.Validar#obtenerLetraNifONie(java.lang.String)
     * @since version 1.1 28/12/2013 (Tarea PROG05)
     * @param id String cadena propuesta para su validaci�n.
     * @return boolean true si la cadena es un NIF/NIE v�lido, false, si no lo
     * es.
     */
    public static boolean validaNifONie(String id) throws Exception {
        boolean validado = false;
        Validar.filtraPatron("[0-9XxYyZz]{1}[0-9]{7}[a-zA-Z]", id, false);
        //Comprobamos si la letra del id coincide con la que deber�a ser v�lida
        if ((id.toUpperCase().charAt(id.length() - 1)) == obtenerLetraNifONie(id)) {
            // Si es as�, lo damos por v�lido.
            validado = true;
        }
        return validado; //Devolvemos el booleano que dice si es v�lido, o no.
    }

    /**
     * M�todo que calcula la letra correspondiente a un n�mero identificador
     * proveniente de un Nif o Nie, listo para la operaci�n de % 23.
     *
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param id String con el identificador para el c�lculo
     * @return char letra correspondiente a ese identificador
     */
    public static char obtenerLetraNifONie(String id) {
        int identificacion;
        //Alojar letras NIF/NIE ordenadas para localizarlas por su �ndice
        String tablaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
        //Seg�n tipo de NIF/NIE, se adjudica un valor entero para operar con �l
        switch (id.toUpperCase().charAt(0)) {
            case 'X':
                identificacion = Integer.valueOf(id.substring(1, 8));
                break;
            case 'Y':
                identificacion = Integer.valueOf('1' + id.substring(1, 8));
                break;
            case 'Z':
                identificacion = Integer.valueOf('2' + id.substring(1, 8));
                break;
            default:
                identificacion = Integer.valueOf(id.substring(0, 8));
        }
        //Se hace la operaci�n % 23 sobre el entero consguido y devolvemos
        //la letra que nos sale correspondiente a su lugar en la tabla
        return tablaLetras.charAt(identificacion % 23);
    }

    /**
     * M�todo que valida un c�digo cuenta cliente, mediante el c�lculo del
     * d�gito de control.
     *
     * @see basicos.Validar#obtenerDigitosControl(java.lang.String,
     * java.lang.String, java.lang.String)
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param ccc String con la ccc que se quiere validar
     * @return booleano con true si es v�lida, false, si no lo es.
     */
    public static boolean validaDigitoControl(String ccc) {
        //Declarar variables necesarias
        //Usamos un array para el resultado de la validaci�n doble
        boolean validado[] = {false, false}; 
        // Descomponemos el ccc en variables separadas, para simplificar
        String entidad = ccc.substring(0, 4);
        String sucursal = ccc.substring(4, 8);
        String cuenta = ccc.substring(10);
        // Delegamos el c�lculo de los d�gitoscorrectos en otros m�todo herlper
        int digitosControl[] = obtenerDigitosControl(entidad, sucursal, cuenta);
        // Comprobamos con un bucle, si ambos d�gitos son correctos.
        for (int i = 0; i < digitosControl.length; i++) {
            if (digitosControl[i]
                    == Integer.parseInt(ccc.substring(8 + i, 9 + i))) {
                //Introducimos el resultado positivo en su sitio del array de resultados
                validado[i] = true;
            }
        }
        // Devolvemos un resultado positivo, s�lo en caso de que ambos d�gitos
        // coincidan con los correctos.
        return validado[0] && validado[1];
    }

    /**
     * M�todo que obtiene los d�gitos de control para una combinaci�n de
     * entidad, sucursal y cuenta facilitadas.
     *
     * @see basicos.Validar#validaDigitoControl(java.lang.String)
     * @link http://es.wikipedia.org/wiki/C%C3%B3digo_cuenta_cliente
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param entidad String con los 4 d�gitos del c�digo de entidad
     * @param sucursal String con los 4 d�gitos del c�digo de la sucursal
     * @param cuenta String con los 10 d�gitos del c�digo de cuenta
     * @return array de 2 elementos con los d�gitos de control calculados
     */
    public static int[] obtenerDigitosControl(String entidad, String sucursal,
            String cuenta) {
        //Factores multiplicadores para la operaci�n de validaci�n.
        int factores[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        //array para alojar los DC
        int[] digitosControl = new int[2];
        //Separamos en 2 cadenas para posteriores c�lculos necesarios.
        // Se sigue el procedimiento para el c�lculo indicado en 
        // http://es.wikipedia.org/wiki/C%C3%B3digo_cuenta_cliente
        String cadenaValidacion[] = {"00" + entidad + sucursal, cuenta};
        // Declaramos las variables intermedias que necesitaremos
        int resultado, resto;
        // Mediante un bucle acumulamos el pre-resultado de cada d�gito
        for (int i = 0; i < digitosControl.length; i++) {
            resultado = 0; //INicializamos el acumulador
            for (int j = 0; j < factores.length; j++) {
                // Vamos multiplicando cada operando y acumulando en la variable
                resultado += factores[j] * Integer.parseInt(cadenaValidacion[i].
                        substring(j, j + 1)); 
            }
            // Seguimos la cadena de operaciones reglamentaria, con otra variable
            resto = resultado % 11;
            // Guardamos cada d�gito en su �ndice correspondiente, 
            digitosControl[i] = 11 - resto;
            switch (digitosControl[i]) { // Retocamos en caso de ser 10 u 11.
                case 10:
                    digitosControl[i] = 1;
                    break;
                case 11:
                    digitosControl[i] = 0;
                    break;
            }
        }
        // Devolvemos un array con ambos d�gitos 
        return digitosControl;
    }

    /**
     * M�todo que valida un tel�fono v�lido si cuadra con la expresi�n regular
     * que s�lo admite cadenas de 9 d�gitos, cuyo primer d�gito, ser�
     * necesariamente, un n�mero del 6 al 9. 
     *
     * @since version 1.1 28/12/2013 (Tarea PROG05)
     * @param telefono String con el n�mero a validar.
     * @return boolean true si ha sido validado, false si no.
     */
    public static boolean validaTelefono(String telefono) {
        boolean validado = false;
        // Instanciamos objeto clase Pattern, definiendo el patron necesario
        // El primer n�mero ser� de 6 a 9, y los 8 siguientes, de 0 a 9.
        Pattern patron = Pattern.compile("[6-9]{1}[0-9]{8}");
        // Probamos a ajustar el dato al patron.
        Matcher cuadra = patron.matcher(telefono);
        if (cuadra.matches()) {
            validado = true; //Si cuadra, cambiamos el booleano.
        }
        return validado; // Se devuelve la validaci�n.
    }

    /**
     * M�todo que nos valida una fecha siempre que pertenezca al calendario
     * occidental rigurosamente. En caso de que sea v�lida, devuelve la
     * excepci�n.
     *
     * @since version 1.1 28/12/2013 (Tarea PROG05)
     * @param fecha String con la cadena tipo dd-mm-aaaa que queremos validar
     * @return boolean devuelve true si a fecha es v�lida, false, si no lo es.
     * @throws java.text.ParseException
     */
    public static boolean validaFecha(String fecha) throws ParseException {
        boolean fechaCorrecta;
        // Instanciamos una variable de la clase SimpleDateFormat con el
        // formato deseado.
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        // Establecemos la variable de tipo riguroso, para que no admita fechas
        // inexistentes en el calendario occidental.
        formato.setLenient(false);
        // Probamos a cuadrar la cadena fecha con el formato establecido.
        formato.parse(fecha);
        fechaCorrecta = true; // Si cuadra, el booleano cambiar� a true
        return fechaCorrecta; //devolvemos el booleano de fecha correcta, o no.
    }
    /**
     * M�todo que nos valida una fecha siempre que pertenezca al calendario
     * occidental rigurosamente. En caso de que sea v�lida, devuelve la
     * excepci�n.
     *
     * @since version 1.1 28/12/2013 (Tarea PROG05)
     * @param fecha String con la cadena tipo dd-mm-aaaa que queremos validar
     * @return boolean devuelve true si a fecha es v�lida, false, si no lo es.
     * @throws java.text.ParseException
     */
    public static boolean validaFechaNacimiento(String fecha) throws ParseException {
        boolean fechaCorrecta = false;
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
        //retornaremos una fecha pasada por par�metro, par validarla, perteneciente
        //a ese rango de fechas.
        if (Validar.validaFecha(fecha) && ES.compararFecha(fecha, minimo)!=-1
                && ES.compararFecha(fecha, maximo)!=1){
            fechaCorrecta = true;
        }
        return fechaCorrecta;
    }
    
    
    
    /**
     * M�todo que valida la altura de una persona, devolviendo true s�lo en 
     * caso de que �sta sea entre 0.00 y 3.00
     * 
     * @since version 1.12 12/01/2014 (Tarea PROG06)
     * @param altura String con altura para su validaci�n.
     * @return true si la altura es v�lida para una persona, false si no lo es.
     */
    public static boolean validaAltura(String altura) {
        boolean validado = false;
        // Instanciamos objeto clase Pattern, definiendo el patron necesario
        // El primer n�mero ser� de 6 a 9, y los 8 siguientes, de 0 a 9.
        Pattern patron = Pattern.compile("([0-2]{1}.[0-9]*)|([3].[0]*)");
        // Probamos a ajustar el dato al patron.
        Matcher cuadra = patron.matcher(altura);
        if (cuadra.matches()) {
            validado = true; //Si cuadra, cambiamos el booleano.
        }
        return validado; // Se devuelve la validaci�n.
    }

}
