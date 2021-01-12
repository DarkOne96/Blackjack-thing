import java.util.*;
/**
*This is a simulated game of Blackjack
*@author Adriane Almaquer
*/
public class Blackjack {
	//statics
	/**
	 * This is a collection of variables used in the methods below
	 */
	static Map <String, Integer> Deck= new HashMap<String,Integer>();//global map
	static Random number = new Random();
	static int Dealer_Score=0,Player_Score=0,
		Dealer_Hand_Total=0,Player_Hand_Total=0,
		Card_Remaining_Counter=0;
	static List<Integer> Dealer_Hand= new ArrayList<Integer>();
	static List<Integer> Player_Hand= new ArrayList<Integer>();
	static List<String> Input_List=new ArrayList<String> (Arrays.asList("hit","stand","surrender","quit"));
	static String input="start";
	//methods
	/**
	 * Creates the deck for the game.
	 */
	static void Deck_Start(){
		 for(int i=1; i<14;i++) { //Deck maker 
			 Deck.put(String.valueOf(i), 4); 
		 }
		 Card_Remaining_Counter=52; //52 cards
	}
	/**
	 * Draws a random card using a random number between 1-13.
	 * Map was used as the keys represent the cards and the values at those keys as the number of cards left for each keys.
	 * The method will recurse if the value at that key is 0, which means no card left at that key.
	 * In order to display the result, it will call another method called Card_Display, after decrementing by 1 to indicate the it has drawn a card from that key.
	 * It will also decrement by 1 to indicate that a card was removed from the total number of cards.
	 * And finally it will return the result.
	 * @return result
	 */
	static Integer Deck_Drawer() {
		int result = number.nextInt(13)+1; //random 1-13
		for (int i=1;;i++)/*loop until break*/ {
			if (Deck.get(String.valueOf(result))==0) {
				Deck_Drawer(); //reroll
				}
			else  {
				Deck.put(String.valueOf(result), Deck.get(String.valueOf(result))-1);//change counter/card amount
				System.out.print(Card_Display(result)); //display
				}
				break;
			}
		Card_Remaining_Counter-=1;//decrement by 1
		return result;
	}
	/**
	 * This method returns the equivalent value of the integer card in the game of Blackjack.
	 * @param card 
	 * @return output
	 */
	static String Card_Display(int card) { //card display
		String output="";
		switch(card) {
			case 13:
				output="[K]";//king
				break;
			case 12:
				output="[Q]";//queen
				break;
			case 11:
				output="[J]";//jack
				break;
			case 1:
				output="[1/11]";//1
				break;
			default:
				output="["+String.valueOf(card)+"]";//10 below
		}
		return output;
		
	}
	/**
	 * This method sums up the values of the cards inside the integer list based on the rules of Blackjack
	 * @param input A list of integers that represents a hand of cards
	 * @return total
	 */
	static Integer Card_Sum(List<Integer> input)/*created for purpose of totaling card amount*/ {
		int total= 0;
		for (int i=0;i<input.size();i++) {//check value before summing
			if (input.get(i)==11||input.get(i)==12||input.get(i)==13) {//if people
				total+=10;
			}
			else if(input.get(0)==1&&input.get(1)==1) {
				total=12;
			}
			else if(input.get(i)==1&&input.size()<3) { //if hand has an ace
				total+=11;	
			}	
			else {
				total+=input.get(i);
			}
		}
		return total; //first card is total here
	}
	/**
	 * This method compares the hands of two parties to determine who is the winner at that turn.
	 * @param dealer The dealer
	 * @param player The player/user
	 */
	static void Card_Calculator(int dealer, int player) { //compare two hands
		if(dealer==player) {
			System.out.println("Draw");	
		}
		else if(dealer<player&&player<=21) {
			System.out.println("Player Win");
			Player_Score+=1;
		}
		else {
			System.out.println("Dealer Wins");
			Dealer_Score+=1;
		}
	
	}
	/**
	 * This method prints the current scoreboard then clears the hand of both parties in preparation for the next turn.
	 */
	static void Card_Reset() {//reset hand and total
		System.out.println("\nScores:\nDealer: "+Dealer_Score+"     Player: "+Player_Score);
		System.out.println("___________________________________________");
		Player_Hand_Total=0;
		Dealer_Hand_Total=0;
		Dealer_Hand.clear();
		Player_Hand.clear();
		input="reset";
	}
	/**
	 * The main part of the program.
	 * First, it will create a deck using Deck_start.It Then it will draw a pair of cards for two parties using Deck_Drawer.
	 * It will check for any blackjack else it will ask the player to enter a keyword for an action. Then the dealer will
	 * respond based on the current status of the turn.
	 * The flow of the program will go back at the start of mainloop after a turn has completed.
	 * @param args
	 */
	public static void main (String[] args) {
		Scanner card_input= new Scanner(System.in);
		Deck_Start();//start shoe(deck)
		mainloop:
		while(!(input.equalsIgnoreCase("quit")))/*just loop until quit*/ {
			if (Card_Remaining_Counter<10) {
				System.out.println("New Shoe");
				Deck_Start(); //refill shoe
				}
			else {//MAIN GAME HERE 
				//player turn here
				System.out.print("Dealer: "); 
				Dealer_Hand.add(Deck_Drawer());
				Dealer_Hand.add(Deck_Drawer());
				Dealer_Hand_Total=Card_Sum(Dealer_Hand);
				System.out.print("     Current Hand: "+Dealer_Hand_Total);
				/*Separated for convenience*/
				System.out.print("\nPlayer: ");
				Player_Hand.add(Deck_Drawer());
				Player_Hand.add(Deck_Drawer());
				Player_Hand_Total=Card_Sum(Player_Hand);
				System.out.print("     Current Hand: "+Player_Hand_Total+"\n");
				//blackjack check
				if(Dealer_Hand_Total==21) {
					System.out.println("Dealer got Blackjack");
					Dealer_Score+=1;
					Card_Reset();
					continue;
				}
				else if(Player_Hand_Total==21) {
					System.out.println("Player got Blackjack");
					Player_Score+=1;
					Card_Reset();
					continue;
				}
				else { //otherwise
					do {
						if(Input_List.contains(input.toLowerCase())) {
							break;
						}
						else {
							System.out.println("Instructions: Type these words: hit=draw, stand=maintain hand, surrender=give up turn, quit=end game");
							input = card_input.nextLine();
						}
					}while(!(Input_List.contains(input.toLowerCase())));//input not a keyword
					
					while(!((input.equalsIgnoreCase("stand")))) {
						if(input.equalsIgnoreCase("surrender")) {//surrender
							break;
						}
						else if(input.equalsIgnoreCase("hit")) {
							System.out.print("Player's draw: ");
							Player_Hand.add(Deck_Drawer()); //add the drawn card to the hand
							Player_Hand_Total=Card_Sum(Player_Hand);//calculate the new total value
							System.out.print("     Current Hand: "+Player_Hand_Total+"\n");
							if(Player_Hand_Total>21) {//a bust
								System.out.println("Bust");
								Dealer_Score+=1;
								break;
							}
							else if(Player_Hand_Total==21) {//highest hand value
								break;
							}
							else {
								input=card_input.nextLine();
							}
						}
						if(input.equalsIgnoreCase("quit")) {//quit
							break mainloop;
						}
					//player turn end	
					}
					//dealer turn here
					/**
					 * This is the "A.I." of the dealer as it will respond to the current status of the turn.
					 */
					while(Dealer_Hand_Total<17&&Player_Hand_Total<=21) { //Dealer goal is to get total of 17-21
						System.out.print("Dealer's draw: ");
						Dealer_Hand.add(Deck_Drawer());
						Dealer_Hand_Total=Card_Sum(Dealer_Hand);
						System.out.print("    Dealer's Current Hand: "+Dealer_Hand_Total+"\n");
						if(Dealer_Hand_Total>21) {
							System.out.println("Dealer Bust");
							Player_Score+=1;
							break;
						}
					if(Dealer_Hand_Total<=21&&Player_Hand_Total<=21) { //calculate hands
						Card_Calculator(Dealer_Hand_Total,Player_Hand_Total);
					}
					}
				Card_Reset();//reset hand
				}
			}
			//game boundary here
		}
		card_input.close();
	}
}