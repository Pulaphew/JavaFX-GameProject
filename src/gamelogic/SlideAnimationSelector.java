package gamelogic;

import java.util.Random;
import entity.Enemy;
import entity.Narong;
import entity.Nattee;
import entity.Pto;

public class SlideAnimationSelector {

    private static final Random rand = new Random();

    public static SlideAnimationSettings getSetting(Enemy enemy) {
        double speed = 1.0; // Default speed
        double initialPositionX = 150; // Default start position (left)
        double endToPositionX = 1039; // Default end position (right)

        if (enemy instanceof Nattee) {
            speed = 1.0; // Fixed speed
        } else if (enemy instanceof Narong) {
            // Narong has three possible speeds: 0.75, 0.5, or 1.0
            double[] narongSpeeds = {0.75, 0.5, 1.0};
            speed = narongSpeeds[rand.nextInt(narongSpeeds.length)];
        } else if (enemy instanceof Pto) {
            // Pto has four possible speeds: 0.75, 0.5, 0.25, or 1.0
            double[] ptoSpeeds = {0.75, 0.5, 0.25, 1.0};
            speed = ptoSpeeds[rand.nextInt(ptoSpeeds.length)];

            // 50% chance to start from right instead of left
            if (rand.nextBoolean()) {
                initialPositionX = 1039; // Start from right
                endToPositionX = 150; // Move to left
            }
        } else {
            throw new IllegalArgumentException("Unknown enemy type");
        }

        return new SlideAnimationSettings(speed, initialPositionX, endToPositionX);
    }

    public static class SlideAnimationSettings {
        public final double speed;
        public final double initialPositionX;
        public final double endToPositionX;

        public SlideAnimationSettings(double speed, double initialPositionX, double endToPositionX) {
            this.speed = speed;
            this.initialPositionX = initialPositionX;
            this.endToPositionX = endToPositionX;
        }
    }
}
