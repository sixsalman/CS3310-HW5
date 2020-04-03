package hw5cs3310_Khan;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Instances of this class store instances of Mydata class and allow insertion, deletion and search. They also
 * contain methods to output inorder, preorder and postorder traversals of the data they store.
 */
class BinaryStreeImplicit {

    private ArrayList<Mydata> tree; // holds all the nodes of the binary search tree
    private int treeSize; // number of nodes in the binary search tree
    private int lastIndexUsed; // the tree may not be full, so specifies the array bound
    private PrintWriter outFilePrint;

    //… // properties and constructors
    // some methods that may be helpful

    /**
     * Creates an instance of this class initializing all of its fields and storing the received parameter
     * @param outFilePrint receives reference to a PrintWriter object to write outputs to
     */
    BinaryStreeImplicit(PrintWriter outFilePrint) {
        this.outFilePrint = outFilePrint;
        tree = new ArrayList<>();
        treeSize = 0;
        lastIndexUsed = -1;
    }

    /**
     * Returns the index of the root of the tree or -1 if tree is empty
     * @return root index; -1 if empty
     */
    private int root() {
        return (treeSize != 0) ? 0 : -1;
    }

    //int leftchild(int i); int rightchild(int i); int parent(int i);
    // return the indices of the children and parent of a node at index i

    /**
     * Returns the index at which left child of the Mydata instance at the received index exists
     * @param i receives index of the Mydata instance
     * @return index at which the left child exists
     */
    private int leftchild(int i) {
        return 2 * i + 1;
    }

    /**
     * Returns the index at which right child of the Mydata instance at the received index exists
     * @param i receives index of the Mydata instance
     * @return index at which the right child exists
     */
    private int rightchild(int i) {
        return 2 * i + 2;
    }

    /**
     * Returns the index at which parent of the Mydata instance at the received index exists
     * @param i receives index of the Mydata instance
     * @return index at which the parent exists
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }


    //void inorderTraversal(); void preorderTraversal(); void postorderTraversal();
    //obvious meanings; prints data of nodes one line at a time; prints only the nodes where actual data exists
    // (i.e., no holes)

    /**
     * Prints an in-orderly traversed representation of the data contained in the calling instance of this class
     */
    void inorderTraversal() {
        StringBuilder toPrint = new StringBuilder();
        if (root() == -1) {
            outFilePrint.println("\tInorder traversal is:\n" + "\t\tEMPTY" + "\n");
        } else {
            buildInorder(root(), toPrint);
            outFilePrint.println("\tInorder traversal is:\n" + toPrint.toString() + "\n");
        }
    }

    /**
     * Prints a pre-orderly traversed representation of the data contained in the calling instance of this class
     */
    void preorderTraversal() {
        StringBuilder toPrint = new StringBuilder();
        if (root() == -1) {
            outFilePrint.println("\tPreorder traversal is:\n" + "\t\tEMPTY" + "\n");
        } else {
            buildPreorder(root(), toPrint);
            outFilePrint.println("\tPreorder traversal is:\n" + toPrint.toString() + "\n");
        }
    }

    /**
     * Prints a post-orderly traversed representation of the data contained in the calling instance of this class
     */
    void postorderTraversal() {
        StringBuilder toPrint = new StringBuilder();
        if (root() == -1) {
            outFilePrint.println("\tPostorder traversal is:\n" + "\t\tEMPTY" + "\n");
        } else {
            buildPostorder(root(), toPrint);
            outFilePrint.println("\tPostorder traversal is:\n" + toPrint.toString() + "\n");
        }
    }

    /**
     * Inserts x into the tree; returns the index of the array where inserted, or -1 if unsuccessful
     * @param x receives the Mydata instance to add
     * @return the index at which the entry was inserted
     */
    int insert (Mydata x) {
        if (x.getStuName() == null)
            return -1;
        int toRet = insertRecur(x, 0);
        treeSize++;
        if (lastIndexUsed < toRet)
            lastIndexUsed = toRet;
        return toRet;
    }

