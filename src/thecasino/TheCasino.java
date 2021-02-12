package thecasino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TheCasino {

    private Scanner input = new Scanner(System.in);
    private ArrayList<UserID> userIDLogs = new ArrayList<>();
    private Random rand = new Random();
    private int[] deckOfCards = new int[13];
    private UserID currentUser = null;
    private AccountInfo usersDb = null;

    public TheCasino() throws IOException {
        this.usersDb = new AccountInfo("users.csv");
    
    }
    
    
    
    
    public void introduction() throws InterruptedException, IOException {
        

        System.out.println("Welcome to The Casino! We have a wonderful selection of games for you to play!");
        System.out.println("If you're a long time player, welcome back! Just enter 'sign in' and you can enjoy yourself.");
        System.out.println("If you're a newbie, that's brilliant! But you're going to have to 'sign up' to play here!");
        System.out.println("What would you like to do?");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("3. Exit");
        int userChoice = input.nextInt();
        switch (userChoice) {
            case 1:
                signIn();
                break;

            case 2:
                signUp();
                break;

            case 3:
                System.out.println("Shame, come by another day!");
                exitCasino();
        }
    }

    public void signIn() throws IOException {
        System.out.println("Please enter your username");
        String username = input.next();
        System.out.println("Now enter your password");
        String password = input.next();
        
        UserID user = usersDb.getUser(username);
        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid credentials");
            signIn();
        } else {
            this.currentUser = user;
            System.out.println("Successfully signed in as " + user.getUsername());
        }
    }

    public void signUp() throws IOException {
        
        System.out.println("Welcome, newbie! Lets get you set up with an account");
        System.out.println("Please enter a username:");
        String username = input.next();
        System.out.println("Now enter a password:");
        String password = input.next();
        int currency = 10;

        UserID newUser = new UserID(username, password, currency);
        usersDb.addUser(newUser);
        this.currentUser = newUser;
        System.out.println("Fantastic! You've got your account all set up now newbie!\nLets put some free mula in your account, eh?");
        System.out.println("Ive added 10 Wheat Grains into your account!");
        System.out.println("Let's get started, shall we?");

    }

    public void higherOrLower() {
        System.out.println("Higher or lower selected, would you like to learn the rules first?");
        String tutorialChoice = input.next();
        if ((tutorialChoice.equalsIgnoreCase("yes")) || (tutorialChoice.equalsIgnoreCase("y"))) {
            System.out.println("The Rules:");
            System.out.println("The dealer draws a card from a deck of cards\nThe dealer will then present you with a card and ask you if the next card they draw will be higher or lower than the card just pulled\nIf your selection is correct, you continue the game and win Wheat Grains!(Fantastic!)\nIf you select wrong, you stand to lose your bet");
        }
        System.out.println("To play, you must bet 3 Wheat Grains! (type 'bet' to put your mula in or 'exit' to return to the main menu)");
        String bet = input.next();
        if (bet.equalsIgnoreCase("bet")) {
            currentUser.setCurrency(currentUser.getCurrency() - 1);
            
            if (currentUser.getCurrency() <= 2) {
                System.out.println("Sorry chap, you dont have enough Wheat Grains to bet!");
                System.out.println("Returning to main menu");
                return;
            } else {

                System.out.println("-3 WGs");
                System.out.println("Fantastic, let's get started");
            }
            if (bet.equalsIgnoreCase("exit")) {
                System.out.println("No worries, perhaps you can play something else?");
                return;
            }

            fillingDeckOfCards();

            int count = 0;
            while (true) {
                int firstCard = rand.nextInt(13);
                int nextCard = rand.nextInt(13);

                switch (firstCard) {
                    case 10:
                        System.out.println("J");
                        break;
                    case 11:
                        System.out.println("Q");
                        break;
                    case 12:
                        System.out.println("K");
                        break;
                    case 0:
                        System.out.println("Ace");
                        break;
                    default:
                        System.out.println(deckOfCards[firstCard]);
                        break;
                }

                System.out.println("Will the next card be higher or lower than this card?");
                String horGuess = input.next();
                if (nextCard > firstCard) {
                    if (horGuess.equalsIgnoreCase("higher") || (horGuess.equalsIgnoreCase("h"))) {
                        System.out.println("You are correct!");
                    } else if (horGuess.equalsIgnoreCase("lower") || (horGuess.equalsIgnoreCase("l"))) {
                        System.out.println("Sorry my friend, but your luck runs out!");
                        int winnings = (count / 2);
                        System.out.println("You've won " + winnings + " Wheat Grains!");
                        currentUser.setCurrency(currentUser.getCurrency() + winnings);
                        return;
                    }
                } else {
                    if (nextCard < firstCard) {
                        if (horGuess.equalsIgnoreCase("lower") || (horGuess.equalsIgnoreCase("l"))) {
                            System.out.println("You are correct!");
                        } else if (horGuess.equalsIgnoreCase("higher") || (horGuess.equalsIgnoreCase("h"))) {
                            System.out.println("Sorry my friend, but your luck runs out!");
                            int winnings = (count / 2);
                            System.out.println("You've won " + winnings + " Wheat Grains!");
                            currentUser.setCurrency(currentUser.getCurrency() + winnings);
                            return;
                        }
                    }
                }
                count++;
            }

        }

    }

    public int fillingDeckOfCards() {
        for (int i = 0; i < 13; i++) {
            deckOfCards[i] = i;
        }
        int randomNumber = rand.nextInt(13);
        return deckOfCards[randomNumber] + 1;
    }

    public void slotMachine() {
        System.out.println("Slot Machine selected, would you like to learn the rules of the machine first?");
        String tutorialChoice = input.next();
        if ((tutorialChoice.equalsIgnoreCase("yes")) || (tutorialChoice.equalsIgnoreCase("y"))) {
            System.out.println("To use the slot machine, you must pay 1 Wheat Grain into the machine. Then, the slot machine will be activated and you'll be presented with three fruits\n Depending on the fruit, if they match, you shall win a certain amount of Wheat Grains!(everyone loves Wheat Grains!)\nBut be careful, some fruits may cause you to LOSE Wheat Grains!\nThe fruits shall appear as their first letter.");
            System.out.println("Heres a list from worst to best prizes!");
            System.out.println("Grape - Lose a Wheat Grain\nLemon - win 1 Wheat Grain\nOrange - win 3 Wheat Grains\nBanana - win 10 Wheat Grains\nMango - win 50 Wheat Grains\nStarfruit - win 500 Wheat Grains");
            System.out.println("Happy playing!");
        }
        while (true) {
            System.out.println("Enter a Wheat Grain to spin! (type 'spin' to put your mula in or 'exit' to return to the main menu)");
            String spin = input.next();
            if (spin.equalsIgnoreCase("spin")) {
                currentUser.setCurrency(currentUser.getCurrency() - 1);
                if (currentUser.getCurrency() <= 0) {
                    System.out.println("Sorry chap, you dont have enough Wheat Grains to bet!");
                    System.out.println("Returning to main menu");
                    return;
                } else {
                    System.out.println("-1 WG");
                }
                if (spin.equalsIgnoreCase("exit")) {
                    System.out.println("No worries, perhaps you can play something else?");
                    return;
                }
            }
            int slot1 = rand.nextInt(60);
            int slot2 = rand.nextInt(60);
            int slot3 = rand.nextInt(60);

            if (slot1 <= 10) {
                System.out.print("|G| ");
            } else if (slot1 <= 30) {
                System.out.print("|L| ");
            } else if (slot1 <= 40) {
                System.out.print("|O| ");
            } else if (slot1 <= 50) {
                System.out.print("|B| ");
            } else if (slot1 <= 55) {
                System.out.print("|M| ");
            } else if (slot1 == 60) {
                System.out.print("|SF| ");
            }

            if (slot2 <= 10) {
                System.out.print("|G| ");
            } else if (slot2 <= 30) {
                System.out.print("|L| ");
            } else if (slot2 <= 40) {
                System.out.print("|O| ");
            } else if (slot2 <= 50) {
                System.out.print("|B| ");
            } else if (slot2 <= 55) {
                System.out.print("|M| ");
            } else if (slot2 == 60) {
                System.out.print("|SF| ");
            }

            if (slot3 <= 10) {
                System.out.print("|G|");
            } else if (slot3 <= 30) {
                System.out.print("|L|");
            } else if (slot3 <= 40) {
                System.out.print("|O|");
            } else if (slot3 <= 50) {
                System.out.print("|B|");
            } else if (slot3 <= 55) {
                System.out.print("|M|");
            } else if (slot3 == 60) {
                System.out.print("|SF|");
            }

            if ((slot1 <= 10) && (slot2 <= 10) && (slot3 <= 10)) {
                System.out.println("All grapes! You lose a Wheat Grain");
            } else if ((slot1 <= 30) && (slot2 <= 30) && (slot3 <= 30)) {
                System.out.println("All lemons! You win a Wheat Grain");

            } else if ((slot1 <= 40) && (slot2 <= 40) && (slot3 <= 40)) {
                System.out.println("All oranges! You win 3 Wheat Grains");

            } else if ((slot1 <= 50) && (slot2 <= 50) && (slot3 <= 50)) {
                System.out.println("All bananas! You win 10 Wheat Grains");
            } else if ((slot1 <= 55) && (slot2 <= 55) && (slot3 <= 55)) {
                System.out.println("All mangos! You win 50 Wheat Grains!!");
            } else if ((slot1 == 60) && (slot2 == 60) && (slot3 == 60)) {
                System.out.println("All Star Fruit!! You win 500 Wheat Grains!! Very well done!");
            } else {
                System.out.println("Sorry, no win.");
            }
        }
    }

    public void mineWG() {
        System.out.println("Welcome, young Wheat farmer! It's time to mine some Wheat Grains!");
        System.out.println("To mine Wheat Grains, you must answer a series of difficult mathematics questions and you will be rewarded with Wheat Grains!");
        System.out.println("Let's get started, to stop at any time, type either '0' or '1'.");
        int count = 0;
        while (true) {
            int num1 = rand.nextInt(10000);
            int num2 = rand.nextInt(10000);
            int operation = rand.nextInt(4);

            if (operation == 0) {
                System.out.println(num1 + " + " + num2);
                double userGuess = input.nextDouble();
                double actualAnswer = num1 + num2;
                if (userGuess == actualAnswer) {
                    System.out.println("Correct");
                    count++;
                } else if (userGuess == 0 || userGuess == 1) {
                    System.out.println("Exiting Wheat Grain farm");
                    System.out.println("You earned " + count / 5 + " Wheat Grains!");
                    currentUser.setCurrency(currentUser.getCurrency() + (count / 5));
                    return;
                } else {
                    System.out.println("Incorrect");
                }

            }
            if (operation == 1) {
                System.out.println(num1 + " - " + num2);
                double userGuess = input.nextDouble();
                double actualAnswer = num1 - num2;
                if (userGuess == actualAnswer) {
                    System.out.println("Correct");
                    count++;
                } else if (userGuess == 0 || userGuess == 1) {
                    System.out.println("Exiting Wheat Grain farm");
                    System.out.println("You earned " + count / 5 + " Wheat Grains!");
                    currentUser.setCurrency(currentUser.getCurrency() + (count / 5));
                    return;
                } else {
                    System.out.println("Incorrect");
                }

            }
            if (operation == 2) {
                System.out.println(num1 + " * " + num2);
                double userGuess = input.nextDouble();
                double actualAnswer = num1 * num2;
                if (userGuess == actualAnswer) {
                    System.out.println("Correct");
                    count++;
                } else if (userGuess == 0 || userGuess == 1) {
                    System.out.println("Exiting Wheat Grain farm");
                    System.out.println("You earned " + count / 5 + " Wheat Grains!");
                    currentUser.setCurrency(currentUser.getCurrency() + (count / 5));
                    return;
                } else {
                    System.out.println("Incorrect");
                }

            }
            if (operation == 3) {
                System.out.println(num1 + " / " + num2);
                double userGuess = input.nextDouble();
                double actualAnswer = num1 / num2;
                if (userGuess == actualAnswer) {
                    System.out.println("Correct");
                    count++;
                } else if (userGuess == 0 || userGuess == 1) {
                    System.out.println("Exiting Wheat Grain farm");
                    System.out.println("You earned " + count / 5 + " Wheat Grains!");
                    currentUser.setCurrency(currentUser.getCurrency() + (count / 5));
                    return;
                } else {
                    System.out.println("Incorrect");
                }

            }
            if (operation == 4) {
                System.out.println(num1 + " % " + num2);
                double userGuess = input.nextDouble();
                double actualAnswer = num1 % num2;
                if (userGuess == actualAnswer) {
                    System.out.println("Correct");
                    count++;
                } else if (userGuess == 0 || userGuess == 1) {
                    System.out.println("Exiting Wheat Grain farm");
                    System.out.println("You earned " + count / 5 + " Wheat Grains!");
                    currentUser.setCurrency(currentUser.getCurrency() + (count / 5));
                    return;
                } else {
                    System.out.println("Incorrect");
                }

            }

        }

    }

    public void exitCasino() throws IOException {
        usersDb.storeUsers();
        System.out.println("Farewell my friend");
        System.exit(0);
    }
    
    private void signOut() throws InterruptedException, IOException {
        usersDb.updateUser(currentUser);
        this.currentUser = null;
        introduction();
    }
    
    
    public static void main(String[] args) throws InterruptedException, IOException {
        TheCasino casino = new TheCasino();
        casino.introduction();

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("We have a selection of games here: ");
            System.out.println("1. Higher or lower");
            System.out.println("2. Slot machines (Player's favourite!)");
            System.out.println("3. BlackJack");
            System.out.println("4. Farm Wheat Grains");
            System.out.println("5. Check Wheat Grains");
            System.out.println("6. Sign Out");
            System.out.println("Please, choose a game my friend (select the number)");
            int gameChoice = input.nextInt();
            switch (gameChoice) {
                case 1:
                    casino.higherOrLower();
                    break;

                case 2:
                    casino.slotMachine();
                    break;

                case 3:
//                  casino.blackJack();
                    break;

                case 4:
                    casino.mineWG();
                    break;

                case 5:
                    
                    break;
                case 6:
                    casino.signOut();
                    break;
            
                default:
                    System.out.println("Unsupported input");

            }
        }
    }

    

}
