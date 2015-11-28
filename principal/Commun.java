package principal;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.imageout.ImageOut;

public class Commun implements Constantes{
	
	public static boolean aVider = false;
	public static boolean jouerSon = false;
	
	public static void viderLesListes() {
		if (aVider){
			//remove any entity that has been marked for clear up
			if (supprListeMechants.size() > 0){
				listeMechant.removeAll(supprListeMechants);
				supprListeMechants.clear();
			}

			if (supprListeTirs.size() > 0){
				listeTir.removeAll(supprListeTirs);
				supprListeTirs.clear();
			}
			aVider = false;
		}
	}

	public static void play(Sound fx) {
		if (jouerSon)
			fx.play();
	}
	public static void printScreenshot(GameContainer gc) throws SlickException {
		Image target = new Image(DIMTOTALX, DIMTOTALY);
		gc.getGraphics().copyArea(target, 0, 0);
		ImageOut.write(target.getFlippedCopy(false, false), "screenshots/screenshot_"+ System.currentTimeMillis() +".png", false);
		target.destroy();			
	}

}
