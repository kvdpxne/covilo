package me.kvdpxne.covilo.presentation.payloads;

public record DeleteCategoryRequest(
  String identifier,
  String name
) {}
