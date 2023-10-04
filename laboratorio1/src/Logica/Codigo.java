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

    public Codigo(String edad, String urgencia, int asuntos) {
        this.edad = edad;
        this.urgencia = urgencia;

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

        // Verificar si el contador de tickets llega a 15
        if (contadorPulsaciones == 15) {
            JOptionPane.showMessageDialog(null, "Se ha alcanzado el límite de 15 tickets, dirigase a cajas");
        }
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
        if (Integer.parseInt(edad) >= 65) {
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
        DefaultTableModel modelito = modelito(tblver);

        // Añadir una fila al modelo de la tabla con los datos
        modelito.addRow(new Object[]{edad, urgencia, asuntos, tiquete});
    }

    // Método para obtener el modelo de la tabla o crear uno nuevo
    private static DefaultTableModel modelito(JTable tblver) {
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

    public static void llenarTablaTiquetes(JTable tblcajas) {
        DefaultTableModel modelitoCajas = modelitoCajas(tblcajas);

        // Verificar que hay tiquetes disponibles antes de llenar la tabla
        if (listaValores.size() > 0) {
            Random random = new Random();

            for (String tiquete : listaValores) {
                int tiempoTramite = random.nextInt(24) + 2; // Entre 2 y 25 minutos
                int caja = random.nextInt(4) + 1; // Seleccionar aleatoriamente una caja de 1 a 4
                modelitoCajas.addRow(new Object[]{tiquete, "Caja " + caja, tiempoTramite + " minutos"});
            }
        }
    }

// Método para obtener el modelo de la tabla o crear uno nuevo
    private static DefaultTableModel modelitoCajas(JTable tblcajas) {
        DefaultTableModel modelitoCajas;
        if (tblcajas.getModel() instanceof DefaultTableModel) {
            modelitoCajas = (DefaultTableModel) tblcajas.getModel();
        } else {
            modelitoCajas = new DefaultTableModel();
            tblcajas.setModel(modelitoCajas);

            // Agregar las columnas a la tabla
            modelitoCajas.addColumn("Tiquete");
            modelitoCajas.addColumn("Caja");
            modelitoCajas.addColumn("Tiempo en Caja");
        }
        return modelitoCajas;
    }

    public static void mostrarResultado(JTable tblcajas, JTextArea txtResultado) {
        DefaultTableModel modelitoCajas = (DefaultTableModel) tblcajas.getModel();
        int totalClientesAtendidos = modelitoCajas.getRowCount();
        int[] clientesAtendidosPorCaja = new int[4];
        int tiempoTotalEspera = 0;

        for (int i = 0; i < totalClientesAtendidos; i++) {
            String cajaStr = modelitoCajas.getValueAt(i, 1).toString();
            int caja = Integer.parseInt(cajaStr.substring(cajaStr.length() - 1)) - 1; // Obtener el número de caja

            clientesAtendidosPorCaja[caja]++;
            tiempoTotalEspera += Integer.parseInt(modelitoCajas.getValueAt(i, 2).toString().split(" ")[0]);
        }

        // Cantidad de clientes atendidos por caja cajero
        StringBuilder resultado = new StringBuilder("Cantidad de clientes atendidos por caja cajero:\n");
        for (int i = 0; i < clientesAtendidosPorCaja.length; i++) {
            resultado.append("Caja ").append(i + 1).append(": ").append(clientesAtendidosPorCaja[i]).append(" clientes\n");
        }

        // Promedio de tiempo de espera por cajeros
        int promedioTiempoEspera = (int) tiempoTotalEspera / totalClientesAtendidos;
        resultado.append("\nPromedio de tiempo de espera por cajeros: ").append(promedioTiempoEspera).append(" minutos\n");

        // Total de clientes que entraron en el banco
        resultado.append("\nTotal de clientes que entraron en el banco: ").append(totalClientesAtendidos).append(" clientes");

        // Mostrar el resultado en el JTextArea
        txtResultado.setText(resultado.toString());
    }

    public static void editar(JTable tblver, JTextField Jtxtedad, JComboBox cbxurg, JSpinner jSpinnerasuntos) {
        // Obtener el índice de la fila seleccionada
        int filaSeleccionada = tblver.getSelectedRow();

        // Verificar si se seleccionó una fila válida
        if (filaSeleccionada >= 0) {
            // Obtener el modelo de la tabla
            DefaultTableModel modelito = (DefaultTableModel) tblver.getModel();

            // Obtener el valor de la columna que contiene el tiquete a editar
            String tiqueteAEditar = modelito.getValueAt(filaSeleccionada, 3).toString();

            // Obtener los valores actuales de la fila seleccionada
            String edadActual = Jtxtedad.getText();
            String urgenciaActual = cbxurg.getSelectedItem().toString();
            int asuntosActual = Integer.parseInt(jSpinnerasuntos.getValue().toString());

            // Actualizar los valores en la fila seleccionada del modelo de la tabla
            modelito.setValueAt(edadActual, filaSeleccionada, 0);
            modelito.setValueAt(urgenciaActual, filaSeleccionada, 1);
            modelito.setValueAt(asuntosActual, filaSeleccionada, 2);

            // Actualizar el tiquete correspondiente en el ArrayList
            String nuevoTiquete = generarTiquete(edadActual, urgenciaActual, asuntosActual);
            listaValores.set(listaValores.indexOf(tiqueteAEditar), nuevoTiquete);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila para editar.");
        }
    }

    public static void eliminar(JTable tblver) {
        int filaSeleccionada = tblver.getSelectedRow();

        // Verificar si se seleccionó una fila válida
        if (filaSeleccionada >= 0) {
            // Obtener el modelo de la tabla
            DefaultTableModel modelito = (DefaultTableModel) tblver.getModel();

            // Obtener el valor de la columna que contiene el tiquete a eliminar
            String tiqueteAEliminar = modelito.getValueAt(filaSeleccionada, 3).toString();

            // Eliminar la fila seleccionada del modelo de la tabla
            modelito.removeRow(filaSeleccionada);

            // Eliminar el tiquete del ArrayList
            listaValores.remove(tiqueteAEliminar);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
        }
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
