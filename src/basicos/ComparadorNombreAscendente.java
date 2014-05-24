/*
 * PROG09: Creación y manejo de comparadores con diferentes criterios.
 */

package basicos;

import java.util.Comparator;

/**
 * Clase para comparar dos elementos clase Trabajador por nombre, alfabéticamente,
 * de forma ascendente A-Z, sin tener en cuenta mayúsculas ni minúsculas.
 * 
 * @since versión 1.5 23/03/2014
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (PROG09)
 */
public class ComparadorNombreAscendente implements Comparator<Persona>{

    /**
     * Método que compara 2 objetos clase Persona según el nombre, de forma
     * ascendente, alfabéticamente A-Z. No distingue may-min. Devuelve:
     *      -1 si el nombre del 1º parám es anterior al segundo, alfabéticamente.
     *      +1 si el nombre del 1º parám es posterior al segundo, alfabéticamente.
     *       0 si son iguales.
     *
     * @since versión 1.5 23/03/2014
     * @param persona1 objeto clase Persona
     * @param persona2 objeto clase Persona
     * @return int con el resultado de la comparación.
     */
    @Override
    public int compare(Persona persona1, Persona persona2) {
        return persona1.getNombre().compareToIgnoreCase(persona2.getNombre());
    }
    
}
