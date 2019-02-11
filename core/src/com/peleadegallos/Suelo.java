package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Suelo extends Actor {
    Texture textura;

    World mundo;

    Body body;

    Fixture fixture;

    JuegoPrincipal juego;

    int anchoPantalla, altoPantalla;

    public Suelo(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, int anchoPantalla, int altoPantalla) {
        this.mundo = mundo;
        this.textura = textura;
        this.juego = juego;
        this.altoPantalla=altoPantalla;
        this.anchoPantalla=anchoPantalla;

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.StaticBody;
        body = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(anchoPantalla/2, textura.getHeight()/juego.PIXEL_METRO);
        fixture = body.createFixture(forma, 3);
        forma.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO, body.getPosition().y * juego.PIXEL_METRO);
        batch.draw(textura, getX(), getY(),anchoPantalla,textura.getHeight()+30);
    }

    @Override
    public void act(float delta) {
//        body.setLinearVelocity(8,8);
    }

    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
