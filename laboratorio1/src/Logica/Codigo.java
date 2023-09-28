package Logica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 *
 * @author deivis
 */
public class Codigo {

    private static List<String> listaValores = new ArrayList<>();
    private static Map<String, Integer> prioridadTiquetes = new HashMap<>();
    private static int contadorPulsaciones = 0;
    private static int numeroTiquete = 1;

    private String edad;
    private String urgencia;
    private int asuntos;

    public Codigo(String edad, String urgencia, int asuntos) {
        this.edad = edad;
        this.urgencia = urgencia;
        this.asuntos = asuntos;
    }

    public static void guardar(JTextField Jtxtedad, JComboBox cbxurg, JSpinner jSpinnerasuntos, JTextField txtcantickets, JTable tblver) {
        // Obtener los valores de los componentes
        String edad = Jtxtedad.getText();
        String urgencia = cbxurg.getSelectedItem().toString();
        int asuntos = Integer.parseInt(jSpinnerasuntos.getValue().toString());

        // Generar el tiquete y agregar la información a la lista
        String tiquete = generarTiquete(edad, urgencia, asuntos);
        listaValores.add(tiquete);

        // Incrementar el contador de pulsaciones
        contadorPulsaciones++;

        // Actualizar el JTextField con el valor del contador
        txtcantickets.setText(String.valueOf(contadorPulsaciones));

        // Vaciar el campo de edad
        Jtxtedad.setText("");

        // Llenar la tabla con la información
        llenarTabla(edad, urgencia, asuntos, tiquete, tblver);
    }

    // Método para generar el tiquete
    private static String generarTiquete(String edad, String urgencia, int asuntos) {
        // Determinar la prioridad del tiquete
        String prioridad = determinarPrioridad(edad, urgencia, asuntos);

        // Obtener el número de tiquete para esta prioridad y aumentar en 1
        int numeroTiquete = prioridadTiquetes.getOrDefault(prioridad, 0) + 1;

        // Actualizar el mapa con la nueva cantidad de tiquetes para esta prioridad
        prioridadTiquetes.put(prioridad, numeroTiquete);

        // Devolver la prioridad junto con el número de tiquete
        return prioridad + numeroTiquete;
    }

    // Método para determinar la prioridad del tiquete
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
        if (urgencia != null && (urgencia.equalsIgnoreCase("Embarazada") || urgencia.equalsIgnoreCase("Niño en brazos"))) {
            return "B";
        }

        // En cualquier otro caso
        return "D";
    }

    // Método para llenar la tabla con la información
    public static void llenarTabla(String edad, String urgencia, int asuntos, String tiquete, JTable tblver) {
        // Crear un objeto DefaultTableModel si aún no existe
        DefaultTableModel modelito = getModel(tblver);

        // Añadir una fila al modelo de la tabla con los datos
        modelito.addRow(new Object[]{edad, urgencia, asuntos, tiquete});
    }

    // Método para obtener el modelo de la tabla o crear uno nuevo
    private static DefaultTableModel getModel(JTable tblver) {
        DefaultTableModel modelito;
        if (tblver.getModel() instanceof DefaultTableModel) {
            modelito = (DefaultTableModel) tblver.getModel();
        } else {
            modelito = new DefaultTableModel();
            tblver.setModel(modelito);

            // Agregar las columnas a la tabla (puedes personalizar los nombres de las columnas aquí)
            modelito.addColumn("Edad");
            modelito.addColumn("Condicion");
            modelito.addColumn("Catn. Transacciones");
            modelito.addColumn("Tiquete");
        }
        return modelito;
    }

    /**
     * @return the edad
     */
    public String getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * @return the urgencia
     */
    public String getUrgencia() {
        return urgencia;
    }

    /**
     * @param urgencia the urgencia to set
     */
    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

}
