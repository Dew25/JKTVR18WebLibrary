/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Melnikov
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Book book;
    private String commentText;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private boolean avalable;

  public Comment() {
  }

  public Comment(User user, Book book, String commentText, Date date, boolean avalable) {
    this.user = user;
    this.book = book;
    this.commentText = commentText;
    this.date = date;
    this.avalable = avalable;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public String getCommentText() {
    return commentText;
  }

  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isAvalable() {
    return avalable;
  }

  public void setAvalable(boolean avalable) {
    this.avalable = avalable;
  }

  @Override
  public String toString() {
    return "Comment{" + "id=" + id + ", user=" + user.getLogin() + ", book=" + book.getTitle() + ", commentText=" + commentText.substring(0, 20) + ", date=" + date + ", avalable=" + avalable + '}';
  }
    
  
}
