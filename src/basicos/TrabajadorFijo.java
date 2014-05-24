/*
 * Tarea PROG10: Unificaci�n de tipado de atributos a String. No admite valores
 * vac�os en atributos de objeto. Por herencia, implementa el m�todo enviarEmail().
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
 * Clase para gestion de objetos TrabajadorFijo, subclase de Trabajador
 *
 * @since version 1.3 15/02/2014 (Tarea PROG07)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public class TrabajadorFijo extends Trabajador implements Serializable {

    //definimos los atributos d la clase, con su tipo y modificadores 
    // que se han considerado apropiados. 
    // Consideramos las cifras en base al SMI 2014 mensual 645,30 y 
    // diario de 21.51 (por tanto 2.69 por hora, de m�nimo)
    // y en base a �l se inicializa el valor de sueldo fijo y
    // el precio de la hora extra inicial
    private String numeroHorasExtra; //espec�fico para cada trabajador fijo
    private static String precioHoraExtra = "2.69"; //com�n a todos los trabajadores fijos
    private static String sueldoFijoMinimo = "645.30"; //c�mun a todos los trabajadores fijos
    //ning�n trabajador fijo podr� tener un sueldo fijo menor al sueldo fijo minimo de la clase.
    private String sueldoFijo; //espec�fico a cada trabajador. Ser� mayor o igual a sueldoMinimoFijo
    public static int totalFijosEnLaEmpresa = 0; //contador para fijos de la empresa

    /**
     * Primer constructor de la clase TrabajadorFijo, subclase de Trabajador.
     * Hereda muchos de los atributos de la superclase, m�s algunos espec�ficos
     * que lanzaran la excepci�n si no est�n en los rangos esperados por los
     * setters correspondientes
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con tel�fono validado
     * @param numeroHijos String con n� de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroHorasExtra String con el n� horas extra realizadas
     * @param sueldoFijo String con el sueldo fijo de la instancia concreta
     * @param email String con el mail del trabajador
     * @throws Exception
     */
    public TrabajadorFijo(String nombre, String id, String fecha, String altura,
            String ocupacion, String telefono, String numeroHijos, CuentaBancaria ccc,
            String fechaContrato, String email, String sueldoFijo, String numeroHorasExtra) throws Exception {
        //usamos el constructor de la sperclase
        super(nombre, id, fecha, altura, ocupacion, telefono, numeroHijos, ccc,
                fechaContrato, email);
        //a�adimos los atributos espec�ficos, de forma segura.
        this.setSueldoFijo(sueldoFijo);
        this.setNumeroHorasExtra(numeroHorasExtra);
        super.setCodigoTrabajador(++Trabajador.totalAltasTrabajador); // incrementamos directametne
        // el contador de altas, que s�lo crece, nunca decrece.
        ++Trabajador.totalTrabajadoresEnLaEmpresa; //y el contador que crecer� con las altas,
        ++TrabajadorFijo.totalFijosEnLaEmpresa; //se incrementa el contador de fijos.
        // y decrecer� con las bajas.

    }

    /**
     * Segundo constructor de la clase TrabajadorFijo. Gestiona el alta de un
     * trabajador con todos los datos del constructor base, pero a�adiendo un n�
     * de c�digo concreto. Es �til para gesti�n de altas intermedias que se usan
     * como trabajadores-puente para alojar datos modificados. No altera los
     * contadores de trabajadores, y asigna al trabajador el c�digo pasado como
     * par�metro. Tambi�n es necesario en recuperaci�n de datos de fichero.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con tel�fono validado
     * @param numeroHijos String con n� de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroHorasExtra String con el n� horas extra realizadas
     * @param sueldoFijo String con el sueldo fijo de la instancia concreta
     * @param email String con el mail del trabajador
     * @param codigo int con el c�digo que se desea para el trabajador
     * @throws Exception
     */
    public TrabajadorFijo(String nombre, String id, String fecha, String altura,
            String ocupacion, String telefono, String numeroHijos, CuentaBancaria ccc,
            String fechaContrato, String email, String sueldoFijo, String numeroHorasExtra,
            int codigo) throws Exception {
        //usamos el constructor de la sperclase
        this(nombre, id, fecha, altura, ocupacion, telefono, numeroHijos, ccc,
                fechaContrato, email, sueldoFijo, numeroHorasExtra);
        //a�adimos los atributos espec�ficos, de forma segura.
        super.setCodigoTrabajador(codigo); // asignamos al trabajador el par�metro c�digo 
        --totalAltasTrabajador; //disminuimos el total de altas de trabajadores, 
        //incrementado desde el primer constructor
        --totalTrabajadoresEnLaEmpresa; //corregimos tambi�n el contador de trabajadores,
        --totalFijosEnLaEmpresa; //y el contador de fijos.

    }

    /**
     * Getter para obtener el n�mero de horas extra.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con el numeroHorasExtra
     */
    public String getNumeroHorasExtra() {
        return numeroHorasExtra;
    }

    /**
     * Setter para fijar el n�mero de horas extra de un trabajador. Admite
     * cualquier cifra mayor o igual que 0, si no, lanza la excepci�n. No admite
     * cadenas vac�as.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param numeroHorasExtra String con el numeroHorasExtra a fijar
     * @throws java.lang.Exception
     */
    public final void setNumeroHorasExtra(String numeroHorasExtra) throws Exception {
        if (!numeroHorasExtra.isEmpty()) {
            try {
                int numero = Integer.parseInt(numeroHorasExtra);
                if (numero >= 0) {
                    this.numeroHorasExtra = String.valueOf(numero);
                } else {
                    throw new Exception("HORAS EXTRA no v�lido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("HORAS EXTRA debe ser un n� entero >= 0");
            }
        } else {
            throw new Exception("Falta HORAS EXTRA");
        }
    }

    /**
     * Getter para obtener el precio de hora extra com�n a todos los
     * trabajadores fijos.
     *
     * @since version 1.3 15/02/2014 (PROG7)
     * @return String con el precioHoraExtra
     */
    public static String getPrecioHoraExtra() {
        return precioHoraExtra; //devolvemos el valor de la variable est�tica
    }

    /**
     * Setter para fijar el valor del precio de la hora extra com�n a todos los
     * trabajadores fijos. Admite cualquier cifra mayor que 0, si no, lanza la
     * excepci�n. No adminte cadenas vac�as, ni que excedan la capacidad de la
     * base de datos (999.99)
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param precioHora String con el precioHoraExtra a fijar
     * @throws java.lang.Exception
     */
    public static void setPrecioHoraExtra(String precioHora) throws Exception {
        if (!precioHora.isEmpty()) {
            try {
                double precio = Double.parseDouble(precioHora);
                if (precio > 0) {
                    if (precio <= 999.99) {
                        //Admite valores mayores que 0 y que no excedan la capacidad de la base de datos
                        //Una posible mejora para este m�todo ser�a no admitir ning�n valor
                        //menor a la hora de forma proporcional al valor de sueldoFijoMinimo actual
                        //dividido por 30 (d�as) y por 8 (horas).
                        precioHoraExtra = String.valueOf(precio); //asignamos valor a la variable est�tica
                    } else {
                        throw new Exception("PRECIO HORA no v�lido (debe ser menor que 999.99)");
                    }
                } else {
                    throw new Exception("PRECIO HORA no v�lido (debe ser mayor que 0)");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("PRECIO HORA debe ser un n� positivo > 0 (admite decimales)");
            }
        } else {
            throw new Exception("Falta PRECIO HORA");
        }
    }

    /**
     * Getter para obtener el sueldo fijo m�nimo com�n a todos los trabajadores
     * fijos
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con sueldo fijo
     */
    public static String getSueldoFijoMinimo() {
        return sueldoFijoMinimo; //devolvemos el valor de la variable est�tica
    }

    /**
     * Setter para fijar el sueldo fijo m�nimo com�n para todos los
     * trabajadores. Admite cualquier cifra mayor o igual al actual
     * sueldoFijoMinimo, si no, lanza la excepci�n. No admite cadenas vac�as, ni
     * que excedan la capacidad de la base de datos (99999.99).
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param sueldo String con el sueldoFijoMinimo a fijar
     * @throws java.lang.Exception
     */
    public static void setSueldoFijoMinimo(String sueldo) throws Exception {
        if (!sueldo.isEmpty()) {
            try {
                double sueldoMinimo = Double.parseDouble(sueldo);
                if (sueldoMinimo >= Double.parseDouble(sueldoFijoMinimo)) {
                    if (sueldoMinimo <= 99999.99) {
                    // No admite un sueldo m�nimo menor al actual, ni mayor a la capacidad de la base de datos.
                        // el tope m�nimo inicial es el SMI de 2014: 645.30 ?
                        sueldoFijoMinimo = String.valueOf(sueldoMinimo); //asignamos el nuevo valor a la variable est�tica
                    } else {//si excede la capacidad de la base de datos, lanzamos el error
                        throw new Exception("SUELDO FIJO MINIMO no valido (debe ser menor o igual a"
                                + " 99999.99)");
                    }
                } else {
                    throw new Exception("SUELDO FIJO MINIMO no valido (debe ser mayor o igual a "
                            + sueldoFijoMinimo + ")");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("SUELDO MINIMO FIJO tiene que ser un n�");
            }
        } else {
            throw new Exception("Falta SUELDO MINIMO FIJO");
        }
    }

    /**
     * Getter para obtener el sueldo fijo espec�fico para un trabajador fijo
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con sueldo fijo
     */
    public String getSueldoFijo() {
        return sueldoFijo; //devolvemos el valor de la variable est�tica
    }

    /**
     * Setter para fijar el sueldo fijo espec�fico para un trabajador fijo.
     * Admite cualquier cifra mayor o igual al sueldoFijoMinimo establecido para
     * la clase, si no, lanza la excepci�n. No admite cadenas vac�as, ni que 
     * excedan la capacidad de la base de datos.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param sueldo String con el sueldoFijoMinimo a fijar
     * @throws java.lang.Exception
     */
    public final void setSueldoFijo(String sueldo) throws Exception {
        if (!sueldo.isEmpty()) {
            try {
                double nuevoSueldoFijo = Double.parseDouble(sueldo);
                if (nuevoSueldoFijo >= Double.parseDouble(sueldoFijoMinimo)) {
                    if (nuevoSueldoFijo <= 99999.99) {
                        // No admitir un sueldo menor al sueldoMinimoFijo, ni mayor a la capacidad de la base de datos
                        this.sueldoFijo = String.valueOf(nuevoSueldoFijo); //asignamos el nuevo valor a la variable est�tica
                    } else { //si excede la capacidad de la base de datos, lanzamos el error
                        throw new Exception("SUELDO FIJO no valido (debe ser menor \no igual a"
                                + " 99999.99)");
                    }
                } else {
                    throw new Exception("SUELDO FIJO NO V�LIDO (debe ser mayor \no igual a "
                            + sueldoFijoMinimo + ")");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("SUELDO FIJO debe ser un n�\n (admite decimales)");
            }
        } else {
            throw new Exception("Falta SUELDO FIJO");
        }
    }

    /**
     * M�todo para fijar el total de trabajadores fijos actualemente en la
     * clase. No admite valores menores a 0.
     *
     * @since version 2.0 13/04/2014
     * @param totalFijos nuevo total a fijar
     * @throws Exception
     */
    public static void setTotalFijos(int totalFijos) throws Exception {
        if (totalFijos >= 0) {
            totalFijosEnLaEmpresa = totalFijos;
        } else {
            throw new Exception("N� FIJOS no v�lido");
        }
    }

    /**
     * M�todo para obtener el total de fijos creados por la clase.
     *
     * @since version 2.0 13/04/2014
     * @return int con el total de fijos actual
     */
    public static int getTotalFijos() {
        return totalFijosEnLaEmpresa;
    }

    /**
     * M�todo que permite inicializar a 0 el total de fijos en la clase. �til en
     * creaci�n de instancias a partir de datos de fichero.
     *
     * @since version 2.0 13/04/2014
     * @throws Exception
     */
    public static void inicializarFijos() throws Exception {
        setTotalFijos(0);
    }

    /**
     * M�todo para calcular el importe bruto de la n�mina de in trabajador fijo.
     * Se usa la f�rmula: sueldo fijo + horas extra * precio hora extra +
     * antiguedad * 5
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return double con importe bruto calculado
     */
    @Override
    public double calcularImporteBrutoNomina() {
        return Double.parseDouble(this.getSueldoFijo()) + Integer.parseInt(this.getNumeroHorasExtra())
                * Double.parseDouble(getPrecioHoraExtra()) + this.getAntiguedad() * 5;

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
        return super.obtenerStringNomina() + "\n\n\n\n\n\nSueldo:\t\t\t\t\t\t"
                + this.getSueldoFijo() + "\nN�mero horas extra: \t\t\t\t\t"
                + ((TrabajadorFijo) this).getNumeroHorasExtra() + "\nImporte Bruto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteBrutoNomina())
                + "\nRetenciones: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularRetencionesNomina())
                + "\n\nImporte Neto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteNetoNomina()) + "\f";
    }

    /**
     * M�todo para obtener un String con los datos de un trabajador fijo
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con todos los datos
     */
    @Override
    public String toString() {
        //usamos el m�todo de la superclase, a�adi�ndole el campo espec�fico
        return super.toString() + "\n  N�mero de horas extra: "
                + this.getNumeroHorasExtra() + ". Sueldo fijo: "
                + this.getSueldoFijo();
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
