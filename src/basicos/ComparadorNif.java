/*
 * PROG10: Ampliaci�n de comparadores con diferentes criterios.
 */

package basicos;

import java.util.Comparator;

/**
 * Clase para comparar dos elementos clase Trabajador por NIF/NIE,
 * de forma ascendente A-Z, sin tener en cuenta may�sculas ni min�sculas.
 * 
 * @since versi�n 2.0 14/04/2014
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 2.0 14/04/2014 (PROG10)
 */
public class ComparadorNif implements Comparator<Persona>{

    /**
     * M�todo que compara 2 objetos clase Persona seg�n el NIF/NIE, de forma
     * ascendente, alfab�ticamente A-Z. No distingue may-min. Devuelve:
     *      -1 si el nombre del 1� par�m es anterior al segundo, alfab�ticamente.
     *      +1 si el nombre del 1� par�m es posterior al segundo, alfab�ticamente.
     *       0 si son iguales.
     *
     * @since versi�n 1.5 23/03/2014
     * @param persona1 objeto clase Persona
     * @param persona2 objeto clase Persona
     * @return int con el resultado de la comparaci�n.
     */
    @Override
    public int compare(Persona persona1, Persona persona2) {
        return persona1.getNifONie().compareToIgnoreCase(persona2.getNifONie());
    }
    
}
