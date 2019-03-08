package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * La pantalla de juego, esta se utiliza independientemente del mapa o los jugadores
 */
public class PantallaJuego extends PlantillaEscenas {

    /**
     * El Mundo.
     */
    World mundo;

    /**
     * El Jugador 1.
     */
    Jugador jugador1;
    /**
     * El Jugador 2.
     */
    Jugador jugador2;
    /**
     * El Jugador actual.
     */
    Jugador jugActual;

    /**
     * String con el nombre de la fixture del jugador actual
     */
    String jugadorActual = "jugador1";

    /**
     * Coleccion de jugadores
     */
    ArrayList<Jugador> jugadores;
    /**
     * Coleccion con los todos los suelos
     */
    ArrayList<Suelo> suelos;

    /**
     * Coleccion de Balas.
     */
    ArrayList<Bala> balas;

    /**
     * El Boton adelante.
     */
    Image btAdelante,
    /**
     * El Boton atras.
     */
    btAtras,
    /**
     * El Boton saltar adelante.
     */
    btSaltarAdelante,
    /**
     * El Boton saltar atras.
     */
    btSaltarAtras,
    /**
     * El Boton disparo.
     */
    btDisparo;

    /**
     * La camara qu see usa, despalzamiento para centrarlo
     */
    OrthographicCamera camera;
    /**
     * Renderer de box2d para poder ver los limites en la partde de box2s
     */
    Box2DDebugRenderer renderer;
    /**
     * El Boton para cambio de personaje.
     */
    TextButton btCambioPersonaje;

    /**
     * La coleccion de puntos para apuntar
     */
    ArrayList<Vector2> fling;
    /**
     * Permite dibujar formas por pantalla
     */
    ShapeRenderer formas;

    /**
     * Permite escribir texto por pantalla
     */
    SpriteBatch batchTexto;

    /**
     * El actor con los limites del mapa
     */
    ActorLimiteMapa limiteMapa;

    /**
     * El tiempo mostrado por pantalla formateado a segundos
     */
    String tiempoString = 20 + "";
    /**
     * El tiempo de duracion de cada turno
     */
    int tiempoTurno = 20;
    /**
     * El tiempo que le queda al turno
     */
    int tiempo = tiempoTurno;
    /**
     * Veces que se ejecuta el timer
     */
    int contTimer = 0;
    /**
     * Los segundos desde que se inicio la partida
     */
    int segundosPartida = 0;

    /**
     * Si se ha acabado la partida
     */
    boolean partidaAcabada;
    /**
     * El id del ganador
     */
    int idGanador;
    /**
     * Si la pantalla esta visible
     */
    boolean visible;
    /**
     * La cantida total de balas disparadas
     */
    int balasUtilizadas = 0;
    /**
     * Si el jugador actual debe volver a saltar en cuanto toca el suelo
     */
    boolean debeSaltar = false; //salto continuo con sonido, contact listener

    /**
     * El angulo
     */
    double angulo;
    /**
     * El gestor de gestos que se utiliza para poder gestionar el fling y el escenario
     */
    GestorGestos gestorGestos;

    /**
     * El nombre del mapa
     */
    String mapa;

    /**
     * El id del jugador actual
     */
    int idActual = 1;

    /**
     * El timer
     */
    Timer.Task timer;

