package game;

import java.util.*;

class Player {
    private final String name;
    private int score;

    Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }
}

public class SnakeGame {
    private static final int TARGET_SCORE = 100;
    private static final Map<Integer, Integer> snakesMap = new HashMap<>();
    private static final Map<Integer, Integer> laddersMap = new HashMap<>();
    private static final int[] choices = new int[]{1, 2, 3, 4, 5, 6};
    private static final Random random = new Random();

    private static int guessNumber() {
        return choices[random.nextInt(6)];
    }

    private static int toss() {
        return random.nextInt(100);
    }

    public static String checkForNearBySnakes(int currentScore) {
        boolean snakePresent = false;
        StringBuilder res = new StringBuilder("Wish to NOT get { ");
        for (int i = 1; i <= 6; i++) {
            if (snakesMap.containsKey(currentScore + i)) {
                snakePresent = true;
                res.append(i).append(" or ");
            }
        }
        res.append("} as Snakes are there");
        return snakePresent ? res.toString().replace(" or }", " }") : "Don't be fear as NO snakes are present near by";
    }

    private static String checkForNearByLadders(int currentScore) {
        boolean ladderPresent = false;
        StringBuilder res = new StringBuilder("Get { ");
        for (int i = 1; i <= 6; i++) {
            if (laddersMap.containsKey(currentScore + i)) {
                ladderPresent = true;
                res.append(i).append(" or ");
            }
        }
        res.append("} to climb the Ladder");
        return ladderPresent ? res.toString().replace(" or }", " }") : "No Ladder is near by";
    }

    private static void loadLaddersAndSnakes() {
        snakesMap.put(26, 11);
        snakesMap.put(52, 29);
        snakesMap.put(62, 19);
        snakesMap.put(66, 59);
        snakesMap.put(74, 17);
        snakesMap.put(89, 69);
        snakesMap.put(95, 75);
        snakesMap.put(98, 79);

        laddersMap.put(4, 14);
        laddersMap.put(9, 31);
        laddersMap.put(18, 45);
        laddersMap.put(21, 42);
        laddersMap.put(28, 84);
        laddersMap.put(51, 67);
        laddersMap.put(71, 91);
        laddersMap.put(78, 97);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many Players: ");
        int noOfPlayers = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++) {
            System.out.print("Enter Player " + (i + 1) + " Name: ");
            String playerName = scanner.nextLine().trim();
            Player player = new Player(playerName);
            players.add(player);
        }
        if (noOfPlayers == 1) {
            Player player = new Player("Computer");
            players.add(player);
            noOfPlayers++;
        }
        System.out.println("Just use Enter button on the Keyboard to play... \n");

        loadLaddersAndSnakes();
        int counter = toss();
        while (true) {
            Player currentPlayer = players.get(counter % noOfPlayers);
            String currentPlayerName = currentPlayer.getName();
            int playerScore = currentPlayer.getScore();
            System.out.print(currentPlayerName + "'s turn, you're at { " + playerScore + " } \n" +
                    checkForNearByLadders(playerScore) + " and " +
                    checkForNearBySnakes(playerScore) + ": ");

            if(!currentPlayerName.equalsIgnoreCase("Computer"))
                scanner.nextLine();

            int playerGuess = guessNumber();
            System.out.print("You've got " + playerGuess + " >> ");

            int playerScoreGoingTobe = playerScore + playerGuess;

            if (laddersMap.containsKey(playerScoreGoingTobe)) {
                currentPlayer.setScore(laddersMap.get(playerScoreGoingTobe));
                System.out.print("You've climbed the ladder at " + playerScoreGoingTobe + " and you get ONE MORE chance >> ");
                counter--;
            } else if (snakesMap.containsKey(playerScoreGoingTobe)) {
                currentPlayer.setScore(snakesMap.get(playerScoreGoingTobe));
                System.out.print("You've got snake bite at " + playerScoreGoingTobe + " >> ");
            } else {
                currentPlayer.setScore(playerScoreGoingTobe);
            }

            playerScore = currentPlayer.getScore();
            System.out.print("Your latest score is " + playerScore + "\n\n");

            if (playerScore >= TARGET_SCORE) {
                System.out.println(currentPlayerName.toUpperCase() + " won the game...");
                players.remove(currentPlayer);
                if (players.size() < 2) {
                    break;
                }
            }

            counter++;
        }
    }
}
