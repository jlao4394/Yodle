import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class JuggleFestEvaluator {
    public static void main(String... args) throws IOException {
        long millis = System.currentTimeMillis();

        // First count number of jugglers and circuits to know array size
        File input = new File("input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(input));

        int c = 0;
        int j = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.charAt(0) == 'C') {
                c++;
            } else if (line.charAt(0) == 'J') {
                j++;
            }
        }

        reader.close();

        final int jugglersPerCircuit = j / c;

        //Create a queue of jugglers to assign
        final Queue<Juggler> jugglers = new ArrayDeque<>(j);

        //Initialize new CircuitList with the amount of circuits
        final CircuitList circuitList = new CircuitList(c);

        //Read in and parse circuits and jugglers
        reader = new BufferedReader(new FileReader(input));

        int circuitIndex = 0;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            final String[] split = line.replaceAll("\\D+", " ").split(" ");

            //Create new Circuit object if line starts with C or new Juggler object if line starts with J
            if (line.charAt(0) == 'C') {
                circuitList.set(circuitIndex++, new Circuit(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]), jugglersPerCircuit));
            } else if (line.charAt(0) == 'J') {
                Circuit[] preferred = new Circuit[split.length - 5];
                for (int i = 0; i < preferred.length; i++) {
                    preferred[i] = circuitList.getCircuitByNumber(Integer.parseInt(split[5 + i]));
                }
                Juggler juggler = new Juggler(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]), preferred);

                jugglers.add(juggler);

                //Naive assign to preferences
                circuitList.assignToImmediateBest(juggler);
            }
        }
        reader.close();

        //Assign jugglers to circuits
        assignJugglers(jugglers);

        //Output to text file
        final File output = new File("output.txt");
        if (!output.exists()) {
            output.createNewFile();
        }

        final FileWriter writer = new FileWriter(output);

        String text = circuitList.toString();

        writer.write(text);
        writer.flush();
        writer.close();
        System.out.print(System.currentTimeMillis() - millis);
    }

    private static void assignJugglers(Queue<Juggler> jugglersToAssign) {
        outer:
        while (jugglersToAssign.size() > 0) {
            //Pull juggler from front of queue to assign
            Juggler juggler = jugglersToAssign.poll();
            Circuit circuit = juggler.getCircuit();

            //Pull most preferred circuit from front of juggler's preferences queue (most to least preferred) to try to assign juggler to
            Circuit preferred;
            while ((preferred = juggler.pollPreferences()) != null) {

                //If most preferred circuit is the one that juggler is already on, we can move on
                if (circuit.equals(preferred)) {
                    continue outer;
                }

                /**
                 * Compare current juggler's match score to match score of worst juggler on other team.
                 *
                 * Can only replace other jugglers if match score is better than at least one of the jugglers on that circuit,
                 * so we only need to check against the worst juggler.
                 */
                int matchScore = preferred.getMatchScore(juggler);
                int worstMatchScore = preferred.getWorstScore();
                if (matchScore > worstMatchScore) {
                    Juggler worstJuggler = preferred.getWorstJuggler();

                    //Replace jugglers
                    circuit.replaceJuggler(juggler, worstJuggler, worstMatchScore);
                    preferred.replaceJuggler(worstJuggler, juggler, matchScore);

                    //Add worstJuggler to queue to be reassigned
                    jugglersToAssign.add(worstJuggler);

                    continue outer;
                }
            }
        }
    }
}
