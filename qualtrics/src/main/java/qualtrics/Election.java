package qualtrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Election {
    private List<Nominee> nominees;
    private List<Nominator> nominators;
    private Map<Integer, String> positions; // key-value pairs representing qualtrics ID-positon name

    public Election() {
      nominees = new ArrayList<Nominee>();
      nominators = new ArrayList<Nominator>();
      positions = new HashMap<Integer, String>();
    }

    public void buildPositions(String[] toAdd) {
      for (int i = 0; i < toAdd.length; i++) {
        positions.put(i, toAdd[i]);
      }
    }

    public void processNominations(List<Response> responses) {
      for (Response r : responses) {
          if (r.getQ1().toCharArray()[0] == '1') {
              System.out.println(r.getQ4() + " is running for " + positions.get(Integer.parseInt(r.getQ10())));
              nominees.add(new Nominee(r.getQ4(), r.getQ6(), r.getQ5(), r.getQ9(), r.getQ10())); 
              // TODO: add phone number, year
          }
          if (r.getQ1().toCharArray()[0] == '2') {
              System.out.println(r.getQ11() + " nominated " + r.getQ15() + " for the position of " + positions.get(Integer.parseInt(r.getQ16())));
              nominators.add(new Nominator(r.getQ11(), r.getQ19(), r.getQ12(), r.getQ13(), r.getQ15(), r.getQ16())); 
              // TODO: add phone number, year
          }
      }
    }
    
    public void totalNominations() {
        HashMap<String, Integer> noms = new HashMap<>();
        for (Nominee n : nominees) {
            noms.put(n.getFullName(), 0);
        }
        for (Nominator n : nominators) {
            if (noms.get(n.getNominatingName()) != null) { 
              // TODO: check the nominee/nominator chose the same position
                noms.put(n.getNominatingName(), noms.get(n.getNominatingName()) + 1);
            }
        }
        for (Entry<String, Integer> n : noms.entrySet()) {
            System.out.println(n.getKey() + " was nominated " + n.getValue() + " times.");
        }
    }

    // Getters and setters

    public void addNominee(Nominee n) {
      nominees.add(n);
    }

    public void addNominator(Nominator n) {
      nominators.add(n);
    }

    public List<Nominee> getNominees() {
      return nominees;
    }

    public List<Nominator> getNominators() {
      return nominators;
    }
}
