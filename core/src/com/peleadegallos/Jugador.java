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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The type Jugador.
 */
public class Jugador extends Actor {

    /**
     * The Img bala.
     */
    public Texture imgBala;
    /**
     * The Img arma.
     */
    public Texture imgArma;

    /**
     * The enum Movimiento.
     */
    enum Movimiento {
        /**
         * Nada movimiento.
         */
        nada(0),
        /**
         * Adelante movimiento.
         */
        adelante(1),
        /**
         * Atras movimiento.
         */
        atras(2),
        /**
         * Salta adelante movimiento.
         */
        saltaAdelante(3),
        /**
         * Salta atras movimiento.
         */
        saltaAtras(4);

        Movimiento(int i) {
        }
    }

    /**
     * The Frame parado av.
     */
    public Texture[] frameParadoAv;//avanza
    private Texture[] frameMovimientoAv;//avanza
    private Texture[] frameSaltaAV;//avanza
    private Sprite[] frameParadoRe;//retrocede
    private Sprite[] frameMovimientoRe;//retrocede
    private Sprite[] frameSaltaRe;//retrocede
    private Texture[] frameMuertoAv;
    private Sprite[] frameMuertoRe;

    /**
     * The Mundo.
     */
    World mundo;

    /**
     * The Body.
     */
    Body body;

    /**
     * The Fixture.
     */
    Fixture fixture;

    /**
     * The Vivo.
     */
    boolean vivo = true;

    private int vida = 100;

    /**
     * The Juego.
     */
    JuegoPrincipal juego;

    /**
     * The Saltando.
     */
    boolean saltando;
    /**
     * The Toca suelo.
     */
    boolean tocaSuelo;

    /**
     * The Avanza.
     */
    boolean avanza = true;
    /**
     * The Turno.
     */
    boolean turno;
    /**
     * The Movimiento.
     */
    Movimiento movimiento;
    /**
     * The Tiempo frame muerto.
     */
    int tiempoFrameMuerto = 100;
    private long tiempoF = System.currentTimeMillis();
    /**
     * The Indice.
     */
    int indice = 1;
    /**
     * The Tamaño x.
     */
    float tamañoX = 0.5f, /**
     * The Tamaño y.
     */
    tamañoY = 0.5f;
    /**
     * The Cont frame muerto.
     */
    int contFrameMuerto = 0;
    /**
     * The Sonido salto.
     */
    Sound sonidoSalto;
    /**
     * The Sonido andar.
     */
    Music sonidoAndar;

    /**
     * The Aspecto arma.
     */
    float aspectoArma;

    /**
     * The Indice aux.
     */
    int indiceAux;

    /**
     * Gets vida.
     *
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Sets vida.
     *
     * @param vida the vida
     */
    public void setVida(int vida) {
        if (vida <= 0) {
            vida = 0;
            vivo = false;
        }
        this.vida = vida;
    }

    /**
     * The Pantalla juego.
     */
    PantallaJuego pantallaJuego;
    /**
     * The Region arma.
     */
    TextureRegion regionArma;
    /**
     * The Region arma re.
     */
    TextureRegion regionArmaRe;//cuando mira hacia atras
    /**
     * The Angulo.
     */
    float angulo;
    /**
     * The Tipo bala.
     */
    String tipoBala;
    /**
     * The Cantidad balas.
     */
    int cantidadBalas;
    /**
     * The Balas restantes.
     */
    int balasRestantes;

    /**
     * The Tiempo frame salto.
     */
    long tiempoFrameSalto;

    /**
     * The Nombre.
     */
    String nombre, /**
     * The Arma.
     */
    arma;

    int fuerzaY = 10;

