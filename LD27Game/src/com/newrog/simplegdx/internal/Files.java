package com.newrog.simplegdx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Files {
	private Files() {
	}
	
	public static FileHandle get(String str){
		return Gdx.files.internal(str);
	
	}
}
