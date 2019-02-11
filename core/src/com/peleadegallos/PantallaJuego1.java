package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaJuego1 extends PlantillaEscenas {

    Stage escenario;

    World mundo;

    Jugador jugador1;
    Suelo suelo;

    public PantallaJuego1(JuegoPrincipal juego) {
        super(juego);

        escenario = new Stage();
        mundo = new World(new Vector2(0, -10), true);
    }

    @Override
    public void show() {
        suelo=new Suelo(mundo,juego.manager.get("suelo2.png",Texture.class),new Vector2(0,0),juego, anchoPantalla, altoPantalla);

        jugador1 = new Jugador(mundo, juego.manager.get("dino1.png", Texture.class),new Vector2(0,4),juego);

        escenario.addActor(suelo);
        escenario.addActor(jugador1);

        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            juego.setScreen(juego.menuInicio);
        }

        escenario.act();
        mundo.step(delta,6,2);
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
