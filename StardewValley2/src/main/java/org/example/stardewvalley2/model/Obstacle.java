package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.stardewvalley2.util.Position;

public class Obstacle {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private int health;
    private Position position;
    private Image notDestroyed;
    private Image cultivar;
    private Image crop;
    private boolean isCultivable;


    public Obstacle(Canvas canvas, int health, int x, int y, String notDestroyed, String crop) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.health = health;
        this.position = new Position(x, y);
        this.isCultivable = false;
        this.notDestroyed = new Image(getClass().getResourceAsStream("/animation/other/" + notDestroyed + ".png"),64,64,true,true);
        this.cultivar = new Image(getClass().getResourceAsStream("/animation/other/cultivar.png"),32,32,true,true);
        this.crop = new Image(getClass().getResourceAsStream("/animation/other/" + crop + ".png"),32,32,true,true);
    }

    public void paint(){
        if(health > 0){
            graphicsContext.drawImage(notDestroyed, position.getX(), position.getY());
        } else if(isCultivable) {
            graphicsContext.drawImage(cultivar, position.getX(), position.getY());
        } else {
            graphicsContext.drawImage(crop, position.getX(), position.getY());
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCultivable() {
        return isCultivable;
    }

    public void setCultivable(boolean cultivable) {
        isCultivable = cultivable;
    }
}
