package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch =20;
	private float yaw;
	private float roll;
	
	private Player player;
	
	public Camera(Player player){
		this.player=player;
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculteAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw=-player.getRotY()  - angleAroundPlayer +180;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float Hdistance, float Vdistance) {
		
		float theta =player.getRotY() + angleAroundPlayer;
		float offsetX= (float) (Hdistance * Math.sin(Math.toRadians(theta)));
		float offsetZ= (float) (Hdistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.y = player.getPosition().y + Vdistance +5.0f;
		position.z = player.getPosition().z - offsetZ;
		//this.yaw=theta;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel()*0.1f;
		distanceFromPlayer -=zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange=Mouse.getDY()*0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculteAngleAroundPlayer() {
		if(Mouse.isButtonDown(1)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

}
