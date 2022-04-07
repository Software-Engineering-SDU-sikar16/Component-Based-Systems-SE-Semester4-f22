package dk.sdu.mmmi.cbse.enemysystem;



public class EnemyControlSystem implements IEntityProcessingService {

    int numPoints = 6;
    Random rnd = new Random(10);
    float angle = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);


            float speed = (float) Math.random() * 10f + 40f;
            if (rnd.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setLeft(true);
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            updateShape(enemy);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity enemy) {

        PositionPart positionPart = enemy.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        float[] shapex = new float[6];
        float[] shapey = new float[6];

        Asteroid asAsteroid = (Asteroid) enemy;
//        SplitterPart splitter = asAsteroid.getPart(SplitterPart.class);
        if(asAsteroid.getSize().equals("LARGE")){
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if(asAsteroid.getSize().equals("MEDIUM")){
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 16;
                shapey[i] = y + (float) Math.sin(angle + radians) * 16;
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if(asAsteroid.getSize().equals("SMALL")){
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / numPoints;
            }
        }


        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);
    }

}