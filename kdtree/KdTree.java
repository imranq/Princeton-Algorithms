import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

/*
Implementing k-d tree, which splits data on several dimensions to create an efficient representation of it

Why is a kdtree so efficient?
- LogN based insertion
- Nearest neighbor search
- Point containment same as BST (which is best in class)
- Partitions space based on the data

Start with vertical
Which data should you start with? Does it work best with center, out? (Ranked by Euclidean distance from the center)

*/


public class KdTree {
	
	private class Node {
		private Point2D p;
		private Node left;
		private Node right;
		private RectHV rect; 

		/*
		*	What's the point of having a rectangle aligned axis?
		*	- It shows the area enclosed by the node
		*
		*/

		public Node(Point2D p, RectHV rect) { 
			this.p = p;
			this.left = null;
			this.right = null;
			this.rect = rect; 
		}

		public Node(Point2D p) {
			this.left = null;
			this.right = null;
			this.rect = null; 	
			this.p = p;
		}
	}

	private Node root;
	private int size;
	
	public KdTree() {
		root = null;
		size = 0;
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> result = new Queue<Point2D>();
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Point2D nearest(Point2D p) {
		//find the nearest Point in the tree that's close to p
		//traverse through the partitions based on vertical or not
		//keep track of the euclidean distances at each tree level (when one side is greater, ignore the other side)
		return nearest(root, p, true, root.p);

	}

	public int size() {
		return size;
	}

	public void draw() {
		draw(root, true);
	}

	private void draw(Node node, boolean vertical) {
		//draw vertical, horizontal lines across the screen at every level
		if (node == null) return;

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		node.p.draw();

		if(vertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
		}
		draw(node.left, !vertical);
		draw(node.right, !vertical);


	}

	/*
	* Traverse through the tree going vertical and horizontal until we reach the end
	*
	*/

	public void insert(Point2D p) {
		//use helper function to recursively insert a value
		root = insert(root, p, true, 0, 0, 1, 1);
	}

	public boolean contains(edu.princeton.cs.algs4.Point2D p) {
		//use helper function to recursively insert a value
		return contains(root, p, true);
	}

	private Node insert(Node node, Point2D p, boolean vertical, double x1, double y1, double x2, double y2) {
		if (node == null) {
			size++;
			return new Node(p, new RectHV(x1, y1, x2, y2));
		}

		//check if p values are the same
		if (node.p.compareTo(p) == 0) { //includes itself, so just return node
			return node;
		}

		//go left based on x value
		double cmp = 0;
		if (vertical) {
			cmp = p.x() - node.p.x(); //go left if negative and right if positive
			if (cmp > 0) { //go to right
				node.left = insert(node.left, p, !vertical, node.p.x(), y1, x2, y2);	
			} else { //go to left
				node.left = insert(node.left, p, !vertical, x1, y1, node.p.x(), y2);	
			}
			
		} else {
			cmp = p.y() - node.p.y();
			if (cmp > 0) { //go up
				node.right = insert(node.right, p, !vertical, x1, node.p.y(), x2, y2);	
			} else { //go down
				node.right = insert(node.right, p, !vertical, x1, y1, x2, node.p.y());	
			}
		}

		return node;
	}

	private boolean contains(Node node, Point2D p, boolean vertical) {
		//go throiugh the left or right based on vertical
		if (node.p.compareTo(p) == 0) {
			return true;
		} else {
			double cmp = 0;
			if (vertical) {
				cmp = p.x() - node.p.x();
			} else {
				cmp = p.y() - node.p.y();
			}

			if (cmp > 0) {
				return contains(node.right, p, !vertical);
			} else {
				return contains(node.left, p, !vertical);
			}
		}
	}

	private Point2D nearest(Node node, Point2D p, boolean vertical, Point2D c) {
		Point2D closestPoint = c;

		if (node == null) {
			return c;
		}


		if (node.p.distanceSquaredTo(p) < node.p.distanceSquaredTo(closestPoint)) {
			closestPoint = node.p;
		}

		//check the subtrees 
		if (node.rect.distanceSquaredTo(p) < closestPoint.distanceSquaredTo(p)) {

		}
		
		double cmp_l = p.distanceSquaredTo(node.left.p);
		double cmp_r = p.distanceSquaredTo(node.right.p);

		if (cmp_l > cmp_r) {
			closestPoint = nearest(node.left, p, !vertical, closestPoint);
		} else {
			closestPoint = nearest(node.right, p, !vertical, closestPoint);
		}
		return closestPoint;
	}

  public static void main(String[] args) {
	  StdOut.println("Test");
  }
}