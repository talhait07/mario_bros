package com.rootnext.supermario.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rootnext.supermario.SuperMario;
import com.rootnext.supermario.scenes.Hud;

/**
 * Created by rootnext on 11/30/16.
 */

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;// as of tile set starts from 1 and compiler starts from 0 so +1 added with the tile object id number
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(SuperMario.COIN_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin collition", "");
        setCategoryFilter(SuperMario.DISTROYED_BIT);
        getCell().setTile(tileSet.getTile(BLANK_COIN));

        Hud.addScore(300);
    }
}
