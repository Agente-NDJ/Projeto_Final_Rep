package server;

import java.util.Random;

public class BotRandom {
	Random random;

	public BotRandom() {
		this.random = new Random();
	}
	public int[] makeMove(int maxInput) {
         int[] play = new int[2];

          play[0] = this.random.nextInt(maxInput + 1);
          play[1] = this.random.nextInt(maxInput + 1);
         
		return play;
	}

}