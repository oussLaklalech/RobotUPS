
import environnement.Impl.EnvironnementImpl;
import gui.GridGuiImpl;
import Robot.Environnement;




public class testClass {

	public static void main(String[] args) {
		new GridGuiImpl().newComponent();
		Environnement.Component systeme = new EnvironnementImpl().newComponent();
		
	}
}
