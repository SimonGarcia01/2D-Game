package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;
import org.example.stardewvalley2.util.Position;

public class Cow extends Animal{

    private Random random;
    private ArrayList<Image> idles;
    private ArrayList<Image> runsLeft;
    private ArrayList<Image> runsRight;
    private ArrayList<Image> sleeps;
    private ArrayList<Image> sits;
    private Image dead;
    private boolean isSleeping;

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public Cow(Canvas canvas) {
        super(canvas,3);
        this.random = new Random();
        this.sits= new ArrayList<>();
        this.idles = new ArrayList<>();
        this.runsLeft = new ArrayList<>();
        this.runsRight = new ArrayList<>();
        this.isSleeping = false;
        this.sleeps = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Image idle = new Image(getClass().getResourceAsStream("/animation/animals/Cow/idle/idle_0" + i + ".png"),64,64,true,true);
            idles.add(idle);
        }

        for (int i = 1; i < 9; i++) {
            Image run = new Image(getClass().getResourceAsStream("/animation/animals/Cow/leftWalk/leftWalk_0" + i + ".png"),64,64,true,true);
            runsLeft.add(run);
        }
        for (int i = 1; i < 9; i++) {
            Image run = new Image(getClass().getResourceAsStream("/animation/animals/Cow/rightWalk/rightWalk_0" + i + ".png"),64,64,true,true);
            runsRight.add(run);
        }
        for (int i = 1; i < 7; i++) {
            Image sit = new Image(getClass().getResourceAsStream("/animation/animals/Cow/sitting/sitting_0" + i + ".png"),64,64,true,true);
            sits.add(sit);
        }
        for (int i = 1; i < 9; i++) {
            Image sleep = new Image(getClass().getResourceAsStream("/animation/animals/Cow/sleep/sleep_0" + i + ".png"),64,64,true,true);
            sleeps.add(sleep);
        }

        this.dead = new Image(getClass().getResourceAsStream("/animation/animals/cow/dead.png"),64,64,true,true);
    }

    @Override
    public void paint() {
        int state = getImageState();
        //idle
        if (state == 0) {
            getGraphicsContext().drawImage(idles.get(getFrame() % 2), getPosition().getX(), getPosition().getY());
            setFrame(getFrame()+1);

        } else if (state == 1) {// right
            getGraphicsContext().drawImage(runsRight.get(getFrame() % 8), getPosition().getX(), getPosition().getY());
            setFrame(getFrame()+1);
        } else if (state == 2) { //left
            getGraphicsContext().drawImage(runsLeft.get(getFrame() % 8), getPosition().getX(), getPosition().getY());
            setFrame(getFrame()+1);
        } else if (state == 3) { // sit
            getGraphicsContext().drawImage(sits.get(getFrame() % 6), getPosition().getX(), getPosition().getY());
            setFrame(getFrame()+1);
        } else if (state == 4) { //sleep
            getGraphicsContext().drawImage(sleeps.get(getFrame() % 8), getPosition().getX(), getPosition().getY());
            setFrame(getFrame()+1);
        } else if (state == 5) { // dead
            getGraphicsContext().drawImage(dead, getPosition().getX(), getPosition().getY());
            Avatar.newAchievement("Cow Hunter", "Make a cow pass away");
        }
    }

    @Override
    public void run() {
        while (true) {

            if(hasHealth()){
                if (!isSleeping()) {
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
                        } else {
                            super.setImageState(0); // Idle state
                        }

                        // Trigger sit and sleep animation randomly
                        if (Math.random() < 0.01) { // 10% chance to start sleep animation
                            sleepAnimation();
                            Thread.sleep(10000);
                        }else{
                            Thread.sleep(100);
                        }

                        // Wait before the next movement iteration
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        // Wait while sleeping, but check if the cow has health
                        Thread.sleep(1000); // Re-check every 500 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                setImageState(5);
            }
        }
    }


    public void sleepAnimation() {
        Thread animationThread = new Thread(() -> {
            try {
                // Cambiar el estado a "sit" y animar
                setImageState(3);
                for (int i = 0; i < sits.size(); i++) {
                    setFrame(i);
                    Thread.sleep(200); // Controla la velocidad de la animación de sentarse
                }

                // Cambiar el estado a "sleep" y animar
                setImageState(4);
                for (int i = 0; i < 100; i++) {
                    if(!hasHealth()){
                        setImageState(5);
                    }
                    setFrame(i%sleeps.size());
                    Thread.sleep(500); // Controla la velocidad de la animación de dormir
                }

                // Marcar la vaca como dormida
                setSleeping(true);
                Thread.sleep(5000); // Tiempo total que la vaca debe dormir

                // Cambiar el estado de vuelta a "idle" cuando termine de dormir
                setSleeping(false);
                setImageState(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        animationThread.start();
    }

}
