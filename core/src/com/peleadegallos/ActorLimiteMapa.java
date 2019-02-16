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

public class ActorLimiteMapa extends Actor {

    World mundo;

    Body bodyIzq, bodyDer;

    Fixture fixture;

    public ActorLimiteMapa(World mundo, JuegoPrincipal juego, int altoPantalla, int anchoPantalla) {
        this.mundo = mundo;

        BodyDef def = new BodyDef();
        def.position.set(-2, 0);
        def.type = BodyDef.BodyType.StaticBody;
        bodyIzq = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(0, altoPantalla);
        fixture = bodyIzq.createFixture(forma, 3);
        fixture.setUserData("limiteIzquierda");

        def.position.set(14, 0);
        def.type = BodyDef.BodyType.StaticBody;
        bodyDer = mundo.createBody(def);

        fixture = bodyDer.createFixture(forma, 3);
        fixture.setUserData("limiteDerecha");
        forma.dispose();
    }

    public void elimina() {
        bodyIzq.destroyFixture(fixture);
        bodyDer.destroyFixture(fixture);
        mundo.destroyBody(bodyIzq);
        mundo.destroyBody(bodyDer);
    }
}
