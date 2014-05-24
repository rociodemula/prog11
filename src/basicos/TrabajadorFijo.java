/*
 * Tarea PROG10: Unificación de tipado de atributos a String. No admite valores
 * vacíos en atributos de objeto. Por herencia, implementa el método enviarEmail().
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
    // diario de 21.51 (por tanto 2.69 por hora, de mínimo)
    // y en base a él se inicializa el valor de sueldo fijo y
    // el precio de la hora extra inicial
    private String numeroHorasExtra; //específico para cada trabajador fijo
    private static String precioHoraExtra = "2.69"; //común a todos los trabajadores fijos
    private static String sueldoFijoMinimo = "645.30"; //cómun a todos los trabajadores fijos
    //ningún trabajador fijo podrá tener un sueldo fijo menor al sueldo fijo minimo de la clase.
    private String sueldoFijo; //específico a cada trabajador. Será mayor o igual a sueldoMinimoFijo
    public static int totalFijosEnLaEmpresa = 0; //contador para fijos de la empresa

    /**
     * Primer constructor de la clase TrabajadorFijo, subclase de Trabajador.
     * Hereda muchos de los atributos de la superclase, más algunos específicos
     * que lanzaran la excepción si no están en los rangos esperados por los
     * setters correspondientes
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con teléfono validado
     * @param numeroHijos String con nº de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroHorasExtra String con el nº horas extra realizadas
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
        //añadimos los atributos específicos, de forma segura.
        this.setSueldoFijo(sueldoFijo);
        this.setNumeroHorasExtra(numeroHorasExtra);
        super.setCodigoTrabajador(++Trabajador.totalAltasTrabajador); // incrementamos directametne
        // el contador de altas, que sólo crece, nunca decrece.
        ++Trabajador.totalTrabajadoresEnLaEmpresa; //y el contador que crecerá con las altas,
        ++TrabajadorFijo.totalFijosEnLaEmpresa; //se incrementa el contador de fijos.
        // y decrecerá con las bajas.

    }

    /**
     * Segundo constructor de la clase TrabajadorFijo. Gestiona el alta de un
     * trabajador con todos los datos del constructor base, pero añadiendo un nº
     * de código concreto. Es útil para gestión de altas intermedias que se usan
     * como trabajadores-puente para alojar datos modificados. No altera los
     * contadores de trabajadores, y asigna al trabajador el código pasado como
     * parámetro. También es necesario en recuperación de datos de fichero.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param nombre String
     * @param id String con el NIF/NIE
     * @param fecha String con fecha de nacimiento en formato dd-mm-aaaa
     * @param altura String con la altura del trabajador en formato .2f
     * @param ocupacion String
     * @param telefono String con teléfono validado
     * @param numeroHijos String con nº de hijos del trabajador
     * @param ccc CuentaBancaria con ccc y NIF/NIE del titular de la cuenta
     * @param fechaContrato String con la fecha en formato dd-mm-aaaa
     * @param numeroHorasExtra String con el nº horas extra realizadas
     * @param sueldoFijo String con el sueldo fijo de la instancia concreta
     * @param email String con el mail del trabajador
     * @param codigo int con el código que se desea para el trabajador
     * @throws Exception
     */
    public TrabajadorFijo(String nombre, String id, String fecha, String altura,
            String ocupacion, String telefono, String numeroHijos, CuentaBancaria ccc,
            String fechaContrato, String email, String sueldoFijo, String numeroHorasExtra,
            int codigo) throws Exception {
        //usamos el constructor de la sperclase
        this(nombre, id, fecha, altura, ocupacion, telefono, numeroHijos, ccc,
                fechaContrato, email, sueldoFijo, numeroHorasExtra);
        //añadimos los atributos específicos, de forma segura.
        super.setCodigoTrabajador(codigo); // asignamos al trabajador el parámetro código 
        --totalAltasTrabajador; //disminuimos el total de altas de trabajadores, 
        //incrementado desde el primer constructor
        --totalTrabajadoresEnLaEmpresa; //corregimos también el contador de trabajadores,
        --totalFijosEnLaEmpresa; //y el contador de fijos.

    }

    /**
     * Getter para obtener el número de horas extra.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con el numeroHorasExtra
     */
    public String getNumeroHorasExtra() {
        return numeroHorasExtra;
    }

    /**
     * Setter para fijar el número de horas extra de un trabajador. Admite
     * cualquier cifra mayor o igual que 0, si no, lanza la excepción. No admite
     * cadenas vacías.
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
                    throw new Exception("HORAS EXTRA no válido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("HORAS EXTRA debe ser un nº entero >= 0");
            }
        } else {
            throw new Exception("Falta HORAS EXTRA");
        }
    }

    /**
     * Getter para obtener el precio de hora extra común a todos los
     * trabajadores fijos.
     *
     * @since version 1.3 15/02/2014 (PROG7)
     * @return String con el precioHoraExtra
     */
    public static String getPrecioHoraExtra() {
        return precioHoraExtra; //devolvemos el valor de la variable estática
    }

    /**
     * Setter para fijar el valor del precio de la hora extra común a todos los
     * trabajadores fijos. Admite cualquier cifra mayor que 0, si no, lanza la
     * excepción. No adminte cadenas vacías, ni que excedan la capacidad de la
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
                        //Una posible mejora para este método sería no admitir ningún valor
                        //menor a la hora de forma proporcional al valor de sueldoFijoMinimo actual
                        //dividido por 30 (días) y por 8 (horas).
                        precioHoraExtra = String.valueOf(precio); //asignamos valor a la variable estática
                    } else {
                        throw new Exception("PRECIO HORA no válido (debe ser menor que 999.99)");
                    }
                } else {
                    throw new Exception("PRECIO HORA no válido (debe ser mayor que 0)");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("PRECIO HORA debe ser un nº positivo > 0 (admite decimales)");
            }
        } else {
            throw new Exception("Falta PRECIO HORA");
        }
    }

    /**
     * Getter para obtener el sueldo fijo mínimo común a todos los trabajadores
     * fijos
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con sueldo fijo
     */
    public static String getSueldoFijoMinimo() {
        return sueldoFijoMinimo; //devolvemos el valor de la variable estática
    }

    /**
     * Setter para fijar el sueldo fijo mínimo común para todos los
     * trabajadores. Admite cualquier cifra mayor o igual al actual
     * sueldoFijoMinimo, si no, lanza la excepción. No admite cadenas vacías, ni
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
                    // No admite un sueldo mínimo menor al actual, ni mayor a la capacidad de la base de datos.
                        // el tope mínimo inicial es el SMI de 2014: 645.30 ?
                        sueldoFijoMinimo = String.valueOf(sueldoMinimo); //asignamos el nuevo valor a la variable estática
                    } else {//si excede la capacidad de la base de datos, lanzamos el error
                        throw new Exception("SUELDO FIJO MINIMO no valido (debe ser menor o igual a"
                                + " 99999.99)");
                    }
                } else {
                    throw new Exception("SUELDO FIJO MINIMO no valido (debe ser mayor o igual a "
                            + sueldoFijoMinimo + ")");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("SUELDO MINIMO FIJO tiene que ser un nº");
            }
        } else {
            throw new Exception("Falta SUELDO MINIMO FIJO");
        }
    }

    /**
     * Getter para obtener el sueldo fijo específico para un trabajador fijo
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con sueldo fijo
     */
    public String getSueldoFijo() {
        return sueldoFijo; //devolvemos el valor de la variable estática
    }

    /**
     * Setter para fijar el sueldo fijo específico para un trabajador fijo.
     * Admite cualquier cifra mayor o igual al sueldoFijoMinimo establecido para
     * la clase, si no, lanza la excepción. No admite cadenas vacías, ni que 
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
                        this.sueldoFijo = String.valueOf(nuevoSueldoFijo); //asignamos el nuevo valor a la variable estática
                    } else { //si excede la capacidad de la base de datos, lanzamos el error
                        throw new Exception("SUELDO FIJO no valido (debe ser menor \no igual a"
                                + " 99999.99)");
                    }
                } else {
                    throw new Exception("SUELDO FIJO NO VÁLIDO (debe ser mayor \no igual a "
                            + sueldoFijoMinimo + ")");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("SUELDO FIJO debe ser un nº\n (admite decimales)");
            }
        } else {
            throw new Exception("Falta SUELDO FIJO");
        }
    }

    /**
     * Método para fijar el total de trabajadores fijos actualemente en la
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
            throw new Exception("Nº FIJOS no válido");
        }
    }

    /**
     * Método para obtener el total de fijos creados por la clase.
     *
     * @since version 2.0 13/04/2014
     * @return int con el total de fijos actual
     */
    public static int getTotalFijos() {
        return totalFijosEnLaEmpresa;
    }

    /**
     * Método que permite inicializar a 0 el total de fijos en la clase. Útil en
     * creación de instancias a partir de datos de fichero.
     *
     * @since version 2.0 13/04/2014
     * @throws Exception
     */
    public static void inicializarFijos() throws Exception {
        setTotalFijos(0);
    }

    /**
     * Método para calcular el importe bruto de la nómina de in trabajador fijo.
     * Se usa la fórmula: sueldo fijo + horas extra * precio hora extra +
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
        return super.obtenerStringNomina() + "\n\n\n\n\n\nSueldo:\t\t\t\t\t\t"
                + this.getSueldoFijo() + "\nNúmero horas extra: \t\t\t\t\t"
                + ((TrabajadorFijo) this).getNumeroHorasExtra() + "\nImporte Bruto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteBrutoNomina())
                + "\nRetenciones: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularRetencionesNomina())
                + "\n\nImporte Neto: \t\t\t\t\t\t"
                + String.format("%.2f", ((Trabajador) this).calcularImporteNetoNomina()) + "\f";
    }

    /**
     * Método para obtener un String con los datos de un trabajador fijo
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return String con todos los datos
     */
    @Override
    public String toString() {
        //usamos el método de la superclase, añadiéndole el campo específico
        return super.toString() + "\n  Número de horas extra: "
                + this.getNumeroHorasExtra() + ". Sueldo fijo: "
                + this.getSueldoFijo();
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
