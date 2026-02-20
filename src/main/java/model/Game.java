/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

/**
 *
 * @author riche
 */
public class Game extends RentItem implements MenuActions {
     protected Calendar fechaPublicacion;                
    protected final ArrayList<String> especificaciones;  

    public Game(String codigo, String nombre) {
        super(codigo, nombre, 20.0); 
        this.fechaPublicacion = Calendar.getInstance();
        this.especificaciones = new ArrayList<>();
    }

    public Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(int year, int mes, int dia) {
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Math.max(0, Math.min(11, mes - 1)));
        c.set(Calendar.DAY_OF_MONTH, dia);
        this.fechaPublicacion = c;
    }
    
    public void setFechaPublicacion(Calendar fecha) {
        if (fecha != null) this.fechaPublicacion = fecha;
    }
    
    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public void agregarEspecificacion(String esp) {
        if (esp != null && !esp.trim().isEmpty()) {
            especificaciones.add(esp.trim());
        }
    }
    
    public ArrayList<String> getEspecificaciones() {
        return especificaciones;
    }

    
    public void listEspecificaciones() {
        if (especificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay especificaciones registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        listarRec(0, sb);
        JOptionPane.showMessageDialog(null, sb.toString(), "Especificaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarRec(int i, StringBuilder sb) {
        if (i >= especificaciones.size()) return;
        sb.append("• ").append(especificaciones.get(i)).append("\n");
        listarRec(i + 1, sb);
    }

    @Override
    public double pagoRenta(int dias) {
        if (dias <= 0) return 0;
        return 20.0 * dias;
    }

    private String fechaB(Calendar c) {
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d", d, m, y);
    }

    @Override
    public String toString() {
        return super.toString() + ", Publicacion=" + fechaB(fechaPublicacion) + " – PS3 Game";
    }

    public void submenu() {
        String menu =
            "Submenú Game\n" +
            "1) Actualizar fecha de publicación\n" +
            "2) Agregar especificación\n" +
            "3) Ver especificaciones\n" +
            "0) Salir";

        while (true) {
            String opStr = JOptionPane.showInputDialog(null, menu, "Submenú Game", JOptionPane.QUESTION_MESSAGE);
            if (opStr == null) return;

            int op;
            try {
                op = Integer.parseInt(opStr.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Opción inválida.");
                continue;
            }

            if (op == 0) return;
            ejecutarOpcion(op);
        }
    }

    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1: {
                String yS = JOptionPane.showInputDialog(null, "Año (ej: 2026):");
                String mS = JOptionPane.showInputDialog(null, "Mes (1-12):");
                String dS = JOptionPane.showInputDialog(null, "Día (1-31):");
                if (yS == null || mS == null || dS == null) return;

                try {
                    int y = Integer.parseInt(yS.trim());
                    int m = Integer.parseInt(mS.trim());
                    int d = Integer.parseInt(dS.trim());
                    setFechaPublicacion(y, m, d);
                    JOptionPane.showMessageDialog(null, "Fecha actualizada.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Datos inválidos.");
                }
                break;
            }
            case 2: {
                String esp = JOptionPane.showInputDialog(null, "Especificación técnica:");
                if (esp != null) {
                    agregarEspecificacion(esp);
                    JOptionPane.showMessageDialog(null, "Especificación agregada.");
                }
                break;
            }
            case 3:
                listEspecificaciones();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción no válida.");
        }
    }
}
