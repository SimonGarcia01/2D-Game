package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.example.stardewvalley2.util.Position;

public abstract class Animal extends Thread{

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private int frame;
    private Position position;
    private int health;
    private int imageState;

    public Animal(Canvas canvas, int health){
        this.canvas = canvas;
        this.health=health;
        this.imageState = 0;
        this.frame = 0;
        this.graphicsContext=canvas.getGraphicsContext2D();
        double randomX = (Math.random())*930;
        double randomY = (Math.random())*710;
        this.position = new Position(randomX, randomY);
    }

    public abstract void paint();

    @Override
    public abstract void run();

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public int getHealth() {
        return health;
    }

    public int getImageState() {
        return imageState;
    }

    public void setImageState(int imageState) {
        this.imageState = imageState;
    }

    public boolean hasHealth() {
        return health>0;
    }

    public void setHealth(int health) {
        this.health = health;
        if(health==0){
            Avatar.setMasteryLevel(Avatar.getMasteryLevel()+1);
        }
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }


}
