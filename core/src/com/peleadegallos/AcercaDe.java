package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Pantalla en la que se muestra informacion relativa al creador y las fuentes de las imagenes o recursos
 */
public class AcercaDe extends PlantillaEscenas {

    SpriteBatch batch;

    int posX;

    /**
     * Constructor de la clase, llama a su superclase y le pasa la pantalla de juego principal
     *
     * @param juego pantalla de juego principal
     */
    public AcercaDe(final JuegoPrincipal juego) {
        super(juego);

        posX = anchoPantalla / 7;

        batch = new SpriteBatch();
    }

    /**
     * Cambia la escala de la fuente y añade los actores
     */
    @Override
    public void show() {
        super.show();

        fuente.getData().setScale(escalado04);

        escenario.addActor(fondo);
        escenario.addActor(home);

        Gdx.input.setInputProcessor(escenario); //Indica quien gestiona las pulsaciones o gestos
    }

    /**
     * Funcion llamada a la hora de mostrar por pantalla, escribe los datos informativos ¡
     * @param delta el tiempo que ha pasado desde la ultima ejecucion, no se utiliza al no mover ningun elemento
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) //Si se pulsa el boton atras
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        batch.begin();
        fuente.draw(batch, juego.idiomas.get("creado")+": Ero Sobrino Dorado", posX, altoPantalla / 10 * 9);
        fuente.draw(batch, juego.idiomas.get("musica")+": DL Sounds", posX, altoPantalla / 10 * 7);
        batch.end();
    }

    /**
     * Se ejecuta al desaparecer la pantalla, quita el escenario como inputproccessor
     * para que se puedan utilizar los botones en las otras pantallas
     */
    @Override
    public void hide() {
        home.remove();
        fondo.remove();
//        escenario.dispose();
        Gdx.input.setInputProcessor(null);

    }
}
