import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class ThreeDigits {
	private static final int MAX_EXPAND_NODES = 1000;
	
	private static List<Node> expand(Node current, List<Integer> forbidden) {
		List<Node> children = new ArrayList<>();
		int curValue = current.value;
		int parValue, n;
		
		// checking for the start node
		if (current.parent == null)
			parValue = curValue;
		else
			parValue = current.parent.value;
		
		// if the first digit is not different between current and parrent
		if (Math.abs(parValue - curValue) != 100) {

			// 222 -> 122
			n = curValue - 100;
			if (n >= 0 && !forbidden.contains(n))
				children.add(new Node(current, n));

			// 222 -> 322
			n = curValue + 100;
			if (n < 1000 && !forbidden.contains(n))
				children.add(new Node(current, n));
		}
		
		// if the second digit is not different between current and parrent
		if (Math.abs(parValue - curValue) != 10) {
			n = curValue - 10;
			if (n % 100 / 10 != 9 && n >= 0 && !forbidden.contains(n))
				children.add(new Node(current, n));
			
			n = curValue + 10;
			if (n % 100 / 10 != 0 && !forbidden.contains(n))
				children.add(new Node(current, n));
		}
		
		// if the third digit is not different between current and parrent
		if (Math.abs(parValue - curValue) != 1) {
			n = curValue - 1;
			if (n % 10 != 9 && n >= 0 && !forbidden.contains(n))
				children.add(new Node(current, n));
			
			n = curValue + 1;
			if (n % 10 != 0 && !forbidden.contains(n))
				children.add(new Node(current, n));
		}
		return children;
	}
	
	private static void calculateHeuristic(List<Node> nodes, int goal) {
		for (Node node : nodes) {
			node.heuristic += Math.abs(goal/100 - node.value/100); // 1st digit
			node.heuristic += Math.abs(goal%100/10 - node.value%100/10); // 2nd digit
			node.heuristic += Math.abs(goal%10 - node.value%10); // 3rd digit
		}
	}
	
	/** This function manipulates the formula f(n) = g(n) + h(n) for A* algorithm */
	private static void calculateTotal(List<Node> nodes) {
		for (Node child : nodes) {
			child.gn += child.parent.gn + child.pathCost; // calculate g(n) for each node
			child.heuristic += child.gn; // calculate f(n) = g(n) + h(n)
		}
	}
	
	/** check if Node n exist in the list.
	  * conditions: two nodes must have same value, and same children, ignore the parent node */
	private static boolean contains(List<Node> list, Node n) {
		boolean loop = false;
		
		for (Node i : list) {
			if (i.value == n.value && i.children.size() == n.children.size()) {

				// when two nodes have same value, compare their children's value
				for (int j = i.children.size()-1; j >= 0; j--) {
				
					// A child's value is different, so break this loop and keep
					// searching in the list
					if (i.children.get(j).value != n.children.get(j).value) {
						loop = true;
						break;
					}
				}
				if (loop == true) loop = false; 
				else return true;
			}
		}
		
		return false;
	}
	
	private static void printSolution(List<Integer> solution) {
		for (int i = solution.size() -1; i > 0; i--)
			System.out.printf("%03d,", solution.get(i));
		
		System.out.printf("%03d\n", solution.get(0));
	}
	
	private static void printExpanded(List<Node> expanded) {
		for (int i = 0; i < expanded.size()-1; i++) {
			System.out.printf("%03d,", expanded.get(i).value);
//			if (expanded.get(i).value == 335) {
//				System.out.print("\n335parent: " + expanded.get(i).parent.value);
//				System.out.print("\n335children: ");
//				for (Node n : expanded.get(i).children)
//					System.out.print(n.value + ",");
//				System.out.println();
//			}
		}
			
		System.out.printf("%03d\n", expanded.get(expanded.size()-1).value);
//		System.out.println(expanded.size());
	}
	
	private static void printIdsExpand(List<Integer> expanded) {
		for (int i = 0; i < expanded.size()-1; i++) {
			System.out.printf("%03d,", expanded.get(i));
		}
			
		System.out.printf("%03d\n", expanded.get(expanded.size()-1));
	}
	
	public static void AStar(int start, int goal, List<Integer> forbidden) {
		List<Node> fringe = new ArrayList<>();
		List<Node> expanded = new ArrayList<>();
		List<Integer> solution = new ArrayList<>();
		fringe.add(new Node(start));
		
		while (!fringe.isEmpty() && expanded.size() != MAX_EXPAND_NODES) {
			int index = 0;
			// if same heuristic value, last addded expands first
			while (fringe.size() > index+1 && fringe.get(index).heuristic == fringe.get(index+1).heuristic) {
				index++;
			}
			
			Node current = fringe.remove(index);
			List<Node> children = expand(current, forbidden);
			if (contains(expanded, current))
				continue;
			
			expanded.add(current);
			// solution found
			if (current.value == goal) {
				solution.add(current.value);
				while (current.parent != null) {
					current = current.parent;
					solution.add(current.value);
				}
				printSolution(solution);
				break;
			}
			calculateHeuristic(children, goal);
			calculateTotal(children);
//			for (Node n : children) {
//				System.out.print(n.heuristic + ",");
//			}
//			System.out.println();
			fringe.addAll(children);
			Collections.sort(fringe); // sort with the heuristic instance variable
		}
		
		if (solution.isEmpty())
			System.out.println("No solution found.");

		printExpanded(expanded);
	}
	
	public static void bfs(int start, int goal, List<Integer> forbidden) {
		List<Node> expanded = new ArrayList<>();
		List<Node> fringe = new ArrayList<>();
		List<Integer> solution = new ArrayList<>();
		
		fringe.add(new Node(start));
		
		while (!fringe.isEmpty() && expanded.size() != MAX_EXPAND_NODES) {
			Node current = fringe.remove(0);
			List<Node> children = expand(current, forbidden);
			
			if (contains(expanded, current))
				continue;
			
			expanded.add(current);
			fringe.addAll(children);
			// solution found
			if (current.value == goal) {
				solution.add(current.value);
				while (current.parent != null) {
					current = current.parent;
					solution.add(current.value);
				}
				printSolution(solution);
				break;
			}
		}
		
		if (solution.isEmpty())
			System.out.println("No solution found.");
		
		printExpanded(expanded);
	}

	public static void dfs(int start, int goal, List<Integer> forbidden) {
		
		List<Node> expanded = new ArrayList<>();
		List<Node> fringe = new ArrayList<>();
		List<Integer> solution = new ArrayList<>();
		
		fringe.add(new Node(start));

		while (!fringe.isEmpty() && expanded.size() != MAX_EXPAND_NODES) {
			Node current = fringe.remove(0);
			List<Node> children = expand(current, forbidden);
			
			if (contains(expanded, current))
				continue;
			
			expanded.add(current);
			fringe.addAll(0, children);
			
			// solution found
			if (current.value == goal) {
				solution.add(current.value);
				while (current.parent != null) {
					current = current.parent;
					solution.add(current.value);
				}
				printSolution(solution);
				printExpanded(expanded);
				break;
			}
		}
		
		if (solution.isEmpty()) {
			System.out.println("No solution found.");
			printExpanded(expanded);
		}
	}
	
	private static List<Node> dfs_for_ids(Node current, int depth, List<Integer> forbidden) {
		List<Node> visit = new ArrayList<>();
		expand(current, forbidden);
		visit.add(current);

		if (depth == 0)
			return visit;
		
    	for (Node child : current.children)
    		visit.addAll(dfs_for_ids(child, depth-1, forbidden));
    	
    	return visit;
	}
	
	public static void greedy(int start, int goal, List<Integer> forbidden) {
		List<Node> fringe = new ArrayList<>();
		List<Node> expanded = new ArrayList<>();
		List<Integer> solution = new ArrayList<>();
		fringe.add(new Node(start));
		
		while (!fringe.isEmpty() && expanded.size() != MAX_EXPAND_NODES) {
			int index = 0;
			// if same heuristic value, last addded expands first
			while (fringe.size() > index+1 && fringe.get(index).heuristic == fringe.get(index+1).heuristic) {
				index++;
			}
			
			Node current = fringe.remove(index);
			List<Node> children = expand(current, forbidden);
			if (contains(expanded, current))
				continue;
			
			expanded.add(current);
			// solution found
			if (current.value == goal) {
				solution.add(current.value);
				while (current.parent != null) {
					current = current.parent;
					solution.add(current.value);
				}
				printSolution(solution);
				break;
			}
			calculateHeuristic(children, goal);
			fringe.addAll(children);
			Collections.sort(fringe); // sort with the heuristic instance variable
		}
		
		if (solution.isEmpty())
			System.out.println("No solution found.");

		printExpanded(expanded);
	}
	
	public static void hillClimbing(int start, int goal, List<Integer> forbidden) {
		List<Node> expanded = new ArrayList<>();
		List<Node> fringe = new ArrayList<>();
		
		fringe.add(new Node(start));
		calculateHeuristic(fringe, goal);
		int vvalue = fringe.get(0).heuristic;
		
		while (expanded.size() != MAX_EXPAND_NODES) {
			int index = 0;
			// if same heuristic value, last addded expands first
			while (fringe.size() > index+1 && fringe.get(index).heuristic == fringe.get(index+1).heuristic) {
				index++;
			}
			
			Node current = fringe.remove(index);
			fringe = expand(current, forbidden);
			calculateHeuristic(fringe, goal);
			Collections.sort(fringe);
			
			expanded.add(current);
			vvalue = current.heuristic;
			
			// solution found
			if (current.value == goal) {
				printExpanded(expanded);
				break;
			// dead end
			} else if (fringe.isEmpty() || fringe.get(0).heuristic > vvalue) {
				System.out.println("No solution found.");
				break;
			}
		}
		printExpanded(expanded);
	}
	
	public static void ids(int start, int goal, List<Integer> forbidden) {
		List<Integer> expanded = new ArrayList<>();
		List<Integer> solution = new ArrayList<>();
		List<Node> previous = new ArrayList<>();
		int cutoff = 0;
		
		while (solution.isEmpty() && expanded.size() < MAX_EXPAND_NODES) {
			List<Node> visit = dfs_for_ids(new Node(start), cutoff++, forbidden);
			List<Node> temp = new ArrayList<>();
			if (visit.equals(previous)) break; // no new nodes to explore
			previous = visit;
			
			for (Node current : visit) {
				// Check for duplicate nodes in dfs
				if (contains(temp, current))
					continue;
				
				temp.add(current);
				expanded.add(current.value);
				
				// solution found
				if (current.value == goal) {
					solution.add(current.value);
					while (current.parent != null) {
						current = current.parent;
						solution.add(current.value);
					}
					printSolution(solution);
					break;
				}
				if (expanded.size() == MAX_EXPAND_NODES)
					break;
			}
		}
		
		if (solution.isEmpty())
			System.out.println("No solution found.");
		
		printIdsExpand(expanded);
//		System.out.println(expanded.size());
	}
	
	public static void main(String[] args) throws IOException {
		
		// File in = new File(args[1]);
		// InputStreamReader ir = new InputStreamReader(new FileInputStream(in), "UTF-8");
		// BufferedReader br = new BufferedReader(ir);

		List<Integer> forbidden = new ArrayList<>();
		int start = 0, goal = 0;
		String[] tokens = null;
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(args[1]), StandardCharsets.UTF_8);
			start = Integer.parseInt(lines.get(0));
			goal = Integer.parseInt(lines.get(1));

			if (lines.size() > 2 && !lines.get(2).equals("")) {
				tokens = lines.get(2).split(",");
			}
		} catch (IOException e) {
			System.err.println("error when reading file: " + e);
		}

		if (tokens != null) {
			for (int i = 0; i < tokens.length; i++)
				forbidden.add(Integer.parseInt(tokens[i]));
		}
		// args[0] specifies the search alg for the puzzle
		switch (args[0].charAt(0)) {
			case 'A': AStar(start, goal, forbidden); break;
			case 'B': bfs(start, goal, forbidden); break;
			case 'D': dfs(start, goal, forbidden); break;
			case 'G': greedy(start, goal, forbidden); break;
			case 'H': hillClimbing(start, goal, forbidden); break;
			case 'I': ids(start, goal, forbidden); break;
		}
	}

}
