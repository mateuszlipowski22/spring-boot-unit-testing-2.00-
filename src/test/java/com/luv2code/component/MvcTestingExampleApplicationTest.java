package com.luv2code.component;

import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
@SpringBootTest(classes = MvcTestingExampleApplication.class)
class MvcTestingExampleApplicationTest {

    private static int count = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach() {
        count++;
        System.out.printf("Testing %s which is %s Version : %s. Execution of test method %d%n", appInfo, appDescription, appVersion, count);
        collegeStudent.setFirstname("Eric");
        collegeStudent.setLastname("Foreman");
        collegeStudent.setEmailAddress("ericforman@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.5, 91.75)));
        collegeStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("Add grade results for student grades")
    @Test
    void addGradeResultsForStudentGrades() {
        assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Add grade results for student grades not equal")
    @Test
    void addGradeResultsForStudentGradesAssertNotEqual() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Is grade greater")
    @Test
    void isGradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(90, 75), "failure - should be true");
    }

    @DisplayName("Is grade greater false")
    @Test
    void isGradeGreaterStudentGradesAssertFalse() {
        assertFalse(studentGrades.isGradeGreater(60, 75), "failure - should be false");
    }

    @DisplayName("Check null for student grades")
    @Test
    void checkNullForStudentGrades() {
        assertNotNull(studentGrades.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Create student without grade init")
    @Test
    void createStudentWithoutGradesInit() {
        CollegeStudent collegeStudentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        collegeStudentTwo.setFirstname("Red");
        collegeStudentTwo.setLastname("Foreman");
        collegeStudentTwo.setEmailAddress("redforman@gmail.com");
        assertNotNull(collegeStudentTwo.getFirstname());
        assertNotNull(collegeStudentTwo.getLastname());
        assertNotNull(collegeStudentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(collegeStudentTwo.getStudentGrades()));
    }

    @DisplayName("Verify students are prototype")
    @Test
    void verifyStudentsArePrototype() {
        CollegeStudent collegeStudentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(collegeStudentTwo, collegeStudent);
    }

    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage() {
        assertAll(() -> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                        collegeStudent.getStudentGrades().getMathGradeResults())),
                () -> assertEquals(88.31, studentGrades.findGradePointAverage(
                        collegeStudent.getStudentGrades().getMathGradeResults())));
    }
}