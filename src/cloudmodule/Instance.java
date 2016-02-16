/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudmodule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author diego
 */
public class Instance {
     private List<Type> types;

    public Instance() {
        this.types = new ArrayList<>();
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
    
    
    public ArrayList<Type> searchType(int ram,int pesnumber ,long storage){
        Type type2 = new Type();
        List<Type> bestType = new ArrayList<>();
        for (Type type : types) {
            if(type.getMemory()>=ram && type.getvCPU()>=pesnumber && type.getStorage()>=storage){
                type2=type;
                bestType.add(type);
            }
        }
        Collections.sort(bestType);
        return (ArrayList<Type>) bestType;
    }
    
    public Type bestchoice(int ram, int pesnumber,long storage){
        ArrayList<Type> types = searchType(ram, pesnumber,storage);
        if(types.get(0)!=null){
        return types.get(0);
        }
        else{
            return null;
        }
    }

     
}
