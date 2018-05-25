package subDivision;

import java.util.List;

public class edge {
	
	public vertex Start;
	public vertex End;
	public face Left;
	public face Right;
	public vertex oddVertex;
	
	public edge(vertex v1, vertex v2,face left) {
		this.Start=v1;
		this.End=v2;
		this.Left=left;
		this.oddVertex=null;
		this.Right=null;
		if(!v1.connectedVertices.contains(v2))
			v1.connectedVertices.add(v2);
		if(!v2.connectedVertices.contains(v1))
			v2.connectedVertices.add(v1);
	}
	

	//Function to search a particular edge from the edges list
	public boolean searchEdge(List<edge> Edges, edge Edge,face right) {
		for(edge E:Edges) {
			if((E.Start==Edge.Start&&E.End==Edge.End)||(E.Start==Edge.End&&E.End==Edge.Start)) {
				E.Right=right;
				return false;
			}
		}
		return true;
	}
	
	//function to find odd vertex corresponding to the given edge and return it to the function in the main
	public vertex findOddVertex(){
		if(oddVertex!=null)
			return oddVertex;
		vertex EvenVertex=null;
		vertex L=null;
		vertex R=null;
		
		if(this.Left.V1!=this.Start&&this.Left.V1!=this.End)
			L=this.Left.V1;
		else if(this.Left.V2!=this.Start&&this.Left.V2!=this.End)
			L=this.Left.V2;
		else if(this.Left.V3!=this.Start&&this.Left.V3!=this.End)
			L=this.Left.V3;
		
		if(this.Right==null) {			
			EvenVertex=new vertex(0.5f*this.Start.x + 0.5f*this.End.x,
					0.5f*this.Start.y + 0.5f*this.End.y,
					0.5f*this.Start.z + 0.5f*this.End.z,
					0.5f*this.Start.tu + 0.5f*this.End.tu,
					0.5f*this.Start.tv + 0.5f*this.End.tv,
					0.5f*this.Start.nx + 0.5f*this.End.nx,
					0.5f*this.Start.ny + 0.5f*this.End.ny,
					0.5f*this.Start.nz + 0.5f*this.End.nz);
		}else {
			if(this.Right.V1!=this.Start&&this.Right.V1!=this.End)
				R=this.Right.V1;
			else if(this.Right.V2!=this.Start&&this.Right.V2!=this.End)
				R=this.Right.V2;
			else if(this.Right.V3!=this.Start&&this.Right.V3!=this.End)
				R=this.Right.V3;
			EvenVertex= new vertex(0.375f*this.Start.x + 0.375f*this.End.x + 0.125f*L.x + 0.125f*R.x,
					0.375f*this.Start.y + 0.375f*this.End.y + 0.125f*L.y + 0.125f*R.y,
					0.375f*this.Start.z + 0.375f*this.End.z + 0.125f*L.z + 0.125f*R.z,
					0.375f*this.Start.tu + 0.375f*this.End.tu + 0.125f*L.tu + 0.125f*R.tu,
					0.375f*this.Start.tv + 0.375f*this.End.tv + 0.125f*L.tv + 0.125f*R.tv,
					0.375f*this.Start.nx + 0.375f*this.End.nx + 0.125f*L.nx + 0.125f*R.nx,
					0.375f*this.Start.ny + 0.375f*this.End.ny + 0.125f*L.ny + 0.125f*R.ny,
					0.375f*this.Start.nz + 0.375f*this.End.nz + 0.125f*L.nz + 0.125f*R.nz) ;
		}
		oddVertex=EvenVertex;
		return EvenVertex;
	}

	public edge checkEdge(List<edge> edges, edge Edge, face right) {
		for(edge E:edges) {
			if((E.Start==Edge.Start&&E.End==Edge.End)||(E.Start==Edge.End&&E.End==Edge.Start)) {
				return E;
			}
		}
		return Edge;
	}
}
