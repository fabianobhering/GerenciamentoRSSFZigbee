/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class executes all the instructions
 * 
 * @author gabriel
 */
public class Exec {
    
    private static ArrayList<Ins> ins;
    //private final XBeeWiFi device;
    
    /*public Exec(XBeeWiFi device){
        this.device = device;
        ins = new ArrayList<>();
    }*/
    
    public static void setIns(ArrayList<Ins> ins){
        Exec.ins = ins;
    }
    
    /*
    Caso seja passado um dispositivo como parâmetro para o método abaixo,
    o mesmo irá escolher as instruções deste dispositivo para que sejam
    executadas. Caso contrário, isto é, seja passado null, ocorrerá o
    processamento das instruções globais.
    */
    
    public static boolean runAll(XBeeWiFi device){
        
        int[] ports;
        boolean exec;
        if(ins == null){
            ins = new ArrayList<>();
        }
        ArrayList<Ins> selected = new ArrayList<>();
        
        //Abaixo, a lógica para a seleção dos dispositivos que serão executados

        if(device != null){ //carregar para um dispositivo específico
            for(Ins i : ins){
                if(i.getAddr().equals(device.getAddr64().toString())){
                    selected.add(i);
                }
            }
        } else {
            for(Ins i : ins){
                if(i.isGlobal()){
                    selected.add(i);
                }
            }
        }
        
        //verifica se realmente existe algo a ser executado
        exec = false;
        for(Object i : selected){
            if(!(i instanceof Delay)){
                exec = true;
                break;
            }
        }
        if(!exec){
            return true;
        }
        
        /*if((ports = XBeeWiFi.readInputs(XBeeWiFiControl.getDevice(macAddr))) == null){ //!!! input value collected once !!!
            System.out.println("Não há portas ativas!");
            return false;
        }*/
        
        for(Ins i : selected){
            if(i instanceof ConIns){
                ConIns in = (ConIns) i;
                if((ports = XBeeWiFi.readInputs(XBeeWiFiControl.getDevice(in.getReadDevice()))) == null){
                    System.out.println("Não há portas ativas no dispositivo!");
                    continue;
                }
                exec = false;
                if(in.getConV() != -1){
                    if(in.getPortIn() < ports.length){
                        switch(in.getOprd()){
                            case "<":
                                if(ports[in.getPortIn()] < in.getConV()) exec = true;
                                break;
                            case ">":
                                if(ports[in.getPortIn()] > in.getConV()) exec = true;
                                break;
                            case "=":
                                if(ports[in.getPortIn()] == in.getConV()) exec = true;
                                break;
                        }
                    }
                } else {
                    exec = true;
                }
                if(exec){
                    try {
                        XBeeWiFi.setPort(XBeeWiFiControl.getDevice(in.getWriteDevice()), in.getPortOut(), (in.getSetV() ? 5 : 4));
                        // REQUISIÇÃO WEB PARA REGISTRO ONLINE
                        String param;
                        
                        if(in.getPortOut().equals("D4")){
                            param = "?key=15102016alfa765&&actuator=1001&&value=";
                        } else {
                            param = "?key=15102016alfa765&&actuator=1002&&value=";
                        }
                        param += (in.getSetV()?"1":"0");
                        
                        URL url = new URL("http://update.taurussystem.com/ws/webresources/Digital/set"+param);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                        int responseCode = conn.getResponseCode();
                        System.out.println("[Request] R: " + responseCode);
                        BufferedReader inp = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = inp.readLine()) != null) {
                                response.append(inputLine);
                        }
                        inp.close();

                        //print result
                        System.out.println(response.toString());
                        /*conn.setDoOutput(true);
                        DataOutputStream wo = new DataOutputStream(conn.getOutputStream());
                        wo.writeBytes(param);
                        wo.close();*/
                        // -----------------------------------
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex){
                    
                    }
                }
                
            } else {
                if(i instanceof LogIns){
                    LogIns li = (LogIns) i;
                    if((ports = XBeeWiFi.readInputs(XBeeWiFiControl.getDevice(li.getAddr()))) == null){
                        System.out.println("Não há portas ativas no dispositivo!");
                        continue;
                    }
                    try {
                        PrintWriter pw = new PrintWriter(new FileWriter(/*"/home/pi/Projeto/"+*/li.getNomeArq(), true));
                        pw.write("PORT "+li.getPort()+" -> "+ports[li.getPort()]+"\n");
                        pw.close();
                        // REQUISIÇÃO WEB PARA REGISTRO ONLINE
                        String param;
                        
                        if(/*li.getPort() == 0*/XBeeWiFiControl.getDevices().indexOf(device) == 0){
                            param = "?key=15102016alfa765&&sensor=2001&&value=";
                        } else {
                            param = "?key=15102016alfa765&&sensor=2002&&value=";
                        }
                        param += ports[li.getPort()];
                        
                        URL url = new URL("http://update.taurussystem.com/ws/webresources/Sensor/set"+param);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                        int responseCode = conn.getResponseCode();
                        System.out.println("[Request] R: " + responseCode);
                        BufferedReader inp = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = inp.readLine()) != null) {
                                response.append(inputLine);
                        }
                        inp.close();

                        //print result
                        System.out.println(response.toString());
                        /*conn.setDoOutput(true);
                        DataOutputStream wo = new DataOutputStream(conn.getOutputStream());
                        wo.writeBytes(param);
                        wo.close();*/
                        // -----------------------------------
                    } catch (IOException e) {
                        System.err.println("Erro [I/O]: "+e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.err.println("Erro [array]: "+e.getMessage());
                    }                 
                } else {
                    Delay dl = (Delay) i;
                    try {
                        Thread.sleep(dl.getTime());
                    } catch (InterruptedException e) {
                        System.err.println("Erro: "+e.getMessage());
                    }
                }
            }
        }
        
        return true;
    }

    public static boolean addIns(Ins i){
        if(ins == null){
            ins = new ArrayList<>();
        }
        return ins.add(i);
    }

    public static boolean removeIns(Ins i) {
        return ins.remove(i);
    }
    
    public static boolean removeIns(int i) {
        return (ins.remove(i) != null);
    }
    
    public static ArrayList<Ins> getIns(){
        return ins;
    }
    
}
