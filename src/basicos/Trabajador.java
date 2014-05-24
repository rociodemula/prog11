/*
 * Tarea PROG10: Retoques en el tipado de los atributos de la clase abstracta 
 * Trabajador, unificando todos a String. Previo retocado en PRO09 la encapsulaci�n,
 * incluyendo desde PROG08 el concepto de serializaci�n.
 * Constructores de la clase, setters, getters y m�todo toString
 * Atributos y m�todos b�sicos para la gesti�n de la clase.
 */
package basicos;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Clase para gesti�n de objetos Trabajador, con datos b�sicos.
 *
 * @since version 1.2 12/01/2014 (Tarea PROG06)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public abstract class Trabajador extends Persona implements Serializable,
        ReceptorDeMensajes {

    // Se cambia a clase abstracta y se definen subclases y m�todos abstractos
    // Definimos los aributos necesarios, protected, los que usamos a trav�s de 
    //  los m�todos de la clase, y de sus subclases.
    protected CuentaBancaria ccc; //uno de clase adecuada para alojar la ccc
    protected int codigoTrabajador; // un entero para el c�digo del trabajador
    // El IDE propone dejarla como final, ya que una vez generado, su valor no va
    // a cambiar a lo largo de la vida del objeto (es private, y tiene getter, pero
    // no setter)
    static int totalAltasTrabajador = 0; // un contador que s�lo cuente el n� de altas
    // (un int est�tico, que ser� s�lo el contador para el n� de objetos creados,
    // servir� s�lo para asignarle el n� de trabajador a cada trabajador creado
    // independientemente de que otros se hayan dado de baja, no decrecer�)
    static int totalTrabajadoresEnLaEmpresa = 0; // y otro que sume altas y reste bajas
    // Para las variables est�ticas no haremos ni getters ni setters, ya que el
    // acceso al dato se har� directamente como variable de clase, y su actualizaci�n
    // ser� de forma automatica, gestionada de forma pareja con otros atributos.
    protected String fechaContrato; // atributo nuevo
    protected static String fechaFundacion = "03-03-1966";
    protected String email; //atributo nuevo para enviar boletin

    /**
     * M�todo Constructor de la clase Trabajador. Permite la instancia de un
     * trabajador con datos concretos:
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param nombre String nombre del Trabajador.
     * @param id String NIF/NIE del trabajador.
     * @param fecha String con la fecha de nacimiento del trabajador.
     * @param altura String con la altura del trabajador.
     * @param ocupacion String con la ocupacion del trabajador.
     * @param telefono String con el tel�fono de l trabajador.
     * @param numeroHijos int con n� de hijos del trabajador
     * @param ccc CuentaBancaria del trabajador, con el ccc, el NIF/NIE y el
     * nombre del titular de la cuenta.
     * @param fechaContrato String con la fecha de contrataci�n
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
        // saltar� la excepci�n, y las l�neas siguientes, no se ejecutar�n.
        //-------------------------------------------------------------------
        this.setFechaContrato(fechaContrato); //se establece la fecha de contrato
        // No tiene sentido hacer setters/getters para las variables est�ticas, que
        // son de incremento autom�tico. Ser�an setters sin par�metros de entrada, lo cual
        // es un poco in�til. Haremos la asignaci�n directamente
        this.setCodigoTrabajador(0); //de momento, inicailizamos el c�digo de trabajador a 0
        //en la subclase se ajustar�.

    }

    /**
     * M�todo get para el atributo ccc de clase CuentaBancaria.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return CuentaBancaria con el contenido del objeto.
     */
    public CuentaBancaria getCcc() {
        return this.ccc; //devuelve el contenido del objeto.
    }

    /**
     * M�todo get para el c�digo del trabajador
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return int con el n� de c�digo del trabajador
     */
    public int getCodigoTrabajador() {
        return this.codigoTrabajador; // devuelve el c�digo de trabajador
    }

    /**
     * M�todo set para establecer un nuevo c�digo de trabajador
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @param codigoTrabajador
     * @throws Exception
     */
    public final void setCodigoTrabajador(int codigoTrabajador) throws Exception {
        if (codigoTrabajador >= 0) {
            this.codigoTrabajador = codigoTrabajador;
        } else {
            // si es menor, se lanza la excepci�n.
            throw new Exception("C�digo Trabajador no v�lido");
        }
    }

    /**
     * M�todo para obtener el total de altas de trabajadores que se han dado.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @return int con el total de altas
     */
    public static int getTotalAltasTrabajador() {
        return totalAltasTrabajador;
    }

    /**
     * M�todo para establecer un nuevo total de altas de trabajador. Necesario
     * para la gesti�n de variables est�ticas almacenadas en ficheros. Si no es
     * un n�mero mayor o igual a 0, salta la excepci�n.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @param total int con el nuevo total a fijar
     * @throws Exception
     */
    public static void setTotalAltasTrabajador(int total) throws Exception {
        if (total >= 0) {
            totalAltasTrabajador = total;
        } else {
            // si es menor, se lanza la excepci�n.
            throw new Exception("Total Altas no v�lido");
        }
    }

    /**
     * M�todo que inicializa a 0 el total de altas de trabajadores. Necesario
     * antes de volcar los datos desde el fichero.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @throws java.lang.Exception
     */
    public static final void inicializarTotalAltasTrabajador() throws Exception {
        setTotalAltasTrabajador(0);
    }

    /**
     * M�todo que devuelve el total de trabajadores actualmente en la empresa
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @return int con el total de trabajadores
     */
    public static int getTotalTrabajadoresEnLaEmpresa() {
        return totalTrabajadoresEnLaEmpresa;
    }

    /**
     * M�todo para establecer un nuevo total de trabajadores en la empresa.
     * Necesario para la gesti�n de variables est�ticas almacenadas en ficheros.
     * Si no es un n�mero mayor o igual a 0, salta la excepci�n.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @param total int con el nuevo total a fijar
     * @throws Exception
     */
    public static void setTotalTrabajadoresEnLaEmpresa(int total) throws Exception {
        if (total >= 0) {
            totalTrabajadoresEnLaEmpresa = total;
        } else {
            // si es menor, se lanza la excepci�n.
            throw new Exception("Total Trabajadores no v�lido");
        }
    }

    /**
     * M�todo que inicializa a 0 el total de trabajadores actual en la empresa.
     * Necesario antes de volcar los datos desde el fichero.
     *
     * @since version 1.5 23/03/2014(Tarea PROG09)
     * @throws java.lang.Exception
     */
    public static final void inicializarTotalTrabajadoresEnLaEmpresa() throws Exception {
        setTotalTrabajadoresEnLaEmpresa(0);
    }

    /**
     * M�todo set para el objeto clase CuentaBancaria. S�lo asigna el valor ccc
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
            this.ccc = ccc; // en caso de que s�, lo asignamos
            if (!this.getNombre().equalsIgnoreCase(this.getCcc().getNombreTitularCuenta())) {
                // si el nombre es distinto al nombre del titular, asignamos
                // con el fin de actualizar el dato, y que quede igual que
                // los datos del banco
                this.setNombre(this.getCcc().getNombreTitularCuenta());
            }
        } else {
            // si los id no concuerdan, se lanza la excepci�n.
            throw new Exception("NIF/NIE (no concuerda)");
        }
    }

    /**
     * Setter para el atributo email. S�lo asigna el valor en caso de que no sea
     * una cadena vac�a y pase el filtro de valizaciones de email de la
     * aplicaci�n.
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
                throw new Exception("E-MAIL no v�lido");
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
     * M�todo para obtener la fecha de contrato del trabajador
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return the fechaContrato String con la fecha contrato en formato
     * dd-mm-aaaa
     */
    public String getFechaContrato() {
        return fechaContrato;
    }

    /**
     * M�todo para establecer la fecha de contrato. Se comprobar� su validez, y
     * su formato dd-mm-aaaa. Si no es correcto, lanzar� la excepci�n. No admite
     * valores vac�os.
     *
     * @see basicos.Validar#validaFecha(java.lang.String)
     * @since version 1.3 15/02/2014 (PROG07)
     * @param fechaContrato the fechaContrato to set
     * @throws java.text.ParseException
     */
    public final void setFechaContrato(String fechaContrato) throws ParseException, Exception {
        //comprobamos que la fecha es una fecha v�lida
        if (!fechaContrato.isEmpty()) {
            try {
                Validar.validaFecha(fechaContrato);
                if (ES.compararFecha(fechaContrato, fechaFundacion) < 0) {
                    //si es anterior, lanzamos la excepci�n.
                    throw new Exception("FECHA CONTRATO (anterior a fundaci�n)");
                } else if (ES.compararFecha(fechaContrato, this.getFechaNacimiento()) < 0) {
                    //si es anterior, lanzamos la excepci�n.
                    throw new Exception("FECHA CONTRATO (anterior a nacimiento)");
                } else if (ES.compararFecha(fechaContrato, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1)
                        + "-" + (Calendar.getInstance().get(Calendar.YEAR))) > 0) {
                    // si es posterior, lanzamos la excepci�n,
                    throw new Exception("FECHA CONTRATO (posterior a actualidad)");
                } else {
                    this.fechaContrato = fechaContrato;
                }
            } catch (ParseException e) {
                throw new Exception("FECHA CONTRATO debe ser en formato dd-mm-aaaa");
            }
        } else {
            throw new Exception("Falta FECHA CONTRATO"); //si no, lanzamos la excepci�n.
        }
    }

    /**
     * M�todo para calcular la antiguedad de un trabajador en a�os. Hace el
     * c�lculo directamente en a�os, rest�ndolo de los a�os de la fecha del
     * sistema. Este c�lculo reconoce el a�o de antiguedad a un trabajador desde
     * el 1 de enero de cada a�o, como es habitual en muchos convenios del
     * sector.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return int con a�os de antiguedad del trabajador.
     */
    public int getAntiguedad() {
        return Calendar.getInstance().get(Calendar.YEAR)
                - Integer.parseInt(this.getFechaContrato().substring(6));
    }

    /**
     * M�todo abstracto para calcular el importe bruto de la n�mina. Se definir�
     * en cada subclae, dependiendo del c�lculo apropiado para cada tipo de
     * trabajador.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return retornar� un real largo.
     */
    public abstract double calcularImporteBrutoNomina();

    /**
     * M�todo para calcular las retenciones a aplicar en la n�mina a un
     * trabajador. Se basa en la f�rmula: cantidad*(10-n� hijos)/100
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return real largo con las retenciones correspondientes.
     */
    public double calcularRetencionesNomina() {
        return this.calcularImporteBrutoNomina() * (10 - Integer.parseInt(this.getNumeroDeHijos()))
                / 100.0; //forzamos divisi�n decimal, para evitar problemas
    }

    /**
     * M�todo para calcular el importe neto de la n�mina de un trabajador. No
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
     * M�todo para calcular el finiquito de un trabajador. Se basa en la
     * f�rmula: importe bruto * antiguedad / 100
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return real largo con el c�lculo del finiquito
     */
    public double calcularFiniquito() {
        return this.calcularImporteBrutoNomina() * this.getAntiguedad() / 100.0;
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
    public String obtenerStringNomina() {
        return "\nEmpresa: IES Aguadulce S.A.\t\t\tNombre: "
                + this.getNombre() + ".\nDomicilio: C/ Alhambra 11\t\t\tNIF/NIE: "
                + this.getNifONie() + ".\n04720 - Aguadulce\t\t\tOcupaci�n: "
                + this.getOcupacion() + ".\nAlmer�a.\t\t\t\tTel�fono: "
                + this.getTelefono() + "\n\n\t\t\t\tFecha Contrato: "
                + ((Trabajador) this).getFechaContrato() + ".\n\t\t\t\tC�digo trabajador: "
                + ((Trabajador) this).getCodigoTrabajador() + ".\n\t\t\t\tCuenta Bancaria: "
                + ((Trabajador) this).getCcc().toString();
    }

    /**
     * M�todo para generar un String con los datos del Trabajador, incluyendo su
     * ccc, etiquetados con su nombre de atributo.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con todos los datos del trabajador etiquetados.
     */
    @Override
    public String toString() {
        // Se utiliza el m�todo de la superclase Persona, incluyendo una 1� linea
        // con el n� de trabajador, y otrs datos que nos interesan aqu�. 
        return ("\n\nTrabajador n�mero " + this.getCodigoTrabajador() + "  ------------------------------------------  "
                + super.toString() + "\n  CCC: " + this.getCcc().toString() + ". Fecha de contrato: " + this.getFechaContrato()
                + ".\n  E-mail: " + this.getEmail()
                + ".\n  Base imponible: " + String.format("%.2f", this.calcularImporteBrutoNomina())
                + ". Retenciones: " + String.format("%.2f", this.calcularRetencionesNomina()) + ". Importe neto: "
                + String.format("%.2f", this.calcularImporteNetoNomina()));
    }

}
