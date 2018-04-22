package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import RenderEngine.DisplayManager;
import models.TexturedModel;
import terrain.Terrain;

public class Player extends Entity{

	private static final float RUN_SPEED= 30;
	private static final float TURN_SPEED=160;
	private static final float GRAVITY=-50;
	private static final float JUMP_POWER=30;
	private static final float FRICTION=30;
	private static final float AIR_FRICTION=5;
	private static final float TERRAIN_HEIGHT = 0;
	
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardSpeed = 0;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}
	
	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTime();
		
		float terrainHeight= terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight) {
			upwardSpeed=0;
			if (currentSpeed>0)
				currentSpeed-=FRICTION * DisplayManager.getFrameTime();
			else if (currentSpeed<0)
				currentSpeed+=FRICTION * DisplayManager.getFrameTime();
			super.getPosition().y=terrainHeight;
		}else {
			upwardSpeed += GRAVITY * DisplayManager.getFrameTime();
			if (currentSpeed>0)
				currentSpeed-=AIR_FRICTION * DisplayManager.getFrameTime();
			else if (currentSpeed<0)
				currentSpeed+=AIR_FRICTION * DisplayManager.getFrameTime();
		}
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dy = upwardSpeed * DisplayManager.getFrameTime();
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(	dx, dy, dz);
		
		
	}
	
	private void jump() {
		this.upwardSpeed=JUMP_POWER;
	}
	
	private void checkInputs() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		}else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}
	
	public void autoMove(Terrain terrain) {
		
		currentSpeed=30;
		currentTurnSpeed +=1;
		super.increaseRotation(0, (float) Math.sin(0.1*super.getPosition().z) , 0);
		float distance = currentSpeed * DisplayManager.getFrameTime();
		float terrainHeight= terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight) {
			upwardSpeed=0;
			super.getPosition().y=terrainHeight;
		}else {
			upwardSpeed += GRAVITY * DisplayManager.getFrameTime();
		}
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dy = upwardSpeed * DisplayManager.getFrameTime();
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(	dx, dy, dz);
	}

}
