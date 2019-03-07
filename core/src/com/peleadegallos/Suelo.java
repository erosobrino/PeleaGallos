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
 * The type Suelo.
 */
public class Suelo extends Actor {
    /**
     * The Textura.
     */
    Texture textura;

    /**
     * The Mundo.
     */
    World mundo;

    /**
     * The Body.
     */
    Body body, /**
     * The Borde izq.
     */
    bordeIzq, /**
     * The Borde der.
     */
    bordeDer;


    /**
     * The Fixture.
     */
    Fixture fixture, /**
     * The Fixture izq.
     */
    fixtureIzq, /**
     * The Fixture der.
     */
    fixtureDer;

    /**
     * The Juego.
     */
    JuegoPrincipal juego;

    /**
     * The Ancho pantalla.
     */
    int anchoPantalla, /**
     * The Alto pantalla.
     */
    altoPantalla;
    /**
     * The Tamaño x.
     */
    float tamañoX, /**
     * The Tamaño y.
     */
    tamañoY;//Son la mitad del real
    /**
     * The Relleno.
     */
    boolean relleno;

    /**
     * Instantiates a new Suelo.
     *
     * @param mundo         the mundo
     * @param textura       the textura
     * @param posicion      the posicion
     * @param juego         the juego
     * @param anchoPantalla the ancho pantalla
     * @param altoPantalla  the alto pantalla
     * @param tamañoX       the tamaño x
     * @param tamañoY       the tamaño y
     * @param relleno       the relleno
     */
    public Suelo(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, int anchoPantalla, int altoPantalla, float tamañoX, float tamañoY, boolean relleno) {
        this.mundo = mundo;
        this.textura = textura;
        this.juego = juego;
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.tamañoX = tamañoX;
        this.tamañoY = tamañoY;
        this.relleno = relleno;

        BodyDef def = new BodyDef();                                    //Hitbox para el suelo completo, no se utiliza con estos mapas
        def.position.set(posicion.x - 1f, posicion.y + 0.5f);
        def.type = BodyDef.BodyType.StaticBody;
        body = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(tamañoX, tamañoY);
        fixture = body.createFixture(forma, 1);
        fixture.setUserData("sueloTodo");
        forma.dispose();

        BodyDef parteArriba=new BodyDef();                               //Utilizado para margen de arriba, evita que salte si no lo toca
        parteArriba.position.set(posicion.x-1, posicion.y + 0.5f+0.97f); //Sustituye a sueloTodo
        parteArriba.type=BodyDef.BodyType.StaticBody;
        Body bordeArriba=mundo.createBody(parteArriba);

        PolygonShape formaArriba=new PolygonShape();
        formaArriba.setAsBox(tamañoX,0.03f);
        Fixture arriba=bordeArriba.createFixture(formaArriba,1);
        arriba.setUserData("suelo");
        formaArriba.dispose();

        BodyDef izqDef = new BodyDef();                                 //Evita que al estar pegado al borde de un suelo se pueda saltar si ya esta saltando
        izqDef.position.set(posicion.x - 1f+tamañoX, posicion.y + 0.5f);
        izqDef.type = BodyDef.BodyType.StaticBody;
        bordeIzq = mundo.createBody(izqDef);

        PolygonShape formaBorde = new PolygonShape();
        formaBorde.setAsBox(0.02f, tamañoY*0.97f);
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

    /**
     * Instantiates a new Suelo.
     *
     * @param mundo         the mundo
     * @param textura       the textura
     * @param posicion      the posicion
     * @param juego         the juego
     * @param anchoPantalla the ancho pantalla
     * @param altoPantalla  the alto pantalla
     * @param relleno       the relleno
     */
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

    /**
     * Elimina.
     */
    public void elimina() {
        bordeIzq.destroyFixture(fixtureIzq);
        bordeDer.destroyFixture(fixtureDer);
        body.destroyFixture(fixture);

        mundo.destroyBody(bordeDer);
        mundo.destroyBody(bordeIzq);
        mundo.destroyBody(body);
    }
}
