package gui;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.RentItem;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class RentarPanel extends JPanel {
    
    public RentarPanel(MainFrame mainFrame) {
        setLayout(new FlowLayout());

        ArrayList<RentItem> items = mainFrame.getItems();
        JComboBox<RentItem> comboBox = new JComboBox<>(items.toArray(new RentItem[0]));
        add(comboBox);

        JButton btnRentar = new JButton("Rentar");
        add(btnRentar);
    }

    
}
