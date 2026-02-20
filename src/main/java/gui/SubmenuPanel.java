package gui;

import model.MenuActions;
import model.NombrePaneles;
import model.RentItem;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SubmenuPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnVolver;
    private JPanel panelOpciones;
    private JTextArea txtEspecificaciones;
    private JScrollPane scrollEspecificaciones;
    private RentItem itemSeleccionado;
    private MenuActions itemConMenu;

    public SubmenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con título
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitulo = new JLabel("Ejecutar Submenú");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblTitulo);

        // Panel central
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Código del item
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(new JLabel("Código del Item:"), gbc);
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
        panelCentral.add(panelCodigo, gbc);

        // Panel de información del item
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        JLabel lblInfo = new JLabel("<html><center>Ingrese el código del item para buscar</center></html>");
        lblInfo.setHorizontalAlignment(JLabel.CENTER);
        lblInfo.setVerticalAlignment(JLabel.CENTER);
        lblInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Información del Item"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        lblInfo.setPreferredSize(new Dimension(400, 100));
        panelCentral.add(lblInfo, gbc);

        // Panel de especificaciones (solo para Game)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        JPanel panelEspecificaciones = new JPanel(new BorderLayout());
        panelEspecificaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Especificaciones"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txtEspecificaciones = new JTextArea();
        txtEspecificaciones.setEditable(false);
        txtEspecificaciones.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtEspecificaciones.setBackground(Color.WHITE);
        scrollEspecificaciones = new JScrollPane(txtEspecificaciones);
        scrollEspecificaciones.setPreferredSize(new Dimension(400, 100));
        scrollEspecificaciones.setVisible(false);
        panelEspecificaciones.add(scrollEspecificaciones, BorderLayout.CENTER);
        panelCentral.add(panelEspecificaciones, gbc);

        // Panel de opciones del submenú
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        panelOpciones = new JPanel(new GridBagLayout());
        panelOpciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Opciones del Submenú"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panelOpciones.setVisible(false);
        panelCentral.add(panelOpciones, gbc);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> volver());

        panelBotones.add(btnVolver);

        // Agregar componentes al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
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
        itemConMenu = null;

        // Buscar el item por código
        for (RentItem item : items) {
            if (item.getCodigo() != null && item.getCodigo().equals(codigo)) {
                itemSeleccionado = item;
                // Verificar si implementa MenuActions
                if (item instanceof MenuActions) {
                    itemConMenu = (MenuActions) item;
                }
                break;
            }
        }

        if (itemSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Item No Existe",
                    "Error", JOptionPane.ERROR_MESSAGE);
            limpiarDatos();
            return;
        }

        if (itemConMenu == null) {
            JOptionPane.showMessageDialog(this,
                    "El item seleccionado no implementa MenuActions.\n" +
                    "Solo los items de tipo Game tienen submenú disponible.",
                    "Sin Submenú",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarDatos();
            return;
        }

        // Mostrar información y opciones del submenú
        mostrarSubmenu();
    }

    private void mostrarSubmenu() {
        if (itemConMenu == null || itemSeleccionado == null) return;

        // Actualizar especificaciones si es Game
        if (itemSeleccionado instanceof model.Game) {
            model.Game game = (model.Game) itemSeleccionado;
            actualizarEspecificaciones(game);
            scrollEspecificaciones.setVisible(true);
        } else {
            scrollEspecificaciones.setVisible(false);
        }

        // Limpiar panel de opciones
        panelOpciones.removeAll();

        // Crear botones de opciones según el tipo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        if (itemSeleccionado instanceof model.Game) {
            // Opciones para Game
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel lblTitulo = new JLabel("<html><b>Submenú Game</b></html>");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
            panelOpciones.add(lblTitulo, gbc);

            gbc.gridwidth = 1;
            gbc.gridy = 1;
            gbc.gridx = 0;
            JButton btnOp1 = new JButton("1) Actualizar fecha de publicación");
            btnOp1.setPreferredSize(new Dimension(300, 40));
            btnOp1.addActionListener(e -> actualizarFechaPublicacion());
            panelOpciones.add(btnOp1, gbc);

            gbc.gridx = 1;
            JButton btnOp2 = new JButton("2) Agregar especificación");
            btnOp2.setPreferredSize(new Dimension(300, 40));
            btnOp2.addActionListener(e -> ejecutarOpcion(2));
            panelOpciones.add(btnOp2, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            JButton btnOp3 = new JButton("3) Ver especificaciones");
            btnOp3.setPreferredSize(new Dimension(300, 40));
            btnOp3.addActionListener(e -> ejecutarOpcion(3));
            panelOpciones.add(btnOp3, gbc);

            gbc.gridx = 1;
            JButton btnSalir = new JButton("0) Salir");
            btnSalir.setPreferredSize(new Dimension(300, 40));
            btnSalir.setBackground(new Color(158, 158, 158));
            btnSalir.setForeground(Color.WHITE);
            btnSalir.addActionListener(e -> volver());
            panelOpciones.add(btnSalir, gbc);
        }

        panelOpciones.setVisible(true);
        panelOpciones.revalidate();
        panelOpciones.repaint();
    }

    private void actualizarFechaPublicacion() {
        if (!(itemSeleccionado instanceof model.Game)) return;
        
        model.Game game = (model.Game) itemSeleccionado;
        
        // Crear diálogo con JDateChooser
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                "Actualizar Fecha de Publicación", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nueva Fecha de Publicación:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(game.getFechaPublicacion().getTime());
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(200, 30));
        panel.add(dateChooser, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBackground(new Color(76, 175, 80));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.addActionListener(e -> {
            Date fechaSeleccionada = dateChooser.getDate();
            if (fechaSeleccionada != null) {
                Calendar fecha = Calendar.getInstance();
                fecha.setTime(fechaSeleccionada);
                game.setFechaPublicacion(fecha);
                JOptionPane.showMessageDialog(dialog, "Fecha actualizada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                mostrarSubmenu(); // Actualizar vista
            } else {
                JOptionPane.showMessageDialog(dialog, "Por favor seleccione una fecha.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(158, 158, 158));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void actualizarEspecificaciones(model.Game game) {
        if (game == null) return;
        
        // Obtener especificaciones usando reflexión o método público si existe
        // Como especificaciones es protected, necesitamos acceder a través de toString o crear método
        java.util.ArrayList<String> especificaciones = game.getEspecificaciones();
        
        if (especificaciones == null || especificaciones.isEmpty()) {
            txtEspecificaciones.setText("No hay especificaciones registradas.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < especificaciones.size(); i++) {
                sb.append("• ").append(especificaciones.get(i));
                if (i < especificaciones.size() - 1) {
                    sb.append("\n");
                }
            }
            txtEspecificaciones.setText(sb.toString());
        }
    }

    private void ejecutarOpcion(int opcion) {
        if (itemConMenu == null) return;

        // Para la opción 1, usar el método especial con JDateChooser
        if (opcion == 1 && itemSeleccionado instanceof model.Game) {
            actualizarFechaPublicacion();
            return;
        }

        // Ejecutar las demás opciones normalmente
        itemConMenu.ejecutarOpcion(opcion);

        // Actualizar la información mostrada si es necesario
        if (itemSeleccionado != null) {
            mostrarSubmenu(); // Esto actualizará las especificaciones
        }
    }

    private void volver() {
        mainFrame.cambiarVista(NombrePaneles.MENU);
        limpiarDatos();
    }

    private void limpiarDatos() {
        itemSeleccionado = null;
        itemConMenu = null;
        txtCodigo.setText("");
        txtEspecificaciones.setText("");
        scrollEspecificaciones.setVisible(false);
        panelOpciones.removeAll();
        panelOpciones.setVisible(false);
        panelOpciones.revalidate();
        panelOpciones.repaint();
    }
}
