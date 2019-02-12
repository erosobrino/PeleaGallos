package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaJuego1 extends PlantillaEscenas {

    Stage escenario;

    World mundo;

    Jugador jugador1;
    Suelo suelo1;
    Suelo suelo2;
    Suelo suelo3;
    Suelo suelo4;
    Suelo suelo5;
    Suelo suelo6;
    Suelo suelo7;
    Suelo suelo8;

    OrthographicCamera camera;
    Box2DDebugRenderer renderer;

    public PantallaJuego1(JuegoPrincipal juego) {
        super(juego);

        mundo = new World(new Vector2(0, -10), true);

        camera = new OrthographicCamera(juego.metrosX, juego.metrosY);
        renderer = new Box2DDebugRenderer();
//        camera.zoom = 0.3f;
//        escenario = new Stage(new FitViewport(anchoPantalla,altoPantalla,camera));
        escenario = new Stage(new FitViewport(anchoPantalla, altoPantalla));
        escenario.setDebugAll(true);
        camera.translate(6, 4);

    }

    @Override
    public void show() {
        //Limites  visible 0-8x 0-15y
        suelo1 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(0, 0), juego, anchoPantalla, altoPantalla);
        suelo2 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(2, 0), juego, anchoPantalla, altoPantalla);
        suelo3 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(4, 0.5f), juego, anchoPantalla, altoPantalla);
        suelo4 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(6, 0.75f), juego, anchoPantalla, altoPantalla);
        suelo5 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(8, 0.75f), juego, anchoPantalla, altoPantalla);
        suelo6 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(10, 0.5f), juego, anchoPantalla, altoPantalla);
        suelo7 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(12, 0), juego, anchoPantalla, altoPantalla);
        suelo8 = new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(14, 0), juego, anchoPantalla, altoPantalla);

        jugador1 = new Jugador(mundo, juego.manager.get("dino1.png", Texture.class), new Vector2(0, 3), juego);

        escenario.addActor(suelo1);
        escenario.addActor(suelo2);
        escenario.addActor(suelo3);
        escenario.addActor(suelo4);
        escenario.addActor(suelo5);
        escenario.addActor(suelo6);
        escenario.addActor(suelo7);
        escenario.addActor(suelo8);
        escenario.addActor(jugador1);

        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            juego.setScreen(juego.menuInicio);
        }

        escenario.act();
        mundo.step(delta, 6, 2);
        camera.update();
        renderer.render(mundo, camera.combined);
        escenario.draw();
    }

    @Override
    public void hide() {
        jugador1.elimina();
        jugador1.remove();

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        escenario.dispose();
        mundo.dispose();
    }
}
