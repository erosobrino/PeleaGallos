package com.peleadegallos;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Actor utilizado para limitar los limites de la pantalla, este es estatico
 */
public class ActorLimiteMapa extends Actor {

    /**
     * El mundo que va a crear los cuerpos del actor y al que se asocia
     */
    World mundo;

    /**
     * El cuerpo de la parte izquierda de la pantalla
     */
    Body bodyIzq, /**
     * El cuerpo de la parte izquierda de la pantalla
     */
    bodyDer;

    /**
     * Descripcion del cuerpo de la izquierda (tamño, forma, densidad)
     */
    Fixture fixtureIzq, /**
     * Descripcion del cuerpo de la derecha (tamño, forma, densidad)
     */
    fixtureDer;

    /**
     * Inicializa los limites del juego en el borde de la pantalla
     *
     * @param mundo         el mundo que lo crea
     * @param altoPantalla  el alto de la pantalla
     * @param anchoPantalla el ancho de la pantalla
     */
    public ActorLimiteMapa(World mundo, int altoPantalla, int anchoPantalla) {
        this.mundo = mundo;

        BodyDef def = new BodyDef();
        def.position.set(-2, 0);                    //Posicion con offset porque se cambia la posicion de la camara
        def.type = BodyDef.BodyType.StaticBody;     //0,0
        bodyIzq = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(0, altoPantalla);
        fixtureIzq = bodyIzq.createFixture(forma, 3);
        fixtureIzq.setUserData("limiteIzquierda");              //Para saber cuando de toca se usa el userdata

        def.position.set(14, 0);                    //Posicion con offset porque se cambia la posicion de la camara
        def.type = BodyDef.BodyType.StaticBody;     //16,0
        bodyDer = mundo.createBody(def);

        fixtureDer = bodyDer.createFixture(forma, 3);
        fixtureDer.setUserData("limiteDerecha");
        forma.dispose();
    }

    /**
     * Destruye las fixtures y los cuerpos del mundo para no cargar la memoria
     */
    public void elimina() {
        bodyIzq.destroyFixture(fixtureIzq);
        bodyDer.destroyFixture(fixtureDer);
        mundo.destroyBody(bodyIzq);
        mundo.destroyBody(bodyDer);
    }
}
