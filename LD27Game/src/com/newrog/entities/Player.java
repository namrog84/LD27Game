package com.newrog.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.newrog.myGame.GameScreen;
import com.newrog.simplegdx.Art;
import com.newrog.simplegdx.CurrentBatch;
import com.newrog.simplegdx.Sound;
import com.newrog.simplegdx.internal.Files;

public class Player extends Entity {
	ParticleEffect p = new ParticleEffect();
	Animation ringEffects;
	public float fuel = 10f;
	public boolean alive = true;
	double speed = .65f;
	GameScreen game;

	float stateTime = 0;
	int tick = 0;
	float floatY = 0;
	public boolean endGame = false;;
	Sound thrust;

	private boolean soundStarted = false;
	
	public Player(){
		p.load(Files.get("data/fireSmallSmoke"), Files.get("data/original"));
		thrust = new Sound("data/thruster3.wav");
		
		position = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		sprite = new Sprite(Art.playerTextureRegion);
		sprite.setOrigin(Art.playerTextureRegion.getRegionWidth() / 2, Art.playerTextureRegion.getRegionHeight() / 2);
		sprite.setPosition(position.x, position.y);
		sprite.setRotation(rotation);
		ringEffects = new Animation(0.06f, Art.rings);
		
		// size.x = 70;
	}
	public Player(GameScreen game) {
		this.game = game;
		p.load(Files.get("data/fireSmallSmoke"), Files.get("data/original"));
		thrust = new Sound("data/thruster4.wav");
		position = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		sprite = new Sprite(Art.playerTextureRegion);
		sprite.setOrigin(Art.playerTextureRegion.getRegionWidth() / 2, Art.playerTextureRegion.getRegionHeight() / 2);
		sprite.setPosition(position.x, position.y);
		sprite.setRotation(rotation);
		ringEffects = new Animation(0.06f, Art.rings);
		// size.x = 70;
		// size.y = 32;
	}

	// boolean holding = false;
	@Override
	public void update() {
		if (!alive)
			return;
		// holding = false;
		p.update(Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			acceleration.x -= speed;
			if (acceleration.x > 0)
				rotation = 0;
			rotation += .6f;

		} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			acceleration.x += speed;
			if (acceleration.x < 0)
				rotation = 0;
			rotation -= .6f;
		} else {
			rotation *= .7;
		}
		rotation = MathUtils.clamp(rotation, -15, 15);

		if (((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) & fuel > 0)||endGame ) {
			acceleration.y += 1; // rocketse
			fuel -= Gdx.graphics.getDeltaTime();
			if (fuel < 0)
				fuel = 0;
			p.findEmitter("fire").setContinuous(true);
			p.findEmitter("smoke").setContinuous(true);
			if(!soundStarted && !endGame ){
				thrust.loop();	
				soundStarted = true;
			}
			
			
		} else {
			p.findEmitter("fire").setContinuous(false);
			p.findEmitter("smoke").setContinuous(false);
			soundStarted = false;
			//if(thrust != null){
				thrust.stop();
			//}
		}

		if(!endGame)
			tryMove(acceleration);

		acceleration.x *= .80;

		if (acceleration.y > 0 && Gdx.input.isKeyPressed(Keys.UP)) {
			acceleration.y *= .9;
			acceleration.y -= .7;// *.5;
		} else {
			acceleration.y *= .9;// friction
			acceleration.y -= .7;// gravity
		}

		if (acceleration.x > 0.2) {
		} else if (acceleration.x < -0.2) {

		} else {
			rotation = 0f;
		}

		floatY = (float) (2.0f * Math.cos(tick++ / 10f));
		sprite.setPosition(position.x, position.y + floatY);
		sprite.setRotation(rotation);
		stateTime += Gdx.graphics.getDeltaTime();
		p.setPosition(sprite.getX() + sprite.getOriginX(), position.y + sprite.getOriginY() / 4);

		if(!endGame)
		if (level.finishRect.contains(position)) {
			//game.currentLevel++;
			game.nextLevel();
		}
	}

	protected void kill() {
		
		if (!alive)
			return;
		thrust.stop();
		alive = false;
		for (int i = 0; i < 1000; i++) {
			ExplosionParticle ep = new ExplosionParticle(position.x + sprite.getWidth() / 2, position.y + sprite.getHeight() / 2);
			ep.init(level);
			game.entities.add(ep);
		}

	}

	@Override
	public void render() {
		if (!alive)
			return;
		if (ringEffects.isAnimationFinished(stateTime)) {
			stateTime = 0;
			ringEffects.setPlayMode(Animation.NORMAL);

		}
		int a = ringEffects.getKeyFrameIndex(stateTime);

		
		CurrentBatch.get().setColor(1, 1, 1, 1 - a / 7f);
		CurrentBatch.get().draw(ringEffects.getKeyFrame(stateTime, false), (float) (sprite.getX() + 15 * MathUtils.sinDeg(sprite.getRotation())),
				position.y - a);
		CurrentBatch.get().setColor(1, 1, 1, 1f);
		p.draw(CurrentBatch.get());

		CurrentBatch.get().draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(),
				sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
	}

}
