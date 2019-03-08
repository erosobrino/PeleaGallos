package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


/**
 * Pantalla de inicio, desdee aqui se lanzan el resto
 */
public class MenuInicio extends PlantillaEscenas {

    /**
     * El boton Jugar.
     */
    TextButton jugar,
    /**
     * El boton Opciones.
     */
    opciones,
    /**
     * El boton Logros.
     */
    logros,
    /**
     * El boton Ayuda.
     */
    ayuda;
    /**
     * El Alto.
     */
    int alto,
    /**
     * El Ancho.
     */
    ancho;
    /**
     * El dibujo para la informacion
     */
    Image imgInfo;

    /**
     * Inicialza la pantalla con todos los botones necesarios
     *
     * @param juego la pantalla de juego principal
     */
    public MenuInicio(final JuegoPrincipal juego) {
        super(juego);
        alto = altoPantalla / 5;
        ancho = anchoPantalla / 7;

        juego.PIXEL_METRO_X = Gdx.graphics.getWidth() / juego.metrosX;
        juego.PIXEL_METRO_Y = Gdx.graphics.getHeight() / juego.metrosY;

        jugar = new TextButton(juego.idiomas.get("jugar"), skin);
        jugar.setSize(ancho * 5, alto * 12 / 10);
        jugar.setPosition(ancho, alto * 3);
        jugar.scaleBy(2);
        jugar.getLabel().setPosition(2, 2);
        jugar.getLabel().setFontScale(escalado075);
        jugar.getLabel().setColor(Color.BLACK);
        jugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                juego.setScreen(juego.selectorPersonajeArma);
            }
        });

        opciones = new TextButton(juego.idiomas.get("opciones"), skin);
        opciones.setSize(ancho * 1.25f, alto);
        opciones.setPosition(ancho, alto * 0.75f);
        opciones.getLabel().setFontScale(escalado03);
        opciones.getLabel().setColor(Color.BLACK);
        opciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                juego.setScreen(juego.opciones);
            }
        });

        logros = new TextButton(juego.idiomas.get("logros"), skin);
        logros.setSize(ancho * 1.25f, alto);
        logros.setPosition(ancho * 3 - (ancho * 0.25f) / 2, alto * 0.75f);
        logros.getLabel().setFontScale(escalado03);
        logros.getLabel().setColor(Color.BLACK);
        logros.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                juego.setScreen(juego.logros = new PantallaLogros(juego));
            }
        });

        ayuda = new TextButton(juego.idiomas.get("ayuda"), skin);
        ayuda.setSize(ancho * 1.25f, alto);
        ayuda.setPosition(ancho * 5 - ancho * 0.25f, alto * 0.75f);
        ayuda.getLabel().setFontScale(escalado03);
        ayuda.getLabel().setColor(Color.BLACK);

        imgInfo = new Image(juego.manager.get("iconos/info-circle.png", Texture.class));
        imgInfo.setSize(altoPantalla / 7, altoPantalla / 7);
        imgInfo.setPosition(anchoPantalla - altoPantalla / 7 - 5, altoPantalla - altoPantalla / 7 - 5);
        imgInfo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.botonPulsado(sonidoClick);
                juego.setScreen(juego.info);
                return true;
            }
        });
    }

    /**
     * Se ejecuta cuando se muestra la pantalla
     * Añade los actores y pone como inputprocesso al escenario
     */
    @Override
    public void show() {
        super.show();

        escenario.addActor(fondo);
        escenario.addActor(jugar);      //Añado los botones al escenario
        escenario.addActor(opciones);
        escenario.addActor(logros);
        escenario.addActor(ayuda);
        escenario.addActor(imgInfo);


        Gdx.input.setInputProcessor(escenario);  //Pone como listener al escenario, asi funcionan botones
    }

    /**
     * Rrenderiza el estado de la pantalla actual
     *
     * @param delta el tiempo desde la utlima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        escenario.act();
        escenario.draw();
    }

    /**
     * Se ejecuta cuando la pantalla no esta visble
     * Quita los actores del escenario
     */
    @Override
    public void hide() {
        imgInfo.remove();
        jugar.remove();
        opciones.remove();
        logros.remove();
        ayuda.remove();

        Gdx.input.setInputProcessor(null);  //Quita como listener al escenario
    }
}
