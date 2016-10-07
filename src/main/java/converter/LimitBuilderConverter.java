package converter;

import sqlbuilder.LimitBuilder;

import javax.servlet.http.HttpServletRequest;

public class LimitBuilderConverter implements Converter<HttpServletRequest, LimitBuilder> {
    @Override
    public LimitBuilder convert(HttpServletRequest storage) {
        String paginationSize = storage.getParameter("paginationSize");
        String page = storage.getParameter("page");

        return new LimitBuilder(page, paginationSize);
    }
}
