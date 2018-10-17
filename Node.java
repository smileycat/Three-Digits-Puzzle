import java.util.List;
import java.util.ArrayList;

public class Node implements Comparable<Node>{
	Node parent;
	List<Node> children;
	Integer heuristic; // In A*, this variable means f(n), where f(n) = g(n) + h(n)
	int value;
	int pathCost;
	int gn;
	
	public Node() {
		value = 0;
	}
	
	public Node(Node parent, int value) {
		this.value = value;
		this.parent = parent;
		this.heuristic = 0;
		this.pathCost = 1;
		this.gn = 0;
		children = new ArrayList<>();
		parent.children.add(this);
	}
	
	public Node(int value) {
		this.heuristic = 0;
		this.pathCost = 1;
		this.gn = 0;
		this.value = value;
		children = new ArrayList<>();
	}
	
	public void remove() {
		this.parent.children.remove(this);
		this.parent = null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        }
		final Node other = (Node) obj;
        if (value == other.value && children.equals(other.children)) {
        	return true;
        } else
        	return false;
	}
	
	@Override
	public int compareTo(Node o) {
		return this.heuristic.compareTo(o.heuristic);
	}
}
