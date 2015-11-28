package entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class MechantMouvement extends Component {

	float direction;
	float speed = 0.4f;

	public MechantMouvement( String id )
	{
		this.id = id;
	}

	public float getSpeed()
	{
		return speed;
	}

	public float getDirection()
	{
		return direction;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {

		float rotation = owner.getRotation();
		float scale = owner.getScale();
		Vector2f position = owner.getPosition();
		int sens = owner.getSens();

		float hip = speed * delta;
		position.x += hip * sens;
		
		if (position.x < COORDZONEJEUX){
			sens = DROITE;
			owner.setSens(sens);
		}else if (position.x > COORDZONEJEUX + DIMZONEJEUX - 100){
			sens = GAUCHE;
			owner.setSens(sens);
		}
		
		owner.setPosition(position);
		owner.setRotation(rotation);
		owner.setScale(scale);
		
	}

}