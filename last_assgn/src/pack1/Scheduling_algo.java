package pack1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
	
	public Integer id;
	public Integer burst_time;
	public Integer waiting_time;
	public Integer arrival_time;
	public Integer turnaround_time;
	public Integer completion_time;
	public Integer remaining_burst_time;
	public Integer priority;
	public Process(){
		id = burst_time = waiting_time  = arrival_time = turnaround_time = 
				completion_time = remaining_burst_time= priority = 0;
	}
	
	
}
public class Scheduling_algo {
	static ArrayList<Process> p;
	Process temp;
	static Scanner sc ;;
	public static int num;
	public Scheduling_algo() {
		// TODO Auto-generated constructor stub
		p = new ArrayList<Process>();
		sc = new Scanner(System.in);
		
	}
	void take_input(){
		
		//System.out.println("Enter no of process");
		num = sc.nextInt();
		
		for(int i=0 ;i < num ;i++){
			temp = new Process();
			//System.out.print("Enter process id : ");
			temp.id = sc.nextInt();
			//System.out.print("Enter process arrival time : ");
			temp.arrival_time = sc.nextInt();
			//System.out.print("Enter process burst time : ");
			temp.burst_time = sc.nextInt();
			temp.remaining_burst_time = temp.burst_time;
			p.add(temp);
			
		}	
	}
	void sjf_non_preemptive(){
		int start  = 0 ; // start will determine from where to start seraching in arraylist of p 
		//whether the new process has occured whose arrival time is less than t; line 116
		ArrayList<Process> p1 = new ArrayList<>();
		for(int i=0 ;i < num ;i++){
			p1.add(p.get(i));
		}
		int flag = 0; // used on line 88
		ArrayList<Integer> gant_chart = new ArrayList<>();

		Collections.sort(p1, new Comparator<Process>() {
		    @Override
		    public int compare(Process o1, Process o2) {
		    	return o1.arrival_time.compareTo(o2.arrival_time);
		    }
		});
		int t = p1.get(0).arrival_time;
		ArrayList<Process> ready_state = new ArrayList<>();
		ready_state.add(p1.get(0));
		while(ready_state.size() != 0){
			flag = 0;
			Process temp = ready_state.get(0);
			t += temp.burst_time;
			temp.completion_time = t;
			gant_chart.add(temp.id);
			start += 1;
			//remove the executed item form readystate
			ready_state.remove(0);
			//check if any new process has arrived in time interval t to t+temp.burst_time
			for(int i =start; i < num; i++){
				temp = p1.get(i);
				if(temp.arrival_time <= t){
					ready_state.add(temp);
					flag = 1;
					start = i;
				}
				
			}
			//if nothing is added dont sort the ready_list and just continue 
			// else sort the ready_list based on burst_time
			if(flag == 1){
				Collections.sort(ready_state, new Comparator<Process>() {
				    @Override
				    public int compare(Process o1, Process o2) {
				    	return o1.burst_time.compareTo(o2.burst_time);
				    }
				});
			}
		}
		display(p1, gant_chart, 0);	
	}
	public void sjf_preemptive(){
		int start  = 0 ; // start will determine from where to start seraching in arraylist of p 
		//whether the new process has occured whose arrival time is less than t; line 116
		ArrayList<Process> p1 = new ArrayList<>();
		for(int i=0 ;i < num ;i++){
			p1.add(p.get(i));
		}
		int flag = 0; // used on line 88
		ArrayList<Integer> gant_chart = new ArrayList<>();
//		Collections.sort(p1);
		Collections.sort(p1, new Comparator<Process>() {
		    @Override
		    public int compare(Process o1, Process o2) {
		    	return o1.arrival_time.compareTo(o2.arrival_time);
		    }
		});
		int t = p1.get(0).arrival_time;
		ArrayList<Process> ready_state = new ArrayList<>();
		ready_state.add(p1.get(0));
		while(ready_state.size() != 0){
			flag = 0;
			Process temp = ready_state.get(0);
			t += 1;
			temp.completion_time = t;
			temp.remaining_burst_time -= 1;
			gant_chart.add(temp.id);
			//remove the executed item form readystate
			
			if(temp.remaining_burst_time == 0){
				ready_state.remove(0);
			}
			start += 1;
			//check if any new process has arrived in time interval t to t+temp.burst_time
			for(int i =start; i < num; i++){
				temp = p1.get(i);
				if(temp.arrival_time <= t){
					ready_state.add(temp);
					flag = 1;
					start = i;
				}
				
			}
			//if nothing is added dont sort the ready_list and just continue 
			// else sort the ready_list based on remainingburst_time
			if(flag == 1){
				Collections.sort(ready_state, new Comparator<Process>() {
				    @Override
				    // if the remaining burst time is same it will sort by arrival time
				    public int compare(Process o1, Process o2) {
				    	int temp = o1.remaining_burst_time.compareTo(o2.remaining_burst_time);
				    	if(temp != 0)
				    		return temp;
				    	return o1.arrival_time.compareTo(o2.arrival_time);
				    }
				});
			}
		}
		display(p1, gant_chart, 0);	
	}	
	
