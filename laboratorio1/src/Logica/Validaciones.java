package Logica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deivi
 */
public class Validaciones {
    
    private static List<String> listaValores = new ArrayList<>();
    
    private static int contadorPulsaciones = 0;
    
    private static int numeroTiquete = 1;
    
    public static void guardar(JTextField Jtxtedad, JComboBox cbxurg, JSpinner jSpinnerasuntos, JTextField txtcantickets) {
        // Obtener los valores de los componentes
        String edad = Jtxtedad.getText();
        String urgencia = cbxurg.getSelectedItem().toString();
        int asuntos = Integer.parseInt(jSpinnerasuntos.getValue().toString());

        // Determinar la prioridad del tiquete
        String prioridad = determinarPrioridad(edad, urgencia, asuntos);

        // Generar el número de tiquete (número actual + prioridad)
        String numeroTiqueteActual = String.valueOf(numeroTiquete);
        String tiquete = numeroTiqueteActual + prioridad;

        // Añadir la información a la lista
        listaValores.add(tiquete);

        // Incrementar el contador
        contadorPulsaciones++;

        // Actualizar el JTextField con el valor del contador
        txtcantickets.setText(String.valueOf(contadorPulsaciones));

        // Incrementar el número de tiquete para el siguiente tiquete
        numeroTiquete++;
        
        //Vaciar campos
        Jtxtedad.setText("");
    }
    
    private static String determinarPrioridad(String edad, String urgencia, int asuntos) {
        // Verificar si es una persona adulta mayor
        if (Integer.parseInt(edad) >= 60) {
            return "A";
        }

        // Verificar si tiene dos o más asuntos a tratar con el banco
        if (asuntos >= 2) {
            return "C";
        }

        // Verificar la situación seleccionada en el JComboBox
        if (urgencia != null) {
            if (urgencia.equalsIgnoreCase("Embarazada") || urgencia.equalsIgnoreCase("Niño en brazos")) {
                return "B";
            }
        }

        // En cualquier otro caso
        return "D";
    }
    
}
