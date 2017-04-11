/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.RemoteAtResponse;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import java.util.Arrays;

/**
 *
 * @author gabriel
 */
public class Sender {
    
    public static void main(String args[]){
        
        desmontar(new RemoteAtResponse());
    }
    
    public static void montar(){
        
        System.out.println("Montando frame para leitura.");
        
        XBeeAddress64 addr = new XBeeAddress64(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF);
        
        RemoteAtRequest req = new RemoteAtRequest(addr, "SI", new int[1]);
        for(int i = 0; i < req.getFrameData().length; i++){
            System.out.print(req.getFrameData()[i]+" ");
        }
        
    }
    
    public static void desmontar(RemoteAtResponse resp){
        
        RemoteAtRequest req = new RemoteAtRequest(XBeeAddress16.BROADCAST, "IS");
        //int[] data = {0x7E, 0x00, 0x19, 0x97, 0x01, 0x00, 0x13, 0xA2, 0x00, 0x40, 0xB2, 0xFE, 0x63, 0x89, 0xC9, 0x49, 0x53, 0x00, 0x01, 0x00, 0x10, 0x06, 0x00, 0x10, 0x01, 0xAB, 0x00, 0x1D, 0x81};
        int[] data = {0x7E, 0x00, 0x0F, 0x97, 0x01, 0x00, 0x13, 0xA2, 0x00, 0x40, 0xB2, 0xFE, 0x63, 0x89, 0xC9, 0x44, 0x34, 0x00, 0x95};
        resp.setRawPacketBytes(data);
        
        resp.setStatus(AtCommandResponse.Status.OK);
        resp.setError(false);
        System.out.println("Received : "+Arrays.toString(resp.getRawPacketBytes()));
        System.out.println("Processed: "+Arrays.toString(resp.getProcessedPacketBytes()));
        System.out.println("Is ok? : "+resp.isOk());
        System.out.println("API: "+resp.getApiId());
        System.out.println("Tamanho: "+resp.getLength());
        System.out.println("Frame: "+resp.getFrameId());
        System.out.println("Endereço de rede: "+resp.getRemoteAddress16());
        System.out.println("Endereço físico: "+resp.getRemoteAddress64());
        System.out.println("Char 1: "+resp.getChar1());
        System.out.println("Char 2: "+resp.getChar2());
        System.out.println("Value: "+Arrays.toString(resp.getValue()));
        
        System.out.println("API: "+req.getApiId());
        System.out.println("Frame: "+req.getFrameId());
        System.out.println("Endereço de rede: "+req.getRemoteAddr16());
        System.out.println("Endereço físico: "+req.getRemoteAddr64());
        System.out.println("Array de bytes: "+Arrays.toString(req.getXBeePacket().getByteArray()));
        
    }
    
}
