/*
 * Tarea PROG10: Retoqies en el tipado de la clase Persona.
 * Constructores de la clase, setters y getters, y método toString
 * Atributos y métodos básicos para la gestión de la clase.
 */
package basicos;
// Paquete que guardará las clases básicas para PROG

import java.io.Serializable;
import java.text.ParseException;

/**
 * Clase para gestión de objetos Persona, con datos básicos.
 *
 * @since version 1.0 13/12/2013 (Tarea PROG04)
 * @author Rocio de Mula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (Tarea PROG09)
 */
public class Persona implements Serializable {
    // En esta clase serán public todos los métodos ya que nos interesará su manejo
    // desde otras clases. No hay métodos estáticos. 
    // Por sugerencia del IDE hay métodos definidos como final

    // Declaramos los atributos necesarios, todos protected, para garantizar su
    // manejo sólo con los métodos de la clase/subclases, y tipo String, para facilitar
    // la gestión. De momento, evitamos el cambio a StringBuilder, que queda
    // pendiente de plantear para futuras tareas, de manera que podamos 
    // estudiar mejor las diferencias entre los métodos de una y otra clase
    // para evitar problemas de incompatibilidades con los métodos actuales.
    // Se añade el número de hijos, que será un tipo int.
    protected String nombre;
    protected String altura;
    protected String ocupacion;
    protected String telefono;
    protected String fechaNacimiento;
    protected String nifONie;
    protected String numeroDeHijos;

