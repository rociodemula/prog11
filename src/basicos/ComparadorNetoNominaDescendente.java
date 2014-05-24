/*
 * PROG09: Creaci�n y manejo de comparadores con diferentes criterios.
 */

package basicos;

import java.util.Comparator;

/**
 * Clase para comparar 2 objetos clase Trabajador seg�n el importe neto de la
 * n�mina de forma descendente.
 * 
 * @since versi�n 1.5 23/03/2014
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (PROG09)
*/
public class ComparadorNetoNominaDescendente implements Comparator<Trabajador> {

    /**
     * M�todo que compara 2 objetos clase trabajador seg�n el importe neto de la
     * n�mina, de forma descendente. Devuelve:
     *      -1 si la n�mina del primer par�metro es mayor que el segundo
     *      +1 si el n�mina del primer par�metro es menor que el segundo
     *       0 si son iguales.
     * 
     * @since versi�n 1.5 23/03/2014
     * @param trabajador1 objeto clase Trabajador
     * @param trabajador2 objeto clase Trabajador
     * @return int con el resultado de la comparaci�n.
     */
    @Override
    public int compare(Trabajador trabajador1, Trabajador trabajador2) {
        int resultado;
        if (trabajador1.calcularImporteNetoNomina() > trabajador2.calcularImporteNetoNomina()) {
            resultado = -1;
        } else {
            if (trabajador1.calcularImporteNetoNomina() < trabajador2.calcularImporteNetoNomina()) {
                resultado = 1;
            } else {
                resultado = 0;
            }
        }
        return resultado;
    }

}
