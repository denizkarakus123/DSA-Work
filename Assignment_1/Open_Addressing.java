import java.io.*;
import java.util.*;

/****************************
*
* COMP251 template file
*
* Assignment 1, Question 1
*
*****************************/

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }

     }

                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {
         Random generator = new Random();
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
            int hash = (((A * key) % power2(w)) >> (w-r));
            return (hash + i) % power2(r);
     }


     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
            int i = 0;

            while (i < m) {
                int hash = probe(key, i);

                if (Table[hash] == -1 || Table[hash] == -2) {
                    Table[hash] = key;
                    return i;
                }

                if (Table[hash] == key) {
                    return i;
                }

                
                i++;
            }

            return i;  
        }

        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }

         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key) {
            int slotsVisited = 0;
            int hash = probe(key, slotsVisited);

            while (slotsVisited < m) {
                if (Table[hash] == -1) {
                    //here we're returning the number of slots we 
                    //visited before giving up, that's why we
                    //add one
                    return slotsVisited+1;
                }
                if (Table[hash] == key) {
                    //delete
                    Table[hash] = -2;
                    //here we're returning the number of collisions
                    //which is ony equal to our current slotsVisited
                    //because we don't count the slot that we're currently
                    //on as a collision
                    return slotsVisited;
                }
                slotsVisited++;
                hash = probe(key, slotsVisited);
            }
            return slotsVisited;
        }
}
