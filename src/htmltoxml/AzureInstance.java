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
public class AzureInstance {
    public static void main(String[] args) throws IOException {
        String html = "http://azure.microsoft.com/en-us/pricing/details/virtual-machines/#Linux";
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("Instance", Instance.class); //creating an alias
        xStream.alias("Type",Type.class);
        
        try {
         Document doc = Jsoup.connect(html).get();
         Elements tableElements = doc.select("table");

         Elements tableHeaderEles = tableElements.select("thead tr th");

         double testee=3.5;
         Elements tableRowElements = tableElements.select(":not(thead) tr");
         Instance ins = new Instance();
         
         for (int i = 13; i < 21; i++) {
            Element row = tableRowElements.get(i);
            
            Elements rowItems = row.select("td");
            //novo tipo instancia
            Type type = new Type();
            
            for (int j = 0; j < rowItems.size(); j++) {
                
//                //limitando o que se quer 
//                //primeiro da um split em todos \n \t etc
                String values = rowItems.get(j).text();
                if(j==0){
                    type.setName(values);
                }
                if(j==1){
                    type.setvCPU(Integer.parseInt(values));
                }
                if(j==2){
                    double temp;
                    if("3,5 GB".equals(values)){
                    temp = Double.parseDouble(values.replace("3,5 GB", "3.5"));
                    }
                    else{
                    temp = Double.parseDouble(values.replace(" GB", ""));
                    }
                    type.setMemory(temp);
                }
                if(j==3){
                    type.setStorage(Integer.parseInt(values.replace(" GB", "")));
                }
                if(j==4){
                  type.setPricePerHour(Double.parseDouble(values.substring(1, 6).replace(",", ".")));
                }            
            }
            ins.getTypes().add(type);
         }
         
            //Teste print list
            
        String teste = xStream.toXML(ins);
        System.out.println(teste); 
        FileOutputStream out = new FileOutputStream("/home/diego/Desktop/tfg-cloud/azure.xml");
        out.write(teste.getBytes());
            
//            for (Type p : ins.getTypes()) {
//                System.out.println(p.getName());
//                System.out.println(p.getvCPU());
//                System.out.println(p.getMemory());
//                System.out.println(p.getPricePerHour());
//                System.out.println();
//            }
      } catch (IOException e) {
         e.printStackTrace();
      }
    }
}
