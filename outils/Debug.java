package outils;

public class Debug {
	private static int x = 10;
	private static int y = 25;
	private static int nombre = 0;

	public static void init(){
		x = 10;
		y = 25;
		nombre = 0;
	}
	public static int getSetY(){
		y += 15;
		return y;
	}
	public static int getSetX(){
		nombre++;
		if (nombre == 11){
			x += 300;
			nombre = 1;
			y = 25;
		}
		return x;
	}
	public static String getXY(){
		return "x " + x + " -- y " + y;
	}
}
