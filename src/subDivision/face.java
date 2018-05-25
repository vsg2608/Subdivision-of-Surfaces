package subDivision;

import java.util.List;

public class face {
	public vertex V1;
	public vertex V2;
	public vertex V3;
	public edge Edge1;
	public edge Edge2;
	public edge Edge3;
	
	public face(vertex v1, vertex v2, vertex v3,List<edge> Edges) {
		this.V1=v1;
		this.V2=v2;
		this.V3=v3;
		edge E1 = new edge(V1,V2,this);
		if(E1.searchEdge(Edges, E1, this))
			Edges.add(E1);
		edge E2 = new edge(V2,V3,this);
		if(E2.searchEdge(Edges, E2, this))
			Edges.add(E2);
		edge E3 = new edge(V3,V1,this);
		if(E3.searchEdge(Edges, E3, this))
			Edges.add(E3);	
		this.Edge1=E1.checkEdge(Edges, E1, this);
		this.Edge2=E2.checkEdge(Edges, E2, this);
		this.Edge3=E3.checkEdge(Edges, E3, this);
	}
	
	public void PrintFace() {
		System.out.println("Face");
		V1.PrintVertex();
		V2.PrintVertex();
		V3.PrintVertex();
		System.out.println();
	}
}
