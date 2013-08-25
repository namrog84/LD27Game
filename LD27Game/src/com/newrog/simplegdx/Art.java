package com.newrog.simplegdx;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.newrog.simplegdx.internal.Files;

public class Art {
	private Art() {

	}

	public static TextureRegion logoTextureRegion;
	public static TextureRegion winnerEndTextureRegion;
	public static TextureRegion playerTextureRegion;
	public static BitmapFont blackFont;
	public static BitmapFont whiteFont;
	public static BitmapFont whiteFontHelv;
	public static BitmapFont hieroArial;
	public static BitmapFont hieroHelv;
	public static TextureRegion allRings;
	public static TextureRegion[] rings;
	
	public static TextureRegion mainMenuLogo;
	public static TextureRegion cloud1;
	public static TextureRegion cloud2;
	
	public static TextureRegion[] hearts = new TextureRegion[6];
	public static TextureRegion[] pixels = new TextureRegion[5];
	
	//public static TextureRegion allSpikes;
	public static TextureRegion[] upSpikes = new TextureRegion[4];
	public static TextureRegion[] leftSpikes = new TextureRegion[4];
	public static TextureRegion[] rightSpikes = new TextureRegion[4];
	public static TextureRegion[] downSpikes = new TextureRegion[4];
	
	public static enum Hearts{
		FULL(0), EMPTY(1), TOPLEFT(2), TOPRIGHT(3), BOTTOMLEFT(4), BOTTOMRIGHT(5);
		public int v;
		private Hearts(int value){
			v = value;
		}
	}
	
	
	public static void loadResources() {
		TextureAtlas myTextures = new TextureAtlas("data/art.txt");
		logoTextureRegion = myTextures.findRegion("84bit_3xpixel");
		winnerEndTextureRegion = myTextures.findRegion("winnerEnd");
		playerTextureRegion = myTextures.findRegion("player2");
		cloud1 = myTextures.findRegion("cloud1");
		cloud2 = myTextures.findRegion("cloud2");
		
		mainMenuLogo = myTextures.findRegion("mainMenu");
		
		allRings = myTextures.findRegion("rings");

		
		TextureRegion[][] tmp = allRings.split(20, 8);
		
		
		rings = new TextureRegion[8];
		 for (int j = 0; j < 8; j++) { 
			 rings[j] = tmp[j][0];
		 }
		
		
		hearts[Hearts.FULL.v] = myTextures.findRegion("fullHeart");
		hearts[Hearts.EMPTY.v] = myTextures.findRegion("emptyHeart");
		hearts[Hearts.TOPLEFT.v] = myTextures.findRegion("topLeftFullHeart");
		hearts[Hearts.TOPRIGHT.v] = myTextures.findRegion("topRightFullHeart");
		hearts[Hearts.BOTTOMLEFT.v] = myTextures.findRegion("BottomLeftFullHeart");
		hearts[Hearts.BOTTOMRIGHT.v] = myTextures.findRegion("BottomRightFullHeart");
		
		pixels[0] = myTextures.findRegion("whiteParticle");
		pixels[1] = myTextures.findRegion("redParticle");
		pixels[2] = myTextures.findRegion("tanParticle");
		pixels[3] = myTextures.findRegion("greenParticle");
		pixels[4] = myTextures.findRegion("greyParticle");
		
		
		upSpikes[0] = myTextures.findRegion("static10");
		upSpikes[1] = myTextures.findRegion("static12");
		upSpikes[2] = myTextures.findRegion("static13");
		upSpikes[3] = myTextures.findRegion("static14");
		
		leftSpikes[0] = myTextures.findRegion("left1");
		leftSpikes[1] = myTextures.findRegion("left2");
		leftSpikes[2] = myTextures.findRegion("left3");
		leftSpikes[3] = myTextures.findRegion("left4");
		
		rightSpikes[0] = myTextures.findRegion("right1");
		rightSpikes[1] = myTextures.findRegion("right2");
		rightSpikes[2] = myTextures.findRegion("right3");
		rightSpikes[3] = myTextures.findRegion("right4");
		
		downSpikes[0] = myTextures.findRegion("down1");
		downSpikes[1] = myTextures.findRegion("down2");
		downSpikes[2] = myTextures.findRegion("down3");
		downSpikes[3] = myTextures.findRegion("down4");
		
		
		blackFont = new BitmapFont(Files.get("data/blackfont.fnt"), Files.get("data/blackfont_0.png"), false);
		whiteFont = new BitmapFont(Files.get("data/whitefont.fnt"), Files.get("data/whitefont_0.png"), false);
		whiteFontHelv = new BitmapFont(Files.get("data/whitefontHelv.fnt"), Files.get("data/whitefontHelv_0.png"), false);
		hieroArial = new BitmapFont(Files.get("data/hieroArial.fnt"), Files.get("data/hieroArial.png"), false);
		hieroHelv = new BitmapFont(Files.get("data/hieroHelv.fnt"), Files.get("data/hieroHelv.png"), false);
		
	}
}
