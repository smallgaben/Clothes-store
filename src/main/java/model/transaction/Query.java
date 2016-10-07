package model.transaction;

import exception.dao.DAOException;

public interface Query<T> {
   T doQuery() throws DAOException;
}
