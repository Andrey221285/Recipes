import java.util.Scanner;

class Robot {

    private String CPU;
    private int legs;
    private int hands;
    private int eyes;

    Robot(String CPU, int legs, int hands, int eyes) {
        this.CPU = CPU;
        this.legs = legs;
        this.hands = hands;
        this.eyes = eyes;
    }

    public static class RobotBuilder {
        private String CPU;
        private int legs;
        private int hands;
        private int eyes;

        /* write your code here (fields) */

        public RobotBuilder() {
        }

        RobotBuilder setCPU(String cpu) {
            this.CPU=cpu;
            return this;
            /* write your code here */
        }

        RobotBuilder setLegs(int legs) {
            this.legs=legs;
            /* write your code here */
            return this;
        }

        RobotBuilder setHands(int hands) {
            this.hands=hands;
            /* write your code here */
            return this;
        }

        RobotBuilder setEyes(int eyes) {
            /* write your code here */
            this.eyes=eyes;
            return this;
        }

        Robot build() {
            return new Robot(CPU,legs,hands,eyes);
            /* write your code here */
        }
    }

    @Override
    public String toString() {
        return "CPU : " + CPU + "\n" +
                "Legs : " + legs + "\n" +
                "Hands : " + hands + "\n" +
                "Eyes : " + eyes + "\n";
    }
}

class TestDrive {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Robot.RobotBuilder robotBuilder = new Robot.RobotBuilder();/* write your code here */
        /* Set CPU */
        robotBuilder.setCPU("Intel Core i5");
        /* Would like to set legs? */
        int legs = scanner.nextInt();
        robotBuilder.setLegs(legs);
        /* Would like to set hands? */
        int hands = scanner.nextInt();
        robotBuilder.setHands(hands);
        /* Would like to set eyes? */
        int eyes = scanner.nextInt();
        robotBuilder.setEyes(eyes);

        Robot robot = robotBuilder.build();/* write your code here */

        System.out.println(robot);
        scanner.close();
    }
}