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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Defines a ZigBee device
 * 
 * @author gabriel
 */
public class XBeeWiFi {
    
    public static final int UDP = 0;
    public static final int TCP = 1;
    
    private String name;
    private XBeeAddress16 addr16;
    private XBeeAddress64 addr64;
            
    private static Socket clientSocket;
    private static DataOutputStream outToServer;
    private static DataInputStream inFromServer;
    private static int commMode;
    
    /**
     * Creates a new ZigBee device.
     * 
     * @param frame The ATND response frame from a ZigBee
     * device.
     */
    
    public XBeeWiFi(int[] frame){
        setXBee(frame);
    }
    
    public static void setSocket(String ip, int port, int comm){
        
        commMode = comm;
        if(commMode == UDP){        
            XBeeWiFiUDP.setSocket(ip, port);
        } else {        
            try{
                clientSocket = new Socket(ip, port);
                outToServer = new DataOutputStream(clientSocket.getOutputStream());
                inFromServer = new DataInputStream(clientSocket.getInputStream());
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Sends a ATND command that finds all the devices connected to the network
     * @return All XBee devices connected
     */
    public static ArrayList<XBeeWiFi> ATND(){
        
        int[] frame;
        ArrayList<XBeeWiFi> dev = new ArrayList<>();
        //RemoteAtRequest req = new RemoteAtRequest(XBeeAddress16.BROADCAST, "ND");
        //System.out.println(Arrays.toString(req.getXBeePacket().getByteArray()));
        //(new Scanner(System.in)).next();
        //send(req.getXBeePacket().getByteArray()); //sending ATND frame
        send(new int[] {0x7E, 0x00, 0x04, 0x08, 0x01, 0x6E, 0x64, 0x24}); //manual ATND
        while((frame = read()) != null){
            dev.add(new XBeeWiFi(frame));
        }
        return dev;
    }
    
    public static int[] readInputs(XBeeWiFi device){
        
        send(new RemoteAtRequest(device.getAddr64(), "IS").getXBeePacket().getByteArray());
        System.out.println(device.getName()+" Enviou");
        return makeReadInputs(read());
    }
    
    /**
     * Return: [SIO, SEA, SSA, [InputValues]]
     * 
     * @param device
     * @return 
     */
    public static int[] readIOState(XBeeWiFi device){
        
        final int SIO = 20;
        final int SEA = 21;
        final int SSA = 23;
        int[] resp, values;
        int[] state = new int[3];
        send(new RemoteAtRequest(device.getAddr64(), "IS").getXBeePacket().getByteArray());
        resp = read();
        values = makeReadInputs(resp);
        state[0] = resp[SIO];
        state[1] = resp[SEA];
        state[2] = resp[SSA];
        resp = new int[state.length+values.length];
        resp[0] = state[0];
        resp[1] = state[1];
        resp[2] = state[2];
        for(int i = 0; i < values.length; i++){
            resp[i+3] = values[i];
        }
        return resp;
    }
    
    public static boolean setDigitalOutput(XBeeWiFi device, int output, boolean value){
        
        final int BYTE_OK = 17;
        send(new RemoteAtRequest(device.getAddr64(), "D"+output, value ? 5 : 4).getXBeePacket().getByteArray());
        return read() != null; //check OK from response
    }
    
    public static boolean setPort(XBeeWiFi device, String port, int value){
        
        final int BYTE_OK = 17;
        send(new RemoteAtRequest(device.getAddr64(), port, value).getXBeePacket().getByteArray());
        return read() != null; //check OK from response
    }
    
    private static void send(int[] frame){
        
        if(commMode == UDP){
            XBeeWiFiUDP.send(frame);
        } else {
            try{
                for(int b : frame){
                    outToServer.write(b);
                    System.out.print(b+" ");
                }
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    private static int[] read(){
        
        if(commMode == UDP){
            return XBeeWiFiUDP.read();
        } else {
            int[] tFrame = new int[65355];
            int r, i, checksum;
            try{
                for(int j = 0; inFromServer.available() <= 0; j++){ //espera de 5 secs.
                    if(j > 100){
                        return null;
                    }
                    try{
                        Thread.sleep(50);
                    } catch(InterruptedException e){}
                }
                //inFromServer.read();
                int readl = 65355;
                for(i = 0; /*((r = inFromServer.read()) != 126) && */inFromServer.available() > 0; i++){
                    r = inFromServer.read();
                    tFrame[i] = r;
                    System.out.println(i+" : "+r);
                    if(i == 2){
                        readl = tFrame[1]*256+tFrame[2]+4;//limitador+size+size+[data]+checksum
                        System.out.println("TAMANHO DA FRAME CALCULADO: "+readl);
                    }
                    if(i >= readl-1){
                        break;
                    }
                    /*if(r == 126 && isChecked(tFrame, i)){
                        return tFrame;
                    }
                    if(i == 0 && r != 126){
                        tFrame[0] = 126;
                        i++;
                    }
                    tFrame[i] = r;*/
                }
                int[] frame = new int[readl];
                for(int j = 0; j < readl; j++){
                    frame[j] = tFrame[j];
                }
                if(isChecked(frame, frame.length)){
                    System.out.println(Arrays.toString(frame));
                    return frame;
                } else {
                    System.out.println("ERRO AO VERIFICAR CHECKSUM!");
                    return null;
                }
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
    }
           
    private boolean setXBee(int[] frame){
        
        System.out.println(Arrays.toString(frame));
        int[] a16 = new int[2];
        int[] a64 = new int[8];
        String nameRec = "";
        //int size = frame[1]*256+frame[2]; 
        /*if(frame[7] != 0){ //Byte 7: Status
            return false;
        }*/
        for(int i = 0; i < 2; i++){
            a16[i] = frame[8+i];
        }
        for(int i = 0; i < 8; i++){
            a64[i] = frame[10+i];
        }
        System.out.println(Arrays.toString(a64));
        setAddr16(new XBeeAddress16(a16));
        setAddr64(new XBeeAddress64(a64));
        for(int i = 18; frame[i] != 0; i++){
            char n = (char) frame[i];
            nameRec += n;
        }
        setName(nameRec);
        return true;
    }
    
    @SuppressWarnings("empty-statement")
    private static int[] makeReadInputs(int[] frame){
        
        int BYTE_START = 24;
        int BYTE_OK = 17;
        int nData, b = 21; //start search at 21st byte
        int[] dataFrame;
        /*if(frame[BYTE_OK] != 0){ //byte "OK"
            System.out.println("Error receiving data!");
            return null;
        }*/
        //*** count inputs ***
        
        //********************
        /*while(frame[b++] != 0 && b < frame.length);
        BYTE_START = (b == frame.length ? BYTE_START : b+2);
        */
        try {
            nData = (frame.length - BYTE_START)/2;
            if(nData < 0){ return (new int[] {0});}
            dataFrame = new int[nData];
            for(int i = 0; i < dataFrame.length; i++){ //BUG: dataFrame.length-1
                dataFrame[i] = frame[BYTE_START+((2*i))]*256;
                dataFrame[i] += frame[BYTE_START+((2*i)+1)];
            }
        } catch(NullPointerException | ArrayIndexOutOfBoundsException e){
            return (new int[]{0});
        }
        return dataFrame;
    }
    
    private static boolean isChecked(int[] frame, int size){
        int sum = 0, checksum;
        if(size > 3){
            for(int i = 3; i < size-1; i++){
                sum += frame[i];
            }
            checksum = 0xFF - (sum & 0xFF);
            System.out.println("Checksum "+checksum+" : "+frame[size-1]);
            return (checksum == frame[size-1]);
        } else {
            return false;
        }
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
