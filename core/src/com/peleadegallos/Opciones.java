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
 * The type Opciones.
 */
class Opciones extends PlantillaEscenas {

    /**
     * The Img volumen.
     */
    Image imgVolumen;
    /**
     * The Img vibrar.
     */
    Image imgVibrar;
    /**
     * The Img desarrollo.
     */
    Image imgDesarrollo;
    /**
     * The Batch.
     */
    SpriteBatch batch;
    /**
     * The Bt borrar.
     */
    TextButton btBorrar;
    /**
     * The Pos x.
     */
    int posX;
    /**
     * The Muestra texto.
     */
    boolean muestraTexto;

    /**
     * Instantiates a new Opciones.
     *
     * @param juego the juego
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
                fondo.remove();
                home.remove();
                imgVolumen.remove();
                imgVibrar.remove();
                imgDesarrollo.remove();
                btBorrar.remove();
                muestraTexto = false;
                confirmacion();
            }
        });

        actualizaIconos();
        batch = new SpriteBatch();
    }

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
                show();
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
                show();
                return true;
            }

        });
        int pad = altoPantalla / 80;
        dialogo.getContentTable().add(texto).pad(pad);

        Table tabla = new Table();
        tabla.add(btSi).pad(pad).width(anchoPantalla/7*2);
        tabla.add(btNo).pad(pad).width(anchoPantalla/7*2);
        dialogo.getButtonTable().add(tabla).center().padBottom(pad);

        dialogo.show(escenario);
        escenario.addActor(dialogo);
    }


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
        Gdx.input.setCatchBackKey(true);
    }

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
