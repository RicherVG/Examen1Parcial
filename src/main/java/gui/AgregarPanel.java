package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class AgregarPanel extends JPanel{

    public AgregarPanel() { 
        setLayout(new BorderLayout());
        add(new FormularioPanel(), BorderLayout.CENTER);
    }
    
}
