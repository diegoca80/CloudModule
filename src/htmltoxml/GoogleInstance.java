/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmltoxml;

/**
 *
 * @author diego
 */
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cloudmodule.Type;
import cloudmodule.Instance;
import java.io.FileOutputStream;

public class GoogleInstance {
    public static void main(String[] args) throws IOException {
        String html = "https://cloud.google.com/compute/#machine-note1";
        
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("Instance", Instance.class); //creating an alias
        xStream.alias("Type",Type.class);
         
        try {
         Document doc = Jsoup.connect(html).get();
         Elements tableElements = doc.select("table");

         Elements tableHeaderEles = tableElements.select("thead tr th");


         Elements tableRowElements = tableElements.select(":not(thead) tr");
         Instance ins = new Instance();
         
         for (int i = 0; i < 19; i++) {
            Element row = tableRowElements.get(i);
            
            Elements rowItems = row.select("td");
            //novo tipo instancia
            Type type = new Type();
            
            for (int j = 0; j < rowItems.size(); j++) {
                
                //limitando o que se quer 
                //primeiro da um split em todos \n \t etc
                String values = rowItems.get(j).text();
                if(j==0){
                    type.setName(values);
                }
                if(j==1){
                    type.setvCPU(Integer.parseInt(values));
                }
                if(j==2){
                    type.setMemory(Double.parseDouble(values.replace("GB", "")));
                }
                if(j==6){
                    type.setPricePerHour(Double.parseDouble(values.replace("$","")));
                }
                
            }
            if((i!=0) && (i!=7) && (i!=13)){//null objects
            ins.getTypes().add(type);
            }
         }
            
        String teste = xStream.toXML(ins);
        System.out.println(teste); 
        FileOutputStream out = new FileOutputStream("/home/diego/Desktop/tfg-cloud/google.xml");
        out.write(teste.getBytes());
            
            for (Type p : ins.getTypes()) {
                System.out.println(p.getName());
                System.out.println(p.getvCPU());
                System.out.println(p.getMemory());
                System.out.println(p.getPricePerHour());
                System.out.println();
            }
      } catch (IOException e) {
         e.printStackTrace();
      }
    }
}