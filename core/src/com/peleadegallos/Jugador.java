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
 * El actor para los jugadores
 */
public class Jugador extends Actor {

    /**
     * La imagen que tendran sus balas
     */
    public Texture imgBala;
    /**
     * La imagen que tendra su arma
     */
    public Texture imgArma;

    /**
     * Enumerado con sus posible movimientos
     */
    enum Movimiento {
        /**
         * Nada.
         */
        nada(0),
        /**
         * Adelante.
         */
        adelante(1),
        /**
         * Atras.
         */
        atras(2),
        /**
         * Salta adelante.
         */
        saltaAdelante(3),
        /**
         * Salta atras.
         */
        saltaAtras(4);

        Movimiento(int i) {
        }
    }

    /**
     * Los frames que tiene dependiendo de lo que hace
     */
    public Texture[] frameParadoAv;//avanza y parado
    private Texture[] frameMovimientoAv;//avanza y se mueve
    private Texture[] frameSaltaAV;//avanza y salta
    private Sprite[] frameParadoRe;//retrocede y parado
    private Sprite[] frameMovimientoRe;//retrocede y se mueve
    private Sprite[] frameSaltaRe;//retrocede y salta
    private Texture[] frameMuertoAv;//muerto avanza
    private Sprite[] frameMuertoRe;//muerto retrocede

    /**
     * El Mundo.
     */
    World mundo;

    /**
     * El cuerpo del jugador
     */
    Body body;

    /**
     * La forma de su cuerpo
     */
    Fixture fixture;

    /**
     * Si esta Vivo.
     */
    boolean vivo = true;

    /**
     * Su vida
     */
    private int vida;

    /**
     * El Juego.
     */
    JuegoPrincipal juego;

    /**
     * Si esta Saltando.
     */
    boolean saltando;
    /**
     * Si Toca el suelo u otro jugador
     */
    boolean tocaSuelo;

    /**
     * Si Avanza.
     */
    boolean avanza = true;
    /**
     * Si tiene el Turno.
     */
    boolean turno;
    /**
     * Su Movimiento.
     */
    Movimiento movimiento;
    /**
     * Cada cuanto cambia el frame cuando muere
     */
    int tiempoFrameMuerto = 100;

    /**
     * El tiempo para cambiar el frame al morir
     */
    private long tiempoF = System.currentTimeMillis();
    /**
     * El Indice del frame.
     */
    int indice = 0;
    /**
     * El Tamaño x.
     */
    float tamañoX = 0.5f,
    /**
     * El Tamaño y.
     */
    tamañoY = 0.5f;
    /**
     * El contador para los frames al morir
     */
    int contFrameMuerto = 0;
    /**
     * El Sonido salto.
     */
    Sound sonidoSalto;
    /**
     * El Sonido andar.
     */
    Music sonidoAndar;

    /**
     * Las proporciones que tiene el arma
     */
    float aspectoArma;

    /**
     * Indice para otro tipo de casos
     */
    int indiceAux;

    /**
     * Devuelve vida.
     *
     * @return la vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece vida esta debe ser o igual a 0
     *
     * @param vida la vida
     */
    public void setVida(int vida) {
        if (vida <= 0) {
            vida = 0;
            vivo = false;
        }
        this.vida = vida;
    }

    /**
     * La region del arma a dibujar
     */
    TextureRegion regionArma;
    /**
     * La region del arma a dibujar /cuando mira hacia atras
     */
    TextureRegion regionArmaRe;
    /**
     * El Angulo.
     */
    float angulo;
    /**
     * El tipo de bala
     */
    String tipoBala;
    /**
     * La cantiad de balas que tiene por cada cargador
     */
    int cantidadBalas;
    /**
     * Las balas que le quedan de ese cargador
     */
    int balasRestantes;

    /**
     * El Tiempo desde el ultimo frame salto.
     */
    long tiempoFrameSalto;

    /**
     * El Nombre.
     */
    String nombre,
    /**
     * El Arma.
     */
    arma;

    /**
     * La fuerza y con la que salta, dependiendo de donde esta el jugador se cambia esta propiedad
     */
    int fuerzaY = 10;
    /**
     * La velocidad
     */
    int velocidad;

