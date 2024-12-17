package org.example.stardewvalley2.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import org.example.stardewvalley2.structures.AVL;
import org.example.stardewvalley2.util.Position;

import java.util.ArrayList;


public class Avatar {

    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    private static Position position;
    private static Position lastPosition;
    private static int state;
    private static boolean interacting;
    private static int frame;
    private static boolean hasPickedTools;
    private static ArrayList<Image> frontIdles;
    private static ArrayList<Image> backIdles;
    private static ArrayList<Image> rightWalk;
    private static ArrayList<Image> frontWalk;
    private static ArrayList<Image> backWalk;
    private static ArrayList<Image> leftWalk;
    private static ArrayList<Image> action;
    private static boolean up;
    private static boolean down;
    private static boolean right;
    private static boolean left;
    private static Tool pickaxe;
    private static Tool axe;
    private static Tool hoe;
    private static Tool sword;
    private static boolean hit;
    private static boolean pickaxing;
    private static boolean axing;
    private static boolean hoeing;
    private static int actionFrame;
    private static int previuosState;
    private static int woodStack;
    private static int stoneStack;
    private static int masteryLevel;
    private static Image wood;
    private static Image stone;
    private static AVL<Achievement> achievements;

    public Avatar(Canvas canvas, double x, double y) {

        lastPosition = new Position(x, y);
        Avatar.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        position = new Position(x, y);
        frontIdles = new ArrayList<>();
        backIdles = new ArrayList<>();
        rightWalk = new ArrayList<>();
        frontWalk = new ArrayList<>();
        backWalk = new ArrayList<>();
        leftWalk = new ArrayList<>();
        action = new ArrayList<>();
        hasPickedTools = false;
        interacting = false;
        pickaxe=new Tool(Avatar.canvas,"pickaxeTool");
        axe=new Tool(Avatar.canvas,"axeTool");
        hoe=new Tool(Avatar.canvas,"hoeTool");
        sword=new Tool(Avatar.canvas,"swordTool");
        pickaxing=false;
        axing=false;
        hoeing=false;
        actionFrame=0;
        previuosState=state;
        woodStack=0;
        stoneStack=0;
        masteryLevel=1;
        wood=new Image(getClass().getResourceAsStream("/animation/other/wood.png"),64,64, false, true);
        stone= new Image(getClass().getResourceAsStream("/animation/other/stone.png"),64,64, false, true);
        achievements= new AVL<>();
        Avatar.newAchievement("Your new Home", "Start a new Stardew Valley journey");
        for (int i = 1; i <= 6; i++) {
            Image frontIdle = new Image(getClass().getResourceAsStream("/animation/frames/front-idle/idle_0" + i + ".png"),64,64,true,true);
            frontIdles.add(frontIdle);
        }

        for (int i = 1; i <= 6; i++) {
            Image backIdle = new Image(getClass().getResourceAsStream("/animation/frames/back-idle/back-idle_0" + i + ".png"),64,64,true,true);
            backIdles.add(backIdle);
        }

        for (int i = 1; i <= 6; i++) {
            Image rightWalk = new Image(getClass().getResourceAsStream("/animation/frames/side-walk/side-walk_0" + i + ".png"),64,64,true,true);
            this.rightWalk.add(rightWalk);
        }

        for (int i = 1; i <= 6; i++) {
            Image frontWalk = new Image(getClass().getResourceAsStream("/animation/frames/front-walk/front-walk_0" + i + ".png"),64,64,true,true);
            this.frontWalk.add(frontWalk);
        }

        for (int i = 1; i <= 6; i++) {
            Image backWalk = new Image(getClass().getResourceAsStream("/animation/frames/back-walk/back-walk_0" + i + ".png"),64,64,true,true);
            this.backWalk.add(backWalk);
        }

        for (int i = 1; i <= 6; i++) {
            Image leftWalk = new Image(getClass().getResourceAsStream("/animation/frames/left-walk/side-walk_0" + i + ".png"),64,64,true,true);
            this.leftWalk.add(leftWalk);
        }
        for (int i = 1; i <= 6; i++) {
            Image action = new Image(getClass().getResourceAsStream("/animation/frames/actions/actions_0" + i + ".png"),64,64,true,true);
            this.action.add(action);
        }
    }

