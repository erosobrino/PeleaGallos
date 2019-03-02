package com.peleadegallos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bala extends Actor {

    public static int cantidadBalas = 0;
    Sprite sprite;

    World mundo;

    Body body;

    Fixture fixture;

    JuegoPrincipal juego;

    int anchoPantalla, altoPantalla;
    float radio;//Son la mitad del real

    boolean impacto = false;

    int daño;
    int cont = 0;

    float fuerza;
    float angulo;
    int idBala;
    Sound sonidoCanon;
    float offsetX, offsetY;
    Jugador jugador;

    public Bala(World mundo, Vector2 posicion, JuegoPrincipal juego, Jugador jugador) {
        this.mundo = mundo;
        this.juego = juego;
        this.angulo = (float) Math.toRadians(jugador.angulo);
        if (jugador.avanza)
            this.sprite = new Sprite(jugador.imgBala);
        else
            this.sprite = espejo(jugador.imgBala);
        cantidadBalas++;
        idBala = cantidadBalas;
        this.jugador = jugador;

//        radio
//        daño
//        fuerza

        Vector2 centroJugador;
        switch (jugador.tipoBala) {
            case "uzi":
                sonidoCanon = juego.manager.get("sonidos/sonidoCanon.mp3", Sound.class);
                juego.botonPulsado(sonidoCanon);
                radio = 0.15f;
                daño = 2;
                fuerza = 0.75f;
                offsetX = 0.8f;
                offsetY = 0.2f;
                centroJugador = new Vector2(jugador.getX() + jugador.tamañoX * juego.PIXEL_METRO_X, jugador.getY() + jugador.tamañoY * juego.PIXEL_METRO_Y);
                posicion.x = centroJugador.x / juego.PIXEL_METRO_X - 2 + jugador.tamañoX * (float) Math.cos(angulo) * 1.5f;
                posicion.y = centroJugador.y / juego.PIXEL_METRO_Y - 0.5f + jugador.tamañoY * (float) Math.sin(angulo) * 1.3f;
                break;
            case "pistola":
                sonidoCanon = juego.manager.get("sonidos/sonidoCanon.mp3", Sound.class);
                juego.botonPulsado(sonidoCanon);
                radio = 0.15f;
                daño = 3;
                fuerza = 0.75f;
                offsetX = 0.8f;
                offsetY = 0.2f;
                centroJugador = new Vector2(jugador.getX() + jugador.tamañoX * juego.PIXEL_METRO_X, jugador.getY() + jugador.tamañoY * juego.PIXEL_METRO_Y);
                posicion.x = centroJugador.x / juego.PIXEL_METRO_X - 2 + jugador.tamañoX * (float) Math.cos(angulo) * 1.5f;
                posicion.y = centroJugador.y / juego.PIXEL_METRO_Y - 0.5f + jugador.tamañoY * (float) Math.sin(angulo) * 1.3f;
                break;
            case "canon":
                sonidoCanon = juego.manager.get("sonidos/sonidoCanon.mp3", Sound.class);
                juego.botonPulsado(null);
                radio = 0.25f;
                daño = 10;
                fuerza = 1;
                posicion.x -= 1.5f;
                break;
        }

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(def);

        CircleShape forma = new CircleShape();
        forma.setRadius(radio);
        fixture = body.createFixture(forma, 1);
        fixture.setUserData(idBala);
        forma.dispose();

    }

    private Sprite espejo(Texture imagen) {
        Sprite sprite = new Sprite(imagen);
        sprite.flip(true, false);
        return sprite;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + radio * 7 * juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y + radio * juego.PIXEL_METRO_Y);
        batch.draw(sprite, getX() + juego.PIXEL_METRO_X * offsetX, getY() + juego.PIXEL_METRO_Y * offsetY, juego.PIXEL_METRO_X * 2 * radio, juego.PIXEL_METRO_Y * 2 * radio);
    }

    @Override
    public void act(float delta) {
        if (!impacto) {
            if (cont < 1) {
                Vector2 posicionCuerpo = body.getPosition();
                double x = fuerza * Math.cos(angulo);
                double y = fuerza * Math.sin(angulo);
                body.applyLinearImpulse((float) x, (float) y, posicionCuerpo.x, posicionCuerpo.y, true);
                cont++;
            }
        }
    }

    public void elimina() {
        if (jugador.tipoBala.equals("canon"))
            juego.botonPulsado(sonidoCanon);
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
