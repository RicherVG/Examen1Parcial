package gui;

import model.NombrePaneles;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;



    public MainFrame() {
        setTitle("Sistema de Renta");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new MenuPanel(), NombrePaneles.MENU.toString());
//        mainPanel.add(new AgregarPanel(), "AGREGAR");
//        mainPanel.add(new RentarPanel(), "RENTAR");
//        mainPanel.add(new ImprimirPanel(), "IMPRIMIR");


        add(mainPanel);
        cardLayout.show(mainPanel, "MENU");
    }

    public void cambiarVista(NombrePaneles nombre) {
        cardLayout.show(mainPanel, nombre.toString());
    }
}
