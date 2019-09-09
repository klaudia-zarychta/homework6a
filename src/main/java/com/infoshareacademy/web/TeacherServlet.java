package com.infoshareacademy.web;

import com.infoshareacademy.dao.CourseDao;
import com.infoshareacademy.dao.TeacherDao;
import com.infoshareacademy.model.Course;
import com.infoshareacademy.model.CourseSummary;
import com.infoshareacademy.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/teacher")
@Transactional
public class TeacherServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(com.infoshareacademy.web.TeacherServlet.class);

    @Inject
    private TeacherDao teacherDao;

    @Inject
    private CourseDao courseDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Test data

        // Courses
        Course course1 = new Course("JJDD7");
        courseDao.save(course1);

        Course course2 = new Course("JJDZ6");
        courseDao.save(course2);

        Course course3 = new Course("JJFD9");
        courseDao.save(course3);

        Teacher t1 = new Teacher("12345678911", "Jan",
                "Kowalski",
                Arrays.asList(course1));
        teacherDao.save(t1);

        Teacher t2 = new Teacher("11111111111", "Ola",
                "Nowak",
                Arrays.asList(course1, course2, course3));
        teacherDao.save(t2);

        Teacher t3 = new Teacher("22222222222", "Filip",
                "Ptak",
                Arrays.asList(course1, course2, course3));
        teacherDao.save(t3);

        course1.setTeachers(Arrays.asList(t1));
        course2.setTeachers(Arrays.asList(t2));
        course3.setTeachers(Arrays.asList(t3));


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final String action = req.getParameter("action");
        LOG.info("Requested action: {}", action);
        if (action == null || action.isEmpty()) {
            resp.getWriter().write("Empty action parameter.");
            return;
        }

        if (action.equals("findAll")) {
            findAll(req, resp);
        } else if (action.equals("add")) {
            addTeacher(req, resp);
        } else if (action.equals("delete")) {
            deleteTeacher(req, resp);
        } else if (action.equals("update")) {
            updateTeacher(req, resp);
        } else if (action.equals("summary")) {
            summary(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void updateTeacher(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final String pesel = req.getParameter("pesel");
        LOG.info("Updating Teacher with pesel = {}", pesel);

        final Teacher existingTeacher = teacherDao.findByPesel(pesel);
        if (existingTeacher == null) {
            LOG.info("No Teacher found for pesel= {}, nothing to be updated", pesel);
        } else {
            existingTeacher.setName(req.getParameter("name"));
            existingTeacher.setSurname(req.getParameter("surname"));

            String courseName = req.getParameter("courseName");
            Course course = courseDao.findByName(courseName);
            existingTeacher.getCourses().add(course);

            teacherDao.update(existingTeacher);
            LOG.info("Teacher object updated: {}", existingTeacher);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addTeacher(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Teacher t = new Teacher();
        t.setPesel(req.getParameter("pesel"));
        t.setName(req.getParameter("name"));
        t.setSurname(req.getParameter("surname"));
        String courseName = req.getParameter("courseName");
        Course course = courseDao.findByName(courseName);
        t.setCourses(Arrays.asList(course));

        teacherDao.save(t);
        LOG.info("Saved a new Teachr object: {}", t);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String pesel = req.getParameter("pesel");
        LOG.info("Removing Teacher with pesel = {}", pesel);

        teacherDao.delete(pesel);

        // Return all persisted objects
        findAll(req, resp);
    }


    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Teacher> result = teacherDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Teacher t : result) {
            resp.getWriter().write(t.toString() + "\n");
        }
    }

    private void summary(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<CourseSummary> result = courseDao.getCoursesSummary();
        LOG.info("Found {} objects", result.size());
        for (CourseSummary p : result) {
            resp.getWriter().write(p.toString() + "\n");
        }
    }
}


