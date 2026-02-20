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
    private ImprimirPanel imprimirPanel;

    public MainFrame() {
        setTitle("Sistema de Renta");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        items = new ArrayList<>();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        imprimirPanel = new ImprimirPanel(this);

        mainPanel.add(new MenuPanel(), NombrePaneles.MENU.name());
        mainPanel.add(new AgregarPanel(), NombrePaneles.AGREGAR.name());
        mainPanel.add(new RentarPanel(this), NombrePaneles.RENTAR.name());
        mainPanel.add(new SubmenuPanel(this), NombrePaneles.SUBMENU.name());
        mainPanel.add(imprimirPanel, NombrePaneles.IMPRIMIR.name());

        add(mainPanel);
        cardLayout.show(mainPanel, NombrePaneles.MENU.name());
    }

    public void cambiarVista(NombrePaneles nombre) {
        if (nombre == NombrePaneles.IMPRIMIR) {
            imprimirPanel.cargarTarjetas();
        }
        cardLayout.show(mainPanel, nombre.name());
    }

    public ArrayList<RentItem> getItems() {
        return items;
    }
}
