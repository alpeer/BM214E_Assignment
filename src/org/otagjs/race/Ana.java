package org.otagjs.race;

import javax.swing.*;

import static org.otagjs.race.Uygulama.*;

public class Ana {
    public static void main (String[] args){
        JFrame frame = new JFrame();

        frame.setContentPane(new Uygulama().APPView);
        frame.getContentPane().setSize(1000,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
