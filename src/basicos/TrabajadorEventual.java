/*
 * Tarea PROG10: Unificaci�n de tipado de atributos a String. No admite valores
 * vac�os en atributos de objeto. Por herencia, implementa el m�todo enviarEmail().
 * Se a�ade el contador para eventuales.
 * Tarea PROG09: Mejoras en la clase TrabajadorEventual, subclase de Trabajador
 * Gesti�n de trabajadores de tipo eventual
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

    // Atributos espec�ficos de esta subclase. Se declaran �con el tipo m�s
    // adecuado a cada una.
    // Consideramos las cifras en base al SMI 2014 mensual 645,30 y 
    // diario de 21.51 (por tanto 2.69 por hora, de m�nimo)
    // y en base a �l se inicializa el precio hora. Para la cuota obrera no
    // inicializamos ning�n valor concreto, dejando como �nica opci�n para su
    // manipulaci�n el uso del setter correspondiente
    private String numeroColaboraciones; //ser� espec�fica de cada elemento
    private String numeroDeHoras;        //se declaran private
    private static String precioHora = "2.69"; //ser� la misma para todos los objetos de la clase
    private static String cuotaObrera = "0";//por eso se declara static.
    public static int totalEventualesEnLaEmpresa = 0; //se a�ade el contador para eventuales

    /**
     * Primer constructor de la clase TrabajadorEventual. Gestiona todos los
     * par�metros usuales para un trabajador. Parte de ellos, heredados de la
     * clase Trabajador En caso de que alguno de los atributos no est� en los
     * rangos/tipos adecuados lanzar� una excepci�n.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String con el nombre del trabajador
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con tel�fono validado
     * @param numeroHijos String con n� de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroColaboraciones String con el n� de colaboraciones
     * @param numeroHoras String con el n� de horas
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
        // a�adimos los atributos espec�ficos, de forma segura.
        this.setNumeroColaboraciones(numeroColaboraciones);
        this.setNumeroDeHoras(numeroHoras);
        super.setCodigoTrabajador(++totalAltasTrabajador); // incrementamos directametne
        // el contador de altas, que s�lo crece, nunca decrece.
        ++totalTrabajadoresEnLaEmpresa; //y el contador que crecer� con las altas,
        ++totalEventualesEnLaEmpresa; //sumamos uno al total de eventuales
        // y decrecer� con las bajas.

    }

    /**
     * Segundo constructor de la clase TrabajadorEventual. Gestiona el alta de
     * un trabajador con todos los datos del constructor base, pero a�adiendo un
     * n� de c�digo concreto. Es �til para gesti�n de altas intermedias que se
     * usan como trabajadores-puente para alojar datos modificados. No altera
     * los contadores de trabajadores, y asigna al trabajador el c�digo pasado
     * como par�metro. Tambi�n es necesario en recuperaci�n de datos de fichero.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String con el nombre del trabajador
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con tel�fono validado
     * @param numeroHijos String con n� de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroColaboraciones String con el n� de colaboraciones
     * @param numeroHoras String con el n� de horas
     * @param email String con el email del trabajador
     * @param codigo int con el c�digo del trabajador que queremos instanciar
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
        // a�adimos los atributos espec�ficos, de forma segura.
        super.setCodigoTrabajador(codigo); // asignamos al trabajador el par�metro c�digo 
        --totalAltasTrabajador; //disminuimos el total de altas de trabajadores, 
        //incrementado desde el primer constructor
        --totalTrabajadoresEnLaEmpresa; //corregimos tambi�n el contador de trabajadores,
        --totalEventualesEnLaEmpresa; //y el del total de eventuales

    }

    /**
     * Getter para obtener el precio hora com�n a todos los objetos
     * TrabajadorEventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con el nuevoPrecioHora
     */
    public static String getPrecioHora() {
        return precioHora; //devolvemos el valor de la variable est�tica
    }

    /**
     * Setter para establecer el precio hora com�n a todos los objetos
     * TrabajadorEventual. Admitir� cualquier cantidad mayor que 0, si no lo es,
     * lanzar�a la excepci�n. No admite cadenas vac�as, ni que exceda la capacidad
     * de la base de datos (999.99).
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param precio String con el nuevoPrecioHora a fijar
     * @throws java.lang.Exception
     */
    public static void setPrecioHora(String precio) throws Exception {
        if (!precio.isEmpty()) {  //no queremos campos vac�os.
            try {
                double nuevoPrecioHora = Double.parseDouble(precio);
                if (nuevoPrecioHora > 0) {
                    if (nuevoPrecioHora <= 999.99) {
                        TrabajadorEventual.precioHora = String.valueOf(nuevoPrecioHora); //asignamos nuevo valor a la variable est�tica
                    } else {
                        throw new Exception("PRECIO HORA no valido (debe ser menor o igual que 999.99)");
                    }
                } else {
                    throw new Exception("PRECIO HORA no valido (debe ser mayor que 0)");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("PRECIO HORA debe ser un n� positivo > 0 (admite decimales)");
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
     * Setter para n�mero de colaboraciones de un trabajador eventual. Admitir�
     * cualquier cantidad mayor o igual que 0, en caso contrario lanzar�a la
     * excepci�n. No admite cadenas vac�as.
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
                    throw new Exception("N� COLABORACIONES no v�lido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("N� COLABORACIONES debe ser un n� entero positivo");
            }
        } else {
            throw new Exception("Falta N� COLABORACIONES");
        }
    }

    /**
     * Getter para obtener la cuota Obrera com�n a todos los trabajadores
     * eventuales
     *
     * @since version 1.3 15/02/2014(PROG07)
     * @return double con la cuota obrera
     */
    public static String getCuotaObrera() {
        return cuotaObrera; //retornamos el valor d la variable est�tica
    }

    /**
     * Setter para fijar nueva cuota Obrera com�n a todos los trabajadores
     * eventuales. Admite cualquier cifra mayor 0 igual que 0, de lo contrario, lanzar�a
     * la excepci�n. No admite valores vac�os, ni que excedan la capacidad de la
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
                        throw new Exception("CUOTA OBRERA NO V�LIDA (debe ser menor o igual que 999.99)");
                    }
                } else {
                    throw new Exception("CUOTA OBRERA NO V�LIDA (debe ser mayor o igual que 0)");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("CUOTA OBRERA debe ser un n� >= 0 (admite decimales)");
            }
        } else {
            throw new Exception("Falta CUOTA OBRERA");
        }
    }

    /**
     * Getter para el n�mero de horas de un trabajador eventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con el numero de horas
     */
    public String getNumeroDeHoras() {
        return this.numeroDeHoras;
    }

    /**
     * Setter para fijar el n�mero de horas de un trabajador eventual. Admite
     * cualquier cantidad mayor que 0. No admite cadenas vac�as.
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
                    throw new Exception("N� DE HORAS no v�lido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("N�DE HORAS debe ser un entero > 0");
            }
        } else {
            throw new Exception("Falta N�DE HORAS");
        }
    }

    /**
     * M�todo que fija el total de eventuales de la clase, modificando el valor
     * de la variable est�tica. No admite enteros menores a 0
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @param totalEventuales int con el nuevo total de eventuales
     * @throws Exception
     */
    public static void setTotalEventuales(int totalEventuales) throws Exception {
        if (totalEventuales >= 0) {
            totalEventualesEnLaEmpresa = totalEventuales;
        } else {
            throw new Exception("N� EVENTUALES no v�lido");
        }
    }

    /**
     * M�todo que recupera el valor de la variable est�tica autogenerada que
     * aloja el total de eventuales en la clase.
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @return int con el total
     */
    public static int getTotalEventuales() {
        return totalEventualesEnLaEmpresa;
    }

    /**
     * M�todo que permite inicializar a 0 el total de eventuales creados por el
     * constructor. �til para la creaci�n de instancias a partir de datos
     * procedentes de ficheros.
     *
     * @since version 2.0 13/04/2014 (PROG10)
     * @throws Exception
     */
    public static void inicializarEventuales() throws Exception {
        setTotalEventuales(0);
    }

    /**
     * M�todo para calcular el importe bruto de la n�mina de un trabajador
     * eventual, seg�n la f�rmula: horas * precio hora - cuota obrera
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con importe bruto
     */
    @Override
    public double calcularImporteBrutoNomina() {
        return Integer.parseInt(this.getNumeroDeHoras()) * Double.parseDouble(getPrecioHora()) - Double.parseDouble(getCuotaObrera());
    }

    /**
     * M�todo para calcular el finiquito de un trabajador eventual, seg�n la
     * f�rmula: (importe bruto * antiguedad / 100) - cuota obrera
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con finiquito calculado
     */
    @Override
    public double calcularFiniquito() {
        //usamos el m�todo de la superclase, m�s el c�lculo especif�co.
        return super.calcularFiniquito() - Double.parseDouble(getCuotaObrera());
    }

    /**
     * M�todo para obtener un String con los datos apropiados para construir Un
     * archivo tipo texto con datos del trabajador y algunos de n�mina. El
     * formato que debe resultar ha sdo facilitado por IES Aguadulce para la
     * Tarea 8 de PROG.
     *
     * @since version 1.4 02/03/2014 (PROG08)
     * @return String formateado con cabecesa y campos comunes a la clase.
     */
    @Override
    public String obtenerStringNomina() {
        return super.obtenerStringNomina() + "\n\n\n\n\n\nN� de horas:\t\t\t\t\t\t"
                + ((TrabajadorEventual) this).getNumeroDeHoras() + "\nPrecio hora: \t\t\t\t\t\t"
                + TrabajadorEventual.getPrecioHora() + "\nCuota obrera: \t\t\t\t\t\t"
                + TrabajadorEventual.getCuotaObrera() + "\nImporte Bruto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteBrutoNomina()) + "\nRetenciones: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularRetencionesNomina()) + "\n\nImporte Neto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteNetoNomina()) + "\f";
    }

    /**
     * M�todo para obtener una cadena con todos los dato organizados para un
     * trabajador eventual
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con todos los datos.
     */
    @Override
    public String toString() {
        //usaremos el m�todo de la superclase, m�s los atributos espec�ficos.
        return super.toString() + "\n  N� de horas: " + this.getNumeroDeHoras()
                + ". N� de colaboraciones: " + this.getNumeroColaboraciones();

    }

    /**
     * M�todo de env�o de mail que se encarga de poner en circulaci�n un mensaje
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
