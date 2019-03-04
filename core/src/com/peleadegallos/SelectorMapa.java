package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SelectorMapa extends SelectorPlantilla {

    Mapa[] mapas;
    Image btadelante, btatras;
    Image mapaSeleccionado;
    int indiceMapa = 0;

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

        actualizaMapa(0);
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
        btadelante.remove();
    }
}
