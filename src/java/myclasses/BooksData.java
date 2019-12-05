/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myclasses;

import entity.Book;
import entity.Image;
import entity.Text;

/**
 *
 * @author Melnikov
 */
public class BooksData {
  private Book book;
  private Image image;
  private Text text;

  public BooksData() {
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public Text getText() {
    return text;
  }

  public void setText(Text text) {
    this.text = text;
  }
  
}
