package pacman.infra;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class RepresentacionDeLetras {
    
    public BufferedImage bitmapFontImage;
    public BufferedImage[] Letras;
    
    public int AnchoDeLetra;
    public int Altura;
    public int EspacioEnVertical = 0;
    public int EspacioEnHorizontal = 0;

    public RepresentacionDeLetras(String fontSprites, int columnas, int renglones) {
        loadFont(fontSprites, columnas, renglones);
    }
    
    public void drawText(Graphics2D g, String text, int x, int y) {
        if (Letras == null) {
            return;
        }
        int px = 0;
        int py = 0;
        for (int i=0; i<text.length(); i++) {
            int c = text.charAt(i);
            if (c == (int) '\n') {
                py += Altura + EspacioEnVertical;
                px = 0;
                continue;
            }
            else if (c == (int) '\r') {
                continue;
            }
            Image letter = Letras[c];
            g.drawImage(letter, (int) (px + x), (int) (py + y + 1), null);
            px += AnchoDeLetra + EspacioEnHorizontal;
        }
    }

    private void loadFont(String filename, Integer cols, Integer rows) {
        try {
            bitmapFontImage = ImageIO.read(getClass().getResourceAsStream(filename));
            loadFont(bitmapFontImage, cols, rows);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    //Cargar el tipo de fuente
    private void loadFont(BufferedImage image, Integer columnas, Integer renglones) {
        int lettersCount = columnas * renglones; 
        bitmapFontImage = image;
        Letras = new BufferedImage[lettersCount];
        AnchoDeLetra = bitmapFontImage.getWidth() / columnas;
        Altura = bitmapFontImage.getHeight() / renglones;

        for (int y=0; y<renglones; y++) {
            for (int x=0; x<columnas; x++) {
                Letras[y * columnas + x] = new BufferedImage(AnchoDeLetra, Altura, BufferedImage.TYPE_INT_ARGB);
                Graphics2D ig = (Graphics2D) Letras[y * columnas + x].getGraphics();
                ig.drawImage(bitmapFontImage, 0, 0, AnchoDeLetra, Altura
                        , x * AnchoDeLetra, y * Altura
                        , x * AnchoDeLetra + AnchoDeLetra, y * Altura + Altura, null);
            }
        }
    }
    
}
