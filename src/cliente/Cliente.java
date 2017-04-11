/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import com.rapplogic.xbee.api.PacketParser;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeePacket;
import com.rapplogic.xbee.api.XBeeParseException;
import com.rapplogic.xbee.util.ByteUtils;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author stephane
 */
public class Cliente {
/*
 * Simple TCP Client
 * 
 * 18.04.2012
 * by Laurid Meyer
 * 
 * http://www.ahorndesign.com
 * 
 */
	//Ip Adress and Port, where the Arduino Server is running on
	private static final String serverIP="192.168.1.50";
	private static final int serverPort=9750;
       
	
	 public static void main(String argv[]) throws Exception
	 {
		
		  String msgToServer;//Message that will be sent to Arduino
		  String msgFromServer;//recieved message will be stored here
		  Socket clientSocket = new Socket(serverIP, serverPort);//making the socket connection
		  System.out.println("Connected to:"+serverIP+" on port:"+serverPort);//debug
		  //OutputStream to Arduino-Server
		 
		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  //BufferedReader from Arduino-Server
		  //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//
                  DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());//testing DataInputStream
                  
                  //ATND
                 //int[] packet ={0x7e, 0, 0x04, 0x08, 0x11, 0x4e, 0x44, 0x54};
                 RemoteAtRequest req = new RemoteAtRequest(XBeeAddress16.BROADCAST, "IS");
                 //int[] packet = req.getXBeePacket().getByteArray();
   
                 int tempo=0;
                //acender 
                   //int[] packet ={0x7e,0,0x10,0x17,0x01,0,0,0,0,0,0,0xff,0xff,0xff,0xfe,0x02,0x44,0x34,0x05,0x6d};
 
                // apagar    
                   int[] packet ={0x7e,0,0x10,0x17,0x01,0,0,0,0,0,0,0xff,0xff,0xff,0xfe,0x02,0x44,0x34,0x04,0x6e};
              	
                 
		 long start_time = System.currentTimeMillis();
					
                  for (int packetByte : packet) {
                          outToServer.write(packetByte);
                           
                        }
                  System.out.println("enviou pacote");
              //outToServer.writeByte('\n');
                  /*
                  msgToServer="teste";
                  outToServer.writeBytes(msgToServer+'\n');//sending the message
		 // System.out.println("sending to Arduino-Server: "+msgToServer);//debug
		*/
		//  while(true){
		 // msgFromServer = inFromServer.read();//recieving the answer
                /* PacketParser parser = new PacketParser(clientSocket.getInputStream()); //using parser
                 for(int i = 0; i < 28; i++){
                     Thread.sleep(50);
                     int r = parser.read();
                     System.out.println(r);
                 }*/
                 
                 int size, type, frameID, status, checksum, ports;
                 int[] addr64 = new int[8];
                 int[] addr16 = new int[2];
                 int[] atCommand = new int[2];
                 int[] res = new int[2];
                 char[] cType = new char[1];
                 
                 inFromServer.read(); //byte 0x7E
                 size = inFromServer.read()*256;
                 size += inFromServer.read();
                 type = inFromServer.read();
                 frameID = inFromServer.read();
                 for(int i = 0; i < 8; i++){
                     addr64[i] = inFromServer.read();
                 }
                 for(int i = 0; i < 2; i++){
                     addr16[i] = inFromServer.read();
                 }
                 for(int i = 0; i < 2; i++){
                     atCommand[i] = inFromServer.read();
                 }
                 status = inFromServer.read();
                 inFromServer.read(); //readings
                 inFromServer.read(); //reserved
                 /*for(int i = 0; i < 2; i++){ //IO and analogic in sum
                     inFromServer.read();
                 }*/
                 ports = inFromServer.read();
                 
                 inFromServer.read(); //reserved
                 inFromServer.read(); //digital outputs active
                 for(int i = 0; i < 2; i++){ //IO and analogic in sum
                     res[i] = inFromServer.read()*256;
                     res[i] += inFromServer.read();
                 }
                 checksum = inFromServer.read();
                 
                 System.out.println("Tamanho: "+size);
                 System.out.println("Tipo: "+ByteUtils.formatByte(type));
                 //System.out.println("Tipo: "+Integer.valueOf(cType[0]));
                 System.out.println("ID: "+frameID);
                 System.out.println("addr64: "+Arrays.toString(addr64));
                 System.out.println("addr16: "+Arrays.toString(addr16));
                 System.out.println("AT Command: "+Arrays.toString(atCommand));
                 System.out.println("Status: "+status);
                 System.out.println("Read 1: "+res[0]);
                 System.out.println("Read 2: "+res[1]);
                 
		  /*System.out.println("\nrecieved: " + inFromServer.readLine());//print the answer
                  long end_time = System.currentTimeMillis();
                  long difference = end_time-start_time; 
                 // start_time = TimeUnit.MILLISECONDS.toSeconds(start_time);
                  System.err.println("Tempo de inicio: "+start_time);
                  
                 // end_time = TimeUnit.MILLISECONDS.toSeconds(end_time);
                  System.err.println("Tempo fiiinal: "+end_time);
                   
                   
                 
                  System.err.println("latencia: "+difference);*/
		 


//                    System.out.println("recieved: " + inFromServer.readLine());//print the answer
		
                  
		  //don't do this if you want to keep the connection
		
                
	 

    
         }
         
}