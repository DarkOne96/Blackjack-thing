import java.util.*;
import org.junit.Test;

public class BlackjackTest {
	
	//ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
	//System.setIn(in);

	@Test
	public void Integer_input_sum(){//check output of Card_Display
		ArrayList <String> first_list=new ArrayList<String>(Arrays.asList(
		"[0]","[1/11]","[2]","[3]","[4]","[5]","[6]","[7]","[8]","[9]","[10]","[J]","[Q]","[K]"));
		System.out.println("First test:");
		for(int i=0;i<=13;i++) {
			if(first_list.get(i).equals(Blackjack.Card_Display(i))) {
				System.out.println("TRUE");
			}
			else {
				System.out.println("FALSE");
			}
		}
	}
	//random card value
	@Test
	public void Random_card_value() {
		Random number = new Random();
		List<Integer> test_hand= new ArrayList<Integer>();
		List<Integer> test_hand_2= new ArrayList<Integer>();
		for(int a=0;a<2;a++) {
			test_hand.add(number.nextInt(13)+1);
			test_hand_2.add(test_hand.get(a));//copy value
		}
		Integer total= 0;
		for (int i=0;i<test_hand.size();i++) {
			if (test_hand.get(i)==11||test_hand.get(i)==12||test_hand.get(i)==13) {
				total+=10;
			}
			else if(test_hand.get(0)==1&&test_hand.get(1)==1) {
				total=12;
			}
			else if(test_hand.get(i)==1&&test_hand.size()<3) { 
				total+=11;	
			}	
			else {
				total+=test_hand.get(i);
			}
		}
		Integer total_2=0;
		total_2=Blackjack.Card_Sum(test_hand_2);
		System.out.println("Second Test:");
		if(total==total_2) {
			System.out.println("TRUE");
		}
		else {
		System.out.println("FALSE");
		}
	
	
	
	}
	
}

