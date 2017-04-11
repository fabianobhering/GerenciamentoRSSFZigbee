/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Saves the program to a file with .zbp extension and loads
 * the written program in a file back to the application.
 * 
 * @author gabriel
 */
public class DataLoader {
    
    /**
     * 
     * @param name File name
     * @param data Array of instructions to be saved 
     */
    
    public static void save(String name, ArrayList<Ins> data){
        String sData = "";
        for(Ins o : data){
            if(o instanceof ConIns){
                ConIns i = (ConIns) o;
                sData += i.toString();
            } else {
                if(o instanceof LogIns){
                    LogIns i = (LogIns) o;
                    sData += i.toString();
                } else {
                    Delay i = (Delay) o;
                    sData += i.toString();
                }
            }
            sData += "\n";
        }
        try {
            File f = new File(name);
            if(!f.exists()){
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f, false);
            PrintWriter pw = new PrintWriter(fw);
            pw.write(sData);
            pw.close();
            fw.close();
        } catch(IOException ex) {
            
        }
    }
    
    /**
     * 
     * @param name File name
     * @return Array of instructions inside the specified file
     */
    
    public static ArrayList<Ins> load(String name){
        ArrayList<Ins> r;
        File f = new File(name);
        if(!f.exists()){
            return null;
        }
        try {
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line;
            String[] cmd;
            r = new ArrayList<>();
            while(bf.ready()){
                line = bf.readLine();
                cmd = line.split(" ");
                if(cmd[0].equals("IF")){
                    r.add(new ConIns(cmd[1], cmd[6], Integer.parseInt(cmd[2]), cmd[7], cmd[3], Integer.parseInt(cmd[4]), cmd[8].equals("true"), cmd[9].equals("true")));//cmd[4] == '>'
                }
                if(cmd[0].equals("DELAY")){
                    r.add(new Delay(Integer.parseInt(cmd[2]), cmd[1]));
                }
                if(cmd[0].equals("LOG")){
                    r.add(new LogIns(cmd[1], Integer.parseInt(cmd[2]), cmd[4], cmd[5].equals("true")));
                }
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex){
            return null;
        }
        return r;
    }
    
    public static boolean fileExists(String name){
        return (new File(name)).exists();
    }
    
}
