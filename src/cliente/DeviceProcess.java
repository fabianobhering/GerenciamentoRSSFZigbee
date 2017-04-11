/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 * Define the Thread of a device.
 * 
 * @author gabriel
 */
public class DeviceProcess extends Thread {
    
    private XBeeWiFi device;

    public DeviceProcess(XBeeWiFi device) {
        this.device = device;
    }
    
    @Override
    public void run(){
        
        while(true){
            if(XBeeWiFiControl.PROG_WORKING){
                Exec.runAll(device);
            }
        }
        
    }

    public XBeeWiFi getDevice() {
        return device;
    }

    public void setDevice(XBeeWiFi device) {
        this.device = device;
    }
    
}
