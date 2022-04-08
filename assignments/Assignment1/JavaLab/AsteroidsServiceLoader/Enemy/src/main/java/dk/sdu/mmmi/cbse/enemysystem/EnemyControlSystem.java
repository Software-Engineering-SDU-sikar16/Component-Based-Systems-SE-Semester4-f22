package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;


/**
 *
 * @author sikar16
 */

public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {

            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            // Random movement for the enemy taken off the web
            double randomMovement = (Math.random() * 100);
            if (randomMovement < 40) {
                movingPart.setLeft(false);
                movingPart.setRight(false);
                movingPart.setUp(true);
            } else if (randomMovement > 50 && randomMovement < 90) {
                movingPart.setLeft(false);
                movingPart.setRight(true);
                movingPart.setUp(false);
            } else if (randomMovement > 40 && randomMovement < 50) {
                movingPart.setLeft(true);
                movingPart.setRight(false);
                movingPart.setUp(false);
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            updateShape(enemy);
        }
    }

    private void updateShape(Entity enemy) {
        float[] shapex = enemy.getShapeX();
        float[] shapey = enemy.getShapeY();
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

//        enemy.setColor(250/255f, 163/255f, 82/255f, 1);
        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);
    }

}
