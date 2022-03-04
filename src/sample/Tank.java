package sample;

import java.io.FileNotFoundException;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class Tank  extends JPanel implements ActionListener {
    private ImageIcon treesImage;


    private Images images = new Images();

    private ImageIcon orel;

    public ImageIcon player1;
    public int player1X = images.player1XX;
    public int player1Y = images.player1YY;
    private boolean player1right = false;
    private boolean player1left = false;
    private boolean player1down = false;
    private boolean player1up = true;
    private boolean player1Shoot = false;
    private String bulletShootDir1 = "";

    private Timer timer;
    private int delay = 8;

    private Player1Listener player1Listener;
    private Bullet player1Bullet = null;

    private boolean play = true;

    public Tank() throws FileNotFoundException {
        player1Listener = new Player1Listener();
        setFocusable(true);
        addKeyListener(player1Listener);
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paint(Graphics g) {
        // play background
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 630);

        orel = new ImageIcon("orel.png");
        orel.paintIcon(this, g, 300, 530);

        images.drawSolids(this, g);
        images.drawWater(this, g);
        images.draw(this, g);

        if (play) {
            // draw player 1
            if(player1up)
                player1=new ImageIcon("player1_tank_up.png");
            else if(player1down)
                player1=new ImageIcon("player1_tank_down.png");
            else if(player1right)
                player1=new ImageIcon("player1_tank_right.png");
            else if(player1left)
                player1=new ImageIcon("player1_tank_left.png");

            player1.paintIcon(this, g, player1X, player1Y);

            if (player1Bullet != null && player1Shoot) {
                if (bulletShootDir1.equals("")) {
                    if (player1up) {
                        bulletShootDir1 = "up";
                    } else if (player1down) {
                        bulletShootDir1 = "down";
                    } else if (player1right) {
                        bulletShootDir1 = "right";
                    } else if (player1left) {
                        bulletShootDir1 = "left";
                    }
                } else {
                    player1Bullet.move(bulletShootDir1);
                    player1Bullet.draw(g);
                }

                if (images.checkOrel(player1Bullet.getX(), player1Bullet.getY()) || images.checkSolidCollision(player1Bullet.getX(), player1Bullet.getY()) || images.checkCollision(player1Bullet.getX(), player1Bullet.getY())) {
                    player1Bullet = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                }

                if (player1Bullet != null && (player1Bullet.getY() < 1
                        || player1Bullet.getY() > 580
                        || player1Bullet.getX() < 1
                        || player1Bullet.getX() > 700)) {
                    player1Bullet = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                }
            }
        }
        treesImage = new ImageIcon("trees.png");
        for(int i=0; i< images.treesXPos.size();i++)
        {
            treesImage.paintIcon(this, g, images.treesXPos.get(i),images.treesYPos.get(i));
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        repaint();
    }

    class Player1Listener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_U) {
                if (!player1Shoot) {
                    if (player1up) {
                        player1Bullet = new Bullet(player1X + 15, player1Y);
                    } else if (player1down) {
                        player1Bullet = new Bullet(player1X + 15, player1Y + 20);
                    } else if (player1right) {
                        player1Bullet = new Bullet(player1X + 20, player1Y + 15);
                    } else if (player1left) {
                        player1Bullet = new Bullet(player1X, player1Y + 15);
                    }

                    player1Shoot = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                player1right = false;
                player1left = false;
                player1down = false;
                player1up = true;

                if (!(player1Y < 10) ) {
                    if (!images.checkWallCollision(player1X, player1Y - 1) && !images.checkWall2Collision(player1X, player1Y - 1)
                            && !images.checkWaterCollision(player1X, player1Y - 1)) {
                        player1Y -= 1;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                player1right = false;
                player1left = true;
                player1down = false;
                player1up = false;

                if (!(player1X < 10)) {
                    if (!images.checkWallCollision(player1X - 1, player1Y) && !images.checkWall2Collision(player1X - 1, player1Y)
                            && !images.checkWaterCollision(player1X - 1, player1Y)) {
                        player1X -= 1;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                player1right = false;
                player1left = false;
                player1down = true;
                player1up = false;

                if (!(player1Y > 540)) {
                    if (!images.checkWallCollision(player1X, player1Y + 1) && !images.checkWall2Collision(player1X, player1Y + 1)
                            && !images.checkWaterCollision(player1X, player1Y + 1)) {
                        player1Y += 1;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                player1right = true;
                player1left = false;
                player1down = false;
                player1up = false;

                if (!(player1X > 700)) {
                    if (!images.checkWallCollision(player1X + 1, player1Y) && !images.checkWall2Collision(player1X + 1, player1Y)
                            && !images.checkWaterCollision(player1X + 1, player1Y)) {
                        player1X += 1;
                    }
                }
            }
        }
    }
}
