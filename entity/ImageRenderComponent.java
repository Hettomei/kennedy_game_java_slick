package entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ImageRenderComponent extends RenderComponent {

	Image image;
	Image imageG;
	Image imageD;

	public ImageRenderComponent(String id, Image image) {
		super(id);
		this.image  = image;
		this.imageG = image;
		this.imageD = image.getFlippedCopy(true, false);
	}
	public ImageRenderComponent(String id, Image image, int sens) {
		super(id);
		this.image  = image;
		this.imageG = image;
		this.imageD = image.getFlippedCopy(true, false);
		sensDeLImage(sens);
	}

	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
	
	//selectionne l'image en fonction du sens;
//	public void sensDeLImage(){
//		if (owner.getSens() == GAUCHE)
//			image = imageG;
//		else
//			image = imageD;
//	}
	public void sensDeLImage(int sens){
		if (sens == GAUCHE)
			image = imageG;
		else
			image = imageD;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		Vector2f pos = owner.getPosition();
		float scale = owner.getScale();

		image.draw(pos.x, pos.y, scale);
		
//		gr.draw(owner.getForme());
//		gr.drawString(owner.getVie() + "", pos.x, COORDZONEJEUY+DIMZONEJEUY);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		image.rotate(owner.getRotation() - image.getRotation());
	}

}