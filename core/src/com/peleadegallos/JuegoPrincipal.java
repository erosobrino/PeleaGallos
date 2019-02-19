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
import com.badlogic.gdx.utils.I18NBundle;

public class JuegoPrincipal extends Game {
    MenuInicio menuInicio;
    Opciones opciones;
    AcercaDe info;
    PantallaJuego1 pantallaJuego1;

    I18NBundle idiomas;

    AssetManager manager;
    public boolean musicaEncendida = true;
    public boolean vibracionEncendida = true;
    public int tiempoVibrar = 50;
    public boolean debug = true;
    float PIXEL_METRO_X;  //Escala para box2s
    float PIXEL_METRO_Y;  //Escala para box2s
    float metrosX = 16;
    float metrosY = 9;


    @Override
    public void create() {
        idiomas = I18NBundle.createBundle(Gdx.files.internal("locale/locale"));

        manager = new AssetManager();                         //Carga las imagenes y audio
        manager.load("suelo.png", Texture.class);    //Imagen del suelo
        manager.load("suelo2.png", Texture.class);    //Imagen del suelo
        manager.load("2.png", Texture.class);    //Imagen del suelo

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
        manager.load("iconos/flechaArriba.png", Texture.class);
        manager.load("iconos/flechaDerecha.png", Texture.class);
        manager.load("iconos/flechaIzquierda.png", Texture.class);
        manager.load("iconos/disparo.png", Texture.class);

        manager.load("dino/Idle (1).png", Texture.class);
        manager.load("dino/Idle (2).png", Texture.class);
        manager.load("dino/Idle (3).png", Texture.class);
        manager.load("dino/Idle (4).png", Texture.class);
        manager.load("dino/Idle (5).png", Texture.class);
        manager.load("dino/Idle (6).png", Texture.class);
        manager.load("dino/Idle (7).png", Texture.class);
        manager.load("dino/Idle (8).png", Texture.class);
        manager.load("dino/Idle (9).png", Texture.class);
        manager.load("dino/Idle (10).png", Texture.class);

        manager.load("dino/Walk (1).png", Texture.class);
        manager.load("dino/Walk (2).png", Texture.class);
        manager.load("dino/Walk (3).png", Texture.class);
        manager.load("dino/Walk (4).png", Texture.class);
        manager.load("dino/Walk (5).png", Texture.class);
        manager.load("dino/Walk (6).png", Texture.class);
        manager.load("dino/Walk (7).png", Texture.class);
        manager.load("dino/Walk (8).png", Texture.class);
        manager.load("dino/Walk (9).png", Texture.class);
        manager.load("dino/Walk (10).png", Texture.class);

        manager.load("dino/Jump (1).png", Texture.class);
        manager.load("dino/Jump (2).png", Texture.class);
        manager.load("dino/Jump (3).png", Texture.class);
        manager.load("dino/Jump (4).png", Texture.class);
        manager.load("dino/Jump (5).png", Texture.class);
        manager.load("dino/Jump (6).png", Texture.class);
        manager.load("dino/Jump (7).png", Texture.class);
        manager.load("dino/Jump (8).png", Texture.class);
        manager.load("dino/Jump (9).png", Texture.class);
        manager.load("dino/Jump (10).png", Texture.class);
        manager.load("dino/Jump (11).png", Texture.class);
        manager.load("dino/Jump (12).png", Texture.class);


        manager.load("skin/fuente200.fnt", BitmapFont.class);    //Fuente
        manager.finishLoading();                             //Espera a que acabe de cargar

        menuInicio = new MenuInicio(this);
        opciones = new Opciones(this);
        info = new AcercaDe(this);
        pantallaJuego1 = new PantallaJuego1(this);

        setScreen(menuInicio);                             //Cambia al menu de inicio
    }
}
