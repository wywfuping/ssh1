package com.yawei.service;

import com.yawei.dao.BookDao;
import com.yawei.dao.BookTypeDao;
import com.yawei.dao.PublisherDao;
import com.yawei.pojo.Book;
import com.yawei.pojo.BookType;
import com.yawei.pojo.Publisher;
import com.yawei.util.Page;
import com.yawei.util.SearchParam;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class BookService {
    @Inject
    private BookDao bookDao;
    @Inject
    private BookTypeDao bookTypeDao;
    @Inject
    private PublisherDao publisherDao;

    public void saveBook(Book book){
        bookDao.save(book);
    }

    public void delBook(Integer id){
        bookDao.delete(id);
    }

    public List<Book> findAllBook(){
        return bookDao.findAll();
    }

    public Book findBookById(Integer id){
       return bookDao.findById(id);
    }

    public List<BookType> findAllBookType(){
        return bookTypeDao.findAll();
    }

    public List<Publisher> findAllPublisher(){
        return publisherDao.findAll();
    }

    public Page<Book> findByPage(Integer pageNo, List<SearchParam> searchParamList){
        return bookDao.findByPageNo(pageNo,5,searchParamList);
    }

}
