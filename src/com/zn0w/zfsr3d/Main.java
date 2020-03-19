package com.zn0w.zfsr3d;

import java.io.IOException;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.FileTreeGenerator;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		String dirName = "C:\\Users\\blagi\\Desktop\\test";
		
        try {
        	@SuppressWarnings("unused")
			FileTree fs = FileTreeGenerator.getFSTree(dirName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}