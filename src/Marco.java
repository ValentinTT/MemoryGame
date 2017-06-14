
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class Marco extends JFrame{
	
	Lienzo lienzo;
	public Marco(){
		setTitle("Memory Game");
		setLocation(200, 200);
		setResizable(false);
		
		try{
			ObjectInputStream guardado = new ObjectInputStream(new FileInputStream("src/Guardado.txpt"));
			lienzo = (Lienzo) guardado.readObject();	
			guardado.close();
		}catch(Exception e){
			System.out.println("No se reinicio partida guardada");
			lienzo = new Lienzo(); 
		}finally{
			add(lienzo);
		}
		addWindowListener(new GuardarPartida());
		
		setSize(440, 575);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class GuardarPartida extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			try{
				ObjectOutputStream guardando = new ObjectOutputStream(new FileOutputStream("src/Guardado.txt"));
				guardando.writeObject(lienzo);
				guardando.close();
				System.out.println("HECHO");
			}catch(Exception er){
				System.out.println("No se Guardo");
			}
		}
	}
}
