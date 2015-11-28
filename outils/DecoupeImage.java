package outils;


import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.util.Log;

import principal.Constantes;

public class DecoupeImage extends BasicGame implements Constantes, ComponentListener{

	private Image image = null;
	private Image image2 = null;
	private String message;

	private int state = 0;

	private ArrayList<Line> ligne =  new ArrayList<Line>();
	private Vector2f debutLigne = new Vector2f();
	private Vector2f finLigne = new Vector2f();
	//	private float rrr [] = { 255,287,259,489,663,465,666,238};
	private Polygon poly = new Polygon();//rrr);

//	private float test [] = { 248,344,253,411,291,455,346,460,387,464,455,460,481,444,482,411,473,385,460,407,438,439,386,441,332,439,304,428,337,362,352,333,342,306,297,294};
//	private Polygon polyT = new Polygon(test);

	private float test [] = { 100,100, 150,150, 300,100};
	private Polygon polyT = new Polygon(test);


	public DecoupeImage(String title) {
		super(title);
	}

	public void componentActivated(AbstractComponent source) {
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		image = new Image("sprites/hero/perso_debout.png");
		image2 =  image;
		message = "Définissez le point de départ";
		debutLigne.set(0, 0) ;
		finLigne.set(0, 0);
		poly.addPoint(0, 0);
	}

	public boolean temp = true;
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		image.draw(100, 100);
		image2.drawFlash(500, 200, image2.getWidth()*3,image2.getHeight()*3);
		g.drawString(message, 10, 30);

	//	polyT.setX(0);
	//	polyT.setY(0);
		
		Polygon polyTT = polyT.copy(); //on copie
		polyTT = (Polygon) polyTT.transform(Transform.createScaleTransform(-1, 1)); //on inverse
		polyTT = polyTT.copy();

		polyTT.setLocation(polyT.getX(), polyT.getY());// + polyT.getHeight());
		construirePoints(polyT);
		construirePoints(polyTT);
	//	polyTT.setCenterX(polyT.getWidth());
		//afficherInfos(polyT);
		//afficherInfos(polyTT);
		//System.out.println("FFFFINNNNNNNN");



		g.setColor(Color.red);
		g.draw(polyT);
//		affichePolyRecCentre(g, polyT);
		

		g.setColor(Color.green);
		g.draw(polyTT);
//		affichePolyRecCentre(g, polyTT);

		g.setColor(Color.magenta);
		affichePolyRecCentre(g,poly);
	}

	private void affichePolyRecCentre(Graphics g, Polygon polyT) {
		g.draw(polyT); // on affiche le normale
		g.draw(new Circle((polyT.getMinX()+polyT.getMaxX())/2, (polyT.getMinY()+polyT.getMaxY())/2, 5));
		g.draw(new Rectangle(polyT.getMinX(), polyT.getMinY(), polyT.getWidth(), polyT.getHeight()));
		g.draw(new Line(polyT.getMinX(), polyT.getMinY(), polyT.getMaxX(), polyT.getMaxY()));
		g.draw(new Line(polyT.getMaxX(), polyT.getMinY(), polyT.getMinX(), polyT.getMaxY()));		
	}

//	private void afficherInfos(Polygon polyTT) {
//		// TODO Auto-generated method stub
//		System.out.println(polyTT);
//		System.out.println("polyTT.getHeight()" + (int)polyTT.getHeight() + " --- " + (int)polyTT.getWidth());
//		System.out.println("polyTT.getMaxX()" + (int)polyTT.getMaxX() + " --- " + (int)polyTT.getMaxY());
//		System.out.println("polyTT.getMinX()" + (int)polyTT.getMinX() + " --- " + (int)polyTT.getMinY());
//		System.out.println("polyTT.getX()" + (int)polyTT.getX() + " --- " + (int)polyTT.getY());
//		Vector2f temp = new Vector2f(polyTT.getCenter());
//		System.out.println("polyTT.getCenter()" + (int)temp.x + " --- " + (int)temp.y);
//		System.out.println("polyTT.getCenterX()" + (int)polyTT.getCenterX() + " --- " + (int)polyTT.getCenterY());
//		
//	}

	@Override
	public void update(GameContainer gc, int delta)
	throws SlickException {
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) { //clique gauche

			switch (state){
			case 0:
				message = "Point de départ : " + x + "-" + y;
				debutLigne.set(x, y);
				poly = new Polygon(); //on le recréé ici sinon il a 0 0 en premiers point
				poly.addPoint(x, y);
				state++;
				break;
			case 1:
				finLigne.set(x, y);
				ligne.add(new Line(debutLigne, finLigne));
				message = "Nouvelle ligne : (" + debutLigne.x + "-" + debutLigne.y +") (" + x + "-" + y + ")";
				debutLigne.set(finLigne);
				poly.addPoint(finLigne.x, finLigne.y) ;
				break;
			}
		}else if(button == 1) { //clique droit, on ressort le shape;
			Log.debug(construirePoints());
		}
	}





	private String construirePoints() {
		String s = new String("{ ");
		for (int i = 0; i< ligne.size(); i++){
			s += (int)ligne.get(i).getX1() + "," + (int)ligne.get(i).getY1() + ",";
		}
		s = s.substring(0,s.length()-1); // on enleve la virgule
		s += "}";
		
		
		System.out.println("");
		
		System.out.print("{");
		Vector2f temp;
		for (int i = 0; i < poly.getPointCount(); i++){	
			temp = new Vector2f(poly.getPoint(i));
			System.out.print( (int)temp.x + "," + (int)temp.y +" ");
		}
		System.out.print("}\n");
		
		return s;
	}

	private void construirePoints(Polygon pol) {

		System.out.println("");		
		System.out.print("{");
		Vector2f temp;
		for (int i = 0; i < pol.getPointCount(); i++){	
			temp = new Vector2f(pol.getPoint(i));
			System.out.print( (int)temp.x + "," + (int)temp.y +" ");
		}
		System.out.print("}\n");

	}
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new DecoupeImage("decoupe image"));

		app.setDisplayMode(DIMTOTALX, DIMTOTALY, false);
		app.start();
	}

}
