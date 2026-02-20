package gui;

import model.NombrePaneles;

import javax.swing.*;
import java.awt.*;

class MenuPanel extends JPanel {

        public MenuPanel() {
                setLayout(new GridLayout(4, 1, 10, 10));

                JButton btnAgregar = new JButton("Agregar Ítem");
                JButton btnRentar = new JButton("Rentar");
                JButton btnSubmenu = new JButton("Ejecutar Submenú");
                JButton btnImprimir = new JButton("Imprimir Todo");

                add(btnAgregar);
                add(btnRentar);
                add(btnSubmenu);
                add(btnImprimir);

                btnAgregar.addActionListener(e -> ((MainFrame) SwingUtilities.getWindowAncestor(this))
                                .cambiarVista(NombrePaneles.AGREGAR));

                btnRentar.addActionListener(e -> ((MainFrame) SwingUtilities.getWindowAncestor(this))
                                .cambiarVista(NombrePaneles.RENTAR));

        btnSubmenu.addActionListener(e ->
                ((MainFrame) SwingUtilities.getWindowAncestor(this))
                        .cambiarVista(NombrePaneles.SUBMENU));

        btnImprimir.addActionListener(e ->
                ((MainFrame) SwingUtilities.getWindowAncestor(this))
                        .cambiarVista(NombrePaneles.IMPRIMIR));
    }
}