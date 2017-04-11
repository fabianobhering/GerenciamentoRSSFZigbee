/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class Control {
    
    private static final int AD1 = 0; //Current
    private static final int AD2 = 1; //Light
    private static final int L_LIMIT = 100;
    private static final int DELAY = 10000;
    
    
    public static void main(String[] args){
        //try {
            XBeeWiFi.setSocket("192.168.0.99", 9750, XBeeWiFi.TCP);
        //} catch (IOException e){
          //  return;
        //}
        ArrayList<XBeeWiFi> devices = XBeeWiFi.ATND();
        for (XBeeWiFi device : devices) {
            System.out.println("Dispositivo "+devices.indexOf(device)+": "+device.getName());
            System.out.println("End. Rede  : "+device.getAddr16());
            System.out.println("End. Físico: "+device.getAddr64());
            
            //System.out.println(Arrays.toString(XBeeWiFi.readInputs(device)));
        }
        
        /*while(!(new Scanner(System.in)).next().equals("0")){
            XBeeWiFi.setDigitalOutput(devices.get(0), 4, true);
            (new Scanner(System.in)).next();
            XBeeWiFi.setDigitalOutput(devices.get(0), 4, false);
        }*/
        
        int[] rData;
        while(true){
            try{Thread.sleep(DELAY);}catch(InterruptedException e){}
            for(XBeeWiFi device : devices){
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                rData = XBeeWiFi.readInputs(device);
                System.out.println(Arrays.toString(rData));
                //**************************************************************
                if(rData.length < 2 && rData.length > 0){
                    if(rData[0] > L_LIMIT){
                        System.out.println(device.getName()+": Erro na lâmpada");
                    } else {
                        System.out.println(device.getName()+": Funcionamento OK");
                    }
                } else {
                    if(rData.length > 0){
                        if(rData[AD2] > 0){
                            if(rData[AD1] > L_LIMIT){
                                System.out.println(device.getName()+": Erro na lâmpada");
                            } else {
                                System.out.println(device.getName()+": Funcionamento OK");
                            }
                        } else {
                            System.out.println(device.getName()+": Falha na energia");
                        }
                    }
                }
                //**************************************************************
                /*if(rData[AD1] > 0){
                    if(rData[AD2] > L_LIMIT){
                        if(!XBeeWiFi.setDigitalOutput(device, 4, true)){
                            System.out.println(device.getName()+": Erro ao enviar requisição");
                            devices = XBeeWiFi.ATND();
                            break;
                        } else {
                            if(XBeeWiFi.readInputs(device)[AD1] > L_LIMIT){
                                System.out.println(device.getName()+": Falha ao ativar lâmpada");
                            } else {
                                System.out.println(device.getName()+": Lâmpada ativada");
                            }
                        }
                    } else {
                        
                    }
                } else {
                    System.out.println(device.getName()+": Falha de alimentação");
                }*/
                
            }
        }
    }
}
