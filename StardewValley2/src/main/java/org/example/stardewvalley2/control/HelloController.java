package org.example.stardewvalley2.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import org.example.stardewvalley2.StardewValleyApplication;
import org.example.stardewvalley2.model.Avatar;
import org.example.stardewvalley2.screens.BaseScreen;
import org.example.stardewvalley2.screens.ForestScreen;
import org.example.stardewvalley2.screens.HouseScreen;
import org.example.stardewvalley2.screens.VillageScreen;
import org.example.stardewvalley2.util.Position;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;

    private GraphicsContext graphicsContext;
    private ArrayList<BaseScreen> screens;
    private boolean isRunning;

    @FXML
    private Label hoeLabel;
    @FXML
    private Label axeLabel;
    @FXML
    private Label pickaxeLabel;
    @FXML
    private Label swordLabel;
    @FXML
    private Label stoneLabel;
    @FXML
    private Label woodLabel;
    @FXML
    private Label masteryLabel;
    @FXML
    private Label tutorialLB;

    public static int SCREEN = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.graphicsContext = canvas.getGraphicsContext2D();
        this.screens = new ArrayList<>(2);
        screens.add(new VillageScreen(canvas));
        screens.add(new ForestScreen(canvas));
        screens.add(new HouseScreen(canvas));
        isRunning = true;
        Avatar avatar0 = screens.get(2).getAvatar();
        avatar0.setPosition(new Position(400, 400));

        new Thread(() -> { //Runnable using a lambda
            while(isRunning){
                Platform.runLater( () -> {
                    screens.get(SCREEN).paint();
                    Avatar avatar=screens.get(SCREEN).getAvatar();
                    if(avatar.hasPickedTools()){
                        hoeLabel.setText((String.valueOf(avatar.getHoe().getDurability())));
                        axeLabel.setText((String.valueOf(avatar.getAxe().getDurability())));
                        pickaxeLabel.setText((String.valueOf(avatar.getPickaxe().getDurability())));
                        swordLabel.setText((String.valueOf(avatar.getSword().getDurability())) );
                        woodLabel.setText(String.valueOf(Avatar.getWoodStack()));
                        stoneLabel.setText(String.valueOf(Avatar.getStoneStack()));
                        masteryLabel.setText("Mastery Level " + Avatar.getMasteryLevel());
                    }
                });
                try{ //Making a delay for the animation
                    Thread.sleep(110);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        initActions();
    }

    public void initActions(){
        canvas.setOnKeyPressed(event -> {
            screens.get(SCREEN).onKeyPressed(event);

            if (event.getCode() == KeyCode.P) {
                StardewValleyApplication.openWindow("user-manual.fxml");
                tutorialLB.setText("");
            } else if (event.getCode() == KeyCode.U) {
                StardewValleyApplication.openWindow("achievement-tree.fxml");
            }
        });

        canvas.setOnKeyReleased(event -> {
            screens.get(SCREEN).onKeyReleased(event);
        });
    }

    public void setRunning(){
        isRunning = false;
    }

}