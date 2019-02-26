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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

    public PlantillaEscenas(final JuegoPrincipal juego) {
        this.juego = juego;

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
    }

    @Override
    public void show() {
        if (juego.musicaEncendida)
            musica.play();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    //Vacia memoria video grafica
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

    }

    @Override
    public void dispose() {

    }
}
