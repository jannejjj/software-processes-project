package at.fhv;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component()
public class AutomaticHandling implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
    Integer creditAmount = (Integer) execution.getVariable("creditAmount");
    Integer equityCapital = (Integer) execution.getVariable("equityCapital");
        execution.setVariable("approved", equityRatioIsOK(creditAmount, equityCapital));
    }

    public Boolean equityRatioIsOK(Integer creditAmount, Integer equityCapital) {

        Double procent = ((double ) equityCapital / creditAmount) ;
        if (procent >= 0.4 && procent < 2) {
            return true;
        }
        return false;
    }
}
