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
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import subDivision.subDivisionObject;
import terrain.Terrain;
import textures.ModelTexture;
import toolBox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		RawModel Cyrborgmodel = OBJLoader.loadObjModel("cube", loader);
		TexturedModel CyborgModel = new TexturedModel(Cyrborgmodel,new ModelTexture(loader.loadTexture("right")));
		
		Player player = new Player(CyborgModel, new Vector3f(40,0,-800),0,-90,0,3f);
		player.increaseRotation(0, 180, 0);

		
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")), "heightmap");
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<500;i++){
			float x=random.nextFloat()*1600 ;
			float z=random.nextFloat() * -1600;
			float y=terrain.getHeightOfTerrain(x,z);
			entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,3));
		}
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Camera camera = new Camera(player);	
		MasterRenderer renderer = new MasterRenderer(loader);
		int N=5;
		Entity[] subObjects = new Entity[N];
		subDivisionObject[] Objectloader =  new subDivisionObject[N];
		TexturedModel[] subTextureModel = new TexturedModel[N];
		Objectloader[0]= new subDivisionObject(loader, "icosahedron.obj",0);
		subTextureModel[0] = new TexturedModel(Objectloader[0].model,new ModelTexture(loader.loadTexture("grass")));
		float y=terrain.getHeightOfTerrain(40,-800);
		subObjects[0] = new Entity(subTextureModel[0], new Vector3f(40,y+10,-780),0,0,0,5);
		for(int i=1;i<N;i++) {
			Objectloader[i]= Objectloader[i-1].subdivide(loader, 1);
			subTextureModel[i]= new TexturedModel(Objectloader[i].model,new ModelTexture(loader.loadTexture("grass")));
			subObjects[i] = new Entity(subTextureModel[i], new Vector3f(40,y+10,-780-10*i),0,0,0,5);
		}
		
		
		Vector3f position = new Vector3f(50,y+10,-780-10);
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
//			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			for(int i=0;i<N;i++) {
//				renderer.processEntity(subObjects[i]);
				subObjects[i].increaseRotation(0, 1, 0);
				subObjects[i].setPosition(position);
			}
			
			float dist= ditance(position, Camera.getPosition());
//			System.out.println(dist);
			if(dist>150) 
				renderer.processEntity(subObjects[0]);
			else if(dist>100)
				renderer.processEntity(subObjects[1]);
			else if(dist>75)
				renderer.processEntity(subObjects[2]);
			else if(dist>50)
				renderer.processEntity(subObjects[3]);
			else if(dist>25)
				renderer.processEntity(subObjects[4]);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
//			System.out.println("OneRenderComplete");
		}

		renderer.cleanUp();
		loader.CLeamUp();
		DisplayManager.closeDisplay();
	}

	private static float ditance(Vector3f position, Vector3f position2) {
		float X= (float) Math.pow(position.x-position2.x, 2);
		float Y= (float) Math.pow(position.y-position2.y, 2);
		float Z= (float) Math.pow(position.z-position2.z, 2);
		return (float) Math.pow(X+Y+Z, 0.5);
	}



}
