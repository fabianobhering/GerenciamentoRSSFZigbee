/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import com.rapplogic.xbee.api.RemoteAtRequest;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author gabriel
 */
public class XBeeWiFiUDP {
    
    private String name;
    private XBeeAddress16 addr16;
    private XBeeAddress64 addr64;
    
    private static DatagramSocket clientSocket;
    private static InetAddress serverIP;
    private static int port;
    
    /**
     * Creates a new ZigBee device.
     * 
     * @param frame The ATND response frame from one ZigBee
     * device.
     */
    
    public XBeeWiFiUDP(int[] frame){
        setXBee(frame);
        
    }
    
    public static void setSocket(String IP, int p){
        
        port = p;
        try{
            serverIP = InetAddress.getByName(IP);
            clientSocket = new DatagramSocket();
        } catch(UnknownHostException | SocketException e){
            System.out.println("Erro ao criar socket: "+e.getMessage());
        }
    }

    
    public static ArrayList<XBeeWiFiUDP> ATND(){
        
        int[] frame;
        ArrayList<XBeeWiFiUDP> dev = new ArrayList<>();
        RemoteAtRequest req = new RemoteAtRequest(XBeeAddress16.BROADCAST, "ND");
        System.out.println(Arrays.toString(req.getXBeePacket().getByteArray()));
        (new Scanner(System.in)).next();
        //send(req.getXBeePacket().getByteArray()); //sending ATND frame
        send(new int[] {0x7E, 0x00, 0x04, 0x08, 0x01, 0x6E, 0x64, 0x24}); //manual ATND
        while((frame = read()) != null){ //  warning!! (delay)
            dev.add(new XBeeWiFiUDP(frame));
        }
        return dev;
    }
    
    public static int[] readInputs(XBeeWiFi device){
        
        RemoteAtRequest req = new RemoteAtRequest(device.getAddr16(), "IS");
        return makeReadInputs(read()); // warning!! (delay)
    }
    
    public static boolean setDigitalOutput(XBeeWiFi device, int output, boolean value){
        
        final int BYTE_OK = 17;
        RemoteAtRequest req = new RemoteAtRequest(device.getAddr16(), "D"+output, value ? 5 : 4);
        return read()[17] == 0; //check OK from response
    }
    
    public static void send(int[] frame){
        
        byte[] data = new byte[frame.length];
        for(int i = 0; i < data.length; i++){
            data[i] = (byte) frame[i];
        }
        try{
            clientSocket.send(new DatagramPacket(data, data.length, serverIP, port));
        } catch(IOException e){
            System.out.println("Erro ao enviar: "+e.getMessage());
        }
    }
    
    public static int[] read(){
        
        byte[] tFrame = new byte[100];
        try{
            System.out.println("Aguardando");
            clientSocket.receive(new DatagramPacket(tFrame, tFrame.length));
            System.out.println("Aguardando");
            
            int[] frame = new int[tFrame.length];
            for(int j = 0; j < frame.length; j++){
                frame[j] = (int) tFrame[j];
            }
            return frame;
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
           
    private boolean setXBee(int[] frame){
        
        int[] a16 = new int[2];
        int[] a64 = new int[8];
        String nameRec = "";
        //int size = frame[1]*256+frame[2]; 
        if(frame[7] != 0){ //Byte 7: Status
            return false;
        }
        for(int i = 0; i < 2; i++){
            a16[i] = frame[8+i];
        }
        for(int i = 0; i < 8; i++){
            a64[i] = frame[10+i];
        }
        addr16 = new XBeeAddress16(a16);
        addr64 = new XBeeAddress64(a64);
        for(int i = 18; frame[i] != 0; i++){
            char n = (char) frame[i];
            nameRec += n;
        }
        name = nameRec;
        return true;
    }
    
    private static int[] makeReadInputs(int[] frame){
        
        final int BYTE_START = 24;
        final int BYTE_OK = 17;
        int nData;
        int[] dataFrame;
        if(frame[BYTE_OK] != 0){ //byte "OK"
            System.out.println("Error receiving data!");
            return null;
        }
        //*** count inputs ***
        
        //********************
        nData = (frame.length - BYTE_START+1)/2;
        dataFrame = new int[nData];
        for(int i = 0; i < dataFrame.length; i++){
            dataFrame[i] += frame[BYTE_START+(2*i)]*256;
            dataFrame[i] += frame[BYTE_START+(2*i+1)];
        }
        return dataFrame;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XBeeAddress16 getAddr16() {
        return addr16;
    }

    public void setAddr16(XBeeAddress16 addr16) {
        this.addr16 = addr16;
    }

    public XBeeAddress64 getAddr64() {
        return addr64;
    }

    public void setAddr64(XBeeAddress64 addr64) {
        this.addr64 = addr64;
    }
    
}

