/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.swing.ImageIcon;

/**
 *
 * @author riche
 */

public  abstract class RentItem {
    protected final String codigo;
    protected final String nombre;
    protected final double precioBase;
    protected int cantidadCopias;
    protected  ImageIcon imagen;
    
    public RentItem(String codigo,String nombre,double precioBase){
        this.codigo=codigo;
        this.nombre=nombre;
        this.precioBase=precioBase;
        this.cantidadCopias=0;
    }
    
    public String getCodigo(){
        return codigo;
    }   
    public String getNombre(){
        return nombre;
    }
    public double getPrecioBase(){
        return precioBase;
    }
    
    public int getCantidadCopias(){
        return cantidadCopias;
    }
    
    public void setCantidadCopias(int cantidadCopias){
        if (cantidadCopias >= 0) {
            this.cantidadCopias = cantidadCopias;
        }
    }
    
    public ImageIcon getImagen(){
        return imagen;
    }
    @Override
    public String toString() {
        return "Codigo=" + codigo + ", Nombre=" + nombre + ", Precio Base=" + precioBase + ", cantidad de copias=" + cantidadCopias;
    }

    public abstract double pagoRenta(int dias);
    
            
 }
