package server;

public class Account {
    //represent a bank account with a balance and withdraw and deposit methods
    private int id;
    private int balance;
    
    /* Constructors */
    public Account(int id,int balance){

        this.setId(id);
        this.setBalance(balance);
    }

    public Account(int balance){
        this.setBalance(balance);
    }

    /* Set & Get */
    public void setBalance(int balance){
        this.balance = balance;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public int getBalance(){
        return this.balance;
    }

    public int getId(){
        return this.id;
    }


    public void withdraw(int amount){
        this.setBalance(this.getBalance() - amount);
    }

    public void deposit(int amount){
        this.setBalance(this.getBalance() + amount);
    }
}
