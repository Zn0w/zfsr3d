package com.zn0w.zfsr3d;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.Node;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		String dirName = "C:\\Users\\blagi\\Desktop\\test";
		
        try {
        	@SuppressWarnings("unused")
			FileTree fs = getFSTree(dirName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO move methods below in a separate class
	
	static public FileTree getFSTree(String root_dir) throws IOException {
		FileTree fs = new FileTree(new Node(root_dir));
		
		Files.list(new File(root_dir).toPath())
        .forEach(path -> {
            Node node = new Node(fs.getRoot(), path.getFileName().toString());
            try {
				getChildren(node, root_dir + "/");
				fs.getRoot().addChild(node);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
		
		return fs;
	}
	
	static public void getChildren(Node node, String abs_path) throws IOException {
		// if it's not a directory
		if (node.getComponentName().contains(".")) {
			return;
		}
		
		Files.list(new File(abs_path + node.getComponentName()).toPath())
        .forEach(path -> {
            Node child_node = new Node(node, path.getFileName().toString());
        	try {
				getChildren(child_node, abs_path + node.getComponentName() + "/");
				node.addChild(child_node);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
	}
	
}