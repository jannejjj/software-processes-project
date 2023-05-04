package at.fhv;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class AutomaticHandling  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
       Integer creditAmount = (Integer)execution.getVariable("creditAmount");
       Integer equityCapital = (Integer)execution.getVariable("equityCapital");

       if((equityCapital/ creditAmount) >= 0.4  ){
        execution.setVariable("approved", true);     
       }else {
        execution.setVariable("approved", false);     
       }
    }
}
