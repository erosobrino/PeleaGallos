package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bala extends Actor {
    Texture textura;

    World mundo;

    Body body;

    Fixture fixture;

    JuegoPrincipal juego;

    int anchoPantalla, altoPantalla;
    float radio;//Son la mitad del real

    boolean impacto = false;

    int daño;
    int cont = 0;

    int fuerza;
    float angulo;

    public Bala(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, float radio, int daño, int fuerza, float angulo) {
        this.mundo = mundo;
        this.textura = textura;
        this.juego = juego;
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.radio = radio;
        this.daño = daño;
        this.fuerza = fuerza;
        this.angulo = angulo;

        BodyDef def = new BodyDef();
        def.position.set(posicion.x - 1.5f, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(def);

        CircleShape forma = new CircleShape();
        forma.setRadius(radio);
        fixture = body.createFixture(forma, 1);
        fixture.setUserData("bala");
        forma.dispose();

    }

    public Bala(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, float angulo) {
        this(mundo, textura, posicion, juego, 0.25f, 10, 1, angulo);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + radio * 7 * juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y + radio * juego.PIXEL_METRO_Y);
        batch.draw(textura, getX(), getY(), juego.PIXEL_METRO_X * 2 * radio, juego.PIXEL_METRO_Y * 2 * radio);
    }

    @Override
    public void act(float delta) {
        if (!impacto) {
            if (cont < 1) {
                Vector2 posicionCuerpo = body.getPosition();
                double x = fuerza * Math.cos(angulo);
                double y = fuerza * Math.sin(angulo);
                System.out.println(angulo);
                body.applyLinearImpulse((float) x, (float) y, posicionCuerpo.x, posicionCuerpo.y, true);
                cont++;
            }
        }
    }

    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
