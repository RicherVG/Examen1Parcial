/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Calendar;
import javax.swing.ImageIcon;

/**
 *
 * @author riche
 */
public class Movie extends RentItem {
    protected Calendar fechaEstreno; 

    public Movie(String codigo, String nombre, double precioBase) {
        super(codigo, nombre, precioBase);
        this.fechaEstreno = Calendar.getInstance();
    }

    public Calendar getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Calendar fechaEstreno) {
        if (fechaEstreno != null) this.fechaEstreno = fechaEstreno;
    }
    
    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        Calendar hoy = Calendar.getInstance();

        int y1 = fechaEstreno.get(Calendar.YEAR);
        int m1 = fechaEstreno.get(Calendar.MONTH);
        int y2 = hoy.get(Calendar.YEAR);
        int m2 = hoy.get(Calendar.MONTH);

        int diffMeses = (y2 - y1) * 12 + (m2 - m1);
        if (diffMeses <= 3) return "ESTRENO";
        return "NORMAL";
    }

    @Override
    public double pagoRenta(int dias) {
        if (dias <= 0) return 0;

        double total = precioBase * dias;
        String estado = getEstado();

        if (estado.equals("ESTRENO") && dias > 2) {
            total += 50.0 * (dias - 2);
        } else if (estado.equals("NORMAL") && dias > 5) {
            total += 30.0 * (dias - 5);
        }
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + ", Estado=" + getEstado() + " – Movie";
    }
}
