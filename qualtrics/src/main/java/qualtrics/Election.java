package qualtrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
              String position = positions.get(Integer.parseInt(r.getQ10()));
              App.logger.info(r.getQ4() + " is running for " + position);
              Nominee n = new Nominee(r.getQ4(), r.getQ6(), r.getQ5(), r.getQ9(), r.getQ10());
              n.setRunningForPositionName(position); 
              nominees.add(n);
              // TODO: add phone number, year
          }
          if (r.getQ1().toCharArray()[0] == '2') {
              App.logger.info(r.getQ11() + " nominated " + r.getQ15() + " for the position of " + positions.get(Integer.parseInt(r.getQ16())));
              nominators.add(new Nominator(r.getQ11(), r.getQ19(), r.getQ12(), r.getQ13(), r.getQ15(), r.getQ16())); 
              // TODO: add phone number, year
          }
      }
    }
    
    public void totalNominations() {
        for (Nominator nom : nominators) {
          for (Nominee nee : nominees) {
            if (nom.getNominatingName().equals(nee.getFullName())) {
              nee.incrementTally();
              break;
            }
          }
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
