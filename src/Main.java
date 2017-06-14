import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Main{
	public static void main(String[] args){
		try{
			MetalLookAndFeel.setCurrentTheme(new EstiloMemoria());
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception ex) {
			System.out.println("Falló la carga del tema");
	      	System.out.println(ex);
	   	}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		Marco juego = new Marco();
		juego.setVisible(true);
	}
}
