package ru.itis.services;

import java.util.LinkedList;
import java.util.List;

public class FormValidateService {

    public static List<String> checkForm(String nickname, String password, String password2){
        List<String> result = new LinkedList<>();

        if(!password.equals(password2)){
            result.add( "Пароли не совпадают!");
        }else if(password.length()<5){
            result.add("Пароль слишком короткий!");
        }else if(nickname.length()<5){
            result.add("Ник слишком короткий!");
        }else if(password.length()>29){
            result.add("Пароль слишком длинный!");
        }else if(nickname.length()>29){
            result.add("Ник слишком длинный!");
        }else if(!password.matches("^[A-Za-z0-9]*$")){
            result.add("В пароле не может быть спецсимволов, только латиница и цифры!");
        }else if(!nickname.matches("^[A-Za-z0-9]*$")){
            result.add("В нике не может быть спецсимволов, только латиница и цифры!");
        }

        return result;
    }

}
