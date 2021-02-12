package thecasino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AccountInfo {

    private Map<String, UserID> userIDStore;
    private String dbFile;

    public AccountInfo(String dbFile) throws IOException {
        this.dbFile = dbFile;
        this.userIDStore = new HashMap<>();

        loadUsers();
    }

    public void loadUsers() throws FileNotFoundException, IOException {
        // Read the dbFile and create the UserID objects

        File myFile = new File(dbFile);
        if (!myFile.exists()) {
            myFile.createNewFile();
        }

        BufferedReader csvReader = new BufferedReader(new FileReader(dbFile));

        String row = csvReader.readLine();

        while (row != null) {
            // row:          hugo,password,5
            // row split :  ['hugo', 'password', '5']
            
            String[] data = row.split(",");
            String username = data[0];
            String password = data[1];
            String currency = data[2];
       
            UserID user = new UserID(username, password, Integer.parseInt(currency));
            userIDStore.put(username, user);

            row = csvReader.readLine();
        }

        csvReader.close();
    }

    public void storeUsers() throws IOException {
        FileWriter csvWriter = new FileWriter(dbFile);

        // hugo,password,5
        // tom,password,2
        for (Map.Entry<String, UserID> userEntry : userIDStore.entrySet()) {
            String username = userEntry.getKey();
            UserID userID = userEntry.getValue();
            csvWriter.append(username);
            csvWriter.append("," + userID.getPassword());
            csvWriter.append("," + userID.getCurrency());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    public UserID getUser(String username) {
        UserID user = userIDStore.get(username);
        return user;
    }

    public void updateUser(UserID newUserID) {
        userIDStore.put(newUserID.getUsername(), newUserID);
    }

    public void addUser(UserID newUserID) {
        // Construct a new UserID using the inputs
        // Add that new UserID to the map of user credentials

        userIDStore.put(newUserID.getUsername(), newUserID);
    }
}
