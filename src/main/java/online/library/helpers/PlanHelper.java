package online.library.helpers;

import online.library.beans.PlanOutputBean;
import online.library.entities.Plan;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanHelper {
    public PlanOutputBean convertToBean(Plan plan){
        PlanOutputBean bean = new PlanOutputBean();
        bean.setId(plan.getId());
        bean.setStartTime(plan.getStatTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Convert the Date object into a formatted String
        String formattedDate = dateFormat.format(plan.getDate());
        bean.setDate(formattedDate);
        if(plan.getBook() != null){
            bean.setBookTitle(plan.getBook().getTitle());
        }
        return bean;
    }

    public List<PlanOutputBean> convertToList(List<Plan> plans){
        List<PlanOutputBean> list = new ArrayList<>();
        for (Plan p : plans){
            list.add(convertToBean(p));
        }
        return  list;
    }
}
