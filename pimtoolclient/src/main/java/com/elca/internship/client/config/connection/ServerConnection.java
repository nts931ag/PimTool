package com.elca.internship.client.config.connection;

import com.elca.internship.client.common.AlertDialog;
import javafx.scene.control.Alert;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Data
public class ServerConnection {
    private String status;
    private boolean isConnected;
    private int retryCount;

    public ServerConnection(){
        this.status = "Disconnected";
        this.isConnected = false;
        this.retryCount = 0;
    }

    public boolean isConnected(){
        return isConnected;
    }


    public boolean connect(){
        try {
            System.out.println("Connecting to server at...." + Rest.PORT);
            var uri = Rest.BASE_URI + "/connect";
            var restTemplate = new RestTemplate();
            var responseEntity = restTemplate.getForEntity(uri, String.class);

            // Find customer successful
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                status = responseEntity.getBody();
                this.isConnected = true;
                System.out.println("Connection status : " + status);
                return this.isConnected;
            }

        } catch (ResourceAccessException e) {
            // Connection not found
            this.status = "Connection refused !";
            this.isConnected = false;
            var errMsg = "Connection not found at: " + Rest.PORT;
            System.out.println(errMsg);
            return this.isConnected;
        }
        catch (Exception e){
            // Connection Error
            this.status = "Error Connecting....";
            this.isConnected = false;
            var errMsg = "Connection Error";
            System.out.println(errMsg);
            // Information dialog
            var alertDialog = new AlertDialog("Error",errMsg,e.getMessage(), Alert.AlertType.ERROR);
            alertDialog.showErrorDialog(e);
            return this.isConnected;
        }

        // Connection failed
        System.out.println("Failed to connect: ");
        return this.isConnected;
    }
}
