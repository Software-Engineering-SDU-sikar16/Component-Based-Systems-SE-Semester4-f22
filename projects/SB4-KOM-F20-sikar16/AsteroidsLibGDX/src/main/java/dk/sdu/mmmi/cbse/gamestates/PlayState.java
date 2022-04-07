package dk.sdu.mmmi.cbse.gamestates;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.lwjgl.Sys;

import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;
    private Enemy enemy;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();
		
		player = new Player();
        enemy = new Enemy();
		
	}
	
	public void update(float dt) {
		
		handleInput();
        enemyRandom();
		
        // System.out.println(dt);
		player.update(dt);
        enemy.update(dt);
		
	}
	
	public void draw() {
		player.draw(sr);
        enemy.draw(sr);
	}
	
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));

        
        
       

        // enemy.setLeft(GameKeys.isDown(GameKeys.RIGHT));
		// enemy.setRight(GameKeys.isDown(GameKeys.LEFT));
		// enemy.setUp(GameKeys.isDown(GameKeys.UP));
	}

    public void enemyRandom() {
        // enemy needs to be randomized and shoot
        // while (true) {
        //     // TimeUnit.SECONDS.sleep(1);
        //     Random rand = new Random();
        //     float r = rand.nextFloat();
        //     if (r > 0.5f) {
        //         enemy.setLeft(true);
        //     }
        //     else {
        //         enemy.setRight(true);
        //     }
        //     enemy.setUp(true);
        // }
        // enemy.setUp(true);
        // try {
        //     TimeUnit.MILLISECONDS.sleep(5);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // enemy.setRight(true);

        ////
        // TimerTask randomizer = new TimerTask() {
        //     public void run(){
        //         Random rand = new Random();
        //         float r = rand.nextFloat();
        //         System.out.println(r);
        //         if (r > 0.5) {
        //             enemy.setLeft(true);
        //         }
        //         else {
        //             enemy.setRight(true);
        //         }
        //         enemy.setUp(true);
        //     }
        // };
        // Timer timer = new Timer(true);
        // timer.schedule(randomizer, 1000, 100000); 

        Random rand = new Random();
        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        float r = rand.nextFloat();
        // System.out.println(r);
        if ( 0.1 < r && r < 0.9) {
            enemy.setUp(true);
            enemy.setLeft(true);
        }
        else {
            enemy.setRight(true);
        }
        

        enemy.setLeft(false);
        enemy.setRight(false);
        enemy.setUp(false);
        
    }

    // Timer timer = new Timer();
    // timer.schedule(, 0, 1)
	
	public void dispose() {}
	
}









