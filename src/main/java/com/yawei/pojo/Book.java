package com.yawei.pojo;

import javax.persistence.*;

@Entity
@Table(name = "t_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bookname;
    private Float bookprice;
    private String bookauthor;
    private Integer booknum;
    private String bookpic;
    @ManyToOne
    @JoinColumn(name = "typeid")
    private BookType bookType;
    @ManyToOne
    @JoinColumn(name = "pubid")
    private Publisher publisher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Float getBookprice() {
        return bookprice;
    }

    public void setBookprice(Float bookprice) {
        this.bookprice = bookprice;
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor;
    }

    public Integer getBooknum() {
        return booknum;
    }

    public void setBooknum(Integer booknum) {
        this.booknum = booknum;
    }

    public String getBookpic() {
        return bookpic;
    }

    public void setBookpic(String bookpic) {
        this.bookpic = bookpic;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}

