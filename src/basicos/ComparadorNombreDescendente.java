/*
 * PROG09: Creaci�n y manejo de comparadores con diferentes criterios.
 */

package basicos;

import java.util.Comparator;

/**
 * Clase para comparar dos elementos clase Trabajador por nombre, alfab�ticamente,
 * de forma descendente Z-A, sin tener en cuenta may�sculas ni min�sculas.
 * 
 * @since versi�n 1.5 23/03/2014
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (PROG09)
 */
public class ComparadorNombreDescendente implements Comparator<Persona>{

    /**
     * M�todo que compara 2 objetos clase Persona seg�n el nombre, de forma
     * descendente, alfab�ticamente Z-A. No distingue may-min. Devuelve:
     *      -1 si el nombre del 1� par�m es posterior al segundo, alfab�ticamente.
     *      +1 si el nombre del 1� par�m es anterior al segundo, alfab�ticamente.
     *       0 si son iguales.
     *
     * @since versi�n 1.5 23/03/2014
     * @param persona1 objeto clase Persona
     * @param persona2 objeto clase Persona
     * @return int con el resultado de la comparaci�n.
     */
    @Override
    public int compare(Persona persona1, Persona persona2) {
        return (-1) * persona1.getNombre().compareToIgnoreCase(persona2.getNombre());
    }
    
}
