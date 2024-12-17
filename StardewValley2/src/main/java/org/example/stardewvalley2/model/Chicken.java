package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.stardewvalley2.util.Position;

import java.util.ArrayList;
import java.util.Random;

public class Chicken extends Animal{

    private Random random;
    private ArrayList<Image> rightWalk;
    private ArrayList<Image> leftWalk;
    private Image dead;

    public Chicken(Canvas canvas) {
        super(canvas,1);
        this.random = new Random();
        this.rightWalk = new ArrayList<>();
        this.leftWalk = new ArrayList<>();
        for(int i =1 ; i < 7; i++){
            rightWalk.add(new Image(getClass().getResourceAsStream("/animation/animals/Chicken/rightWalk/walk_0" + i + ".png"),64,64,true,true));
        }
        for(int i =1 ; i < 7; i++){
            leftWalk.add(new Image(getClass().getResourceAsStream("/animation/animals/Chicken/leftWalk/walk_0" + i + ".png"),64,64,true,true));
        }
        this.dead = new Image(getClass().getResourceAsStream("/animation/animals/Chicken/dead.png"),64,64,true,true);
    }

    @Override
    public void paint() {

        int state = getImageState();
        if (state == 1) {// right
            getGraphicsContext().drawImage(rightWalk.get(getFrame() % 6), getPosition().getX(), getPosition().getY());
            setFrame(getFrame()+1);
        } else if (state == 2) { //left
            getGraphicsContext().drawImage(leftWalk.get(getFrame() % 6), getPosition().getX(), getPosition().getY());
            setFrame(getFrame() + 1);
        }else if (state == 3) { //dead
            getGraphicsContext().drawImage(dead, getPosition().getX(), getPosition().getY());
            Avatar.newAchievement("Chicken Killer", "Hunt a chicken");
        }
    }

    @Override
    public void run() {
        while (true) {
            if(hasHealth()){

                try {

                    double currentX = super.getPosition().getX();
                    double currentY = super.getPosition().getY();


                    int dx = (random.nextInt(3) - 1)*5;
                    int dy = (random.nextInt(3) - 1)*5;

                    // Movement logic
                    if (dx != 0 || dy != 0) {

                        currentX += dx;
                        currentY += dy;
                        super.setPosition(new Position(currentX, currentY));
                        setImageState(dx >= 0 ? 1 : 2); // Set to right or left walk
                    }

                    Thread.sleep(100);

                    // Wait before the next movement iteration
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else{
                setImageState(3);
            }
        }
    }

}
