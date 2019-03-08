package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Pantalla para las opciones
 */
class Opciones extends PlantillaEscenas {

    /**
     * La Imagen volumen.
     */
    Image imgVolumen;
    /**
     * La Imagen vibrar.
     */
    Image imgVibrar;
    /**
     * La Imagen desarrollo.
     */
    Image imgDesarrollo;
    /**
     * Batch para escribir por pantalla
     */
    SpriteBatch batch;
    /**
     * El boton borrar records
     */
    TextButton btBorrar;
    /**
     * La posicion x para colocar el texto
     */
    int posX;
    /**
     * Para que no se superponga el texto a la ventana modal
     */
    boolean muestraTexto;

    /**
     * Inicializa la pantalla opciones
     *
     * @param juego la pantalla de juego principal
     */
    public Opciones(final JuegoPrincipal juego) {
        super(juego);
        posX = anchoPantalla / 7;


        btBorrar = new TextButton(juego.idiomas.get("borrar"), skin);
        btBorrar.setSize(anchoPantalla / 7 * 2, altoPantalla / 5);
        btBorrar.setPosition(anchoPantalla / 7 * 2, altoPantalla / 5 * 0.75f);
        btBorrar.getLabel().setFontScale(escalado03);
        btBorrar.getLabel().setColor(Color.BLACK);
        btBorrar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                muestraTexto = false;
                confirmacion();
            }
        });

        actualizaIconos();
        batch = new SpriteBatch();
    }

    /**
     * Lanza una ventana de confirmacion para borrar los records, si se pulsa si los borra, sino no hace nada
     */
    private void confirmacion() {
        final Dialog dialogo = new Dialog("", skin);
        dialogo.setModal(true);
        dialogo.setMovable(false);
        dialogo.setResizable(false);

        Label.LabelStyle estilo = new Label.LabelStyle(fuente, Color.BLACK);
        Label texto = new Label(juego.idiomas.get("estasSeguro"), estilo);

        TextButton btSi = new TextButton(juego.idiomas.get("si"), skin);
        btSi.getLabel().setColor(Color.BLACK);
        btSi.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialogo.hide();
                dialogo.cancel();
                dialogo.remove();
                juego.borraDatos();
                return true;
            }

        });

        TextButton btNo = new TextButton(juego.idiomas.get("no"), skin);
        btNo.getLabel().setColor(Color.BLACK);
        btNo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialogo.hide();
                dialogo.cancel();
                dialogo.remove();
                return true;
            }

        });
        int pad = altoPantalla / 80;
        dialogo.getContentTable().add(texto).pad(pad); //Para añadir elementos se añaden a la tabla de contenido

        Table tabla = new Table();
        tabla.add(btSi).pad(pad).width(anchoPantalla / 7 * 2);
        tabla.add(btNo).pad(pad).width(anchoPantalla / 7 * 2);
        dialogo.getButtonTable().add(tabla).center().padBottom(pad);

        dialogo.show(escenario);
        escenario.addActor(dialogo);
    }

    /**
     * Actualiza los iconos mostrados dependiendo de si estan activado o no y
     * cuando se modifican guarda los cambios en las shared preferences
     */
    private void actualizaIconos() {
        if (imgVibrar != null)
            imgVibrar.remove();
        if (imgVolumen != null)
            imgVolumen.remove();
        if (imgDesarrollo != null)
            imgDesarrollo.remove();

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


        if (juego.modoDesarrollo)
            imgDesarrollo = new Image(juego.manager.get("iconos/developer.png", Texture.class));
        else
            imgDesarrollo = new Image(juego.manager.get("iconos/developerNo.png", Texture.class));
        imgDesarrollo.setSize(altoPantalla / 7, altoPantalla / 7);
        imgDesarrollo.setPosition(anchoPantalla / 7 * 4, altoPantalla / 10 * 3.5f * 1.07f);
        imgDesarrollo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                juego.modoDesarrollo = !juego.modoDesarrollo;
                juego.preferences.putBoolean("desarrollo", juego.modoDesarrollo);
                juego.preferences.flush();
                juego.botonPulsado(sonidoClick);
                actualizaIconos();
                return true;
            }
        });

        show();
    }

    /**
     * Se ejecuta cuando aparece la pantalla
     * Cambia el escalado de la fuente y añade los actores
     */
    @Override
    public void show() {
        super.show();

        fuente.getData().setScale(escalado075);

        muestraTexto = true;

        escenario.addActor(fondo);
        escenario.addActor(home);
        escenario.addActor(imgVibrar);
        escenario.addActor(imgVolumen);
        escenario.addActor(imgDesarrollo);
        escenario.addActor(btBorrar);

        Gdx.input.setInputProcessor(escenario);  //Pone como listener al escenario, asi funcionan botones
    }

    /**
     * Renderiza la pantalla actual, muestra el texto y si se pulsa atras va al menu
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        if (muestraTexto) {
            batch.begin();
            fuente.draw(batch, juego.idiomas.get("volumen"), posX, altoPantalla / 10 * 9);
            fuente.draw(batch, juego.idiomas.get("vibrar"), posX, altoPantalla / 10 * 7);
            fuente.draw(batch, juego.idiomas.get("desarrollo"), posX, altoPantalla / 10 * 5 * 1.05f);
            batch.end();
        }
    }

    /**
     * Se ejecuta al desaparecer la pantalla, elimna los actores y el inputprocessor
     */
    @Override
    public void hide() {
        fondo.remove();
        home.remove();
        imgVolumen.remove();
        imgVibrar.remove();
        imgDesarrollo.remove();
        btBorrar.remove();
        Gdx.input.setInputProcessor(null);
    }
}
