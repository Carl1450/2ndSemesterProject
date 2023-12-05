package Database;


public enum ConnectionEnvironment {
    PRODUCTION("DMA-CSD-V23_10478739", "DMA-CSD-V23_10478739"), // Scotts StudieNummer
    TESTING("DMA-CSD-V23_10478735", "DMA-CSD-V23_10478735"); // Rasmus' StudieNummer

    private final String databaseName;
    private final String userName;

    ConnectionEnvironment(String databaseName, String userName) {
        this.databaseName = databaseName;
        this.userName = userName;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
    
    public String getUserName() {
    	return this.userName;
    }
}
