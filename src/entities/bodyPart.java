package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import RenderEngine.MasterRenderer;
import models.TexturedModel;

public class bodyPart extends Entity {
	public List<bodyPart> children = new ArrayList<bodyPart>();
	float X;
	float Y;
	float Z;
	float distance;
	public bodyPart(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float distance, float X, float Y, float Z) {
		super(model, new Vector3f(
				position.x + (float)(distance * Math.cos(Math.toRadians(X))),
				position.y + (float)(distance * Math.cos(Math.toRadians(Y))),
				position.z + (float)(distance * Math.cos(Math.toRadians(Z)))), rotX, rotY, rotZ, scale);
		this.X=X;
		this.Y=Y;
		this.Z=Z;
		this.distance=distance;
	}

	public void addChildren(bodyPart part) {
		children.add(part);
	}
	
	public void renderBodyParts(MasterRenderer renderer, bodyPart part) {
		renderer.processEntity(part);
		if(!children.isEmpty()) {
			for(bodyPart Part:children){
				Part.renderBodyParts(renderer,Part);
			}
		}
	}
	
	public void increaseBodyPosition(float dx, float dy, float dz) {
		super.increasePosition(dx, dy, dz);
		if(!children.isEmpty()) {
			for(bodyPart Part:children){
				Part.increaseBodyPosition(dx, dy, dz);
			}
		}
	}
	
	public void setBodyPosiiton(Vector3f position) {
		super.setPosition(position);
		if(!children.isEmpty()) {
			for(bodyPart Part:children){
				Part.setPosition(new Vector3f(
				position.x + (float)(Part.distance * Math.cos(Math.toRadians(Part.X))),
				position.y + (float)(Part.distance * Math.cos(Math.toRadians(Part.Y))),
				position.z + (float)(Part.distance * Math.cos(Math.toRadians(Part.Z)))));
			}
		}
	}
	
	public void increaseBodyRotation(float dx, float dy, float dz) {
		super.increaseRotation(dx, dy, dz);
		if(!children.isEmpty()) {
			for(bodyPart Part:children){
				Matrix4f matrix = new Matrix4f();
				matrix.setIdentity();
				Matrix4f.translate(new Vector3f(this.getPosition().x,this.getPosition().y,this.getPosition().z), matrix, matrix);
				Matrix4f.rotate((float) Math.toRadians(dx), new Vector3f(1,0,0), matrix, matrix);
				Matrix4f.rotate((float) Math.toRadians(dy), new Vector3f(0,1,0), matrix, matrix);
				Matrix4f.rotate((float) Math.toRadians(dz), new Vector3f(0,0,1), matrix, matrix);
				Matrix4f.translate(new Vector3f(-this.getPosition().x,-this.getPosition().y,-this.getPosition().z), matrix, matrix);
				Vector4f ans = new Vector4f();
				Matrix4f.transform(matrix,new Vector4f(Part.getPosition().x,Part.getPosition().y,Part.getPosition().z,1), ans);
				Part.setBodyPosiiton(new Vector3f(
						ans.x,
						ans.y,
						ans.z));
				Part.increaseBodyRotation(dx, dy, dz);
			}
		}
	}
	

}
