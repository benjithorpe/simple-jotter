package jotter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class Jotter {

    JFrame frame = new JFrame("Simple Jotter");
    JMenu fileMenu = new JMenu("File");
    JMenu helpMenu = new JMenu("Help");
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
        frame.setLocation(200, 150);

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
        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
//        textArea.setWrapStyleWord(true); // wraps by word

        // adding components
        frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
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
                // TODO: sent new file to tabbed menu on the text editor
                // consideration: show warning before erasing text area
                textArea.setText("");
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser openNewFile = new JFileChooser();
                int response = openNewFile.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    // gets the specified file
                    File file = new File(openNewFile.getSelectedFile().getAbsolutePath());
                    try (FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);) {

                        String currentLine;
                        String content = "";
                        while ((currentLine = bufferedReader.readLine()) != null) {
                            content += currentLine + "\n";
                        }
                        textArea.setText(content);
                    } catch (Exception e) {
                    }
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
