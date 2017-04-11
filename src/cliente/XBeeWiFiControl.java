/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabriel
 * 
 * Identificar configuração das portas OK
 * Interface para configuração de portas
 * Inserir medição de valores na interface de saídas OK
 * Ajustar interface conforme configuração do dispositivo OK
 * Interface para desenvolvimento do fluxo de controle (Loop)
 */
public class XBeeWiFiControl extends javax.swing.JFrame implements ActionListener {

    public static boolean PROG_WORKING = false;
    public static final String SAVE_FILE = /*"/home/pi/Projeto/prog.zbp"*/"prog.zbp";
    
    private String ip;
    private int port;
    private static ArrayList<XBeeWiFi> devices;
    private static ArrayList<DeviceProcess> devProcs;
    private boolean firstRun = true;
    DefaultTableModel tableModel;
    Object[] data = new Object[3];
    Timer t;
    /**
     * Creates new form XBeeWiFiControl
     */
    public XBeeWiFiControl() {
        initComponents();
        UDP.setSelected(true);
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Dispositivo");
        tableModel.addColumn("Net Addr");
        tableModel.addColumn("MAC Addr");
        table.setModel(tableModel);
        t = new Timer(1, this);
        t.start();
        tIP.setText("192.168.1.99");
        tPort.setText("9750");
        TCP.setSelected(true);
        /*data[0] = "Disp";
        data[1] = "144.53";
        data[2] = "45:54:a4:e1:ee:11";
        tableModel.addRow(data);*/
        jToggleButton1.setSelected(false);
        jPanel1.setBackground(Color.red);
        
        /*
        A inicialização abaixo não funciona.
        Adicionar mais sleeps (em vários lugares)
        */
        System.out.println("a");
        if((new File("auto.lock")).exists()){
            // AUTOSTART
            //while(jTable1.getRowCount() == 0){
                System.out.println("file ok");
                refreshDevices();
                System.out.println("pass");
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException ex) {

                }
            //}
            //toggleProg();
            PROG_WORKING = true;
            jPanel1.setBackground(Color.green);
            // ------------ START THREADS ------------
            for(DeviceProcess p : devProcs){
                p.start();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {

            }
            // ---------------------------------------
        }
        // ---------
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tIP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tPort = new javax.swing.JTextField();
        UDP = new javax.swing.JRadioButton();
        TCP = new javax.swing.JRadioButton();
        config = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        config1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Identificar dispositivos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(table);

        jLabel1.setText("ZigBee WiFi");

        jLabel2.setText("IP");

        jLabel3.setText("Porta");

        buttonGroup1.add(UDP);
        UDP.setText("UDP");

        buttonGroup1.add(TCP);
        TCP.setText("TCP");

        config.setText("Configurar Portas");
        config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configActionPerformed(evt);
            }
        });

        jButton4.setText("Modificar saídas");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        config1.setText("Programar");
        config1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                config1ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("ON/OFF");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(139, 139, 139))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tIP, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tPort, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UDP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TCP))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(config, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(config1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UDP)
                    .addComponent(TCP))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(config)
                    .addComponent(jButton4)
                    .addComponent(config1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refreshDevices();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void refreshDevices(){
        ip = tIP.getText();
        port = Integer.parseInt(tPort.getText());
        XBeeWiFi.setSocket(ip, port, UDP.isSelected() ? XBeeWiFi.UDP : XBeeWiFi.TCP);
        devices = XBeeWiFi.ATND();
        devProcs = new ArrayList<>();
        for(XBeeWiFi device : devices){
            data[0] = device.getName();
            data[1] = device.getAddr16();
            data[2] = device.getAddr64();
            tableModel.addRow(data);
            
            //Criando o objeto DeviceProcess para cada dispositivo
            devProcs.add(new DeviceProcess(device));
        }
        devProcs.add(new DeviceProcess(null));
        Exec.setIns(DataLoader.load(SAVE_FILE));
    }
    
    private void configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configActionPerformed
        int sIndex = table.getSelectedRow();
        if(sIndex != -1){
            Config zb = new Config(devices.get(sIndex));
            zb.setVisible(true);
        }
    }//GEN-LAST:event_configActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int sIndex = table.getSelectedRow();
        if(sIndex != -1){
            ZigBeeOutputs zb = new ZigBeeOutputs(devices.get(sIndex));
            zb.setVisible(true);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void config1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_config1ActionPerformed
        //int sIndex = table.getSelectedRow();
        if(true){
            Prog zb = new Prog();
            zb.setVisible(true);
        }
    }//GEN-LAST:event_config1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        toggleProg();
    }//GEN-LAST:event_jToggleButton1ActionPerformed
    
    private void toggleProg(){
        if(jToggleButton1.isSelected()){
            PROG_WORKING = true;
            jPanel1.setBackground(Color.green);
            // ------------ START THREADS ------------
            for(DeviceProcess p : devProcs){
                if(firstRun){
                    p.start();
                } else {
                    p.notify();
                }
            }
            // ---------------------------------------
        } else {
            PROG_WORKING = false;
            jPanel1.setBackground(Color.red);
            // ------------ STOP THREADS ------------
            for(DeviceProcess p : devProcs){
                try {
                    p.wait();
                } catch (InterruptedException ex) {
                    
                }
            }
            // --------------------------------------
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(PROG_WORKING){
            if(devices != null){
                /*
                Todas as instruções de um dado dispositivo devem ser executadas
                em um Thread específica para tal. Cada Thread consistirá de um
                único laço, perpétuo, que realizará a chamada ao método runAll, 
                passando o dispositivo em questão como parâmetro, além da Thread
                global. É importante lembrar que a execuxão dos comandos só deve
                ser realizada se a flag PROG_WORKING estiver em true, caso
                contrário, toda a execução deve ser parada. Isso indica que cada
                objeto Thread deve ser armazenado, para que se lhe possa pausar
                a execução.
                */
                //Exec.runAll();
                /*for(XBeeWiFi device : devices){
                    device.getProg().runAll();
                }*/
            }
        }
    }
    
    public static XBeeWiFi getDevice(int i){
        return devices.get(i);
    }
    
    public static XBeeWiFi getDevice(String macAddr){
        for(XBeeWiFi d : devices){
            if(d.getAddr64().toString().equals(macAddr)){
                return d;
            }
        }
        return null;
    } 
    
    public static ArrayList<XBeeWiFi> getDevices(){
        return devices;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(XBeeWiFiControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XBeeWiFiControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XBeeWiFiControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XBeeWiFiControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new XBeeWiFiControl().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton TCP;
    private javax.swing.JRadioButton UDP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton config;
    private javax.swing.JButton config1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextField tIP;
    private javax.swing.JTextField tPort;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    
}