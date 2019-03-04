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

    SpriteBatch batch;
    int posX;

    public AcercaDe(final JuegoPrincipal juego) {
        super(juego);

        posX = anchoPantalla / 7;

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        super.show();

        fuente.getData().setScale(escalado04);

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
        fuente.draw(batch, juego.idiomas.get("creado")+": Ero Sobrino Dorado", posX, altoPantalla / 10 * 9);
        fuente.draw(batch, juego.idiomas.get("musica")+": DL Sounds", posX, altoPantalla / 10 * 7);
        batch.end();
    }

    @Override
    public void hide() {
        home.remove();
        fondo.remove();
//        escenario.dispose();
        Gdx.input.setInputProcessor(null);

    }
}
