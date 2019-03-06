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

import java.util.ArrayList;
import java.util.Random;

public class PlantillaEscenas implements Screen {

    public JuegoPrincipal juego;
    int anchoPantalla = Gdx.graphics.getWidth();
    int altoPantalla = Gdx.graphics.getHeight();
    BitmapFont fuente;
    Music musica;
    Image fondo;
    Sound sonidoClick;
    Image home;
    Skin skin;
    ArrayList<Nube> nubes;
    ArrayList<Texture> nubesTextura;
    Random rand;
    Stage escenario;

    float escalado03 = anchoPantalla / 6400f;
    float escalado075 = anchoPantalla / 2560f;
    float escalado04 = anchoPantalla / 4800f;
    float escalado035 = anchoPantalla / 5486f;
    float escalado025 = anchoPantalla / 7680f;
    float escalado05 = anchoPantalla / 3840f;

    public PlantillaEscenas(final JuegoPrincipal juego) {
        this.juego = juego;

        rand = new Random();
        escenario = new Stage();
        escenario.setDebugAll(juego.debug);

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

    //Añade 5 nubes mas por cada vez que se suma uno a cantidad
    private void anadeNubes(int cantidad) {
        for (int j = 0; j < cantidad; j++) {
            nubesTextura.add(juego.manager.get("nubes/nube1.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG2.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG14.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG18.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG27.png", Texture.class));
        }
    }

    @Override
    public void show() {
        if (juego.musicaEncendida)
            musica.play();

        for (int i = 0; i < nubesTextura.size(); i++) {
            nubes.add(new Nube(altoPantalla, anchoPantalla, nubesTextura.get(i), rand));
            escenario.addActor(nubes.get(i));
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    //Vacia memoria video grafica

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
