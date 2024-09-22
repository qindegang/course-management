package ai.minden.course_management.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageDTO<T>(List<T> content,
                         long totalElements,
                         long totalPages,
                         long number,
                         int size) {

    public PageDTO(Page<T> page) {
        this(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }
}
