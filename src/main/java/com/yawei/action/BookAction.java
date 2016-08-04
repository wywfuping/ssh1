package com.yawei.action;

import com.yawei.pojo.Book;
import com.yawei.pojo.BookType;
import com.yawei.pojo.Publisher;
import com.yawei.service.BookService;
import com.yawei.util.Page;
import com.yawei.util.SearchParam;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.inject.Inject;
import java.util.List;

@Namespace("/book")
public class BookAction extends BaseAction{
    @Inject
    private BookService bookService;
    private List<Book> bookList;
    private List<BookType> bookTypeList;
    private List<Publisher> publisherList;
    private Page<Book> page;
    private Integer p;
    private Book book;
    private Integer id;


    @Action("list")
    @Override
    public String execute() throws Exception {
        //bookList = bookService.findAllBook();

        if(p==null){
            p=1;
        }
        List<SearchParam> searchParamList= SearchParam.buildSearchParm(getHttpServletRequest());
        page = bookService.findByPage(p,searchParamList);
        bookTypeList = bookService.findAllBookType();
        publisherList = bookService.findAllPublisher();
        return SUCCESS;
    }

    @Action("new")
    public String toSave(){
        bookTypeList = bookService.findAllBookType();
        publisherList = bookService.findAllPublisher();
        return SUCCESS;
    }

    @Action(value = "save",results = {@Result(type = "redirectAction",
            params = {"actionName","list","namespace","/book"})})
    public String save(){
        bookService.saveBook(book);
        return SUCCESS;
    }

    @Action("edit")
    public String edit(){
        book = bookService.findBookById(id);
        bookTypeList = bookService.findAllBookType();
        publisherList = bookService.findAllPublisher();
        return SUCCESS;
    }

    @Action(value = "update",results = {@Result(type = "redirectAction",
            params = {"actionName","list","namespace","/book"})})
    public String update(){
        bookService.saveBook(book);
        return SUCCESS;
    }

    @Action(value = "del",results = {@Result(type = "redirectAction",
            params = {"actionName","list","namespace","/book"})})
    public String del(){
        bookService.delBook(id);
        return SUCCESS;
    }


    //get set
    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<BookType> getBookTypeList() {
        return bookTypeList;
    }

    public void setBookTypeList(List<BookType> bookTypeList) {
        this.bookTypeList = bookTypeList;
    }

    public List<Publisher> getPublisherList() {
        return publisherList;
    }

    public void setPublisherList(List<Publisher> publisherList) {
        this.publisherList = publisherList;
    }

    public Page<Book> getPage() {
        return page;
    }

    public void setPage(Page<Book> page) {
        this.page = page;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
