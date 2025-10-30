/****************************
*
* COMP251 template file
*
* Assignment 1, Question 2b
*
*****************************/

/* contructor: creates a partition of n elements. */
/* Each element is in a separate disjoint set */
public class DisjointSetsB {

    private int[] par;
    private int[] rank;

    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSetsB(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }

    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }

    /* find resentative of element i */
    public int find(int i) {

        /* Fill this method (The statement return 0 is here only to compile) */
        if (i != par[i]) {
            par[i] = find(par[i]);
        }
        return par[i];

    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {

        /* Fill this method (The statement return 0 is here only to compile) */
        if (find(i) == find(j)) {
            return 0;
        }
        if (rank[find(i)] == rank[find(j)] ) {
            par[find(i)] = find(j);
            rank[find(j)]++;
        }
        else if (rank[find(i)] > rank[find(j)]) {
            par[find(j)] = find(i);
        }
        else {
            par[find(i)] = find(j);
        }

        return 0;

    }

    /* move i to the set containing j */
    public void move(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI == rootJ) {
            return;
        }
        //deal with the case where the node we're moving is the root of its set
        if (rootI == i) {
            //give all the children of rootI a new parent/root
            int newRoot = -1;
            for (int k = 0; k < par.length; k++) {
                //we want to find the first child of rootI that is not i
                if (par[k] == i && k != i) {
                    newRoot = k;
                    break;
                }
            }
            //if we find a new root, we want to update all of rootI's children to have that new root
            if (newRoot != -1) {
                for (int k = 0; k < par.length; k++) {
                    if (par[k] == i) {
                        par[k] = newRoot;
                    }
                }
            }
        }
        //deal with the case where the node we're moving is not the root of its set
        if(rootI != i) {
            for (int k = 0; k < par.length; k++) {
                if (par[k] == i) {
                    par[k] = rootI;
                }
            }
        }
        //now we can actually move i to the set containing j
        par[i] = rootJ;
    }

    /* return the sum of elements in the set of i */
    public int sum_elements(int i) {

        /* Fill this method */
        int rootI = find(i);
        int sum = 0;
        for (int k = 0; k < par.length; k++) {
            if (find(k) == rootI) {
                sum += k;
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        DisjointSetsB myset = new DisjointSetsB(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);

    }

}
