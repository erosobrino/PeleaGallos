package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Selector para los mapas
 */
public class SelectorMapa extends SelectorPlantilla {

    /**
     * Los Mapas.
     */
    Mapa[] mapas;
    /**
     * El boton adelante.
     */
    Image btadelante,
    /**
     * El boton atras.
     */
    btatras;
    /**
     * El Mapa seleccionado.
     */
    Image mapaSeleccionado;
    /**
     * El Indice del mapa seleccionado
     */
    int indiceMapa = 0;

    /**
     * Inicializa un nuevo selector de mapas
     *
     * @param juego     la pantalla de juego principal
     * @param mapas     los mapas
     * @param idJugador el id jugador, gestion de los botones de la clase padre
     */
    public SelectorMapa(final JuegoPrincipal juego, final Mapa[] mapas, int idJugador) {
        super(juego, idJugador);
        this.mapas = mapas;

        //Cambian los mapas elegidos
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

    /**
     * Funcion que cambia la imagen del mapa seleccionado, si esta en el escenario lo elimina y despues lo añade
     * @param indice el indice de mapa que se tiene que mostrar
     */
    private void actualizaMapa(int indice) {
        if (mapaSeleccionado != null)
            mapaSeleccionado.remove();
        mapaSeleccionado = new Image(mapas[indice].mapa);
        mapaSeleccionado.setPosition(anchoPantalla / 5, altoPantalla/2 - altoPantalla / 7*0.8f);
        mapaSeleccionado.setSize(anchoPantalla/2, altoPantalla/4);
        escenario.addActor(mapaSeleccionado);
    }

    /**
     * Se ejecuta al mostrar la pantalla, añade los actores y actualiza la imagen del mapa
     */
    @Override
    public void show() {
        super.show();

        escenario.addActor(btadelante);
        escenario.addActor(btatras);

        indiceMapa=0;

        actualizaMapa(indiceMapa);
    }

    /**
     * Renderiza la pantalla llamando a la clase padre
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);
    }

    /**
     * Se ejecuta al desaparecer la pantalla, elimina los actores
     */
    @Override
    public void hide() {
        super.hide();

        if (mapaSeleccionado!=null)
            mapaSeleccionado.remove();

        btadelante.remove();
        btatras.remove();
    }
}
