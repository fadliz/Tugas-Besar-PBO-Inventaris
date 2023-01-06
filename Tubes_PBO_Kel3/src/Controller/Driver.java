/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Application;
import viewConsole.Console;
/**
 *
 * @author asama
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application app = new Application();
        //GUI nya
        new Controller(app);
        
        //Aplikasi Console
        Console view = new Console(app);
        //view.menuUtama();
    }
    
}
