package entities;


import org.lwjgl.util.vector.Vector3f;

import RenderEngine.Loader;
import RenderEngine.OBJLoader;
import models.RawModel;
import models.TexturedModel;
import terrain.Terrain;
import textures.ModelTexture;



public class body {
	int dy=90;
	float time=0;
	float dX;
	float dY;
	float dZ;
	public bodyPart body;
	public bodyPart facemodel;
	public bodyPart leftArm;
	public bodyPart rightArm;
	public bodyPart leftLeg;
	public bodyPart rightLeg;
	public bodyPart createBody(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Loader loader) {
		body = new bodyPart(model, position, rotX, rotY, rotZ, scale/2, 0,0,0,0);
		
		//Face Model
		RawModel face = OBJLoader.loadObjModel("face", loader);
		TexturedModel faceModel = new TexturedModel(face,new ModelTexture(loader.loadTexture("white")));
		facemodel= new bodyPart(faceModel, body.getPosition()	,0,0,0,1f,5,90,0,90);
		body.addChildren(facemodel);
		RawModel Leg = OBJLoader.loadObjModel("Leg", loader);
		TexturedModel legModel = new TexturedModel(Leg,new ModelTexture(loader.loadTexture("Red")));
		leftLeg= new bodyPart(legModel, body.getPosition()	,0,0,0,0.5f,3.9f,70,185,90);
		body.addChildren(leftLeg);
		rightLeg= new bodyPart(legModel, body.getPosition()	,0,0,0,0.5f,3.9f,260,185,90);
		body.addChildren(rightLeg);
		
		return body;
	}
	
	public void move(Terrain terrain) {
		body.increaseBodyRotation(0, dy, 0);
		dy=0;
		float d =(float) Math.cos(time);
		dY=terrain.getHeightOfTerrain(body.getPosition().x,body.getPosition().z )-body.getPosition().y+3.9f;
		body.increaseBodyPosition(0.1f, dY, 0.2f*d);
		body.increaseBodyRotation(0,0.5f*d,0);
		facemodel.increaseBodyRotation(0, d, 0);
		leftLeg.increaseBodyRotation(0, 0, 0.2f*d);
		rightLeg.increaseBodyRotation(0, 0, 0.2f*d);	
		time=(float) (time+0.01);
		//facemodel.increaseBodyRotation(0, 0, -1);
		//body.setBodyPosiiton(new Vector3f(100, body.getPosition().y,-800));
	}
	
	

}
