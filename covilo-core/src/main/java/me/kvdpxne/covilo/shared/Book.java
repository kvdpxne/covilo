package me.kvdpxne.covilo.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Book<T> implements Iterable<T> {

  private final List<T> elements;
  private final int index;

  public Book(final int index, final int count) {
    this.elements = 0 >= count ? new ArrayList<>() : new ArrayList<>(count);
    this.index = index;
  }

  public Book(final BookAttributes attributes) {
    this(attributes.page(), attributes.size());
  }

  public static <T> Book<T> boxed(final BookAttributes attributes, final Consumer<Book<T>> consumer) {
    final Book<T> book = new Book<>(attributes);
    consumer.accept(book);
    return book;
  }

  public void put(final T element) {
    this.elements.add(element);
  }

  public List<T> getContent() {
    return this.elements;
  }

  public int getIndex() {
    return index;
  }

  @Override
  public String toString() {
    return "Box{" +
      "content=" + elements +
      ", index=" + index +
      '}';
  }

  @Override
  public Iterator<T> iterator() {
    return this.elements.iterator();
  }
}
