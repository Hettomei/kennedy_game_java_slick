package entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import principal.Commun;

public class HeroMouvement extends Component {

	float direction;
	float speed = 0.4f;
	private boolean enTrainDeSauter = false;
	private float forceSaut = GRAVITE;

	
	private Sound jumpFx = null;
	
	public HeroMouvement( String id ) throws SlickException
	{
		this.id = id;
		jumpFx  = new Sound("sprites/sounds/jump.ogg");
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

		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_LEFT)) {
			float hip = speed * delta ;//speed * delta;
			position.x -= hip;
			owner.setSens(GAUCHE);
		}

		if(input.isKeyDown(Input.KEY_RIGHT)) {
			float hip = speed * delta ;//speed * delta;
			position.x += hip;
			owner.setSens(DROITE);
		}

		if(input.isKeyDown(Input.KEY_UP) && !enTrainDeSauter ) {
			//saut
			enTrainDeSauter  = true;
			Commun.play(jumpFx);
		}

		if(input.isKeyDown(Input.KEY_DOWN)) {
			//accroupis;
		}
		if(enTrainDeSauter){
			float hip = speed * delta ;//speed * delta;
			position.y -= hip + forceSaut ;
			forceSaut -= CONSTANTE_SAUT * delta;
			// Arrivé à terre
			if (position.y > COORDZONEJEUY + DIMZONEJEUY - owner.getHeight() - 5){
				position.y = COORDZONEJEUY + DIMZONEJEUY - owner.getHeight() - 5 ;
				enTrainDeSauter = false;
				forceSaut = GRAVITE;
			}
		}
		if (position.x < COORDZONEJEUX){
			position.x = COORDZONEJEUX;
		}else if (position.x > COORDZONEJEUX + DIMZONEJEUX - owner.getWidth()){
			position.x = COORDZONEJEUX+ DIMZONEJEUX - owner.getWidth();
		}
		owner.setPosition(position);
		owner.setRotation(rotation);
		owner.setScale(scale);

	}
}