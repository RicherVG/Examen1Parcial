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
        tarjeta.setLayout(new BorderLayout(10, 10));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(280, 380));
        tarjeta.setMaximumSize(new Dimension(280, 380));

        // Panel para la imagen
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(Color.WHITE);
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(250, 200));
        lblImagen.setMinimumSize(new Dimension(250, 200));
        lblImagen.setMaximumSize(new Dimension(250, 200));
        lblImagen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        lblImagen.setBackground(new Color(245, 245, 245));
        lblImagen.setOpaque(true);
        
        // Cargar imagen
        ImageIcon imagenOriginal = item.getImagen();
        if (imagenOriginal != null) {
            try {
                Image img = imagenOriginal.getImage();
                if (img != null) {
                    // Escalar manteniendo proporción
                    int ancho = imagenOriginal.getIconWidth();
                    int alto = imagenOriginal.getIconHeight();
                    if (ancho > 0 && alto > 0) {
                        double escala = Math.min(240.0 / ancho, 190.0 / alto);
                        int nuevoAncho = (int) (ancho * escala);
                        int nuevoAlto = (int) (alto * escala);
                        Image imgEscalada = img.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                        lblImagen.setIcon(new ImageIcon(imgEscalada));
                    } else {
                        mostrarSinImagen(lblImagen);
                    }
                } else {
                    mostrarSinImagen(lblImagen);
                }
            } catch (Exception e) {
                mostrarSinImagen(lblImagen);
            }
        } else {
            mostrarSinImagen(lblImagen);
        }
        
        panelImagen.add(lblImagen, BorderLayout.CENTER);
        
        // Panel para la información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Nombre
        JLabel lblNombre = new JLabel("<html><center><b>" + item.getNombre() + "</b></center></html>");
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(new Color(50, 50, 50));
        
        // Estado (solo para Movie)
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            String estado = movie.getEstado();
            JLabel lblEstado = new JLabel("<html><center>Estado: <b>" + estado + "</b></center></html>");
            lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
            if (estado.equals("ESTRENO")) {
                lblEstado.setForeground(new Color(220, 20, 60)); // Rojo para estreno
            } else {
                lblEstado.setForeground(new Color(30, 144, 255)); // Azul para normal
            }
            panelInfo.add(lblEstado);
            panelInfo.add(Box.createVerticalStrut(8));
        }
        
        // Precio de renta
        JLabel lblPrecio = new JLabel("<html><center>Precio: <b>L." + String.format("%.2f", item.getPrecioBase()) + "/día</b></center></html>");
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPrecio.setForeground(new Color(0, 100, 0));
        
        // Tipo
        String tipo = item instanceof Movie ? "Movie" : "Game";
        JLabel lblTipo = new JLabel("<html><center><i>" + tipo + "</i></center></html>");
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTipo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblTipo.setForeground(new Color(120, 120, 120));
        
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblPrecio);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(lblTipo);
        
        // Agregar componentes a la tarjeta
        tarjeta.add(panelImagen, BorderLayout.CENTER);
        tarjeta.add(panelInfo, BorderLayout.SOUTH);
        
        return tarjeta;
    }
    
    private void mostrarSinImagen(JLabel lblImagen) {
        lblImagen.setIcon(null);
        lblImagen.setText("<html><center>Sin<br>imagen</center></html>");
        lblImagen.setForeground(new Color(150, 150, 150));
        lblImagen.setFont(new Font("Arial", Font.ITALIC, 12));
    }
}
