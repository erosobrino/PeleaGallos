package com.peleadegallos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;


/**
 * The type Juego principal.
 */
public class JuegoPrincipal extends Game {
    /**
     * The Menu inicio.
     */
    MenuInicio menuInicio;
    /**
     * The Opciones.
     */
    Opciones opciones;
    /**
     * The Info.
     */
    AcercaDe info;
    /**
     * The Pantalla juego.
     */
    PantallaJuego pantallaJuego;
    /**
     * The Finalizacion partida.
     */
    FinalizacionPartida finalizacionPartida;
    /**
     * The Selector personaje arma.
     */
    SelectorPersonajeArma selectorPersonajeArma;
    /**
     * The Selector personaje arma 2.
     */
    SelectorPersonajeArma selectorPersonajeArma2;
    /**
     * The Selector mapa.
     */
    SelectorMapa selectorMapa;
    /**
     * The Logros.
     */
    PantallaLogros logros;
    /**
     * The Datos guardados.
     */
    ObjetoGuardado datosGuardados = new ObjetoGuardado();

    /**
     * The Animales.
     */
    String[] animales = new String[]{"dino", "dog"};
    /**
     * The Acciones.
     */
    String[] acciones = new String[]{"Idle", "Walk", "Jump", "Dead"};
    /**
     * The Cantidad acciones.
     */
    int[] cantidadAcciones = new int[]{10, 10, 12, 8};
    /**
     * The Mapas.
     */
    Mapa[] mapas = new Mapa[2];

    /**
     * The Idiomas.
     */
    I18NBundle idiomas;

    /**
     * The Manager.
     */
    AssetManager manager;
    /**
     * The Musica encendida.
     */
    public boolean musicaEncendida;
    /**
     * The Vibracion encendida.
     */
    public boolean vibracionEncendida;
    /**
     * The Tiempo vibrar.
     */
    public int tiempoVibrar = 50;
    /**
     * The Debug.
     */
    public boolean debug = true;
    /**
     * The Modo desarrollo.
     */
    public boolean modoDesarrollo = true;
    /**
     * The Pixel metro x.
     */
    float PIXEL_METRO_X;  //Escala para box2s
    /**
     * The Pixel metro y.
     */
    float PIXEL_METRO_Y;  //Escala para box2s
    /**
     * The Metros x.
     */
    float metrosX = 16;
    /**
     * The Metros y.
     */
    float metrosY = 9;

    /**
     * Boton pulsado.
     *
     * @param sonido the sonido
     */
    public void botonPulsado(Sound sonido) {
        if (vibracionEncendida)
            Gdx.input.vibrate(tiempoVibrar);
        if (musicaEncendida && sonido != null) {
            sonido.play();
        }
    }

    /**
     * Boton pulsado musica.
     *
     * @param musica the musica
     */
    public void botonPulsadoMusica(Music musica) {
        if (vibracionEncendida)
            Gdx.input.vibrate(tiempoVibrar);
        if (musicaEncendida) {
            musica.setLooping(true);
            musica.play();
        }
    }

    /**
     * The Preferences.
     */
    Preferences preferences;
    /**
     * The Adaptador codigo android.
     */
    AdaptadorCodigoAndroid adaptadorCodigoAndroid;

    /**
     * Sets adaptador notificaciones.
     *
     * @param handler the handler
     */
    public void setAdaptadorNotificaciones(AdaptadorCodigoAndroid handler) {
        this.adaptadorCodigoAndroid = handler;
    }

