 	package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolBox.Maths;

public class StaticShader extends ShaderProgram{
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_reflectivity;
	private int location_shineDampner;
	private int location_skyColor;
	

	private static final String VERTEX_FILE="src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE="src/shaders/fragmentShader.txt";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords"); 
		super.bindAttribute(2, "normal");
	}

	protected void getAllUniformLocations() {
		location_transformationMatrix=super.getUniformLocation("transformationMatrix");
		location_projectionMatrix=super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour");
		location_shineDampner = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDampner, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix= Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
}
