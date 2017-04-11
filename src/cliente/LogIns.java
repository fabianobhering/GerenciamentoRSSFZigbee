/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 * Defines the log instruction
 * 
 * @author root
 */
public class LogIns extends Ins {
    
    private int port;
    private String nomeArq;

    /**
     * 
     * @param addr MAC address of the device
     * @param port Port that will be recorded
     * @param nomeArq Log file name
     * @param global "Is global?"
     */
    public LogIns(String addr, int port, String nomeArq, boolean global) {
        super(addr);
        this.port = port;
        this.nomeArq = nomeArq;
        this.global = global;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNomeArq() {
        return nomeArq;
    }

    public void setNomeArq(String nomeArq) {
        this.nomeArq = nomeArq;
    }
    
    @Override
    public String toString(){
        return "LOG "+ getAddr() + " " + getPort() +" FILE "+ getNomeArq() + " " + isGlobal();
    }
    
}
