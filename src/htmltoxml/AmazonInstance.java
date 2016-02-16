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




public class AmazonInstance {
    public static void main(String[] args) throws IOException {
        String html = "http://aws.amazon.com/pt/ec2/pricing/";
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("Instance", Instance.class); //creating an alias
        xStream.alias("Type",Type.class);
        
        try {
         Document doc = Jsoup.connect(html).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36").timeout(12000).get();
         Elements tableElements = doc.select("table");

         Elements tableHeaderEles = tableElements.select("thead tr th");
         System.out.println("headers");
         for (int i = 0; i < tableHeaderEles.size(); i++) {
            System.out.println(tableHeaderEles.get(i).text());
         }
         System.out.println();

         Elements tableRowElements = tableElements.select(":not(thead) tr");

         for (int i = 0; i < tableRowElements.size(); i++) {
            Element row = tableRowElements.get(i);
            System.out.println("row");
            Elements rowItems = row.select("td");
            for (int j = 0; j < rowItems.size(); j++) {
               System.out.println(rowItems.get(j).text());
            }
            System.out.println();
         }
//         for (int i = 13; i < 21; i++) {
//            Element row = tableRowElements.get(i);
//            
//            Elements rowItems = row.select("td");
//            //novo tipo instancia
//            Type type = new Type();
//            
//            for (int j = 0; j < rowItems.size(); j++) {
//                
////                //limitando o que se quer 
////                //primeiro da um split em todos \n \t etc
//                String values = rowItems.get(j).text();
//                if(j==0){
//                    type.setName(values);
//                }
//                if(j==1){
//                    type.setvCPU(Integer.parseInt(values));
//                }
//                if(j==2){
//                    type.setMemory(Double.parseDouble(values.replace("GB", "")));
//                }
//                if(j==3){
//                    type.setStorage(Integer.parseInt(values.replace(" GB", "")));
//                }
//                if(j==4){
//                  type.setPricePerHour(Double.parseDouble(values.substring(1, 6).replace(",", ".")));
//                }            
//            }
//            ins.getTypes().add(type);
//         }
//         
//            //Teste print list
//            
//            String xml = xStream.toXML(ins);
//            System.out.println(xml);
            
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
