package gui;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.NombrePaneles;

public class AgregarPanel extends JPanel{

    public AgregarPanel() { 
        setLayout(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar Movie/Game");
        JButton btnVolver = new JButton("Volver");

        add(btnAgregar);
        add(btnVolver);

        btnAgregar.addActionListener(e -> agregarItem());
        btnVolver.addActionListener(e ->
                ((MainFrame) SwingUtilities.getWindowAncestor(this))
                        .cambiarVista(NombrePaneles.MENU));
    }
    
    private void agregarItem() {

        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        ArrayList<RentItem> items = frame.getItems();

        String[] opciones = {"Movie", "Game"};
        String tipo = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione tipo",
                "Tipo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        String codigo = JOptionPane.showInputDialog("Código:");

        // Validar código único
        for (RentItem item : items) {
            if (item.getCodigo().equals(codigo)) {
                JOptionPane.showMessageDialog(this, "Código ya existe");
                return;
            }
        }

        String nombre = JOptionPane.showInputDialog("Nombre:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio por día:"));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);
        String rutaImagen = fileChooser.getSelectedFile().getAbsolutePath();

        if (tipo.equals("Movie")) {
            String estado = JOptionPane.showInputDialog("Estado:");
            items.add(new Movie(codigo, nombre, precio, rutaImagen, estado));
        } else {
            items.add(new Game(codigo, nombre, precio, rutaImagen));
        }

        JOptionPane.showMessageDialog(this, "Item agregado correctamente");
    }
    
}
