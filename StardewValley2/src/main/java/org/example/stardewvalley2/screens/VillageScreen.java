package org.example.stardewvalley2.screens;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.example.stardewvalley2.control.HelloController;
import org.example.stardewvalley2.model.Avatar;
import org.example.stardewvalley2.model.Box;
import org.example.stardewvalley2.model.Building;
import org.example.stardewvalley2.util.IDistance;
import org.example.stardewvalley2.util.Position;

public class VillageScreen extends BaseScreen{

    private Avatar avatar;
    private Box bridge;
    private Building[] buildings;
    private Box door;


    public VillageScreen(Canvas canvas) {

        super(canvas);
        this.avatar = new Avatar(canvas,900,400 );
        this.bridge = new Box(super.canvas,960, 470);
        this.door = new Box(super.canvas,370, 160);
        this.buildings = new Building[5];
        this.buildings[0] = new Building(super.canvas, 345, 0, "houses/MainHouse",256,224);
        this.buildings[1] = new Building(super.canvas, 240, 405, "houses/LeftHouse",235,200);
        this.buildings[2] = new Building(super.canvas, 708, 300, "houses/RightHouse",220,190);
        this.buildings[3] = new Building(super.canvas, 0, 15, "houses/AbandonedHouse",256,224);
        this.buildings[4] = new Building(super.canvas, 505, 380, "other/Fountain",96,111);
    }

    @Override
    public void paint() {

        super.graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        super.graphicsContext.drawImage(new Image(getClass().getResourceAsStream("/animation/screens/VillageScene.png")), 0, 0);

        bridge.paint();
        door.paint();

        for (Building building : buildings) {
            building.paint();
        }

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

        double distanceBridge = iDistance.distance(avatar.getPosition(), bridge.getPosition());
        double distanceDoor = iDistance.distance(avatar.getPosition(), door.getPosition());


        if(distanceBridge <= 50){
            avatar.setUp(false);
            avatar.setDown(false);
            avatar.setLeft(false);
            avatar.setRight(false);
            avatar.setState(0);
            HelloController.SCREEN = 1;
            Avatar.newAchievement("Aventurer", "Discover the forest");
            avatar.setPosition(new Position(152,30));
        }

        if(distanceDoor <= 40){
            avatar.setUp(false);
            avatar.setDown(false);
            avatar.setLeft(false);
            avatar.setRight(false);
            avatar.setState(4);
            HelloController.SCREEN = 2;
            Avatar.newAchievement("Welcome Home", "Get into the main house for the first time");
            avatar.setPosition(new Position(480, 494));

        }

        limits();

    }

    public void limits(){

        if(avatar.getPosition().getX()<=-10){
            avatar.setPosition(new Position(-10, avatar.getPosition().getY()));
        }
        if(avatar.getPosition().getX()>=970){
            avatar.setPosition(new Position(970, avatar.getPosition().getY()));
        }
        if(avatar.getPosition().getY()<=0){
            avatar.setPosition(new Position((avatar.getPosition().getX()),0));
        }
        if(avatar.getPosition().getY()>=710){
            avatar.setPosition(new Position((avatar.getPosition().getX()),710));
        }

    }

    public boolean avatarCollided(){
        for (Building building : buildings) {
            if(collided(building)){
                return true;
            }
        }
        return false;
    }

    public boolean collided(Building building) {
        double avatarX = avatar.getPosition().getX();
        double avatarY = avatar.getPosition().getY();
        double avatarWidth = avatar.getWidth();
        double avatarHeight = avatar.getHeight();

        double buildingX = building.getPosition().getX();
        double buildingY = building.getPosition().getY();
        double buildingWidth = building.getWidth();
        double buildingHeight = building.getHeight();
        boolean xCollided = avatarX+(avatarWidth/2) > buildingX && avatarX < buildingX + buildingWidth-buildingWidth*0.20;
        boolean yCollided = avatarY + avatarHeight/2 > buildingY && avatarY < buildingY + buildingHeight-buildingHeight*0.27;

        if(building != buildings[1]){
            return xCollided && yCollided;
        }

        xCollided = avatarX+(avatarWidth/2) > 240 && avatarX < buildingX + 100;
        return xCollided && yCollided;
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
