package franky_BF38;

import java.util.Random;

public class Pacifista extends Enemigo {

	/**
	 * Constructor de la classe Pacifista
	 * @param centro_X Posició centre x
	 * @param centro_Y Posició centre y
	 */
	public Pacifista(int centro_X, int centro_Y) {
		setCentro_X(centro_X);
		setCentro_Y(centro_Y);
		generarespera();
	}

	int tempsperdisparar;

	/**
	 * Funció que genera un temps aleatori d'espera per poder disparar.
	 */
	private void generarespera() {
		Random rand;
		rand = new Random();
		try {
			tempsperdisparar = rand.nextInt(200) + 40;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Funció que permet disparar al pacifista aleatòriament en el temps.
	 */
	public void step() {
		tempsperdisparar--;
		if (tempsperdisparar == 0) {
			this.disparar();
			generarespera();
		}
	}

}
