package io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import lexer.Lexer;
import parser.Parser;
import processing.TreeProcessing;
import tree.CodeGenerator;
import tree.SyntaxTree;

/**
 * 
 * @author Flip van Spaendonck
 *
 */
public class View {
	
	private final JFileChooser fileChooser;
	
	private File lastFileLocation = null;
	
		
	public View() {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Simple Programming Language File",".spl"));
		
		final JFrame frame = new JFrame("SPL Compiler Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080, 920);
		final JTextArea textArea = new JTextArea();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem itemLoadFile = new JMenuItem("Load");
		itemLoadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setVisible(true);
				if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					lastFileLocation = fileChooser.getSelectedFile();
					textArea.setText("");
					try {
						FileReader fr = new FileReader(lastFileLocation);
						BufferedReader reader = new BufferedReader(fr);
						while(reader.ready()) {
							textArea.setText(textArea.getText() +"\n"+ reader.readLine());
						}
						reader.close();
						fr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		fileMenu.add(itemLoadFile);
		
		JMenuItem itemSaveFile = new JMenuItem("Save");
		itemSaveFile.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					lastFileLocation = fileChooser.getSelectedFile();
					try {
						FileWriter fw = new FileWriter(lastFileLocation);
						System.out.print(textArea.getText());
						fw.write(textArea.getText());
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fileMenu.add(itemSaveFile);
		JMenuItem compileFile = new JMenuItem("Compile");
		compileFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Lexer l = new Lexer(textArea.getText());
				Parser p = new Parser(l);
				try {
					
					SyntaxTree t = TreeProcessing.processIntoAST(p.tree.root);
					System.out.println("=====");
					System.out.println(t);
					System.out.println("=====");
					
					System.out.println(TreeProcessing.checkWellTyped(t));
					System.out.println(CodeGenerator.generateCode(t));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		fileMenu.add(compileFile);
		menuBar.add(new JTextField("Het Geheime wachtwoord veld"));
		frame.add(menuBar, BorderLayout.NORTH);
		//The Textare in which the user can write the code.
		
		textArea.setMinimumSize(new Dimension(640, 480));
		textArea.setTabSize(4);
		frame.add(textArea);
		
		
		
		frame.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		new View();
	}
}
