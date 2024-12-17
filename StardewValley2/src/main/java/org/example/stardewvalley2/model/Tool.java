package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.stardewvalley2.util.Position;

public class Tool {

    private int durability;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Image skin;

    public Tool(Canvas canvas, String source) {
        this.durability = 10;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.skin = new Image(getClass().getResourceAsStream("/animation/other/"+source+".png"),64,64, false, true);
    }

    public void paint(int x, int y){
        graphicsContext.drawImage(skin,x,y);

    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }
}
