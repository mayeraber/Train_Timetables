import java.io.*;
import java.util.IntSummaryStatistics;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by MNA on 7/16/2017.
 */
public class TimeTables {

    static Kattio kattio = new Kattio(System.in, System.out);

    public static void main(String[] args){

        int numCases = kattio.getInt();
        int caseNum=0;

        PriorityQueue<Integer> fromA = new PriorityQueue<>();
        PriorityQueue<Integer> fromB = new PriorityQueue<>();
        PriorityQueue<Integer> toA = new PriorityQueue<>();
        PriorityQueue<Integer> toB = new PriorityQueue<>();

        for(int y=0; y<numCases; y++)
        {
            String tripTime;
            int numATrains=0, numBTrains=0, tempA, tempB;

            int turnaroundTime = kattio.getInt();

            int ab = kattio.getInt();  //number of trips from A to B
            int ba =  kattio.getInt();  //number of trips from B to A

            //process all trips from A to B
            for(int x=0; x<ab; x++)
            {
                //get time of arrival at A
                tripTime = kattio.nextToken();

                //call method to parse the string into an integer
                fromA.add(parseTime(tripTime));

                //get time of arrival at B
                tripTime = kattio.nextToken();

                //call method to parse into integer and call mathod to add the turnaround time
                toB.add(addTurnAround(parseTime(tripTime), turnaroundTime));
            }

            //process B to A
            for(int x=0; x<ba; x++)
            {
                tripTime = kattio.nextToken();

                fromB.add(parseTime(tripTime));

                tripTime = kattio.nextToken();

                toA.add(addTurnAround(parseTime(tripTime), turnaroundTime));
            }

            //calculate number of trains needed using method numTrainsNeeded
            numATrains= numTrainsNeeded(fromA, toA);
            numBTrains = numTrainsNeeded(fromB, toB);

            //print results
            System.out.println("Case #"+ ++caseNum+": "+numATrains+" "+numBTrains);

            //clear the prioQueues for the next case
            fromA.clear();
            fromB.clear();
            toA.clear();
            toB.clear();
        }
    }

    //parse the departure/arrival time to be able to treat it a s an Integer
    public static int parseTime(String tripTime)
    {
        String parsedTimes;
        int charLoc, numATrains=0, numBTrains=0, tempA, tempB;

            charLoc = 0;
            parsedTimes= "";
            //remove leading zeros
            while(tripTime.charAt(charLoc)=='0')
                charLoc++;
            //remove colon
            while(tripTime.charAt(charLoc)!=':')
                parsedTimes+=tripTime.charAt(charLoc++);
            charLoc++;
            while(charLoc<tripTime.length())
                parsedTimes+=tripTime.charAt(charLoc++);
            return Integer.parseInt(parsedTimes);
    }

    //add the time needed to turn around to the arrival time
    // to calculate when the train will be available for another trip
    public static int addTurnAround(int arrivalTime, int turnTime)
    {
        int minutes = arrivalTime%100;
        int hours = arrivalTime - minutes;
        int minWithTurnaround= minutes+turnTime;
        if(minWithTurnaround>=60)
        {
            hours+=100;
            minWithTurnaround-=60;
        }

        return hours+minWithTurnaround;
    }

    //calculate number of trains needed
    public static int numTrainsNeeded(PriorityQueue<Integer> from, PriorityQueue<Integer> to)
    {
        int numTrainsNeeded= 0;
        int temp;
        while(!from.isEmpty())
        {
            if(to.isEmpty())
            {
                numTrainsNeeded+=from.size();
                break;
            }
            temp = from.poll();
            if(to.peek()>temp)
                numTrainsNeeded++;
            else
                to.poll();
        }
        return numTrainsNeeded;
    }

    //taken from Kattis, for enhanced i/o performance
    private static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }
        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        /*public boolean hasMoreTokens() {
            return peekToken() != null;
        }
        */
        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        private BufferedReader r;
        private String line;
        private StringTokenizer st;
        private String token;

        private String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) { }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}

