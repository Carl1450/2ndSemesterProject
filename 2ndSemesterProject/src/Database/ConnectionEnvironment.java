package Database;


public enum ConnectionEnvironment {
    PRODUCTION("DMA-CSD-V23_10478739"), // Scotts StudieNummer
    TESTING("DMA-CSD-V23_10478735"); // Rasmus' StudieNummer

    private final String databaseName;

    ConnectionEnvironment(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
}
