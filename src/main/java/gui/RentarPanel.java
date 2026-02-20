package gui;

import model.NombrePaneles;
import model.RentItem;
import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class RentarPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField txtCodigo;
    private JTextField txtDias;
    private JLabel lblInfo;
    private JLabel lblImagen;
    private JLabel lblMontoTotal;
    private JButton btnBuscar;
    private JButton btnCalcular;
    private JButton btnRentar;
    private JButton btnVolver;
    private RentItem itemSeleccionado;

    public RentarPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con título
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitulo = new JLabel("Rentar Item");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblTitulo);

        // Panel central con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Código del item
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código del Item:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCodigo = new JTextField(20);
        txtCodigo.setPreferredSize(new Dimension(200, 30));
        JPanel panelCodigo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCodigo.add(txtCodigo);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarItem());
        panelCodigo.add(btnBuscar);
        panelFormulario.add(panelCodigo, gbc);

        // Información del item
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        lblInfo = new JLabel("<html><center>Ingrese el código del item para buscar</center></html>");
        lblInfo.setHorizontalAlignment(JLabel.CENTER);
        lblInfo.setVerticalAlignment(JLabel.CENTER);
        lblInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Información del Item"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        lblInfo.setPreferredSize(new Dimension(400, 100));
        panelFormulario.add(lblInfo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.4;
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setVerticalAlignment(JLabel.CENTER);
        lblImagen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Imagen"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setMinimumSize(new Dimension(150, 150));
        lblImagen.setBackground(Color.WHITE);
        lblImagen.setOpaque(true);
        lblImagen.setText("<html><center>No hay imagen<br>disponible</center></html>");
        lblImagen.setForeground(Color.GRAY);
        panelFormulario.add(lblImagen, gbc);

        // Cantidad de días
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panelFormulario.add(new JLabel("Cantidad de días:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDias = new JTextField(10);
        txtDias.setPreferredSize(new Dimension(100, 30));
        txtDias.setEnabled(false);
        // Listener para actualizar monto automáticamente
        txtDias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                actualizarMontoAutomatico();
            }
        });
        panelFormulario.add(txtDias, gbc);

        // Monto total
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Monto Total:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        lblMontoTotal = new JLabel("L.0.00");
        lblMontoTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblMontoTotal.setForeground(new Color(0, 100, 0));
        panelFormulario.add(lblMontoTotal, gbc);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnCalcular = new JButton("Calcular Renta");
        btnCalcular.setPreferredSize(new Dimension(140, 35));
        btnCalcular.setBackground(new Color(33, 150, 243));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setEnabled(false);
        btnCalcular.addActionListener(e -> calcularRenta());

        btnRentar = new JButton("Rentar");
        btnRentar.setPreferredSize(new Dimension(120, 35));
        btnRentar.setBackground(new Color(76, 175, 80));
        btnRentar.setForeground(Color.WHITE);
        btnRentar.setFocusPainted(false);
        btnRentar.setEnabled(false);
        btnRentar.addActionListener(e -> rentarItem());

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> volver());

        panelBotones.add(btnCalcular);
        panelBotones.add(btnRentar);
        panelBotones.add(btnVolver);

        // Agregar componentes al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void buscarItem() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un código", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<RentItem> items = mainFrame.getItems();
        itemSeleccionado = null;

        // Buscar el item por código (funciona para Movie y Game)
        for (RentItem item : items) {
            if (item.getCodigo() != null && item.getCodigo().equals(codigo)) {
                itemSeleccionado = item;
                break;
            }
        }

        if (itemSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Item No Existe", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            limpiarDatos();
            return;
        }

        // Limpiar el campo de días cuando se busca otro item
        txtDias.setText("");
        lblMontoTotal.setText("L.0.00");

        // Mostrar información del item
        mostrarInformacionItem();
    }

    private void mostrarInformacionItem() {
        if (itemSeleccionado == null) return;

        // Mostrar información
        String info = String.format(
            "<html><center>" +
            "<b>Código:</b> %s<br>" +
            "<b>Nombre:</b> %s<br>" +
            "<b>Precio Base:</b> L.%.2f<br>" +
            "<b>Copias Disponibles:</b> %d<br>" +
            "<b>%s</b>" +
            "</center></html>",
            itemSeleccionado.getCodigo(),
            itemSeleccionado.getNombre(),
            itemSeleccionado.getPrecioBase(),
            itemSeleccionado.getCantidadCopias(),
            itemSeleccionado.toString()
        );
        lblInfo.setText(info);

        // Mostrar imagen
        ImageIcon imagen = itemSeleccionado.getImagen();
        if (imagen != null) {
            try {
                // Escalar imagen para el preview
                Image img = imagen.getImage();
                if (img != null) {
                    // Para GIFs animados, no escalar para mantener la animación
                    // Solo escalar si es necesario
                    int ancho = imagen.getIconWidth();
                    int alto = imagen.getIconHeight();
                    
                    if (ancho > 0 && alto > 0) {
                        // Si la imagen es muy grande, escalarla
                        if (ancho > 200 || alto > 200) {
                            double escala = Math.min(200.0 / ancho, 200.0 / alto);
                            int nuevoAncho = (int) (ancho * escala);
                            int nuevoAlto = (int) (alto * escala);
                            Image imgEscalada = img.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                            ImageIcon iconoEscalado = new ImageIcon(imgEscalada);
                            lblImagen.setIcon(iconoEscalado);
                        } else {
                            // Usar la imagen original si es pequeña
                            lblImagen.setIcon(imagen);
                        }
                        lblImagen.setText("");
                    } else {
                        // Si no tiene dimensiones válidas, intentar usar directamente
                        lblImagen.setIcon(imagen);
                        lblImagen.setText("");
                    }
                } else {
                    mostrarSinImagen();
                }
            } catch (Exception e) {
                System.err.println("Error al mostrar imagen: " + e.getMessage());
                e.printStackTrace();
                // Intentar mostrar la imagen original sin escalar
                try {
                    lblImagen.setIcon(imagen);
                    lblImagen.setText("");
                } catch (Exception e2) {
                    mostrarSinImagen();
                }
            }
        } else {
            mostrarSinImagen();
        }

        // Habilitar campo de días y botones
        txtDias.setEnabled(true);
        btnCalcular.setEnabled(true);
        
        // Verificar si es una película que aún no se ha estrenado
        boolean puedeRentar = itemSeleccionado.getCantidadCopias() > 0;
        if (itemSeleccionado instanceof Movie) {
            Movie movie = (Movie) itemSeleccionado;
            boolean yaEstrenada = peliculaYaEstrenada(movie);
            puedeRentar = puedeRentar && yaEstrenada;
            
            // Mostrar mensaje si la película aún no se ha estrenado
            if (!yaEstrenada) {
                JOptionPane.showMessageDialog(this, 
                        "Esta película aún no se ha estrenado, muy pronto estaremos rentándola",
                        "Película No Disponible", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        btnRentar.setEnabled(puedeRentar);
        lblMontoTotal.setText("L.0.00");
    }
    
    /**
     * Verifica si una película ya se ha estrenado (fecha de estreno <= fecha actual)
     */
    private boolean peliculaYaEstrenada(Movie movie) {
        Calendar fechaEstreno = movie.getFechaEstreno();
        Calendar hoy = Calendar.getInstance();
        
        // Comparar fechas (solo año, mes y día, sin hora)
        int anioEstreno = fechaEstreno.get(Calendar.YEAR);
        int mesEstreno = fechaEstreno.get(Calendar.MONTH);
        int diaEstreno = fechaEstreno.get(Calendar.DAY_OF_MONTH);
        
        int anioHoy = hoy.get(Calendar.YEAR);
        int mesHoy = hoy.get(Calendar.MONTH);
        int diaHoy = hoy.get(Calendar.DAY_OF_MONTH);
        
        // Si el año de estreno es menor, ya se estrenó
        if (anioEstreno < anioHoy) {
            return true;
        }
        // Si el año es igual pero el mes es menor, ya se estrenó
        if (anioEstreno == anioHoy && mesEstreno < mesHoy) {
            return true;
        }
        // Si el año y mes son iguales pero el día es menor o igual, ya se estrenó
        if (anioEstreno == anioHoy && mesEstreno == mesHoy && diaEstreno <= diaHoy) {
            return true;
        }
        
        // Si llegamos aquí, la fecha de estreno es futura
        return false;
    }
    
    private void mostrarSinImagen() {
        lblImagen.setIcon(null);
        lblImagen.setText("<html><center>No hay imagen<br>disponible</center></html>");
        lblImagen.setForeground(Color.GRAY);
    }
    
    private void actualizarMontoAutomatico() {
        if (itemSeleccionado == null) return;
        
        String diasStr = txtDias.getText().trim();
        if (diasStr.isEmpty()) {
            lblMontoTotal.setText("L.0.00");
            return;
        }
        
        try {
            int dias = Integer.parseInt(diasStr);
            if (dias > 0) {
                double montoTotal = itemSeleccionado.pagoRenta(dias);
                lblMontoTotal.setText(String.format("L.%.2f", montoTotal));
            } else {
                lblMontoTotal.setText("L.0.00");
            }
        } catch (NumberFormatException e) {
            lblMontoTotal.setText("L.0.00");
        }
    }

    private void calcularRenta() {
        if (itemSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor busque un item primero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String diasStr = txtDias.getText().trim();
        if (diasStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la cantidad de días", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(diasStr);
            if (dias <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido de días mayor a 0", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular monto total
        double montoTotal = itemSeleccionado.pagoRenta(dias);
        lblMontoTotal.setText(String.format("L.%.2f", montoTotal));

        // Mostrar mensaje con el monto total
        JOptionPane.showMessageDialog(this, 
                String.format("Monto total de la renta: L.%.2f", montoTotal),
                "Renta Calculada", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void rentarItem() {
        if (itemSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor busque un item primero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si es una película que aún no se ha estrenado
        if (itemSeleccionado instanceof Movie) {
            Movie movie = (Movie) itemSeleccionado;
            if (!peliculaYaEstrenada(movie)) {
                JOptionPane.showMessageDialog(this, 
                        "Esta película aún no se ha estrenado, muy pronto estaremos rentándola",
                        "Película No Disponible", 
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // Verificar que haya copias disponibles
        int copiasDisponibles = itemSeleccionado.getCantidadCopias();
        if (copiasDisponibles <= 0) {
            JOptionPane.showMessageDialog(this, 
                    "No hay copias disponibles para rentar. Cantidad de copias: " + copiasDisponibles,
                    "Sin Copias", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String diasStr = txtDias.getText().trim();
        if (diasStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la cantidad de días", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(diasStr);
            if (dias <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido de días mayor a 0", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular monto total
        double montoTotal = itemSeleccionado.pagoRenta(dias);
        
        // Confirmar renta
        int opcion = JOptionPane.showConfirmDialog(this,
                String.format(
                    "<html><center>" +
                    "<b>Confirmar Renta</b><br><br>" +
                    "Item: %s<br>" +
                    "Código: %s<br>" +
                    "Días: %d<br>" +
                    "Monto Total: L.%.2f<br><br>" +
                    "¿Desea confirmar la renta?" +
                    "</center></html>",
                    itemSeleccionado.getNombre(),
                    itemSeleccionado.getCodigo(),
                    dias,
                    montoTotal
                ),
                "Confirmar Renta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            // Reducir cantidad de copias
            itemSeleccionado.setCantidadCopias(copiasDisponibles - 1);
            
            JOptionPane.showMessageDialog(this,
                    String.format(
                        "<html><center>" +
                        "<b>Renta Exitosa</b><br><br>" +
                        "Item rentado: %s<br>" +
                        "Días: %d<br>" +
                        "Monto Total: L.%.2f<br>" +
                        "Copias restantes: %d" +
                        "</center></html>",
                        itemSeleccionado.getNombre(),
                        dias,
                        montoTotal,
                        itemSeleccionado.getCantidadCopias()
                    ),
                    "Renta Completada",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar información mostrada
            mostrarInformacionItem();
            txtDias.setText("");
            lblMontoTotal.setText("L.0.00");
        }
    }

    private void volver() {
        mainFrame.cambiarVista(NombrePaneles.MENU);
        limpiarDatos();
    }

    private void limpiarDatos() {
        itemSeleccionado = null;
        txtCodigo.setText("");
        txtDias.setText("");
        txtDias.setEnabled(false);
        btnCalcular.setEnabled(false);
        btnRentar.setEnabled(false);
        lblInfo.setText("<html><center>Ingrese el código del item para buscar</center></html>");
        lblImagen.setIcon(null);
        lblImagen.setText("<html><center>No hay imagen<br>disponible</center></html>");
        lblImagen.setForeground(Color.GRAY);
        lblMontoTotal.setText("L.0.00");
    }
}
