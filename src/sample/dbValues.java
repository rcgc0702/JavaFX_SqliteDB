package sample;

public enum dbValues {
    ID("ID"),
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    AGE("age"),
    POSITION("position"),
    SUPERVISOR("supervisor"),
    WORKADDRESS("workaddress");

    private String dbColumn;

    dbValues(String dbColumn) {
        this.dbColumn = dbColumn;
    }

    @Override
    public String toString() {
        return dbColumn;
    }
}
