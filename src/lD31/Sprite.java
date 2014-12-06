package lD31;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Sprite extends GameObject{
	int[][] img;

	public Sprite(URL path) {
		super(0, 0);
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(path);
		} catch (IOException e) {
		}
		this.width = temp.getWidth();
		this.height = temp.getHeight();
		img = new int[this.width][this.height];
		for (int i = 0; i < this.width; i++)
			for (int j = 0; j < this.height; j++)
				img[i][j] = temp.getRGB(i, j);
	}

	public void useMask(int mask) {
		for (int i = 0; i < this.width; i++)
			for (int j = 0; j < this.height; j++)
				if (img[i][j] != 0xFFFF00FF)
					img[i][j] &= mask;
	}

	public Sprite(int[][] arr) {
		super(0, 0);
		this.img = arr;
		this.width = arr.length;
		this.height = arr[0].length;
	}

	public void reset(URL path) {
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(path);
		} catch (IOException e) {
		}
		this.width = temp.getWidth();
		this.height = temp.getHeight();
		img = new int[this.width][this.height];
		for (int i = 0; i < this.width; i++)
			for (int j = 0; j < this.height; j++)
				img[i][j] = temp.getRGB(i, j);
	}

	public Sprite(BufferedImage temp) {
		super(0, 0);
		this.width = temp.getWidth();
		this.height = temp.getHeight();
		img = new int[this.width][this.height];
		for (int i = 0; i < this.width; i++)
			for (int j = 0; j < this.height; j++)
				img[i][j] = temp.getRGB(i, j);
	}


	public int get(int x, int y) {
		if (x >= 0 && y >= 0 && x < img.length && y < img[0].length
				&& this.img != null)
			return this.img[x][y];
		else
			return 0xFFFF00FF;
	}

	public int[][] getImg() {
		return this.img;
	}
	
	public void  setImg(int[][] img) {
		this.img = img;
	}

	public int[] getPixelArray() {
		int[] temp = new int[this.getWidth() * this.getHeight()];
		int currentState = 0;
		for (int i = 0; i < this.getWidth(); i++)
			for (int j = 0; j < this.getHeight(); j++) {
				temp[currentState] = this.img[i][j];
				currentState++;
			}
		return temp;
	}
}
