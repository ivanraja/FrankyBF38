package franky_BF38;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Jugador {
	final int VELOCIDAD_SALTO = -15;
	final int VELOCIDAD_MOVIMIENTO = 5;

	private int centro_X = 100;
	private int centro_Y = 382;
	private boolean salto = false;
	private boolean movimiento_Izquierda = false;
	private boolean movimiento_Derecha = false;
	private boolean agachado = false;
	private boolean puedeDisparar = true;

	private int velocidad_X = 0;
	private int velocidad_Y = 0;

	private Fondo fondo1 = Principal.getFondo1();
	private Fondo fondo2 = Principal.getFondo2();

	public int vida = 10;

	public Rectangle rect = new Rectangle(0, 0, 0, 0);
	public Rectangle rect2 = new Rectangle(0, 0, 0, 0);
	public Rectangle rect3 = new Rectangle(0, 0, 0, 0);
	public Rectangle rect4 = new Rectangle(0, 0, 0, 0);
	public Rectangle ref = new Rectangle(0, 0, 0, 0);
	public Rectangle pie_izquierdo = new Rectangle(0, 0, 0, 0);
	public Rectangle pie_derecho = new Rectangle(0, 0, 0, 0);

	private ArrayList<Misiles> municion = new ArrayList<Misiles>();

	/**
	 * Funció que actualitza el posicionament del jugador.
	 * <p>Gestiona moviments laterals i de salt. També defineix un espai de 200 píxels on el jugador té llibertat <br/>
	 * de moviment; en cas de passar-los, es rota el fons de pantalla i el moviment del jugador queda estàtic.</p>
	 */
	public void update() {
		// Moviment cap a l'esquerra
		if (velocidad_X < 0) {
			centro_X += velocidad_X;
		}

		// No fem rotació de fons de pantalla si no hi ha moviment o és cap a
		// l'esquerra
		if (velocidad_X == 0 || velocidad_X < 0) {
			try {
				fondo1.setVelocidad_X(0);
				fondo2.setVelocidad_X(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Si el jugador està dins dels primers 200 pixels de pantalla, es mou.
		if (centro_X <= 200 && velocidad_X > 0) {
			centro_X += velocidad_X;
		}

		// Si el jugador excedeix els primers 200 pixels, el fons de pantalla es
		// mou.

		if (velocidad_X > 0 && centro_X > 200) {
			fondo1.setVelocidad_X(-VELOCIDAD_MOVIMIENTO / 5);
			fondo2.setVelocidad_X(-VELOCIDAD_MOVIMIENTO / 5);
		}

		// Actualització vertical (Y)
		centro_Y += velocidad_Y;

		// Salt del jugador
		velocidad_Y += 1;
		if (velocidad_Y > 3) {
			salto = true;
		}

		// Evitem que el jugador vagi més enllà de de la coordenada X(0) i el
		// reposicionem a X=61
		if (centro_X + velocidad_X <= 60) {
			centro_X = 61;
		}

		rect.setRect(centro_X - 34, centro_Y - 63, 68, 63);
		rect2.setRect(rect.getX(), rect.getY() + 63, 68, 64);
		rect3.setRect(rect.getX() - 18, rect.getY() + 32, 26, 20); // 26
		rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
		ref.setRect(centro_X - 110, centro_Y - 110, 180, 180);
		pie_izquierdo.setRect(centro_X - 50, centro_Y + 20, 50, 15);
		pie_derecho.setRect(centro_X, centro_Y + 20, 50, 15);
	}

	/**
	 * Funció per moure el personatge a la dreta.
	 */
	public void moverDerecha() {
		if (agachado == false) {
			velocidad_X = VELOCIDAD_MOVIMIENTO;
		}
	}

	/**
	 * Funció per moure el personatge a l'esquerra.
	 */
	public void moverIzquierda() {
		if (agachado == false) {
			velocidad_X = -VELOCIDAD_MOVIMIENTO;
		}
	}

	/**
	 * Funció booleana per parar el moviment cap a l'esquerra.
	 */
	public void stopIzquierda() {
		setMovimiento_Izquierda(false);
		stop();
	}

	/**
	 * Funció booleana per parar el moviment cap a la dreta.
	 */
	public void stopDerecha() {
		setMovimiento_Derecha(false);
		stop();
	}

	/**
	 * Funció per parar el moviment total del personatge..
	 */
	public void stop() {
		if (isMovimiento_Derecha() == false
				&& isMovimiento_Izquierda() == false) {
			velocidad_X = 0;
		}

		if (isMovimiento_Derecha() == false && isMovimiento_Izquierda() == true) {
			moverIzquierda();
		}

		if (isMovimiento_Derecha() == true && isMovimiento_Izquierda() == false) {
			moverDerecha();
		}
	}

	/**
	 * Funció per saltar amb el personatge.
	 */
	public void saltar() {
		if (salto == false) {
			velocidad_Y = VELOCIDAD_SALTO;
			salto = true;
		}
	}

	/**
	 * Funció per disparar amb el personatge.
	 */
	public void disparar() {
		if (puedeDisparar) {
			Misiles m = new Misiles(centro_X + 50, centro_Y - 25);
			municion.add(m);
		}
	}

	public ArrayList<Misiles> getMisiles() {
		return municion;
	}

	public int getCentro_X() {
		return centro_X;
	}

	public int getCentro_Y() {
		return centro_Y;
	}

	public boolean isSalto() {
		return salto;
	}

	public int getVelocidad_X() {
		return velocidad_X;
	}

	public int getVelocidad_Y() {
		return velocidad_Y;
	}

	public void setCentro_X(int centro_X) {
		this.centro_X = centro_X;
	}

	public void setCentro_Y(int centro_Y) {
		this.centro_Y = centro_Y;
	}

	public void setSalto(boolean salto) {
		this.salto = salto;
	}

	public void setVelocidad_X(int velocidad_X) {
		this.velocidad_X = velocidad_X;
	}

	public void setVelocidad_Y(int velocidad_Y) {
		this.velocidad_Y = velocidad_Y;
	}

	public boolean isMovimiento_Izquierda() {
		return movimiento_Izquierda;
	}

	public boolean isMovimiento_Derecha() {
		return movimiento_Derecha;
	}

	public boolean isAgachado() {
		return agachado;
	}

	public void setMovimiento_Izquierda(boolean movimiento_Izquierda) {
		this.movimiento_Izquierda = movimiento_Izquierda;
	}

	public void setMovimiento_Derecha(boolean movimiento_Derecha) {
		this.movimiento_Derecha = movimiento_Derecha;
	}

	public void setAgachado(boolean agachado) {
		this.agachado = agachado;
	}

	public boolean isPuedeDisparar() {
		return puedeDisparar;
	}

	public void setPuedeDisparar(boolean puedeDisparar) {
		this.puedeDisparar = puedeDisparar;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
}
