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

Future
- Put the type of node in the Node class (either horizontal or vertical)
- Handle curvature of the surface
- Handle moving points (kinetic)
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
		if (rect == null) {
			throw new IllegalArgumentException("Rect is null")
		}
		
		Queue<Point2D> q = new Queue<Point2D>();
		range(rect, root, q);
		return q;
	}

	/*
		Given a node and a rect, we check whether the node
		is in the rectangle by checking whether its distance is zero.
		In that case, we return the point
		

		if intersects the positive part of the node, we go 
		to the 
	*/
	private void range(RectHV rect, Node node, Queue<Point2D> q) {
		if (node == null) {
			return;
		}

		if (rect.contains(node.p)) {
			q.enqueue(node.p);
		}		
		
		if (rect.intersects(node.rect)) {
			range(rect, node.right, q);
			range(rect, node.left, q);
		} else {
			return;
		}
		return;
	}

	public boolean isEmpty() {
		return size == 0;
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
	
	Traverse through the tree going vertical and horizontal nodes until we reach the end
	start with vertical nodes



	*/

	public void insert(Point2D p) {
		//use helper function to recursively insert a value
		root = insert(root, p, true, 0, 0, 1, 1);
	}


	/*
		Helper function for insertion
		Recursive insert. Once we get to the end of the tree, we return a new node
		Keep track of the current rectangle we are associated with
	*/

	private Node insert(Node node, Point2D p, boolean vertical, double x1, double y1, double x2, double y2) {
		if (node == null) {
			size++;
			return new Node(p, new RectHV(x1, y1, x2, y2));
		}

		//check if p values are the same
		if (node.p.compareTo(p) == 0) { //includes itself, so just return node
			return node;
		}

		double cmp = 0;
		if (vertical) {
			cmp = p.x() - node.p.x(); //go left if negative and right if positive
			if (cmp > 0) { //go to right, with rectangle partitioned by node x value
				node.right = insert(node.right, p, !vertical, node.p.x(), y1, x2, y2);	
			} else { //go to left
				node.left = insert(node.left, p, !vertical, x1, y1, node.p.x(), y2);	
			}
		} else { //horizontal node, compare y-values
			cmp = p.y() - node.p.y();
			if (cmp > 0) { //go up with node partitioned by node y value
				node.right = insert(node.right, p, !vertical, x1, node.p.y(), x2, y2);	
			} else { //go down
				node.left = insert(node.left, p, !vertical, x1, y1, x2, node.p.y());	
			}
		}
		return node;
	}

	public boolean contains(Point2D p) {
		//use helper function to recursively insert a value
		return contains(root, p, true);
	}

	private boolean contains(Node node, Point2D p, boolean vertical) {
		//go throiugh the left or right based on vertical
		if (node == null) {
			return false;
		} else if (node.p.compareTo(p) == 0) {
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
			// return false;
		}
	}

	/*
		This is the recursive function to find the nearest point p
		within the KdTree. We find the nearest point in a unique way
		not by comparing each value with each other, but by partitioning
		the region. The first point becomes the node and then futher insertions
		go to the left if node y-value is greater or to the right if the node x-value 
		is greater. The next insertion we switch to x.

		Now given any arbitrary point, we calculate the nearest point by iterating
		through the tree. If the current node is a vertical node, we compare x values to 
		see whether we go left or right. If it is a horizontal node, we compare y values.

		We keep going through the tree until we reach the end, while keeping the minimum point
		stored. Once we reach a null value, we send back the minimum 
	*/

	public Point2D nearest(Point2D p) {
		//find the nearest Point in the tree that's close to p
		//traverse through the partitions based on vertical or not
		//keep track of the euclidean distances at each tree level (when one side is greater, ignore the other side)
		if (p == null) {
			throw new IllegalArgumentException("Given point is null");
		}
		
		//starts out as a vertical node, with closest point as the root
		return nearest(root, p, root.p);
	}

	private Point2D nearest(Node node, Point2D p, Point2D closestPoint) {
		//reached the end of the tree, return the closest point
		if (node != null) {
			//rare, but possible that none of the subtree points are closer than the current previous point
			if (closestPoint.distanceSquaredTo(p) >= node.rect.distanceSquaredTo(p)) {
				if (node.p.distanceSquaredTo(p) < closestPoint.distanceSquaredTo(p)) {
					closestPoint = node.p;
				}

				if (node.right != null && node.right.rect.contains(p)) {
					closestPoint = nearest(node.right, p, closestPoint);
					closestPoint = nearest(node.left, p, closestPoint);
				} else {
					closestPoint = nearest(node.left, p, closestPoint);
					closestPoint = nearest(node.right, p, closestPoint);
				}
			}
		}
		return closestPoint;
	}

  public static void main(String[] args) {
	  StdOut.println("Test");
  }
}