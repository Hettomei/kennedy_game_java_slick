package principal;

import java.util.ArrayList;


import entity.Entity;


public interface Constantes {

	
	//information sur l'affichage :
	final static String TITRE = new String("Bagdad à nos Portes");	
	final int DIMTOTALX = 1100;
	final int DIMTOTALY = 640 ;
	//dimension de la zone de jeu
	final int DIMZONEJEUX = 1100;
	final int DIMZONEJEUY = 340 ;	
	//on connais "l'emplacement" de la boite de jeux, plus simple pour le future
	final int COORDZONEJEUX = 0;
	final int COORDZONEJEUY = 200;
	
	final int GAUCHE = -1;
	final int DROITE = 1;
	
	//nombre de personnages a choisir
	final int NOMBREPERSOS = 10;
	
	final int CHOIXPERSO = 100;
	final int ETAGE1 = 1;
	
	final float GRAVITE = 2;
	final float C_GRAVITE = 2.8f;
//	final float CONSTANTE_SAUT = 0.02f;
	final float CONSTANTE_SAUT = 0.01f;
	
	static ArrayList<Entity> listeMechant = new ArrayList<Entity>();
	static ArrayList<Entity> listeTir = new ArrayList<Entity>();
	/** The list of entities that need to be removed from the game this loop */
	static ArrayList<Entity> supprListeMechants = new ArrayList<Entity>();
	static ArrayList<Entity> supprListeTirs = new ArrayList<Entity>();
	
}
