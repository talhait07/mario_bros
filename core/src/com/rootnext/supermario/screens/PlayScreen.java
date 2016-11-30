package com.rootnext.supermario.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rootnext.supermario.SuperMario;
import com.rootnext.supermario.scenes.Hud;
import com.rootnext.supermario.sprites.Mario;

/**
 * Created by rootnext on 11/29/16.
 */

public class PlayScreen implements Screen {
    private SuperMario game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    public Mario mario;

    public PlayScreen(SuperMario game){
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SuperMario.V_WIDTH / SuperMario.PPM, SuperMario.V_HEIGHT / SuperMario.PPM,gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map, 1 / SuperMario.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10 ), true);
        mario = new Mario(world);

        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body ;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SuperMario.PPM, (rect.getY() + rect.getHeight() / 2) / SuperMario.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / SuperMario.PPM, (rect.getHeight() / 2) / SuperMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ SuperMario.PPM, (rect.getY() + rect.getHeight() / 2)/ SuperMario.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2)/ SuperMario.PPM, (rect.getHeight() / 2)/ SuperMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


        //create brickes bodies/fixtures

        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ SuperMario.PPM, (rect.getY() + rect.getHeight() / 2)/ SuperMario.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2)/ SuperMario.PPM, (rect.getHeight() / 2)/ SuperMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create coins bodies/fixtures

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ SuperMario.PPM, (rect.getY() + rect.getHeight() / 2)/ SuperMario.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2)/ SuperMario.PPM, (rect.getHeight() / 2)/ SuperMario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            mario.b2body.applyLinearImpulse(new Vector2(0,4f), mario.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2body.getLinearVelocity().x <= 2)
            mario.b2body.applyLinearImpulse(new Vector2(0.1f, 0),mario.b2body.getWorldCenter(), true);
       if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2body.getLinearVelocity().x >= -2)
           mario.b2body.applyLinearImpulse(new Vector2(-0.1f, 0),mario.b2body.getWorldCenter(), true);


    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);
        gameCam.position.x = mario.b2body.getPosition().x;
        gameCam.update();

        renderer.setView(gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        // render our Box2drender
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();



    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
