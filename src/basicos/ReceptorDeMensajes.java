/*
 * Tarea PROG10: Reconstrucción de la aplicación incorporando la capa gráfica, y
 * nueva interfaz para implementar en clases de objetos con gestión de email.
 */

package basicos;

/**
 * Interfaz para implementar en clases que precisen de gestión de email.
 *
 * @since version 2.0 13/04/2014 (PROG10)
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 2.0 13/04/2014 (PROG10)
 */
public interface ReceptorDeMensajes {
    
    
    /**
     * Método a definir en las clases que implementen este interfaz.
     * 
     * @since version 2.0 13/04/2014
     * @param usuarioRemitente String con el usuario remitente
     * @param password String con la password del remitente
     * @param asunto String con el asunto del mensaje
     * @param textoMensaje String con el cuerpo del mensaje.
     * @return String con cadena de mensaje informativo
     */
    public boolean enviarEmail(String usuarioRemitente, String password, String asunto, 
            String textoMensaje);
}
