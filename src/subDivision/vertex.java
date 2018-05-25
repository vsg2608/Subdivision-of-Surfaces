package subDivision;

import java.util.ArrayList;
import java.util.List;

public class vertex
{
	public float x,y,z;
	public float tu,tv;
	public float nx, ny, nz;
	public List<vertex> connectedVertices=new ArrayList<vertex>();
	
	public vertex(float X, float Y, float Z) {
		x=X;
		y=Y;
		z=Z;
		tu=tv=10;
		nx=ny=nz=(float) Math.pow(1/3, 0.5);
	}
	
	public vertex(float X, float Y, float Z, float Tu, float Tv, float Nx, float Ny, float Nz) {
		x=X;
		y=Y;
		z=Z;
		tu=Tu;
		tv=Tv;
		nx=Nx;
		ny=Ny;
		nz=Nz;
	}	
	
	public void PrintVertex() {
		System.out.println(x+",  "+y+",  "+z+ " ="+connectedVertices.size());
	}
	
	
}
