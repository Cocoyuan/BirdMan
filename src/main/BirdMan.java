package main;

import java.applet.Applet;
import java.applet.AudioClip;

import controller.MainController;
import view.BirdManFrame;

/**
 * Created by HighLengCo on 15/10/27.
 */


public class BirdMan {
    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.getComponent().setVisible(true);
        
    }
}
