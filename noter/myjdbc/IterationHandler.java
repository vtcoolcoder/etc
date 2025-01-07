package myjdbc;

import java.sql.SQLException; 

@FunctionalInterface
public interface IterationHandler<E> {
    void handle(E item) throws SQLException;
}