    public static int getMasteryLevel() {
        return masteryLevel;
    }

    public static void setMasteryLevel(int masteryLevel) {
        Avatar.masteryLevel = masteryLevel;
    }

    public static int getWoodStack() {
        return woodStack;
    }

    public static void setWoodStack(int woodStack) {
        Avatar.woodStack = woodStack;
    }

    public static int getStoneStack() {
        return stoneStack;
    }

    public static void setStoneStack(int stoneStack) {
        Avatar.stoneStack = stoneStack;
    }

    public void paint() {
        onMove();
        if(state == 6 && actionFrame<6){
            Position positionForFrame=new Position(position.getX(),position.getY());
            if(actionFrame==0 || actionFrame==5){
                positionForFrame.setY(positionForFrame.getY()-14);
            }
            else{
                positionForFrame.setY(positionForFrame.getY()-2);
            }
            graphicsContext.drawImage(action.get(actionFrame), position.getX(), positionForFrame.getY());
            actionFrame++;
        } else if (state == 0) {
            graphicsContext.drawImage(frontIdles.get(frame % 6), position.getX(), position.getY());
            frame++;
        } else if (state == 1) {
            graphicsContext.drawImage(rightWalk.get(frame % 6), position.getX(), position.getY());
            frame++;
        } else if(state == 2){
            graphicsContext.drawImage(frontWalk.get(frame % 6), position.getX(), position.getY());
            frame++;
        } else if(state == 3){
            graphicsContext.drawImage(backWalk.get(frame % 6), position.getX(), position.getY());
            frame++;
        } else if(state == 4){
            graphicsContext.drawImage(backIdles.get(frame % 6), position.getX(), position.getY());
            frame++;
        } else if(state == 5){
            graphicsContext.drawImage(leftWalk.get(frame % 6), position.getX(), position.getY());
            frame++;
        }else{
            state=previuosState;
            actionFrame=0;
            graphicsContext.drawImage(frontIdles.get(frame % 6), position.getX(), position.getY());
        }
        if(hasPickedTools){
            axe.paint(960,0);
            hoe.paint(900,0);
            pickaxe.paint(835,1);
            sword.paint(770,1);
            graphicsContext.drawImage(wood, 630,1);
            graphicsContext.drawImage(stone,700,1);
        }
    }

    public static AVL<Achievement> getAchievements() {
        return achievements;
    }

    public void onMove() {
        lastPosition = new Position(position.getX(), position.getY());
        if (up) {
            position.setY(position.getY() - 8);
        }

        if (down) {
            position.setY(position.getY() + 8);
        }

        if (right) {
            position.setX(position.getX() + 8);
        }

        if (left) {
            position.setX(position.getX() - 8);
        }
    }

    public boolean isInteracting() {
        return interacting;
    }

    public boolean isHitting(){
        return hit;
    }

    public void setOnKeyPressed(KeyEvent event) {
        if(state!=6){
            switch (event.getCode()) {
                case UP -> {
                    state = right || left ? right ? 1 : 5 : 3;
                    up = true;
                }
                case DOWN -> {
                    state = right || left ? right ? 1 : 5 : 2 ;
                    down = true;
                }
                case RIGHT -> {
                    state = 1;
                    right = true;
                }
                case LEFT -> {
                    state = 5;
                    left = true;
                }
                case E->{
                    interacting=true;
                }
                case F->{
                    if(hasPickedTools && sword.getDurability()>0){
                        previuosState=state;
                        state=6;
                        hit=true;
                    }
                }
                case Z->{
                    if(hasPickedTools && pickaxe.getDurability()>0){
                        previuosState=state;
                        state=6;
                        pickaxing=true;
                    }
                }
                case X->{

                    if(hasPickedTools && hoe.getDurability()>0){
                        previuosState=state;
                        state=6;
                        hoeing=true;
                    }
                }
                case C->{
                    if(hasPickedTools && axe.getDurability()>0){
                        previuosState=state;
                        state=6;
                        axing=true;
                    }
                }
            }
        }
    }

