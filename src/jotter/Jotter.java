package jotter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Jotter {

    JMenu fileMenu = new JMenu("File");  // open, save, close
    JMenu editMenu = new JMenu("Edit");  // todo
    JMenu helpMenu = new JMenu("Help");  // about
    JTextArea textArea = new JTextArea();

    // menu items
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem quitItem = new JMenuItem("Quit");
    JMenuItem aboutItem = new JMenuItem("About");

    public static void main(String[] args) {
        setLooks();
        Jotter jotter = new Jotter();
        jotter.design();
    }

    public void design() {
        JFrame frame = new JFrame("Simple Jotter");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main menu
        JMenuBar menuPanel = new JMenuBar();
        menuPanel.add(fileMenu);
        menuPanel.add(editMenu);
        menuPanel.add(helpMenu);

        // file menu items
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(quitItem);

        // help menu items
        helpMenu.add(aboutItem);
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(frame, "Version 0.0.1");
            }
        });

        // setting the fonts
//        fileMenu.setFont(new Font("Arial", Font.BOLD, 15));
//        editMenu.setFont(new Font("Arial", Font.BOLD, 15));
//        helpMenu.setFont(new Font("Arial", Font.BOLD, 15));
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        // adding components
        frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.setVisible(true);
    }

    static void setLooks() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
    }

}
