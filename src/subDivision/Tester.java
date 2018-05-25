package subDivision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class Tester {
	public static List<vertex> Vertices= new ArrayList<vertex>();
	public static List<edge> Edges= new ArrayList<edge>();
	public static List<face> Faces= new ArrayList<face>();
	public static List<vertex> EvenVertices= new ArrayList<vertex>();
	List<Vector2f> textures= new ArrayList<Vector2f> ();
	List<Vector3f> normals= new ArrayList<Vector3f> ();
	
	public void loadObject(String Object) {
		
		System.out.println("_______________________________Working");
		
		FileReader fr = null;
		try{
			fr = new FileReader(new File("res/"+Object));
		}catch(FileNotFoundException e) {
			System.err.println("Couldn't load file!");
			e.printStackTrace();
		}
		BufferedReader reader= new BufferedReader(fr);
		String line;
		
		try {
			line = reader.readLine();
			while(line != null) {
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")) {
					vertex Vertex = new vertex(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					Vertices.add(Vertex);
					//Vertex.PrintVertex();
				}else if(line.startsWith("vt ")) {
					Vector2f texture= new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}else if(line.startsWith("f ")) {
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					processVertex(vertex1);
					processVertex(vertex2);
					processVertex(vertex3);
					face Face = new face(
							Vertices.get(Integer.parseInt(vertex1[0])-1),
							Vertices.get(Integer.parseInt(vertex2[0])-1),
							Vertices.get(Integer.parseInt(vertex3[0])-1),
							Edges);
					Faces.add(Face);
				}
				line = reader.readLine();
			}
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		printVertices();
		System.out.println("Number of vertices= "+Vertices.size());
		System.out.println("Number of edges= "+Edges.size());
		System.out.println("Number of faces= "+Faces.size());
		System.out.println("Number of vertices in evenvertices list= "+EvenVertices.size());
		System.out.println("_______________________________Working");
	
	}
	
	//function to subdivide mesh for given times
	public void subDivide(int S) {
		for(int i=0;i<S;i++) {
			int noOfFaces=Faces.size();
			int noOfEdges=Edges.size();
			int noOfVertices=Vertices.size();
			EvenVertices=new ArrayList<vertex>();
			makeListOfEvenVertices(noOfVertices);
			subDivideOdd(noOfFaces,noOfEdges);
			updateEvenVertices(noOfVertices);	
			
			System.out.println("Number of vertices= "+Vertices.size());
			System.out.println("Number of edges= "+Edges.size());
			System.out.println("Number of faces= "+Faces.size());
			System.out.println("Number of vertices in evenvertices list= "+EvenVertices.size());
			System.out.println("_______________________________Working");
		}
//		printVertices();
	}
	
	
	
	//function to finally update even vertices after finding odd vertices
	public static void updateEvenVertices(int noOfVertices) {
		for(int i=0;i<noOfVertices;i++) {
			Vertices.get(i).x=EvenVertices.get(i).x;
			Vertices.get(i).y=EvenVertices.get(i).y;
			Vertices.get(i).z=EvenVertices.get(i).z;
		}
	}
	
	public static void makeListOfEvenVertices(int noOfVertices) {
		for(int i=0;i<noOfVertices;i++) {
			vertex Vertex= Vertices.get(i);
			int N=Vertex.connectedVertices.size();
			if(N<2) {
				EvenVertices.add(Vertex);
				System.out.println("Error");
			}else {
				float Alpha,X=0,Y=0,Z=0;
				if(N==2) {
					Alpha=(float) (1.0/8.0);
				}
				else if(N==3)
					Alpha=(float) (3.0/16.0);
				else
					Alpha=(float) (1.0/N*(5.0/8.0-Math.pow(3.0/8.0+1.0/4.0*Math.cos(360/N),2)));
				
				for(int j=0;j<N;j++) {
					X+=Vertex.connectedVertices.get(j).x;
					Y+=Vertex.connectedVertices.get(j).y;
					Z+=Vertex.connectedVertices.get(j).z;
				}
				Vertex.connectedVertices.clear();
				System.out.print(Alpha);
				vertex evenVertex= new vertex((1-N*Alpha)*Vertex.x+Alpha*X,
											(1-N*Alpha)*Vertex.y+Alpha*Y,
											(1-N*Alpha)*Vertex.z+Alpha*Z);
				EvenVertices.add(evenVertex);
			}	
		}
	}
	
	//Function to subdivide mesh and get all the odd vertices
	//Need to delete vertices from the list in vertex class when deleting faces
	public static void subDivideOdd(int noOfFaces, int noOfEdges) {		
		//Iterator for finding odd vertices
		//Need to take care for the duplicate vertices which can be done easily by making new function for adding vertices but not that much requirement
		for(int i=0;i<noOfFaces;i++) {
			face F=Faces.get(i);
			addVertex(F.Edge1.findOddVertex());
			addVertex(F.Edge2.findOddVertex());
			addVertex(F.Edge3.findOddVertex());
			face Face= new face(F.V1,
					F.Edge1.findOddVertex(),
					F.Edge3.findOddVertex(),
					Edges);
			Faces.add(Face);
			Face= new face(F.V2,
					F.Edge2.findOddVertex(),
					F.Edge1.findOddVertex(),
					Edges);
			Faces.add(Face);
			Face= new face(F.V3,
					F.Edge3.findOddVertex(),
					F.Edge2.findOddVertex(),
					Edges);
			Faces.add(Face);
			Face= new face(F.Edge1.findOddVertex(),
					F.Edge3.findOddVertex(),
					F.Edge2.findOddVertex(),
					Edges);
			Faces.add(Face);
		}
		
		for(int i=0;i<noOfFaces;i++)
			Faces.remove(0);
		for(int i=0;i<noOfEdges;i++)
			Edges.remove(0);
	}

	//Function to print all the vertices in the list
	public void printVertices() {
		for(int i=0;i<Vertices.size();i++) {
			this.Vertices.get(i).PrintVertex();
		}
		System.out.println();
	}
	
	private static void printListVertices(List<vertex> vertices) {
		System.out.println("Vertices in the given list");
		for(int i=0;i<Vertices.size();i++) {
			vertices.get(i).PrintVertex();
		}
		System.out.println();
	}

	//Function to print all the faces in the list
	private static void printFaces() {
		for(int i =0; i<Faces.size();i++) {
			Faces.get(i).PrintFace();
			System.out.println();
		}
	}
	
	public static void addVertex(vertex Vertex) {
		if(!Vertices.contains(Vertex))
			Vertices.add(Vertex);
	}
	
	public void processVertex(String[] vertex1) {
		int currentVertexPointer = Integer.parseInt(vertex1[0])-1;
		Vector2f currentTex= textures.get(Integer.parseInt(vertex1[1])-1);
		Vertices.get(currentVertexPointer).tu= currentTex.x;
		Vertices.get(currentVertexPointer).tu= 1-currentTex.y;
		Vector3f currentNorm= normals.get(Integer.parseInt(vertex1[2])-1);
		Vertices.get(currentVertexPointer).nx=currentNorm.x;
		Vertices.get(currentVertexPointer).ny=currentNorm.y;
		Vertices.get(currentVertexPointer).nz=currentNorm.z;
	}
}
