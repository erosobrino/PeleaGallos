package com.peleadegallos;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Jugador extends Actor {

    public Texture imgBala;
    public Texture imgArma;

    enum Movimiento {
        nada(0),
        adelante(1),
        atras(2),
        saltaAdelante(3),
        saltaAtras(4);

        Movimiento(int i) {
        }
    }

    public Texture[] frameParadoAv;//avanza
    private Texture[] frameMovimientoAv;//avanza
    private Texture[] frameSaltaAV;//avanza
    private Sprite[] frameParadoRe;//retrocede
    private Sprite[] frameMovimientoRe;//retrocede
    private Sprite[] frameSaltaRe;//retrocede
    private Texture[] frameMuertoAv;
    private Sprite[] frameMuertoRe;

    World mundo;

    Body body;

    Fixture fixture;

    boolean vivo = true;

    private int vida = 100;

    JuegoPrincipal juego;

    boolean saltando;
    boolean tocaSuelo;

    boolean avanza = true;
    boolean turno;
    Movimiento movimiento;
    int tiempoFrameMuerto = 100;
    private long tiempoF = System.currentTimeMillis();
    int indice = 1;
    float tamañoX = 0.5f, tamañoY = 0.5f;
    int contFrameMuerto = 0;
    Sound sonidoSalto;
    Music sonidoAndar;

    float aspectoArma;

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        if (vida <= 0) {
            vida = 0;
            vivo = false;
        }
        this.vida = vida;
    }

    PantallaJuego1 pantallaJuego;
    TextureRegion regionArma;
    TextureRegion regionArmaRe;//cuando mira hacia atras
    float angulo;
    String tipoBala;

    public Jugador(World mundo, Texture[] texturaParado, Texture[] texturaMovimiento, Texture[] texturaSaltando, Texture[] muerto, Vector2 posicion, Texture imgBala, Texture imgArma, JuegoPrincipal juego, boolean turno, Movimiento movimiento, PantallaJuego1 pantallaJuego) {
        this.mundo = mundo;
        this.frameParadoAv = texturaParado;
        this.frameMovimientoAv = texturaMovimiento;
        this.frameSaltaAV = texturaSaltando;
        this.frameMuertoAv = muerto;
        this.juego = juego;
        this.turno = turno;
        this.movimiento = movimiento;
        this.imgBala = imgBala;
        this.imgArma = imgArma;
        this.pantallaJuego = pantallaJuego;
        this.angulo = 45;

        if (imgArma != null) {
            aspectoArma = imgArma.getWidth() / imgArma.getHeight();
            regionArma = new TextureRegion(imgArma); //nesecario para poder rotar arma
            regionArmaRe = new TextureRegion(espejo(imgArma));
            tipoBala = "pistola";
        } else {
            tipoBala = "canon";
        }

        sonidoSalto = juego.manager.get("sonidos/sonidoSalto.mp3", Sound.class);
        sonidoAndar = juego.manager.get("sonidos/sonidoAndar.mp3", Music.class);

        this.frameParadoRe = new Sprite[frameParadoAv.length];
        for (int i = 0; i < frameParadoAv.length; i++) {//lo invierte
            frameParadoRe[i] = espejo(frameParadoAv[i]);
        }
        this.frameMovimientoRe = new Sprite[frameMovimientoAv.length];
        for (int i = 0; i < frameMovimientoAv.length; i++) {//lo invierte
            frameMovimientoRe[i] = espejo(frameMovimientoAv[i]);
        }

        this.frameSaltaRe = new Sprite[frameSaltaAV.length];
        for (int i = 0; i < frameSaltaAV.length; i++) {
            frameSaltaRe[i] = espejo(frameSaltaAV[i]);
        }

        this.frameMuertoRe = new Sprite[frameMuertoAv.length];
        for (int i = 0; i < frameMuertoAv.length; i++) {
            frameMuertoRe[i] = espejo(frameMuertoAv[i]);
        }

        BodyDef def = new BodyDef();
        def.position.set(posicion.x - 1.5f, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        body = mundo.createBody(def);

//        PolygonShape forma = new PolygonShape();
//        forma.setAsBox(tamañoX, tamañoY);
//        fixture = body.createFixture(forma, 3);
//        forma.dispose();
        CircleShape forma = new CircleShape();
        forma.setRadius(tamañoY);
        fixture = body.createFixture(forma, 3);
        fixture.setFriction(0.5f);
        fixture.setUserData("jugador");
        forma.dispose();
    }

    private Sprite espejo(Texture imagen) {
        Sprite sprite = new Sprite(imagen);
        sprite.flip(true, false);
        return sprite;
    }

    public void cambiaFrame() {
        indice++;
        if (indice >= 10)
            indice = 1;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + 1.5f * juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y);
        if (vivo) {
            switch (movimiento) {
                case nada:
                    if (avanza) {
                        if (saltando) {
                            int indiceAux = indice + 1;
                            if (indiceAux > 7) {
                                indiceAux = 7;
                            }
                            batch.draw(frameSaltaAV[indiceAux], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        } else {
                            batch.draw(frameParadoAv[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (tocaSuelo) {
                            tocaSuelo = false;
                            batch.draw(frameSaltaAV[frameSaltaAV.length - 1], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (regionArma != null)
                            batch.draw(regionArma, getX() + juego.PIXEL_METRO_X / 2, getY() + juego.PIXEL_METRO_Y / 3 * 1.3f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 45);
                    } else {
                        if (saltando) {
                            int indiceAux = indice + 1;
                            if (indiceAux > 7) {
                                indiceAux = 7;
                            }
                            batch.draw(frameSaltaRe[indiceAux], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        } else {
                            batch.draw(frameParadoRe[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (tocaSuelo) {
                            tocaSuelo = false;
                            batch.draw(frameSaltaAV[frameSaltaAV.length - 1], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (regionArmaRe != null)
                            batch.draw(regionArmaRe, getX(), getY() + juego.PIXEL_METRO_Y * 0.15f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                    }
                    break;
                case atras:
                    batch.draw(frameMovimientoRe[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY * 1.07f);
                    if (regionArmaRe != null)
                        batch.draw(regionArmaRe, getX(), getY() + juego.PIXEL_METRO_Y * 0.15f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                    break;
                case adelante:
                    batch.draw(frameMovimientoAv[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                    if (regionArma != null)
                        batch.draw(regionArma, getX() + juego.PIXEL_METRO_X / 2, getY() + juego.PIXEL_METRO_Y / 3 * 1.3f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 45);
                    break;
                case saltaAdelante:
                    batch.draw(frameSaltaAV[0], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX * 1.1f, juego.PIXEL_METRO_Y * 2 * tamañoY);
                    if (regionArma != null)
                        batch.draw(regionArma, getX() + juego.PIXEL_METRO_X / 2, getY() + juego.PIXEL_METRO_Y / 3 * 1.3f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 45);
                    break;
                case saltaAtras:
                    batch.draw(frameSaltaRe[0], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                    if (regionArmaRe != null)
                        batch.draw(regionArmaRe, getX(), getY() + juego.PIXEL_METRO_Y * 0.15f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                    break;
            }
        } else {
            if ((System.currentTimeMillis() - tiempoF) > tiempoFrameMuerto) {
                if (contFrameMuerto < frameMuertoAv.length - 1)
                    contFrameMuerto++;
                tiempoF = System.currentTimeMillis();
            }
            if (avanza)
                batch.draw(frameMuertoAv[contFrameMuerto], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX * 1.2f, juego.PIXEL_METRO_Y * 2 * tamañoY);
            else
                batch.draw(frameMuertoRe[contFrameMuerto], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX * 1.2f, juego.PIXEL_METRO_Y * 2 * tamañoY);
        }
    }

    @Override
    public void act(float delta) {
        if (turno && vivo && !saltando) {
            mundo.clearForces();
            Vector2 posicionCuerpo = body.getPosition();
            switch (movimiento) {
                case nada:
                    break;
                case atras:
                    avanza = false;
                    body.applyLinearImpulse(-0.75f, 0, posicionCuerpo.x, posicionCuerpo.y, true);
                    break;
                case adelante:
                    avanza = true;
                    body.applyLinearImpulse(0.75f, 0, posicionCuerpo.x, posicionCuerpo.y, true);
                    break;
                case saltaAtras:
                    avanza = false;
                    body.applyLinearImpulse(-5, 10, posicionCuerpo.x, posicionCuerpo.y, true);
                    saltando = true;
                    break;
                case saltaAdelante:
                    avanza = true;
                    body.applyLinearImpulse(5, 10, posicionCuerpo.x, posicionCuerpo.y, true);
                    saltando = true;
                    break;
            }
        }
    }

    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
