import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/*
 * To compile: javac A2.java
 * To execute: java A2 (you need to have the sample.png image)
 */

public class A2 {
	public A2 (String inFileName, String outFileName) {
		BufferedImage inI  = null;
		BufferedImage outI = null;
		try {
			inI = ImageIO.read(new File(inFileName));	// Load input image
			int W = inI.getWidth();				// width of image
			int H = inI.getHeight();			// height of image
			
			// Create same type output image as the input (but has no content)
			outI = new BufferedImage(W, H, inI.getType());  

			//
			// Note: LSB is the bit 0
			//
			// WHAT YOU NEED TO DO:
			// 1. Get the RGB color of a pixel (code given).
			// 2. Extract the Red, Green, Blue channels from the input image.
			// 3. For each channel, if the Most significant bit is 1, set it to 0
			//    and if it is 0, set it to 1.
			// 4. For each channel, set to 1 bit 6 (bit 0 is the LSB)
			// 5. For each channel, set to 0 bit 3 (bit 0 is the LSB)
			// 6. Construct an new RGB color where:
			//    a) The Red   channel from the input image becomes the Green channel. 
			//    b) The Blue  channel from the input image becomes the Red   channel.
			//    c) The Green channel from the input image becomes the Blue  channel.
			//    d) The alpha channel should be 0xff.
			//    Remember that the format for each pixel is:  ARGB
			for(int y=0; y<H; y++){
				for(int x=0; x<W; x++) {
					int rgb = inI.getRGB(x,y);	// (1) Getting RGB of a pixel (done)

					// NEED TO IMPLEMENT (2) (replace the 128,64,32 below)
					
					int RED = ((rgb >> 16) & 0xff);
					int GREEN = ((rgb >>  8) & 0xff);
					int BLUE = ((rgb >>  0) & 0xff);
					int alpha = 255;
					// NEED TO IMPLEMENT (3)
					RED ^= 1 << 7;
					GREEN ^= 1 << 7;
					BLUE ^= 1 << 7;
					// NEED TO IMPLEMENT (4)
					RED |= 1 << 6;
					GREEN |= 1 << 6;
					BLUE |= 1 << 6;
					// NEED TO IMPLEMENT (5)
					RED &= ~(1 << 3);
					GREEN &= ~(1 << 3);
					BLUE &= ~(1 << 3);
					// NEED TO IMPLEMENT (6) (replace the 0xffaa6633 value)
					int new_rgb=(rgb & 0x00000000)|(alpha<<24) | (BLUE << 16) | (RED << 8) | (GREEN << 0);


					outI.setRGB(x,y,new_rgb); // set the pixel of the output image to new_rgb
				}
			}

			ImageIO.write(outI, "png", new File(outFileName));	// write the image to the output file
		}
		catch(IOException ee) {
			System.err.println(ee);
			System.exit(-1);
		}
	}


	public static void main(String[] args) {
		new A2("sample.png", "out.png");
  	}
}


