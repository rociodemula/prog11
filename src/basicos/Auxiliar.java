/*
 * Tarea PROG11: Se mejora esta clase con la incorporación de la persistencia de datos
 * en la base de datos relacional mysql. Se apoya frecuentemente en el ArrayList para
 * gestiones sobre más de un registro.
 *
 * Tarea PROG10: Clase Gestión general de datos de Trabajadores con archivos
 * Permite manejar cualquier cantidad de elementos de la clase Trabajador, permitiendo
 * su almacenamiento en disco y posterior recuperación. También vuelca los datos
 * a un archivo xml y recupera los datos posteriorente, incorporándolos a ArrayList
 * en memoria.
 */
package basicos;
// Paquete que guardará las clases básicas para PROG11
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase principal para la gestión de datos de trabajadores. Tiene métodos para
 * operaciones básicas de datos sobre una cantidad indeterminada de sujetos, e
 * incorpora la persistencia en base de datos relacional mysql.
 *
 * @since version 1.0 13/12/2013 (Tarea PROG04)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 2.1 04/05/2014 (Tarea PROG11)
 */
public class Auxiliar {
    /* Declaramos un Arraylist como variable de clase, para poderla usar desde
     * cualquiera de los métodos de ayuda (helpers). 
     */

    static ArrayList<Trabajador> archivo = new ArrayList<>();
    // Declaramos booleano para saber si el usuario modifica el array
    static boolean datosModificados = false; //esta variable ha caído en desuso.

    /**
     * Método que genera un archivo de texto nominas.dat en el directorio
     * actual, con las nóminas de todos los trabajadores actualmente en la
     * base de datos, y con los datos de las variables estáticas con sus valores
     * actuales. En caso de que el archivo no exista, lo crea. Si el archivo
     * existe, los datos son despreciados, y se genera el archivo de nuevo. Si
     * existe algún problema al escribir los datos, volcará un mensaje de error,
     * incluyendo el mensaje del sistema, por su interés.
     *
     * @since version 1.4 02/03/2014 (PROG08)
     * @return String con mensaje corto indicando exito o fracaso de la
     * operación.
     */
    static String generarFicheroNominas() {
        String mensaje;
        //volcamos el contenido de la base de datos sobre el array, para poder
        //gestionar la creación del fichero de nóminas con mayor facilidad
        Auxiliar.leerFichero();
        try {
            File ficheroNominas = new File("nominas.dat"); //creamos objeto file
            if (!ficheroNominas.exists()) {
                // si esa ruta no se corresponde con un archivo físico, lo creamos.
                ficheroNominas.createNewFile();
            } else {
                // si el archivo físico existe, lo borramos, y creamos de nuevo.
                ficheroNominas.delete(); //borramos los datos
                ficheroNominas.createNewFile(); //creamos el archivo vacío.
            }
            /* creamos un objeto File, esta vez en modo append, para que se 
             * añadan los datos de todos los trabajadores en el bucle for.
             */
            FileWriter fichero = new FileWriter("nominas.dat", true);
            //creamos el objeto BufferedWriter
            BufferedWriter ficheroBuffer = new BufferedWriter(fichero);
            // y el PrintWriter, para poder manejar los datos con println
            PrintWriter ficheroSalida = new PrintWriter(ficheroBuffer);
            for (Persona archivo1 : archivo) {
                // Para cada posición del array, probamos a imprimir
                try {
                    // Si la posición tiene datos, los imprimimos
                    if (archivo1 instanceof TrabajadorFijo) {
                        // Si es TrabajadorFijo, usamos el string de nómina para fijos.
                        ficheroSalida.println(((TrabajadorFijo) archivo1).obtenerStringNomina());

                    } else {
                        // SI es TrabajadorEventual, el de eventuales.
                        ficheroSalida.println(((TrabajadorEventual) archivo1).obtenerStringNomina());
                    }

                } catch (NullPointerException e) {
                    // Si la posición es null, no haremos nada
                }
            }
            ficheroSalida.close();// cerramos el archivo
            //lanzamos el mensaje de éxito para el usuario.            
            mensaje = "Archivo nominas.dat generado.";
        } catch (IOException ex) {
            // Si salta esta excepción, es que hay un problema para leer los datos.
            mensaje = "Error de entrada/salida de datos."
                    + "\n  El archivo está vacío, o el usuario"
                    + "\n  no tiene permiso para generarlo."; //incluimos el mensaje del sistema, por su interés.
        }
        return mensaje;
    }

