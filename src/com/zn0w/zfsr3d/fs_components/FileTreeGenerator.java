package com.zn0w.zfsr3d.fs_components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileTreeGenerator {
	
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
		if (!node.isDirectory()) {
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