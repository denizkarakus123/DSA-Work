import java.util.*;

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}

		for (int i = 0; i < Assignments.size(); i++) {
        //get our current assignment that we're looking at 
        Assignment curr = Assignments.get(i);
        
        //gonna try to put this as close to the deadline as possible
        //to leave earlier slots free for other stuff
        int bestSlot = curr.deadline - 1; 
        
        //look for an open slot
        boolean found = false;
        while (bestSlot >= 0) {
            if (homeworkPlan[bestSlot] == -1) {
                //we found an empty slot!!
                homeworkPlan[bestSlot] = curr.number;
                found = true;
                break;
            }
            bestSlot--;
        }

		//we can exit the loop if we find no assignment
	
		}
		return homeworkPlan;
	}
}
	


