package ua.heatloss.web.utils;

import ua.heatloss.dao.AbstractDao;

import org.springframework.ui.Model;


public class PagingUtils {

    public static int preparePaging(Integer offset, Integer limit, Long total, Model model) {
        if (limit == null || limit == 0) {
            limit = AbstractDao.DEFAULT_LIMIT;
        }
        long pages = total / limit;
        int extraPage = (total % limit) != 0 ? 1 : 0;
        int pagesQuantity = (int) (pages + extraPage);

        model.addAttribute("pagesQuantity", pagesQuantity);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        return pagesQuantity;
    }
}