    /**
     * Deletes x from the tree – BST is maintained; returns -1 if x doesn’t exist, otherwise the index where x was
     * @param x receives the Mydata instance to delete
     * @return [0]: the index at which the deleted entry existed; -1 if it didn't - [1]: no. of descendants of the
     * deleted node (if it existed; -1 otherwise)
     */
    int[] delete (Mydata x) {
        int foundAt = search(x);
        int[] toRet = new int[2];
        if (foundAt == -1) {
            toRet[0] = -1;
            toRet[1] = -1;
            return toRet;
        } else {
            toRet[1] = count(foundAt) - 1;
            if (leftchild(foundAt) > lastIndexUsed || (tree.get(leftchild(foundAt)) == null &&
                    (rightchild(foundAt) > lastIndexUsed || tree.get(rightchild(foundAt)) == null))) {
                tree.set(foundAt, null);
            } else if (rightchild(foundAt) <= lastIndexUsed && tree.get(leftchild(foundAt)) == null &&
                    tree.get(rightchild(foundAt)) != null) {
                tree.set(foundAt, tree.get(rightchild(foundAt)));
                tree.set(rightchild(foundAt), null);
                move(foundAt, rightchild(foundAt));
            } else if ((leftchild(foundAt) <= lastIndexUsed && tree.get(leftchild(foundAt)) != null) ||
                    (rightchild(foundAt) <= lastIndexUsed && tree.get(leftchild(foundAt)) != null &&
                            tree.get(rightchild(foundAt)) == null)) {
                tree.set(foundAt, tree.get(leftchild(foundAt)));
                tree.set(leftchild(foundAt), null);
                move(foundAt, leftchild(foundAt));
            } else {
                int rightMostIndex = getRightMostIndex(leftchild(foundAt));
                tree.set(foundAt, tree.get(rightMostIndex));
                tree.set(rightMostIndex, null);
            }
        }
        treeSize--;
        if (foundAt == lastIndexUsed) {
            for (int i = foundAt; i >= (parent(foundAt)); i--) {
                if (tree.get(i) != null) {
                    lastIndexUsed = i;
                    break;
                }
            }
        }
        toRet[0] = foundAt;
        return toRet;
    }

    /**
     * Returns the index where x exists, otherwise -1
     * @param x receives the Mydata instance to find
     * @return index at which the entry was found; -1 otherwise
     */
    int search (Mydata x) {
        if (root() == -1)
            return -1;
        return searchRecur(x, 0);
    }

    //… // add any other private / public methods that may help manipulation of BST

    /**
     * Traverses (in-order) through the data contained in the calling instance of this class and builds a string
     * @param thisIndex receives index of the root
     * @param toPrint reference to the StringBuilder to which traversed data is to be added
     */
    private void buildInorder (int thisIndex, StringBuilder toPrint) {
        if (thisIndex > lastIndexUsed || tree.get(thisIndex) == null)
            return;
        buildInorder(thisIndex * 2 + 1, toPrint);
        if (tree.get(thisIndex) != null) {
            if (!toPrint.toString().equals(""))
                toPrint.append("\n");
            toPrint.append(tree.get(thisIndex));
        }
        buildInorder(thisIndex * 2 + 2, toPrint);
    }

    /**
     * Traverses (pre-order) through the data contained in the calling instance of this class and builds a string
     * @param thisIndex receives index of the root
     * @param toPrint reference to the StringBuilder to which traversed data is to be added
     */
    private void buildPreorder (int thisIndex, StringBuilder toPrint) {
        if (thisIndex > lastIndexUsed || tree.get(thisIndex) == null)
            return;
        if (tree.get(thisIndex) != null) {
            if (!toPrint.toString().equals(""))
                toPrint.append("\n");
            toPrint.append(tree.get(thisIndex));
        }
        buildPreorder(thisIndex * 2 + 1, toPrint);
        buildPreorder(thisIndex * 2 + 2, toPrint);
    }

