package gui;

import model.NombrePaneles;
import model.RentItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;

    private ArrayList<RentItem> items;

    public MainFrame() {
        setTitle("Sistema de Renta");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        items = new ArrayList<>();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new MenuPanel(), NombrePaneles.MENU.name());
        mainPanel.add(new AgregarPanel(), NombrePaneles.AGREGAR.name());
//        mainPanel.add(new RentarPanel(), "RENTAR");
//        mainPanel.add(new ImprimirPanel(), "IMPRIMIR");


        add(mainPanel);
        cardLayout.show(mainPanel, NombrePaneles.MENU.name());
    }

    public void cambiarVista(NombrePaneles nombre) {
        cardLayout.show(mainPanel, nombre.name());
    }

    public ArrayList<RentItem> getItems() {
        return items;
    }
}
