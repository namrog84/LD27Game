package com.newrog.myGame;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.newrog.entities.Heart;
import com.newrog.simplegdx.Art;
import com.newrog.simplegdx.Art.Hearts;

public class Level {

	public BatchTiledMapRenderer tileMapRenderer;
	TiledMap map;

	int blocked[][];
	// TextureAtlas atlas;
	TmxMapLoader loader;
	AtlasTmxMapLoader atmp;
	TiledMapTileLayer tmtl;
	public int width, height, tileSize;

	public Rectangle finishRect;

	public Level(GameScreen game, int num) {
		loader = new TmxMapLoader();
		atmp = new AtlasTmxMapLoader();

		map = loader.load("data/level" + num + ".tmx");

		tileMapRenderer = new OrthogonalTiledMapRenderer(map);

		tmtl = (TiledMapTileLayer) (tileMapRenderer.getMap().getLayers().get("tileLayer1"));

		width = tmtl.getWidth();
		height = tmtl.getHeight();
		tileSize = (int) tmtl.getTileHeight();
		blocked = new int[width][height];

		for (int i = 1; i < map.getTileSets().getTileSet("tilesMap").size(); i++) {
			map.getTileSets().getTileSet("tilesMap").getTile(i).getTextureRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}

		Array<StaticTiledMapTile> UpframeTiles = new Array<StaticTiledMapTile>();
		UpframeTiles.add(new StaticTiledMapTile(Art.upSpikes[0]));
		UpframeTiles.add(new StaticTiledMapTile(Art.upSpikes[1]));
		UpframeTiles.add(new StaticTiledMapTile(Art.upSpikes[2]));
		UpframeTiles.add(new StaticTiledMapTile(Art.upSpikes[3]));
		AnimatedTiledMapTile upAnimatedTile = new AnimatedTiledMapTile(.15f, UpframeTiles);
		upAnimatedTile.getProperties().put("block", "2");
		
		
		Array<StaticTiledMapTile> DownframeTiles = new Array<StaticTiledMapTile>();
		DownframeTiles.add(new StaticTiledMapTile(Art.downSpikes[0]));
		DownframeTiles.add(new StaticTiledMapTile(Art.downSpikes[1]));
		DownframeTiles.add(new StaticTiledMapTile(Art.downSpikes[2]));
		DownframeTiles.add(new StaticTiledMapTile(Art.downSpikes[3]));
		AnimatedTiledMapTile DownAnimatedTile = new AnimatedTiledMapTile(.15f, DownframeTiles);
		DownAnimatedTile.getProperties().put("block", "2");
		
		
		Array<StaticTiledMapTile> LeftframeTiles = new Array<StaticTiledMapTile>();
		LeftframeTiles.add(new StaticTiledMapTile(Art.leftSpikes[0]));
		LeftframeTiles.add(new StaticTiledMapTile(Art.leftSpikes[1]));
		LeftframeTiles.add(new StaticTiledMapTile(Art.leftSpikes[2]));
		LeftframeTiles.add(new StaticTiledMapTile(Art.leftSpikes[3]));
		AnimatedTiledMapTile LeftAnimatedTile = new AnimatedTiledMapTile(.15f, LeftframeTiles);
		LeftAnimatedTile.getProperties().put("block", "2");
		
		
		Array<StaticTiledMapTile> rightframeTiles = new Array<StaticTiledMapTile>();
		rightframeTiles.add(new StaticTiledMapTile(Art.rightSpikes[0]));
		rightframeTiles.add(new StaticTiledMapTile(Art.rightSpikes[1]));
		rightframeTiles.add(new StaticTiledMapTile(Art.rightSpikes[2]));
		rightframeTiles.add(new StaticTiledMapTile(Art.rightSpikes[3]));
		AnimatedTiledMapTile rightAnimatedTile = new AnimatedTiledMapTile(.15f, rightframeTiles);
		rightAnimatedTile.getProperties().put("block", "2");
		
		
		
		
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tileLayer1");
		for (int i = 0; i < layer.getWidth(); i++) {
			for (int j = 0; j < layer.getHeight(); j++) {
				
				if (layer.getCell(i, j).getTile().getProperties().containsKey("spikes")
						&& layer.getCell(i, j).getTile().getProperties().get("spikes", String.class).equals("1")) {
					layer.getCell(i, j).setTile(upAnimatedTile);
				}else if (layer.getCell(i, j).getTile().getProperties().containsKey("spikes")
						&& layer.getCell(i, j).getTile().getProperties().get("spikes", String.class).equals("2")) {
					layer.getCell(i, j).setTile(DownAnimatedTile);
				}else if (layer.getCell(i, j).getTile().getProperties().containsKey("spikes")
						&& layer.getCell(i, j).getTile().getProperties().get("spikes", String.class).equals("3")) {
					layer.getCell(i, j).setTile(LeftAnimatedTile);
				}else if (layer.getCell(i, j).getTile().getProperties().containsKey("spikes")
						&& layer.getCell(i, j).getTile().getProperties().get("spikes", String.class).equals("4")) {
					layer.getCell(i, j).setTile(rightAnimatedTile);
				}
				
			}
		}

		buildBlocked();
		RectangleMapObject mo = (RectangleMapObject) map.getLayers().get("objLayer1").getObjects().get("end");
		finishRect = new Rectangle(mo.getRectangle());
		Heart h = new Heart(Hearts.FULL);
		h.position.x = finishRect.x + finishRect.width/2;
		h.position.y = finishRect.y + finishRect.height/2;
		game.hurts.add(h);
	}

	private void buildBlocked() {
		for (int i = 0; i < tmtl.getWidth(); i++) {
			for (int j = 0; j < tmtl.getHeight(); j++) {
				Object t = tmtl.getCell(i, j).getTile().getProperties().get("block");
				if (t != null) {
					if (t.equals("1")) {
						// System.out.println("BLOCKED");
						blocked[i][j] = 1;
					} else if (t.equals("2")) {
						blocked[i][j] = 2; // DEATH!
					} else {
						blocked[i][j] = 0;
					}
				}
			}
		}
	}

	public boolean isFree(float xc, float yc, int w, int h, Vector2 Acceleration) {

		double e = 0.1;
		int x0 = (int) ((xc) / tileSize);
		int y0 = (int) (yc / tileSize);

		int x1 = (int) ((xc + w - e) / tileSize);
		int y1 = (int) ((yc + h - e) / tileSize);
		boolean ok = true;

		if (blocked[x0][y0] != 0) {
			return false;
		}
		if (blocked[x1][y0] != 0) {
			return false;
		}
		if (blocked[x0][y1] != 0) {
			return false;
		}
		if (blocked[x1][y1] != 0) {
			return false;
		}
		return ok;
	}

	public int isType(float xc, float yc, int w, int h, Vector2 Acceleration) {

		double e = 0.1;
		int x0 = (int) ((xc) / tileSize);
		int y0 = (int) (yc / tileSize);

		int x1 = (int) ((xc + w - e) / tileSize);
		int y1 = (int) ((yc + h - e) / tileSize);

		if (blocked[x0][y0] != 0) {
			return blocked[x0][y0];
		}
		if (blocked[x1][y0] != 0) {
			return blocked[x1][y0];
		}
		if (blocked[x0][y1] != 0) {
			return blocked[x0][y1];
		}
		if (blocked[x1][y1] != 0) {
			return blocked[x1][y1];
		}
		return 0;
	}

	public void render() {

		tileMapRenderer.render();
	}
}