    /**
     * Traverses (post-order) through the data contained in the calling instance of this class and builds a string
     * @param thisIndex receives index of the root
     * @param toPrint reference to the StringBuilder to which traversed data is to be added
     */
    private void buildPostorder (int thisIndex, StringBuilder toPrint) {
        if (thisIndex > lastIndexUsed || tree.get(thisIndex) == null)
            return;
        buildPostorder(thisIndex * 2 + 1, toPrint);
        buildPostorder(thisIndex * 2 + 2, toPrint);
        if (tree.get(thisIndex) != null) {
            if (!toPrint.toString().equals(""))
                toPrint.append("\n");
            toPrint.append(tree.get(thisIndex));
        }
    }

    /**
     * Finds the right place for the Mydata instance to insert and inserts it there
     * @param x the Mydata instance to insert
     * @param index initially - index of the root; in general - index in consideration (recursively)
     * @return the index at which insertion was made
     */
    private int insertRecur (Mydata x, int index) {
        if (lastIndexUsed < index || tree.get(index) == null) {
            for (int i = lastIndexUsed + 1; i <= index; i++)
                tree.add(null);
            tree.set(index, x);
            return index;
        } else if (x.compareTo(tree.get(index)) >= 0) {
            return insertRecur(x, (rightchild(index)));
        } else {
            return insertRecur(x, (leftchild(index)));
        }
    }

    /**
     * Returns index of the right most child of the Mydata instance at the received index
     * @param index receives index of the Mydata instance
     * @return index of the right most child
     */
    private int getRightMostIndex (int index) {
        if (lastIndexUsed < rightchild(index) || tree.get(rightchild(index)) == null)
            return index;
        return getRightMostIndex(rightchild(index));
    }

    /**
     * Tries to find received the Mydata instance
     * @param x the Mydata instance to find
     * @param index initially - receives index of the root; in general - index in consideration (recursively)
     * @return the index at which a match was found; -1 otherwise
     */
    private int searchRecur (Mydata x, int index) {
        if (lastIndexUsed < index || tree.get(index) == null) {
            return -1;
        } else if (tree.get(index).compareTo(x) == 0) {
            return index;
        } else if (x.compareTo(tree.get(index)) > 0) {
            return searchRecur(x, (rightchild(index)));
        } else {
            return searchRecur(x, (leftchild(index)));
        }
    }

    /**
     * Moves all descendants of one instance of Mydata class to another
     * @param toIndex receives index of the Mydata instance to which the descendants are to be moved
     * @param fromIndex receives index of the Mydata instance from which the descendants are to be moved
     */
    private void move (int toIndex, int fromIndex) {
        if (lastIndexUsed < fromIndex || tree.get(fromIndex) == null)
            return;
        tree.set(leftchild(toIndex), tree.get(leftchild(fromIndex)));
        tree.set(leftchild(fromIndex), null);
        tree.set(rightchild(toIndex), tree.get(rightchild(fromIndex)));
        tree.set(rightchild(fromIndex), null);
        move(leftchild(toIndex), leftchild(fromIndex));
        move(rightchild(toIndex), rightchild(fromIndex));
    }

    /**
     * Counts (descendants + itself (1)) no. of tree indices starting at the received index
     * @param index receives the index from which counting is to be started
     * @return no. of descendants of the received node + 1
     */
    private int count (int index) {
        if (lastIndexUsed < index || tree.get(index) == null)
            return 0;
        return count(leftchild(index)) + count(rightchild(index)) + 1;
    }

    /**
     * Returns no. of entries stored
     * @return value contained in treeSize variable
     */
    int getTreeSize() {
        return treeSize;
    }

    /**
     * Calculates height of the tree
     * @return height of the tree
     */
    int height() {
        return (int) Math.ceil((Math.log(lastIndexUsed + 1) / Math.log(2)) - 1);
    }

}