/*
 * Tarea PROG10: Unificación de tipado de atributos a String. No admite valores
 * vacíos en atributos de objeto. Por herencia, implementa el método enviarEmail().
 * Se añade el contador para eventuales.
 * Tarea PROG09: Mejoras en la clase TrabajadorEventual, subclase de Trabajador
 * Gestión de trabajadores de tipo eventual
 */
package basicos;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase para gestion de objetos TrabajadorEventual, subclase de Trabajador
 *
 * @since version 1.3 15/02/2014 (Tarea PROG07)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public class TrabajadorEventual extends Trabajador implements Serializable {

    // Atributos específicos de esta subclase. Se declaran ´con el tipo más
    // adecuado a cada una.
    // Consideramos las cifras en base al SMI 2014 mensual 645,30 y 
    // diario de 21.51 (por tanto 2.69 por hora, de mínimo)
    // y en base a él se inicializa el precio hora. Para la cuota obrera no
    // inicializamos ningún valor concreto, dejando como única opción para su
    // manipulación el uso del setter correspondiente
    private String numeroColaboraciones; //será específica de cada elemento
    private String numeroDeHoras;        //se declaran private
    private static String precioHora = "2.69"; //será la misma para todos los objetos de la clase
    private static String cuotaObrera = "0";//por eso se declara static.
    public static int totalEventualesEnLaEmpresa = 0; //se añade el contador para eventuales

    /**
     * Primer constructor de la clase TrabajadorEventual. Gestiona todos los
     * parámetros usuales para un trabajador. Parte de ellos, heredados de la
     * clase Trabajador En caso de que alguno de los atributos no esté en los
     * rangos/tipos adecuados lanzará una excepción.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String con el nombre del trabajador
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con teléfono validado
     * @param numeroHijos String con nº de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroColaboraciones String con el nº de colaboraciones
     * @param numeroHoras String con el nº de horas
     * @param email String con el email del trabajador
     * @throws Exception
     */
    public TrabajadorEventual(String nombre, String id, String fecha,
            String altura, String ocupacion, String telefono, String numeroHijos,
            CuentaBancaria ccc, String fechaContrato, String email, String numeroColaboraciones,
            String numeroHoras)
            throws Exception {
        // usamos el constructor de la superclase
        super(nombre, id, fecha, altura, ocupacion, telefono, numeroHijos, ccc,
                fechaContrato, email);
        // añadimos los atributos específicos, de forma segura.
        this.setNumeroColaboraciones(numeroColaboraciones);
        this.setNumeroDeHoras(numeroHoras);
        super.setCodigoTrabajador(++totalAltasTrabajador); // incrementamos directametne
        // el contador de altas, que sólo crece, nunca decrece.
        ++totalTrabajadoresEnLaEmpresa; //y el contador que crecerá con las altas,
        ++totalEventualesEnLaEmpresa; //sumamos uno al total de eventuales
        // y decrecerá con las bajas.

    }

    /**
     * Segundo constructor de la clase TrabajadorEventual. Gestiona el alta de
     * un trabajador con todos los datos del constructor base, pero añadiendo un
     * nº de código concreto. Es útil para gestión de altas intermedias que se
     * usan como trabajadores-puente para alojar datos modificados. No altera
     * los contadores de trabajadores, y asigna al trabajador el código pasado
     * como parámetro. También es necesario en recuperación de datos de fichero.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String con el nombre del trabajador
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con teléfono validado
     * @param numeroHijos String con nº de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroColaboraciones String con el nº de colaboraciones
     * @param numeroHoras String con el nº de horas
     * @param email String con el email del trabajador
     * @param codigo int con el código del trabajador que queremos instanciar
     * @throws Exception
     */
    public TrabajadorEventual(String nombre, String id, String fecha,
            String altura, String ocupacion, String telefono, String numeroHijos,
            CuentaBancaria ccc, String fechaContrato, String email, String numeroColaboraciones,
            String numeroHoras, int codigo)
            throws Exception {
        // usamos el constructor de la superclase
        this(nombre, id, fecha, altura, ocupacion, telefono, numeroHijos, ccc,
                fechaContrato, email, numeroColaboraciones, numeroHoras);
        // añadimos los atributos específicos, de forma segura.
        super.setCodigoTrabajador(codigo); // asignamos al trabajador el parámetro código 
        --totalAltasTrabajador; //disminuimos el total de altas de trabajadores, 
        //incrementado desde el primer constructor
        --totalTrabajadoresEnLaEmpresa; //corregimos también el contador de trabajadores,
        --totalEventualesEnLaEmpresa; //y el del total de eventuales

    }

    /**
     * Getter para obtener el precio hora común a todos los objetos
     * TrabajadorEventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con el nuevoPrecioHora
     */
    public static String getPrecioHora() {
        return precioHora; //devolvemos el valor de la variable estática
    }

    /**
     * Setter para establecer el precio hora común a todos los objetos
     * TrabajadorEventual. Admitirá cualquier cantidad mayor que 0, si no lo es,
     * lanzaría la excepción. No admite cadenas vacías, ni que exceda la capacidad
     * de la base de datos (999.99).
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param precio String con el nuevoPrecioHora a fijar
     * @throws java.lang.Exception
     */
    public static void setPrecioHora(String precio) throws Exception {
        if (!precio.isEmpty()) {  //no queremos campos vacíos.
            try {
                double nuevoPrecioHora = Double.parseDouble(precio);
                if (nuevoPrecioHora > 0) {
                    if (nuevoPrecioHora <= 999.99) {
                        TrabajadorEventual.precioHora = String.valueOf(nuevoPrecioHora); //asignamos nuevo valor a la variable estática
                    } else {
                        throw new Exception("PRECIO HORA no valido (debe ser menor o igual que 999.99)");
                    }
                } else {
                    throw new Exception("PRECIO HORA no valido (debe ser mayor que 0)");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("PRECIO HORA debe ser un nº positivo > 0 (admite decimales)");
            }

        } else {
            throw new Exception("Falta PRECIO HORA");
        }
    }

    /**
     * Getter para numero de colaboraciones de un trabajador eventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con el numeroColaboraciones
     */
    public String getNumeroColaboraciones() {
        return numeroColaboraciones;
    }

    /**
     * Setter para número de colaboraciones de un trabajador eventual. Admitirá
     * cualquier cantidad mayor o igual que 0, en caso contrario lanzaría la
     * excepción. No admite cadenas vacías.
     *
     * @since version 1.3 15/02/2014(PROG07)
     * @param numeroColaboraciones String con el numeroColaboraciones
     * @throws java.lang.Exception
     *
     */
    public final void setNumeroColaboraciones(String numeroColaboraciones) throws Exception {
        if (!numeroColaboraciones.isEmpty()) {
            try {
                int colaboraciones = Integer.parseInt(numeroColaboraciones);
                if (colaboraciones >= 0) {
                    this.numeroColaboraciones = String.valueOf(colaboraciones);
                } else {
                    throw new Exception("Nº COLABORACIONES no válido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("Nº COLABORACIONES debe ser un nº entero positivo");
            }
        } else {
            throw new Exception("Falta Nº COLABORACIONES");
        }
    }

    /**
     * Getter para obtener la cuota Obrera común a todos los trabajadores
     * eventuales
     *
     * @since version 1.3 15/02/2014(PROG07)
     * @return double con la cuota obrera
     */
    public static String getCuotaObrera() {
        return cuotaObrera; //retornamos el valor d la variable estática
    }

    /**
     * Setter para fijar nueva cuota Obrera común a todos los trabajadores
     * eventuales. Admite cualquier cifra mayor 0 igual que 0, de lo contrario, lanzaría
     * la excepción. No admite valores vacíos, ni que excedan la capacidad de la
     * base de datos (999.99).
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param cuota String con la nuevaCuotaObrera a fijar
     * @throws java.lang.Exception
     */
    public static void setCuotaObrera(String cuota) throws Exception {
        if (!cuota.isEmpty()) {
            try {
                double nuevaCuotaObrera = Double.parseDouble(cuota);
                if (nuevaCuotaObrera >= 0) {
                    if (nuevaCuotaObrera <= 999.99){
                    TrabajadorEventual.cuotaObrera = String.valueOf(nuevaCuotaObrera); //fijamos la nueva couta obrera
                    }else{
                        throw new Exception("CUOTA OBRERA NO VÁLIDA (debe ser menor o igual que 999.99)");
                    }
                } else {
                    throw new Exception("CUOTA OBRERA NO VÁLIDA (debe ser mayor o igual que 0)");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("CUOTA OBRERA debe ser un nº >= 0 (admite decimales)");
            }
        } else {
            throw new Exception("Falta CUOTA OBRERA");
        }
    }

    /**
     * Getter para el número de horas de un trabajador eventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con el numero de horas
     */
    public String getNumeroDeHoras() {
        return this.numeroDeHoras;
    }

    /**
     * Setter para fijar el número de horas de un trabajador eventual. Admite
     * cualquier cantidad mayor que 0. No admite cadenas vacías.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param numeroDeHoras String con el numeroDeHoras a fijar
     * @throws java.lang.Exception
     */
    public final void setNumeroDeHoras(String numeroDeHoras) throws Exception {
        if (!precioHora.isEmpty()) {
            try {
                int horas = Integer.parseInt(numeroDeHoras);
                if (horas > 0) {
                    this.numeroDeHoras = String.valueOf(horas);
                } else {
                    throw new Exception("Nº DE HORAS no válido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("NºDE HORAS debe ser un entero > 0");
            }
        } else {
            throw new Exception("Falta NºDE HORAS");
        }
    }

    /**
     * Método que fija el total de eventuales de la clase, modificando el valor
     * de la variable estática. No admite enteros menores a 0
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @param totalEventuales int con el nuevo total de eventuales
     * @throws Exception
     */
    public static void setTotalEventuales(int totalEventuales) throws Exception {
        if (totalEventuales >= 0) {
            totalEventualesEnLaEmpresa = totalEventuales;
        } else {
            throw new Exception("Nº EVENTUALES no válido");
        }
    }

    /**
     * Método que recupera el valor de la variable estática autogenerada que
     * aloja el total de eventuales en la clase.
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @return int con el total
     */
    public static int getTotalEventuales() {
        return totalEventualesEnLaEmpresa;
    }

    /**
     * Método que permite inicializar a 0 el total de eventuales creados por el
     * constructor. Útil para la creación de instancias a partir de datos
     * procedentes de ficheros.
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @throws Exception
     */
    public static void inicializarEventuales() throws Exception {
        setTotalEventuales(0);
    }

    /**
     * Método para calcular el importe bruto de la nómina de un trabajador
     * eventual, según la fórmula: horas * precio hora - cuota obrera
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con importe bruto
     */
    @Override
    public double calcularImporteBrutoNomina() {
        return Integer.parseInt(this.getNumeroDeHoras()) * Double.parseDouble(getPrecioHora()) - Double.parseDouble(getCuotaObrera());
    }

    /**
     * Método para calcular el finiquito de un trabajador eventual, según la
     * fórmula: (importe bruto * antiguedad / 100) - cuota obrera
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con finiquito calculado
     */
    @Override
    public double calcularFiniquito() {
        //usamos el método de la superclase, más el cálculo especifíco.
        return super.calcularFiniquito() - Double.parseDouble(getCuotaObrera());
    }

    /**
     * Método para obtener un String con los datos apropiados para construir Un
     * archivo tipo texto con datos del trabajador y algunos de nómina. El
     * formato que debe resultar ha sdo facilitado por IES Aguadulce para la
     * Tarea 8 de PROG.
     *
     * @since version 1.4 02/03/2014 (PROG08)
     * @return String formateado con cabecesa y campos comunes a la clase.
     */
    @Override
    public String obtenerStringNomina() {
        return super.obtenerStringNomina() + "\n\n\n\n\n\nNº de horas:\t\t\t\t\t\t"
                + ((TrabajadorEventual) this).getNumeroDeHoras() + "\nPrecio hora: \t\t\t\t\t\t"
                + TrabajadorEventual.getPrecioHora() + "\nCuota obrera: \t\t\t\t\t\t"
                + TrabajadorEventual.getCuotaObrera() + "\nImporte Bruto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteBrutoNomina()) + "\nRetenciones: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularRetencionesNomina()) + "\n\nImporte Neto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteNetoNomina()) + "\f";
    }

    /**
     * Método para obtener una cadena con todos los dato organizados para un
     * trabajador eventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con todos los datos.
     */
    @Override
    public String toString() {
        //usaremos el método de la superclase, más los atributos específicos.
        return super.toString() + "\n  Nº de horas: " + this.getNumeroDeHoras()
                + ". Nº de colaboraciones: " + this.getNumeroColaboraciones();

    }

    /**
     * Método de envío de mail que se encarga de poner en circulación un mensaje
     * remitido por un usuario dado por parametro, y enviarlo al trabajador en
     * su correo definido en el atributo Email. PREMISA: la cuenta del remitente
     * ha de ser de @gmail.com
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @param usuarioRemitente String con el mail del remitente
     * @param password String con el password del remitente
     * @param asunto String cn el asunto del mensaje
     * @param textoMensaje String con el cuerpo del mensaje.
     * @return String con mensaje informativo
     */
    @Override
    public boolean enviarEmail(String usuarioRemitente, String password,
            String asunto, String textoMensaje) {
        boolean envioCorrecto = false;
        Properties configuracion = new Properties();
        configuracion.setProperty("mail.smtp.host", "smtp.gmail.com");
        configuracion.setProperty("mail.smtp.starttls.enable", "true");
        configuracion.setProperty("mail.smtp.port", "587");
        configuracion.setProperty("mail.smtp.user", this.getEmail());
        configuracion.setProperty("mail.smtp.auth", "true");
        Session sesion = Session.getDefaultInstance(configuracion);
        MimeMessage mensaje = new MimeMessage(sesion);
        try {
            mensaje.setFrom(new InternetAddress(usuarioRemitente));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(this.getEmail()));
            mensaje.setSubject(asunto);
            mensaje.setText(textoMensaje, "UTF-8", "html");
            Transport conexion = sesion.getTransport("smtp");
            conexion.connect(usuarioRemitente, password);
            conexion.sendMessage(mensaje, mensaje.getAllRecipients());
            conexion.close();
            envioCorrecto = true;
        } catch (MessagingException ex) {
            envioCorrecto = false;
        }
        return envioCorrecto;

    }
}
