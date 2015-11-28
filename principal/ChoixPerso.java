package principal;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

public class ChoixPerso extends BasicGameState implements Constantes, ComponentListener{

	public static final int ID = CHOIXPERSO;
	private Image perso = null;
	private MouseOverArea[] areas = new MouseOverArea[NOMBREPERSOS+1];
	private boolean persoChoisi=false;
	private Hero hero;

	public ChoixPerso(Hero perso) {
		hero = perso;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {

		int k = 1;
		int  imageParLigne = 4;
		Log.debug("eeeee");
		for (int i = 1; i <= NOMBREPERSOS; i++){
			perso = new Image("sprites/choixPersos/"+i+".png");
			areas[i] = new MouseOverArea(gc, perso, (i % imageParLigne)*150 + 200, k * 150, this);
			areas[i].setNormalColor(new Color(1,1,1,0.5f));
			areas[i].setMouseOverColor(new Color(1,1,1,3.9f));
			if (i % imageParLigne == 0){
				k++;
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		for (int i=1;i<=NOMBREPERSOS;i++) {
			areas[i].render(gc, g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		if (persoChoisi)
			sb.enterState(ETAGE1,new FadeOutTransition(), new FadeInTransition());
	}
	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException{
		persoChoisi = false;
	}
	
	@Override
	public int getID() {
		return ID;
	}
	
	public void componentActivated(AbstractComponent source) {
		for (int i=1;i<=NOMBREPERSOS;i++) {
			if (source == areas[i]) {
				Log.debug("Perso "+(i)+" cliqué !");
				persoChoisi=true;
				hero.setChoix(i);
			}
		}
	}
}
