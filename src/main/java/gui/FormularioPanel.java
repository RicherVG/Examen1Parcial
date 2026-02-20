package gui;

import model.Game;
import model.Movie;
import model.NombrePaneles;
import model.RentItem;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;

public class FormularioPanel extends JPanel {
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidadCopias;
    private JDateChooser dateChooser;
    private JLabel lblFecha;
    private JLabel lblPrecio;
    private JComboBox<String> cmbTipo;
    private JLabel lblImagen;
    private JLabel lblPreviewImagen;
    private JButton btnSeleccionarImagen;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private String rutaImagen;
    
    // Campos para Game - Especificaciones
    private JPanel panelEspecificaciones;
    private JTextField txtEspecificacion;
    private JButton btnAgregarEspecificacion;
    private JList<String> listaEspecificaciones;
    private DefaultListModel<String> modeloEspecificaciones;
    private JScrollPane scrollEspecificaciones;

    public FormularioPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con título y tipo
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitulo = new JLabel("Agregar Item");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblTitulo);
        
        JLabel lblTipo = new JLabel("Tipo:");
        cmbTipo = new JComboBox<>(new String[]{"Movie", "Game"});
        cmbTipo.setPreferredSize(new Dimension(120, 30));
        cmbTipo.addActionListener(e -> actualizarCamposSegunTipo());
        panelSuperior.add(Box.createHorizontalStrut(20));
        panelSuperior.add(lblTipo);
        panelSuperior.add(cmbTipo);

        // Panel central con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Código
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCodigo = new JTextField(20);
        txtCodigo.setPreferredSize(new Dimension(250, 30));
        panelFormulario.add(txtCodigo, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        txtNombre.setPreferredSize(new Dimension(250, 30));
        panelFormulario.add(txtNombre, gbc);

        // Precio (solo para Movie)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        lblPrecio = new JLabel("Precio por día:");
        panelFormulario.add(lblPrecio, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtPrecio = new JTextField(20);
        txtPrecio.setPreferredSize(new Dimension(250, 30));
        panelFormulario.add(txtPrecio, gbc);

        // Cantidad de Copias
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Cantidad de Copias:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCantidadCopias = new JTextField(20);
        txtCantidadCopias.setPreferredSize(new Dimension(250, 30));
        txtCantidadCopias.setText("0");
        panelFormulario.add(txtCantidadCopias, gbc);

        // Fecha (Estreno para Movie, Publicación para Game)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        lblFecha = new JLabel("Fecha de Estreno:");
        panelFormulario.add(lblFecha, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(250, 30));
        panelFormulario.add(dateChooser, gbc);

        // Especificaciones (solo para Game)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        JLabel lblEspecificaciones = new JLabel("Especificaciones:");
        panelFormulario.add(lblEspecificaciones, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        panelEspecificaciones = new JPanel(new BorderLayout(5, 5));
        panelEspecificaciones.setBorder(BorderFactory.createTitledBorder("Especificaciones Técnicas"));
        
        // Panel para agregar especificaciones
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtEspecificacion = new JTextField(20);
        txtEspecificacion.setPreferredSize(new Dimension(200, 25));
        btnAgregarEspecificacion = new JButton("Agregar");
        btnAgregarEspecificacion.addActionListener(e -> agregarEspecificacion());
        panelAgregar.add(txtEspecificacion);
        panelAgregar.add(btnAgregarEspecificacion);
        
        // Lista de especificaciones
        modeloEspecificaciones = new DefaultListModel<>();
        listaEspecificaciones = new JList<>(modeloEspecificaciones);
        scrollEspecificaciones = new JScrollPane(listaEspecificaciones);
        scrollEspecificaciones.setPreferredSize(new Dimension(250, 100));
        scrollEspecificaciones.setMinimumSize(new Dimension(200, 80));
        
        panelEspecificaciones.add(panelAgregar, BorderLayout.NORTH);
        panelEspecificaciones.add(scrollEspecificaciones, BorderLayout.CENTER);
        panelEspecificaciones.setVisible(false);
        panelFormulario.add(panelEspecificaciones, gbc);

        // Imagen
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Imagen:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblImagen = new JLabel("No seleccionada");
        lblImagen.setForeground(Color.GRAY);
        btnSeleccionarImagen = new JButton("Seleccionar...");
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());
        panelImagen.add(btnSeleccionarImagen);
        panelImagen.add(lblImagen);
        panelFormulario.add(panelImagen, gbc);

        // Preview de Imagen
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Preview:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        lblPreviewImagen = new JLabel();
        lblPreviewImagen.setHorizontalAlignment(JLabel.CENTER);
        lblPreviewImagen.setVerticalAlignment(JLabel.CENTER);
        lblPreviewImagen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        lblPreviewImagen.setPreferredSize(new Dimension(200, 200));
        lblPreviewImagen.setMinimumSize(new Dimension(150, 150));
        lblPreviewImagen.setBackground(Color.WHITE);
        lblPreviewImagen.setOpaque(true);
        lblPreviewImagen.setText("<html><center>No hay imagen<br>seleccionada</center></html>");
        lblPreviewImagen.setForeground(Color.GRAY);
        panelFormulario.add(lblPreviewImagen, gbc);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setBackground(new Color(158, 158, 158));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);

        btnGuardar.addActionListener(e -> guardarItem());
        btnCancelar.addActionListener(e -> cancelar());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Agregar componentes al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes", "jpg", "jpeg", "png", "gif", "bmp"));
        
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();
            lblImagen.setText(archivo.getName());
            lblImagen.setForeground(Color.BLACK);
            
            // Mostrar preview de la imagen
            mostrarPreviewImagen(archivo);
        }
    }
    
    private void mostrarPreviewImagen(File archivo) {
        try {
            BufferedImage imagenOriginal = ImageIO.read(archivo);
            if (imagenOriginal != null) {
                // Calcular el tamaño del preview (máximo 200x200 manteniendo proporción)
                int anchoOriginal = imagenOriginal.getWidth();
                int altoOriginal = imagenOriginal.getHeight();
                int anchoPreview = 200;
                int altoPreview = 200;
                
                // Calcular dimensiones manteniendo la proporción
                double escala = Math.min(
                    (double) anchoPreview / anchoOriginal,
                    (double) altoPreview / altoOriginal
                );
                
                int nuevoAncho = (int) (anchoOriginal * escala);
                int nuevoAlto = (int) (altoOriginal * escala);
                
                // Escalar la imagen
                Image imagenEscalada = imagenOriginal.getScaledInstance(
                    nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH
                );
                
                // Mostrar en el label
                lblPreviewImagen.setIcon(new ImageIcon(imagenEscalada));
                lblPreviewImagen.setText("");
            } else {
                lblPreviewImagen.setIcon(null);
                lblPreviewImagen.setText("<html><center>Error al cargar<br>la imagen</center></html>");
                lblPreviewImagen.setForeground(Color.RED);
            }
        } catch (Exception e) {
            lblPreviewImagen.setIcon(null);
            lblPreviewImagen.setText("<html><center>Error al cargar<br>la imagen</center></html>");
            lblPreviewImagen.setForeground(Color.RED);
            System.err.println("Error al cargar preview de imagen: " + e.getMessage());
        }
    }

    private void guardarItem() {
        // Validar campos
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un código", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el código sea numérico y mayor a 0
        String codigoStr = txtCodigo.getText().trim();
        int codigoNum;
        try {
            codigoNum = Integer.parseInt(codigoStr);
            if (codigoNum <= 0) {
                JOptionPane.showMessageDialog(this, "El código debe ser un número mayor a 0", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número válido mayor a 0", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un nombre", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = (String) cmbTipo.getSelectedItem();
        double precio = 0;
        
        // Validar precio solo para Movie
        if (tipo.equals("Movie")) {
            try {
                precio = Double.parseDouble(txtPrecio.getText().trim());
                if (precio <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un precio válido mayor a 0", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Validar cantidad de copias
        int cantidadCopias;
        try {
            cantidadCopias = Integer.parseInt(txtCantidadCopias.getText().trim());
            if (cantidadCopias < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad de copias debe ser mayor o igual a 0", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una cantidad de copias válida", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        ArrayList<RentItem> items = frame.getItems();

        String codigo = String.valueOf(codigoNum);

        // Validar código único
        for (RentItem item : items) {
            if (item.getCodigo().equals(codigo)) {
                JOptionPane.showMessageDialog(this, "El código ya existe", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String nombre = txtNombre.getText().trim();

        // Obtener fecha
        Date fechaSeleccionada = dateChooser.getDate();
        if (fechaSeleccionada == null) {
            String tipoSeleccionado = (String) cmbTipo.getSelectedItem();
            String mensaje = tipoSeleccionado.equals("Movie") ? "Por favor seleccione una fecha de estreno" : "Por favor seleccione una fecha de publicación";
            JOptionPane.showMessageDialog(this, mensaje, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaSeleccionada);

        String tipoSeleccionado = (String) cmbTipo.getSelectedItem();
        RentItem nuevoItem;

        // Crear Movie o Game según el tipo
        if (tipoSeleccionado.equals("Movie")) {
            Movie nuevoMovie = new Movie(codigo, nombre, precio);
            nuevoMovie.setFechaEstreno(fecha);
            nuevoMovie.setCantidadCopias(cantidadCopias);
            nuevoItem = nuevoMovie;
        } else {
            Game nuevoGame = new Game(codigo, nombre);
            nuevoGame.setFechaPublicacion(fecha);
            nuevoGame.setCantidadCopias(cantidadCopias);
            
            // Agregar especificaciones
            for (int i = 0; i < modeloEspecificaciones.getSize(); i++) {
                nuevoGame.agregarEspecificacion(modeloEspecificaciones.getElementAt(i));
            }
            
            nuevoItem = nuevoGame;
        }

        // Si hay imagen seleccionada, establecerla
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            try {
                File archivoImagen = new File(rutaImagen);
                if (archivoImagen.exists() && archivoImagen.isFile()) {
                    // Intentar cargar con ImageIO primero (mejor para formatos estándar)
                    ImageIcon imagen = null;
                    try {
                        BufferedImage bufferedImg = ImageIO.read(archivoImagen);
                        if (bufferedImg != null) {
                            imagen = new ImageIcon(bufferedImg);
                        }
                    } catch (Exception e) {
                        // Si ImageIO falla, intentar con ImageIcon directamente (para GIFs animados)
                        System.out.println("ImageIO no pudo cargar, intentando con ImageIcon directo: " + e.getMessage());
                    }
                    
                    // Si ImageIO falló, usar ImageIcon directamente
                    if (imagen == null) {
                        imagen = new ImageIcon(rutaImagen);
                    }
                    
                    // Verificar que la imagen sea válida
                    if (imagen != null && imagen.getImage() != null) {
                        // Para GIFs animados, el ImageLoadStatus puede no ser COMPLETE inmediatamente
                        // pero la imagen puede estar disponible
                        if (nuevoItem instanceof Movie) {
                            ((Movie) nuevoItem).setImagen(imagen);
                        } else if (nuevoItem instanceof Game) {
                            ((Game) nuevoItem).setImagen(imagen);
                        }
                        System.out.println("Imagen cargada exitosamente: " + rutaImagen);
                    } else {
                        System.err.println("La imagen no se pudo cargar: " + rutaImagen);
                    }
                } else {
                    System.err.println("El archivo de imagen no existe: " + rutaImagen);
                }
            } catch (Exception e) {
                // Si hay error al cargar la imagen, continuar sin ella
                System.err.println("Error al cargar la imagen: " + e.getMessage());
                e.printStackTrace();
            }
        }

        items.add(nuevoItem);
        JOptionPane.showMessageDialog(this, "Item agregado correctamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar formulario
        limpiarFormulario();
    }

    private void cancelar() {
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        frame.cambiarVista(NombrePaneles.MENU);
    }

    private void agregarEspecificacion() {
        String especificacion = txtEspecificacion.getText().trim();
        if (!especificacion.isEmpty()) {
            modeloEspecificaciones.addElement(especificacion);
            txtEspecificacion.setText("");
        }
    }

    private void actualizarCamposSegunTipo() {
        String tipo = (String) cmbTipo.getSelectedItem();
        if (tipo.equals("Movie")) {
            lblPrecio.setText("Precio por día:");
            lblPrecio.setVisible(true);
            txtPrecio.setVisible(true);
            txtPrecio.setEnabled(true);
            lblFecha.setText("Fecha de Estreno:");
            panelEspecificaciones.setVisible(false);
        } else {
            lblPrecio.setVisible(false);
            txtPrecio.setVisible(false);
            txtPrecio.setText("");
            lblFecha.setText("Fecha de Publicación:");
            panelEspecificaciones.setVisible(true);
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidadCopias.setText("0");
        dateChooser.setDate(new Date());
        cmbTipo.setSelectedIndex(0);
        modeloEspecificaciones.clear();
        txtEspecificacion.setText("");
        actualizarCamposSegunTipo();
        lblImagen.setText("No seleccionada");
        lblImagen.setForeground(Color.GRAY);
        lblPreviewImagen.setIcon(null);
        lblPreviewImagen.setText("<html><center>No hay imagen<br>seleccionada</center></html>");
        lblPreviewImagen.setForeground(Color.GRAY);
        rutaImagen = null;
    }
}
