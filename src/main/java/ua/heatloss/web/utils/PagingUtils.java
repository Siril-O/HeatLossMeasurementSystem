package ua.heatloss.web.utils;

import org.springframework.ui.Model;
import ua.heatloss.dao.AbstractDao;

import java.util.List;


public class PagingUtils {

    public static int preparePaging(Paging pagingWrapper, Long total, Model model) {
        Integer limit = pagingWrapper.getLimit();
        if (limit == null || limit == 0) {
            limit = AbstractDao.DEFAULT_LIMIT;
        }
        long pages = total / limit;
        int extraPage = (total % limit) != 0 ? 1 : 0;
        int pagesQuantity = (int) (pages + extraPage);

        model.addAttribute("pagesQuantity", pagesQuantity);
        model.addAttribute("offset", pagingWrapper.getOffset());
        model.addAttribute("limit", limit);
        model.addAttribute("enablePaging", true);
        return pagesQuantity;
    }

    public static int prepareJSPaging(Paging pagingWrapper, Long total, Model model) {
        Integer limit = pagingWrapper.getLimit();
        if (limit == null || limit == 0) {
            limit = AbstractDao.DEFAULT_LIMIT;
        }
        long pages = total / limit;
        int extraPage = (total % limit) != 0 ? 1 : 0;
        int pagesQuantity = (int) (pages + extraPage);

        model.addAttribute("pagesQuantity", pagesQuantity);
        return pagesQuantity;
    }
}
