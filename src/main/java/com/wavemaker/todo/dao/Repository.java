package com.wavemaker.todo.dao;

import java.util.List;

/**
 * Created by sainihala on 26/7/16.
 */
public interface Repository<T> {
    T insert(T object);

    void delete(int id);

    void update(T object);

    T retrive(int id);

    List<T> getAll();
}
