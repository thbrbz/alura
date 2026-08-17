package com.thbrbz.bookstore.dtos;

import java.util.Set;
import java.util.UUID;

public record BookDto(String title,
                      UUID idPublisher,
                      Set<UUID> authorsId,
                      String reviewComment) {
}
