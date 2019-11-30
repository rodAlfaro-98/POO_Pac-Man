package pacman.infra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;


public class Pantalla extends Canvas {
   
    private Juego game;
    private boolean running;
    private BufferStrategy bs;
    
    public Pantalla(Juego game) {
        this.game = game;
        int sx = (int) (game.screenSize.width * game.screenScale.getX());
        int sy = (int) (game.screenSize.height * game.screenScale.getY());
        setPreferredSize(new Dimension(sx, sy));
        addKeyListener(new Teclado());
    }

    public void start() {
        if (running) {
            return;
        }
        createBufferStrategy(3);
        bs = getBufferStrategy();
        game.init();
        running = true;
        Thread thread = new Thread(new MainLoop());
        thread.start();
    }
    
    private class MainLoop implements Runnable {

        @Override
        public void run() {
            long desiredFrameRateTime = 1000 / 60;
            long currentTime = System.currentTimeMillis();
            long lastTime = currentTime - desiredFrameRateTime;
            long unprocessedTime = 0;
            boolean needsRender = false;
            while (running) {
                currentTime = System.currentTimeMillis();
                unprocessedTime += currentTime - lastTime;
                lastTime = currentTime;
                
                while (unprocessedTime >= desiredFrameRateTime) {
                    unprocessedTime -= desiredFrameRateTime;
                    update();
                    needsRender = true;
                }
                //NeedsRender es para mostrar el fondo y la animacion del inicio de la Presentacion POOderosa
                if (needsRender) {
                    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                    g.setBackground(Color.BLACK);
                    g.clearRect(0, 0, getWidth(), getHeight());
                    g.scale(game.screenScale.getX(), game.screenScale.getY());
                    draw(g);
                    g.dispose();
                    bs.show();
                    needsRender = false;
                }
                else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
        
    }
    
    public void update() {
        game.update();
    }
    
    public void draw(Graphics2D g) {
        game.draw(g);
    }
    
}
