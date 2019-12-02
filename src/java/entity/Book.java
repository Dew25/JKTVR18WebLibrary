/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author melnikov
 */
@Entity
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic()
    @Column()
    private String title;
    private String author;
    private int year;
    private int price;
    private boolean active;

    public Book() {
    }

    public Book(String title, String author, int year, int price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.active = true;
    }

    
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Book{" 
                + "id=" + id 
                + ", title=" + title 
                + ", author=" + author 
                + ", year=" + year 
                + ", price=" + price 
                + ", active=" + active 
                + '}';
    }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 17 * hash + Objects.hashCode(this.id);
    hash = 17 * hash + Objects.hashCode(this.title);
    hash = 17 * hash + Objects.hashCode(this.author);
    hash = 17 * hash + this.year;
    hash = 17 * hash + this.price;
    hash = 17 * hash + (this.active ? 1 : 0);
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
    final Book other = (Book) obj;
    if (this.year != other.year) {
      return false;
    }
    if (this.price != other.price) {
      return false;
    }
    if (this.active != other.active) {
      return false;
    }
    if (!Objects.equals(this.title, other.title)) {
      return false;
    }
    if (!Objects.equals(this.author, other.author)) {
      return false;
    }
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    return true;
  }
    
}