    /**
     * Instantiates a new Jugador.
     *
     * @param mundo             the mundo
     * @param texturaParado     the textura parado
     * @param texturaMovimiento the textura movimiento
     * @param texturaSaltando   the textura saltando
     * @param muerto            the muerto
     * @param posicion          the posicion
     * @param imgBala           the img bala
     * @param imgArma           the img arma
     * @param juego             the juego
     * @param turno             the turno
     * @param movimiento        the movimiento
     * @param pantallaJuego     the pantalla juego
     * @param tipoBala          the tipo bala
     * @param cantidadBalas     the cantidad balas
     */
    public Jugador(World mundo, Texture[] texturaParado, Texture[] texturaMovimiento, Texture[] texturaSaltando, Texture[] muerto, Vector2 posicion, Texture imgBala, Texture imgArma, JuegoPrincipal juego, boolean turno, Movimiento movimiento, PantallaJuego pantallaJuego, String tipoBala, int cantidadBalas) {
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
        this.tipoBala = tipoBala;
        this.cantidadBalas = cantidadBalas;
        this.balasRestantes = cantidadBalas;

        if (imgArma != null) {
            aspectoArma = imgArma.getWidth() / imgArma.getHeight();
            regionArma = new TextureRegion(imgArma); //nesecario para poder rotar arma
            regionArmaRe = new TextureRegion(espejo(imgArma));
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

    /**
     * Cambia frame.
     */
    public void cambiaFrame() {
        indice++;
        if (indice >= 10)
            indice = 1;
    }

    private void cambiaFrameAux() {//frame salto que cambia
        if ((System.currentTimeMillis() - tiempoFrameSalto) > 150) {
            indiceAux++;
            if (indiceAux > 7) {
                indiceAux = 7;
            }
            tiempoFrameSalto = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + 1.5f * juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y);
        if (vivo) {
            switch (movimiento) {
                case nada:
                    if (avanza) {
                        if (saltando) {
                            cambiaFrameAux();
                            batch.draw(frameSaltaAV[indiceAux], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        } else {
                            batch.draw(frameParadoAv[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (tocaSuelo) {
                            tocaSuelo = false;
                            batch.draw(frameSaltaAV[frameSaltaAV.length - 1], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (regionArma != null)
                            batch.draw(regionArma, getX() + juego.PIXEL_METRO_X / 2, getY() + juego.PIXEL_METRO_Y / 3 * 1.2f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 45);
                    } else {
                        if (saltando) {
                            cambiaFrameAux();
                            batch.draw(frameSaltaRe[indiceAux], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        } else {
                            batch.draw(frameParadoRe[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (tocaSuelo) {
                            tocaSuelo = false;
                            batch.draw(frameSaltaAV[frameSaltaAV.length - 1], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                        }
                        if (regionArmaRe != null) {
                            if (angulo < 181 && angulo > 0)
                                batch.draw(regionArmaRe, getX() + juego.PIXEL_METRO_X / Math.abs(angulo) * angulo / 10, getY() + juego.PIXEL_METRO_Y * 0.15f + (float) (juego.PIXEL_METRO_Y * Math.sin(Math.toRadians(angulo)) * 0.3), 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                            else
                                batch.draw(regionArmaRe, getX() + juego.PIXEL_METRO_X * 0.1f + juego.PIXEL_METRO_X / Math.abs(angulo) * 40, getY() + juego.PIXEL_METRO_Y * 0.15f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                        }
                    }
                    break;
                case atras:
                    batch.draw(frameMovimientoRe[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY * 1.07f);
                    if (regionArmaRe != null) {
                        if (angulo < 181 && angulo > 0)
                            batch.draw(regionArmaRe, getX() + juego.PIXEL_METRO_X / Math.abs(angulo) * angulo / 10, getY() + juego.PIXEL_METRO_Y * 0.15f + (float) (juego.PIXEL_METRO_Y * Math.sin(Math.toRadians(angulo)) * 0.3), 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                        else
                            batch.draw(regionArmaRe, getX() + juego.PIXEL_METRO_X * 0.1f + juego.PIXEL_METRO_X / Math.abs(angulo) * 40, getY() + juego.PIXEL_METRO_Y * 0.15f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                    }
                    break;
                case adelante:
                    batch.draw(frameMovimientoAv[indice], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                    if (regionArma != null)
                        batch.draw(regionArma, getX() + juego.PIXEL_METRO_X / 2, getY() + juego.PIXEL_METRO_Y / 3 * 1.2f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 45);
                    break;
                case saltaAdelante:
                    cambiaFrameAux();
                    batch.draw(frameSaltaAV[indiceAux], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX * 1.1f, juego.PIXEL_METRO_Y * 2 * tamañoY);
                    if (regionArma != null)
                        batch.draw(regionArma, getX() + juego.PIXEL_METRO_X / 2, getY() + juego.PIXEL_METRO_Y / 3 * 1.2f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 45);
                    break;
                case saltaAtras:
                    cambiaFrameAux();
                    batch.draw(frameSaltaRe[indiceAux], getX(), getY(), juego.PIXEL_METRO_X * 2 * tamañoX, juego.PIXEL_METRO_Y * 2 * tamañoY);
                    if (regionArmaRe != null) {
                        if (angulo < 181 && angulo > 0)
                            batch.draw(regionArmaRe, getX() + juego.PIXEL_METRO_X / Math.abs(angulo) * angulo / 10, getY() + juego.PIXEL_METRO_Y * 0.15f + (float) (juego.PIXEL_METRO_Y * Math.sin(Math.toRadians(angulo)) * 0.3), 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                        else
                            batch.draw(regionArmaRe, getX() + juego.PIXEL_METRO_X * 0.1f + juego.PIXEL_METRO_X / Math.abs(angulo) * 40, getY() + juego.PIXEL_METRO_Y * 0.15f, 0, 0, juego.PIXEL_METRO_X / 2, juego.PIXEL_METRO_Y / 2 * aspectoArma, 1, 1, angulo - 135);
                    }
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
            Vector2 posicionCuerpo = body.getPosition();
            mundo.clearForces();
            switch (movimiento) {
                case nada:
                    break;
                case atras:
                    avanza = false;
                    angulo = 135;
                    body.applyLinearImpulse(-0.75f, 0, posicionCuerpo.x, posicionCuerpo.y, true);
                    break;
                case adelante:
                    avanza = true;
                    angulo = 45;
                    body.applyLinearImpulse(0.75f, 0, posicionCuerpo.x, posicionCuerpo.y, true);
                    break;
                case saltaAtras:
                    avanza = false;
                    indiceAux = 0;
                    angulo = 135;
                    tiempoFrameSalto = System.currentTimeMillis();
                    if (body.getLinearVelocity().x > 0) //Si se esta moviento no salta tan lejos
                        body.setLinearVelocity(-1, 0);
                    body.applyLinearImpulse(-5, fuerzaY, posicionCuerpo.x, posicionCuerpo.y, true);
                    saltando = true;
                    break;
                case saltaAdelante:
                    avanza = true;
                    indiceAux = 0;
                    angulo = 45;
                    tiempoFrameSalto = System.currentTimeMillis();
                    if (body.getLinearVelocity().x > 0) //Si se esta moviento no salta tan lejos
                        body.setLinearVelocity(1, 0);
                    body.applyLinearImpulse(5, fuerzaY, posicionCuerpo.x, posicionCuerpo.y, true);
                    saltando = true;
                    break;
            }
        }
    }

    /**
     * Elimina.
     */
    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
