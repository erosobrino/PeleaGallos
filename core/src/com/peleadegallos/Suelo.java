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
 * El actor suelo, este es estatico
 */
public class Suelo extends Actor {
    /**
     * La Textura.
     */
    Texture textura;

    /**
     * El Mundo.
     */
    World mundo;
    //Los bodys son los cuerpos, estes tiene varias fixtures para poder gestionar donde se esta con el jugador
    /**
     * El Body.
     */
    Body body,
    /**
     * El Borde izq.
     */
    bordeIzq, /**
     * El Borde der.
     */
    bordeDer,bordeArriba;

    //Las fixture almacena los datos de los cuerpos
    /**
     * La Fixture.
     */
    Fixture fixture,
    /**
     * The Fixture izq.
     */
    fixtureIzq,
    /**
     * La Fixture der.
     */
    fixtureDer,fixtureArriba;

    /**
     * El Juego.
     */
    JuegoPrincipal juego;

    /**
     * El Ancho pantalla.
     */
    int anchoPantalla,
    /**
     * El Alto pantalla.
     */
    altoPantalla;
    /**
     * El Tamaño x.
     */
    float tamañoX, /**
     * El Tamaño y.
     */
    tamañoY;//Son la mitad del real
    /**
     * The Relleno.
     */
    boolean relleno;

    /**
     * Inicializa el suelo con todos los datos
     *
     * @param mundo         el mundo
     * @param textura       la textura
     * @param posicion      la posicion
     * @param juego         el juego
     * @param anchoPantalla el ancho pantalla
     * @param altoPantalla  el alto pantalla
     * @param tamañoX       el tamaño x
     * @param tamañoY       el tamaño y
     * @param relleno       el relleno
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
        bordeArriba=mundo.createBody(parteArriba);

        PolygonShape formaArriba=new PolygonShape();
        formaArriba.setAsBox(tamañoX,0.03f);
        fixtureArriba=bordeArriba.createFixture(formaArriba,1);
        fixtureArriba.setUserData("suelo");
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
     * Inicializa el suelo con algunos datos predefinidos
     *
     * @param mundo         el mundo
     * @param textura       la textura
     * @param posicion      la posicion
     * @param juego         la pantalla de juego principal
     * @param anchoPantalla el ancho pantalla
     * @param altoPantalla  el alto pantalla
     * @param relleno       si se rellena
     */
    public Suelo(World mundo, Texture textura, Vector2 posicion, JuegoPrincipal juego, int anchoPantalla, int altoPantalla, boolean relleno) {
        this(mundo, textura, posicion, juego, anchoPantalla, altoPantalla, 1, 1, relleno);
    }

    /**
     * Dibuja el suelo y si relleno es true, superpone varias veces la misma textura para que no queden huecos
     * @param batch dibuja las texturas
     * @param parentAlpha el alpha del padre
     */
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

    /**
     * Destruye las fixtures y los cuerpos del mundo para no cargar la memoria,
     * si la bala es de canon vibra y suena el sonido de disparo
     */
    public void elimina() {
        bordeIzq.destroyFixture(fixtureIzq);
        bordeDer.destroyFixture(fixtureDer);
        body.destroyFixture(fixture);
        bordeArriba.destroyFixture(fixtureArriba);

        mundo.destroyBody(bordeArriba);
        mundo.destroyBody(bordeDer);
        mundo.destroyBody(bordeIzq);
        mundo.destroyBody(body);
    }
}
