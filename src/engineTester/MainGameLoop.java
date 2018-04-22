 package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import RenderEngine.OBJLoader;
import RenderEngine.EntityRenderer;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.body;
import entities.bodyPart;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;
import toolBox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		body Body = new body();
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		RawModel flag = OBJLoader.loadObjModel("Flag", loader);
		TexturedModel flagModel = new TexturedModel(flag,new ModelTexture(loader.loadTexture("Red")));
		
		RawModel body = OBJLoader.loadObjModel("body1", loader);
		TexturedModel bodyModel = new TexturedModel(body,new ModelTexture(loader.loadTexture("Red")));
		
		
		RawModel Cyrborgmodel = OBJLoader.loadObjModel("skier", loader);
		TexturedModel CyborgModel = new TexturedModel(Cyrborgmodel,new ModelTexture(loader.loadTexture("Blue")));
		
		Player player = new Player(CyborgModel, new Vector3f(40,0,-800),0,-90,0,3f);
		player.increaseRotation(0, 180, 0);

		
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("Ice")), "heightmap");
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<500;i++){
			float x=random.nextFloat()*1600 ;
			float z=random.nextFloat() * -1600;
			float y=terrain.getHeightOfTerrain(x,z);
			entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,3));
		}
		float y=terrain.getHeightOfTerrain(20,-800);
		Entity flagEntity= new Entity(flagModel, new Vector3f(20, y, -800),0,0,0,1f);
		y=terrain.getHeightOfTerrain(50,-800);
		
		bodyPart bodyParts= Body.createBody(bodyModel, new Vector3f(50, y+2f, -800),0,0,0,3f, loader);
	
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Camera camera = new Camera(player);	
		MasterRenderer renderer = new MasterRenderer(loader);
		
		
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			//player.autoMove(terrain);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processEntity(flagEntity);
			bodyParts.renderBodyParts(renderer, bodyParts);
			Body.move(terrain);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			System.out.println("OneRenderComplete");
		}

		renderer.cleanUp();
		loader.CLeamUp();
		DisplayManager.closeDisplay();
	}

	

}
