package Project;


import java.util.Map;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;


public class ImageManager {

	public final static String path = "media/";
	public final static String ext = ".png";

	public static Map<String, Image> images = new ConcurrentHashMap<>();

	public static Image loadImage(String filename) throws IOException {
		BufferedImage img;
		img = ImageIO.read(new File(path + filename + ext));
		images.put(filename, img);
		return img; 
	}

	public static Image getImage(String s) {
		return images.get(s);
	}

	public static Image loadImage(String imgName, String fname) throws IOException {
		BufferedImage pic;
		pic = ImageIO.read(new File(path + fname + ext));
		images.put(imgName, pic);
		return pic;
	}

	public static void loadImages(Iterable<String> fNames) throws IOException {
		for (String s : fNames)
			loadImage(s);
	}

	public static void loadImages(String[] fNames) throws IOException {
		for (String s : fNames)
			loadImage(s);
	}

}
