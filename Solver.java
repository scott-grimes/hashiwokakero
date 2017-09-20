import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Solver{
    
    public static int[][] islands;
    public static ArrayList<int[]> islandList;
    public static int[][] adjacency;
    
    public static void main(String []args){
        readIslands();
        buildAdjacency();
        printIslands();
        int i = islandInPos(3,3);
        System.out.println("The island in position 0,0 is index "+i);
        System.out.println("Index of Island to the North: "+partner(i,'n'));
        System.out.println("Index of Island to the South: "+partner(i,'s'));
        System.out.println("Index of Island to the East: "+partner(i,'e'));
        System.out.println("Index of Island to the West: "+partner(i,'w'));
        
    }


    /*Reads in the islands from stdin, stores them in the *islands arraylist */
    public static void readIslands(){
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        islandList = new ArrayList<int[]>();
        int maxX = 0; //width of our board
        int maxY = 0; //height of our board
        int islandCount = 0; //number of islands
        String line = "";
        try{
            //reads in each island from stdin
            while ((line = stdin.readLine()) != null){
                int[] row = new int[3];
                for(int i = 0;i<3;i++){
                    row[i] = Integer.parseInt(line.split(",")[i]);
                    }
                islandList.add(row);
                islandCount++;
                
                //Increases the size of our gameboard as islands are read
                if(row[0]>maxX){
                    maxX = row[0];
                }
                if(row[1]>maxY){
                    maxY = row[1];
                }
            }
            
            //creates our gameboard
            islands = new int[maxY+1][maxX+1];
            for(int i = 0;i<islands.length;i++){
                java.util.Arrays.fill(islands[i],-1);
            }
            
            //adds each island to our gameboard (0,0) is in the bottom left
            for(int i = 0;i<islandCount;i++){
                int[] j = islandList.get(i);
                islands[maxY-j[1]][j[0]] = i;
            }
            
            
        }
        catch(Exception e)
        {
        }
        
        
    }
    
    /* Prints the label for each island, then the number of required bridges for each island */
    public static void printIslands(){
        System.out.println("Island Identifiers");
        for(int[] row : islands){
                for(int i : row)
                {
                        if(i==-1){
                            System.out.print("   ");
                        }
                        else{
                            System.out.printf("%2d ",i);
                        }
                        
                }
                System.out.println();
            }
        System.out.println();
        System.out.println("Bridges Required");
        for(int[] row : islands){
                for(int i : row)
                {
                        if(i==-1){
                            System.out.print("   ");
                        }
                        else{
                            System.out.printf("%2d ",islandList.get(i)[2]);
                        }
                }
                System.out.println();
            }
            
        System.out.println();
        System.out.println("Adjacency Matrix");
        for(int[] row : adjacency){
                for(int i : row)
                {
                        System.out.print(i+" ");
                }
                System.out.println();
            }
        
    }
  
    
    /*Builds the adjacency matrix which represents which islands are connected */
    public static void buildAdjacency(){
        adjacency = new int[islandList.size()][islandList.size()];
        char[] directions = {'n','s','e','w'};
        for(int i = 0;i<islandList.size();i++){
            for(char direction : directions){
                int connection = partner(i,direction);
                if(connection!=-1){
                    adjacency[i][connection] = 1;
                    adjacency[connection][i] = 1;
                }
            }
        }
        
        
        
    }
    
    /* Finds the partner of a island *a in a particular direction
       if island *a has no partner in the *direction given, returns -1. Otherwise, 
       returns the index of the nearest islandin *direction...
       n - north
       s - south
       e - east
       w - west
       */
    
    public static int partner(int a, char direction){
        int[] delta = {0,0};
        int[] pos = {islandList.get(a)[0],islandList.get(a)[1]};
        
        switch(direction){
            case 'n':
                delta[1]=1;
                break;
            case 's':
                delta[1]=-1;
                break;
            case 'e':
                delta[0]=1;
                break;
            case 'w':
                delta[0]=-1;
                break;
        }
        int partner = -1;
        do{
            pos[0]+=delta[0];
            pos[1]+=delta[1];
            partner = islandInPos(pos[0],pos[1]);
            if(partner!=-1){
                break;
            }
            
        }
        while(pos[0]<islands.length && pos[1]<islands.length && pos[0]>-1 && pos[1]>-1 );
        
        return partner;
    }
    
    /* returns the index of the island in a given position.
       If no island exists, or we access an out of bounds location,
       in that position, we return a -1*/
     public static int islandInPos(int xc, int yc){
        if(xc>islands.length || yc > islands.length || xc<0 || yc<0){
            return -1;
        }
        for(int i = 0;i<islandList.size();i++)
        {
            if((islandList.get(i)[0]==xc)&&(islandList.get(i)[1]==yc))
            return i;
        }
        return -1;
    }
     
     
}
