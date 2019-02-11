package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AcercaDe extends PlantillaEscenas {

    Image fondo;
    Image home;
    Stage escenario;
    SpriteBatch batch;
    int posX;

    public AcercaDe(JuegoPrincipal juego) {
        super(juego);
        posX = anchoPantalla / 7;
    }

    @Override
    public void show() {
        super.show();
        fuente.getData().setScale(0.4f);
        escenario = new Stage();

        fondo = new Image(juego.manager.get("suelo.png", Texture.class));     //Coge imagen del assetmanager
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

        batch = new SpriteBatch();

        escenario.addActor(fondo);
        escenario.addActor(home);

        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
       super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        batch.begin();
        fuente.draw(batch, "Creado por: Ero Sobrino Dorado", posX, altoPantalla / 10 * 9);
        fuente.draw(batch, "Musica de: DL Sounds", posX, altoPantalla / 10 * 7);
        batch.end();
    }

    @Override
    public void hide() {
        home.remove();
        fondo.remove();
        escenario.dispose();
        Gdx.input.setInputProcessor(null);

    }
}