    /**
     * Método para leer el fichero de datos existente nominas.dat, y lo devuelve
     * como un String con retornos de carro. En caso de que el fichero nominas.dat no haya
     * sido generado, o no tengamos permiso para acceder a los datos, lanzaría
     * el mensaje de error, incluyendo los mensajes del sistema, por su interés.
     *
     * @since version 1.4 02/03/2014 (PROG08)
     * @return String con fichero de nóminas completo
     */
    static String leerFicheroNominas() throws Exception {
        String ficheroNominas = ""; //inicializamos en String
        try {
            //creamos objeto File, con el nombre del archivo
            FileReader fichero = new FileReader("nominas.dat");
            //creamos objero BufferedReader, para poder leer línea a línea.
            BufferedReader ficheroBuffer = new BufferedReader(fichero);
            String linea; //definimos un String para ir volcando las líneas.
            while ((linea = ficheroBuffer.readLine()) != null) {
                // mientras la línea no sea vacía, la acumulamos en el String
                //seguido de un retorno de carro
                ficheroNominas = ficheroNominas + linea + "\n";
            }
            //cerarmos el fichero
            ficheroBuffer.close();
            // lanzamos el mensaje de éxito al usuario de operación terminada
            ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                    + "completarse la lectura de datos del archivo nominas.dat.\n");
        } catch (FileNotFoundException e) {
            // si el fichero no existe, lanzamos un mensaje de error al usuario
            throw new Exception("\n¡Atención! El fichero nominas.dat no existe."
                    + "\n  Tenga en cuenta que para primero debe generarlo"
                    + "\n  con la opción Generar fichero Nóminas. Mensaje sistema: \n  "
                    + e.getMessage()); //incluimos el mensaje del sistema, por interés.
        } catch (IOException ex) {
            // Si salta esta excepción, es que hay un problema para leer los datos.
            throw new Exception("\n¡Atención! Error de entrada/salida de datos"
                    + "\n  Puede que el archivo esté vacío, o que usted"
                    + "\n  no tenga los permisos de lectura/escritura necesarios."
                    + "\n  Compruébelo, y vuelva a intentarlo.\n  Mensaje sistema: "
                    + ex.getMessage()); //incluimos el mensaje del sistema, por interés-
        }
        return ficheroNominas; //devolvemos el String formateado a nuestro gusto.
    }

    /**
     * Método para volcar el contenido de las tablas de la base de datos mysql,
     * en el ArrayList de memoria, para facilitar operaciones sobre varios registros.
     * Vuelca también el valor de las variables estáticas. En caso de cualquier
     * problema, además de un mensaje de error, se incluye, por su interés, el
     * mensaje del sistema
     *
     * @since version 1.4 02/03/2014 (PROG08)
     * @return String con mensaje corto de éxito o fracaso de la operación.
     */
    static void leerFichero() {
        CuentaBancaria ccc; //variable de apoyo para la creación de trabajadores
        Auxiliar.archivo = new ArrayList<>(); //inicializamos el ArrayList
        try { //abrimos la conexión
            GMainUI.conexion = DriverManager.getConnection(GMainUI.url);
            GMainUI.sentencia = GMainUI.conexion.createStatement();
            //ejecutamos la SELECT para traernos de mysql la tabla de eventuales
            ResultSet resultado = GMainUI.sentencia.executeQuery("SELECT nombre, nifonie, "
                    + "DATE_FORMAT(fechanacimiento,'%d-%m-%Y') fechanac, altura, ccc, "
                    + "ocupacion, telefono, numerodehijos, DATE_FORMAT(fechacontrato,'%d-%m-%Y') fechacon,"
                    + "email, numerocolaboraciones, numerohorastrabajadas, codigo FROM trabajadoreventual");
            while (resultado.next()) { //mientras haya trabajadores eventuales, los añadiremos al array
                ccc = new CuentaBancaria(resultado.getString("ccc"),
                        resultado.getString("nifonie"), resultado.getString("nombre"));
                Auxiliar.archivo.add(new TrabajadorEventual(resultado.getString("nombre"),
                        resultado.getString("nifonie"), resultado.getString("fechanac"),
                        resultado.getString("altura"), resultado.getString("ocupacion"),
                        resultado.getString("telefono"), resultado.getString("numerodehijos"),
                        ccc, resultado.getString("fechacon"), resultado.getString("email"),
                        resultado.getString("numerocolaboraciones"), resultado.getString("numerohorastrabajadas"),
                        resultado.getInt("codigo"))); //usamos el constructor que no altera los contadores
            } //ejecutamos la nueva SELECT para traernos la tabla de fijos
            resultado = GMainUI.sentencia.executeQuery("SELECT nombre, nifonie, "
                    + "DATE_FORMAT(fechanacimiento,'%d-%m-%Y') fechanac, altura, ccc, "
                    + "ocupacion, telefono, numerodehijos, DATE_FORMAT(fechacontrato,'%d-%m-%Y') fechacon,"
                    + "email, sueldofijo, numerohorasextras, codigo FROM trabajadorfijo");
            while (resultado.next()) { //volcaremos todos los fijos en el array
                ccc = new CuentaBancaria(resultado.getString("ccc"),
                        resultado.getString("nifonie"), resultado.getString("nombre"));
                Auxiliar.archivo.add(new TrabajadorFijo(resultado.getString("nombre"),
                        resultado.getString("nifonie"), resultado.getString("fechanac"),
                        resultado.getString("altura"), resultado.getString("ocupacion"),
                        resultado.getString("telefono"), resultado.getString("numerodehijos"),
                        ccc, resultado.getString("fechacon"), resultado.getString("email"),
                        resultado.getString("sueldofijo"), resultado.getString("numerohorasextras"),
                        resultado.getInt("codigo"))); //usamos el constructor que no altera los contadores
            } //ejecutamos la SELECT que nos trae la tabla configuración
            resultado = GMainUI.sentencia.executeQuery("SELECT * FROM configuracion");
            if (resultado.next()) { //volcamos a memoria todos los datos de configuración
                Trabajador.setTotalAltasTrabajador(resultado.getInt("codigoultimotrabajador"));
                Trabajador.setTotalTrabajadoresEnLaEmpresa(resultado.getInt("totaltrabajadores"));
                TrabajadorEventual.setTotalEventuales(resultado.getInt("totaleventuales"));
                TrabajadorEventual.setCuotaObrera(Double.toString(resultado.getDouble("cuotaobrera")));
                TrabajadorEventual.setPrecioHora(Double.toString(resultado.getDouble("preciohoratrabajada")));
                TrabajadorFijo.setTotalFijos(resultado.getInt("totalfijos"));
                TrabajadorFijo.setPrecioHoraExtra(Double.toString(resultado.getDouble("preciohoraextra")));
                TrabajadorFijo.setSueldoFijoMinimo(Double.toString(resultado.getDouble("sueldofijominimo")));
            }
            GMainUI.conexion.close(); //cerramos la conexión
        } catch (SQLException e) { //si ha habido algún error de mysql saltará esta excepción
            JOptionPane.showMessageDialog(null, "Error de conexión con la base de datos: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) { //si hay algún dato incorrecto en la base de datos, saltará esta excepción
            JOptionPane.showMessageDialog(null, "Error generando trabajadores: "
                    + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para borrar el contenido de la base de datos de MySql completa.
     * Es útil antes de una importación de datos xml, ya que si no se mexclarían
     * los datos de la importación con lo previamente existentes en la mysql.
     * 
     * @since version 2.1 04/05/2014 (PROG11)
     */
    static void borrarFichero() {
        try { //abrimos la conexión
            GMainUI.conexion = DriverManager.getConnection(GMainUI.url);
            GMainUI.sentencia = GMainUI.conexion.createStatement();
            //ejecutamos los DELETE que borran todas las tablas en la base de datos
            GMainUI.sentencia.executeUpdate("DELETE FROM trabajadoreventual");
            GMainUI.sentencia.executeUpdate("DELETE FROM trabajadorfijo");
            GMainUI.sentencia.executeUpdate("DELETE FROM configuracion");
            
            GMainUI.conexion.close(); //cerramos la conexión
        } catch (SQLException e) { //si ha habido algún error de mysql saltará esta excepción
            JOptionPane.showMessageDialog(null, "Error de conexión con la base de datos: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Método para grabar en la base de datos mysql, todos los datos nuevos de
     * memoria, tanto el array de trabajadores, como las variables estáticas. 
     * Añade los trabajadores no existentes aún en mysql, pero los repetidos,
     * no los actualiza, respeta la copia existente en la base de datos. En
     * caso de que exista algún problema, se lanzarán las excepciones
     * correspondientes a falta de permisos o que el fichero no exista
     *
     * @since version 1.4 01/03/2014
     */
    static void grabarFichero() {
        try { //abrimos la conexión
            GMainUI.conexion = DriverManager.getConnection(GMainUI.url);
            GMainUI.sentencia = GMainUI.conexion.createStatement();
            for (Trabajador trabajador : archivo) { //volcamos los trabajadores en mysql según su tipo
                //nos aseguramos de que el trabajador a volcar no exista ya en la base de datos.
                try{
                if (trabajador instanceof TrabajadorEventual && GMainUI.buscarTrabajador(trabajador.getNifONie(), true) == null) {
                    //ejecutamos el INSERT para incorporar el trabajador nuevo a mysql
                    GMainUI.sentencia.executeUpdate("INSERT INTO trabajadoreventual(nombre, "
                            + "nifonie, fechanacimiento, altura, ocupacion, "
                            + "telefono, numerodehijos, ccc, fechacontrato, "
                            + "email, numerocolaboraciones, numerohorastrabajadas, codigo) VALUES ('" + trabajador.getNombre()
                            + "', '" + trabajador.getNifONie() + "', STR_TO_DATE('" + trabajador.getFechaNacimiento()
                            + "','%d-%m-%Y'), " + Double.parseDouble(trabajador.getAltura()) + ", '" + trabajador.getOcupacion()
                            + "', '" + trabajador.getTelefono() + "', " + Integer.parseInt(trabajador.getNumeroDeHijos())
                            + ", '" + trabajador.getCcc().getCodigoCuentaCliente() + "', STR_TO_DATE('" + trabajador.getFechaContrato()
                            + "','%d-%m-%Y'), '" + trabajador.getEmail() + "', " + Integer.parseInt(((TrabajadorEventual) trabajador).getNumeroColaboraciones())
                            + ", " + Integer.parseInt(((TrabajadorEventual) trabajador).getNumeroDeHoras()) + ", " + trabajador.getCodigoTrabajador()
                            + ")");
                } else {
                    //si es fijo, y no está ya en la base de datos, ejeutaremos el INSERT sobre la tabla de fijos.
                    if (trabajador instanceof TrabajadorFijo && GMainUI.buscarTrabajador(trabajador.getNifONie(), true) == null) {                
                    GMainUI.sentencia.executeUpdate("INSERT INTO trabajadorfijo(nombre, "
                            + "nifonie, fechanacimiento, altura, ocupacion, "
                            + "telefono, numerodehijos, ccc, fechacontrato, "
                            + "email, sueldofijo, numerohorasextras, codigo) VALUES ('"
                            + trabajador.getNombre() + "', '"
                            + trabajador.getNifONie() + "', STR_TO_DATE('"
                            + trabajador.getFechaNacimiento() + "','%d-%m-%Y'), "
                            + Double.parseDouble(trabajador.getAltura()) + ", '"
                            + trabajador.getOcupacion() + "', '"
                            + trabajador.getTelefono() + "', "
                            + Integer.parseInt(trabajador.getNumeroDeHijos()) + ", '"
                            + trabajador.getCcc().getCodigoCuentaCliente() + "', STR_TO_DATE( '"
                            + trabajador.getFechaContrato() + "','%d-%m-%Y'), '"
                            + trabajador.getEmail() + "', "
                            + Double.parseDouble(((TrabajadorFijo) trabajador).getSueldoFijo()) + ", "
                            + Integer.parseInt(((TrabajadorFijo) trabajador).getNumeroHorasExtra()) + ", "
                            + trabajador.getCodigoTrabajador() + ")");
                    }
                }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            //guardamos los datos correspondientes a la configuración
            GMainUI.guardarConfiguracion(true);
            //cerramos el archivo
            GMainUI.conexion.close();
        } catch (SQLException e) { //si ha saltado esta excepción, es que algo ha ido mal con mysql
            JOptionPane.showMessageDialog(null, "Error de conexión con la base de datos: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /**
     * Método para volcar el contenido de fichero .xml en la base de datos, tanto
     * trabajadores como valores de variables estáticas. En caso de que el
     * archivo no exista, lanza la excepción. Se apoya en el método grabarFichero()
     * que no actaliza los datos, de existir ya ese trabajador en la base de datos.
     * Sólo lo añade, en caso de que no exista aún.
     *
     * @since version 1.4 02/03/2014 (PROG08)
     * @see basicos.Auxiliar#grabarFichero() 
     * @throws io.Exception en caso de que algo salga mal.
     */
    static void importarXml() throws Exception {
        try {
            //nos traemos la información del xml al árbol DOM
            Document documento = DOMUtil.XML2DOM("trabajadores.xml");
            //pisotemos el ArrayList con otra instancia, y modificamos el booleano
            Auxiliar.archivo = new ArrayList<>();
            datosModificados = true;
            //inicializamos los contadores, para evitar falsear los datos, con las
            //nuevas instancias volcadas del xml
            Trabajador.setTotalAltasTrabajador(Integer.parseInt(DOMUtil.extraerHijoDeRaiz(documento, "totalAltasTrabajador")));
            Trabajador.setTotalTrabajadoresEnLaEmpresa(Integer.parseInt(DOMUtil.extraerHijoDeRaiz(documento, "totalTrabajadoresEmpresa")));
            TrabajadorFijo.setTotalFijos(Integer.parseInt(DOMUtil.extraerHijoDeRaiz(documento, "totalFijosEmpresa")));
            TrabajadorEventual.setTotalEventuales(Integer.parseInt(DOMUtil.extraerHijoDeRaiz(documento, "totalEventualesEmpresa")));
            //extraemos las variables estáticas con el método helper de DOMUtil
            TrabajadorEventual.setCuotaObrera(DOMUtil.extraerHijoDeRaiz(documento, "cuotaObrera"));
            TrabajadorEventual.setPrecioHora(DOMUtil.extraerHijoDeRaiz(documento, "precioHora"));
            TrabajadorFijo.setSueldoFijoMinimo(DOMUtil.extraerHijoDeRaiz(documento, "sueldoFijoMinimo"));
            TrabajadorFijo.setPrecioHoraExtra(DOMUtil.extraerHijoDeRaiz(documento, "precioHoraExtra"));

            //extraemos la lista de trabajadores en una variable, para verlos uno a uno
            NodeList trabajadores = documento.getElementsByTagName("trabajador");

            //con el for peinaremos todos los elementos existentes para cada trabajador
            for (int i = 0; i < trabajadores.getLength(); i++) {
                //para cada trabajador, extraemos el item, y volcamos cada etiqueta
                //en variables puente que luego usaremos
                Node trabajador = trabajadores.item(i);
                String id = DOMUtil.extraerHijoDeElemento((Element) trabajador, "nifONie");
                String nombre = DOMUtil.extraerHijoDeElemento((Element) trabajador, "nombre");
                String fechaNacimiento = DOMUtil.extraerHijoDeElemento((Element) trabajador, "fechaNacimiento");
                String altura = DOMUtil.extraerHijoDeElemento((Element) trabajador, "altura");
                String ocupacion = DOMUtil.extraerHijoDeElemento((Element) trabajador, "ocupacion");
                String telefono = DOMUtil.extraerHijoDeElemento((Element) trabajador, "telefono");
                String numeroHijos = DOMUtil.extraerHijoDeElemento((Element) trabajador, "numHijos");
                String bancoCcc = DOMUtil.extraerHijoDeElemento((Element) trabajador, "bancoCcc");
                String bancoNifONie = DOMUtil.extraerHijoDeElemento((Element) trabajador, "bancoNifONie");
                String bancoNombre = DOMUtil.extraerHijoDeElemento((Element) trabajador, "bancoNombre");
                CuentaBancaria ccc = new CuentaBancaria(bancoCcc, bancoNifONie, bancoNombre);
                String fechaContrato = DOMUtil.extraerHijoDeElemento((Element) trabajador, "fechaContrato");
                int codigo = Integer.parseInt(DOMUtil.extraerHijoDeElemento((Element) trabajador, "codigo"));
                String email = DOMUtil.extraerHijoDeElemento((Element) trabajador, "email");
                try {
                    //tenemos que contemplar la posibilidad de que las etiqutas de los atributos específicos
                    //de las subclases, no existan en algunos casos.
                    String horasExtra = DOMUtil.extraerHijoDeElemento((Element) trabajador, "numHorasExtra");
                    String sueldoFijo = DOMUtil.extraerHijoDeElemento((Element) trabajador, "sueldoFijo");
                    //en caso de que aquí no haya saltado la excepcion, es que el objeto es un fijo
                    //instanciamos con los datos almacenados en las variables puente (usando 2º constructor)
                    TrabajadorFijo fijo = new TrabajadorFijo(nombre, id, fechaNacimiento, altura, ocupacion,
                            telefono, numeroHijos, ccc, fechaContrato, email, sueldoFijo, horasExtra, codigo);
                    //así que instanciamos con el 2º constructor, para no alterar los totales ni el código 
                    //original del trabajador, y añadimos al archivo un elemento
                    Auxiliar.archivo.add(fijo);

                } catch (Exception e) {
                    //si ha saltado la excepción, comprobamos el mensaje lanzado
                    if (e.getMessage().equalsIgnoreCase("No existe numHorasExtra")) {
                        //si hemos llegado aquí es que el trabajador no es fijo
                        //así que intentamos recuperar los atributos específicos de eventual
                        String numHoras = DOMUtil.extraerHijoDeElemento((Element) trabajador, "numHoras");
                        String numeroColaboraciones = DOMUtil.extraerHijoDeElemento((Element) trabajador, "numColaboracion");
                        //instanciamos un eventual, con el segundo constructor, los datos almacenados en las variables puente
                        TrabajadorEventual eventual = new TrabajadorEventual(nombre, id, fechaNacimiento, altura, ocupacion,
                                telefono, numeroHijos, ccc, fechaContrato, email, numeroColaboraciones, numHoras, codigo);
                        //añadimos el trabajador al archivo
                        Auxiliar.archivo.add(eventual);

                    }
                }
            }
            //si hemos llegado aquí, es que toda la importación ha ido como la seda
            ES.msgln("\n¡Operación realizada con exito!\n  Acaban de "
                    + "importarse los datos del archivo trabajadores.xml.\n");
        } catch (ClassNotFoundException ex) {
            // si salta esta excepción hay un problema con la serialización
            throw new Exception("No se pudo leer el archivo de forma correcta."
                    + "\n  Existe un problema con la serialización de datos.");
            // insertamos el mensaje del sistema, por su interés
        } catch (FileNotFoundException e) {
            // Si no existe, avisamos al usuario de que no existen datos grabados
            throw new Exception("No se pudieron leer los datos."
                    + "\nEl fichero no existe, o el usuario \n  "
                    + "no tiene permisos de lectura.");
            // insertamos el mensaje del sistema, por su interés
        } catch (IOException e) {
            // Si salta esta excepción, es que hay un problema para leer los datos.
            throw new Exception("Error de entrada/salida de datos"
                    + "\nPuede que el archivo esté vacío."
                    + "\nCompruébelo, y vuelva a intentarlo."); // insertamos el mensaje del sistema, por su interés
        } catch (Exception ex) {
            // si ha saltado la excepción, lanzamos el mensaje de error
            throw new Exception("No se pudo leer el dato de forma correcta."
                    + "\n  Puede que el archivo trabajadores.xml esté vacío, o no exista.");
        }
        if (!archivo.isEmpty()) { 
            //si hay algo que grabar, lo volcamos en mysql, previo borrado de tablas
            Auxiliar.borrarFichero();
            Auxiliar.grabarFichero();
        } else { // si no existen datos, lanzamos el mensaje al usuario.
            throw new Exception("No hay datos en trabajadores.xml para importar."
                    + "\n  Es posible que el fichero no exista o esté vacío.");
        }
    }

    /**
     * Método para grabar en el trabajadores.xml en disco, todos los datos de la
     * base de datos, tanto trabajadores, como variables estáticas. En
     * caso de que exista algún problema, se lanzará la excepción.
     *
     * @since version 1.4 01/03/2014
     * @throws io.Exception lanzada si no existen datos que grabar o hay algun
     * problema al grabar los datos.
     */
    static void exportarXml() throws Exception {
        //miramos si tenemos datos para volcar en el ArratyList
        leerFichero(); //volcamos a memoria los datos de mysql
        if (!archivo.isEmpty()) { //si hay datos que exportar, comenzamos el proceso
            //creamos un árbol DOM vacíopara alojar los datos
            Document documento = DOMUtil.crearDOMVacio("archivoTrabajadores");
            //con ayuda de los métodos helper de DOMUtil, añadimos los datos al DOM
            //comenzamos por las variable estáticas genéricas, y específicas de subclases
            DOMUtil.añadirHijoARaiz(documento, "totalAltasTrabajador", Trabajador.getTotalAltasTrabajador());
            DOMUtil.añadirHijoARaiz(documento, "totalTrabajadoresEmpresa", Trabajador.getTotalTrabajadoresEnLaEmpresa());
            DOMUtil.añadirHijoARaiz(documento, "totalEventualesEmpresa", TrabajadorEventual.getTotalEventuales());
            DOMUtil.añadirHijoARaiz(documento, "totalFijosEmpresa", TrabajadorFijo.getTotalFijos());
            DOMUtil.añadirHijoARaiz(documento, "cuotaObrera", TrabajadorEventual.getCuotaObrera());
            DOMUtil.añadirHijoARaiz(documento, "precioHora", TrabajadorEventual.getPrecioHora());
            DOMUtil.añadirHijoARaiz(documento, "sueldoFijoMinimo", TrabajadorFijo.getSueldoFijoMinimo());
            DOMUtil.añadirHijoARaiz(documento, "precioHoraExtra", TrabajadorFijo.getPrecioHoraExtra());
            for (Trabajador trabajador : archivo) {
                //recorreremos el ArrayList volcando uno a uno todos los atributos de los trabajadores
                //añadimos una etiqueta en el elemento raíz para cada trabajador
                Element etiquetaTrabajador = DOMUtil.añadirHijoARaiz(documento, "trabajador");

                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "nifONie", trabajador.getNifONie());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "nombre", trabajador.getNombre());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "fechaNacimiento", trabajador.getFechaNacimiento());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "altura", trabajador.getAltura());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "ocupacion", trabajador.getOcupacion());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "telefono", trabajador.getTelefono());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "numHijos", trabajador.getNumeroDeHijos());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "bancoCcc", trabajador.getCcc().getCodigoCuentaCliente());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "bancoNifONie", trabajador.getCcc().getNifONieDelTitular());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "bancoNombre", trabajador.getCcc().getNombreTitularCuenta());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "fechaContrato", trabajador.getFechaContrato());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "codigo", trabajador.getCodigoTrabajador());
                DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "email", trabajador.getEmail());
                //si se trata de un eventual, volcaremos los atributos específicos con sus correspondientes etiquetas
                if (trabajador instanceof TrabajadorEventual) {
                    DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "numHoras", ((TrabajadorEventual) trabajador).getNumeroDeHoras());
                    DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "numColaboracion", ((TrabajadorEventual) trabajador).getNumeroColaboraciones());
                } else {
                    //si no, si es un fijo, volcaremos el atributo específico de fijo
                    if (trabajador instanceof TrabajadorFijo) {
                        DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "numHorasExtra", ((TrabajadorFijo) trabajador).getNumeroHorasExtra());
                        DOMUtil.añadirHijoAElemento(documento, etiquetaTrabajador, "sueldoFijo", ((TrabajadorFijo) trabajador).getSueldoFijo());
                    } else {
                        //si hemos llegado aquí, ha pasado algo con las subclases, y lanzamos el mensaje de error
                        throw new Exception("Existe un problema "
                                + "con el tipo de dato a grabar.\n La clase de Trabajador no es válida.\n");
                    }
                }
            }
            //aquí ya tendremos en el DOM todos los datos, volcamos al .xml
            if (DOMUtil.DOM2XML(documento, "trabajadores.xml")) {
                //si sale bien, lanzamos mensaje de éxito
                ES.msgln("\n¡Operación realizada con exito!\n  Acaban de "
                        + "exportarse los datos al archivo trabajadores.xml.\n");
            } else {
                //si no, lanzamos mensaje de error
                throw new Exception("Se ha producido un problema "
                        + "al grabar el archivo.\n");
            }
        } else {
            //si el ArrayList stá vacío, lanzamos mensaje de que no hay nada para exportar
            throw new Exception("No existen datos para exportar. "
                    + "La base de datos está vacía.\n");
        }
    }

    /**
     * Método que pide confirmación previa al usuario para una gestión que está
     * a punto de realizarse. Existen 2 posibilidades de datos de entrada: - Si
     * el índicePersona facilitado es diferente de -1, pedirá confirmación para
     * hacer la gestión sobre un dato de una persona en concreto, que se
     * corresponderá con ese índice en el array. - Si el indicePersona es -1
     * pedira confirmación para realizar la gestión sobre un archivo completo.
     *
     * Devuelve true si la respuesta es afirmativa, false, si es negativa.
     *
     * @since version 1.0 13/12/2013
     * @param indicePersona int del índice de la persona sobre la que se
     * realizará la gestión.
     * @param gestion String mensaje que contiene el nombre de la gestión.
     * @param dato String mensae que contiene el nombre del dato a modificar.
     * @return boolean confirmar true,respuesta afirmativa, false, negativa.
     */
    static boolean confirmarGestion(int indicePersona, String gestion,
            String dato) {
        // Lo hemos definido como estático para poderlo usar como queremos,
        // como método de clase, sin tenerlo que instanciar, y es private 
        // ya que es muy específico, para usarlo sólo en esta clase
        boolean confirmado = false; // declaramos e inicializamos un booleano
        if (indicePersona != -1) {
            if ("s".equals(ES.leeRespuesta("\n¿Desea " + gestion + " " + dato + " de "
                    // Pedimos confirmación al usuario con ES.leeRespuesta().
                    + archivo.get(indicePersona).getNifONie() + " ? "))) {
                confirmado = true; // Si la respuesta es positiva, cambiamos a true.
            }
        } else if ("s".equals(ES.leeRespuesta("\n¿Desea " + gestion + " " + dato
                // Pedimos confirmación al usuario con ES.leeRespuesta().
                + " grabado en disco? "))) {
            confirmado = true; // Si la respuesta es positiva, cambiamos a true.
        }
        return confirmado; // Retornamos la respuesta del usuario.
    }

    /**
     * Método que devuelve la posición del ArrayList donde hay un hueco o que
     * coincida con el id facilitado.
     *
     * @since version 1.0 13/12/2013
     * @param buscarHueco boolean si es false comparará un NIF/NIE y lo buscará
     * en el array, si es true buscará el primer null que encuentre.
     * @param id String con el numero de identificación (Nif o nie) a buscar, en
     * caso de que buscarHueco sea false, en el recorrido de la matriz
     * @return int el índice de la posición del array que coindide con el hueco,
     * o con el id buscado.
     */
    static int buscarPosicion(boolean buscarHueco, String id) {
        // Definimos como estático, para usarlo sin isntanciar, y private.
        int indicePersona = -1; // Le damos un valor negativo al indice.
        boolean hecho = false; //Booleano para activar y terminar bucles
        if (!buscarHueco) { // si no estamos buscando un hueco...
            // Buscamos en el archivo el número de identificación
            for (int i = 0; !hecho && i < Auxiliar.archivo.size(); i++) {
                if ((Auxiliar.archivo.get(i) != null) && (Auxiliar.archivo.get(i).getNifONie().
                        equalsIgnoreCase(id))) {
                    // Si lo encontramos, guardamos posición en variable índice
                    indicePersona = i;
                    // Y cambiamos el boleano, para que deje de buscar
                    hecho = true;
                }
            }
        } else { //si estamos buscando un hueco...
            //buscamos el primer null del array
            // Y lo buscamos en el archivo
            for (int i = 0; ((!hecho) && (i < Auxiliar.archivo.size())); i++) {
                if (Auxiliar.archivo.get(i) == null) {
                    // Si lo encontramos, guardamos posición en variable indice
                    indicePersona = i;
                    // Y cambiamos booleano para forzar la salida del bucle.
                    hecho = true;
                }
            }
        }
        return indicePersona; //Devolvemos el índice de la posición solicitada
    }

    /**
     * Método "helper" que realiza todas las gestiones que no implican
     * instanciación de nuevas personas, pide confirmaciones al usuario, y
     * sobreescribe los datos si es lo deseado.
     *
     * @since version 1.0 13/12/2013
     * @param indicePersona int posición índice en el array de la persona sobre
     * la que se realiza la gestión.
     * @param opcion int opción pulsada por el usuario en el menú.
     * @param dato String cadena con el nombre del dato a gestionar.
     */
    static void gestionarPersona(int indicePersona, int opcion, String dato) {
        // Definido como static y private, igual que esl resto de métodos de
        // esta clase.
        // Dependiendo de la opcion de menú tecleada, hacemos una u otra gestión.
        switch (opcion) {
            case 1:
                // Si es 1 (corregir NIF/NIE del Trabajador y Titular de la cuenta),
                // pedimos confirmación, y si es
                // positiva, pedimos nuevo NIF/NIE de Trabajador/Titular, y sobreeescribimos.
                // ambos datos de una vez, para evitar problemas de disparidad
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setNifONie(ES.leeNifONie("\nTeclee "
                                + dato + " nuevo para "
                                + archivo.get(indicePersona).getNombre()));
                        archivo.get(indicePersona).setCcc(new CuentaBancaria(archivo.get(indicePersona).getCcc().getCodigoCuentaCliente(),
                                archivo.get(indicePersona).getNifONie(),
                                archivo.get(indicePersona).getCcc().getNombreTitularCuenta()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
            case 2:
                // Si es 2 (corregir nombre), y el usuario confirma sobrrescribir,
                // cambiamos el dato con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setNombre(ES.leeCadena("\nTeclee "
                                + dato + " nuevo para "
                                + archivo.get(indicePersona).getNifONie()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }

                }
                break;
            case 3:
                // Si es 3 (corregir fecha nacimiento), y el usuario confirma,
                // sobreescribimos con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setFechaNacimiento(ES.leeFecha("\n"
                                + "Teclee " + dato + " nueva para "
                                + archivo.get(indicePersona).getNifONie()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (ParseException ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
            case 4:
                // Si es 4 (corregir altura), y el usuario confirma,
                // sobreescribimos con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setAltura(ES.leeAltura("\nTeclee "
                                + dato + " nueva para "
                                + archivo.get(indicePersona).getNifONie()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
            case 5:
                // Si es 5 (corregir teléfono), y el usuario confirma,
                // sobreescribimos con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setTelefono(ES.leeTelefono("\nTeclee "
                                + dato + " nuevo para "
                                + archivo.get(indicePersona).getNifONie()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
            case 6:
                // Si es 6 (corregir ocupación), y el usuario confirma,
                // sobreescribimos con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setOcupacion(ES.leeCadena("\nTeclee "
                                + dato + " nuevo para "
                                + archivo.get(indicePersona).getNifONie()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }

                }
                break;
            case 7:
                // Si es 7 (corregir nº hijos), y el usuario confirma,
                // sobreescribimos con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setNumeroDeHijos(ES.leeCadena("\nTeclee "
                                + dato + " nuevo para "
                                + archivo.get(indicePersona).getNifONie()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
            case 8:
                // Si es 8 (corregir fecha de contrato), y el usuario confirma,
                // sobreescribimos con la entrada de teclado.
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        String fechaContrato = ES.leeFecha("\nTeclee " + dato
                                + " nueva para " + archivo.get(indicePersona).getNifONie(),
                                archivo.get(indicePersona).getFechaNacimiento(),
                                "" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                                + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1)
                                + "-" + (Calendar.getInstance().get(Calendar.YEAR)));
                        // después de haber recogido la fecha, comprobamos que sea
                        // posterior o igual a la fecha de fundación de la empresa.
                        // usamos un método helper de la clase ES para comparar fechas                                    
                        archivo.get(indicePersona).setFechaContrato(fechaContrato);
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
            case 9:
                // Si es 9 (corregir nº colaboraciones), lo primero que haremos es 
                // confirmar que el índice facilitado corresponde a una instancia
                // de la subclase TrabajadorEventual
                if (archivo.get(indicePersona) instanceof TrabajadorEventual) {
                    // Si lo es, y el usuario confirma,en primer lugar, 
                    // sobreescribimos con la entrada de teclado.
                    if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                        try {
                            //Tenemos que hacer un casting para acceder a los métodosde la subclase
                            ((TrabajadorEventual) archivo.get(indicePersona)).
                                    setNumeroColaboraciones(ES.leeCadena("\nTeclee "
                                                    + dato + " nuevo para "
                                                    + archivo.get(indicePersona).getNifONie()));
                            datosModificados = true;
                            ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                    + "modificarse el trabajador "
                                    + archivo.get(indicePersona).getNifONie() + ".\n");
                        } catch (Exception ex) {
                            ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                    + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                    + "datos, e intente la operación desde el principio."
                                    + "\n  Disculpe las molestias, por favor.");
                        }
                    }
                } else {
                    ES.msgln("\n¡Atención! No se puede realizar esta operación "
                            + "para uns trabajador no eventual.\n  Revise sus "
                            + "datos, e intente la operación desde el principio."
                            + "\n  Disculpe las molestias, por favor.");
                }
                break;
            case 10:
                // Si es 10 (corregir nº de horas), lo primero que haremos es 
                // confirmar que el índice facilitado corresponde a una instancia
                // de la subclase TrabajadorEventual
                if (archivo.get(indicePersona) instanceof TrabajadorEventual) {
                    // Si lo es, y el usuario confirma,en primer lugar, 
                    // sobreescribimos con la entrada de teclado.
                    if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                        try {
                            //Tenemos que hacer un casting para acceder a los métodosde la subclase
                            ((TrabajadorEventual) archivo.get(indicePersona)).
                                    setNumeroDeHoras(ES.leeCadena("\nTeclee "
                                                    + dato + " nuevo para "
                                                    + archivo.get(indicePersona).getNifONie()));
                            datosModificados = true;
                            ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                    + "modificarse el trabajador "
                                    + archivo.get(indicePersona).getNifONie() + ".\n");
                        } catch (Exception ex) {
                            ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                    + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                    + "datos, e intente la operación desde el principio."
                                    + "\n  Disculpe las molestias, por favor.");
                        }
                    }
                } else {
                    ES.msgln("\n¡Atención! No se puede realizar esta operación "
                            + "para uns trabajador no eventual.\n  Revise sus "
                            + "datos, e intente la operación desde el principio."
                            + "\n  Disculpe las molestias, por favor.");
                }
                break;
            case 11:
                // Si es 11 (corregir nº de horas extra), lo primero que haremos es 
                // confirmar que el índice facilitado corresponde a una instancia
                // de la subclase TrabajadorFijo
                if (archivo.get(indicePersona) instanceof TrabajadorFijo) {
                    // Si lo es, y el usuario confirma,en primer lugar, 
                    // sobreescribimos con la entrada de teclado.
                    if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                        try {
                            //Tenemos que hacer un casting para acceder a los métodosde la subclase
                            ((TrabajadorFijo) archivo.get(indicePersona)).
                                    setNumeroHorasExtra(ES.leeCadena("\nTeclee "
                                                    + dato + " nuevo para "
                                                    + archivo.get(indicePersona).getNifONie()));
                            datosModificados = true;
                            ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                    + "modificarse el trabajador "
                                    + archivo.get(indicePersona).getNifONie() + ".\n");
                        } catch (Exception ex) {
                            ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                    + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                    + "datos, e intente la operación desde el principio."
                                    + "\n  Disculpe las molestias, por favor.");
                        }
                    }
                } else {
                    ES.msgln("\n¡Atención! No se puede realizar esta operación "
                            + "para uns trabajador no eventual.\n  Revise sus "
                            + "datos, e intente la operación desde el principio."
                            + "\n  Disculpe las molestias, por favor.");
                }
                break;
            case 12:
                // Si es 1 (corregir el nºde cuenta corriente),
                // pedimos confirmación, y si es positiva, sobreescribimos
                if (confirmarGestion(indicePersona, "sobreescribir", dato)) {
                    try {
                        archivo.get(indicePersona).setCcc(new CuentaBancaria(ES.leeCuentaBancaria("\nTeclee "
                                + dato + " nuevo para " + archivo.get(indicePersona).getNifONie()),
                                archivo.get(indicePersona).getCcc().getNifONieDelTitular(),
                                archivo.get(indicePersona).getCcc().getNombreTitularCuenta()));
                        datosModificados = true;
                        ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
                                + "modificarse el trabajador "
                                + archivo.get(indicePersona).getNifONie() + ".\n");
                    } catch (Exception ex) {
                        ES.msgln("\n¡Atención! Existe algún problema con el dato que"
                                + " ha introducido para " + ex.getMessage() + ".\n  Revise sus "
                                + "datos, e intente la operación desde el principio."
                                + "\n  Disculpe las molestias, por favor.");
                    }
                }
                break;
        } // Devolvemos el control a main(), al bucle del submenú modificar.
    }

    /**
     * Método helper que dibuja un menú y recoge la opción que el usuario pulsa.
     * Dispone de 3 tipos de menú, según el parámetro suministrado:
     * <br> 1 - Menú de 16 opciones para gestión de Trabajadores.
     * <br> 2 - Menú de 2 opciones para altas de los subtipos de Trabajadores
     * <br> 3 - Menú de 12 opciones para modificación de Trabajadores
     * <br> 4 - Menú de 4 opciones para ordenación de trabajadores por
     * diferentes criterios.
     *
     * @see basicos.ES#leeEntero(java.lang.String, int, int)
     * @since version 1.3 15/02/2014 (PROG07)
     * @param tipoMenu entero con el tipo de menu que se desea. 1 para menú
     * Gestión de Trabajadores 2 para menú Altas de Trabajadores 3 para menú
     * Modificar Trabajadores y 4 para Menú de ordenación de trabajadores.
     * @return opción tecleada por el usuario para el menu que proceda.
     */
    static int elegirOpcionMenu(int tipoMenu) {
        // inicializamos la variable opcion a 0
        int opcion = 0;
        switch (tipoMenu) {
            //según el tipo de menú (1, 2 ó 3), se dibuja uno o otro abanico de opciones.
            case 1:  //opcion 1: menu principal
                ES.msgln("\nGestión de Trabajadores.");
                ES.msgln("================================");
                ES.msgln("    1.- Añadir Trabajadores.");
                ES.msgln("    2.- Borrar Trabajadores.");
                ES.msgln("    3.- Listar Trabajadores.");
                ES.msgln("    4.- Ordenar Trabajadores.");
                ES.msgln("    5.- Modificar Trabajadores.");
                ES.msgln("    6.- Mostrar por pantalla los datos de un trabajador.");
                ES.msgln("    7.- Establecer Sueldo Fijo (Para trabajadores fijos).");
                ES.msgln("    8.- Establecer Precio Hora Extra (Para trabajadores fijos).");
                ES.msgln("    9.- Establecer Cuota Obrera (Para trabajadores eventuales).");
                ES.msgln("   10.- Establecer Precio Hora (Para trabajadores eventuales).");
                ES.msgln("   11.- Recuperar los datos desde el fichero.");
                ES.msgln("   12.- Escribir los datos al fichero.");
                ES.msgln("   13.- Recuperar fichero de nóminas.");
                ES.msgln("   14.- Generar fichero de nóminas.");
                ES.msgln("   15.- Exportar a XML.");
                ES.msgln("   16.- Importar de XML.\n");
                ES.msgln("    0.- Salir de la aplicación.");
                ES.msgln("================================");

                // Recogemos opción tecleada, utilizando el método ES.leeEntero()
                opcion = ES.leeEntero("  Introduzca la opción elegida", 0, 16);
                break;
            case 2: // opcion 2: menú de altas
                ES.msgln("\nAltas de Trabajadores.");
                ES.msgln("================================");
                ES.msgln("   1.- Añadir Trabajador Fijo.");
                ES.msgln("   2.- Añadir Trabajador Eventual.\n");
                ES.msgln("   0.- Volver al menú anterior.");
                ES.msgln("================================");

                // Recogemos subopción tecleada, utilizando el método ES.leeEntero()
                opcion = ES.leeEntero("  Introduzca la opción elegida", 0, 2);
                break;
            case 3: // opcion 3: menú modificaciones
                ES.msgln("\nModificar Trabajadores.");
                ES.msgln("================================");
                ES.msgln("   1.- Cambiar NIF/NIE Trabajador/Titular Cuenta.");
                ES.msgln("   2.- Cambiar nombre.");
                ES.msgln("   3.- Cambiar la fecha de nacimiento.");
                ES.msgln("   4.- Cambiar la altura.");
                ES.msgln("   5.- Cambiar el teléfono.");
                ES.msgln("   6.- Cambiar la ocupación.");
                ES.msgln("   7.- Cambiar nº de hijos.");
                ES.msgln("   8.- Cambiar fecha de contrato.");
                ES.msgln("   9.- Cambiar nº de colaboraciones (Trabajadores eventuales).");
                ES.msgln("   10.- Cambiar nº de horas trabajadas (Trabajadores eventuales).");
                ES.msgln("   11.- Cambiar nº de horas extra (Trabajadores fijos).");
                ES.msgln("   12.- Cambiar nº de cuenta corriente.\n");
                ES.msgln("   0.- Volver al menú anterior.");
                ES.msgln("================================");

                // Recogemos subopción tecleada, utilizando el método ES.leeEntero()
                opcion = ES.leeEntero("  Introduzca la opción elegida", 0, 12);
                break;
            case 4: // opcion 3: menú modificaciones
                ES.msgln("\nOrdenar Trabajadores.");
                ES.msgln("================================");
                ES.msgln("   1.- Por nombre, ascendente A-Z (no distingue may/min).");
                ES.msgln("   2.- Por nombre, descendente Z-A (no distingue may/min).");
                ES.msgln("   3.- Por importe neto de nómina, descendente.");
                ES.msgln("   4.- Por código de trabajador, ascendente.\n");
                ES.msgln("   0.- Volver al menú anterior.");
                ES.msgln("================================");

                // Recogemos subopción tecleada, utilizando el método ES.leeEntero()
                opcion = ES.leeEntero("  Introduzca la opción elegida", 0, 4);
                break;
        }
        return opcion; // se retorna la opcion tecleada.
    }

    /**
     * Antiguo Método principal de la clase Auxiliar.
     *
     * Visualiza el menú, y realiza las instancias de las 2 sublases de
     * Trabajador. Se ayuda de otros métodos para algunas acciones.
     *
     * @since version 1.0 13/12/2013
     * @param args la E/S de datos se efectúa por la ventana output estándar.
     * @throws java.lang.Exception
     * @deprecated Desde la version 2.0 se usa el GMainUI.main()
     */
    /* Comentamos el método main() para evitar confusiones con la clase gráfica
     GmainUI y su método main(), llamada principal de la aplicación desde la
     versión 2.0
    
     public static void main(String[] args) throws Exception {
     // Declarar variables para opciones menús e indices de array.
     int opcion, subopcion, indiceArray;
     // Variables para gestión intermedia de datos de la clase Trabajador.
     String nombre, id, fechaNacimiento, altura, ocupacion, telefono,
     fechaContrato, numeroHijos, horasExtra,
     numeroColaboraciones, sueldoFijo, email, numHoras;
     // Nos traemos los datos del fichero trabajadores.dat
     leerFichero(); //Este método se encarga de contro1larlo todo.

     do {
     /* Usamos método para pintar menú principal en pantalla,            
     * y recoger opción tecleada. Pasamos parámetro para menú principal.
     */ /*
     opcion = elegirOpcionMenu(1);
     // Seleccionamos la acción correspondiente a la opción tecleada.
     switch (opcion) {
     case 0:
     /*
     * Controlamos si los datos han sido modificados antes de permitir
     * al usuario abandonar la aplicación
     */ /*
     if (datosModificados) {
     ES.msgln("¡Atención! Está a punto de abandonar el programa"
     + "sin haber grabado los últimos datos introducidos.");
     // Pedimos al usuario confirmación para abandonar el programa
     if (confirmarGestion(-1, "guardar los datos antes de salir",
     " y conservarlos")) {
     // Si confirma que quiere guardar los datos actuales, grabamos
     grabarFichero();
     } else {
     // si no confirma, descartamoe los últimos cambios.
     ES.msgln("Ha elegido descartar los últimos cambios.");
     }
     }
     ES.msgln("\n---- Fin de la aplicación ----");
     break;
     case 1:
     /* Usamos de nuevo el mismo método para el submenú altas
     * y recoger subopción tecleada. Parámetro 2 para menú altas
     */ /*
     subopcion = elegirOpcionMenu(2);
     while (subopcion != 0) { // se ejecutará hasta que se pulse 0
     //Recogemos el número de NIF/NIE para identificación.
     id = ES.leeNifONie("Introduzca NIF/NIE del trabajador "
     + "a crear");
     /* Antes de crear un trabajador, pasamos al método helper el id
     * y buscamos si ya existe en el array
     */ /*
     if (buscarPosicion(false, id) != -1) {
     // si devuelve algo que no sea -1 es que ese nif/nie ya existe.
     ES.msgln("\n¡Atención! Ese trabajador ya existe.\n  "
     + "Para modificarlo debe acceder "
     + "al menú de modificaciones,\n  o introducir "
     + "nuevo NIF/NIE para otra alta.");
     } else {
     // si no, pedimos los datos para crear un nuevo objeto.
     try {
     //Si se encontró hueco, instanciamos la ccc
     CuentaBancaria ccc = new CuentaBancaria(ES.leeCuentaBancaria("CCC"),
     ES.leeNifONie("NIF/NIE del titular de la cuenta"),
     ES.leeCadena("Nombre del Titular de la cuenta"));
     //luego recogemos todos los atributos para crear el trabajador
     nombre = ES.leeCadena("Nombre");
     fechaNacimiento = ES.leeFechaNacimiento("Fecha de nacimiento");
     altura = ES.leeAltura("Altura (en metros)");
     ocupacion = ES.leeCadena("Ocupación");
     telefono = ES.leeTelefono("Teléfono");
     numeroHijos = ES.leeCadena("Nº de hijos");
     email = ES.leeCadena("E-mail");
     //Para la fecha de contrato, no admitimos fecha posterior al día de hoy
     // ni anterior a la fecha de nacimiento del trabajador.
     fechaContrato = ES.leeFecha("Fecha contrato", fechaNacimiento,
     "" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
     + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1)
     + "-" + (Calendar.getInstance().get(Calendar.YEAR)));
     // después de haber recogido la fecha, comprobamos que sea
     // posterior o igual a la fecha de fundación de la empresa.
     // usamos un método helper de la clase ES para comparar fechas

     switch (subopcion) {
     // Usamos una instancia diferente según sea trabajador 
     // de uno u otro tipo, pero antes terminamos de 
     // recopilar información de los atributos específicos.
     case 1:
     // la clase TrabajadorFijo tiene un atributo específico
     horasExtra = ES.leeCadena("Nº de horas extra");
     sueldoFijo = ES.leeCadena("Suelfo Fijo:");
     //Una vez guardadas en las variables de apoyo, instanciamos al trabajador.
     //añadiéndolo al ArrayList
     if (archivo.add(new TrabajadorFijo(nombre,
     id, fechaNacimiento, altura, ocupacion,
     telefono, numeroHijos, ccc, fechaContrato, email,
     sueldoFijo, horasExtra))) {
     datosModificados = true; //marcamos nuestro booleano 
     // Si sale bien, se lanza un mensaje de éxito
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "crearse el trabajador fijo nuevo "
     + id + ".\n");
     } else {
     // si no, lanzamos un mensaje de error
     ES.msgln("\n¡Atención!\n  Ha habido un problema en "
     + "el proceso de creación del trabajador fijo "
     + id + ".\n");
     }
     break;
     case 2:
     // la clase TrabajadorEventual tiene 2 atributos específicos.
     numeroColaboraciones = ES.leeCadena("Nº de colaboraciones");
     numHoras = ES.leeCadena("Nº de horas");
     // instanciamos en un if, para ver si todo sale bien.
     if (archivo.add(new TrabajadorEventual(nombre,
     id, fechaNacimiento, altura, ocupacion,
     telefono, numeroHijos, ccc, fechaContrato, email,
     numeroColaboraciones, numHoras))) {
     datosModificados = true; //marcamos el booleano
     // Y se lanza un mensaje de éxito
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "crearse el trabajador eventual nuevo "
     + id + ".\n");
     } else {
     //o lanzamos el mensaje de error, si ha fallado algo
     ES.msgln("\n¡Atención!\n  Ha habido un problema en "
     + "el proceso de creación del trabajador eventual "
     + id + ".\n");
     }
     break;

     }
     } catch (Exception ex) {
     // si se ha recogido alguna excepción, lanzamos el mensaje documental.
     ES.msgln("\n¡Atención! Existe algún problema con el dato que"
     + " ha introducido\n  para " + ex.getMessage() + ".\n  Revise sus "
     + "datos, e intente la operación desde el principio."
     + "\n  Disculpe las molestias, por favor.");
     }
     }
     // volvemos a pintar el menu, y recoger nueva subopcion 
     // antes de volver a hacer la comparación del while.
     subopcion = elegirOpcionMenu(2);
     }
     break;
     case 2:
     // Usamos el helper para localizar a la persona a borrar
     id = ES.leeNifONie("Introduzca NIF/NIE del trabajador "
     + "a borrar");
     indiceArray = buscarPosicion(false, id);
     if (indiceArray == -1) {
     // Si no la hemos encontrado, mensaje de error
     ES.msgln("El NIF/NIE facilitado no existe en el archivo.");
     } else {
     // Si la encontramos, pedimos confirmación de borrado
     if (confirmarGestion(indiceArray, "borrar", "datos")) {
     // Y si hay confirmación, borramos.
     archivo.remove(indiceArray);
     datosModificados = true; //marcamos el booleano
     //restamos un trabajador en la empresa
     Trabajador.totalTrabajadoresEnLaEmpresa--;
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "borrar el trabajador del archivo.\n");
     }
     }
     break;
     case 3:
     // Pintamos mensaje para que el usuario sepa qué está viendo
     int contadorEventuales = 0,
     contadorFijos = 0;
     ES.msg("\nListado de Datos de todos los trabajadores en el archivo:");
     for (Trabajador archivo1 : archivo) {
     // Para cada posición del array, probamos a imprimir
     try {
     // Si la posición tiene datos, los imprimimos
     ES.msg(archivo1.toString());
     if (archivo1 instanceof TrabajadorFijo) {
     // si es fijo, subimos el contador de fijos
     contadorFijos++;
     } else {
     // si es eventual, el contador de eventuales sube
     contadorEventuales++;
     }
     } catch (NullPointerException e) {
     // Si la posición es null, no haremos nada
     }
     }
     // Mensaje de fin de archivo, para informar al usuario.
     ES.msgln("\n\n-- Total: " + Trabajador.getTotalTrabajadoresEnLaEmpresa()
     + " trabajadores. Fijos: " + contadorFijos
     + ". Eventuales: " + contadorEventuales
     + ". Fin de archivo --");
     break;
     case 4:
     do {
     // Si la encontramos, se visualiza el menú de modificaciones (3)
     //  y se recoge subopción tecleada.
     subopcion = elegirOpcionMenu(4);
     switch (subopcion) {
     /* Según la subopcion de que se trate, mandamos
     * elegimos uno u otro método de ordenación
     */ /*
     case 1:
     //usamos el comparador adecuado a cada opción, con sort
     Collections.sort(archivo, new ComparadorNombreAscendente());
     datosModificados = true; //marcamos el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "ordenarse el archivo de trabajadores por nombre (A-Z).\n");
     break;
     case 2:
     Collections.sort(archivo, new ComparadorNombreDescendente());
     datosModificados = true; //marcamos el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "ordenarse el archivo de trabajadores por nombre (Z-A).\n");
     break;
     case 3:
     Collections.sort(archivo, new ComparadorNetoNominaDescendente());
     datosModificados = true; //marcamos el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "ordenarse el archivo de trabajadores por neto nómina.\n");
     break;
     case 4:
     Collections.sort(archivo, new ComparadorCodigoTrabajador());
     datosModificados = true; //marcamos el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "ordenarse el archivo de trabajadores por código de trabajador.\n");
     break;
     }
     } while (subopcion != 0); //Seguiremos hasta pulsar 0
     break;
     case 5:
     // Usamos de nuevo el helper que encuentra personas o huecos
     id = ES.leeNifONie("Introduzca NIF/NIE del trabajador "
     + "a modificar");
     indiceArray = buscarPosicion(false, id);
     if (indiceArray == -1) {
     // Si no la encontramos, mensaje de error.
     ES.msgln("El NIF/NIE facilitado no existe en el archivo.");
     } else {
     do {
     // Si la encontramos, se visualiza el menú de modificaciones (3)
     //  y se recoge subopción tecleada.
     subopcion = elegirOpcionMenu(3);
     switch (subopcion) {
     /* Según la subopcion de que se trate, mandamos
     * la inforamación al helper para que realice
     * la gestión que corresponda.
     * Pasamos como parámetros la posición de índice
     * de la persona, la subopcion, y un par de String
     * que nos serán de ayuda.
     */ /*
     case 1:
     gestionarPersona(indiceArray, subopcion,
     "NIF/NIE del Trabajador/Titular Cuenta");
     break;
     case 2:
     gestionarPersona(indiceArray, subopcion,
     "nombre");
     break;
     case 3:
     gestionarPersona(indiceArray, subopcion,
     "fecha de nacimiento");
     break;
     case 4:
     gestionarPersona(indiceArray, subopcion,
     "altura");
     break;
     case 5:
     gestionarPersona(indiceArray, subopcion,
     "teléfono");
     break;
     case 6:
     gestionarPersona(indiceArray, subopcion,
     "ocupación");
     break;
     case 7:
     gestionarPersona(indiceArray, subopcion,
     "nº de hijos");
     break;
     case 8:
     gestionarPersona(indiceArray, subopcion,
     "fecha de contrato");
     break;
     case 9:
     gestionarPersona(indiceArray, subopcion,
     "nº colaboraciones");
     break;
     case 10:
     gestionarPersona(indiceArray, subopcion,
     "nº horas trabajadas");
     break;
     case 11:
     gestionarPersona(indiceArray, subopcion,
     "nº horas extra");
     break;
     case 12:
     gestionarPersona(indiceArray, subopcion,
     "nº cuenta bancaria");
     break;
     }
     } while (subopcion != 0); //Seguiremos hasta pulsar 0
     }
     break;
     case 6:
     // Usamos de nuevo el helper que localiza personas en el array
     // Si no hay que buscar un hueco, pedimos NIF/NIE al usuario
     id = ES.leeNifONie("Introduzca NIF/NIE del trabajador "
     + "a visualizar");
     indiceArray = buscarPosicion(false, id);
     if (indiceArray == -1) {
     // Mensaje de error, si no se ha encontrado
     ES.msgln("El NIF/NIE facilitado no existe en el archivo.");
     } else {
     // Si se encuentra, se imprime la información.
     ES.msgln(archivo.get(indiceArray).toString());
     }
     break;
     case 7:
     try {
     // fijamos el nuevo sueldo fijo. Para este ejercicio,
     // partiremos siempre de que tiene que ser superior o
     // igual al actual. Como hemos inicializado sueldoFijo en
     // la clase en base al SMI, no debe admitir menos de eso.
     TrabajadorFijo.setSueldoFijoMinimo(ES.leeCadena("Introduzca nuevo"
     + " sueldo fijo"));
     datosModificados = true; //marcamos el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "modificar el sueldo fijo.\n");
     } catch (Exception ex) {
     ES.msgln("\n¡Atención! Existe algún problema con el dato que"
     + " ha introducido\n  para " + ex.getMessage() + ".\n  Revise sus "
     + "datos, e intente la operación desde el principio."
     + "\n  Disculpe las molestias, por favor.");
     }
     break;
     case 8:
     try {
     // con la misma filosofía fijamos el nuevo precio hora extra
     TrabajadorFijo.setPrecioHoraExtra(ES.leeCadena("Introduzca nuevo"
     + " precio hora extra"));
     datosModificados = true; //marcamos la modificación en el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "modificar el precio de la hora extra.\n");
     } catch (Exception ex) {
     ES.msgln("\n¡Atención! Existe algún problema con el dato que"
     + " ha introducido\n  para " + ex.getMessage() + ".\n  Revise sus "
     + "datos, e intente la operación desde el principio."
     + "\n  Disculpe las molestias, por favor.");
     }
     break;
     case 9:
     try {
     // para la cuota obrera, permitiremos cualquier numero
     // permitido por el propio método
     TrabajadorEventual.setCuotaObrera(ES.leeCadena("Introduzca nueva"
     + " cuota obrera"));
     datosModificados = true;//marcamos la modificación en el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "modificar la cuota obrera.\n");
     } catch (Exception ex) {
     ES.msgln("\n¡Atención! Existe algún problema con el dato que"
     + " ha introducido\n  para " + ex.getMessage() + ".\n  Revise sus "
     + "datos, e intente la operación desde el principio."
     + "\n  Disculpe las molestias, por favor.");
     }
     break;
     case 10:
     try {
     //el precio hora...
     TrabajadorEventual.setPrecioHora(ES.leeCadena("Introduzca nuevo"
     + " precio hora"));
     datosModificados = true;//marcamos la modificación en el booleano
     ES.msgln("\n¡Operación realizada con exito!\n  Acaba de "
     + "modificar el precio hora.\n");
     } catch (Exception ex) {
     ES.msgln("\n¡Atención! Existe algún problema con el dato que"
     + " ha introducido\n  para " + ex.getMessage() + ".\n  Revise sus "
     + "datos, e intente la operación desde el principio."
     + "\n  Disculpe las molestias, por favor.");
     }
     break;
     case 11:
     /* Antes de recuperar el fichero, comprobamos si hmos modificado los datos
     * de memoria desde el último vuelco de fichero, y si es así, pedimos confirmación
     */ /*
     if (!datosModificados || (datosModificados && confirmarGestion(-1, "descartar",
     "los cambios, y volver a lo "))) {
     // si el usuario confirma, o los datos no han sido modificados, leemos.
     leerFichero();
     } else {
     // en caso contrario, confirmamos que no se ha realizado la acción
     ES.msgln("\n¡Atención! No se han leido los datos de disco."
     + "\n  Usted sigue trabajando con los datos de memoria.");
     }
     break;
     case 12:
     /* Si los datos de memoria se han modificado, pedimos confirmación 
     * antes de sobreescribir los datos de disco
     */ /*
     if (datosModificados && confirmarGestion(-1, "sobreescribir",
     "el archivo de trabajadores")) {
     // si el usuario confirma, volcamos la información al archivo.
     grabarFichero();
     } else {
     // Si no ha sido modificado, avisamos que no hay datos nuevos que grabar
     ES.msgln("\n¡Atención! No se han introducido datos nuevos "
     + " y no hay nada para grabar. Revise el proceso"
     + "\n  e inténtelo de nuevo, por favor.");
     }
     break;
     case 13:
     // recuperamos el archivo nominas.dat, y lo mostramos por pantalla
     leerFicheroNominas();
     break;
     case 14:
     /* generamos el nuevo archivo nominas.dat con el contenido actual
     * de los datos de memoria.
     */ /*
     generarFicheroNominas();
     break;
     case 15:
     // volcamos el ArrayList a un archivo trabajadores.xml
     exportarXml();
     break;
     case 16:
     /* recuperamos los datos del archivo tabajadores.xml y los 
     * volcamos en el ArrayList. En caso de que haya datos en
     * memoria, pedimos confirmación antes de machacarlos
     */ /*
     if (!datosModificados || (datosModificados && confirmarGestion(-1, "descartar",
     "los cambios, y volver a lo "))) {
     // si el usuario confirma, o los datos no han sido modificados, importamos.
     importarXml();
     } else {
     // en otro caso, comunicamos que no se ha realizado la acción
     ES.msgln("\n¡Atención! No se han importado datos del disco."
     + "\n  Usted sigue trabajando con los datos de memoria.");
     }
     break;
     }
     } while (opcion != 0); // Si la opción pulsada es 0, finaliza el programa.
     }*/










}
