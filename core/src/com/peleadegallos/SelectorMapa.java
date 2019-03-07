package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The type Selector mapa.
 */
public class SelectorMapa extends SelectorPlantilla {

    /**
     * The Mapas.
     */
    Mapa[] mapas;
    /**
     * The Btadelante.
     */
    Image btadelante, /**
     * The Btatras.
     */
    btatras;
    /**
     * The Mapa seleccionado.
     */
    Image mapaSeleccionado;
    /**
     * The Indice mapa.
     */
    int indiceMapa = 0;

    /**
     * Instantiates a new Selector mapa.
     *
     * @param juego     the juego
     * @param mapas     the mapas
     * @param idJugador the id jugador
     */
    public SelectorMapa(final JuegoPrincipal juego, final Mapa[] mapas, int idJugador) {
        super(juego, idJugador);
        this.mapas = mapas;

        btadelante = new Image(juego.manager.get("iconos/flechaDerecha.png", Texture.class));
        btadelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btadelante.setPosition(anchoPantalla / 10*7.25f, altoPantalla/2-altoPantalla/7*0.8f);
        btadelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceMapa++;
                if (indiceMapa >= mapas.length)
                    indiceMapa = 0;
                actualizaMapa(indiceMapa);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btatras = new Image(juego.manager.get("iconos/flechaIzquierda.png", Texture.class));
        btatras.setSize(altoPantalla / 7, altoPantalla / 7);
        btatras.setPosition(anchoPantalla / 10, altoPantalla/2-altoPantalla/7*0.8f);
        btatras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceMapa--;
                if (indiceMapa < 0)
                    indiceMapa = mapas.length - 1;
                actualizaMapa(indiceMapa);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });
    }

    private void actualizaMapa(int indice) {
        if (mapaSeleccionado != null)
            mapaSeleccionado.remove();
        mapaSeleccionado = new Image(mapas[indice].mapa);
        mapaSeleccionado.setPosition(anchoPantalla / 5, altoPantalla/2 - altoPantalla / 7*0.8f);
        mapaSeleccionado.setSize(anchoPantalla/2, altoPantalla/4);
        escenario.addActor(mapaSeleccionado);
    }

    @Override
    public void show() {
        super.show();

        escenario.addActor(btadelante);
        escenario.addActor(btatras);

        indiceMapa=0;

        actualizaMapa(indiceMapa);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void hide() {
        super.hide();

        if (mapaSeleccionado!=null)
            mapaSeleccionado.remove();

        btadelante.remove();
        btatras.remove();
    }
}