    /**
     * Inicializa el jugador
     *
     * @param mundo             el mundo
     * @param texturaParado     la textura parado
     * @param texturaMovimiento la textura movimiento
     * @param texturaSaltando   la textura saltando
     * @param muerto            si esta muerto
     * @param posicion          la posicion
     * @param imgBala           la imagen bala
     * @param imgArma           la imagen arma
     * @param juego             el juego
     * @param turno             el turno
     * @param movimiento        el movimiento
     * @param tipoBala          el tipo de bala
     * @param cantidadBalas     la cantidad de balas
     * @param velocidad         la velocidad
     */
    public Jugador(World mundo, Texture[] texturaParado, Texture[] texturaMovimiento, Texture[] texturaSaltando, Texture[] muerto, Vector2 posicion, Texture imgBala, Texture imgArma, JuegoPrincipal juego, boolean turno, Movimiento movimiento, String tipoBala, int cantidadBalas, int velocidad) {
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
        this.angulo = 45;
        this.tipoBala = tipoBala;
        this.cantidadBalas = cantidadBalas;
        this.balasRestantes = cantidadBalas;
        this.velocidad=velocidad;

        if (imgArma != null) {
            aspectoArma = imgArma.getWidth() / imgArma.getHeight();
            regionArma = new TextureRegion(imgArma); //nesecario para poder rotar arma
            regionArmaRe = new TextureRegion(juego.espejo(imgArma));
        }

        sonidoSalto = juego.manager.get("sonidos/sonidoSalto.mp3", Sound.class);
        sonidoAndar = juego.manager.get("sonidos/sonidoAndar.mp3", Music.class);

        //Roto las imagenes para que vaya en la otra direccion
        this.frameParadoRe = new Sprite[frameParadoAv.length];
        for (int i = 0; i < frameParadoAv.length; i++) {//lo invierte
            frameParadoRe[i] = juego.espejo(frameParadoAv[i]);
        }
        this.frameMovimientoRe = new Sprite[frameMovimientoAv.length];
        for (int i = 0; i < frameMovimientoAv.length; i++) {//lo invierte
            frameMovimientoRe[i] = juego.espejo(frameMovimientoAv[i]);
        }

        this.frameSaltaRe = new Sprite[frameSaltaAV.length];
        for (int i = 0; i < frameSaltaAV.length; i++) {
            frameSaltaRe[i] = juego.espejo(frameSaltaAV[i]);
        }

        this.frameMuertoRe = new Sprite[frameMuertoAv.length];
        for (int i = 0; i < frameMuertoAv.length; i++) {
            frameMuertoRe[i] = juego.espejo(frameMuertoAv[i]);
        }

        BodyDef def = new BodyDef();
        def.position.set(posicion.x - 1.5f, posicion.y);
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        body = mundo.createBody(def);

        CircleShape forma = new CircleShape();
        forma.setRadius(tamañoY);
        fixture = body.createFixture(forma, 3);
        fixture.setFriction(0.5f);
        fixture.setUserData("jugador");
        forma.dispose();
    }

    /**
     * Cambia frame.
     */
    public void cambiaFrame() {
        indice++;
        if (indice >= 10)
            indice = 0;
    }

    /**
     * Cambia el frame cuando esta saltando para que parezca continuo
     */
    private void cambiaFrameAux() {//frame salto que cambia
        if ((System.currentTimeMillis() - tiempoFrameSalto) > 150) {
            indiceAux++;
            if (indiceAux > 7) {
                indiceAux = 7;
            }
            tiempoFrameSalto = System.currentTimeMillis();
        }
    }

    /**
     * Cambia la posicion con el offset que tiene y dependiendo de su movimineto dibuja sus frames
     * y los frames de su arma, dependiendo del angulo con el que apunto esta se coloca en una posicion u otra
     *
     * @param batch       con el que se dibujan los frames
     * @param parentAlpha el alpha del padre
     */
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

    /**
     * Mueve el cuerpo del jugador en fucnion de su posicion, esto se hace aplicando fuerzas sobre su centro,
     * si se esta moviendo reduce su velocidad y salta, ded esta forma no salta tan lejos, cada vez que
     * se cambia el movimiento de nada a otro caambia el angulo que tiene el jugador al predefinido
     *
     * @param delta el tiempo desde la ultima ejecucion
     */
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
                    body.applyLinearImpulse(-0.75f/2*velocidad, 0, posicionCuerpo.x, posicionCuerpo.y, true);
                    break;
                case adelante:
                    avanza = true;
                    angulo = 45;
                    body.applyLinearImpulse(0.75f/2*velocidad, 0, posicionCuerpo.x, posicionCuerpo.y, true);
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
     * Destruye las fixtures y los cuerpos del mundo para no cargar la memoria,
     * si la bala es de canon vibra y suena el sonido de disparo
     */
    public void elimina() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
