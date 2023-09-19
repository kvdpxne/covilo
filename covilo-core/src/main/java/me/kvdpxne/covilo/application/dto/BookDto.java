package me.kvdpxne.covilo.application.dto;

public record BookDto<T>(
  T[] content,
  int page,
  int size
) {

}
