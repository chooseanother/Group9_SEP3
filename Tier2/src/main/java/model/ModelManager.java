package model;

import RabbitMQ.RabbitMQClient;
import RabbitMQ.RabbitMQClientController;
import RMI.ITier2RMIClient;
import RMI.Tier2RMIClient;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model{
    private ITier2RMIClient iTier2RMIClient;
    private RabbitMQClient rabbitMQClient;

    public ModelManager() throws RemoteException {
        iTier2RMIClient = new Tier2RMIClient();
        rabbitMQClient = new RabbitMQClientController(this);
        try{
            rabbitMQClient.initRPCQueue();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String registerUser(String username, String password, String email) {
        try {
            if (iTier2RMIClient.registerUser(username, password, email))
                return "Registered successfully";
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return "Failed registration";
    }

    @Override
    public String validateChallenge(Challenge challenge) {
        try {
            if (iTier2RMIClient.validateChallenge(challenge))
                return "Success";
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return "Failed registration";
    }

    @Override
    public ArrayList<Challenge> loadChallenges() {
        try {
            return iTier2RMIClient.loadChallenges();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Challenge> loadChallenges(String username) {
        try {
            return iTier2RMIClient.loadChallenges(username);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean acceptChallenge(Challenge challenge) {
        try{
//            return iTier2RMIClient.acceptChallenge(challenge);
            if (iTier2RMIClient.acceptChallenge(challenge)){
//                iTier2RMIClient.createMatch(challenge.getChallenger(), challenge.getChallenged(), challenge.getTurnTime());
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean rejectChallenge(Challenge challenge) {
        try{
            return iTier2RMIClient.rejectChallenge(challenge);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return false;
    }
}
