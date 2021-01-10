package jotter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class Jotter {

    JFrame frame = new JFrame("Simple Jotter");
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
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
    JMenu color = new JMenu("Color");
    JMenuItem bgColor = new JMenuItem("Background Color");
    JMenuItem fontColor = new JMenuItem("Font Color");
    JMenu lineWrap = new JMenu("Line Wrap");
    JMenuItem lineWrapOn = new JMenuItem("ON");
    JMenuItem lineWrapOff = new JMenuItem("OFF");

    JPanel lineNumbers = new JPanel();

    public static void main(String[] args) {
        setLooks();
        Jotter jotter = new Jotter();
        jotter.design();
        jotter.buttonFunctions();
    }

    public void design() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // testing side bar feature
        JButton but = new JButton("test");

        lineNumbers.add(but);

        // end of test

        // main menu
        JMenuBar menuPanel = new JMenuBar();
        menuPanel.add(fileMenu);
        menuPanel.add(editMenu);
        menuPanel.add(helpMenu);

        // file menu items
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // edit menu items
        color.add(fontColor);
        color.add(bgColor);
        editMenu.add(color);
        lineWrap.add(lineWrapOn);
        lineWrap.add(lineWrapOff);
        editMenu.add(lineWrap);

        // help menu items
        helpMenu.add(aboutItem);

        // adding components
        frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.getContentPane().add(BorderLayout.WEST, lineNumbers);
        frame.setVisible(true);
    }

    static void setLooks() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
    }

    void buttonFunctions() {
        JFileChooser fileChooser = new JFileChooser();

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                textArea.setText("");
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int response = fileChooser.showSaveDialog(frame);
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                            PrintWriter printWriter = new PrintWriter(bufferedWriter, true);) {

                        printWriter.println(textArea.getText());  // writes content to the file
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage());
                    }
                }
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int response = fileChooser.showOpenDialog(frame);
                if (response == JFileChooser.APPROVE_OPTION) {

                    textArea.setText("");  // clears the text area before writing
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    try (FileReader fileReader = new FileReader(file);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);) {

                        String currentLine;
                        while ((currentLine = bufferedReader.readLine()) != null) {
                            textArea.append(currentLine + "\n");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage());
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
                JOptionPane.showMessageDialog(frame, "Version 0.0.7");
            }
        });

        fontColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Color fontColor = JColorChooser.showDialog(frame, "Choose Text Color", Color.BLACK);
                textArea.setForeground(fontColor);
            }
        });

        bgColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Color fontColor = JColorChooser.showDialog(frame, "Choose Background Color", Color.WHITE);
                textArea.setBackground(fontColor);
            }
        });

        lineWrapOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
            }
        });

        lineWrapOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                textArea.setLineWrap(false);
                textArea.setWrapStyleWord(false);
            }
        });
    }
}
