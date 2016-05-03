package ua.heatloss.web.utils;

import org.springframework.ui.Model;
import ua.heatloss.dao.AbstractDao;


public class PagingUtils {

    public static int preparePaging(PagingWraper pagingWraper, Long total, Model model) {
        Integer limit = pagingWraper.getLimit();
        if (limit == null || limit == 0) {
            limit = AbstractDao.DEFAULT_LIMIT;
        }
        long pages = total / limit;
        int extraPage = (total % limit) != 0 ? 1 : 0;
        int pagesQuantity = (int) (pages + extraPage);

        model.addAttribute("pagesQuantity", pagesQuantity);
        model.addAttribute("offset", pagingWraper.getOffset());
        model.addAttribute("limit", limit);
        return pagesQuantity;
    }
}