    public void setOnKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> {
                state = right || left || down ? right ? 1 : left ? 5 : 2 : 4;
                up = false;
                Avatar.newAchievement("Up", "Move up");
            }
            case DOWN -> {
                state = right || left || up ? right ? 1 : left ? 5 : 3 : 0;
                down = false;
                Avatar.newAchievement("Down", "Move down");
            }
            case RIGHT -> {
                state = up ||  left || down  ? up ? 3 : left ?  5 : 2 : 0 ;
                right = false;
                Avatar.newAchievement("Right", "Move right");
            }
            case LEFT -> {
                state = up ||  right || down  ? up ? 3 : right ?  1: 2 : 0 ;
                left = false;
                Avatar.newAchievement("Left", "Move left");
            }
            case E->{
                interacting=false;
            }
            case F->{
                hit=false;
                Avatar.newAchievement("Sword Master", "Use the sword for the first time");
            }
            case Z->{
                pickaxing=false;
                Avatar.newAchievement("Miner", "Use the pickaxe for the first time");
            }
            case X->{
                hoeing=false;
                Avatar.newAchievement("Farmer", "Use the hoe for the first time");
            }
            case C->{
                axing=false;
                Avatar.newAchievement("Leviathan", "Use the axe for the first time");
            }
            case H->{
                if(sword.getDurability()<10 && woodStack>=1 && stoneStack>=2){
                    sword.setDurability(10);
                    setStoneStack(getStoneStack()-2);
                    setWoodStack(getWoodStack()-1);
                    Avatar.newAchievement("Blacksmith", "Repair a tool");
                }
            }
            case J->{
                if(pickaxe.getDurability()<10 && woodStack>=2 && stoneStack>=3){
                    pickaxe.setDurability(10);
                    setStoneStack(getStoneStack()-3);
                    setWoodStack(getWoodStack()-2);
                    Avatar.newAchievement("Blacksmith", "Repair a tool");
                }
            }
            case K->{
                if(axe.getDurability()<10 && woodStack>=2 && stoneStack>=2){
                    axe.setDurability(10);
                    setStoneStack(getStoneStack()-2);
                    setWoodStack(getWoodStack()-2);
                    Avatar.newAchievement("Blacksmith", "Repair a tool");
                }
            }
            case L->{
                if(hoe.getDurability()<10 && woodStack>=1 && stoneStack>=1){
                    hoe.setDurability(10);
                    setStoneStack(getStoneStack()-1);
                    setWoodStack(getWoodStack()-1);
                    Avatar.newAchievement("Blacksmith", "Repair a tool");
                }
            }
        }
    }

    public static void actionAnimation() {
        Thread animationThread = new Thread(() -> {
            try {
                // Cambiar el estado a "sit" y animar
                state = 6;
                for (int i = 0; i < action.size(); i++) {
                    graphicsContext.drawImage(action.get(i), position.getX(), position.getY());
                    Thread.sleep(200); // Controla la velocidad de la animación de sentarse
                }
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        animationThread.start();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    public GraphicsContext getGraphicsContext(){
        return graphicsContext;
    }

    public int getWidth(){
        return 64;
    }

    public Tool getPickaxe() {
        return pickaxe;
    }

    public Tool getAxe() {
        return axe;
    }

    public Tool getHoe() {
        return hoe;
    }

    public boolean isPickaxing() {
        return pickaxing;
    }

    public void setPickaxing(boolean pickaxing) {
        Avatar.pickaxing = pickaxing;
    }

    public boolean isAxing() {
        return axing;
    }

    public void setAxing(boolean axing) {
        Avatar.axing = axing;
    }

    public boolean isHoeing() {
        return hoeing;
    }

    public void setHoeing(boolean hoeing) {
        Avatar.hoeing = hoeing;
    }

    public double getHeight() {
        return 64;
    }

    public boolean hasPickedTools() {
        return hasPickedTools;
    }

    public void setHasPickedTools(boolean hasPickedTools) {
        this.hasPickedTools = hasPickedTools;
    }

    public Tool getSword() {
        return sword;
    }

    public int getState() {
        return state;
    }

    public ArrayList<Image> getAction() {
        return action;
    }

    public static void newAchievement(String name, String description){
        achievements.insert(new Achievement(name,description));
    }
}
