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

import sun.rmi.runtime.Log;

public class Suelo extends Actor {
    Texture textura;

    World mundo;

    Body body;

    Fixture fixture;

    JuegoPrincipal juego;

    int anchoPantalla, altoPantalla;
    float tamañoX, tamañoY;//Son la mitad del real
    boolean relleno;

    public Suelo(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, int anchoPantalla, int altoPantalla, float tamañoX, float tamañoY, boolean relleno) {
        this.mundo = mundo;
        this.textura = textura;
        this.juego = juego;
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.tamañoX = tamañoX;
        this.tamañoY = tamañoY;
        this.relleno = relleno;

        BodyDef def = new BodyDef();
        def.position.set(posicion.x - 1f, posicion.y + 0.5f);
        def.type = BodyDef.BodyType.StaticBody;
        body = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(tamañoX, tamañoY);
        fixture = body.createFixture(forma, 1);
        fixture.setUserData("suelo");
        forma.dispose();

    }

    public Suelo(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, int anchoPantalla, int altoPantalla, boolean relleno) {
        this(mundo, textura, posicion, juego, anchoPantalla, altoPantalla, 1, 1, relleno);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + 1f * juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y - 0.5f * juego.PIXEL_METRO_Y);
        if (relleno) {
            float h = getY();
            while (h >= 0) {
                batch.draw(textura, getX(), 0, juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                h -= textura.getHeight() / juego.PIXEL_METRO_Y;
            }
        }
        batch.draw(textura, getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
    }

    @Override
    public void act(float delta) {
    }

    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