    /**
     * Inicializa la pantalla de juego
     * Inicia los botones y el mundo pero no añade los actores, eso se hace en el show
     *
     * @param juego the juego
     */
    public PantallaJuego(final JuegoPrincipal juego) {
        super(juego);

        mundo = new World(new Vector2(0, -10), true);
        mundo.setAutoClearForces(true);

        camera = new OrthographicCamera(juego.metrosX, juego.metrosY);
        renderer = new Box2DDebugRenderer();
        camera.translate(6, 4);

        btAdelante = new Image(juego.manager.get("iconos/flechaDerecha.png", Texture.class));
        btAdelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btAdelante.setPosition(anchoPantalla - altoPantalla / 7 - 5, 5);
        btAdelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.adelante;
                juego.botonPulsadoMusica(jugActual.sonidoAndar);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                if (jugActual.sonidoAndar.isPlaying())
                    jugActual.sonidoAndar.stop();
            }
        });

        btAtras = new Image(juego.manager.get("iconos/flechaIzquierda.png", Texture.class));
        btAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btAtras.setPosition(5, 5);
        btAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.atras;
                juego.botonPulsadoMusica(jugActual.sonidoAndar);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                if (jugActual.sonidoAndar.isPlaying())
                    jugActual.sonidoAndar.stop();
            }
        });

        btSaltarAdelante = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAdelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAdelante.setPosition(anchoPantalla - altoPantalla / 7 * 2 - 10, 5);
        btSaltarAdelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.saltaAdelante;
                if (!jugActual.saltando)
                    juego.botonPulsado(jugActual.sonidoSalto);
                debeSaltar = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                debeSaltar = false;
            }
        });

        btSaltarAtras = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAtras.setPosition(altoPantalla / 7 + 10, 5);
        btSaltarAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.saltaAtras;
                if (!jugActual.saltando)
                    juego.botonPulsado(jugActual.sonidoSalto);
                debeSaltar = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                debeSaltar = false;
            }
        });

        btDisparo = new Image(juego.manager.get("iconos/disparo.png", Texture.class));
        btDisparo.setSize(altoPantalla / 7, altoPantalla / 7);
        btDisparo.setPosition(altoPantalla / 7 * 3, 5);
        btDisparo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (jugActual.balasRestantes > 0) {
                    Vector2 inicio = null;
                    inicio = new Vector2(jugActual.getX() / juego.PIXEL_METRO_X, jugActual.getY() / juego.PIXEL_METRO_Y); //Punto de salida de la bala, en la esquina a la que apunte
                    if (jugActual.avanza)                       //prefeinido para cuando lanza a 45
                        inicio.x += jugActual.tamañoX * 1.5f;
                    else
                        inicio.x -= jugActual.tamañoX * 1.5f;
                    inicio.y += jugActual.tamañoY * 1.5f;
                    if (inicio != null) {
                        balasUtilizadas++;
                        jugActual.balasRestantes--;
                        balas.add(new Bala(mundo, inicio, juego, jugActual));
                        escenario.addActor(balas.get(balas.size() - 1));
                    }
                }
                return true;
            }
        });

        btCambioPersonaje = new TextButton(juego.idiomas.get("cambioPersonaje"), skin);
        btCambioPersonaje.setSize(anchoPantalla / 5, altoPantalla / 7);
        btCambioPersonaje.setPosition(anchoPantalla / 2 - btCambioPersonaje.getWidth() / 2, 0);
        btCambioPersonaje.getLabel().setFontScale(escalado05);
        btCambioPersonaje.getLabel().setColor(Color.BLACK);
        btCambioPersonaje.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                for (Jugador jugador : jugadores) {
                    jugador.turno = !jugador.turno;
                    if (jugador.turno) {
                        jugadorActual = jugador.fixture.getUserData().toString();
                        jugActual = jugador;
                        gestorGestos.jugadorActual = jugador;
                        if (jugador.fixture.getUserData().equals("jugador1"))
                            idActual = 1;
                        else
                            idActual = 2;
                    } else
                        jugador.movimiento = Jugador.Movimiento.nada;
                    jugador.balasRestantes = jugador.cantidadBalas;
                }
                tiempo = tiempoTurno;
                tiempoString = tiempo + "";
                fling.clear();
            }
        });

        formas = new ShapeRenderer();
        batchTexto = new SpriteBatch();
    }

    /**
     * Ventana de confirmacion para salir de la partida
     * Pausa el timer y se reinicia si se cancela, la ventana es modal, por tanto no se puede seguir jugando
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
                juego.setScreen(juego.menuInicio);
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
                timer = Timer.schedule(new Timer.Task() {
                                           @Override
                                           public void run() {
                                               timer();
                                           }
                                       }
                        , 0, 0.200f
                );
                return true;
            }

        });
        int pad = altoPantalla / 80; //El padding
        dialogo.getContentTable().add(texto).pad(pad); //Para añadir elementos se añaden a la tabla de contenido

        Table tabla = new Table();
        tabla.add(btSi).pad(pad).width(anchoPantalla / 7 * 2);
        tabla.add(btNo).pad(pad).width(anchoPantalla / 7 * 2);
        dialogo.getButtonTable().add(tabla).center().padBottom(pad);

        dialogo.show(escenario); //Lo centra en pantalla
        escenario.addActor(dialogo);
    }

    /**
     * Resetea las estadisticas e inicia los jugadores cargando sus texturas y colocandolos
     * Carga los suelos
     * Añade los actores y estabelece el gestor de gestos
     */
    @Override
    public void show() {
        super.show();
        fling = new ArrayList<Vector2>();
        balas = new ArrayList<Bala>();
        idGanador = 0;
        segundosPartida = 0;
        partidaAcabada = false;
        visible = true;
        //Limites  visible 0-8x 0-15y
        suelos = cargaSuelos(juego.selectorMapa.indiceMapa);

        jugador1 = cargaJugadore(juego.selectorPersonajeArma, true, new Vector2(0, 3));
        jugador2 = cargaJugadore(juego.selectorPersonajeArma2, false, new Vector2(15, 3));

        jugador2.avanza = false;
        jugador2.angulo = 135;

        jugador1.fixture.setUserData("jugador1");
        jugador2.fixture.setUserData("jugador2");

        jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        jugActual = jugador1;


        limiteMapa = new ActorLimiteMapa(mundo, altoPantalla, anchoPantalla);
        escenario.addActor(limiteMapa);

        escenario.addActor(jugador1);
        escenario.addActor(jugador2);

        escenario.addActor(btAtras);
        escenario.addActor(btAdelante);
        escenario.addActor(btSaltarAtras);
        escenario.addActor(btSaltarAdelante);

        escenario.addActor(btCambioPersonaje);

        escenario.addActor(btDisparo);


        mundo.setContactListener(new ContactListener() {

            /**
             * Detecta si la colision se produce entre dos cuerpos
             * No depende de quien choca con quien
             * @param contact la colision
             * @param nombreObjeto1 el nombre de la fixture del elemento 1
             * @param nombreObjeto2 el nombre de la fixture del elemento 2
             * @return true si son ellos, false si no
             */
            private boolean hanColisionado(Contact contact, Object nombreObjeto1, Object nombreObjeto2) {
                return (contact.getFixtureA().getUserData().equals(nombreObjeto1) && contact.getFixtureB().getUserData().equals(nombreObjeto2))
                        || (contact.getFixtureA().getUserData().equals(nombreObjeto2) && contact.getFixtureB().getUserData().equals(nombreObjeto1));
            }

            /**
             * Cuando se chocan
             * @param contact el choque
             */
            @Override
            public void beginContact(Contact contact) {
                if (hanColisionado(contact, "jugador1", "suelo")) {
                    jugador1.indiceAux = 0; //Para que tenga efecto al saltar
                    if (jugador1.saltando) {
                        jugador1.saltando = false;
                        jugador1.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador1.sonidoSalto);
                    }
                }
                if (hanColisionado(contact, "jugador2", "suelo")) {
                    jugador2.indiceAux = 0; //Para que tenga efecto al saltar
                    if (jugador2.saltando) {
                        jugador2.saltando = false;
                        jugador2.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador2.sonidoSalto);
                    }
                }
                //Para que puedan seguir saltando y no parezca que se esta caeyendo, se le reduce el salto para que np se pueda saltar muy alto
                if (hanColisionado(contact, "jugador1", "jugador2")) {
                    if (jugador1.saltando) {
                        jugador1.saltando = false;
                        jugador1.tocaSuelo = true;
                        jugador1.indiceAux = 0;
                        jugador1.fuerzaY = 6; //Asi cuando salte salta menos
                        if (debeSaltar)
                            juego.botonPulsado(jugador1.sonidoSalto);
                    }
                    if (jugador2.saltando) {
                        jugador2.saltando = false;
                        jugador2.tocaSuelo = true;
                        jugador2.indiceAux = 0;
                        jugador2.fuerzaY = 6;   //Asi cuando salte salta menos
                        if (debeSaltar)
                            juego.botonPulsado(jugador2.sonidoSalto);
                    }
                }

                //Si alguna bala se choca desaparece y si es un jugador le quita vida
                for (Bala bala : balas) {
                    if (hanColisionado(contact, bala.idBala, "jugador1")) {
                        jugador1.setVida(jugador1.getVida() - bala.daño);
                        bala.impacto = true;
                        comprobarFinalizacion();
                    } else {
                        if (hanColisionado(contact, bala.idBala, "jugador2")) {
                            jugador2.setVida(jugador2.getVida() - bala.daño);
                            bala.impacto = true;
                            comprobarFinalizacion();
                        } else {
                            if (hanColisionado(contact, bala.idBala, "suelo")) {
                                bala.impacto = true;
                            } else {
                                if (hanColisionado(contact, bala.idBala, "limiteIzquierda")) {
                                    bala.impacto = true;
                                } else {
                                    if (hanColisionado(contact, bala.idBala, "limiteDerecha")) {
                                        bala.impacto = true;
                                    } else {
                                        for (Bala bala2 : balas) {
                                            if (!bala.impacto) {
                                                if (hanColisionado(contact, bala.idBala, bala2.idBala))
                                                    bala.impacto = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /**
             * Cuando se separan
             * @param contact el choque
             */
            @Override
            public void endContact(Contact contact) {
                if (hanColisionado(contact, "jugador1", "jugador2")) {
                    jugador1.fuerzaY = 10;   //Cuando se separaran salta con la fuerza antigua
                    jugador2.fuerzaY = 10;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

        gestorGestos = new GestorGestos(new GestureDetector.GestureAdapter(), escenario, fling, juego);
        gestorGestos.jugadorActual = jugador1;
        Gdx.input.setInputProcessor(gestorGestos);//Poder usar gestos y que funcionen botones del escenario

        fuente.getData().setScale(escalado04);

        tiempo = tiempoTurno;
        tiempoString = tiempo + "";

        timer = Timer.schedule(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       timer();
                                   }
                               }
                , 0, 0.200f
        );
    }

    /**
     * Carga los suelos dependiendo del mapa elegida el la pantalla de mapas
     *
     * @param indice el indice del mapa a utilizar
     * @return coleccion con los suelos creados
     */
    private ArrayList<Suelo> cargaSuelos(int indice) {
        ArrayList<Suelo> suelos = new ArrayList<Suelo>();
        boolean relleno = false;
        for (Vector2 punto : juego.mapas[indice].puntos) {
            mapa = juego.mapas[indice].nombre;
            relleno = false;
            if (punto.y > 0)
                relleno = true;
            Suelo suelo = new Suelo(mundo, juego.mapas[indice].cuadradoMapa, punto, juego, anchoPantalla, altoPantalla, relleno);
            suelo.fixture.setFriction(juego.mapas[indice].rozamiento);
            suelos.add(suelo);
            escenario.addActor(suelo);
        }
        return suelos;
    }

    /**
     * Carga el con las texturas seleccionadas, el arma seleccionada y lo coloca en el mundo
     *
     * @param selector el selector del arma y las texturas
     * @param turno    si tiene el turno
     * @param posicion la posisicon en la que se coloca
     * @return un nuevo jugador con esas propiedades
     */
    private Jugador cargaJugadore(SelectorPersonajeArma selector, boolean turno, Vector2 posicion) {
        String nombreJug = "dino";
        String arma = "armas/gun.png";
        String bala = "armas/bullet.png";
        String tipoBala = "pistola";
        int cantidadBalasJugador = 1;
        //datos para guardar y para coger las texturas
        switch (selector.indicePersonaje) {
            case 0:
                nombreJug = "dino";
                break;
            case 1:
                nombreJug = "dog";
                break;
        }
        //dependiendo del arma escogida cambia la cantidad de balas y las imagenes a mostrar de las mismas
        switch (selector.indiceArma) {
            case 0:
                arma = "armas/gun.png";
                bala = "armas/bullet.png";
                tipoBala = "pistola";
                cantidadBalasJugador = 4;
                break;
            case 1:
                arma = "armas/shotgun.png";
                bala = "armas/bullet.png";
                tipoBala = "uzi";
                cantidadBalasJugador = 6;
                break;
            case 2:
                arma = null;
                bala = "armas/bomb.png";
                tipoBala = "canon";
                cantidadBalasJugador = 1;
                break;
        }

        Texture texturaArma = null; //Para poder cargar un jugador sin arma
        try {
            texturaArma = juego.manager.get(arma, Texture.class);
        } catch (NullPointerException e) {
        }

        TexturasJugador texturas = cargaTexturas(nombreJug);
        Jugador jugador = new Jugador(mundo, texturas.parado, texturas.mov, texturas.salto, texturas.muerto, posicion,
                juego.manager.get(bala, Texture.class),
                texturaArma,
                juego, turno, Jugador.Movimiento.nada, tipoBala, cantidadBalasJugador);
        jugador.setVida(selector.personajes[selector.indicePersonaje].getVida());
        jugador.nombre = nombreJug;
        jugador.arma = arma;
        if (jugador.arma != null) {
            jugador.arma = jugador.arma.substring(jugador.arma.indexOf("/") + 1);
            jugador.arma = jugador.arma.substring(0, jugador.arma.indexOf(".png"));
        } else
            jugador.arma = "bomba";
        return jugador;
    }

    /**
     * Carga las texturas del jugador dependiendo del nombre del personaje
     *
     * @param nombre en nombre en el que estan las teturas, nombre del animal
     * @return un nuevo objeto con las texturas
     */
    private TexturasJugador cargaTexturas(String nombre) {
        TexturasJugador texturasJugador = new TexturasJugador();
        texturasJugador.parado[0] = juego.manager.get(nombre + "/Idle (1).png", Texture.class);
        texturasJugador.parado[1] = juego.manager.get(nombre + "/Idle (2).png", Texture.class);
        texturasJugador.parado[2] = juego.manager.get(nombre + "/Idle (3).png", Texture.class);
        texturasJugador.parado[3] = juego.manager.get(nombre + "/Idle (4).png", Texture.class);
        texturasJugador.parado[4] = juego.manager.get(nombre + "/Idle (5).png", Texture.class);
        texturasJugador.parado[5] = juego.manager.get(nombre + "/Idle (6).png", Texture.class);
        texturasJugador.parado[6] = juego.manager.get(nombre + "/Idle (7).png", Texture.class);
        texturasJugador.parado[7] = juego.manager.get(nombre + "/Idle (8).png", Texture.class);
        texturasJugador.parado[8] = juego.manager.get(nombre + "/Idle (9).png", Texture.class);
        texturasJugador.parado[9] = juego.manager.get(nombre + "/Idle (10).png", Texture.class);

        texturasJugador.mov[0] = juego.manager.get(nombre + "/Walk (1).png", Texture.class);
        texturasJugador.mov[1] = juego.manager.get(nombre + "/Walk (2).png", Texture.class);
        texturasJugador.mov[2] = juego.manager.get(nombre + "/Walk (3).png", Texture.class);
        texturasJugador.mov[3] = juego.manager.get(nombre + "/Walk (4).png", Texture.class);
        texturasJugador.mov[4] = juego.manager.get(nombre + "/Walk (5).png", Texture.class);
        texturasJugador.mov[5] = juego.manager.get(nombre + "/Walk (6).png", Texture.class);
        texturasJugador.mov[6] = juego.manager.get(nombre + "/Walk (7).png", Texture.class);
        texturasJugador.mov[7] = juego.manager.get(nombre + "/Walk (8).png", Texture.class);
        texturasJugador.mov[8] = juego.manager.get(nombre + "/Walk (9).png", Texture.class);
        texturasJugador.mov[9] = juego.manager.get(nombre + "/Walk (10).png", Texture.class);

        texturasJugador.salto[0] = juego.manager.get(nombre + "/Jump (3).png", Texture.class);
        texturasJugador.salto[1] = juego.manager.get(nombre + "/Jump (4).png", Texture.class);
        texturasJugador.salto[2] = juego.manager.get(nombre + "/Jump (5).png", Texture.class);
        texturasJugador.salto[3] = juego.manager.get(nombre + "/Jump (6).png", Texture.class);
        texturasJugador.salto[4] = juego.manager.get(nombre + "/Jump (7).png", Texture.class);
        texturasJugador.salto[5] = juego.manager.get(nombre + "/Jump (8).png", Texture.class);
        texturasJugador.salto[6] = juego.manager.get(nombre + "/Jump (9).png", Texture.class);
        texturasJugador.salto[7] = juego.manager.get(nombre + "/Jump (10).png", Texture.class);
        texturasJugador.salto[8] = juego.manager.get(nombre + "/Jump (11).png", Texture.class);

        texturasJugador.muerto[0] = juego.manager.get(nombre + "/Dead (1).png", Texture.class);
        texturasJugador.muerto[1] = juego.manager.get(nombre + "/Dead (2).png", Texture.class);
        texturasJugador.muerto[2] = juego.manager.get(nombre + "/Dead (3).png", Texture.class);
        texturasJugador.muerto[3] = juego.manager.get(nombre + "/Dead (4).png", Texture.class);
        texturasJugador.muerto[4] = juego.manager.get(nombre + "/Dead (5).png", Texture.class);
        texturasJugador.muerto[5] = juego.manager.get(nombre + "/Dead (6).png", Texture.class);
        texturasJugador.muerto[6] = juego.manager.get(nombre + "/Dead (7).png", Texture.class);
        texturasJugador.muerto[7] = juego.manager.get(nombre + "/Dead (8).png", Texture.class);

        return texturasJugador;
    }

    /**
     * Comprueba si se finaliza la partida
     * En ese caso espera dos segundos y cambia a la pantalla de finalizacion de partida cambiando
     * los frames a mostrar y tambien los datos de la partida para su posterior guardado
     */
    private void comprobarFinalizacion() {
        for (final Jugador jugador : jugadores) {
            if (!partidaAcabada) {
                if (!jugador.vivo) {
                    partidaAcabada = true;
                    if (jugador.fixture.getUserData().equals("jugador1"))
                        idGanador = 2;
                    else
                        idGanador = 1;
                    Gdx.input.setInputProcessor(null);
                    fling.clear();
                    //Cuando acaba la partida espera 2 segundo y ejecuta el siguiente codigo
                    escenario.addAction(Actions.sequence(Actions.delay(2), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            juego.finalizacionPartida.ganador = idGanador;
                            juego.finalizacionPartida.setTiempo(segundosPartida);
                            juego.finalizacionPartida.balas = balasUtilizadas;
                            juego.finalizacionPartida.jugadores = jugadores;
                            juego.finalizacionPartida.mapa = mapa;
                            if (jugadorActual.equals("jugador1"))
                                juego.finalizacionPartida.framesGanador = jugador1.frameParadoAv;
                            else
                                juego.finalizacionPartida.framesGanador = jugador2.frameParadoAv;
                            juego.pantallaJuego = new PantallaJuego(juego);
                            juego.setScreen(juego.finalizacionPartida);
                        }
                    })));
                }
            }
        }
    }

    /**
     * Tiemer que cambia de frame, de turno y los segundos que quedan
     */
    private void timer() {
        if (visible && !partidaAcabada) {
            contTimer++;
            if (contTimer > 5) {
                contTimer = 0;
                tiempo--;
                segundosPartida++;
                tiempoString = tiempo + "";
                if (tiempo <= 0) {
                    tiempo = 20;
                    InputEvent evento = new InputEvent();
                    evento.setType(InputEvent.Type.touchDown);
                    btCambioPersonaje.fire(evento);
                    evento.setType(InputEvent.Type.touchUp);
                    btCambioPersonaje.fire(evento);
                }
            }
            for (Jugador jugador : jugadores) {
                jugador.cambiaFrame();
            }
        }
    }

    /**
     * Renderiza el juego actual mostrando los actores, el texto y si se pulsa attras lanza la ventana de confirmacion
     * @param delta el tiempo desde la ultima ejecuion, no se usa porque box2d no depende de ese delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            timer.cancel();
            confirmacion();
        }
        //Si existe el movimiento fling
        if (fling.size() > 1) {
            formas.begin(ShapeRenderer.ShapeType.Line);
            formas.setColor(Color.BLACK);
            Vector2 inicio = null;

            Vector2 centroJugador = new Vector2(jugActual.getX() + jugActual.tamañoX * juego.PIXEL_METRO_X, jugActual.getY() + jugActual.tamañoY * juego.PIXEL_METRO_Y);
            Vector2 ptoFinal = fling.get(fling.size() - 1);
            float yy = altoPantalla - ptoFinal.y - centroJugador.y; //Calcula el angulo con el que apunta el jugador
            float xx = ptoFinal.x - centroJugador.x;
            angulo = (float) Math.atan2(yy, xx);
            jugActual.angulo = (float) Math.toDegrees(angulo);

            inicio = new Vector2(jugActual.getX() + jugActual.tamañoX * juego.PIXEL_METRO_X, jugActual.getY() + jugActual.tamañoY * juego.PIXEL_METRO_Y);

            if (inicio != null)
                formas.line(inicio.x, inicio.y, fling.get(fling.size() - 1).x, altoPantalla - fling.get(fling.size() - 1).y);
            formas.end();
        }

        escenario.act();
        mundo.step(delta, 6, 2); //Actualiza el mundo
        camera.update();
        if (juego.debug)        //Para poder ver los limites de box2d
            renderer.render(mundo, camera.combined);
        escenario.draw();

        for (int i = balas.size() - 1; i >= 0; i--) {
            if (balas.get(i).impacto) { //Se elimina despues del mundo step para evitar error al renderizar la bala a null
                balas.get(i).elimina();
                balas.get(i).remove();
                balas.remove(i);
            }
        }
        //Informacion de la partida
        batchTexto.begin();
        fuente.draw(batchTexto, juego.idiomas.get("jugador") + ": " + idActual, anchoPantalla / 3, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, tiempoString + "s", anchoPantalla / 3 * 2, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, jugador1.getVida() + "", anchoPantalla / 18, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, jugador2.getVida() + "", anchoPantalla - anchoPantalla / 12, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, juego.idiomas.get("balasRestantes") + ": " + jugActual.balasRestantes, anchoPantalla / 18, altoPantalla - altoPantalla / 10 * 2);
        batchTexto.end();
    }

    /**
     * Se ejecuta cuando la pantalla no esta visible
     */
    @Override
    public void hide() {
        super.hide();
        visible = false;
        //Primero los elimina del mundo y despues del escenario
        for (Suelo suelo : suelos) {
            suelo.elimina();
            suelo.remove();
        }
        jugador1.elimina();
        jugador1.remove();
        jugador2.elimina();
        jugador2.remove();

        btAtras.remove();
        btAdelante.remove();
        btSaltarAtras.remove();
        btSaltarAdelante.remove();

        btCambioPersonaje.remove();
        btDisparo.remove();

        limiteMapa.elimina();
        limiteMapa.remove();

        Gdx.input.setInputProcessor(null);
    }

    /**
     * Cuando se cierra la aplicacion se elmina el escenario y el mundo
     */
    @Override
    public void dispose() {
        escenario.dispose();
        mundo.dispose();
    }
}
