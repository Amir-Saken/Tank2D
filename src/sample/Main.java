package sample;
import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) throws Exception {

        JFrame obj=new JFrame();
        Tank gamePlay = new Tank();

        obj.setBounds(10, 10, 800, 630);
        obj.setTitle("Tank 2D");
        obj.setBackground(Color.gray);

        obj.setResizable(false);

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        obj.setVisible(true);

    }
}
