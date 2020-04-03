package hw5cs3310_Khan;

import java.io.*;
import java.util.Scanner;

/**
 * This program has been written in response to question 5 of the assignment and reads inputs, line by line, from a
 * file named 'hw5cs3310Sp2020data.txt' - performs the instructions it contains and correspondingly, either inserts to
 * or deletes an entry, searches for one or prints the contained entries using inorder, preorder or postorder traversal
 * from a Binary Search Tree - Output associated with each of the first 100 instructions is written to a file named
 * 'hw5output_M. Salman Khan.txt'.
 *
 * @author M. Salman Khan
 */
class Main {

    /**
     * Main method takes inputs, calls other methods to accomplish certain tasks and outputs
     * @param args is not used
     * @throws IOException can be thrown if an error occurs during input/output
     */
    public static void main(String[] args) throws IOException {

        Scanner inFileScan = new Scanner(new File("hw5cs3310Sp2020data.txt"));
        PrintWriter outFilePrint = new PrintWriter(new FileWriter("hw5output_M. Salman Khan.txt"));
        String thisInstruction;
        BinaryStreeImplicit tree = new BinaryStreeImplicit(outFilePrint);

        int[] insCount = {0, 0, 0, 0, 0, 0};
        // insCount[0] -> 'insert' - 50 instructions in input file
        // insCount[1] -> 'delete' - 16 instructions
        // insCount[2] -> 'search' - 15 instructions
        // insCount[3] -> 'inorder traversal' - 8 instructions
        // insCount[4] -> 'preorder traversal' - 5 instructions
        // insCount[5] -> 'postorder traversal' - 7 instructions

        int treeSize;
        int height;
        long stTime, enTime;
        int count = 0;

        while (inFileScan.hasNextLine() && count < 100) { // Through one of the discussion groups on canvas, Dr. Gupta
                                                          // instructed to output for first 100 instructions only
            treeSize = tree.getTreeSize();
            height = tree.height();
            thisInstruction = inFileScan.nextLine();
            outFilePrint.println(thisInstruction);
            if (thisInstruction.startsWith("Insert")) {
                insCount[0]++;
                thisInstruction = thisInstruction.substring(8);
                String[] addTokens = thisInstruction.split(",");
                for (int i = 0; i < addTokens.length; i++) {
                    addTokens[i]= addTokens[i].strip();
                }
                Mydata toAdd = new Mydata(addTokens[0], Integer.parseInt(addTokens[1]), addTokens[2].charAt(0));
                stTime = System.nanoTime();
                outFilePrint.printf("\t%s added at index %d\n\n", toAdd.getStuName(), tree.insert(toAdd));
                enTime = System.nanoTime();
                if (insCount[0] == 10 || insCount[0] == 20 || insCount[0] == 25 || insCount[0] == 40)
                    System.out.printf("Time taken to Insert: %24d ns\t\tn = %-2d\t\th = %-5s\n", (enTime - stTime),
                            treeSize, ((height >= 0) ? String.valueOf(height) : "EMPTY"));
            } else if (thisInstruction.startsWith("Delete")) {
                insCount[1]++;
                Mydata toDelete = new Mydata(thisInstruction.substring(8), 0, '0');
                stTime = System.nanoTime();
                int[] foundAt = tree.delete(toDelete);
                enTime = System.nanoTime();
                if (foundAt[0] == -1) {
                    outFilePrint.printf("\t%s was not deleted as no match was found\n\n",
                            toDelete.getStuName());
                } else {
                    outFilePrint.printf("\t%s deleted from index %d\n\n", toDelete.getStuName(), foundAt[0]);
                }
                if (insCount[1] == 1 || insCount[1] == 5 || insCount[1] == 7 || insCount[1] == 13)
                    System.out.printf("Time taken to Delete: %24d ns\t\tn = %-2d\t\th = %-5s\t\tNo. of Descendants" +
                                    " = %s\n", (enTime - stTime), treeSize,
                            ((height >= 0) ? String.valueOf(height) : "EMPTY"),
                            ((foundAt[0] == -1) ? "NO MATCH FOUND" : String.valueOf(foundAt[1])));
            } else if (thisInstruction.startsWith("Search")) {
                insCount[2]++;
                Mydata toSearch = new Mydata(thisInstruction.substring(8), 0, '0');
                stTime = System.nanoTime();
                int foundAt = tree.search(toSearch);
                enTime = System.nanoTime();
                if (foundAt == -1) {
                    outFilePrint.printf("\t%s not found\n\n", toSearch.getStuName());
                } else {
                    outFilePrint.printf("\t%s found at index %d\n\n", toSearch.getStuName(), foundAt);
                }
                if (insCount[2] == 2 || insCount[2] == 5 || insCount[2] == 7 || insCount[2] == 15)
                    System.out.printf("Time taken to Search: %24d ns\t\tn = %-2d\t\th = %-5s\n", (enTime - stTime),
                            treeSize, ((height >= 0) ? String.valueOf(height) : "EMPTY"));
            } else if (thisInstruction.startsWith("Preorder")) {
                insCount[4]++;
                stTime = System.nanoTime();
                tree.preorderTraversal();
                enTime = System.nanoTime();
                if (insCount[4] == 1 || insCount[4] == 2 || insCount[4] == 3 || insCount[4] == 4)
                    System.out.printf("Time taken for Preorder Traversal: %11d ns\t\tn = %-2d\t\th = %-5s\n",
                            (enTime - stTime), treeSize,
                            ((height >= 0) ? String.valueOf(height) : "EMPTY"));
            } else if (thisInstruction.startsWith("Inorder")) {
                insCount[3]++;
                stTime = System.nanoTime();
                tree.inorderTraversal();
                enTime = System.nanoTime();
                if (insCount[3] == 2 || insCount[3] == 4 || insCount[3] == 6 || insCount[3] == 8)
                    System.out.printf("Time taken for Inorder Traversal: %12d ns\t\tn = %-2d\t\th = %-5s\n",
                            (enTime - stTime), treeSize,
                            ((height >= 0) ? String.valueOf(height) : "EMPTY"));
            } else if (thisInstruction.startsWith("Postorder")) {
                insCount[5]++;
                stTime = System.nanoTime();
                tree.postorderTraversal();
                enTime = System.nanoTime();
                if (insCount[5] == 1 || insCount[5] == 3 || insCount[5] == 5 || insCount[5] == 7)
                    System.out.printf("Time taken for Postorder Traversal: %10d ns\t\tn = %-2d\t\th = %-5s\n",
                            (enTime - stTime), treeSize,
                            ((height >= 0) ? String.valueOf(height) : "EMPTY"));
            }
            count++;
        }

        inFileScan.close();
        outFilePrint.close();

        System.out.println("\nInstructions from file \"hw5cs3310Sp2020data.txt\" have been executed and " +
                "corresponding outputs\nhave been outputted to file \"hw5output_M. Salman Khan.txt\"");

    }

}