package me.kvdpxne.covilo.presentation.dto;

public record BookDto<T>(
  T[] content,
  int page,
  int size
) {

}
