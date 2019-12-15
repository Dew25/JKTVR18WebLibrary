/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
public class Comment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Book book;
    private String commentText;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    private Date lastEditDate;
    private boolean avalable;

  public Comment() {
  }

  public Comment(User user, Book book, String commentText, Date createDate, boolean avalable) {
    this.user = user;
    this.book = book;
    this.commentText = commentText;
    this.createDate = createDate;
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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getLastEditDate() {
    return lastEditDate;
  }

  public void setLastEditDate(Date lastEditDate) {
    this.lastEditDate = lastEditDate;
  }

  public boolean isAvalable() {
    return avalable;
  }

  public void setAvalable(boolean avalable) {
    this.avalable = avalable;
  }

  @Override
  public String toString() {
    return "Comment{" + "id=" + id + ", user=" + user.getLogin() + ", book=" + book.getTitle() + ", commentText=" + commentText.substring(0, 10) + ", createDate=" + createDate + ", lastEditDate=" + lastEditDate + ", avalable=" + avalable + '}';
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + Objects.hashCode(this.id);
    hash = 79 * hash + Objects.hashCode(this.user);
    hash = 79 * hash + Objects.hashCode(this.book);
    hash = 79 * hash + Objects.hashCode(this.commentText);
    hash = 79 * hash + Objects.hashCode(this.createDate);
    hash = 79 * hash + Objects.hashCode(this.lastEditDate);
    hash = 79 * hash + (this.avalable ? 1 : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Comment other = (Comment) obj;
    if (this.avalable != other.avalable) {
      return false;
    }
    if (!Objects.equals(this.commentText, other.commentText)) {
      return false;
    }
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    if (!Objects.equals(this.user, other.user)) {
      return false;
    }
    if (!Objects.equals(this.book, other.book)) {
      return false;
    }
    if (!Objects.equals(this.createDate, other.createDate)) {
      return false;
    }
    if (!Objects.equals(this.lastEditDate, other.lastEditDate)) {
      return false;
    }
    return true;
  }
  

  
    
  
}
