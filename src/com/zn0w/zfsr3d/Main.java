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
		
		String dirName = "D:\\work";
		
		FileTree fs = new FileTree(new Node(dirName));

        try {
			Files.list(new File(dirName).toPath())
			        //.limit(10)
			        .forEach(path -> {
			            System.out.println(path);
			            fs.getRoot().addChild(new Node(fs.getRoot(), path.getFileName().toString()));
			        });
			
			ArrayList<Node> components = fs.getRoot().getChildren();
			for (int i = 0; i < components.size(); i++) {
				System.out.println("Node " + i + ": " + components.get(i).getComponentName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}