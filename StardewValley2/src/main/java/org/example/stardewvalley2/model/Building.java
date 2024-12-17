package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Building extends Box{

    private String buildingImage;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Building(Canvas canvas, double x, double y, String source, int width, int height) {
        super(canvas, x, y);
        this.width = width;
        this.height = height;
        this.buildingImage = source;
    }

    @Override
    public void paint() {
        super.getGraphicsContext().drawImage(new Image(getClass().getResourceAsStream("/animation/" + buildingImage + ".png"),width,height,true,true), getPosition().getX(), getPosition().getY());
    }
}
