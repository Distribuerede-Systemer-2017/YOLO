package server.models;

import java.util.Date;

public class Token {
    private String tokenString;
    private Date tokenDate;
    private int tokenId;
    private int userId;


    public Token (){

    }


    public void setTokenString(String tokenString){
        this.tokenString = tokenString;
    }

    public void setTokenDate(Date tokenDate){
        this.tokenDate = tokenDate;
    }

    public void setTokenId(int tokenId){
        this.tokenId = tokenId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getTokenString(){
        return tokenString;
    }

    public Date getTokenDate(){
        return tokenDate;
    }

    public int getTokenId(){
        return tokenId;
    }

    public int getUserId(){
        return userId;
    }
}
