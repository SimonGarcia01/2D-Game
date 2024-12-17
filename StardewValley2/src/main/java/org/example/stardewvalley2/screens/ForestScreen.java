package org.example.stardewvalley2.screens;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import org.example.stardewvalley2.control.HelloController;
import org.example.stardewvalley2.model.*;
import org.example.stardewvalley2.util.IDistance;
import org.example.stardewvalley2.util.Position;

import java.util.Random;

public class ForestScreen  extends BaseScreen{

    private Avatar avatar;
    private Box bridge;
    private Cow[] cows;
    private Chicken[] chickens;
    private Obstacle[] rocks;
    private Obstacle[] trees;

    public ForestScreen(Canvas canvas){
        super(canvas);
        this.avatar = new Avatar(canvas,900,400 );
        this.bridge = new Box(super.canvas, 152, -40);
        avatar.setUp(false);
        avatar.setDown(false);
        avatar.setLeft(false);
        avatar.setRight(false);
        Random rand = new Random();
        this.cows= new Cow[rand.nextInt(5)+2];
        for(int i = 0; i < cows.length; i++){
            cows[i] = new Cow(super.canvas);
        }
        this.chickens= new Chicken[rand.nextInt(7)+2];
        for(int i = 0; i < chickens.length; i++){
            chickens[i] = new Chicken(super.canvas);
        }
        this.rocks = new Obstacle[rand.nextInt(5)+4];
        for(int i = 0; i < rocks.length; i++){
            int randomX = rand.nextInt(-10, 950);
            int randomY = rand.nextInt(0, 710);
            rocks[i] = new Obstacle(canvas, 3, randomX, randomY, "rock", "tomatoe");
        }

        this.trees = new Obstacle[rand.nextInt(5)+3];
        for(int i = 0; i < trees.length; i++){
            int randomX = rand.nextInt(-10, 950);
            int randomY = rand.nextInt(0, 710);
            trees[i] = new Obstacle(canvas, 2, randomX, randomY, "Oak_Tree_03", "wheat");
        }

    }

    @Override
    public void paint() {
        super.graphicsContext.drawImage(new Image(getClass().getResourceAsStream("/animation/screens/ForestScene.png")), 0, 0);
        bridge.paint();
        avatar.paint();

        animalsLimits();
        hunting();
        destroying();

        for(Cow cow : cows){
            if(!cow.isAlive()){
                cow.start();
            }
            cow.paint();
        }
        for(Chicken chicken : chickens){
            if(!chicken.isAlive()){
                chicken.start();
            }
            chicken.paint();
        }

        for(Obstacle rock : rocks){
            rock.paint();
        }

        for(Obstacle tree : trees){
            tree.paint();
        }

        IDistance iDistance = (from, to) -> {
            return Math.sqrt(
                    Math.abs(
                            Math.pow(from.getX() - to.getX(), 2) +
                                    Math.pow(from.getY() - to.getY(),2)
                    )
            );
        };

        double distance = iDistance.distance(avatar.getPosition(), bridge.getPosition());

        if(distance <= 50){
            avatar.setUp(false);
            avatar.setDown(false);
            avatar.setLeft(false);
            avatar.setRight(false);
            avatar.setState(0);
            HelloController.SCREEN = 0;
            avatar.setPosition(new Position(900, 470));
        }

        if(avatar.getPosition().getX()<=-10){
            avatar.setPosition(new Position(-10, avatar.getPosition().getY()));
        }
        if(avatar.getPosition().getX()>=930){
            avatar.setPosition(new Position(930, avatar.getPosition().getY()));
        }
        if(avatar.getPosition().getY()<=0){
            avatar.setPosition(new Position((avatar.getPosition().getX()),0));
        }
        if(avatar.getPosition().getY()>=710){
            avatar.setPosition(new Position((avatar.getPosition().getX()),710));
        }
    }

    private void hunting() {
        if(avatar.hasPickedTools()){
            IDistance iDistance = (from, to) -> {
                return Math.sqrt(
                        Math.abs(
                                Math.pow(from.getX() - to.getX(), 2) +
                                        Math.pow(from.getY() - to.getY(),2)
                        )
                );
            };

            for(Cow cow : cows){
                if(cow.hasHealth()){
                    if(cow.isAlive()){

                        if(iDistance.distance(cow.getPosition(), avatar.getPosition()) < 50 && avatar.isHitting()){
                            cow.setHealth(cow.getHealth()-1);
                            avatar.getSword().setDurability(avatar.getSword().getDurability()-1);
                        }

                    }
                }

            }

            for(Chicken chicken : chickens){
                if(chicken.hasHealth()){
                    if(chicken.isAlive()){
                        if(iDistance.distance(chicken.getPosition(), avatar.getPosition()) < 50 && avatar.isHitting()){
                            chicken.setHealth(chicken.getHealth()-1);
                            avatar.getSword().setDurability(avatar.getSword().getDurability()-1);
                        }
                    }
                }

            }
        }
    }

