package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Selector para las armas y los personajes
 */
public class SelectorPersonajeArma extends SelectorPlantilla {

    /**
     *Los personajes
     */
    Personaje[] personajes;
    /**
     * Las armas
     */
    Arma[] armas;
    /**
     * La imagen del personaje actual
     */
    Image imgPersonaje,
    /**
     * La imagen del arma actual
     */
    imgArmas;
    /**
     * El boton adelante del personaje
     */
    Image btadelantePer, /**
     * El boton atras del personaje
     */
    btatrasPer, /**
     * El boton adelante del arma.
     */
    btadelanteArm, /**
     * El boton atras del arma
     */
    btatrasArm;

    /**
     * El indice del personaje seleccionado
     */
    int indicePersonaje = 0,
    /**
     * El indice del arma seleccionada
     */
    indiceArma = 0;

    /**
     * Inicializa un nuevo selector de armas y personajes
     *
     * @param juego      la pantalla de juego principal
     * @param armas      las armas
     * @param personajes los personajes
     * @param idJugador  el id jugador, gestion de los botones de la clase padre
     */
    public SelectorPersonajeArma(final JuegoPrincipal juego, final Personaje[] personajes, final Arma[] armas, final int idJugador) {
        super(juego, idJugador);
        this.personajes = personajes;
        this.armas = armas;

        //Cambian las imagenes y vuelven a empezar al llegar al final y viceversa

        btadelantePer = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btadelantePer.setSize(altoPantalla / 7, altoPantalla / 7);
        btadelantePer.setPosition(anchoPantalla / 8 * 1.25f, altoPantalla - altoPantalla / 3);
        btadelantePer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indicePersonaje++;
                if (indicePersonaje >= personajes.length)
                    indicePersonaje = 0;
                actualizaImagenPersonaje(indicePersonaje);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btadelanteArm = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btadelanteArm.setSize(altoPantalla / 7, altoPantalla / 7);
        btadelanteArm.setPosition(anchoPantalla / 2 * 1.05f, altoPantalla - altoPantalla / 3);
        btadelanteArm.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceArma++;
                if (indiceArma >= armas.length)
                    indiceArma = 0;
                actualizaImagenArma(indiceArma);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btatrasPer = new Image(juego.manager.get("iconos/flechaAbajo.png", Texture.class));
        btatrasPer.setSize(altoPantalla / 7, altoPantalla / 7);
        btatrasPer.setPosition(anchoPantalla / 8 * 1.25f, altoPantalla - altoPantalla / 2 * 1.65f);
        btatrasPer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indicePersonaje--;
                if (indicePersonaje < 0)
                    indicePersonaje = personajes.length - 1;
                actualizaImagenPersonaje(indicePersonaje);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btatrasArm = new Image(juego.manager.get("iconos/flechaAbajo.png", Texture.class));
        btatrasArm.setSize(altoPantalla / 7, altoPantalla / 7);
        btatrasArm.setPosition(anchoPantalla / 2 * 1.05f, altoPantalla - altoPantalla / 2 * 1.65f);
        btatrasArm.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceArma--;
                if (indiceArma < 0)
                    indiceArma = armas.length - 1;
                actualizaImagenArma(indiceArma);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });
    }


    //Necesario hacer asi porque sino no se puede eliminar del escenario y se superponen las imagenes

    /**
     * Cambia la imagen del personaje seleccionada
     *
     * @param indice el indice actual
     */
    private void actualizaImagenPersonaje(int indice) {
        if (imgPersonaje != null)
            imgPersonaje.remove();
        imgPersonaje = new Image(personajes[indice].getTextura());
        imgPersonaje.setPosition(anchoPantalla / 8, altoPantalla - altoPantalla / 6 * 3.75f);
        imgPersonaje.setSize(juego.PIXEL_METRO_X * 2, juego.PIXEL_METRO_Y * 2);
        escenario.addActor(imgPersonaje);
    }

    /**
     * Cambia la imagen del arma seleccionada
     *
     * @param indice el indice actual
     */
    private void actualizaImagenArma(int indice) {
        if (imgArmas != null)
            imgArmas.remove();
        imgArmas = new Image(armas[indice].getTextura());
        imgArmas.setPosition(anchoPantalla / 2, altoPantalla - altoPantalla / 6 * 3.75f);
        imgArmas.setSize(juego.PIXEL_METRO_X * 2, juego.PIXEL_METRO_Y * 2);
        escenario.addActor(imgArmas);
    }

    /**
     * Se ejecuta al mostrar la pantalla, añade los actores y actualiza la imagen del personaje y el arma
     */
    @Override
    public void show() {
        super.show();

        escenario.addActor(btatrasPer);
        escenario.addActor(btatrasArm);
        escenario.addActor(btadelantePer);
        escenario.addActor(btadelanteArm);

        indiceArma = 0;
        indicePersonaje = 0;

        actualizaImagenArma(indiceArma);
        actualizaImagenPersonaje(indicePersonaje);
    }

    /**
     * Renderiza la pantalla llamando a la clase padre y muestra informacion del arma y del personaje
     *
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        fuente.getData().setScale(escalado035);
        fuente.draw(spriteBatch, juego.idiomas.get("jugador") + ": " + idJugador, anchoPantalla / 4 * 1.1f, altoPantalla - altoPantalla / 10);

        fuente.getData().setScale(escalado025);
        fuente.draw(spriteBatch, juego.idiomas.get("vida") + ": " + personajes[indicePersonaje].getVida(), anchoPantalla / 4 * 1.1f, altoPantalla - altoPantalla / 6 * 2 * 1.25f);
        fuente.draw(spriteBatch, juego.idiomas.get("velocidadPer") + ": " + personajes[indicePersonaje].getVelocidad(), anchoPantalla / 4 * 1.1f, altoPantalla - altoPantalla / 6 * 4 * 0.75f);

        fuente.draw(spriteBatch, juego.idiomas.get("alcance") + ": " + armas[indiceArma].getAlcance(), anchoPantalla / 2 * 1.25f, altoPantalla - altoPantalla / 3 * 1.15f);
        fuente.draw(spriteBatch, juego.idiomas.get("balasTotales") + ": " + armas[indiceArma].getBalas(), anchoPantalla / 2 * 1.25f, altoPantalla - altoPantalla / 3 * 1.35f);
        fuente.draw(spriteBatch, juego.idiomas.get("daño") + ": " + armas[indiceArma].getDaño(), anchoPantalla / 2 * 1.25f, altoPantalla - altoPantalla / 3 * 1.55f);
        spriteBatch.end();
    }

    /**
     * Se ejecuta al desaparecer la pantalla, elimina los actores
     */
    @Override
    public void hide() {
        super.hide();

        if (imgArmas != null)
            imgArmas.remove();
        if (imgPersonaje != null)
            imgPersonaje.remove();

        btatrasPer.remove();
        btatrasArm.remove();
        btadelantePer.remove();
        btadelanteArm.remove();
    }
}
