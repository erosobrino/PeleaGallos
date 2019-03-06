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

    Body body, bordeIzq, bordeDer;


    Fixture fixture, fixtureIzq, fixtureDer;

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

        BodyDef izqDef = new BodyDef();                                 //Evita que al estar pegado al borde de un suelo se pueda saltar si ya esta saltando
        izqDef.position.set(posicion.x - 1f+tamañoX, posicion.y + 0.5f);
        izqDef.type = BodyDef.BodyType.StaticBody;
        bordeIzq = mundo.createBody(izqDef);

        PolygonShape formaBorde = new PolygonShape();
        formaBorde.setAsBox(0.02f, tamañoY*0.95f);
        fixtureIzq = bordeIzq.createFixture(formaBorde, 1);
        fixtureIzq.setUserData("sueloIzq");

        BodyDef derDef = new BodyDef();                                 //Evita que al estar pegado al borde de un suelo se pueda saltar si ya esta saltando
        derDef.position.set(posicion.x - 1f-1, posicion.y + 0.5f);
        derDef.type = BodyDef.BodyType.StaticBody;
        bordeDer = mundo.createBody(derDef);

        fixtureDer = bordeDer.createFixture(formaBorde, 1);
        fixtureDer.setUserData("sueloDer");

        formaBorde.dispose();
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
        bordeIzq.destroyFixture(fixtureIzq);
        bordeDer.destroyFixture(fixtureDer);
        body.destroyFixture(fixture);

        mundo.destroyBody(bordeDer);
        mundo.destroyBody(bordeIzq);
        mundo.destroyBody(body);
    }
}
