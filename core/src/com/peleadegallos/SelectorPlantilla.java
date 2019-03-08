package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Plantilla para los selectores de mapa o personaje y armas
 */
public class SelectorPlantilla extends PlantillaEscenas {

    /**
     * El boton Continuar.
     */
    TextButton continuar,
    /**
     * El boton Anterior.
     */
    anterior;
    /**
     * El Id del jugador.
     */
    int idJugador;
    /**
     * The Sprite batch para poder escribir por pantalla
     */
    SpriteBatch spriteBatch;

    /**
     * Inicializa el selector
     *
     * @param juego     la clase de juego principal
     * @param idJugador el id del jugador
     */
    public SelectorPlantilla(final JuegoPrincipal juego, final int idJugador) {
        super(juego);
        this.idJugador = idJugador;

        //Cambia al siguente selector o a la pantall de juego
        continuar = new TextButton(juego.idiomas.get("continuar"), skin);
        continuar.setSize(anchoPantalla / 7 * 2, altoPantalla / 5);
        continuar.setPosition(anchoPantalla - anchoPantalla / 7 * 2 * 1.05f, altoPantalla * 0.05f);
        continuar.scaleBy(2);
        continuar.getLabel().setPosition(2, 2);
        continuar.getLabel().setFontScale(escalado05);
        continuar.getLabel().setColor(Color.BLACK);
        continuar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                switch (idJugador) {
                    case 1:
                        juego.setScreen(juego.selectorPersonajeArma2);
                        break;
                    case 2:
                        juego.setScreen(juego.selectorMapa);
                        break;
                    case 3:
                        juego.setScreen(juego.pantallaJuego);
                        break;
                }
            }
        });

        //Cambia al selector anterio o a la pantalla de menu
        anterior = new TextButton(juego.idiomas.get("atras"), skin);
        anterior.setSize(anchoPantalla / 7 * 2, altoPantalla / 5);
        anterior.setPosition(anchoPantalla - anchoPantalla / 7 * 2 * 1.05f, altoPantalla * 0.95f - altoPantalla / 5);
        anterior.scaleBy(2);
        anterior.getLabel().setPosition(2, 2);
        anterior.getLabel().setFontScale(escalado05);
        anterior.getLabel().setColor(Color.BLACK);
        anterior.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                switch (idJugador) {
                    case 1:
                        juego.setScreen(juego.menuInicio);
                        break;
                    case 2:
                        juego.setScreen(juego.selectorPersonajeArma);
                        break;
                    case 3:
                        juego.setScreen(juego.selectorPersonajeArma2);
                        break;
                }
            }
        });

        spriteBatch = new SpriteBatch();
    }

    /**
     * Se ejecuta al mostrar la pantalla, añade los actore y cambia el inputprocessor
     * Cambia el escalado de la fuente para que no se muy grande
     */
    @Override
    public void show() {
        super.show();
        escenario.addActor(fondo);
        escenario.addActor(continuar);
        escenario.addActor(anterior);
        escenario.addActor(home);

        Gdx.input.setInputProcessor(escenario);

        fuente.getData().setScale(escalado025);
    }

    /**
     * Renderiza la pantalla actual, si se pulsa atras va a la pantalla anterior
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            InputEvent evento = new InputEvent();
            evento.setType(InputEvent.Type.touchDown);
            anterior.fire(evento);
            evento.setType(InputEvent.Type.touchUp);
            anterior.fire(evento);
        }

        escenario.act();
        escenario.draw();
    }

    /**
     * Elimina del escenario los actores
     */
    @Override
    public void hide() {
        super.hide();

        continuar.remove();
        anterior.remove();
        home.remove();
        fondo.remove();
        Gdx.input.setInputProcessor(null);
    }
}
