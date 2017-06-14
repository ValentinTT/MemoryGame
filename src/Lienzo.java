import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class Lienzo extends JPanel{
	LienzoTablero tablero;
	LienzoPuntaje puntaje;
	
	public Lienzo(){
		setLayout(new BorderLayout());
		puntaje = new LienzoPuntaje(Color.BLACK);//Tablero que indica los puntos y movimientos;
		add(puntaje, BorderLayout.SOUTH);
		tablero = new LienzoTablero(Color.BLACK, puntaje);//Crea el lienzo con las fichas.
		add(tablero, BorderLayout.CENTER);
	}
	public void reiniciar(){//Reinicia los paneles de fichas y datos(puntaje);
		puntaje.reiniciarPuntaje();
		tablero.reiniciarTablero();
	}
	class LienzoPuntaje extends JPanel{
		private int mov = 0, pun = 0;//Numero de movimientos iniciales
		private JButton iniciar;//Boton que reinicia el juego.
		private JLabel movimientos, puntos;
		
		public LienzoPuntaje(Color fondo){
			setBackground(fondo);
			setLayout(new BorderLayout());
			
			iniciar = new JButton("Start");
			iniciar.addActionListener(new ActionListener(){//Lama al método reiniciar que hereda de la clase Lienzo.
				public void actionPerformed(ActionEvent e){
					if(iniciar.getText() == "Start") return;
					reiniciar();
				}
			});
			//Creacion de las etiquetas de puntaje y movimientos
			Font fuente = new Font("Arial", Font.PLAIN, 20);
			movimientos = new JLabel("Movements: 0", JLabel.CENTER);
			movimientos.setFont(fuente);
			movimientos.setForeground(Color.WHITE);
			puntos = new JLabel("Points: 0  ");
			puntos.setFont(fuente);
			puntos.setForeground(Color.WHITE);

			add(iniciar, BorderLayout.WEST);
			add(movimientos, BorderLayout.CENTER);
			add(puntos, BorderLayout.EAST);
		}
		public void actualizar(int p, int m){//Si resive 1 en p aumenta en 10 el puntaje, y si recive 1 en m aumentan los movimientos
			if(p > 0) pun += 10;
			if(m > 0)mov++;
			
			if(mov > 0)iniciar.setText("Restart");//Se ejecuta una vez por partida
			movimientos.setText("Movements: " + mov);
			puntos.setText("Points: " + pun + "  ");
			
			if(pun == 100) JOptionPane.showMessageDialog(Lienzo.this, "Congratulations You Won !!!!", "Winner", 1);//Si el jugador descubre todas las fichas.
		}
		public void reiniciarPuntaje(){
			pun = 0;
			mov = 0;
			actualizar(0, 0);
			iniciar.setText("Start");
		}
	}
}
