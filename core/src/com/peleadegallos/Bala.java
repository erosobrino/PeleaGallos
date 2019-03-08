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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Actor bala
 */
public class Bala extends Actor {

    /**
     * Cantidad de balas lanzadas, utilizado a la hora de eliminarlas
     */
    public static int cantidadBalas = 0;
    /**
     * La imagen de la bala
     */
    Sprite sprite;

    /**
     * El mundo al que pertenece
     */
    World mundo;

    /**
     * El cuerpo de la bala
     */
    Body body;

    /**
     * Descripcion del cuerpo de la bala (tamaño, posicion, etc)
     */
    Fixture fixture;

    /**
     * La clase principal, en ella estan algunas constantes y datos necesarios
     */
    JuegoPrincipal juego;

    /**
     * El radio de la bala
     */
    float radio;//Son la mitad del real

    /**
     * Utilizada para eliminar la bala en caso de que impacte en elgun lugar
     */
    boolean impacto = false;

    /**
     * El daño que quita cuando toca a un personaje
     */
    int daño;
    /**
     * Para que solo se le aplique fuerza una vez, en el momento de dispararse
     */
    boolean disparada = false;

    /**
     * La fuerza que se le aplica a la bala
     */
    float fuerza;
    /**
     * El angulo con el que se dispara
     */
    float angulo;
    /**
     * El Id de la bala, se utiliza con cantidaBalas, para utilizarlo debe estar en radianes
     */
    int idBala;
    /**
     * El sonido de disparo
     */
    Sound sonidoCanon;
    /**
     * El offset que tiene la imagen respecto al body en el eje x
     */
    float offsetX,
    /**
     * El offset que tiene la imagen respecto al body en el eje y
     */
    offsetY;
    /**
     * El jugador que dispara esta bala
     */
    Jugador jugador;

    /**
     * Inicializa la bala en la posicion y se dispara al realizar el act
     *
     * @param mundo    el mundo que la crea
     * @param posicion la posicion de la que se dispara
     * @param juego    la pantalla principal
     * @param jugador  el jugador que la dispara
     */
    public Bala(World mundo, Vector2 posicion, JuegoPrincipal juego, Jugador jugador) {
        this.mundo = mundo;
        this.juego = juego;
        this.angulo = (float) Math.toRadians(jugador.angulo);
        if (jugador.avanza)                                 //Dependiendo de si el jugador avanza, la imagen se rota para que se diriga hacia el mismo lado
            this.sprite = new Sprite(jugador.imgBala);
        else
            this.sprite = juego.espejo(jugador.imgBala);
        cantidadBalas++;
        idBala = cantidadBalas;
        this.jugador = jugador;

        //Cada case debe tener como minimo estas propiedades
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
                posicion.x = centroJugador.x / juego.PIXEL_METRO_X - 2 + jugador.tamañoX * (float) Math.cos(angulo) * 1.5f;         //Dependiendo del angulo la bala sale mas arriba o abajo
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
                posicion.x = centroJugador.x / juego.PIXEL_METRO_X - 2 + jugador.tamañoX * (float) Math.cos(angulo) * 1.5f;      //Dependiendo del angulo la bala sale mas arriba o abajo
                posicion.y = centroJugador.y / juego.PIXEL_METRO_Y - 0.5f + jugador.tamañoY * (float) Math.sin(angulo) * 1.3f;
                break;
            case "canon":
                sonidoCanon = juego.manager.get("sonidos/sonidoCanon.mp3", Sound.class);
                juego.botonPulsado(null);
                radio = 0.25f;
                daño = 10;
                fuerza = 1;
                posicion.x -= 1.5f;     //En el caso del canon la bala sale siempre desde la misma altura
                break;
        }

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(def);

        CircleShape forma = new CircleShape();
        forma.setRadius(radio);
        fixture = body.createFixture(forma, 1);
        fixture.setUserData(idBala);                    //Con este vamos cambiando la propiedad impacto cuand esta se choca
        forma.dispose();

    }


    /**
     * Modifica la posicion del actor con la posicion del body de la bala, se multiplica por el offset para que las coordenadas de ambos se vena igual sobre la pantalla
     * Se tiene que cambia la realcion porque uno esta en metros y el otro en pixeles
     *
     * @param batch el batch que se utiliza para dibujar el sprite de la bala
     * @param parentAlpha el alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * juego.PIXEL_METRO_X + radio * 7 * juego.PIXEL_METRO_X, body.getPosition().y * juego.PIXEL_METRO_Y + radio * juego.PIXEL_METRO_Y);
        batch.draw(sprite, getX() + juego.PIXEL_METRO_X * offsetX, getY() + juego.PIXEL_METRO_Y * offsetY, juego.PIXEL_METRO_X * 2 * radio, juego.PIXEL_METRO_Y * 2 * radio);
    }

    /**
     * Esta funcion se ejecuta al hacer escenario.act, en este caso solo se debe ejecutar una vez, en el momento del disparo
     * @param delta el tiempo en ms desde la ultima ejecucion
     */
    @Override
    public void act(float delta) {
        if (!impacto) {
            if (!disparada) {
                Vector2 posicionCuerpo = body.getPosition();
                double x = fuerza * Math.cos(angulo);
                double y = fuerza * Math.sin(angulo);
                body.applyLinearImpulse((float) x, (float) y, posicionCuerpo.x, posicionCuerpo.y, true);
                disparada = true;
            }
        }
    }

    /**
     * Destruye las fixtures y los cuerpos del mundo para no cargar la memoria,
     * si la bala es de canon vibra y suena el sonido de disparo
     */
    public void elimina() {
        if (jugador.tipoBala.equals("canon"))
            juego.botonPulsado(sonidoCanon);
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }
}
