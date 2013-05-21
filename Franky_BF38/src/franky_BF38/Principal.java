package franky_BF38;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Principal extends Applet implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	enum Estado {
		Jugando, Muerto, Pausa, Error, Ganador, Menu
	}

	public static Jugador jugador;
	public static Pacifista PX_1, PX_2, PX_3, PX_4, PX_5;
	public static int score = 0;
	private Font font = new Font(getName(), Font.BOLD, 30);
	private Image imagen, franky, franky_agachado, franky_salto, fondo,
			imagen_actual, pacifista;
	public static Image superior, inferior, lateral_izquierda, lateral_derecha,
			interior;
	private Graphics graficos;
	private URL direccion;
	private static Fondo fondo1, fondo2;
	private AudioClip disparo, tema;
	public static Estado modo = Estado.Menu;
	private boolean flag = false;
	private boolean mute = false;
	private int temp = 0, temp2 = 0;

	private ArrayList<Plataforma> plataformas = new ArrayList<Plataforma>();

	/**
	 * Mètode <b>init</b> de la superclass Applet. És el primer mètode que es crida en el cicle de vida de l'aplicació <br/>
	 * i on s'inicia la configuració inicial.
	 */
	@Override
	public void init() {
		setSize(800, 480); // Tamany d'ample i alçada del Applet
		setBackground(Color.BLACK); // Color de fons
		setFocusable(true); // L'Applet capta l'atenció del nostre cursor; sense
							// aquesta linia, seria necessari clicar prèviament
							// per jugar
		addKeyListener(this); // Permet la captació d'events de teclat
								// (KeyListener)
		try {
			direccion = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		franky = getImage(direccion, "imagenes/franky_estatico.png");
		franky_agachado = getImage(direccion, "imagenes/franky_sentado.png");
		franky_salto = getImage(direccion, "imagenes/franky_salto.png");
		imagen_actual = franky;

		pacifista = getImage(direccion, "imagenes/pacifista1.png");

		fondo = getImage(direccion, "imagenes/background.png");

		interior = getImage(direccion, "imagenes/plataforma_bot.png");
		superior = getImage(direccion, "imagenes/plataforma_top.png");
		inferior = getImage(direccion, "imagenes/plataforma_bot.png");
		lateral_izquierda = getImage(direccion, "imagenes/plataforma_left.png");
		lateral_derecha = getImage(direccion, "imagenes/plataforma_right.png");

		disparo = getAudioClip(getCodeBase(), "audio/miss.au");
		tema = getAudioClip(getCodeBase(), "audio/thestrongest.au");
	}

	/**
	 * Mètode <b>start</b> de la superclass Applet. Es crida quan l'Applet ha carregat la configuració inicial.
	 */
	@Override
	public void start() {
		fondo1 = new Fondo(0, 0);
		fondo2 = new Fondo(2160, 0);
		jugador = new Jugador();

		try {
			cargaMapa("http://zeytpruebas.prosopin.com/map2.txt");
		} catch (IOException e) {
			e.printStackTrace();
			modo = Estado.Error;
		}

		PX_1 = new Pacifista(520, 353);
		PX_2 = new Pacifista(1200, 353);
		PX_3 = new Pacifista(2900, 353);
		PX_4 = new Pacifista(3400, 353);
		PX_5 = new Pacifista(4800, 353);

		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Funció que rep un mapa com a paràmetre i crea un vector de plataformes.
	 * <p>S'omiteixen totes les linies començades en "!" (comentaris) i es llegeixen 12 que són les que pertanyen al mapa.<br/>
	 * Cada número llegit simbolitza un tipus de plataforma diferent. Observar llegenda en l'interior del mapa.</p>
	 * @param mapa Rep una ruta absoluta on es troba el mapa a dibuixar
	 * @throws IOException
	 */
	private void cargaMapa(String mapa) throws IOException {
		ArrayList<String> lineas = new ArrayList<String>();
		int W = 0;
		URL dataStream = new URL(mapa);
		BufferedReader in = new BufferedReader(new InputStreamReader(dataStream.openStream()));
		while (true) {
			String linia = in.readLine();
			if (linia == null) {
				in.close();
				break;
			}
			if (!linia.startsWith("!")) {
				lineas.add(linia);
				W = Math.max(W, linia.length());
			}
		}

		for (int j = 0; j < 12; j++) { // 12
			String linia2 = (String) lineas.get(j);
			for (int i = 0; i < W; i++) {
				if (i < linia2.length()) {
					char ch = linia2.charAt(i);
					Plataforma t = new Plataforma(i, j,	Character.getNumericValue(ch));
					plataformas.add(t);
				}
			}
		}
	}

	/**
	 * Funció encarregada de la gestió del fil que controla l'aplicació.
	 * <p>Totes les activitats animades i en moviment passen per aquí, simulant una sensació real de desplaçament.</p>
	 */
	@Override
	public void run() {
		if (modo == Estado.Menu) {
			while (true) {
				if (modo != Estado.Pausa && modo != Estado.Menu
						&& modo != Estado.Ganador) {
					try {
						if (score == 25 && flag == false) {
							temp = jugador.getVida();
							siguienteNivel();
						}
						if (score == 40) {
							temp2 = jugador.getVida();
							modo = Estado.Ganador;
						}
						jugador.update();
						if (jugador.isSalto()) {
							imagen_actual = franky_salto;
						} else if (jugador.isSalto() == false
								&& jugador.isAgachado() == false) {
							imagen_actual = franky;
						}

						ArrayList<Misiles> municion = jugador.getMisiles();
						for (int i = 0; i < municion.size(); i++) {
							Misiles m = (Misiles) municion.get(i);
							if (m.isVisible() == true) {
								m.update();
							} else {
								municion.remove(i);
							}
						}

						ArrayList<Misiles_PX> municion_px = PX_1
								.getMisiles_PX();
						for (int i = 0; i < municion_px.size(); i++) {
							Misiles_PX m = (Misiles_PX) municion_px.get(i);
							if (m.isVisible() == true) {
								m.update();
							} else {
								municion_px.remove(i);
							}
						}

						ArrayList<Misiles_PX> municion_px2 = PX_2
								.getMisiles_PX();
						for (int i = 0; i < municion_px2.size(); i++) {
							Misiles_PX m = (Misiles_PX) municion_px2.get(i);
							if (m.isVisible() == true) {
								m.update();
							} else {
								municion_px2.remove(i);
							}
						}

						ArrayList<Misiles_PX> municion_px3 = PX_3
								.getMisiles_PX();
						for (int i = 0; i < municion_px3.size(); i++) {
							Misiles_PX m = (Misiles_PX) municion_px3.get(i);
							if (m.isVisible() == true) {
								m.update();
							} else {
								municion_px3.remove(i);
							}
						}

						ArrayList<Misiles_PX> municion_px4 = PX_4
								.getMisiles_PX();
						for (int i = 0; i < municion_px4.size(); i++) {
							Misiles_PX m = (Misiles_PX) municion_px4.get(i);
							if (m.isVisible() == true) {
								m.update();
							} else {
								municion_px4.remove(i);
							}
						}

						ArrayList<Misiles_PX> municion_px5 = PX_5
								.getMisiles_PX();
						for (int i = 0; i < municion_px5.size(); i++) {
							Misiles_PX m = (Misiles_PX) municion_px5.get(i);
							if (m.isVisible() == true) {
								m.update();
							} else {
								municion_px5.remove(i);
							}
						}

						updatePlataformas();

						PX_1.update();
						PX_2.update();
						PX_3.update();
						PX_4.update();
						PX_5.update();

						fondo1.update();
						fondo2.update();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					PX_1.step();
					PX_2.step();
					PX_3.step();
					PX_4.step();
					PX_5.step();

					repaint(); // Crida a paint() i dibuixa/actualitza objectes
								// en
								// pantalla
				}
				try {
					Thread.sleep(17); // 17 milisegons equivalen a 60FPS
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (jugador.getCentro_Y() > 500) { // Si es cau el jugador
					modo = Estado.Muerto;
				}
			}
		}
	}

	/**
	 * Funció encarregada del <i>double buffering</i> per evitar esdeveniments com el <i>flickering</i> o els talls.
	 */
	@Override
	public void update(Graphics g) { // Funció pel "double buffering"
		if (imagen == null) {
			imagen = createImage(this.getWidth(), this.getHeight());
			graficos = imagen.getGraphics();
		}

		graficos.setColor(getBackground());
		graficos.fillRect(0, 0, getWidth(), getHeight());
		graficos.setColor(getForeground());
		paint(graficos);

		g.drawImage(imagen, 0, 0, this);

	}

	/**
	 * Funció encarregada del dibuixat d'objectes.
	 * <p>Aquí es gestiona tota la il·lustració de l'aplicació; des dels personatges fins a les bales.</p>
	 * @param g Graphics
	 */
	@Override
	public void paint(Graphics g) {
		if (modo == Estado.Jugando) {
			// S'ha de respectar l'ordre de linies a l'hora de pintar; en el
			// nostre cas, si volem que en Franky estigui sobre el fons, el fons
			// es dibuixa primer
			g.drawImage(fondo, fondo1.getFondo_X(), fondo1.getFondo_Y(), this);
			g.drawImage(fondo, fondo2.getFondo_X(), fondo2.getFondo_Y(), this);
			pintaPlataformas(g);

			ArrayList<Misiles> municion = jugador.getMisiles();
			for (int i = 0; i < municion.size(); i++) {
				Misiles m = (Misiles) municion.get(i);
				g.setColor(Color.RED);
				g.fillRect(m.getX(), m.getY(), 10, 5);
			}

			ArrayList<Misiles_PX> municion_px = PX_1.getMisiles_PX();
			for (int i = 0; i < municion_px.size(); i++) {
				Misiles_PX m = (Misiles_PX) municion_px.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(m.getX(), m.getY(), 10, 5);
			}

			ArrayList<Misiles_PX> municion_px2 = PX_2.getMisiles_PX();
			for (int i = 0; i < municion_px2.size(); i++) {
				Misiles_PX m = (Misiles_PX) municion_px2.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(m.getX(), m.getY(), 10, 5);
			}

			ArrayList<Misiles_PX> municion_px3 = PX_3.getMisiles_PX();
			for (int i = 0; i < municion_px3.size(); i++) {
				Misiles_PX m = (Misiles_PX) municion_px3.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(m.getX(), m.getY(), 10, 5);
			}

			ArrayList<Misiles_PX> municion_px4 = PX_4.getMisiles_PX();
			for (int i = 0; i < municion_px4.size(); i++) {
				Misiles_PX m = (Misiles_PX) municion_px4.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(m.getX(), m.getY(), 10, 5);
			}

			ArrayList<Misiles_PX> municion_px5 = PX_5.getMisiles_PX();
			for (int i = 0; i < municion_px5.size(); i++) {
				Misiles_PX m = (Misiles_PX) municion_px5.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(m.getX(), m.getY(), 10, 5);
			}

			g.drawImage(pacifista, PX_1.getCentro_X() - 58,
					PX_1.getCentro_Y() - 58, this);
			g.drawImage(pacifista, PX_2.getCentro_X() - 58,
					PX_2.getCentro_Y() - 58, this);
			g.drawImage(pacifista, PX_3.getCentro_X() - 58,
					PX_3.getCentro_Y() - 58, this);
			g.drawImage(pacifista, PX_4.getCentro_X() - 58,
					PX_4.getCentro_Y() - 58, this);
			g.drawImage(pacifista, PX_5.getCentro_X() - 58,
					PX_5.getCentro_Y() - 58, this);

			g.drawImage(imagen_actual, jugador.getCentro_X() - 61,
					jugador.getCentro_Y() - 63, this);

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
			g.drawString(Integer.toString(jugador.getVida()), 25, 30);
			if (flag != true)
				g.drawString("LEVEL 1", 400, 30);
			else
				g.drawString("LEVEL 2", 400, 30);
		} else if (modo == Estado.Muerto) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER: ¿CONTINUE?", 300, 240);
			g.drawString("Press R to play again", 300, 270);
			tema.stop();
		} else if (modo == Estado.Error) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("ERROR LOADING MAP", 360, 240);
		} else if (modo == Estado.Ganador) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("YOU WON!", 360, 240);
			g.drawString("Final score: " + Integer.toString(score + temp2),
					300, 270);
			g.drawString("Press R to play again", 300, 300);
			tema.stop();
		} else if (modo == Estado.Menu) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Press Enter to start playing", 360, 240);
		}
	}

	/**
	 * Funció que actualitza la situació de les plataformes cada 17 milisegons.
	 */
	private void updatePlataformas() {
		for (int i = 0; i < plataformas.size(); i++) {
			Plataforma t = (Plataforma) plataformas.get(i);
			t.update();
		}
	}

	/**
	 * Funció que dibuixa les plataformes creades en el vector prèviament.
	 * <p>Aquesta funció ha de ser invocada dins del mètode <b>Paint</b>.</p>
	 * @param g Graphics
	 */
	private void pintaPlataformas(Graphics g) {
		for (int i = 0; i < plataformas.size(); i++) {
			Plataforma t = (Plataforma) plataformas.get(i);
			g.drawImage(t.getPlat_Imagen(), t.getPlataforma_X(), t.getPlataforma_Y(), this);
		}
	}

	/**
	 * Funció que construeix una nova partida del joc.
	 * <p>S'inicialitzen tots els objectes i variables que intervenen en l'activitat típica de l'aplicació <br/>
	 * i es neteja el vector de plataformes per recrear-les novament.
	 * @throws IOException
	 */
	private void nuevaPartida() {
		flag = false;
		score = 0;
		modo = Estado.Jugando;
		fondo1 = new Fondo(0, 0);
		fondo2 = new Fondo(2160, 0);
		fondo1.setVelocidad_X(0);
		fondo2.setVelocidad_X(0);

		jugador = new Jugador();
		jugador.setCentro_X(100);
		jugador.setCentro_Y(382);
		jugador.setVelocidad_X(0);
		jugador.setVelocidad_Y(0);

		plataformas.clear();

		try {
			cargaMapa("http://zeytpruebas.prosopin.com/map1.txt");
		} catch (IOException e) {
			e.printStackTrace();
			modo = Estado.Error;
		}

		PX_1 = new Pacifista(520, 353);
		PX_2 = new Pacifista(1200, 353);
		PX_3 = new Pacifista(2900, 353);
		PX_4 = new Pacifista(3400, 353);
		PX_5 = new Pacifista(4800, 353);

		tema.loop();
		repaint();
	}

	/**
	 * Funció que passa al següent mapa/nivell. Igual que la funció anterior, s'inicialitzen novament objectes i variables <br/>
	 * que intervenen en l'activitat típica de l'aplicació.
	 * @throws IOException
	 */
	private void siguienteNivel() {
		flag = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		modo = Estado.Jugando;
		fondo1 = new Fondo(0, 0);
		fondo2 = new Fondo(2160, 0);
		fondo1.setVelocidad_X(0);
		fondo2.setVelocidad_X(0);

		jugador = new Jugador();
		jugador.setVida(temp);
		jugador.setCentro_X(100);
		jugador.setCentro_Y(382);
		jugador.setVelocidad_X(0);
		jugador.setVelocidad_Y(0);

		plataformas.clear();

		try {
			cargaMapa("http://zeytpruebas.prosopin.com/map2.txt");
		} catch (IOException e) {
			e.printStackTrace();
			modo = Estado.Error;
		}

		PX_1 = new Pacifista(520, 353);
		PX_2 = new Pacifista(2250, 353);
		PX_3 = new Pacifista(3800, 353);
	}

	/**
	 * Mètode <b>stop</b> de la superclass Applet. Es crida quan l'Applet es para.
	 */
	@Override
	public void stop() {
		tema.stop();
	}

	/**
	 * Mètode <b>destroy</b> de la superclass Applet. Es crida quan l'Applet és destruïda.
	 */
	@Override
	public void destroy() {
		modo = Estado.Menu;
		tema.stop();
	}

	/**
	 * Funció invocada quan es capta un event de teclat. Actua en funció de la programació especificada per a cada tecla.
	 * @param e Un event de teclat (tecla)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			imagen_actual = franky_agachado;
			if (jugador.isSalto() == false) {
				jugador.setAgachado(true);
				jugador.setVelocidad_X(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			jugador.moverIzquierda();
			jugador.setMovimiento_Izquierda(true);
			break;

		case KeyEvent.VK_RIGHT:
			jugador.moverDerecha();
			jugador.setMovimiento_Derecha(true);
			break;

		case KeyEvent.VK_SPACE:
			jugador.saltar();
			break;

		case KeyEvent.VK_CONTROL:
			if (jugador.isAgachado() == false && jugador.isSalto() == false) {
				jugador.disparar();
				jugador.setPuedeDisparar(false);
			}
			break;

		case KeyEvent.VK_ESCAPE:
			if (modo == Estado.Jugando)
				modo = Estado.Pausa;
			else if (modo == Estado.Pausa) {
				modo = Estado.Jugando;
				repaint();
			}
			break;

		case KeyEvent.VK_R:
			if (modo == Estado.Muerto || modo == Estado.Ganador)
				nuevaPartida();
			break;

		case KeyEvent.VK_M:
			if (!mute) {
				tema.stop();
				mute = true;
			} else {
				tema.play();
				mute = false;
			}
			break;

		case KeyEvent.VK_ENTER:
			if (modo == Estado.Menu)
				nuevaPartida();
			break;
		}
	}

	/**
	 * Funció invocada quan es deixa de captar un event de teclat. Actua en funció de la programació especificada per a cada tecla.
	 * @param e Un event de teclat (tecla)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			imagen_actual = franky;
			jugador.setAgachado(false);
			break;

		case KeyEvent.VK_LEFT:
			jugador.stopIzquierda();
			break;

		case KeyEvent.VK_RIGHT:
			jugador.stopDerecha();
			break;

		case KeyEvent.VK_CONTROL:
			disparo.play();
			jugador.setPuedeDisparar(true);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static Fondo getFondo1() {
		return fondo1;
	}

	public static Fondo getFondo2() {
		return fondo2;
	}

	public static Jugador getJugador() {
		return jugador;
	}
}
