/*
 * PROG09: Creación y manejo de comparadores con diferentes criterios.
 */

package basicos;

import java.util.Comparator;

/**
 * Clase para comparar dos elementos clase Trabajador por código.
 * 
 * @since versión 1.5 23/03/2014
 * @author RociodeMula <rociodemula@gmail.com>
 * @version 1.5 23/03/2014 (PROG09)
 */
public class ComparadorCodigoTrabajador implements Comparator<Trabajador>{

    /**
     * Método que compara 2 objetos clase trabajador según el código de
     * trabajador. Devuelve:
     *      -1 si el código del primer parámetro es menor que el segundo
     *      +1 si el código del primer parámetro es mayor que el segundo
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
        if (trabajador1.getCodigoTrabajador() < trabajador2.getCodigoTrabajador()) {
            resultado = -1;
        } else {
            if (trabajador1.calcularImporteNetoNomina() > trabajador2.calcularImporteNetoNomina()) {
                resultado = 1;
            } else {
                resultado = 0;
            }
        }
        return resultado;
    }
    
}
