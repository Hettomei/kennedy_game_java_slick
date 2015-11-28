package entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import principal.Constantes;

public class Entity implements Constantes{

	private String id;

	private Vector2f position;
	private float scale;
	private float rotation;
	private int sens;
	private Rectangle forme;
	private boolean vivant = true; //
	
	private int vie;
	private int force;
	
	private RenderComponent renderComponent = null;
	private ArrayList<Component> components = null;


	public Entity(String id)
	{
		this.id = id;

		this.components = new ArrayList<Component>();

		this.position = new Vector2f(0,0);
		this.forme = new Rectangle(0,0, 0, 0);
		this.scale = 1;
		this.rotation = 0;
		this.sens = GAUCHE;
		this.vie = 100;
		this.force = 100;
	}
	public Entity(String id, int sens)
	{
		this.id = id;

		this.components = new ArrayList<Component>();

		this.position = new Vector2f(0,0);
		this.forme = new Rectangle(0,0, 0, 0);
		this.scale = 1;
		this.rotation = 0;
		this.sens = sens;
		this.vie = 100;
		this.force = 100;
	}


	public void AddComponent(Component component)
	{
		//si c'est du type image
		if(RenderComponent.class.isInstance(component)){
			renderComponent = (RenderComponent)component;
		}

		component.setOwnerEntity(this);
		components.add(component);
	}

	public Component getComponent(String id)
	{
		for(Component comp : components)
		{
			if ( comp.getId().equalsIgnoreCase(id) )
				return comp;
		}

		return null;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public float getScale()
	{
		return scale;
	}

	public float getRotation()
	{
		return rotation;
	}

	public String getId()
	{
		return id;
	}
	
	public int getSens() {
		return sens;
	}

	public void setSens(int sens) {
		if (this.sens != sens){
			this.sens = sens;
			renderComponent.sensDeLImage(sens);
		}
	}

	public void setPosition(Vector2f position) {
		this.position = position;
		this.forme.setLocation(position);
	}

	public void setForme(Rectangle r) {
		this.forme = r;
	}
	
	public Shape getForme() {
		return this.forme;
	}
	
	public void setRotation(float rotate) {
		rotation = rotate;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta)
	{
		for(Component component : components){
			component.update(gc, sb, delta);
		}
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
	{
		if(renderComponent != null)
			renderComponent.render(gc, sb, gr);
	}
	public int getHeight() {
		return renderComponent.getHeight();
	}
	public int getWidth() {
		return renderComponent.getWidth();
	}

	public boolean Touche(Entity tir) {
		return forme.intersects(tir.getForme());
	}

	public void collisionAvec(Entity entite) {
		// previens les doubles kills,
		// si le mechant ou la balle n'existe plus on ne fait rien.
		if (!vivant  || !entite.isVivant()) {
			return;
		}
		this.vie -= entite.getForce();
		
		if (this.vie <= 0)
			vivant = false;
			
	}

	public int getForce() {
		return this.force;
	}

	public int getVie() {
		return this.vie;
	}

	public boolean isVivant() {
		return vivant;
	}
}

