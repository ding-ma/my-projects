import java.util.ArrayList;

import org.w3c.dom.events.MouseEvent;

import acm.graphics.GOval;

/**
 * Implements a B-Tree class.
 * @author ferrie
 *
 */

public class bTree  {
	private static boolean potato = false;
	static bNode root=null;					// Default constructor

	public static double xpos = 0; //set the location for the sorted ball at 0


	/**
	 * addNode method - wrapper for rNode
	 */	
	public void addNode(gBall iBall) {		// This is a wrapper to hide
		root=rNode(root,iBall);			// having to deal with root node.

	}									// Calls actual method below.
	/**
	 * rNode method - recursively adds a new entry into the B-Tree
	 */	
	private bNode rNode(bNode root, gBall iBall) {
		//
		//  Termination condition for recursion.  We have descended to a leaf
		//  node of the tree (or the tree may initially be empty).  In either case,
		//	create a new node --> copy over data, set successor nodes to null.
		//
		if (root==null) {
			bNode node = new bNode();	// Create a new node
			node.iBall = iBall;			// Copy data
			node.left = null;			// Successors to null.
			node.right = null;
			root=node;					// Node created is root
			return root;				// of new (sub) tree.
		}
		//
		//	Not at a leaf node, so traverse to the left if data being
		//  added is strictly less than data at current node.
		//
		else if (iBall.bSize < root.iBall.bSize) {
			root.left = rNode(root.left,iBall);
		}
		//
		//  If greater than or equal, then traverse to the right.  Keep
		//  recursing until a node is found with no successors.
		else {
			root.right = rNode(root.right,iBall);
		}
		return root;
	}

	/**
	 * inorder method - inorder traversal via call to recursive method
	 */

	public void inorder() {			// This is a wrapper for traverse_
		traverse_inorder(root);		// inorder to hide details of the
	}								// root node.

	/**
	 * traverse_inorder method - recursively traverses tree in order and prints each node.
	 * Order: Descend following left successor links, returning back to the current
	 *        root node (where a specific action takes place, e.g., printing data),
	 *        and then repeat the descent along right successor links.
	 */

	private void traverse_inorder(bNode root) {
		if (root.left != null) traverse_inorder(root.left);
		System.out.println(root.iBall.bSize);
		if (root.right != null) traverse_inorder(root.right);
	}

	// Example of a nested class //



	public class bNode {
		gBall iBall;
		bNode left;
		bNode right;
	}



	public boolean isRunning() { //method to detect if the balls are still bouncing
		potato = false;
		risRunning(root);//	Recursive method for isRunning, constantly checking if the balls are bouncing
		return potato;
	}


	private void risRunning(bNode root) {
		if (root.left != null) risRunning(root.left);
		if (root.iBall.isMoving == true) potato = true;
		if (root.right != null) risRunning(root.right);
	}


	public void moveSort() {
		moveTo(root);
	}
	private void moveTo(bNode root) { //setting the location of each ball
		if (root.left != null) moveTo(root.left);
		root.iBall.myball.setLocation(xpos, bSim.HEIGHT-root.iBall.bSize-bSim.OFFSET+100); //taking the size of each ball in consideration in order to not have any overlap
		xpos += root.iBall.bSize; //repeats for all the ball present
		if (root.right != null) moveTo(root.right);
	}



	public static void clearTree() { //clears the binary tree
		root=null; //sets the root to null (nothing), hence clearing the tree
	}

}





