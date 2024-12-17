package org.example.stardewvalley2.screens;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import org.example.stardewvalley2.model.Avatar;
import org.example.stardewvalley2.util.Position;

public abstract class BaseScreen {

    protected Canvas canvas;
    protected GraphicsContext graphicsContext;

    public BaseScreen(Canvas canvas){
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();

    }

    public abstract void paint();

    public abstract void onKeyPressed(KeyEvent event);

    public abstract void onKeyReleased(KeyEvent event);

    public abstract Avatar getAvatar();
}
