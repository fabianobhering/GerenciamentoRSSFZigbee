/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 * This class defines a delay instruction for a device
 * 
 * @author gabriel
 */
public class Delay extends Ins {
    
    private int time;

    /**
     * 
     * @param time Time (in milliseconds) of delay
     * @param addr MAC address of the device
     */
    
    public Delay(int time, String addr) {
        super(addr);
        this.time = time;
        this.global = addr.equals("GLOBAL");
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    @Override
    public String toString(){
        return "DELAY " + getAddr() + " " + getTime();
    }
    
}
