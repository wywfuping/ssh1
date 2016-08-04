package com.yawei.dao;

import com.yawei.pojo.Book;
import com.yawei.util.Page;
import com.yawei.util.SearchParam;
import org.hibernate.Criteria;

import javax.inject.Named;
import java.util.List;

@Named
public class BookDao extends BaseDao<Book,Integer>{
    @Override
    public Page<Book> findByPageNo(Integer pageNo, Integer pageSize, List<SearchParam> searchParamList) {
        Criteria criteria = getSession().createCriteria(Book.class);
        criteria.createAlias("bookType","bookType");
        return super.findByPageNo(criteria,pageNo, pageSize, searchParamList);
    }
}
