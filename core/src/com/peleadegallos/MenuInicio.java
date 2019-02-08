package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.awt.Font;

public class MenuInicio extends PlantillaEscenas {

    Stage escenario;

    TextButton jugar, opciones, logros, ayuda;

    int alto, ancho;
    Skin skin;
    Image fondo;

//    BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/fuente200.fnt"), false);


    public MenuInicio(JuegoPrincipal juego) {
        super(juego);

        alto = altoPantalla / 5;
        ancho = anchoPantalla / 7;

        escenario = new Stage();
        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));         //Skin para botones y fuente (creada con hierro v5)

        fondo=new Image(juego.manager.get("suelo.png", Texture.class));     //Coge imagen del assetmanager
        fondo.setSize(anchoPantalla,altoPantalla);
        fondo.setPosition(0,0);

        jugar = new TextButton("Jugar", skin);
        jugar.setSize(ancho * 5, alto * 12 / 10);
        jugar.setPosition(ancho, alto * 3);
        jugar.scaleBy(2);
        jugar.getLabel().setPosition(2,2);
        jugar.getLabel().setFontScale(0.75f);

        opciones = new TextButton("Opciones", skin);
        opciones.setSize(ancho*1.25f, alto);
        opciones.setPosition(ancho, alto);
        opciones.getLabel().setFontScale(0.3f);

        logros = new TextButton("Logros", skin);
        logros.setSize(ancho*1.25f, alto);
        logros.setPosition(ancho * 3-(ancho*0.25f)/2, alto);
        logros.getLabel().setFontScale(0.3f);

        ayuda = new TextButton("Ayuda", skin);
        ayuda.setSize(ancho*1.25f, alto);
        ayuda.setPosition(ancho * 5-ancho*0.25f, alto);
        ayuda.getLabel().setFontScale(0.3f);

        escenario.addActor(fondo);
        escenario.addActor(jugar);      //Añado los botones al escenario
        escenario.addActor(opciones);
        escenario.addActor(logros);
        escenario.addActor(ayuda);

        Gdx.input.setInputProcessor(escenario);  //Pone como listener al escenario, ais funcionan botones
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    //Vacia memoria video grafica

        escenario.act();
        escenario.draw();
    }

    @Override
    public void hide() {
        fondo.remove();
        jugar.remove();
        opciones.remove();
        logros.remove();
        ayuda.remove();
        escenario.dispose();

        Gdx.input.setInputProcessor(null);  //Quita como listener al escenario
    }
}
