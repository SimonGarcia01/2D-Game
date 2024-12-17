package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.stardewvalley2.util.Position;

public class Box {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private Position position;

    public Box(Canvas canvas, double x, double y){
        this.canvas = canvas;
        this.position = new Position(x, y);
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void paint(){
        graphicsContext.setFill(Color.TRANSPARENT);
        graphicsContext.fillRect(position.getX(), position.getY(), 50, 50);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
}
