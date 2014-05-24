/*
 * Tarea PROG09: Retoques en la encapsulaci�n de la clase CuentaBancaria
 * Gesti�n de C�digos de Cuenta Cliente y datos de titular de la cuenta.
 */
package basicos;

import java.io.Serializable;

/**
 * Clase para la gesti�n de objetos CuentaBancaria, con ccc y datos b�sicos de
 * titular de cuenta. Todos sus m�todos son public, ya que usaremos esta clase
 * desde otros paquetes
 *
 * @since version 1.2 12/01/2014(Tarea PROG06)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public class CuentaBancaria implements Serializable {

    //Clase que gestiona la creacion y gestion de CCC
    //Atributos de clase, todos private, para garantizar su gesti�n s�lo desde
    // los m�todos de la clase
    private String codigoCuentaCliente;
    private String nombreTitularCuenta;
    private String nifONieDelTitular;

    /**
     * M�todo constructor que no incluye ning�n valor para el nombr del titular.
     * En caso de que el CCC � NIF/NIE no sean v�lidos, arroja una excepci�n
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param ccc String con el CCC facilitado
     * @param id String con el NIF/NIE del titular
     * @throws Exception en caso de que el CCC o el NIF/NIE no sean v�lidos
     */
    public CuentaBancaria(String ccc, String id) throws Exception {
        // Se usan los setter para cargar los atributos.
        this.setCodigoCuentaCliente(ccc);
        this.setNifONieDelTitular(id);
        // El nombre, lo cargamos a null
        this.setNombreTitularCuenta(null);
    }

    /**
     * M�todo constructor para los 3 atributos de la clase. En caso de que el
     * CCC o el NIF/NIE no sean v�lidos, lanza la excepcion.
     *
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param ccc String con el CCC facilitado
     * @param id String con el NIF/NIE del titular
     * @param nombre String con el nombre del titular
     * @throws Exception en caso de que el CCC o el NIF/NIE no sean v�lidos
     */
    public CuentaBancaria(String ccc, String id, String nombre) throws Exception {
        //Usamos el m�todo this() para ahorrar l�neas de c�digo.
        this(ccc, id);
        //En este caso no tiene sentido usar el setter, ya que no tiene una
        //funcionalidad especial.
        this.setNombreTitularCuenta(nombre);
    }

    /**
     * M�todo constructor que permite cargar los 3 atributos, el CCC, a partir
     * de los d�gitos de entidad, sucursal, y cuenta (sin facilitar los d�gitos
     * de control), y se hace una llamada a un m�todo helper, para calcular la
     * parte de CCC que nos falta. En caso de que el el id no sea v�lido,
     * arrojar�a la excepci�n. La carga del nombre del titular ser� null
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param entidad String con los 4 d�gitos de la entidad bancaria.
     * @param sucursal String con los 4 d�gitos de la sucursal bancaria.
     * @param cuenta String con los 10 d�gitos de la cuenta bancaria.
     * @param id String con el NIF/NIE del titular de la cuenta.
     * @throws Exception
     */
    public CuentaBancaria(String entidad, String sucursal, String cuenta,
            String id) throws Exception {
        // Obtenemos los d�gitos de control que nos faltan entre los par�metros
        // con ayuda del m�todo helper que los calcula.
        this.setCodigoCuentaCliente(entidad + sucursal
                + this.getDigitosControl() + cuenta);
        this.setNifONieDelTitular(id);
        this.setNombreTitularCuenta(null);
    }

    /**
     * M�todo constructor que permite cargar los 3 atributos, el CCC, a partir
     * de los d�gitos de entidad, sucursal, y cuenta (sin facilitar los d�gitos
     * de control), y se hace una llamada a un m�todo helper, para calcular la
     * parte de CCC que nos falta. En caso de que el el id no sea v�lido,
     * arrojar�a la excepci�n. Tambien se incluye el nombre del titular
     * facilitado
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param entidad String con los 4 d�gitos de la entidad bancaria.
     * @param sucursal String con los 4 d�gitos de la sucursal bancaria.
     * @param cuenta String con los 10 d�gitos de la cuenta bancaria.
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
     * M�todo get para obtener el CCC
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el CCC
     */
    public String getCodigoCuentaCliente() {
        return this.codigoCuentaCliente;
    }

    /**
     * M�todo get para obtener el nombre del titular de la cuenta
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el nombre
     */
    public String getNombreTitularCuenta() {
        return this.nombreTitularCuenta;
    }

    /**
     * M�todo get para obtener el NIF/NIE del titilar
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el NIF/NIE
     */
    public String getNifONieDelTitular() {
        return this.nifONieDelTitular;
    }

    /**
     * M�todo get para obtener el c�digo de entidad bancaria (4 d�gitos)
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el c�digo de entidad
     */
    public String getEntidadBancaria() {
        return this.getCodigoCuentaCliente().substring(0, 4);
    }

    /**
     * M�todo get para obtener el c�digo de la sucursal bancaria(4 digitos)
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el c�dgo de sucursal
     */
    public String getSucursalBancaria() {
        return this.getCodigoCuentaCliente().substring(4, 8);
    }

    /**
     * Metodo para obtener los d�gitos de control en un String, a partir de la
     * validaci�n en un m�todo de la clase Validar.
     *
     * @see basicos.Validar#obtenerDigitosControl(java.lang.String,
     * java.lang.String, java.lang.String)
     * @since versio 1.3 15/02/2014 (PROG07)
     * @return String con los 2 d�gitos de control
     */
    public final String getDigitosControl() {
        return "" + Integer.toString(Validar.obtenerDigitosControl(this.getEntidadBancaria(),
                this.getSucursalBancaria(), this.getCuentaBancaria())[0])
                + Integer.toString(Validar.obtenerDigitosControl(this.getEntidadBancaria(),
                                this.getSucursalBancaria(), this.getCuentaBancaria())[1]);
    }

    /**
     * M�todo get para obtener el c�digo de cuenta de cliente (10 d�gitos)
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @return String con el c�digo de cuenta
     */
    public String getCuentaBancaria() {
        return this.getCodigoCuentaCliente().substring(10);
    }

    /**
     * M�todo para establecer el CCC como atributo del objeto. En caso de que no
     * sea v�lido el ccc facilitado como par�metro, arrojar�a una excepci�n.
     *
     * @see basicos.Validar#validaDigitoControl(java.lang.String)
     * @since version 1.2 12/01/2014 (PROG06)
     * @param ccc String con el ccc quq se quiere establecer
     * @throws Exception
     */
    public final void setCodigoCuentaCliente(String ccc) throws Exception {
        if (!ccc.isEmpty()) {
            String cccFiltrada = Validar.filtraPatron("[0-9]{20}", ccc, true);
            if (Validar.validaDigitoControl(cccFiltrada)) { //Validamos con otro m�todo
                this.codigoCuentaCliente = cccFiltrada; // si es ok, asignamos
            } else {
                throw new Exception("CCC no v�lida"); // si no, lanzamos la excepci�n
            }
        } else {
            throw new Exception("Falta CCC"); // si no, lanzamos la excepci�n
        }
    }

    /**
     * M�todo para establecer el nombre del titular de la cuenta. Si est� vac�o,
     * saltar�a la excepci�n.
     *
     * @since version 1.2 12/01/2014(Tarea PROG06)
     * @param nombre String con el nuevo nombre para el titular
     * @throws java.lang.Exception
     */
    public final void setNombreTitularCuenta(String nombre) throws Exception {
        //if (!nombre.isEmpty()){
        this.nombreTitularCuenta = nombre;
        //} else {
            //throw new Exception("Falta Nombre"); // si no, lanzamos la excepci�n
        //}
    }

    /**
     * M�todo para establecer el NIF/NIE del titular de la cuenta. En caso de no
     * ser un NIF/NIE v�lido, arrojar� la excepci�n.
     *
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.2 12/01/2014 (Tarea PROG06)
     * @param id String con el nueco NIF/NIE
     * @throws Exception
     */
    public final void setNifONieDelTitular(String id) throws Exception {
        if (!id.isEmpty()) {
            if (Validar.validaNifONie(id)) {  //Validamos con otro m�todo
                this.nifONieDelTitular = id; //si es ok, asignamos
            } else {
                throw new Exception("NIF/NIE no v�lido"); // si no, lanzamos la excepci�n
            }
        } else {
            throw new Exception("Falta NIF/NIE"); // si no, lanzamos la excepci�n
        }
    }

    /**
     * M�todo que obtiene una cadena con el CCC del cliente, separado con
     * guiones "-" entre el c�digo entidad, c�digo sucursal, d�gito de control,
     * y los 10 d�gitos de cuenta "0000-0000-00-0000000000"
     *
     * @since version 1.2 12/01/2014 (PROG06)
     * @return String con cadena CCC en formato "0000-0000-00-0000000000"
     */
    @Override //Propuesta por Netbeans para sobreescribir m�todo clase Object
    public String toString() {
        //Devolvemos directamente el String con los atributos incrustados.
        return (this.getEntidadBancaria() + "-"
                + this.getSucursalBancaria() + "-"
                + this.getDigitosControl() + "-"
                + this.getCuentaBancaria());
    }
}
