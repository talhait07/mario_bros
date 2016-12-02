package com.rootnext.supermario.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rootnext.supermario.SuperMario;

/**
 * Created by rootnext on 11/30/16.
 */

public class Coin extends InteractiveTileObject {
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(SuperMario.COIN_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin collition", "");
        setCategoryFilter(SuperMario.DISTROYED_BIT);
        getCell().setTile(null);

    }
}
