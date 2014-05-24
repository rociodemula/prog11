/*
 * Tarea PROG10: Retoqies en el tipado de la clase Persona.
 * Constructores de la clase, setters y getters, y m�todo toString
 * Atributos y m�todos b�sicos para la gesti�n de la clase.
 */
package basicos;
// Paquete que guardar� las clases b�sicas para PROG

import java.io.Serializable;
import java.text.ParseException;

/**
 * Clase para gesti�n de objetos Persona, con datos b�sicos.
 *
 * @since version 1.0 13/12/2013 (Tarea PROG04)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public class Persona implements Serializable {
    // En esta clase ser�n public todos los m�todos ya que nos interesar� su manejo
    // desde otras clases. No hay m�todos est�ticos. 
    // Por sugerencia del IDE hay m�todos definidos como final

    // Declaramos los atributos necesarios, todos protected, para garantizar su
    // manejo s�lo con los m�todos de la clase/subclases, y tipo String, para facilitar
    // la gesti�n. De momento, evitamos el cambio a StringBuilder, que queda
    // pendiente de plantear para futuras tareas, de manera que podamos 
    // estudiar mejor las diferencias entre los m�todos de una y otra clase
    // para evitar problemas de incompatibilidades con los m�todos actuales.
    // Se a�ade el n�mero de hijos, que ser� un tipo int.
    protected String nombre;
    protected String altura;
    protected String ocupacion;
    protected String telefono;
    protected String fechaNacimiento;
    protected String nifONie;
    protected String numeroDeHijos;

    /**
     * M�todo constructor para la clase Persona.<br>Instancia una persona sin
     * datos concretos, con: <br>nombre "Sin nombre"<br>
     * altura "0.00"<br>ocupacion "Sin determinar"<br>telefono "000000000"
     * <br>fecha de nacimiento "00/00/0000" <br> identificaci�n "00000000Z"
     *
     * @since version 1.0 13/12/2013
     * @throws java.lang.Exception
     */
    public Persona() throws Exception {
        // Inicializamos los atributos del objeto valores null, ya que los
        // propuestos en la tarea 4, ya no se usan.
        this.setNombre(null);
        this.setNifONie(null);
        this.setFechaNacimiento(null);
        this.setAltura(null);
        this.setOcupacion(null);
        this.setTelefono(null);
        this.setNumeroDeHijos(null);
    }

    /**
     * M�todo constructor para la clase Persona.<br>Instancia una persona nueva,
     * con datos concretos:
     *
     * @since version 1.0 13/12/2013
     * @param nombre String nombre de la persona.
     * @param id String n� de documento identificativo de la persona (NIF/NIE).
     * @param fecha String fecha de nacimiento de la persona.
     * @param altura String altura de la persona en metros, con 2 decimales.
     * @param ocupacion String ocupaci�n de la persona.
     * @param telefono String tel�fono de la persona.
     * @param numeroHijos String con el n� de hijos de la persona
     * @throws java.lang.Exception en caso de datos inv�lidos.
     */
    public Persona(String nombre, String id, String fecha, String altura,
            String ocupacion, String telefono, String numeroHijos) throws Exception {
        // Inicializamos los atributos del objeto con los par�metros de entrada.
        // Dejamos que los setters controlen todos los casos de entrada de datos
        this.setNombre(nombre);
        this.setNifONie(id);
        this.setFechaNacimiento(fecha);
        this.setAltura(altura);
        this.setOcupacion(ocupacion);
        this.setTelefono(telefono);
        this.setNumeroDeHijos(numeroHijos);

    }

    /**
     * M�todo get que devuelve el nombre de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente al nombre de la persona.
     */
    public String getNombre() {
        return this.nombre; // Devuelve el atributo nombre actual.
    }

    /**
     * M�todo set que permite establecer el nombre de una Persona. Admite
     * cualquier cadena de caracteres alfanum�ricos, sin restricciones. No
     * admite un nombre vac�o (s� un null).
     *
     * @since version 1.0 13/12/2013
     * @param nombre String que se establecer� como atributo nombre.
     * @throws java.lang.Exception
     */
    public final void setNombre(String nombre) throws Exception {
        // Actualiza el atributo nombre con el par�metro de entrada.
        if (!nombre.isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new Exception("Falta NOMBRE");
        }
    }

    /**
     * Getter que devuelve la altura de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente a la altura de la persona.
     */
    public String getAltura() {
        return this.altura; // Devuelve el atributo altura actual.
    }

    /**
     * Setter que permite establecer la altura de una Persona. Se asegura antes
     * de la creaci�n, que la altura es v�lida para una personna, entre 0.00 y
     * 3.00 mts. Se controla la posible excepci�n en caso de que la entrada
     * sesulte no v�lida, pero no debe ocurrir, ya que el filtro se realiza en
     * un m�todo validador que pedir� el dato hasta qe sea correcto.
     * No admite cadenas vac�as (s�, null).
     *
     * @see basicos.Validar#validaAltura(java.lang.String)
     * @since version 1.0 13/12/2013
     * @param altura String que se establecer� la altura de la persona.
     * @throws java.lang.Exception en caso de datos inv�lidos, devolviendo la
     * cadena "ALTURA".
     */
    public final void setAltura(String altura) throws Exception {
        // Actualiza el atributo altura con el par�metro de entrada.
        if (!altura.isEmpty()) {
            if (Validar.validaAltura(altura)) { //Usamos otro m�todo para validar
                this.altura = altura; // Si es conforme, la asignamos
            } else {
                throw new Exception("ALTURA no v�lida"); // si no, lanzamos la excepci�n
            }
        } else {
            throw new Exception("Falta ALTURA"); // si no, lanzamos la excepci�n
        }
    }

    /**
     * Getter que devuelve la ocupaci�n de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente a la ocupaci�n de la persona.
     */
    public String getOcupacion() {
        return this.ocupacion; // Devuelve el atributo ocupacion actual.
    }

    /**
     * Setter que permite establecer la ocupaci�n de una Persona. Admite
     * cualquier cadena de caracteres alfanum�ricos sin restricciones. No admite
     * un valor vac�o, s� un null.
     *
     * @since version 1.0 13/12/2013
     * @param ocupacion String que se establecer� como ocupaci�n de la persona.
     * @throws java.lang.Exception
     */
    public final void setOcupacion(String ocupacion) throws Exception {
        // Actualiza el atributo ocupacion con el par�metro de entrada.
        if (!ocupacion.isEmpty()) {
            this.ocupacion = ocupacion;
        } else {
            throw new Exception("Falta OCUPACION"); // si no, lanzamos la excepci�n
        }
    }

    /**
     * Getter que devuelve el tel�fono de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente al tel�fono de la persona.
     */
    public String getTelefono() {
        return this.telefono; // Devuelve el atributo telefono actual.
    }

    /**
     * Setter que permite establecer el telefono de una Persona. Se asegura
     * antes de la creaci�n, que el tel�fono es v�lido, con 9 d�gitos y el 1� de
     * ellos siempre ser� un n�mero entre 6 y 9. Se controla la posible
     * excepci�n en caso de que la entrada sesulte no v�lida.
     * No admite valores nulos, saltar�a la excepc�on.
     *
     * @see basicos.Validar#validaTelefono(java.lang.String)
     * @since version 1.0 13/12/2013
     * @param telefono String que se establecer� como tel�fono de la persona.
     * @throws java.lang.Exception en caso de datos inv�lidos, devolviendo la
     * cadena "TLF no v�lido".
     */
    public final void setTelefono(String telefono) throws Exception {
        // Actualiza el atributo telefono con el par�metro de entrada.
        if (!telefono.isEmpty()) {
            try {
                String telefonoFiltrado = Validar.filtraPatron("[6-9]{1}[0-9]{8}", telefono, true);
                if (Validar.validaTelefono(telefonoFiltrado)) { //Usamos otro m�todo para validar
                    this.telefono = telefonoFiltrado; //Si es conforme, lo asignamos
                } else {
                    throw new Exception("TLF no v�lido"); //si no, lanzamos la excepci�n.
                }
            } catch (Exception e) {
                throw new Exception("TLF no v�lido"); //si no, lanzamos la excepci�n.
            }
        } else {
            throw new Exception("Falta TLF"); //si no, lanzamos la excepci�n.
        }
    }

    /**
     * Getter que devuelve la fecha de nacimiento de una Persona.
     *
     * @since version 1.1 28/12/2013
     * @return String El atributo correspondiente a la fechade nacimiento.
     */
    public String getFechaNacimiento() {
        return this.fechaNacimiento; // Devuelve el atributo fecha de nacimiento.
    }

    /**
     * Setter que permite establecer la fecha de nacimiento de una Persona. En
     * caso de que la asignaci�n no sea v�lida, o no est� en formato dd-mm-aaaa,
     * lanza una excepcion. No admite cadenas vac�as.
     *
     * @see basicos.Validar#validaFecha(java.lang.String)
     * @since version 1.1 28/12/2013
     * @param fecha String que se establecer� como fecha de nacimiento.
     * @throws Exception en caso de que la fecha no sea v�lida o est� vac�a.
     */
    public final void setFechaNacimiento(String fecha) throws Exception {
        // Actualiza el atributo fecha nacimiento con el par�metro de entrada.
        if (!fecha.isEmpty()) {
            try {
                boolean validado = Validar.validaFechaNacimiento(fecha);
                if (validado) { //Esta instrucci�n generar� la excepci�n
                    this.fechaNacimiento = fecha; //En caso de que no la lance, la asignamos
                } else {
                    throw new Exception("FECHA no v�lida"); //si no, lanzamos la excepci�n.
                }
            } catch (ParseException e) {
                throw new Exception("FECHA NACIMIENTO debe ser en formato dd-mm-aaaa");
            }
        } else {
            throw new Exception("Falta FECHA"); //si no, lanzamos la excepci�n.
        }
    }

    /**
     * Getter que devuelve la identificaci�n de una Persona, transformando las
     * letras en may�sculas.
     *
     * @since version 1.1 28/12/2013
     * @return String La cadena correspondiente al n� de identificaci�n.
     */
    public String getNifONie() {
        return this.nifONie.toUpperCase(); // Devuelve el atributo nif o nie.
    }

    /**
     * Setter que permite establecer la identificaci�n de una Persona. Si no es
     * v�lido, arroja una excepcion con el mensaje "NIF/NIE". No admite cadenas
     * vac�as.
     *
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.1 28/12/2013
     * @param id String que se establecer� como fecha de nacimiento.
     * @throws Exception
     */
    public final void setNifONie(String id) throws Exception {
        // Actualiza el atributo nifONie con el par�metro de entrada.
        if (!id.isEmpty()) {
            if (Validar.validaNifONie(id)) { //Validamos el id con otro m�todo
                this.nifONie = id; //Si es v�lido, lo asignamos
            } else {
                throw new Exception("NIF/NIE no v�lido");  //Si no, se lanza la excepci�n.
            }
        } else {
            throw new Exception("Falta NIF/NIE"); //si no, lanzamos la excepci�n.
        }
    }

    /**
     * Getter para obtener el n� de hijos de una persona.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return the numeroDeHijos
     */
    public String getNumeroDeHijos() {
        return numeroDeHijos;
    }

    /**
     * Setter que establece el n�mero de hijos de una persona. Admite cualquier
     * n�mero entero mayor o iguale que 0, si no, lanza la excepci�n. No admite 
     * cadenas vac�as.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param numeroDeHijos the numeroDeHijos to set
     * @throws java.lang.Exception Si no est� en rango, salta la excepci�n
     */
    public final void setNumeroDeHijos(String numeroDeHijos) throws Exception {
        int hijos;
        // filtramos un posible n�mero de hijos, siempre debe ser >=0
        if (!numeroDeHijos.isEmpty()) {
            try {
                hijos = Integer.parseInt(numeroDeHijos);
                if (hijos >= 0) {
                    this.numeroDeHijos = numeroDeHijos;
                } else // si no, lanzaremos la excepci�n con el mensaje.
                {
                    throw new Exception("N�HIJOS no v�lido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("N�HIJOS debe ser un n� entero");
            }

        } else {
            throw new Exception("Falta N�HIJOS"); //si no, lanzamos la excepci�n.
        }
    }

    /**
     * M�todo que devuelve los atributos de una persona incrustados en una
     * cadena de texto con etiquetas identificativas para cada uno de ellos.
     *
     * @since version 1.1 28/12/2013
     * @return String con etiquetas qu anteceden a cada atributo, incluyendo un
     * retorno de carro, para evitar l�neas demasiado largas.
     */
    @Override //Propuesta por Netbeans para sobreescribir m�todo clase Object
    public String toString() {
        //Devolvemos directamente el String con los atributos incrustados.
        return ("\nNombre: " + this.getNombre() + ". NIF/NIE: " + this.getNifONie()
                + ". Fecha nacimiento: " + this.getFechaNacimiento() + ".\n  Altura: "
                + this.getAltura() + ". Ocupaci�n: " + this.getOcupacion() + ". Tel.: "
                + this.getTelefono() + ". N� hijos: " + this.getNumeroDeHijos() + ".");
    }

}
