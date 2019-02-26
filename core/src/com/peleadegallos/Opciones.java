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

    Stage escenario;
    Image imgVolumen;
    Image imgVibrar;
    SpriteBatch batch;
    int posX;

    public Opciones(final JuegoPrincipal juego) {
        super(juego);
        posX = anchoPantalla / 7;

        escenario = new Stage();
        escenario.setDebugAll(juego.debug);

        actualizaIconos();

        batch = new SpriteBatch();
    }


    private void actualizaIconos() {
        if (imgVibrar != null)
            imgVibrar.remove();
        if (imgVolumen != null)
            imgVolumen.remove();

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
                juego.preferences.putBoolean("musica", juego.musicaEncendida);
                juego.preferences.flush();
                juego.botonPulsado(sonidoClick);
                actualizaIconos();
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
            imgVibrar.setPosition(anchoPantalla / 7 * 4 * 0.97f, altoPantalla / 10 * 5.5f);
        }
        imgVibrar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.vibracionEncendida = !juego.vibracionEncendida;
                juego.botonPulsado(sonidoClick);
                juego.preferences.putBoolean("vibracion", juego.vibracionEncendida);
                juego.preferences.flush();
                actualizaIconos();
                return true;
            }
        });
        show();
    }

    @Override
    public void show() {
        super.show();

        fuente.getData().setScale(0.75f);

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
        fuente.draw(batch, juego.idiomas.get("volumen"), posX, altoPantalla / 10 * 9);
        fuente.draw(batch, juego.idiomas.get("vibrar"), posX, altoPantalla / 10 * 7);
        batch.end();
    }

    @Override
    public void hide() {
        fondo.remove();
        home.remove();
        imgVolumen.remove();
        imgVibrar.remove();
        Gdx.input.setInputProcessor(null);
    }
}
