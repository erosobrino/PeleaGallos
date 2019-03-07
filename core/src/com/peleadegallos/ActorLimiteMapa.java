package com.peleadegallos;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The type Actor limite mapa.
 */
public class ActorLimiteMapa extends Actor {

    /**
     * The Mundo.
     */
    World mundo;

    /**
     * The Body izq.
     */
    Body bodyIzq, /**
     * The Body der.
     */
    bodyDer;

    /**
     * The Fixture izq.
     */
    Fixture fixtureIzq, /**
     * The Fixture der.
     */
    fixtureDer;

    /**
     * Instantiates a new Actor limite mapa.
     *
     * @param mundo         the mundo
     * @param juego         the juego
     * @param altoPantalla  the alto pantalla
     * @param anchoPantalla the ancho pantalla
     */
    public ActorLimiteMapa(World mundo, JuegoPrincipal juego, int altoPantalla, int anchoPantalla) {
        this.mundo = mundo;

        BodyDef def = new BodyDef();
        def.position.set(-2, 0);
        def.type = BodyDef.BodyType.StaticBody;
        bodyIzq = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(0, altoPantalla);
        fixtureIzq = bodyIzq.createFixture(forma, 3);
        fixtureIzq.setUserData("limiteIzquierda");

        def.position.set(14, 0);
        def.type = BodyDef.BodyType.StaticBody;
        bodyDer = mundo.createBody(def);

        fixtureDer = bodyDer.createFixture(forma, 3);
        fixtureDer.setUserData("limiteDerecha");
        forma.dispose();
    }

    /**
     * Elimina.
     */
    public void elimina() {
        bodyIzq.destroyFixture(fixtureIzq);
        bodyDer.destroyFixture(fixtureDer);
        mundo.destroyBody(bodyIzq);
        mundo.destroyBody(bodyDer);
    }
}
