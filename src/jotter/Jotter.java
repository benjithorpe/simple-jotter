package jotter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Jotter {

    JFrame frame = new JFrame("Simple Jotter");
    JMenu fileMenu = new JMenu("File");
    JMenu helpMenu = new JMenu("Help");
    JTextArea textArea = new JTextArea();

    // menu items
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem aboutItem = new JMenuItem("About");

    public static void main(String[] args) {
        setLooks();
        Jotter jotter = new Jotter();
        jotter.design();
        jotter.buttonFunctions();
    }

    public void design() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main menu
        JMenuBar menuPanel = new JMenuBar();
        menuPanel.add(fileMenu);
        menuPanel.add(helpMenu);

        // file menu items
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // help menu items
        helpMenu.add(aboutItem);

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

    void buttonFunctions() {

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // create new file
                Jotter newJotter = new Jotter();
                newJotter.design();
                newJotter.buttonFunctions();
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser openNewFile = new JFileChooser();
                int response = openNewFile.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(openNewFile.getSelectedFile().getAbsolutePath());
                    System.out.println("Path: " + file);
                }
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser saveFile = new JFileChooser();
                int response = saveFile.showSaveDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    // gets the specified file
                    File filename = new File(saveFile.getSelectedFile().getAbsolutePath());
                    try (FileWriter fileWriter = new FileWriter(filename);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        PrintWriter printWriter = new PrintWriter(bufferedWriter, true);) {

                        printWriter.println(textArea.getText());  // writes content to the file

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(frame, "Version 0.0.2");
            }
        });

    }
}
