package me.kvdpxne.covilo.shared;

import java.util.List;

public interface Bookcase<T> {

  List<T> getContent();

  int getNumberOfShelf();

  int getSizeOfShelf();

  int getTotalSize();
}
