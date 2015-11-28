package principal;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.particles.ConfigurableEmitter;

import outils.Debug;

import entity.Entity;
import entity.ImageRenderComponent;
import entity.HeroMouvement;
import entity.MechantMouvement;
import entity.TirMouvement;

public class Etage1 extends BasicGameState implements Constantes {

	public static final int ID = ETAGE1;

	long intervalDeTir = 200;
	long dernierTir;

	Image zoneJeux = null;
	Image imageMechant = null;
	Image imageTir = null;

	Entity perso = null;

	private Hero hero;

	private Sound tirFx = null;
	private Sound tirFx1 = null;
	private Sound bossFx = null;

	private int iii = 0;

	ParticleSystem trail;
	
	public Etage1(Hero perso) {
		hero = perso;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		tirFx = new Sound("sprites/sounds/tirNak.ogg");
		tirFx1 = new Sound("sprites/sounds/tir.ogg");
		// bossFx = new Sound("sprites/sounds/boss.ogg");
		bossFx = new Sound("sprites/sounds/nakibMort.ogg");

		hero.getChoix();
		zoneJeux = new Image("sprites/etage1/zonejeu.png");
		// imageMechant = new Image("sprites/mechant/mechant.png");
		// imageTir = new Image("sprites/tir.png");

		perso = new Entity("Perso");
		ImageRenderComponent aa; // permet de récupérer le getWidth;
		perso.AddComponent(aa = new ImageRenderComponent("PersoRender",
				new Image("sprites/hero/perso_debout.png")));
		perso.AddComponent(new HeroMouvement("PersoMovement"));
		Vector2f temp = new Vector2f(COORDZONEJEUX + 10, COORDZONEJEUY
				+ DIMZONEJEUY - aa.getHeight() - 5);
		perso.setPosition(temp);
		perso.setForme(new Rectangle(temp.x, temp.y, aa.getWidth(), aa
				.getHeight()));

		creerMechant();

		try {
		//	trail = ParticleIO.loadConfiguredSystem("sprites/system.xml");
			trail = ParticleIO.loadConfiguredSystem("sprites/smoketrail.xml");
		} catch (IOException e) {

			throw new SlickException("Failed to load particle systems", e);
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		zoneJeux.draw(COORDZONEJEUX, COORDZONEJEUY);
		Debug.init();
		g.drawString("Bruitage on/off S : " + Commun.jouerSon
				+ "Screenshot I - creer mechant M - changer d'etat T",
				Debug.getSetX(), Debug.getSetY());
		// g.drawString("Plus vite moins vite : V B", Debug.getSetX(),
		// Debug.getSetY());
		// g.drawString("vitesse : " + speed, Debug.getSetX(), Debug.getSetY());
		g.drawString("Mechants : " + listeMechant.size() + "  -  Tir : "
				+ listeTir.size(), Debug.getSetX(), Debug.getSetY());

		for (int i = 0; i < listeMechant.size(); i++) {
			listeMechant.get(i).render(gc, null, g);
		}
		for (int i = 0; i < listeTir.size(); i++) {
			listeTir.get(i).render(gc, null, g);
			((ConfigurableEmitter) trail.getEmitter(0)).setPosition(listeTir.get(0).getPosition().x , listeTir.get(0).getPosition().y);
			trail.render();
		}

		perso.render(gc, null, g);
		// fonctionnement :
		// on parcour tous les mechant
		// on test si le tir à touché le mechant
		// si oui, dans l'entité, on enleve la vie du mechant avec la puissance
		// du tir
		// et on supprime ICI le tir et le mechant si il est mort
		// (on considere que la balle meurt à chaque fois pour le moment)
		for (int p = 0; p < listeMechant.size(); p++) {
			Entity mechant = listeMechant.get(p);
			for (int s = 0; s < listeTir.size(); s++) {
				Entity tir = listeTir.get(s);
				if (tir.Touche(mechant)) {
					mechant.collisionAvec(tir);
					tir.collisionAvec(mechant);
					listeTir.remove(tir);
					if (!mechant.isVivant()) { // si le mechant est mort, on
												// quitte la boucle car ça ne
												// sert à rien
						listeMechant.remove(mechant);
						Commun.play(bossFx); // on joue le bruitage

						// Commun.play(fx); //on joue le bruitage
						break;
					}
				}
			}
		}
		
	}

	public void creerMechant() throws SlickException {
		Entity temp = new Entity("mechant");
		listeMechant.add(temp);
		// ImageRenderComponent tempImage = new
		// ImageRenderComponent("mechantRender", imageMechant);
		ImageRenderComponent tempImage = new ImageRenderComponent(
				"mechantRender", new Image("sprites/mechant/mechant.png"));
		temp.AddComponent(tempImage);
		temp.AddComponent(new MechantMouvement("mechantRender"));

		Vector2f tempp = new Vector2f(COORDZONEJEUX + DIMZONEJEUX
				- tempImage.getHeight(), COORDZONEJEUY + DIMZONEJEUY
				- tempImage.getHeight() - 5);
		temp.setPosition(tempp);

		temp.setForme(new Rectangle(tempp.x, tempp.y, tempImage.getWidth(),
				tempImage.getHeight()));
		// Log.debug(listeMechant.size()+"");
	}

	public void tryToFire() throws SlickException {
		if ((System.currentTimeMillis() - dernierTir) < intervalDeTir) {
			return;
		}
		if (iii % 2 == 0) {
			Commun.play(tirFx);
		} else {
			Commun.play(tirFx1);
		}
		iii++;

		dernierTir = System.currentTimeMillis();
		Entity temp = new Entity("balle", perso.getSens());

		// ImageRenderComponent tempImage = new
		// ImageRenderComponent("tirRender", imageTir);
		ImageRenderComponent tempImage = new ImageRenderComponent("tirRender",
				new Image("sprites/tir.png"), perso.getSens());
		temp.AddComponent(tempImage);
		temp.AddComponent(new TirMouvement("tirRender"));
		temp.setPosition(new Vector2f(perso.getForme().getCenterX(), perso
				.getForme().getCenterY() - 30));
		temp.setForme(new Rectangle(perso.getPosition().x,
				perso.getPosition().y, tempImage.getWidth(), tempImage
						.getHeight()));
		listeTir.add(temp);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		perso.update(gc, null, delta);
		
		for (int i = 0; i < listeMechant.size(); i++) {
			listeMechant.get(i).update(gc, null, delta);
		}
		for (int i = 0; i < listeTir.size(); i++) {
			listeTir.get(i).update(gc, null, delta);
			trail.update(delta);
		}

		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_SPACE)) {
			tryToFire();
		}

		if (input.isKeyPressed(Input.KEY_S)) {
			Commun.jouerSon = !Commun.jouerSon;
		}

		// if(input.isKeyPressed(Input.KEY_M)){
		// creerMechant();
		// }
		if (input.isKeyDown(Input.KEY_M)) {
			creerMechant();
		}

		// screenshot
		if (input.isKeyPressed(Input.KEY_I)) {
			Commun.printScreenshot(gc);
		}
		if (input.isKeyDown(Input.KEY_T)) {
			sb.enterState(CHOIXPERSO, new FadeOutTransition(Color.blue),
					new FadeInTransition(Color.magenta));
		}

		if (input.isKeyPressed(Input.KEY_P)) {
			// if(gc.isPaused()){
			// gc.resume();
			// }else{
			// gc.pause();
			// }
			// faire un changement d'état, c'est mieux....
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void enter(GameContainer gc, StateBasedGame sb)
			throws SlickException {
	}

}
