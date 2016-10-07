package exception.dao;

public class DAOException extends Exception {
    public DAOException(String s) {
        super(s);
    }

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(DAOException daoEx, String s) {
        super(s, daoEx);
    }
}