	void fcfs(){
		ArrayList<Process> p1 = p;
		Collections.sort(p1, new Comparator<Process>() {
		    @Override
		    // if the remaining burst time is same it will sort by arrival time
		    public int compare(Process o1, Process o2) {
		    	int temp = o1.arrival_time.compareTo(o2.arrival_time);
		    	if(temp != 0)
		    		return temp;
		    	return o1.burst_time.compareTo(o2.burst_time);
		    }
		});
		ArrayList<Integer> gant_chart = new ArrayList<>();
		int t = p1.get(0).arrival_time;
		for(int i=0 ;i < num; i++){
			Process temp = p1.get(i);
			t += temp.burst_time;
			temp.completion_time = t;
			gant_chart.add(temp.id);
		}
		display(p1, gant_chart , 0);
	}
	void display(ArrayList<Process> p, ArrayList<Integer>gant_chart,int flag){
		
		int avg_turn_around_time = 0;
		int avg_waiting_time = 0;
		System.out.println("Gantt Chart");
		for(int i=0 ;i < gant_chart.size(); i++){
			System.out.print("| "+gant_chart.get(i)+" ");
		}
		System.out.println("|");
		System.out.println();
		System.out.println("Process_id\tPriority\tArrival_Time\tBurst_Time\tCompletion_Time\tTurnaround Time\tWaiting Time");
		for(int i=0 ;i < num ;i++){
			Process temp = p.get(i);
			temp.turnaround_time = temp.completion_time - temp.arrival_time;
			temp.waiting_time = temp.turnaround_time - temp.burst_time;
			if(flag == 1){
				System.out.println(Integer.toString(p.get(i).id)+ "\t\t"+
				Integer.toString(temp.priority)+"\t\t"+
				Integer.toString(temp.arrival_time)+"\t\t"+
				Integer.toString(temp.burst_time)+"\t\t"+
				Integer.toString(temp.completion_time)+"\t\t"+
				Integer.toString(temp.turnaround_time) + "\t\t"+
				Integer.toString(temp.waiting_time));
			}else{
			System.out.println(Integer.toString(p.get(i).id)+ "\t\t"+
					Integer.toString(temp.arrival_time)+"\t\t"+
					Integer.toString(temp.burst_time)+"\t\t"+
					Integer.toString(temp.completion_time)+"\t\t"+
					Integer.toString(temp.turnaround_time) + "\t\t"+
					Integer.toString(temp.waiting_time));
			}
			avg_turn_around_time += temp.turnaround_time;
			avg_waiting_time += temp.waiting_time;
		}
		avg_turn_around_time /= num;
		avg_waiting_time /= num;
		System.out.println("Average TurnAround Time is"+Integer.toString(avg_turn_around_time));
		System.out.println("Average Waiting Time is"+Integer.toString(avg_waiting_time));
	}
	void round_robin(int quantum){
		ArrayList<Integer> gant_chart = new ArrayList<>();
		int start  = 0 ; // start will determine from where to start seraching in arraylist of p 
		//whether the new process has occured whose arrival time is less than t; line 116
		ArrayList<Process> p1 = new ArrayList<>();
		for(int i=0 ;i < num ;i++){
			p1.add(p.get(i));
		}
		Collections.sort(p1, new Comparator<Process>() {
		    @Override
		    public int compare(Process o1, Process o2) {
		    	return o1.burst_time.compareTo(o2.burst_time);
		    }
		});
		Process temp = new Process();
		temp = p1.get(0);
		Queue<Process> ready_state = new LinkedList<>();
		ready_state.add(temp);
		int t = 0;
		while(ready_state.size() != 0){
			Process temp1 = ready_state.remove();
			//reduce the burst time
			gant_chart.add(temp1.id);
			if(temp1.remaining_burst_time > quantum){
				temp1.remaining_burst_time -= quantum;
				t += quantum;
				temp1.completion_time = t;
			}else{
				t += temp1.remaining_burst_time;
				temp1.remaining_burst_time = 0;
				temp1.completion_time = t;
			}
			
			//check if any new process is arrived till time t = t+ quantum
			start += 1;
			for(int i=start ; i< num ;i++){
				Process temp2 = p.get(i);
				if(temp2.arrival_time <= t){
					ready_state.add(temp2);
					start = i;
				}
			}
			//check if remainig burst time of process which is dequeed is 0
			if(temp1.remaining_burst_time != 0){
				ready_state.add(temp1);
			}
			
			
		}
		display(p1, gant_chart, 0);
	}
	public void Priority_non_preemptive(){
		// lower no has greater priority
		
		// p2 contains the description of process
		//sort based on arrival time
		ArrayList<Integer> gant_chart = new ArrayList<>();
		int start  = 0 ; // start will determine from where to start seraching in arraylist of p 
		//whether the new process has occured whose arrival time is less than t; line 116
		ArrayList<Process> p1 = new ArrayList<>();
		for(int i=0 ;i < num ;i++){
			p1.add(p.get(i));
		}
		Collections.sort(p1, new Comparator<Process>() {
		    @Override
		    public int compare(Process o1, Process o2) {
		    	return o1.arrival_time.compareTo(o2.arrival_time);
		    }
		});
		int flag;
		int t = p1.get(0).arrival_time;
		ArrayList<Process> ready_state = new ArrayList<>();
		ready_state.add(p1.get(0));
		while(ready_state.size() != 0){
			flag = 0;
			Process temp = ready_state.get(0);
			t += temp.burst_time;
			temp.completion_time = t;
			gant_chart.add(temp.id);
			start += 1;
			//remove the executed item form readystate
			ready_state.remove(0);
			//check if any new process has arrived in time interval t to t+temp.burst_time
			for(int i =start; i < num; i++){
				temp = p1.get(i);
				if(temp.arrival_time <= t){
					ready_state.add(temp);
					flag = 1;
					start = i;
				}
			}
			//if nothing is added dont sort the ready_list and just continue 
			// else sort the ready_list based on burst_time
			if(flag == 1){
				Collections.sort(ready_state, new Comparator<Process>() {
				    @Override
				    public int compare(Process o1, Process o2) {
				    	int temp = o2.priority.compareTo(o1.priority);
				    	if (temp != 0)
				    		return temp;
				    	return o1.arrival_time.compareTo(o2.arrival_time);
				    }
				});
			}
		}
		display(p1, gant_chart, 1);
	}
	public void Priority_preemptive(){
		// higher no has greater priority
		// p2 contains the description of process
		//sort based on arrival time
		ArrayList<Integer> gant_chart = new ArrayList<>();
		int start  = 0 ; // start will determine from where to start seraching in arraylist of p 
		//whether the new process has occured whose arrival time is less than t; line 116
		ArrayList<Process> p1 = new ArrayList<>();
		for(int i=0 ;i < num ;i++){
			p1.add(p.get(i));
		}
		
		Collections.sort(p1, new Comparator<Process>() {
		    @Override
		    public int compare(Process o1, Process o2) {
		    	return o1.arrival_time.compareTo(o2.arrival_time);
		    }
		});
		int flag;
		int t = p1.get(0).arrival_time;
		ArrayList<Process> ready_state = new ArrayList<>();
		ready_state.add(p1.get(0));
		 
		while(ready_state.size() != 0){
			flag = 0;
			Process temp = ready_state.get(0);
			t += 1;
			temp.completion_time = t;
			temp.remaining_burst_time -= 1;
			gant_chart.add(temp.id);
			if(temp.remaining_burst_time == 0){
				ready_state.remove(temp);
			}
			
			start += 1;
			//check if any new process has arrived in time interval t to t+1 if yes the 
			// insert into the ready state and its its priority is greater than the current
			// event schedule it;
			for(int i =start; i < num; i++){
				temp = p1.get(i);
				if(temp.arrival_time <= t){
					ready_state.add(temp);
					flag = 1;
					start = i;
				}
			}
			//if nothing is added dont sort the ready_list and just continue 
			// else sort the ready_list based on burst_time
			if(flag == 1){
				Collections.sort(ready_state, new Comparator<Process>() {
				    @Override
				    public int compare(Process o1, Process o2) {
				    	int temp = o2.priority.compareTo(o1.priority);
				    	if (temp != 0)
				    		return temp;
				    	//if priority is same sort by arrival time;
				    	return o1.arrival_time.compareTo(o2.arrival_time);
				    }
				});
			}
		}
		display(p1, gant_chart, 1);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Scanner sc = new Scanner(System.in);
		Scheduling_algo o1 = new Scheduling_algo();
		o1.take_input();
		
		int choice;
		int flag = 0;
		while(true){
			System.out.println("1.FCFS \n2.SJF non preemptive\n3.SJF preemptive\n4.Priority Scheduling non preemptive" +
					" \n5.Priority Scheduling Preemptive\n6.Round Robin");
			System.out.println("Enter choice");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				o1.fcfs();
				break;
			case 2:
				o1.sjf_non_preemptive();
				break;
			case 3:
				o1.sjf_preemptive();
			case 4:
				if(flag == 0){
				System.out.println("Enter the priority of process in order");
				for(int i=0 ;i < num ;i++){
					p.get(i).priority = sc.nextInt();
					flag = 1;
				}
				}
				o1.Priority_non_preemptive();
				break;
			case 5:
				if(flag == 0){
					System.out.println("Enter the priority of process in order");
					for(int i=0 ;i < num ;i++){
						p.get(i).priority = sc.nextInt();
						flag = 1;
						
					}
				}
				o1.Priority_preemptive();
				break;
			case 6:
				System.out.println("Enter the quantum size");
				int quantum = sc.nextInt();
				o1.round_robin(quantum);
			default:
				break;
			}
			
			System.out.println("Do u want to continue??(0/1)");
			choice = sc.nextInt();
			if(choice == 0)break;
		}
		
	}

}
/*
 * round_robin
6
1
0
4
2
1
5
3
2
2
4
3
1
5
4
6
6
6
3
 */
 /*sjf_non_preemptive
5
1
1
7
2
2
5
3
3
1
4
4
2
5
5
8
  */
/*
Priority Pre emptive 
7
1
0
4
2
1
2
3
2
3
4
3
5
5
4
1
6
5
4
7
6
6
Priority
2
4
6
10
8
12
9
 */
