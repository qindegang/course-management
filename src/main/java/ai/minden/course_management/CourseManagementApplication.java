package ai.minden.course_management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Simple Course Management System",
                version = "0.0.1-SNAPSHOT",
                description = """
                        This is a simple course management system to facilitate the students to sign up for courses.
                        \nThe following students are prepared for illustration purpose:
                        	john@abc.edu, alice@abc.edu, cindy@abc.edu, bob@abc.edu
                        \nThe following courses are prepared as well:
                        	mathematics, physics"""
        )
)
public class CourseManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManagementApplication.class, args);
    }
}

