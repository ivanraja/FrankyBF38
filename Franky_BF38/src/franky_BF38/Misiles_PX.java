package franky_BF38;

import java.awt.Rectangle;

public class Misiles_PX {
	private int x, y, velocidad_X;
	private boolean visible;
	private Rectangle r;
	private Jugador jugador = Principal.getJugador();

	/**
	 * Constructor de la classe Misiles_PX
	 * @param inicial_X Posició x inicial
	 * @param inicial_Y Posició y inicial
	 */
	public Misiles_PX(int inicial_X, int inicial_Y) {
		x = inicial_X;
		y = inicial_Y;
		velocidad_X = 7;
		visible = true;
		r = new Rectangle(0, 0, 0, 0);
	}

	/**
	 * Funció que actualitza el posicionament de les bales enemigues i comproba col·lisions amb en Franky.
	 */
	public void update() {
		x -= velocidad_X;
		r.setBounds(x, y, 10, 5);
		if (x < 0) {
			visible = false;
			r = null;
		}
		if (x > 0) {
			checkCollision();
		}
	}

	/**
	 * Funció que comproba col·lisions amb el jugador. En cas de col·lisió, el jugador perd una vida i la bala es destrueix.
	 */
	private void checkCollision() {
		if (Principal.getJugador().isAgachado() == false) {
			if (r.intersects(jugador.rect) || r.intersects(jugador.rect2)
					|| r.intersects(jugador.rect3)
					|| r.intersects(jugador.rect4)) {
				visible = false;
				if (jugador.vida > 0) {
					jugador.vida -= 1;
				}
				if (jugador.vida == 0) {
					Principal.modo = Principal.Estado.Muerto;
				}
			}
		} else if (r.intersects(jugador.rect2)) {
			visible = false;
			if (jugador.vida > 0) {
				jugador.vida -= 1;
			}
			if (jugador.vida == 0) {
				Principal.modo = Principal.Estado.Muerto;
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVelocidad_X() {
		return velocidad_X;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelocidad_X(int velocidad_X) {
		this.velocidad_X = velocidad_X;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
