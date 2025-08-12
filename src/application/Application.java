/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package application;

import application.utils.DatabaseUtil;
import application.views.*;
import javax.swing.JFrame;

/**
 *
 * @author mhdjanuar
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
//            Config.load();
//            DatabaseUtil.getInstance().getConnection();
             new LoginView().start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
