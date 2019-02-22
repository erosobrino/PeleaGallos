package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;
import java.util.Random;

public class MenuInicio extends PlantillaEscenas {

    Stage escenario;

    TextButton jugar, opciones, logros, ayuda;

    int alto, ancho;
    Skin skin;
    ArrayList<Nube> nubes;
    ArrayList<Texture> nubesTextura;
    Image imgInfo;
    Random rand;


    public MenuInicio(final JuegoPrincipal juego) {
        super(juego);
        alto = altoPantalla / 5;
        ancho = anchoPantalla / 7;

        juego.PIXEL_METRO_X = Gdx.graphics.getWidth() / juego.metrosX;
        juego.PIXEL_METRO_Y = Gdx.graphics.getHeight() / juego.metrosY;

        rand = new Random();

        escenario = new Stage();
        escenario.setDebugAll(juego.debug);

        nubes = new ArrayList<Nube>();
        nubesTextura = new ArrayList<Texture>();

        anadeNubes(1);

        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));         //Skin para botones y fuente (creada con hierro v5)

        jugar = new TextButton(juego.idiomas.get("jugar"), skin);
        jugar.setSize(ancho * 5, alto * 12 / 10);
        jugar.setPosition(ancho, alto * 3);
        jugar.scaleBy(2);
        jugar.getLabel().setPosition(2, 2);
        jugar.getLabel().setFontScale(0.75f);
        jugar.getLabel().setColor(Color.BLACK);
        jugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                juego.setScreen(juego.pantallaJuego1);
            }
        });

        opciones = new TextButton(juego.idiomas.get("opciones"), skin);
        opciones.setSize(ancho * 1.25f, alto);
        opciones.setPosition(ancho, alto * 0.75f);
        opciones.getLabel().setFontScale(0.3f);
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
        logros.getLabel().setFontScale(0.3f);
        logros.getLabel().setColor(Color.BLACK);

        ayuda = new TextButton(juego.idiomas.get("ayuda"), skin);
        ayuda.setSize(ancho * 1.25f, alto);
        ayuda.setPosition(ancho * 5 - ancho * 0.25f, alto * 0.75f);
        ayuda.getLabel().setFontScale(0.3f);
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

    //Añade 5 nubes mas por cada vez que se suma uno a cantidad
    private void anadeNubes(int cantidad) {
        for (int j = 0; j < cantidad; j++) {
            nubesTextura.add(juego.manager.get("nubes/nube1.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG2.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG14.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG18.png", Texture.class));
            nubesTextura.add(juego.manager.get("nubes/cloud_PNG27.png", Texture.class));
        }
    }

    @Override
    public void show() {
        super.show();


        for (int i = 0; i < nubesTextura.size(); i++) {
            nubes.add(new Nube(altoPantalla, anchoPantalla, nubesTextura.get(i), rand));
            escenario.addActor(nubes.get(i));
        }

        escenario.addActor(fondo);
        escenario.addActor(jugar);      //Añado los botones al escenario
        escenario.addActor(opciones);
        escenario.addActor(logros);
        escenario.addActor(ayuda);
        escenario.addActor(imgInfo);


        Gdx.input.setInputProcessor(escenario);  //Pone como listener al escenario, asi funcionan botones
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        escenario.act();
        escenario.draw();
    }

    @Override
    public void hide() {
        imgInfo.remove();
//        musica.pause();
        for (Nube nube : nubes) {
            nube.remove();
        }
        jugar.remove();
        opciones.remove();
        logros.remove();
        ayuda.remove();
//        escenario.dispose();

        Gdx.input.setInputProcessor(null);  //Quita como listener al escenario
    }
}
