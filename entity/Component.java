package entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import principal.Constantes;
 
public abstract class Component  implements Constantes{
 
    protected String id;
    protected Entity owner;
    
    public String getId()
    {
        return id;
    }
 
    public void setOwnerEntity(Entity owner)
    {
    	this.owner = owner;
    }
 
    public abstract void update(GameContainer gc, StateBasedGame sb, int delta);
}