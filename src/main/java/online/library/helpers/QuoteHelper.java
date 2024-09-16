package online.library.helpers;

import online.library.beans.QuoteOutputBean;
import online.library.entities.Quote;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteHelper {
    public QuoteOutputBean convertToBean(Quote quote) throws IOException {
        QuoteOutputBean bean = new QuoteOutputBean();
        bean.setId(quote.getId());
        bean.setPrivate(quote.isPrivate());
        bean.setBookTitle(quote.getBook().getTitle());
        bean.setUsername(quote.getUser().getUsername());
        bean.setQuoteText(quote.getQuote());
        bean.setUserProfilePicture(FileConverterHelper.convertImageToBase64(quote.getUser().getProfilePicture()));
         return  bean;
    }

    public List<QuoteOutputBean> convertToList(List<Quote> quotes) throws IOException {
        List<QuoteOutputBean> bean = new ArrayList<>();
        for(Quote q : quotes){
            bean.add(convertToBean(q));
        }
        return bean;
    }
}
