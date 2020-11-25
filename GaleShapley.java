import java.util.Arrays;

public class GaleShapley {

    public static void main(String[] args) {
        /***
         *
         *
         *
         *
         Initialize all men and women to free
         while there exist a free man m who still has a woman w to propose to
         {
         w = m's highest ranked such woman to whom he has not yet proposed
         if w is free
         (m, w) become engaged
         else some pair (m', w) already exists
         if w prefers m to m'
         (m, w) become engaged
         m' becomes free
         else
         (m', w) remain engaged
         }
         *
         */
        int prefer[][] = new int[][]{{7, 5, 6, 4},
                {5, 4, 6, 7},
                {4, 5, 6, 7},
                {4, 5, 6, 7},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3},
                {0, 1, 2, 3}};
        int size = prefer.length;
        if(size % 2 != 0){
            System.out.print("Invalid input");
        }else {
            int N = size/2;
            int[] output = stableMarriage(prefer, N);
            for (int i = 0; i < output.length; i++) {
                System.out.println("women: " + (i + N) + "-> men: " + output[i]);
            }
        }
    }

    public static int[] stableMarriage(int prefer[][], int N) {
        int[] women = new int[N]; //here, 0th index woman means Nth woman
        boolean[] freeMen = new boolean[N]; //initialize all men as free freeMan[i] = false means that the ith Man is occupied
        Arrays.fill(women, -1); //initialize all women as free
        int free = N; //All men are free
        // loop when all get occupied
        while(free > 0){
            //pick first free man
            int freeMan = -1;
            for(int i=0; i<freeMen.length; i++){
                if(!freeMen[i]){
                    freeMan = i;
                    break;
                }
            }
            if(freeMan == -1) break;

            for(int i=0; i<N; i++){
                int w = prefer[freeMan][i];
                int wIndex = w-N;
                // if free then engage
                if(women[wIndex] == -1){
                    women[wIndex] = freeMan;
                    freeMen[freeMan] = true;
                    free--;
                    break;
                }else{
                    int engagedMan = women[wIndex];
                    // if w prefers freeMan then break engagement
                    if(breakEngagement(prefer, freeMan, engagedMan, w)){
                        women[wIndex] = freeMan;
                        freeMen[engagedMan] = false;
                        freeMen[freeMan] = true;
                        break;
                    }
                }
            }

        }
        return women;
    }

    private static boolean breakEngagement(int[][] prefer, int man, int engagedMan, int w){
        int[] womenPref = prefer[w];
        for(int i=0; i<womenPref.length; i++){
            if(womenPref[i] == man) return true;
            if(womenPref[i] == engagedMan) return false;
        }
        return false;
    }

}
