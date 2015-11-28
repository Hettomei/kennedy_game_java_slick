package entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class TirMouvement extends Component {

	float speed = 0.7f;

	public TirMouvement( String id )
	{
		this.id = id;
		speed += Math.random()*0.1;
	}
	public TirMouvement( String id, float speed )
	{
		this.id = id;
		this.speed = speed ;
	}

	public float getSpeed(){
		return speed;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {

	//	this.speed += 0.01;
		float rotation = owner.getRotation();
		float scale = owner.getScale();
		Vector2f position = owner.getPosition();
		int sens = owner.getSens();

		float hip = speed * delta;
		position.x += hip * sens;
		
		owner.setPosition(position);
		owner.setRotation(rotation);
		owner.setScale(scale);
		
		// -100 et +100 c'est pour ne pas prendre de risque (sur qu'ils soit bien sortis)
		if (position.x < COORDZONEJEUX - 100 || position.x > COORDZONEJEUX + DIMZONEJEUX + 100){
//			owner.setSens(-sens);
			listeTir.remove(owner);
			return;
	//		2000; 500 ; 38
		}
		
	}

}