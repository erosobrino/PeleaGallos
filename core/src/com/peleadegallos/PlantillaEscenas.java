package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Random;

/**
 * Plantilla para todas las escenas, en esta se crean las nubes,
 */
public class PlantillaEscenas implements Screen {

    /**
     * La pantalla de juego principal
     */
    public JuegoPrincipal juego;
    /**
     * El Ancho pantalla.
     */
    int anchoPantalla = Gdx.graphics.getWidth();
    /**
     * El Alto pantalla.
     */
    int altoPantalla = Gdx.graphics.getHeight();
    /**
     * La Fuente, es la misma que la de la skin
     */
    BitmapFont fuente;
    /**
     * La Musica.
     */
    Music musica;
    /**
     * El Fondo.
     */
    Image fondo;
    /**
     * El Sonido click.
     */
    Sound sonidoClick;
    /**
     * El boton home
     */
    Image home;
    /**
     * La skin utilizada para los botones y el resto de iu
     */
    Skin skin;
    /**
     * Coleccion con las nubes del escenario
     */
    ArrayList<Nube> nubes;
    /**
     * Las texturas de las Nubes
     */
    ArrayList<Texture> nubesTextura;
    /**
     * El Random para crear las nubes
     */
    Random rand;
    /**
     * El Escenario.
     */
    Stage escenario;

    //Escalados usados para la fuente y que se adapten a los distintos tamños de pantallas
    /**
     * El Escalado 03.
     */
    float escalado03 = anchoPantalla / 6400f;
    /**
     * El Escalado 075.
     */
    float escalado075 = anchoPantalla / 2560f;
    /**
     * El Escalado 04.
     */
    float escalado04 = anchoPantalla / 4800f;
    /**
     * El Escalado 035.
     */
    float escalado035 = anchoPantalla / 5486f;
    /**
     * El Escalado 025.
     */
    float escalado025 = anchoPantalla / 7680f;
    /**
     * El Escalado 05.
     */
    float escalado05 = anchoPantalla / 3840f;

    /**
     * Inicializa la plantilla para las pantallas, asi tenemos todos estos elemento en todas las pantallas
     *
     * @param juego the juego
     */
    public PlantillaEscenas(final JuegoPrincipal juego) {
        this.juego = juego;

        rand = new Random();
        escenario = new Stage(new FitViewport(anchoPantalla,altoPantalla));

        fondo = new Image(juego.manager.get("suelo.png", Texture.class));     //Coge imagen del assetmanager
        fondo.setSize(anchoPantalla, altoPantalla);
        fondo.setPosition(0, 0);

        fuente = juego.manager.get("skin/fuente200.fnt", BitmapFont.class);
        fuente.setColor(Color.BLACK);
        musica = juego.manager.get("Music.wav", Music.class);
        musica.setLooping(true);
        musica.setVolume(0.5f);

        sonidoClick = juego.manager.get("sonidos/sonidoClick.mp3", Sound.class);

        home = new Image(juego.manager.get("iconos/home.png", Texture.class));
        home.setSize(altoPantalla / 7, altoPantalla / 7);
        home.setPosition(5, altoPantalla - altoPantalla / 7);
        home.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.botonPulsado(sonidoClick);
                juego.setScreen(juego.menuInicio);
                return true;
            }
        });

        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));         //Skin para botones y fuente (creada con hierro v5)


        nubes = new ArrayList<Nube>();
        nubesTextura = new ArrayList<Texture>();

        anadeNubes(1);
    }

    /**
     * Añade 5 nubes mas por cada vez que se suma uno a cantidad
     *
     * @param cantidad la cantida de veces que se añaden 5 nubes
     */
    private void anadeNubes(int cantidad) {
        for (int j = 0; j < cantidad; j++) {
            nubesTextura.add(juego.manager.get("nubes/nube1.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG2.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG14.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG18.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG27.png", Texture.class));
        }
    }

    /**
     * Se ejecuta al mostrar la pantalla, enciende la musica y añade las nubes al escenario
     */
    @Override
    public void show() {
        if (juego.musicaEncendida)
            musica.play();

        for (int i = 0; i < nubesTextura.size(); i++) {
            nubes.add(new Nube(altoPantalla, anchoPantalla, nubesTextura.get(i), rand));
            escenario.addActor(nubes.get(i));
        }

    }

    /**
     * Rendereiza la pantalla, pone el fondo azul y vacia la memoria de video
     * En caso de que estea el modo desarrollo activado y se tape el sensor de luz muestra los
     * limites de los actores de box2d y los del escenario
     *
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    //Vacia memoria video grafica

        //Utiliza una interfaz para poder acceder a los sensores
        if (juego.adaptadorCodigoAndroid.getCurrentLux() < 10 && juego.modoDesarrollo) {
            juego.debug = true;
            escenario.setDebugAll(juego.debug);
        } else {
            juego.debug = false;
            escenario.setDebugAll(juego.debug);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * Se ejecuta al desaparecer la pantalla, quita las nubes del escenario
     */
    @Override
    public void hide() {
        for (Nube nube : nubes) {
            nube.remove();
        }

    }

    @Override
    public void dispose() {

    }
}