    @Override
    public void create() {
        preferences = Gdx.app.getPreferences("PeleaDeGallos");
        musicaEncendida = preferences.getBoolean("musica", true);
        vibracionEncendida = preferences.getBoolean("vibracion", true);
        modoDesarrollo = preferences.getBoolean("desarrollo", true);
        cargaDatos();

        idiomas = I18NBundle.createBundle(Gdx.files.internal("locale/locale"));

        manager = new AssetManager();                         //Carga las imagenes y audio
        manager.load("suelo.png", Texture.class);    //Imagen del suelo

        manager.load("nubes/nube1.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG2.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG14.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG18.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG27.png", Texture.class);    //Imagen nube
        manager.load("Music.wav", Music.class);            //Musica
        manager.load("iconos/home.png", Texture.class);
        manager.load("iconos/bell.png", Texture.class);
        manager.load("iconos/bell-slash.png", Texture.class);
        manager.load("iconos/info-circle.png", Texture.class);
        manager.load("iconos/volume-mute.png", Texture.class);
        manager.load("iconos/volume-up.png", Texture.class);
        manager.load("iconos/flechaArriba.png", Texture.class);
        manager.load("iconos/flechaAbajo.png", Texture.class);
        manager.load("iconos/flechaDerecha.png", Texture.class);
        manager.load("iconos/flechaIzquierda.png", Texture.class);
        manager.load("iconos/disparo.png", Texture.class);
        manager.load("iconos/developer.png", Texture.class);
        manager.load("iconos/developerNo.png", Texture.class);

        for (int i = 0; i < animales.length; i++) {
            for (int j = 0; j < acciones.length; j++) {
                for (int k = 1; k <= cantidadAcciones[j]; k++) {
                    manager.load(animales[i] + "/" + acciones[j] + " (" + k + ").png", Texture.class);
                }
            }
        }

        manager.load("sonidos/sonidoClick.mp3", Sound.class);
        manager.load("sonidos/sonidoCanon.mp3", Sound.class);
        manager.load("sonidos/sonidoSalto.mp3", Sound.class);
        manager.load("sonidos/sonidoEscopeta.mp3", Sound.class);
        manager.load("sonidos/sonidoAndar.mp3", Music.class);

        manager.load("armas/bullet.png", Texture.class);
        manager.load("armas/gun.png", Texture.class);
        manager.load("armas/shotgun.png", Texture.class);
        manager.load("armas/bomb.png", Texture.class);

        manager.load("mapas/mapa1.png", Texture.class);
        manager.load("mapas/mapa2.png", Texture.class);
        manager.load("mapas/mapaCuadrado1.png", Texture.class);
        manager.load("mapas/mapaCuadrado2.png", Texture.class);

        manager.load("skin/fuente200.fnt", BitmapFont.class);    //Fuente

        manager.finishLoading();                             //Espera a que acabe de cargar

        Personaje[] personajes = new Personaje[animales.length];
        for (int i = 0; i < personajes.length; i++) {
            personajes[i] = new Personaje(manager.get(animales[i] + "/Idle (1).png", Texture.class), 100, 2, animales[i]);
            if (animales[i].equals("dog"))
                personajes[i].setVida(80);
        }

        Arma[] armas = new Arma[3];
        armas[0] = new Arma(manager.get("armas/gun.png", Texture.class), 10, 4, 3, "pistola");
        armas[1] = new Arma(manager.get("armas/shotgun.png", Texture.class), 10, 6, 2, "uzi");
        armas[2] = new Arma(manager.get("armas/bomb.png", Texture.class), 2, 1, 10, "canon");

        mapas[0] = new Mapa();
        mapas[0].mapa = manager.get("mapas/mapa1.png", Texture.class);
        mapas[0].cuadradoMapa = manager.get("mapas/mapaCuadrado1.png", Texture.class);
        mapas[0].puntos[0] = new Vector2(0, 0);
        mapas[0].puntos[1] = new Vector2(2, 0);
        mapas[0].puntos[2] = new Vector2(4, 0.5f);
        mapas[0].puntos[3] = new Vector2(6, 0.75f);
        mapas[0].puntos[4] = new Vector2(8, 0.75f);
        mapas[0].puntos[5] = new Vector2(10, 0.5f);
        mapas[0].puntos[6] = new Vector2(12, 0);
        mapas[0].puntos[7] = new Vector2(14, 0);
        mapas[0].rozamiento = 0.7f;
        mapas[0].nombre = "tierra";

        mapas[1] = new Mapa();
        mapas[1].mapa = manager.get("mapas/mapa2.png", Texture.class);
        mapas[1].cuadradoMapa = manager.get("mapas/mapaCuadrado2.png", Texture.class);
        mapas[1].puntos[0] = new Vector2(0, 0);
        mapas[1].puntos[1] = new Vector2(2, 0.65f);
        mapas[1].puntos[2] = new Vector2(4, 0);
        mapas[1].puntos[3] = new Vector2(6, -0.5f);
        mapas[1].puntos[4] = new Vector2(8, -0.5f);
        mapas[1].puntos[5] = new Vector2(10, 0f);
        mapas[1].puntos[6] = new Vector2(12, 0.65f);
        mapas[1].puntos[7] = new Vector2(14, 0);
        mapas[1].rozamiento = 0.5f;
        mapas[0].nombre = "nieve";

        menuInicio = new MenuInicio(this);
        opciones = new Opciones(this);
        info = new AcercaDe(this);
        pantallaJuego = new PantallaJuego(this);
        finalizacionPartida = new FinalizacionPartida(this);

        datosGuardados.addRecord(new Record("dino", "dog", "nieve", "uzi", "gun", 20, 60, 1));
        datosGuardados.addRecord(new Record("dino", "dog", "nieve", "uzi", "gun", 20, 60, 1));
        datosGuardados.addRecord(new Record("dino", "dog", "nieve", "uzi", "gun", 20, 60, 1));
        datosGuardados.addRecord(new Record("dino", "dog", "nieve", "uzi", "gun", 20, 60, 1));
        datosGuardados.addRecord(new Record("dino", "dog", "nieve", "uzi", "gun", 20, 60, 1));

        guardaDatos();
//        borraDatos();

        selectorPersonajeArma = new SelectorPersonajeArma(this, personajes, armas, 1);
        selectorPersonajeArma2 = new SelectorPersonajeArma(this, personajes, armas, 2);
        selectorMapa = new SelectorMapa(this, mapas, 3);
        setScreen(menuInicio);                             //Cambia al menu de inicio
    }

