package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Jugador extends Actor {

    Texture textura;

    World mundo;

    Body body;

    Fixture fixture;

    boolean vivo = true;

    int vida;

    JuegoPrincipal juego;

    public Jugador(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego) {
        this.mundo = mundo;
        this.textura = textura;

        this.juego = juego;

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(forma, 3);
        forma.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO, body.getPosition().y * juego.PIXEL_METRO);
        batch.draw(textura, getX(), getY()-10);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched())
            body.setLinearVelocity(1, 1);
    }

    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
