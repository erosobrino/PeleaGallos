package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PlantillaEscenas implements Screen {

    public JuegoPrincipal juego;
    int anchoPantalla = Gdx.graphics.getWidth();
    int altoPantalla = Gdx.graphics.getHeight();
    BitmapFont fuente;
    Music musica;
    Image fondo;
    Sound sonidoClick;

    public PlantillaEscenas(JuegoPrincipal juego) {
        this.juego = juego;

        fondo = new Image(juego.manager.get("suelo.png", Texture.class));     //Coge imagen del assetmanager
        fondo.setSize(anchoPantalla, altoPantalla);
        fondo.setPosition(0, 0);

        fuente = juego.manager.get("skin/fuente200.fnt", BitmapFont.class);
        fuente.setColor(Color.BLACK);
        musica = juego.manager.get("Music.wav", Music.class);
        musica.setLooping(true);
        musica.setVolume(0.75f);

        sonidoClick=juego.manager.get("sonidos/sonidoClick.mp3",Sound.class);
    }

    @Override
    public void show() {
        if (juego.musicaEncendida)
            musica.play();

    }

    public void botonPulsado(Sound sonido){
        if (juego.vibracionEncendida)
            Gdx.input.vibrate(juego.tiempoVibrar);
        if (juego.musicaEncendida){
            sonido.play();
        }
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
