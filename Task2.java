import java.util.Random;
import java.util.Scanner;

public class Task2 {

	public static void main(String[] args) {
		int min_number = 1;
		int max_number = 100;
		int max_attempts = 10;
		int rounds = 3;
		int totalscore = 0;
		System.out.println("Number Guessing Game...\nRules:Total number of rounds: 3 \nYou only have 10 attemps to guess the number in each round.\n");
		Scanner scanner = new Scanner(System.in);
		for(int i=1;i<=rounds;i++) {
			
			Random random = new Random();
			int randomnumber =random.nextInt(min_number, max_number+1);
			
			int attempts = 0;
			while(attempts < max_attempts) {
				System.out.printf("\nROUND: %d \nGuess the Number between %d and %d :",i,min_number,max_number);
				int userinput = scanner.nextInt();
				attempts++;
                if(userinput>100 || userinput<1){
                    System.out.println("Guess the number between the range!!!");
                }
				if(userinput==randomnumber) {
					int score = (max_attempts - attempts)*10;
					totalscore = totalscore+score;
					System.out.printf("Congrats! You guessed right.\nRound %d score = %d\n",i,score);
					break;
				}
				else if(userinput< randomnumber) {
					System.out.printf("You missed!, Guess Higher Again... \nyou have %d attempts left\n",max_attempts-attempts);
				}
				else if(userinput> randomnumber) {
					System.out.printf("You missed!, Guess Lesser Again... \nYou have %d attempts left\n",max_attempts-attempts);
				}
			}
			if(attempts==max_attempts) {
				System.out.printf("The guessed number is %d\nYOU LOSE!!!\n",randomnumber);
			}
		}
		System.out.printf("GAME OVER.Total Score = %d\n", totalscore);
		scanner.close();
	}

}
