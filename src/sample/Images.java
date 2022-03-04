package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.nio.InvalidMarkException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class Images  {
    public String [] [] array;
    public int player1XX;
    public int player1YY;

    ArrayList<Integer> bricksXPos = new ArrayList<>();

    ArrayList<Integer> bricksYPos = new ArrayList<>();

    ArrayList<Integer> solidBricksXPos = new ArrayList<>();

    ArrayList<Integer> solidBricksYPos = new ArrayList<>();
    ArrayList<Integer> waterXPos = new ArrayList<>();
    ArrayList<Integer> waterYPos = new ArrayList<>();
    ArrayList<Integer> treesXPos = new ArrayList<>();
    ArrayList<Integer> treesYPos = new ArrayList<>();
    public int [] arrForBricksX;
    public int [] arrForBricksY;
    int [] brickON;

    private Tank greyTank;
    private ImageIcon breakBrickImage;
    private ImageIcon solidBrickImage;
    private ImageIcon waterImage;

    public Images() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        KeyEvent key;

        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();

        array = new String [size] [size];
        if (size == 0) {
            System.out.println("Map size can not be zero");
            System.exit(0);
        }
        int i = 0; int j = 0;
        do {
            j = 0;
            do {
                array[i][j] = sc.next();
                if (array [i] [j].equals("S") || array [i] [j].equals("s")) {
                    solidBricksXPos.add(j * 50);
                    solidBricksYPos.add(i * 50);
                } else if (array [i] [j].equals("B") || array [i] [j].equals("b")) {
                    bricksXPos.add(j * 50);
                    bricksYPos.add(i * 50);
                } else if (array [i] [j].equals("W") || array [i] [j].equals("w")) {
                    waterXPos.add(j * 50);
                    waterYPos.add(i * 50);
                } else if (array [i] [j].equals("T") || array [i] [j].equals("t")) {
                    treesXPos.add(j * 50);
                    treesYPos.add(i * 50);
                }
                else if (array [i] [j].equals("P") || array [i] [j].equals("p")) {
                    player1XX = j * 50;
                    player1YY = i * 50;
                }
                j++;
            } while(j < array.length);
            i++;
        } while (i < array.length);
        brickON = new int [bricksXPos.size()];
        arrForBricksX = new int [bricksXPos.size()];
        arrForBricksY = new int [bricksYPos.size()];
        for (int idea = 0; idea < bricksXPos.size(); idea++) {
            brickON [idea] = 4;
            arrForBricksX [idea] = bricksXPos.get(idea);
            arrForBricksY [idea] = bricksYPos.get(idea);
        }
        breakBrickImage = new ImageIcon("wall2.png");
        solidBrickImage = new ImageIcon("wall.png");
        waterImage = new ImageIcon("water.png");
        brickON = new int [bricksXPos.size()];
        arrForBricksX = new int [bricksXPos.size()];
        arrForBricksY = new int [bricksYPos.size()];
        for (int idea = 0; idea < bricksXPos.size(); idea++) {
            brickON [idea] = 4;
            arrForBricksX [idea] = bricksXPos.get(idea);
            arrForBricksY [idea] = bricksYPos.get(idea);
        }
    }

    public void draw(Component c, Graphics g)
    {

        for(int i=0; i < arrForBricksX.length;i++)
        {
            if(brickON[i]>= 1)
            {
                breakBrickImage.paintIcon(c, g, arrForBricksX[i], arrForBricksY[i]);
            }
        }
    }

    public void drawSolids(Component c, Graphics g)
    {
        for(int i=0; i < solidBricksXPos.size();i++)
        {
            solidBrickImage.paintIcon(c, g, solidBricksXPos.get(i),solidBricksYPos.get(i));
        }
    }

    public void drawWater(Component c, Graphics g)
    {
        for(int i=0; i< waterXPos.size();i++)
        {
            waterImage.paintIcon(c, g, waterXPos.get(i),waterYPos.get(i));
        }
    }

    public boolean checkCollision(int x, int y)
    {
        boolean collided = false;
        for(int i=0; i< arrForBricksX.length;i++)
        {
            if(brickON[i]>= 1)
            {
                if(new Rectangle(x, y, 10, 10).intersects(new Rectangle(arrForBricksX[i], arrForBricksY[i], 50, 50)))
                {
                    brickON[i] -= 1;
                    if (brickON [i] < 1) {
                        arrForBricksX [i] = -50;
                        arrForBricksY [i] = -50;
                    }
                    collided = true;
                    break;
                }
            }
        }

        return collided;
    }

    public boolean checkSolidCollision(int x, int y)
    {
        boolean collided = false;
        for(int i=0; i< solidBricksXPos.size();i++)
        {
            if(new Rectangle(x, y, 10, 10).intersects(new Rectangle(solidBricksXPos.get(i), solidBricksYPos.get(i), 50, 50)))
            {
                collided = true;
                break;
            }
        }

        return collided;
    }
    public boolean checkWall2Collision (int x, int y)
    {
        boolean collided = false;
        for(int i=0; i< solidBricksXPos.size();i++)
        {
            if(new Rectangle(x, y, 40, 40).intersects(new Rectangle(solidBricksXPos.get(i), solidBricksYPos.get(i), 50, 50)))
            {
                collided = true;
                break;
            }
        }

        return collided;
    }
    public boolean checkWallCollision (int x, int y)
    {
        boolean collided = false;
        for(int i=0; i< arrForBricksX.length; i++)
        {
            if(new Rectangle(x, y, 40, 40).intersects(new Rectangle(arrForBricksX[i], arrForBricksY[i], 50, 50)))
            {
                collided = true;
                break;
            }
        }

        return collided;
    }
    public boolean checkWaterCollision (int x, int y)
    {
        boolean collided = false;
        for(int i=0; i< waterXPos.size();i++)
        {
            if(new Rectangle(x, y, 40, 40).intersects(new Rectangle(waterXPos.get(i), waterYPos.get(i), 50, 50)))
            {
                collided = true;
                break;
            }
        }

        return collided;
    }
    public boolean checkOrel (int x, int y) {
        boolean collided = false;
        if(new Rectangle(x, y, 10, 10).intersects(new Rectangle(300, 530, 50, 50)))
        {
            collided = true;
        }


        return collided;
    }


}
class InvalidMapException extends Exception {
    public InvalidMapException(String message) {
        super(message);

    }
}
