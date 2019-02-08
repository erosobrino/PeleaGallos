package com.peleadegallos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JuegoPrincipal extends Game {
    MenuInicio menuInicio;
    AssetManager manager;

    @Override
    public void create() {
        manager = new AssetManager();                         //Carga las imagenes y audio
        manager.load("suelo.png", Texture.class);    //Imagen del suelo
        manager.finishLoading();                             //Espera a que acabe de cargar

        menuInicio = new MenuInicio(this);
        setScreen(menuInicio);                             //Cambia al menu de inicio
    }
}
