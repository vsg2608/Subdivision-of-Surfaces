package subDivision;

import org.lwjgl.util.vector.Vector3f;

import RenderEngine.Loader;
import models.RawModel;
import textures.ModelTexture;
public class subDivisionObject {
	public RawModel model;
	Tester Object= new Tester();
	
	public subDivisionObject(Loader loader, String object,int n) {
		Object.loadObject(object);
		Object.subDivide(n);
//		Object.printVertices();
		model=generateObject(Object, loader);
	}
	
	public RawModel generateObject(Tester Sub, Loader loader) {
		int Size=Sub.Vertices.size();
		float[] Vertices=new float[3*Size];
		float[] textureCoords = new float[Size*2];
		float[] normals = new float[Size * 3];
		for(int i=0;i<Size;i++) {
			vertex Vertex = Sub.Vertices.get(i);
			Vertices[3*i]=Vertex.x;
			Vertices[3*i+1]=Vertex.y;
			Vertices[3*i+2]=Vertex.z;
			normals[i*3] =Vertex.nx;
			normals[i*3+1] = Vertex.ny;
			normals[i*3+2] = Vertex.nz;
			textureCoords[i*2] = Vertex.tu;
			textureCoords[i*2+1] = Vertex.tv;
		}
		
		int Faces= Sub.Faces.size();
		int[] Indicies= new int[3*Faces];
		for(int i=0;i<Faces;i++) {
			face Face= Sub.Faces.get(i);
			Indicies[3*i]=Sub.Vertices.indexOf(Face.V1);
			Indicies[3*i+1]=Sub.Vertices.indexOf(Face.V2);
			Indicies[3*i+2]=Sub.Vertices.indexOf(Face.V3);
		}
		return loader.loadtoVAO(Vertices, textureCoords, normals, Indicies);
	}
	
	public subDivisionObject subdivide(Loader loader, int n) {
		this.Object.subDivide(n);
		model = generateObject(Object, loader);
		return this;
	}
}
