package franky_BF38;

import java.awt.Rectangle;

public class Misiles {
	private int x, y, velocidad_X;
	private boolean visible;
	private Rectangle r;

	/**
	 * Constructor de la classe Misiles
	 * @param inicial_X Posició x inicial
	 * @param inicial_Y Posició y inicial
	 */
	public Misiles(int inicial_X, int inicial_Y) {
		x = inicial_X;
		y = inicial_Y;
		velocidad_X = 7;
		visible = true;
		r = new Rectangle(0, 0, 0, 0);
	}

	/**
	 * Funció que actualitza el posicionament dels misils amics i comproba col·lisions dins del rang de pantalla.
	 */
	public void update() {
		x += velocidad_X;
		r.setBounds(x, y, 10, 5);
		if (x > 800) {
			visible = false;
			r = null;
		}
		if (x < 801) {
			checkCollision();
		}
	}

	/**
	 * Funció que comproba col·lisions amb els enemics. En cas de col·lisió, es resta una vida i la bala es destrueix.
	 */
	private void checkCollision() {
		if (r.intersects(Principal.PX_1.r)) {
			visible = false;
			if (Principal.PX_1.vida > 0) {
				Principal.PX_1.vida -= 1;
			}
			if (Principal.PX_1.vida == 0) {
				Principal.PX_1.setCentro_X(-100);
				Principal.score += 5;
			}
		}

		if (r.intersects(Principal.PX_2.r)) {
			visible = false;
			if (Principal.PX_2.vida > 0) {
				Principal.PX_2.vida -= 1;
			}
			if (Principal.PX_2.vida == 0) {
				Principal.PX_2.setCentro_X(-100);
				Principal.score += 5;
			}
		}

		if (r.intersects(Principal.PX_3.r)) {
			visible = false;
			if (Principal.PX_3.vida > 0) {
				Principal.PX_3.vida -= 1;
			}
			if (Principal.PX_3.vida == 0) {
				Principal.PX_3.setCentro_X(-100);
				Principal.score += 5;
			}
		}

		if (r.intersects(Principal.PX_4.r)) {
			visible = false;
			if (Principal.PX_4.vida > 0) {
				Principal.PX_4.vida -= 1;
			}
			if (Principal.PX_4.vida == 0) {
				Principal.PX_4.setCentro_X(-100);
				Principal.score += 5;
			}
		}

		if (r.intersects(Principal.PX_5.r)) {
			visible = false;
			if (Principal.PX_5.vida > 0) {
				Principal.PX_5.vida -= 1;
			}
			if (Principal.PX_5.vida == 0) {
				Principal.PX_5.setCentro_X(-100);
				Principal.score += 5;
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
