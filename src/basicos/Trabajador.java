/*
 * Tarea PROG10: Retoques en el tipado de los atributos de la clase abstracta 
 * Trabajador, unificando todos a String. Previo retocado en PRO09 la encapsulación,
 * incluyendo desde PROG08 el concepto de serialización.
 * Constructores de la clase, setters, getters y método toString
 * Atributos y métodos básicos para la gestión de la clase.
 */
package basicos;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Clase para gestión de objetos Trabajador, con datos básicos.
 *
 * @since version 1.2 12/01/2014 (Tarea PROG06)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public abstract class Trabajador extends Persona implements Serializable,
        ReceptorDeMensajes {

    // Se cambia a clase abstracta y se definen subclases y métodos abstractos
    // Definimos los aributos necesarios, protected, los que usamos a través de 
    //  los métodos de la clase, y de sus subclases.
    protected CuentaBancaria ccc; //uno de clase adecuada para alojar la ccc
    protected int codigoTrabajador; // un entero para el código del trabajador
    // El IDE propone dejarla como final, ya que una vez generado, su valor no va
    // a cambiar a lo largo de la vida del objeto (es private, y tiene getter, pero
    // no setter)
    static int totalAltasTrabajador = 0; // un contador que sólo cuente el nº de altas
    // (un int estático, que será sólo el contador para el nº de objetos creados,
    // servirá sólo para asignarle el nº de trabajador a cada trabajador creado
    // independientemente de que otros se hayan dado de baja, no decrecerá)
    static int totalTrabajadoresEnLaEmpresa = 0; // y otro que sume altas y reste bajas
    // Para las variables estáticas no haremos ni getters ni setters, ya que el
    // acceso al dato se hará directamente como variable de clase, y su actualización
    // será de forma automatica, gestionada de forma pareja con otros atributos.
    protected String fechaContrato; // atributo nuevo
    protected static String fechaFundacion = "03-03-1966";
    protected String email; //atributo nuevo para enviar boletin

    /**
     * Método Constructor de la clase Trabajador. Permite la instancia de un
     * trabajador con datos concretos:
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param nombre String nombre del Trabajador.
     * @param id String NIF/NIE del trabajador.
     * @param fecha String con la fecha de nacimiento del trabajador.
     * @param altura String con la altura del trabajador.
     * @param ocupacion String con la ocupacion del trabajador.
     * @param telefono String con el teléfono de l trabajador.
     * @param numeroHijos int con nº de hijos del trabajador
     * @param ccc CuentaBancaria del trabajador, con el ccc, el NIF/NIE y el
     * nombre del titular de la cuenta.
     * @param fechaContrato String con la fecha de contratación
     * @param email String con el email del trabajador
     * @throws Exception en caso de que no haya concordancia entre los NIF/NIE.
     */
    public Trabajador(String nombre, String id, String fecha, String altura,
            String ocupacion, String telefono, String numeroHijos, CuentaBancaria ccc,
            String fechaContrato, String email) throws Exception {
        super(nombre, id, fecha, altura, ocupacion, telefono, numeroHijos);
        this.setCcc(ccc);
        this.setEmail(email);
        // En caso de que haya un problema con la concordancia de los nif/nie,
        // saltará la excepción, y las líneas siguientes, no se ejecutarán.
        //-------------------------------------------------------------------
        this.setFechaContrato(fechaContrato); //se establece la fecha de contrato
        // No tiene sentido hacer setters/getters para las variables estáticas, que
        // son de incremento automático. Serían setters sin parámetros de entrada, lo cual
        // es un poco inútil. Haremos la asignación directamente
        this.setCodigoTrabajador(0); //de momento, inicailizamos el código de trabajador a 0
        //en la subclase se ajustará.

    }

    /**
     * Método get para el atributo ccc de clase CuentaBancaria.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return CuentaBancaria con el contenido del objeto.
     */
    public CuentaBancaria getCcc() {
        return this.ccc; //devuelve el contenido del objeto.
    }

    /**
     * Método get para el código del trabajador
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return int con el nº de código del trabajador
     */
    public int getCodigoTrabajador() {
        return this.codigoTrabajador; // devuelve el código de trabajador
    }

    /**
     * Método set para establecer un nuevo código de trabajador
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @param codigoTrabajador
     * @throws Exception
     */
    public final void setCodigoTrabajador(int codigoTrabajador) throws Exception {
        if (codigoTrabajador >= 0) {
            this.codigoTrabajador = codigoTrabajador;
        } else {
            // si es menor, se lanza la excepción.
            throw new Exception("Código Trabajador no válido");
        }
    }

    /**
     * Método para obtener el total de altas de trabajadores que se han dado.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @return int con el total de altas
     */
    public static int getTotalAltasTrabajador() {
        return totalAltasTrabajador;
    }

    /**
     * Método para establecer un nuevo total de altas de trabajador. Necesario
     * para la gestión de variables estáticas almacenadas en ficheros. Si no es
     * un número mayor o igual a 0, salta la excepción.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @param total int con el nuevo total a fijar
     * @throws Exception
     */
    public static void setTotalAltasTrabajador(int total) throws Exception {
        if (total >= 0) {
            totalAltasTrabajador = total;
        } else {
            // si es menor, se lanza la excepción.
            throw new Exception("Total Altas no válido");
        }
    }

    /**
     * Método que inicializa a 0 el total de altas de trabajadores. Necesario
     * antes de volcar los datos desde el fichero.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @throws java.lang.Exception
     */
    public static final void inicializarTotalAltasTrabajador() throws Exception {
        setTotalAltasTrabajador(0);
    }

    /**
     * Método que devuelve el total de trabajadores actualmente en la empresa
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @return int con el total de trabajadores
     */
    public static int getTotalTrabajadoresEnLaEmpresa() {
        return totalTrabajadoresEnLaEmpresa;
    }

    /**
     * Método para establecer un nuevo total de trabajadores en la empresa.
     * Necesario para la gestión de variables estáticas almacenadas en ficheros.
     * Si no es un número mayor o igual a 0, salta la excepción.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @param total int con el nuevo total a fijar
     * @throws Exception
     */
    public static void setTotalTrabajadoresEnLaEmpresa(int total) throws Exception {
        if (total >= 0) {
            totalTrabajadoresEnLaEmpresa = total;
        } else {
            // si es menor, se lanza la excepción.
            throw new Exception("Total Trabajadores no válido");
        }
    }

    /**
     * Método que inicializa a 0 el total de trabajadores actual en la empresa.
     * Necesario antes de volcar los datos desde el fichero.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @throws java.lang.Exception
     */
    public static final void inicializarTotalTrabajadoresEnLaEmpresa() throws Exception {
        setTotalTrabajadoresEnLaEmpresa(0);
    }

    /**
     * Método set para el objeto clase CuentaBancaria. Sólo asigna el valor ccc
     * en caso de que el NIF/NIE del titular de la cuenta coincida con el del
     * trabajador.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param ccc CuentaBancaria con el contenido del objeto
     * @throws java.lang.Exception, en caso de que los NIF/NIE no concuerden
     */
    public final void setCcc(CuentaBancaria ccc) throws Exception {
        // Comparamos si el NIF/NIE del trabajador es el mismo que el que figura
        // como el del titular de la ccc
        if (this.getNifONie().equalsIgnoreCase(ccc.getNifONieDelTitular())) {
            this.ccc = ccc; // en caso de que sí, lo asignamos
            if (!this.getNombre().equalsIgnoreCase(this.getCcc().getNombreTitularCuenta())) {
                // si el nombre es distinto al nombre del titular, asignamos
                // con el fin de actualizar el dato, y que quede igual que
                // los datos del banco
                this.setNombre(this.getCcc().getNombreTitularCuenta());
            }
        } else {
            // si los id no concuerdan, se lanza la excepción.
            throw new Exception("NIF/NIE (no concuerda)");
        }
    }

    /**
     * Setter para el atributo email. Sólo asigna el valor en caso de que no sea
     * una cadena vacía y pase el filtro de valizaciones de email de la
     * aplicación.
     *
     * @see basicos.Validar#esEmailCorrecto(java.lang.String)
     * @since version 2.0 13/04/2014 (PROG10)
     * @param email String con el mail que se quiere
     * @throws Exception
     */
    public final void setEmail(String email) throws Exception {
        if (!email.isEmpty()) {
            if (Validar.esEmailCorrecto(email)) {
                this.email = email;
            } else {
                throw new Exception("E-MAIL no válido");
            }
        } else {
            throw new Exception("Falta E-MAIL");
        }
    }

    /**
     * Getter para el atributo email. Devuelve su valor actual
     *
     * @see basicos.Validar#esEmailCorrecto(java.lang.String)
     * @since version 2.0 13/04/2014 (PROG10)
     * @return String con el email actual.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Método para obtener la fecha de contrato del trabajador
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return the fechaContrato String con la fecha contrato en formato
     * dd-mm-aaaa
     */
    public String getFechaContrato() {
        return fechaContrato;
    }

    /**
     * Método para establecer la fecha de contrato. Se comprobará su validez, y
     * su formato dd-mm-aaaa. Si no es correcto, lanzará la excepción. No admite
     * valores vacíos.
     *
     * @see basicos.Validar#validaFecha(java.lang.String)
     * @since version 1.3 15/02/2014 (PROG07)
     * @param fechaContrato the fechaContrato to set
     * @throws java.text.ParseException
     */
    public final void setFechaContrato(String fechaContrato) throws ParseException, Exception {
        //comprobamos que la fecha es una fecha válida
        if (!fechaContrato.isEmpty()) {
            try {
                Validar.validaFecha(fechaContrato);
                if (ES.compararFecha(fechaContrato, fechaFundacion) < 0) {
                    //si es anterior, lanzamos la excepción.
                    throw new Exception("FECHA CONTRATO (anterior a fundación)");
                } else if (ES.compararFecha(fechaContrato, this.getFechaNacimiento()) < 0) {
                    //si es anterior, lanzamos la excepción.
                    throw new Exception("FECHA CONTRATO (anterior a nacimiento)");
                } else if (ES.compararFecha(fechaContrato, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1)
                        + "-" + (Calendar.getInstance().get(Calendar.YEAR))) > 0) {
                    // si es posterior, lanzamos la excepción,
                    throw new Exception("FECHA CONTRATO (posterior a actualidad)");
                } else {
                    this.fechaContrato = fechaContrato;
                }
            } catch (ParseException e) {
                throw new Exception("FECHA CONTRATO debe ser en formato dd-mm-aaaa");
            }
        } else {
            throw new Exception("Falta FECHA CONTRATO"); //si no, lanzamos la excepción.
        }
    }

    /**
     * Método para calcular la antiguedad de un trabajador en años. Hace el
     * cálculo directamente en años, restándolo de los años de la fecha del
     * sistema. Este cálculo reconoce el año de antiguedad a un trabajador desde
     * el 1 de enero de cada año, como es habitual en muchos convenios del
     * sector.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con años de antiguedad del trabajador.
     */
    public int getAntiguedad() {
        return Calendar.getInstance().get(Calendar.YEAR)
                - Integer.parseInt(this.getFechaContrato().substring(6));
    }

    /**
     * Método abstracto para calcular el importe bruto de la nómina. Se definirá
     * en cada subclae, dependiendo del cálculo apropiado para cada tipo de
     * trabajador.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return retornará un real largo.
     */
    public abstract double calcularImporteBrutoNomina();

    /**
     * Método para calcular las retenciones a aplicar en la nómina a un
     * trabajador. Se basa en la fórmula: cantidad*(10-nº hijos)/100
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return real largo con las retenciones correspondientes.
     */
    public double calcularRetencionesNomina() {
        return this.calcularImporteBrutoNomina() * (10 - Integer.parseInt(this.getNumeroDeHijos()))
                / 100.0; //forzamos división decimal, para evitar problemas
    }

    /**
     * Método para calcular el importe neto de la nómina de un trabajador. No
     * puede ser sobreescrito por ninguna subclase. Se basa en la formula:
     * importe bruto - retenciones
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return real largo con el importe neto a cobrar
     */
    public final double calcularImporteNetoNomina() {
        return this.calcularImporteBrutoNomina() - this.calcularRetencionesNomina();
    }

    /**
     * Método para calcular el finiquito de un trabajador. Se basa en la
     * fórmula: importe bruto * antiguedad / 100
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return real largo con el cálculo del finiquito
     */
    public double calcularFiniquito() {
        return this.calcularImporteBrutoNomina() * this.getAntiguedad() / 100.0;
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
    public String obtenerStringNomina() {
        return "\nEmpresa: IES Aguadulce S.A.\t\t\tNombre: "
                + this.getNombre() + ".\nDomicilio: C/ Alhambra 11\t\t\tNIF/NIE: "
                + this.getNifONie() + ".\n04720 - Aguadulce\t\t\tOcupación: "
                + this.getOcupacion() + ".\nAlmería.\t\t\t\tTeléfono: "
                + this.getTelefono() + "\n\n\t\t\t\tFecha Contrato: "
                + ((Trabajador) this).getFechaContrato() + ".\n\t\t\t\tCódigo trabajador: "
                + ((Trabajador) this).getCodigoTrabajador() + ".\n\t\t\t\tCuenta Bancaria: "
                + ((Trabajador) this).getCcc().toString();
    }

    /**
     * Método para generar un String con los datos del Trabajador, incluyendo su
     * ccc, etiquetados con su nombre de atributo.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con todos los datos del trabajador etiquetados.
     */
    @Override
    public String toString() {
        // Se utiliza el método de la superclase Persona, incluyendo una 1ª linea
        // con el nº de trabajador, y otrs datos que nos interesan aquí. 
        return ("\n\nTrabajador número " + this.getCodigoTrabajador() + "  ------------------------------------------  "
                + super.toString() + "\n  CCC: " + this.getCcc().toString() + ". Fecha de contrato: " + this.getFechaContrato()
                + ".\n  E-mail: " + this.getEmail()
                + ".\n  Base imponible: " + String.format("%.2f", this.calcularImporteBrutoNomina())
                + ". Retenciones: " + String.format("%.2f", this.calcularRetencionesNomina()) + ". Importe neto: "
                + String.format("%.2f", this.calcularImporteNetoNomina()));
    }

}