    private void cargaDatos() {
        try {
            if (Gdx.files.local("records.json").exists()) {
                FileHandle in = Gdx.files.local("records.json");
                Json json = new Json();
                datosGuardados = json.fromJson(ObjetoGuardado.class, in.readString());
            } else
                datosGuardados = new ObjetoGuardado();
        } catch (RuntimeException e) {
        }
    }

    /**
     * Guarda datos.
     */
    public void guardaDatos() {
        try {
            Json json = new Json();
            FileHandle out = Gdx.files.local("records.json");
            out.writeString(json.toJson(datosGuardados), false);
            notificacion();
        } catch (RuntimeException e) {
        }
    }

    /**
     * Borra datos.
     */
    public void borraDatos() {
        if (Gdx.files.local("records.json").exists()) {
            try {
                Gdx.files.local("records.json").delete();
                cargaDatos();
            } catch (Exception e) {
            }
        }
    }

    private void notificacion() {
        System.out.println("adfsadgafegasd");
        if (datosGuardados.getBalas() > 80 && datosGuardados.getBalas() < 180)
            adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("balas") + ": " + datosGuardados.getBalas());
        if (datosGuardados.getTiempo() > 60 && datosGuardados.getTiempo() < 120)
            adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("tiempoTotal") + ": " + String.format("%02d:%02d", datosGuardados.getTiempo()/ 60, datosGuardados.getTiempo()% 60));
        if (datosGuardados.getRecords().size() == 1) {
            adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("partidas") + ": " + datosGuardados.getRecords().size());
        } else {
            if (datosGuardados.getRecords().size() ==10) {
                adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("partidas") + ": " + datosGuardados.getRecords().size());
            } else {
                if (datosGuardados.getRecords().size() % 100 == 0) {
                    adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("partidas") + ": " + datosGuardados.getRecords().size());
                }
            }
        }
    }
}
