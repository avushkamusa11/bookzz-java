package online.library.helpers;

import online.library.beans.YearGoalBean;
import online.library.entities.YearGoal;
import online.library.services.YearGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YearGoalHelper {

    public YearGoalBean convertToBean(YearGoal yearGoal){
        YearGoalBean bean = new YearGoalBean();

        bean.setId(yearGoal.getId());
        bean.setCount(yearGoal.getCount());
        bean.setYear(yearGoal.getYear());
        return  bean;
    }
}
