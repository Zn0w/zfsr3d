package com.zn0w.zfsr3d;

import java.awt.Color;
import java.io.IOException;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.FileTreeGenerator;
import com.zn0w.zfsr3d.graphics.DirectoryRenderObject;
import com.zn0w.zfsr3d.graphics.Display;
import com.zn0w.zfsr3d.graphics.RenderObject;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		String dirName = "C:\\Users\\blagi\\Desktop\\test";
		
        try {
			FileTree fs = FileTreeGenerator.getFSTree(dirName);
			
			Display display = new Display(1280, 720, "zfsr3d display test");
			
			display.setClearColor(Color.darkGray);
			
			while (display.isClosed()) {
				display.render();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}