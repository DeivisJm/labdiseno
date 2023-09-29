package Logica;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author deivis
 */
public class Banco {

    private static List<String> listaValores = new ArrayList<>();

    // Método para llenar la tabla con los tiquetes en tblver y tblcajas
    public static void llenarTablasConTiquetes(JTable tblver, JTable tblcajas) {
        DefaultTableModel modelVer = getModel(tblver);
        DefaultTableModel modelCajas = getModel(tblcajas);

        // Limpiar ambas tablas
        modelVer.setRowCount(0);
        modelCajas.setRowCount(0);

        // Llenar ambas tablas con los tiquetes
        for (String tiquete : listaValores) {
            modelCajas.addRow(new Object[]{tiquete});
        }
    }

    // Método para obtener el modelo de la tabla o crear uno nuevo
    private static DefaultTableModel getModel(JTable tabla) {
        DefaultTableModel model;
        if (tabla.getModel() instanceof DefaultTableModel) {
            model = (DefaultTableModel) tabla.getModel();
        } else {
            model = new DefaultTableModel();
            tabla.setModel(model);

            // Agregar las columnas a la tabla (puedes personalizar los nombres de las columnas aquí)
            model.addColumn("Tiquete");
        }
        return model;
    }

}
