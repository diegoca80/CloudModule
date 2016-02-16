/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudmodule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class Type implements Comparable<Type> {
    private String name;
    private int vCPU;
    private double ecu;
    private double memory; // Gib
    private int storage; // GB
    private double pricePerHour; // $

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getvCPU() {
        return vCPU;
    }

    public void setvCPU(int vCPU) {
        this.vCPU = vCPU;
    }

    public double getEcu() {
        return ecu;
    }

    public void setEcu(double ecu) {
        this.ecu = ecu;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public int compareTo(Type t) {
      
        return (int) (this.getPricePerHour()-t.getPricePerHour());
    }
    
}
