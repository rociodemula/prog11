/*
 * Tarea PROG09: Retoques en la encapsulación de la clase CuentaBancaria
 * Gestión de Códigos de Cuenta Cliente y datos de titular de la cuenta.
 */
package basicos;

import java.io.Serializable;

/**
 * Clase para la gestión de objetos CuentaBancaria, con ccc y datos básicos de
 * titular de cuenta. Todos sus métodos son public, ya que usaremos esta clase
 * desde otros paquetes
 *
 * @since version 1.2 12/01/2014(Tarea PROG06)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public class CuentaBancaria implements Serializable {

    //Clase que gestiona la creacion y gestion de CCC
    //Atributos de clase, todos private, para garantizar su gestión sólo desde
    // los métodos de la clase
    private String codigoCuentaCliente;
    private String nombreTitularCuenta;
    private String nifONieDelTitular;

    /**
     * Método constructor que no incluye ningún valor para el nombr del titular.
     * En caso de que el CCC ó NIF/NIE no sean válidos, arroja una excepción
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param ccc String con el CCC facilitado
     * @param id String con el NIF/NIE del titular
     * @throws Exception en caso de que el CCC o el NIF/NIE no sean válidos
     */
    public CuentaBancaria(String ccc, String id) throws Exception {
        // Se usan los setter para cargar los atributos.
        this.setCodigoCuentaCliente(ccc);
        this.setNifONieDelTitular(id);
        // El nombre, lo cargamos a null
        this.setNombreTitularCuenta(null);
    }

    /**
     * Método constructor para los 3 atributos de la clase. En caso de que el
     * CCC o el NIF/NIE no sean válidos, lanza la excepcion.
     *
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param ccc String con el CCC facilitado
     * @param id String con el NIF/NIE del titular
     * @param nombre String con el nombre del titular
     * @throws Exception en caso de que el CCC o el NIF/NIE no sean válidos
     */
    public CuentaBancaria(String ccc, String id, String nombre) throws Exception {
        //Usamos el método this() para ahorrar líneas de código.
        this(ccc, id);
        //En este caso no tiene sentido usar el setter, ya que no tiene una
        //funcionalidad especial.
        this.setNombreTitularCuenta(nombre);
    }

    /**
     * Método constructor que permite cargar los 3 atributos, el CCC, a partir
     * de los dígitos de entidad, sucursal, y cuenta (sin facilitar los dígitos
     * de control), y se hace una llamada a un método helper, para calcular la
     * parte de CCC que nos falta. En caso de que el el id no sea válido,
     * arrojaría la excepción. La carga del nombre del titular será null
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param entidad String con los 4 dígitos de la entidad bancaria.
     * @param sucursal String con los 4 dígitos de la sucursal bancaria.
     * @param cuenta String con los 10 dígitos de la cuenta bancaria.
     * @param id String con el NIF/NIE del titular de la cuenta.
     * @throws Exception
     */
    public CuentaBancaria(String entidad, String sucursal, String cuenta,
            String id) throws Exception {
        // Obtenemos los dígitos de control que nos faltan entre los parámetros
        // con ayuda del método helper que los calcula.
        this.setCodigoCuentaCliente(entidad + sucursal
                + this.getDigitosControl() + cuenta);
        this.setNifONieDelTitular(id);
        this.setNombreTitularCuenta(null);
    }

    /**
     * Método constructor que permite cargar los 3 atributos, el CCC, a partir
     * de los dígitos de entidad, sucursal, y cuenta (sin facilitar los dígitos
     * de control), y se hace una llamada a un método helper, para calcular la
     * parte de CCC que nos falta. En caso de que el el id no sea válido,
     * arrojaría la excepción. Tambien se incluye el nombre del titular
     * facilitado
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param entidad String con los 4 dígitos de la entidad bancaria.
     * @param sucursal String con los 4 dígitos de la sucursal bancaria.
     * @param cuenta String con los 10 dígitos de la cuenta bancaria.
     * @param id String con el NIF/NIE del titular de la cuenta.
     * @param nombre String con el nombre del titular
     * @throws Exception
     */
    public CuentaBancaria(String entidad, String sucursal, String cuenta,
            String id, String nombre) throws Exception {
        this(entidad, sucursal, cuenta, id);
        this.setNombreTitularCuenta(nombre);
    }

    /**
     * Método get para obtener el CCC
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el CCC
     */
    public String getCodigoCuentaCliente() {
        return this.codigoCuentaCliente;
    }

    /**
     * Método get para obtener el nombre del titular de la cuenta
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el nombre
     */
    public String getNombreTitularCuenta() {
        return this.nombreTitularCuenta;
    }

    /**
     * Método get para obtener el NIF/NIE del titilar
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el NIF/NIE
     */
    public String getNifONieDelTitular() {
        return this.nifONieDelTitular;
    }

    /**
     * Método get para obtener el código de entidad bancaria (4 dígitos)
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el código de entidad
     */
    public String getEntidadBancaria() {
        return this.getCodigoCuentaCliente().substring(0, 4);
    }

    /**
     * Método get para obtener el código de la sucursal bancaria(4 digitos)
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el códgo de sucursal
     */
    public String getSucursalBancaria() {
        return this.getCodigoCuentaCliente().substring(4, 8);
    }

    /**
     * Metodo para obtener los dígitos de control en un String, a partir de la
     * validación en un método de la clase Validar.
     *
     * @see basicos.Validar#obtenerDigitosControl(java.lang.String,
     * java.lang.String, java.lang.String)
     * @since versio 1.3 15/02/2014 (PROG07)
     * @return String con los 2 dígitos de control
     */
    public final String getDigitosControl() {
        return "" + Integer.toString(Validar.obtenerDigitosControl(this.getEntidadBancaria(),
                this.getSucursalBancaria(), this.getCuentaBancaria())[0])
                + Integer.toString(Validar.obtenerDigitosControl(this.getEntidadBancaria(),
                                this.getSucursalBancaria(), this.getCuentaBancaria())[1]);
    }

    /**
     * Método get para obtener el código de cuenta de cliente (10 dígitos)
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el código de cuenta
     */
    public String getCuentaBancaria() {
        return this.getCodigoCuentaCliente().substring(10);
    }

    /**
     * Método para establecer el CCC como atributo del objeto. En caso de que no
     * sea válido el ccc facilitado como parámetro, arrojaría una excepción.
     *
     * @see basicos.Validar#validaDigitoControl(java.lang.String)
     * @since version 1.2 12/01/2014 (PROG06)
     * @param ccc String con el ccc quq se quiere establecer
     * @throws Exception
     */
    public final void setCodigoCuentaCliente(String ccc) throws Exception {
        if (!ccc.isEmpty()) {
            String cccFiltrada = Validar.filtraPatron("[0-9]{20}", ccc, true);
            if (Validar.validaDigitoControl(cccFiltrada)) { //Validamos con otro método
                this.codigoCuentaCliente = cccFiltrada; // si es ok, asignamos
            } else {
                throw new Exception("CCC no válida"); // si no, lanzamos la excepción
            }
        } else {
            throw new Exception("Falta CCC"); // si no, lanzamos la excepción
        }
    }

    /**
     * Método para establecer el nombre del titular de la cuenta. Si está vacío,
     * saltaría la excepción.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param nombre String con el nuevo nombre para el titular
     * @throws java.lang.Exception
     */
    public final void setNombreTitularCuenta(String nombre) throws Exception {
        //if (!nombre.isEmpty()){
        this.nombreTitularCuenta = nombre;
        //} else {
            //throw new Exception("Falta Nombre"); // si no, lanzamos la excepción
        //}
    }

    /**
     * Método para establecer el NIF/NIE del titular de la cuenta. En caso de no
     * ser un NIF/NIE válido, arrojará la excepción.
     *
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param id String con el nueco NIF/NIE
     * @throws Exception
     */
    public final void setNifONieDelTitular(String id) throws Exception {
        if (!id.isEmpty()) {
            if (Validar.validaNifONie(id)) {  //Validamos con otro método
                this.nifONieDelTitular = id; //si es ok, asignamos
            } else {
                throw new Exception("NIF/NIE no válido"); // si no, lanzamos la excepción
            }
        } else {
            throw new Exception("Falta NIF/NIE"); // si no, lanzamos la excepción
        }
    }

    /**
     * Método que obtiene una cadena con el CCC del cliente, separado con
     * guiones "-" entre el código entidad, código sucursal, dígito de control,
     * y los 10 dígitos de cuenta "0000-0000-00-0000000000"
     *
     * @since version 1.2 12/01/2014 (PROG06)
     * @return String con cadena CCC en formato "0000-0000-00-0000000000"
     */
    @Override //Propuesta por Netbeans para sobreescribir método clase Object
    public String toString() {
        //Devolvemos directamente el String con los atributos incrustados.
        return (this.getEntidadBancaria() + "-"
                + this.getSucursalBancaria() + "-"
                + this.getDigitosControl() + "-"
                + this.getCuentaBancaria());
    }
}
