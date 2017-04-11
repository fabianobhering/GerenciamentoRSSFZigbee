/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 * This class defines a simple conditional instruction, that will
 * be executed if the condition is satisfied.
 *
 * @author gabriel
 */
public class ConIns extends Ins {
    
    //private String readDevice;
    private String writeDevice;
    private int portInIndex;
    private String portOut;
    private String oprd;
    private int conV;
    private boolean setV;

    /**
     * 
     * @param readDevice MAC address of the input device
     * @param writeDevice MAC address of the output device
     * @param portInIndex Input port index
     * @param portOut Output port name
     * @param oprd Comparation operand (>, <, =)
     * @param conV Comparation value
     * @param setV Set value to the output port (true or false)
     * @param global "Is global?"
     */
    
    public ConIns(String readDevice, String writeDevice, int portInIndex, String portOut, String oprd, int conV, boolean setV, boolean global) {
        //this.readDevice = readDevice; //dispositivo que realiza a leitura
        super(readDevice); //neste caso, o dispositivo responsável pela instrução
                           //é o que faz a leitura do dado
        this.writeDevice = writeDevice; //dispositivo que realizará a atuação
        this.portInIndex = portInIndex; //index da porta analógica de entrada
        this.portOut = portOut; //porta de saída
        this.oprd = oprd; //operador de comparação
        this.conV = conV; //valor de comparação
        this.setV = setV; //valor a ser definido na porta de saída
        this.global = global; //global
    }

    public String getReadDevice() { //métodos get e set mantidos, realizando
        return getAddr();           //chamadas ao getAddr() da superclasse
    }

    public void setReadDevice(String readDevice) {
        setAddr(readDevice);
    }

    public String getWriteDevice() {
        return writeDevice;
    }

    public void setWriteDevice(String writeDevice) {
        this.writeDevice = writeDevice;
    }
    
    public int getPortIn() {
        return portInIndex;
    }

    public void setPortIn(int portIn) {
        this.portInIndex = portIn;
    }

    public String getPortOut() {
        return portOut;
    }

    public void setPortOut(String portOut) {
        this.portOut = portOut;
    }

    public String getOprd() {
        return oprd;
    }

    public void setOprd(String oprd) {
        this.oprd = oprd;
    }

    public int getConV() {
        return conV;
    }

    public void setConV(int conV) {
        this.conV = conV;
    }

    public boolean getSetV() {
        return setV;
    }

    public void setSetV(boolean setV) {
        this.setV = setV;
    }
    
    @Override
    public String toString(){
        String r = "IF ";
        r += getReadDevice() + " " + getPortIn() + " " + getOprd() + " " + getConV() + " > " + getWriteDevice() + " " + getPortOut() + " " + getSetV() + " " + isGlobal();
        return r;
    }
    
}
