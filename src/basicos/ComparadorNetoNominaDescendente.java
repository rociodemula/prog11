/*
 * PROG09: Creación y manejo de comparadores con diferentes criterios.
 */

package basicos;

import java.util.Comparator;

/**
 * Clase para comparar 2 objetos clase Trabajador según el importe neto de la
 * nómina de forma descendente.
 * 
 * @since versión 1.5 23/03/2014
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (PROG09)
*/
public class ComparadorNetoNominaDescendente implements Comparator<Trabajador> {

    /**
     * Método que compara 2 objetos clase trabajador según el importe neto de la
     * nómina, de forma descendente. Devuelve:
     *      -1 si la nómina del primer parámetro es mayor que el segundo
     *      +1 si el nómina del primer parámetro es menor que el segundo
     *       0 si son iguales.
     * 
     * @since versión 1.5 23/03/2014
     * @param trabajador1 objeto clase Trabajador
     * @param trabajador2 objeto clase Trabajador
     * @return int con el resultado de la comparación.
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
