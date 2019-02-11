package com.peleadegallos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JuegoPrincipal extends Game {
    MenuInicio menuInicio;
    Opciones opciones;
    AcercaDe info;

    AssetManager manager;
    public boolean musicaEncendida = true;
    public boolean vibracionEncendida = true;

    @Override
    public void create() {
        manager = new AssetManager();                         //Carga las imagenes y audio
        manager.load("suelo.png", Texture.class);    //Imagen del suelo

        manager.load("nubes/nube1.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG2.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG14.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG18.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG27.png", Texture.class);    //Imagen nube
        manager.load("Music.wav", Music.class);            //Musica
        manager.load("iconos/home.png", Texture.class);
        manager.load("iconos/bell.png", Texture.class);
        manager.load("iconos/bell-slash.png", Texture.class);
        manager.load("iconos/info-circle.png", Texture.class);
        manager.load("iconos/volume-mute.png", Texture.class);
        manager.load("iconos/volume-up.png", Texture.class);
        manager.load("skin/fuente200.fnt", BitmapFont.class);    //Fuente
        manager.finishLoading();                             //Espera a que acabe de cargar

        menuInicio = new MenuInicio(this);
        opciones = new Opciones(this);
        info = new AcercaDe(this);

        setScreen(menuInicio);                             //Cambia al menu de inicio
    }
}
