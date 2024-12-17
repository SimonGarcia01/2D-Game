package org.example.stardewvalley2.screens;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import org.example.stardewvalley2.control.HelloController;
import org.example.stardewvalley2.model.Avatar;
import org.example.stardewvalley2.model.Box;
import org.example.stardewvalley2.model.Building;
import org.example.stardewvalley2.util.IDistance;
import org.example.stardewvalley2.util.Position;

public class HouseScreen extends BaseScreen{

    private Avatar avatar;
    private Box door;
    private Building chest;

    public HouseScreen(Canvas canvas) {
        super(canvas);
        this.avatar = new Avatar(canvas,900,400 );
        this.avatar.setState(4);
        this.door = new Box(super.canvas,487, 560);
        this.chest= new Building(super.canvas,490, 360, "other/Chest",32,32);
    }

    @Override
    public void paint() {
        super.graphicsContext.drawImage(new Image(getClass().getResourceAsStream("/animation/screens/HouseScene.png")), 0, 0);

        chest.paint();
        door.paint();
        avatar.paint();

        if(avatarCollided()){
            avatar.setPosition(avatar.getLastPosition());
        }


        IDistance iDistance = (from, to) -> {
            return Math.sqrt(
                    Math.abs(
                            Math.pow(from.getX() - to.getX(), 2) +
                                    Math.pow(from.getY() - to.getY(),2)
                    )
            );
        };

        double distanceDoor = iDistance.distance(avatar.getPosition(), door.getPosition());

        if(distanceDoor <= 66){
            avatar.setUp(false);
            avatar.setDown(false);
            avatar.setLeft(false);
            avatar.setRight(false);
            avatar.setState(0);
            HelloController.SCREEN = 0;
            avatar.setPosition(new Position(368, 200));
        }

        double distanceChest = iDistance.distance(avatar.getPosition(), chest.getPosition());

        if(distanceChest <= 60 && avatar.isInteracting()){
            avatar.setHasPickedTools(true);
            Avatar.newAchievement("Tool Picker", "Pick your tools for the first time");
        }

        if(avatar.getPosition().getX()<=255){
            avatar.setPosition(new Position(255, avatar.getPosition().getY()));
        }
        if(avatar.getPosition().getX()>=700){
            avatar.setPosition(new Position(700, avatar.getPosition().getY()));
        }
        if(avatar.getPosition().getY()<=200){
            avatar.setPosition(new Position((avatar.getPosition().getX()),200));
        }
        if(avatar.getPosition().getY()>=500){
            avatar.setPosition(new Position((avatar.getPosition().getX()),500));
        }

    }

    public boolean avatarCollided(){
        return (avatar.getPosition().getX() <= 504 && avatar.getPosition().getX() >= 448) && (avatar.getPosition().getY() <= 366 && avatar.getPosition().getY() >= 310);
    }

    public Avatar getAvatar(){
        return avatar;
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        avatar.setOnKeyPressed(event);
    }

    @Override
    public void onKeyReleased(KeyEvent event) {
        avatar.setOnKeyReleased(event);
    }
}
