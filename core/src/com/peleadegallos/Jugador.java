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

    float tamañoX=0.5f, tamañoY=0.5f;

    public Jugador(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego) {
        this.mundo = mundo;
        this.textura = textura;

        this.juego = juego;

        BodyDef def = new BodyDef();
        System.out.println(posicion.x);
        def.position.set(posicion.x-1.5f,posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(tamañoX, tamañoY);
        fixture = body.createFixture(forma, 3);
        forma.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X+1.5f*juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y);
        batch.draw(textura, getX(),getY(),juego.PIXEL_METRO_X*2*tamañoX,juego.PIXEL_METRO_Y*2*tamañoY);

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
