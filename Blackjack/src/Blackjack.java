
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            playGame();

            System.out.print("Хотите сыграть еще? (да/нет): ");
            String answer = scanner.nextLine().toLowerCase();
            playAgain = answer.equals("да");
        }

        scanner.close();
        System.out.println("Спасибо за игру!");
    }

    static void playGame() {
        List<Integer> playerHand = new ArrayList<>();
        List<Integer> dealerHand = new ArrayList<>();

        // Начальные карты
        dealCard(playerHand);
        dealCard(playerHand);
        dealCard(dealerHand);
        dealCard(dealerHand);

        System.out.println("Ваши карты: " + playerHand + ", сумма: " + calculateHandValue(playerHand));
        System.out.println("Карта дилера: " + dealerHand.get(0) + ", ?");

        // Ход игрока
        while (calculateHandValue(playerHand) < 21) {
            System.out.print("Взять карту? (да/нет): ");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("да")) {
                dealCard(playerHand);
                System.out.println("Ваши карты: " + playerHand + ", сумма: " + calculateHandValue(playerHand));
            } else {
                break;
            }
        }

        // Ход дилера
        if (calculateHandValue(playerHand) <= 21) {
            System.out.println("Карты дилера: " + dealerHand + ", сумма: " + calculateHandValue(dealerHand));
            while (calculateHandValue(dealerHand) < 17) {
                dealCard(dealerHand);
                System.out.println("Дилер взял карту: " + dealerHand.get(dealerHand.size() - 1));
                System.out.println("Карты дилера: " + dealerHand + ", сумма: " + calculateHandValue(dealerHand));
            }
        }

        // Определение победителя
        determineWinner(playerHand, dealerHand);
    }

    static void dealCard(List<Integer> hand) {
        int card = random.nextInt(11) + 1; //  Случайное число от 1 до 11
        if (card > 10) { // Туз может быть равен 1 или 11
            card = 10;
        }
        hand.add(card);
    }

    static int calculateHandValue(List<Integer> hand) {
        int value = 0;
        int aces = 0;
        for (int card : hand) {
            if (card == 1) {
                aces++;
            }
            value += card;
        }
        // Туз = 11, если не перебор
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }

    static void determineWinner(List<Integer> playerHand, List<Integer> dealerHand) {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (playerValue > 21) {
            System.out.println("Перебор! Вы проиграли.");
        } else if (dealerValue > 21) {
            System.out.println("Дилер перебрал! Вы выиграли!");
        } else if (playerValue > dealerValue) {
            System.out.println("Вы выиграли!");
        } else if (dealerValue > playerValue) {
            System.out.println("Вы проиграли.");
        } else {
            System.out.println("Ничья!");
        }
    }
}