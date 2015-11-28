package principal;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Initialisation du tout
 *
 * @author Timothée Gauthier
 */
public class StartGame extends StateBasedGame implements Constantes {

	/**
	 * Create a new test
	 */
	public StartGame() {
		super(TITRE);
	}
	
	/**
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	public void initStatesList(GameContainer container) {
		Hero perso = new Hero();
		addState(new Etage1(perso));
		addState(new ChoixPerso(perso));
		
	}
	
	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new StartGame());
			container.setDisplayMode(DIMTOTALX, DIMTOTALY,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
