/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 * General definition of a instruction
 * 
 * @author gabriel
 */
public abstract class Ins {
    
    protected String addr;
    protected boolean global;

    public Ins(String addr) {
        this.addr = addr;
    }

    public Ins(String addr, boolean global) {
        this.addr = addr;
        this.global = global;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }
    
}
