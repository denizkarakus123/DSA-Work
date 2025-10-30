import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		//we need to sort by weight first since we want to maximize weights
		if (a1.weight > a2.weight){
			return -1;
		}
		if (a1.weight < a2.weight){
			return 1;
		}

		//then if our weights are the same, we want to use the deadlines as the tiebreaker of the sorting 
		if (a1.deadline < a2.deadline){
			return -1;
		}
		if (a1.deadline > a2.deadline){
			return 1;
		}
		
		//if everything's the same just return 0
		return 0;
	}
}
