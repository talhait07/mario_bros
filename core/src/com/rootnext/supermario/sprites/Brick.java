package com.rootnext.supermario.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rootnext on 11/30/16.
 */

public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);


    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick collition", "");

    }
}
