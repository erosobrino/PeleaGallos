package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

class Opciones extends PlantillaEscenas {

    Image fondo;
    Stage escenario;
    Image home;
    Image imgVolumen;
    Image imgVibrar;
    SpriteBatch batch;
    int posX;

    public Opciones(JuegoPrincipal juego) {
        super(juego);
        posX = anchoPantalla / 7;
    }

    @Override
    public void show() {
        super.show();
        fuente.getData().setScale(0.75f);
        escenario = new Stage();

        fondo = new Image(juego.manager.get("suelo.png", Texture.class));
        fondo.setSize(anchoPantalla, altoPantalla);
        fondo.setPosition(0, 0);

        home = new Image(juego.manager.get("iconos/home.png", Texture.class));
        home.setSize(altoPantalla / 7, altoPantalla / 7);
        home.setPosition(5, altoPantalla - altoPantalla / 7);
        home.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.setScreen(juego.menuInicio);
                return true;
            }
        });

        if (juego.musicaEncendida) {
            imgVolumen = new Image(juego.manager.get("iconos/volume-up.png", Texture.class));
            musica.play();
        } else {
            imgVolumen = new Image(juego.manager.get("iconos/volume-mute.png", Texture.class));
            musica.pause();
        }
        imgVolumen.setSize(altoPantalla / 7, altoPantalla / 7);
        imgVolumen.setPosition(anchoPantalla / 7 * 4, altoPantalla / 10 * 7.5f);
        imgVolumen.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.musicaEncendida = !juego.musicaEncendida;
                show();
                return true;
            }
        });

        if (juego.vibracionEncendida) {
            imgVibrar = new Image(juego.manager.get("iconos/bell.png", Texture.class));
            imgVibrar.setSize(altoPantalla / 7, altoPantalla / 7);
            imgVibrar.setPosition(anchoPantalla / 7 * 4, altoPantalla / 10 * 5.5f);
        } else {
            imgVibrar = new Image(juego.manager.get("iconos/bell-slash.png", Texture.class));
            imgVibrar.setSize(altoPantalla / 7 * 1.25f, altoPantalla / 7);
            imgVibrar.setPosition(anchoPantalla / 7 * 4 * 0.95f, altoPantalla / 10 * 5.5f);
        }
        imgVibrar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.vibracionEncendida = !juego.vibracionEncendida;
                System.out.println("sadasdffasdf");
                show();
                return true;
            }
        });

        batch = new SpriteBatch();

        escenario.addActor(fondo);
        escenario.addActor(home);
        escenario.addActor(imgVibrar);
        escenario.addActor(imgVolumen);

        Gdx.input.setInputProcessor(escenario);  //Pone como listener al escenario, asi funcionan botones
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        batch.begin();
        fuente.draw(batch, "Volumen", posX, altoPantalla / 10 * 9);
        fuente.draw(batch, "Vibrar", posX, altoPantalla / 10 * 7);
        batch.end();
    }

    @Override
    public void hide() {
        fondo.remove();
        home.remove();
        imgVolumen.remove();
        imgVibrar.remove();
        escenario.dispose();
//        musica.pause();
        Gdx.input.setInputProcessor(null);
    }
}
