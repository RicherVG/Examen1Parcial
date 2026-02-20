package gui;

import model.Movie;
import model.NombrePaneles;
import model.RentItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ImprimirPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel contenedorTarjetas;

    public ImprimirPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con título y botón volver
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Todos los Ítems Registrados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> mainFrame.cambiarVista(NombrePaneles.MENU));
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(btnVolver, BorderLayout.EAST);

        // Panel contenedor de tarjetas
        contenedorTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        
        // JScrollPane para poder hacer scroll si hay muchos ítems
        JScrollPane scrollPane = new JScrollPane(contenedorTarjetas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Ajustamos la velocidad de scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

   
    public void cargarTarjetas() {
        contenedorTarjetas.removeAll();
        ArrayList<RentItem> items = mainFrame.getItems();

        if (items.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay ítems registrados.");
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 14));
            contenedorTarjetas.add(lblVacio);
        } else {
           
            int cols = 3;
            int rows = (int) Math.ceil((double) items.size() / cols);
            contenedorTarjetas.setLayout(new GridLayout(Math.max(rows, 1), cols, 15, 15));
            
            for (RentItem item : items) {
                contenedorTarjetas.add(crearTarjeta(item));
            }
        }
        
        contenedorTarjetas.revalidate();
        contenedorTarjetas.repaint();
    }

    private JPanel crearTarjeta(RentItem item) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(250, 300));
        tarjeta.setMaximumSize(new Dimension(250, 300));

        
        JLabel lblImagen = new JLabel();
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImagen.setPreferredSize(new Dimension(150, 150));
        lblImagen.setMinimumSize(new Dimension(150, 150));
        lblImagen.setMaximumSize(new Dimension(150, 150));
        
        ImageIcon imagenOriginal = item.getImagen();
        if (imagenOriginal != null && imagenOriginal.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            Image imgEscalada = imagenOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
        } else {
            lblImagen.setText("Sin imagen");
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagen.setVerticalAlignment(SwingConstants.CENTER);
            lblImagen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        
        JLabel lblNombre = new JLabel("Nombre: " + item.getNombre());
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        
        
        JLabel lblPrecio = new JLabel(String.format("Precio de renta: L.%.2f", item.getPrecioBase()));
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblImagen);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createVerticalStrut(5));
        
        
        if (item instanceof Movie) {
            String estado = ((Movie) item).getEstado();
            JLabel lblEstado = new JLabel("Estado: " + estado);
            lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblEstado.setForeground(estado.equals("ESTRENO") ? Color.RED : Color.BLUE);
            tarjeta.add(lblEstado);
            tarjeta.add(Box.createVerticalStrut(5));
        }
        
        tarjeta.add(lblPrecio);
        
        return tarjeta;
    }
}
