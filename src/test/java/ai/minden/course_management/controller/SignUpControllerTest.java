package ai.minden.course_management.controller;

import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.facade.SignUpFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {
    private static final String CINDY = "cindy@a.com";
    private static final String ENGLISH = "English";

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
}
