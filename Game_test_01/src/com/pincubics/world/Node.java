package com.pincubics.world;

public class Node {

	public Vector2i tile;
	public Node parent;
	public double fcost, gcost, hcost;
	
	public Node(Vector2i tile, Node parent, double gcost, double hcost) {
		super();
		this.tile = tile;
		this.parent = parent;
		this.gcost = gcost;
		this.hcost = hcost;
		this.fcost = gcost + hcost;
	}
	
}
