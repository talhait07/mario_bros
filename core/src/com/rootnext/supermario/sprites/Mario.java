package com.rootnext.supermario.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rootnext.supermario.SuperMario;
import com.rootnext.supermario.screens.PlayScreen;

/**
 * Created by rootnext on 11/29/16.
 */

public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marionRun,marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        setBounds(0,0, 16/ SuperMario.PPM, 16 / SuperMario.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=1; i<4; i++)
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        marionRun = new Animation(0.1f, frames);
        frames.clear();
        for(int i=4; i<6; i++)
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        marioJump = new Animation(0.1f, frames);
        frames.clear();

        defineMario();
        marioStand = new TextureRegion(getTexture(), 0,0,16,16);
        setRegion(marioStand);

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion regoion;
        switch (currentState) {
            case JUMPING:
                regoion = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                regoion = marionRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                regoion = marioStand;
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !regoion.isFlipX()){
            regoion.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && regoion.isFlipX()){
            regoion.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return regoion;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y <0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;

    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ SuperMario.PPM,32/ SuperMario.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ SuperMario.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
