/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imec.v2.controller;

import com.imec.v2.view.FormIncidentePrincipal;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author aaraujo
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for ( UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
                if ("Windows".equals( info.getName())) {
                    UIManager.setLookAndFeel( info.getClassName() );
                    FormIncidentePrincipal form = new FormIncidentePrincipal();
                    form.setVisible(true);            
                    break;
                } 
            }
        } catch ( UnsupportedLookAndFeelException exc ) {
            exc.printStackTrace();
        } catch ( ClassNotFoundException exc ) {
            exc.printStackTrace();
        } catch ( InstantiationException exc ) {
            exc.printStackTrace();
        } catch ( IllegalAccessException exc ) {
            exc.printStackTrace();
        }
        
    }
    
}