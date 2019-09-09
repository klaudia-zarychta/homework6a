package com.infoshareacademy.dao;

import com.infoshareacademy.model.Student;

import java.util.List;

public class TestGenericDao extends GenericDao<Student,Long> {
    public TestGenericDao(Class<Student> type) {
        super(type);
    }

    @Override
    public void save(Student entity) {
        super.save(entity);
    }

    @Override
    public Student update(Student entity) {
        return super.update(entity);
    }

    @Override
    public Student findById(Long val) {
        return super.findById(val);
    }

    @Override
    public void delete(Long val) {
        super.delete(val);
    }

    @Override
    public List<Student> findAll() {
        return super.findAll();
    }
}
