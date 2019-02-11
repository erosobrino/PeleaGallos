package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlantillaEscenas implements Screen {

    public JuegoPrincipal juego;
    int anchoPantalla = Gdx.graphics.getWidth();
    int altoPantalla = Gdx.graphics.getHeight();
    BitmapFont fuente;
    Music musica;

    public PlantillaEscenas(JuegoPrincipal juego) {
        this.juego = juego;

        fuente = juego.manager.get("skin/fuente200.fnt", BitmapFont.class);
        fuente.setColor(Color.BLACK);
        musica = juego.manager.get("Music.wav", Music.class);
        musica.setLooping(true);
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
