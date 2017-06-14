import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class LienzoTablero extends JPanel{
	public JLabel[] fichas = new JLabel[20];//Contiene las 20 fichas.
	private Icon[] fotos = new Icon[11];//Contie las imagenes de las fichas.
	private int ultimo_click = -1;//La ultima ficha volteada.
	private int activos = 0, anterior;//La cantidad de fichas volteadas, la penultima ficha volteada.
	private Lienzo.LienzoPuntaje puntaje;//Permite actualizar los puntajes y movimientos.
	
	public LienzoTablero(Color fondo, Lienzo.LienzoPuntaje puntaje){
		this.puntaje = puntaje;
		setBackground(fondo);
		
		GridLayout alineacion = new GridLayout(5, 5);//Grilla que contiene las fichas
		alineacion.setHgap(5);
		alineacion.setVgap(5);
		setLayout(alineacion);
		iniciarFotos(fotos);
		iniciarFichas(fichas, fotos[10]);
	}
	public void reiniciarTablero(){//No es nechesario reiniciar la variable anterior.
		removeAll();//Borramos las fichas
		ultimo_click = -1;
		activos = 0;
		iniciarFotos(fotos);
		iniciarFichas(fichas, fotos[10]);
	}
	private void iniciarFotos(Icon[] fo_iniciar){//Toma 10 fotos alazar de las 20 disponibles.
		ArrayList<Integer> pos = new ArrayList<Integer>();//Array que contiene numeros del 0 al 19.
		for(int i = 0; i < 20; i++)
			pos.add(i);
		Collections.shuffle(pos);//Mezcla los numeros del array, los primeros diez seran las direcciones de las fotos de las fichas
		for(int i = 0; i < fo_iniciar.length; i++)
			fo_iniciar[i] = new ImageIcon(LienzoTablero.class.getResource("image/" + String.valueOf(pos.get(i))+ ".jpg"));//Inicia las fotos con la direccion dada por las primeras 10 del array.
		fo_iniciar[fo_iniciar.length-1] = new ImageIcon(LienzoTablero.class.getResource("image/quien.jpg"));//La ultima foto contiene la foto del reverso de todas las fichas
	}
	private void iniciarFichas(JLabel[] fi_iniciar, Icon fondo){//Inicia las fichas y les añade un oyente
		ArrayList<Integer> donde_im = new ArrayList<Integer>();//Array que contiene las posicion de las fotos repetidas
		for(int i = 0; i < fi_iniciar.length/2; i++){
			donde_im.add(i);
			donde_im.add(i);
		}
		Collections.shuffle(donde_im);//Desordena el array.
		for(int i = 0; i < fi_iniciar.length; i++){
			fi_iniciar[i] = new JLabel(fondo);//Inicia la etiqueta con la imágen del reverso
			fi_iniciar[i].addMouseListener(new AccionClick(i, (int)donde_im.get(i)));//Oyente, que resive la pósicion de la foto en el array fotos y la posicion del JLabel en el array
			add(fi_iniciar[i]);
		}
	}
	private class AccionClick extends MouseAdapter{
		private int pos_e, pos_foto;//Posicion de la etiqueta en su array y la foto de la ficha en su array.
		
		public AccionClick(int pos, int pos_foto){
			super();
			pos_e = pos;
			this.pos_foto = pos_foto;
		}
		public void mouseClicked(MouseEvent e) {
			if(ultimo_click == pos_e) return;//Si se cliquea dos veces en el mismo jlabel
			else ultimo_click = pos_e;//Se asigna la posicion del jlabel al ultimo click
			fichas[pos_e].setIcon(fotos[pos_foto]);//Se voltea la ficha
			activos++;//Aumenta el número de fichas volteadas.
			puntaje.actualizar(0, 1);//Aumenta el número de clicks
			if(activos == 2)//Comprueba si las fichas son iguales
				compararFichas(pos_e, anterior, true);
		}
		public void mouseExited(MouseEvent e){//Al salir del jlabel se comparan las dos fichas dadas vuelta
			if(anterior == ultimo_click || activos == 0) return;
			if(activos > 1)
				compararFichas(pos_e, anterior, false);
			else anterior = pos_e;
		}
	}
	private void compararFichas(int a, int b, boolean click){//Compara dos fichas. Si click es falso y las voltea o les quita el oyente
		if(fichas[a].getIcon().equals(fichas[b].getIcon()) && !click){//Si son iguales y click es falso retira los oyentes
			fichas[a].removeMouseListener(fichas[a].getMouseListeners()[0]);
			fichas[b].removeMouseListener(fichas[b].getMouseListeners()[0]);
		}
		else if(fichas[a].getIcon().equals(fichas[b].getIcon()))//Si son iguales pero click es verdadero suma puntos
			puntaje.actualizar(1, 0);
		else if(!click){//Si click es falso y las fichas son iguales, las voltea
			fichas[a].setIcon(fotos[10]);
			fichas[b].setIcon(fotos[10]);
		}
		if(click) return;
		activos = 0;		//Estas dos ultimas lineas se ejecutan solo si click es false
		ultimo_click = -1;		
	}
}