    private void destroying() {
        Random random = new Random();
        if(avatar.hasPickedTools()){
            IDistance iDistance = (from, to) -> {
                return Math.sqrt(
                        Math.abs(
                                Math.pow(from.getX() - to.getX(), 2) +
                                        Math.pow(from.getY() - to.getY(),2)
                        )
                );
            };

            for(Obstacle tree : trees){
                if(tree.getHealth() > 0){
                    if(iDistance.distance(tree.getPosition(), avatar.getPosition()) < 50 && avatar.isAxing()){
                        tree.setHealth(tree.getHealth()-1);
                        avatar.getAxe().setDurability(avatar.getAxe().getDurability()-1);
                        if(tree.getHealth() == 0){
                            tree.setCultivable(true);
                            Avatar.setWoodStack((Avatar.getWoodStack()+random.nextInt(5)+3));
                            Avatar.setMasteryLevel(Avatar.getMasteryLevel()+1);
                            Avatar.newAchievement("Lumberjack", "Log a tree");
                        }
                    }
                } else {
                    if(iDistance.distance(tree.getPosition(), avatar.getPosition()) < 50 && avatar.isHoeing() && tree.isCultivable()){
                        avatar.getHoe().setDurability(avatar.getHoe().getDurability()-1);
                        tree.setCultivable(false);
                        Avatar.setMasteryLevel(Avatar.getMasteryLevel()+1);
                        Avatar.newAchievement("Bread Lover", "Plant a wheat crop");
                    }
                }

            }

            for(Obstacle rock : rocks){
                if(rock.getHealth() > 0){
                    if(iDistance.distance(rock.getPosition(), avatar.getPosition()) < 50 && avatar.isPickaxing()){
                        rock.setHealth(rock.getHealth()-1);
                        avatar.getPickaxe().setDurability(avatar.getPickaxe().getDurability()-1);
                        if(rock.getHealth() == 0){
                            rock.setCultivable(true);
                            Avatar.setStoneStack((Avatar.getStoneStack()+random.nextInt(5)+3));
                            Avatar.setMasteryLevel(Avatar.getMasteryLevel()+1);
                            Avatar.newAchievement("Minecraft", "Mine a rock");
                        }
                    }
                } else {
                    if(iDistance.distance(rock.getPosition(), avatar.getPosition()) < 50 && avatar.isHoeing() && rock.isCultivable()){
                        avatar.getHoe().setDurability(avatar.getHoe().getDurability()-1);
                        rock.setCultivable(false);
                        Avatar.setMasteryLevel(Avatar.getMasteryLevel()+1);
                        Avatar.newAchievement("Tomato Heinz", "Plant a tomato crop");
                    }
                }

            }
        }
    }

    public Avatar getAvatar(){
        return avatar;
    }

    public void animalsLimits(){
        for(Cow cow : cows){

            if(cow.getPosition().getX()<=-10){
                cow.setPosition(new Position(-10, cow.getPosition().getY()));
            }
            if(cow.getPosition().getX()>=950){
                cow.setPosition(new Position(950, cow.getPosition().getY()));
            }
            if(cow.getPosition().getY()<=0){
                cow.setPosition(new Position((cow.getPosition().getX()),0));
            }
            if(cow.getPosition().getY()>=710){
                cow.setPosition(new Position((cow.getPosition().getX()),710));
            }

        }

        for(Chicken chicken : chickens){
            if(chicken.getPosition().getX()<=-10){
                chicken.setPosition(new Position(-10, chicken.getPosition().getY()));
            }
            if(chicken.getPosition().getX()>=970){
                chicken.setPosition(new Position(970, chicken.getPosition().getY()));
            }
            if(chicken.getPosition().getY()<=0){
                chicken.setPosition(new Position((chicken.getPosition().getX()),0));
            }
            if(chicken.getPosition().getY()>=710){
                chicken.setPosition(new Position((chicken.getPosition().getX()),710));
            }
        }
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
