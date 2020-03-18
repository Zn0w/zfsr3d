package com.zn0w.zfsr3d.fs_components;

import java.util.ArrayList;

public class Node {
	
	private Node parent;
	private ArrayList<Node> children;
	
	private String component_name;
	
	
	public Node(String component_name) {
		this.component_name = component_name;
		parent = null;
		children = new ArrayList<Node>();
	}
	
	public Node(Node parent, String component_name) {
		this.component_name = component_name;
		this.parent = parent;
		children = new ArrayList<Node>();
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void addChild(Node node) {
		children.add(node);
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}

	public String getComponentName() {
		return component_name;
	}

	public void setComponentName(String component_name) {
		this.component_name = component_name;
	}
	
}