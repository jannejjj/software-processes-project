package at.fhv;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class AutomaticHandling implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Double creditAmount = (Double) execution.getVariable("creditAmount");
        Double equityCapital = (Double) execution.getVariable("equityCapital");

        execution.setVariable("approved", equityRatioIsOK(creditAmount, equityCapital));
    }

    public Boolean equityRatioIsOK(Double creditAmount, Double equityCapital) {
        System.err.println(equityCapital / creditAmount);
        if ((equityCapital / creditAmount) >= 0.4) {
            return true;
        } else {
            return false;
        }
    }
}
