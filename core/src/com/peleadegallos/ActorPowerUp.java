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


/**
 * El actor para los power ups
 */
public class ActorPowerUp extends Actor {

    /**
     * Su cuerpo
     */
    Body body;
    /**
     * La descripcin del cuerpo
     */
    Fixture fixture;

    /**
     * La Cantidad total.
     */
    static int cantidadTotal = 0;
    /**
     * El Id power up, para saber cual es al momento del choque
     */
    int idPowerUp;
    /**
     * La Textura.
     */
    Texture textura;
    /**
     * El Mundo.
     */
    World mundo;
    /**
     * El Juego.
     */
    JuegoPrincipal juego;
    /**
     * Si se ha Recogido.
     */
    boolean recogida = false;

    /**
     * Los tipos de powerUp
     */
    enum Tipo {
        /**
         * Quita vida 10.
         */
        quitaVida(0),
        /**
         * Suma vida 10.
         */
        sumaVida(1),
        /**
         * Pierde turno.
         */
        pierdeTurno(2),
        /**
         * Rival pierde 10 vida.
         */
        rivalquitaVida(3),
        /**
         * Rival gana 10 vida.
         */
        rivalSumaVida(4),
        /**
         * Doble balas en los siguientes turnos y tiene las balas actuales mas el total en ese turno.
         */
        dobleBalas(5);

        Tipo(int i) {
        }
    }

    /**
     * Su tipo
     */
    Tipo tipoPowerUp;

    /**
     * Instantiates a new Actor power up.
     *
     * @param mundo   el mundo
     * @param textura su textura
     * @param juego   la clase de juego principal
     */
    public ActorPowerUp(World mundo, Texture textura, JuegoPrincipal juego) {
        this.mundo = mundo;
        this.textura = textura;
        this.juego = juego;

        cantidadTotal++;
        idPowerUp = cantidadTotal;

        int randX = (int) (Math.random() * ((15) + 1));
        tipoPowerUp = Tipo.values()[(int) (Math.random() * ((5) + 1))];

        Vector2 posicion = new Vector2(randX, 8);

        BodyDef def = new BodyDef();
        def.position.set(posicion.x - 1.5f, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(def);
        body.setFixedRotation(true);    //Asi la caja no se puede volvar

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(0.25f, 0.25f);
        fixture = body.createFixture(forma, 1);
        fixture.setUserData(idPowerUp);                    //Con este vamos cambiando la propiedad recogido cuand esta se choca
        forma.dispose();
    }

    /**
     * Modifica la posicion del actor con la posicion del body de la caja
     * Se tiene que cambia la realcion porque uno esta en metros y el otro en pixeles
     *
     * @param batch       el batch que se utiliza para dibujar
     * @param parentAlpha el alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + juego.PIXEL_METRO_X * 1.75f, body.getPosition().y * juego.PIXEL_METRO_Y + juego.PIXEL_METRO_Y * 0.25f);
        batch.draw(textura, getX(), getY(), juego.PIXEL_METRO_X * 2 * 0.25f, juego.PIXEL_METRO_Y * 2 * 0.25f);
    }


    /**
     * Destruye las fixtures y los cuerpos del mundo para no cargar la memoria
     */
    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
