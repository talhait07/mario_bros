package com.rootnext.supermario.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.rootnext.supermario.SuperMario;
import com.rootnext.supermario.scenes.Hud;

/**
 * Created by rootnext on 11/30/16.
 */

public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(SuperMario.BRICK_BIT);



    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick collition", "");
        setCategoryFilter(SuperMario.DISTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);

    }
}