    /**
     * Método constructor para la clase Persona.<br>Instancia una persona sin
     * datos concretos, con: <br>nombre "Sin nombre"<br>
     * altura "0.00"<br>ocupacion "Sin determinar"<br>telefono "000000000"
     * <br>fecha de nacimiento "00/00/0000" <br> identificación "00000000Z"
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
     * Método constructor para la clase Persona.<br>Instancia una persona nueva,
     * con datos concretos:
     *
     * @since version 1.0 13/12/2013
     * @param nombre String nombre de la persona.
     * @param id String nº de documento identificativo de la persona (NIF/NIE).
     * @param fecha String fecha de nacimiento de la persona.
     * @param altura String altura de la persona en metros, con 2 decimales.
     * @param ocupacion String ocupación de la persona.
     * @param telefono String teléfono de la persona.
     * @param numeroHijos String con el nº de hijos de la persona
     * @throws java.lang.Exception en caso de datos inválidos.
     */
    public Persona(String nombre, String id, String fecha, String altura,
            String ocupacion, String telefono, String numeroHijos) throws Exception {
        // Inicializamos los atributos del objeto con los parámetros de entrada.
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
     * Método get que devuelve el nombre de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente al nombre de la persona.
     */
    public String getNombre() {
        return this.nombre; // Devuelve el atributo nombre actual.
    }

    /**
     * Método set que permite establecer el nombre de una Persona. Admite
     * cualquier cadena de caracteres alfanuméricos, sin restricciones. No
     * admite un nombre vacío (sí un null).
     *
     * @since version 1.0 13/12/2013
     * @param nombre String que se establecerá como atributo nombre.
     * @throws java.lang.Exception
     */
    public final void setNombre(String nombre) throws Exception {
        // Actualiza el atributo nombre con el parámetro de entrada.
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
     * de la creación, que la altura es válida para una personna, entre 0.00 y
     * 3.00 mts. Se controla la posible excepción en caso de que la entrada
     * sesulte no válida, pero no debe ocurrir, ya que el filtro se realiza en
     * un método validador que pedirá el dato hasta qe sea correcto.
     * No admite cadenas vacías (sí, null).
     *
     * @see basicos.Validar#validaAltura(java.lang.String)
     * @since version 1.0 13/12/2013
     * @param altura String que se establecerá la altura de la persona.
     * @throws java.lang.Exception en caso de datos inválidos, devolviendo la
     * cadena "ALTURA".
     */
    public final void setAltura(String altura) throws Exception {
        // Actualiza el atributo altura con el parámetro de entrada.
        if (!altura.isEmpty()) {
            if (Validar.validaAltura(altura)) { //Usamos otro método para validar
                this.altura = altura; // Si es conforme, la asignamos
            } else {
                throw new Exception("ALTURA no válida"); // si no, lanzamos la excepción
            }
        } else {
            throw new Exception("Falta ALTURA"); // si no, lanzamos la excepción
        }
    }

    /**
     * Getter que devuelve la ocupación de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente a la ocupación de la persona.
     */
    public String getOcupacion() {
        return this.ocupacion; // Devuelve el atributo ocupacion actual.
    }

    /**
     * Setter que permite establecer la ocupación de una Persona. Admite
     * cualquier cadena de caracteres alfanuméricos sin restricciones. No admite
     * un valor vacío, sí un null.
     *
     * @since version 1.0 13/12/2013
     * @param ocupacion String que se establecerá como ocupación de la persona.
     * @throws java.lang.Exception
     */
    public final void setOcupacion(String ocupacion) throws Exception {
        // Actualiza el atributo ocupacion con el parámetro de entrada.
        if (!ocupacion.isEmpty()) {
            this.ocupacion = ocupacion;
        } else {
            throw new Exception("Falta OCUPACION"); // si no, lanzamos la excepción
        }
    }

    /**
     * Getter que devuelve el teléfono de una Persona.
     *
     * @since version 1.0 13/12/2013
     * @return El atributo String correspondiente al teléfono de la persona.
     */
    public String getTelefono() {
        return this.telefono; // Devuelve el atributo telefono actual.
    }

    /**
     * Setter que permite establecer el telefono de una Persona. Se asegura
     * antes de la creación, que el teléfono es válido, con 9 dígitos y el 1º de
     * ellos siempre será un número entre 6 y 9. Se controla la posible
     * excepción en caso de que la entrada sesulte no válida.
     * No admite valores nulos, saltaría la excepcíon.
     *
     * @see basicos.Validar#validaTelefono(java.lang.String)
     * @since version 1.0 13/12/2013
     * @param telefono String que se establecerá como teléfono de la persona.
     * @throws java.lang.Exception en caso de datos inválidos, devolviendo la
     * cadena "TLF no válido".
     */
    public final void setTelefono(String telefono) throws Exception {
        // Actualiza el atributo telefono con el parámetro de entrada.
        if (!telefono.isEmpty()) {
            try {
                String telefonoFiltrado = Validar.filtraPatron("[6-9]{1}[0-9]{8}", telefono, true);
                if (Validar.validaTelefono(telefonoFiltrado)) { //Usamos otro método para validar
                    this.telefono = telefonoFiltrado; //Si es conforme, lo asignamos
                } else {
                    throw new Exception("TLF no válido"); //si no, lanzamos la excepción.
                }
            } catch (Exception e) {
                throw new Exception("TLF no válido"); //si no, lanzamos la excepción.
            }
        } else {
            throw new Exception("Falta TLF"); //si no, lanzamos la excepción.
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
     * caso de que la asignación no sea válida, o no esté en formato dd-mm-aaaa,
     * lanza una excepcion. No admite cadenas vacías.
     *
     * @see basicos.Validar#validaFecha(java.lang.String)
     * @since version 1.1 28/12/2013
     * @param fecha String que se establecerá como fecha de nacimiento.
     * @throws Exception en caso de que la fecha no sea válida o esté vacía.
     */
    public final void setFechaNacimiento(String fecha) throws Exception {
        // Actualiza el atributo fecha nacimiento con el parámetro de entrada.
        if (!fecha.isEmpty()) {
            try {
                boolean validado = Validar.validaFechaNacimiento(fecha);
                if (validado) { //Esta instrucción generará la excepción
                    this.fechaNacimiento = fecha; //En caso de que no la lance, la asignamos
                } else {
                    throw new Exception("FECHA no válida"); //si no, lanzamos la excepción.
                }
            } catch (ParseException e) {
                throw new Exception("FECHA NACIMIENTO debe ser en formato dd-mm-aaaa");
            }
        } else {
            throw new Exception("Falta FECHA"); //si no, lanzamos la excepción.
        }
    }

    /**
     * Getter que devuelve la identificación de una Persona, transformando las
     * letras en mayúsculas.
     *
     * @since version 1.1 28/12/2013
     * @return String La cadena correspondiente al nº de identificación.
     */
    public String getNifONie() {
        return this.nifONie.toUpperCase(); // Devuelve el atributo nif o nie.
    }

    /**
     * Setter que permite establecer la identificación de una Persona. Si no es
     * válido, arroja una excepcion con el mensaje "NIF/NIE". No admite cadenas
     * vacías.
     *
     * @see basicos.Validar#validaNifONie(java.lang.String)
     * @since version 1.1 28/12/2013
     * @param id String que se establecerá como fecha de nacimiento.
     * @throws Exception
     */
    public final void setNifONie(String id) throws Exception {
        // Actualiza el atributo nifONie con el parámetro de entrada.
        if (!id.isEmpty()) {
            if (Validar.validaNifONie(id)) { //Validamos el id con otro método
                this.nifONie = id; //Si es válido, lo asignamos
            } else {
                throw new Exception("NIF/NIE no válido");  //Si no, se lanza la excepción.
            }
        } else {
            throw new Exception("Falta NIF/NIE"); //si no, lanzamos la excepción.
        }
    }

    /**
     * Getter para obtener el nº de hijos de una persona.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @return the numeroDeHijos
     */
    public String getNumeroDeHijos() {
        return numeroDeHijos;
    }

    /**
     * Setter que establece el número de hijos de una persona. Admite cualquier
     * número entero mayor o iguale que 0, si no, lanza la excepción. No admite 
     * cadenas vacías.
     *
     * @since version 1.3 15/02/2014 (PROG07)
     * @param numeroDeHijos the numeroDeHijos to set
     * @throws java.lang.Exception Si no está en rango, salta la excepción
     */
    public final void setNumeroDeHijos(String numeroDeHijos) throws Exception {
        int hijos;
        // filtramos un posible número de hijos, siempre debe ser >=0
        if (!numeroDeHijos.isEmpty()) {
            try {
                hijos = Integer.parseInt(numeroDeHijos);
                if (hijos >= 0) {
                    this.numeroDeHijos = numeroDeHijos;
                } else // si no, lanzaremos la excepción con el mensaje.
                {
                    throw new Exception("NºHIJOS no válido");
                }
            } catch (NumberFormatException ex) {
                throw new Exception("NºHIJOS debe ser un nº entero");
            }

        } else {
            throw new Exception("Falta NºHIJOS"); //si no, lanzamos la excepción.
        }
    }

    /**
     * Método que devuelve los atributos de una persona incrustados en una
     * cadena de texto con etiquetas identificativas para cada uno de ellos.
     *
     * @since version 1.1 28/12/2013
     * @return String con etiquetas qu anteceden a cada atributo, incluyendo un
     * retorno de carro, para evitar líneas demasiado largas.
     */
    @Override //Propuesta por Netbeans para sobreescribir método clase Object
    public String toString() {
        //Devolvemos directamente el String con los atributos incrustados.
        return ("\nNombre: " + this.getNombre() + ". NIF/NIE: " + this.getNifONie()
                + ". Fecha nacimiento: " + this.getFechaNacimiento() + ".\n  Altura: "
                + this.getAltura() + ". Ocupación: " + this.getOcupacion() + ". Tel.: "
                + this.getTelefono() + ". Nº hijos: " + this.getNumeroDeHijos() + ".");
    }

}
