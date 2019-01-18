/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.ArrayList;

/**
 *The raw material market.
 * @author Zosia
 */
public class RawMaterialMarket extends Market {
    private ArrayList<RawMaterial> materials;
    
    public RawMaterialMarket() {
        this.name = "Raw Material market";
        this.materials = new ArrayList<>();
        this.markup = 0.1;
    }

    public ArrayList getMaterials() {
        return materials;
    }

    public void add(RawMaterial m) {
        this.materials.add(m);
    }
 
}
