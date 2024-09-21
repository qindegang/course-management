package ai.minden.course_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String course;
}
