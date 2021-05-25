package studying;

import studying.data_structures.hash_table.Entry;
import studying.data_structures.hash_table.HashTableSeparateChaining;
import studying.design_patterns.implementations.decorator_implementation.HawaiianSalad;
import studying.design_patterns.implementations.decorator_implementation.RomanianSalad;
import studying.design_patterns.implementations.decorator_implementation.Salad;
import studying.design_patterns.decorator.salad_decorators.Ananas;
import studying.design_patterns.decorator.salad_decorators.CheesePieces;
import studying.design_patterns.decorator.salad_decorators.MeatPieces;
import studying.design_patterns.decorator.salad_decorators.Oil;
import studying.design_patterns.implementations.observer_implementation.Foul;
import studying.design_patterns.implementations.observer_implementation.Goal;
import studying.design_patterns.implementations.observer_implementation.MatchScores;

import java.util.Iterator;


public class App {

    public static void main(String[] args) {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("This is your current operating system: " + osName);

        currentWorkingOnIssueTest();
    }

    public static void currentWorkingOnIssueTest() {
        HashTableSeparateChaining<String, Integer> hashTableSeparateChaining = new HashTableSeparateChaining<>();
        hashTableSeparateChaining.put("andrea", 29);
        hashTableSeparateChaining.put("federica", 30);
        hashTableSeparateChaining.put("luigi", 29);
        hashTableSeparateChaining.put("riccardo", 29);
        hashTableSeparateChaining.put("giacomo", 29);

        hashTableSeparateChaining.print();
    }

    public void testDecoratorPattern() {
        Salad hawaiianSalad = new HawaiianSalad();
        hawaiianSalad.printDetails();

        // Adding double Ananas and Oil
        hawaiianSalad = new Ananas(new Ananas(new Oil(new CheesePieces(hawaiianSalad))));
        hawaiianSalad.printDetails();

        Salad romanianSalad = new RomanianSalad();
        romanianSalad = new MeatPieces(romanianSalad);
        romanianSalad = new MeatPieces(romanianSalad);
        romanianSalad.printDetails();
    }


    /**
     * Observer design pattern demonstration
     */
    public void testObserverDesignPatter() {
        MatchScores matchScores = new MatchScores();
        Goal goal = new Goal(matchScores);
        Foul foul = new Foul(matchScores);

        matchScores.payload.goals = 3;
        matchScores.payload.fouls = 12;
        matchScores.notifyObservers();

        matchScores.unregisterObserver(goal);
        matchScores.notifyObservers();

        matchScores.unregisterObserver(foul);
        matchScores.notifyObservers();
    }
}
