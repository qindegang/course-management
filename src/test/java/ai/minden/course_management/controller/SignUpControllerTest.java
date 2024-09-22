package ai.minden.course_management.controller;

import ai.minden.course_management.dto.CourseDTO;
import ai.minden.course_management.dto.PageDTO;
import ai.minden.course_management.dto.StudentDTO;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.facade.SignUpFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {
    private static final String CINDY = "cindy@a.com";

    private static final String ALICE = "alice@a.com";
    private static final StudentDTO STUDENT_ALICE = new StudentDTO(ALICE);

    private static final String BOB = "bob@a.com";
    private static final StudentDTO STUDENT_BOB = new StudentDTO(BOB);

    private static final String ENGLISH = "English";
    private static final CourseDTO COURSE_ENGLISH = new CourseDTO(ENGLISH);

    private static final String PHYSICS = "English";
    private static final CourseDTO COURSE_PHYSICS = new CourseDTO(PHYSICS);

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignUpFacade signUpFacade;

    @Test
    public void signUp_success() throws Exception {
        var requestBody = String.format("{\"email\": \"%s\", \"course\": \"%s\"}", CINDY, ENGLISH);

        doNothing().when(signUpFacade).signUp(CINDY, ENGLISH);
        mvc.perform(MockMvcRequestBuilders
                        .post("/signups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void signUp_invalidEmail() throws Exception {
        var requestBody = String.format("{\"email\": \"%s\", \"course\": \"%s\"}", CINDY, ENGLISH);
        doThrow(new InvalidStudentEmailException("dummy")).when(signUpFacade).signUp(CINDY, ENGLISH);
        mvc.perform(MockMvcRequestBuilders
                        .post("/signups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUp_invalidCourse() throws Exception {
        var requestBody = String.format("{\"email\": \"%s\", \"course\": \"%s\"}", CINDY, ENGLISH);
        doThrow(new InvalidCourseNameException("dummy")).when(signUpFacade).signUp(CINDY, ENGLISH);
        mvc.perform(MockMvcRequestBuilders
                        .post("/signups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cancelSignUp_success() throws Exception {
        var requestBody = String.format("{\"email\": \"%s\", \"course\": \"%s\"}", CINDY, ENGLISH);

        doNothing().when(signUpFacade).cancelSignUp(CINDY, ENGLISH);
        mvc.perform(MockMvcRequestBuilders
                        .delete("/signups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());
    }

    @Test
    public void cancelSignUp_invalidEmail() throws Exception {
        var requestBody = String.format("{\"email\": \"%s\", \"course\": \"%s\"}", CINDY, ENGLISH);
        doThrow(new InvalidStudentEmailException("dummy")).when(signUpFacade).cancelSignUp(CINDY, ENGLISH);
        mvc.perform(MockMvcRequestBuilders
                        .delete("/signups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cancelSignUp_invalidCourse() throws Exception {
        var requestBody = String.format("{\"email\": \"%s\", \"course\": \"%s\"}", CINDY, ENGLISH);
        doThrow(new InvalidCourseNameException("dummy")).when(signUpFacade).cancelSignUp(CINDY, ENGLISH);
        mvc.perform(MockMvcRequestBuilders
                        .delete("/signups")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findCourses_success() throws Exception {
        when(signUpFacade.findCourses(CINDY)).thenReturn(List.of(COURSE_ENGLISH, COURSE_PHYSICS));
        mvc.perform(MockMvcRequestBuilders
                        .get(String.format("/signups/courses?email=%s", CINDY)))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("[{'name':'%s'},{'name':'%s'}]", ENGLISH, PHYSICS)));
    }

    @Test
    public void findCourses_invalidEmail() throws Exception {
        when(signUpFacade.findCourses(CINDY)).thenThrow(new InvalidStudentEmailException("dummy"));
        mvc.perform(MockMvcRequestBuilders
                        .get(String.format("/signups/courses?email=%s", CINDY)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findClassMates_success() throws Exception {
        when(signUpFacade.findClassMates(CINDY, ENGLISH, PAGEABLE)).thenReturn(new PageDTO<>(List.of(STUDENT_ALICE, STUDENT_BOB), 2, 1, 0, 10));

        var response = String.format("""
                {
                  "content": [{"email":"%s"}, {"email":"%s"}],
                  "totalElements": 2,
                  "totalPages": 1,
                  "number": 0,
                  "size": 10
                }""", ALICE, BOB);

        mvc.perform(MockMvcRequestBuilders
                        .get(String.format("/signups/classmates?email=%s&course=%s", CINDY, ENGLISH)))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void findClassMates_invalidEmail() throws Exception {
        when(signUpFacade.findClassMates(CINDY, ENGLISH, PAGEABLE)).thenThrow(new InvalidStudentEmailException("dummy"));
        mvc.perform(MockMvcRequestBuilders
                        .get(String.format("/signups/classmates?email=%s&course=%s", CINDY, ENGLISH)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findClassMates_invalidCourse() throws Exception {
        when(signUpFacade.findClassMates(CINDY, ENGLISH, PAGEABLE)).thenThrow(new InvalidCourseNameException("dummy"));
        mvc.perform(MockMvcRequestBuilders
                        .get(String.format("/signups/classmates?email=%s&course=%s", CINDY, ENGLISH)))
                .andExpect(status().isBadRequest());
    }
}
