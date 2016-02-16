/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudmodule;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author diego
 */
public class CloudModule {

    /**
     * @param args the command line arguments
     */
    public  void searchTypesAmazon(int i,int j,double time,long k){
        FileReader fis = null;
        try {
            fis = new FileReader("/home/diego/NetBeansProjects/CloudModule/tfg-cloud/table-prices-amazon.xml");
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("Instance", Instance.class); //creating an alias
            xStream.alias("Type",Type.class);
         
            Instance ins2 = (Instance)xStream.fromXML(fis);
            for (Type type : ins2.getTypes()) {
                type.setMemory(type.getMemory()*1024);
                type.setStorage((int)k);//save in MB
                //$ 0.10 GB/month
                double cast = (double)type.getStorage()/1024;
                type.setPricePerHour(type.getPricePerHour()+(cast*0.10/720));//transform in GB and use hours beside month(1month=720hrs)
                //type.setStorage(type.getStorage()*1024); //transform storage in MB
            }
            
            ArrayList<Type> types = ins2.searchType(i, j, k);
            System.out.println("---------------------------------------------------------------------------------");
            BigDecimal price;
                
            for (Type type : types) {
                int rest,result;//quociente e resto
                if(time<=3600){
                            price = new BigDecimal(type.getPricePerHour()); 
                }
                else{
                            result = (int)time/3600;
                            rest = (int)time % 3600;
                            if(rest==0){
                            price = new BigDecimal(type.getPricePerHour()*result);
                            }
                            else{                                
                            price = new BigDecimal((type.getPricePerHour()*result)+type.getPricePerHour());
                            }
                }
                            price = price.setScale(4, BigDecimal.ROUND_HALF_UP); 
                            System.out.println("Instance name = " + type.getName() + "/ memory:" + type.getvCPU()+ "/ memory:" + type.getMemory()+ "/ storage:" + type.getStorage()+ "/ cost:" + price);
                            }
                            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    public  void searchBestTypeAmazon(int i,int j,double time,long k){
        FileReader fis = null;
        try {
            fis = new FileReader("/home/diego/NetBeansProjects/CloudModule/tfg-cloud/table-prices-amazon.xml");
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("Instance", Instance.class); //creating an alias
            xStream.alias("Type",Type.class);
         
            Instance ins2 = (Instance)xStream.fromXML(fis);
            for (Type type : ins2.getTypes()) {
                type.setMemory(type.getMemory()*1024); 
                type.setStorage((int)k);//save in MB
                //$ 0.10 GB/month
                double cast = (double)type.getStorage()/1024;
                type.setPricePerHour(type.getPricePerHour()+(cast*0.10/720));//transform in GB and use hours beside month(1month=720hrs)
            }
            
            ArrayList<Type> types = ins2.searchType(i, j, k);
            System.out.println("---------------------------------------------------------------------------------");
            BigDecimal bestPrice = new BigDecimal(BigInteger.ONE);
            Type bestType = new Type();
            bestType = ins2.bestchoice(i, j, k);
                
            for (Type type : types) {
                int rest,result;//quociente e resto
                if(time<=3600){
                            bestPrice = new BigDecimal(bestType.getPricePerHour()); 
                }
                else{
                            result = (int)time/3600;
                            rest = (int)time % 3600;
                            if(rest==0){
                            bestPrice = new BigDecimal(bestType.getPricePerHour()*result); 
                            }
                            else{                                
                            bestPrice = new BigDecimal((bestType.getPricePerHour()*result)+bestType.getPricePerHour());
                            }
                }
                            }
                            System.out.println("");
                            bestPrice = bestPrice.setScale(4, BigDecimal.ROUND_HALF_UP); 
                            System.out.println("Best Choice of Amazon for you:");
                            System.out.println("Instance name = " + bestType.getName() + "/ vCPU:" + bestType.getvCPU()+ "/ memory:" + bestType.getMemory()+ "/ storage:" + bestType.getStorage() + "/ cost:" + bestPrice);
                            System.out.println("");
                            
                            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    
    //In google, storage is individual price
    public  void searchTypesGoogle(int i,int j,double time, long k){
        FileReader fis = null;
        try {
            fis = new FileReader("/home/diego/NetBeansProjects/CloudModule/tfg-cloud/google.xml");
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("Instance", Instance.class); //creating an alias
            xStream.alias("Type",Type.class);
         
            Instance ins2 = (Instance)xStream.fromXML(fis);
            
            System.out.println("---------------------------------------------------------------------------------");
            for (Type type : ins2.getTypes()) {
                type.setMemory(type.getMemory()*1024);
                type.setStorage((int)k);//save in MB
                //$ 0.218 GB/month
                double cast = (double)type.getStorage()/1024;
                type.setPricePerHour(type.getPricePerHour()+(cast*0.218/720));//transform in GB and use hours beside month(1month=720hrs)
            }
            
            ArrayList<Type> types = ins2.searchType(i, j, k);
            BigDecimal price = new BigDecimal(BigInteger.ONE);
                            
            for (Type type : types) {
                int rest,result;
                if(time<=600){//cost for minimum 10 minutes
                            price = new BigDecimal((type.getPricePerHour()/60)*10); 
                            }
                else        {
                            result = (int)time/60;
                            rest = (int)time % 60;
                            if(rest==0){
                            price = new BigDecimal(type.getPricePerHour()/60*result); //price for minute at azure 
                            }
                            else{
                            price = new BigDecimal((type.getPricePerHour()/60*result)+type.getPricePerHour()/60); //price for minute at azure 
                            }
                }
                            price = price.setScale(4, BigDecimal.ROUND_HALF_UP); 
                            System.out.println("Instance name = " + type.getName() + "/ vCPU:" + type.getvCPU()+ "/ memory:" + type.getMemory()+ "/ storage:" + type.getStorage() + "/ cost:" + price);
                            
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    //In google, storage is individual price
    public  void searchBestTypeGoogle(int i,int j,double time, long k){
        FileReader fis = null;
        try {
            fis = new FileReader("/home/diego/NetBeansProjects/CloudModule/tfg-cloud/google.xml");
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("Instance", Instance.class); //creating an alias
            xStream.alias("Type",Type.class);
         
            Instance ins2 = (Instance)xStream.fromXML(fis);
            
            System.out.println("---------------------------------------------------------------------------------");
            for (Type type : ins2.getTypes()) {
                type.setMemory(type.getMemory()*1024);
                type.setStorage((int)k);//save in MB
                //$ 0.218 GB/month
                double cast = (double)type.getStorage()/1024;
                type.setPricePerHour(type.getPricePerHour()+(cast*0.218/720));//transform in GB and use seconds beside month(1month=720hrs)
            }
            
            ArrayList<Type> types = ins2.searchType(i, j, k);
            BigDecimal bestPrice = new BigDecimal(BigInteger.ONE);
            Type bestType = new Type();
            bestType = ins2.bestchoice(i, j, k);
                            
            for (Type type : types) {
                int rest,result;
                if(time<=600){//cost for minimum 10 minutes
                            bestPrice = new BigDecimal((bestType.getPricePerHour()/60)*10); 
                            }
                else        {
                            result = (int)time/60;
                            rest = (int)time % 60;
                            if(rest==0){
                            bestPrice = new BigDecimal(bestType.getPricePerHour()/60*result); 
                            }
                            else{
                            bestPrice = new BigDecimal((bestType.getPricePerHour()/60*result)+bestType.getPricePerHour()/60); 
                            }
                }
            }
                            System.out.println("");
                            bestPrice = bestPrice.setScale(4, BigDecimal.ROUND_HALF_UP); 
                            System.out.println("Best Choice of Google for you:");
                            System.out.println("Instance name = " + bestType.getName() + "/ vCPU:" + bestType.getvCPU()+ "/ memory:" + bestType.getMemory()+ "/ storage:" + bestType.getStorage() + "/ cost:" + bestPrice);
                            System.out.println("");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    
    public  void searchTypesAzure(int i,int j,double time,long k){
        FileReader fis = null;
        try {
            fis = new FileReader("/home/diego/NetBeansProjects/CloudModule/tfg-cloud/azure.xml");
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("Instance", Instance.class); //creating an alias
            xStream.alias("Type",Type.class);
            
            Instance ins2 = (Instance)xStream.fromXML(fis);
            System.out.println("---------------------------------------------------------------------------------");
            
            for (Type type : ins2.getTypes()) {
                type.setMemory(type.getMemory()*1024);                
                type.setStorage(type.getStorage()*1024);//transform in MB
            }
            
            ArrayList<Type> types = ins2.searchType(i, j,k);
            BigDecimal price = new BigDecimal(BigInteger.ONE);
                            
            for (Type type : types) {
                int rest,result;
                if(time<=60){
                            price = new BigDecimal(type.getPricePerHour()/60); //price for minute at azure 
                }
                else{
                            result = (int)time/60;
                            rest = (int)time % 60;
                            if(rest==0){
                            price = new BigDecimal(type.getPricePerHour()/60*result); //price for minute at azure 
                            }
                            else{
                            price = new BigDecimal((type.getPricePerHour()/60*result)+type.getPricePerHour()/60); //price for minute at azure 
                            }
                }
                            price = price.setScale(4, BigDecimal.ROUND_HALF_UP); 
                            System.out.println("Instance name = " + type.getName() + "/ vCPU:" + type.getvCPU()+ "/ memory:" + type.getMemory()+ "/ storage:" + type.getStorage() + "/ cost:" + price);
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public  void searchBestTypeAzure(int i,int j,double time,long k){
        FileReader fis = null;
        try {
            fis = new FileReader("/home/diego/NetBeansProjects/CloudModule/tfg-cloud/azure.xml");
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("Instance", Instance.class); //creating an alias
            xStream.alias("Type",Type.class);
            
            Instance ins2 = (Instance)xStream.fromXML(fis);
            System.out.println("---------------------------------------------------------------------------------");
            
            for (Type type : ins2.getTypes()) {
                type.setMemory(type.getMemory()*1024);                
                type.setStorage(type.getStorage()*1024);//transform in MB
            }
            
            ArrayList<Type> types = ins2.searchType(i, j,k);
            BigDecimal bestPrice = new BigDecimal(BigInteger.ONE);
            Type bestType = new Type();
            bestType = ins2.bestchoice(i, j,k);
                            
            for (Type type : types) {
                int rest,result;
                if(time<=60){
                            bestPrice = new BigDecimal(bestType.getPricePerHour()*time/3600); 
                }
                else{
                            result = (int)time/60;
                            rest = (int)time % 60;
                            if(rest==0){
                            bestPrice = new BigDecimal(bestType.getPricePerHour()/60*result); 
                            }
                            else{
                            bestPrice = new BigDecimal((bestType.getPricePerHour()/60*result)+bestType.getPricePerHour()/60); 
                            }
                }
            }
            
                            System.out.println("");
                            bestPrice = bestPrice.setScale(4, BigDecimal.ROUND_HALF_UP); 
                            System.out.println("Best Choice of Azure for you:");
                            System.out.println("Instance name = " + bestType.getName() + "/ vCPU:" + bestType.getvCPU()+ "/ memory:" + bestType.getMemory()+ "/ storage:" + bestType.getStorage() + "/ cost:" + bestPrice);
                            System.out.println("");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CloudModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
}
