package com.auth.util;

import com.auth.model.User;
import com.auth.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

// klase skirta patikrinti ar useris praeina validacijas arba meta klaida
// anotacijose toki utils irankiai yra aprasomi kaip kompotentai
@Component
public class UserValidator implements Validator{

    @Autowired
    UserRoleService userRoleService;

    // kokia klase palaikom
    @Override
    public boolean supports(Class<?> aClass) {
        // musu pasirinkta klase palaiko aClass (Validacijos klase)
        // regis cia padarom, kad User klase pagrazintu true (jei retunr paliksime tik "true", tai visas klases priims, bet cia jau rizikuosime saugumu)
        return User.class.equals(aClass);
    }

    // tikriname User, jei neatitinka taisykliu, tai turesim error. Tuomet per error pakisim raktazodi (pranesima vartotojui)
    // Object reikia gauti useri (reikes castint)
    @Override
    public void validate(Object ouser, Errors errors) {
        User user = (User) ouser;

        // SU USERNAME PARASOM:
        // pastoviai tikrina musu laukus Jsp ir ziuri ar visi atitinka. Jei nors vienam Jsp lauke "username" bus tuscias
        // , is karto mes klaida, kurios errorCode yra "EmptySpace" musu apsirasytas
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "EmptySpace");
        // Jeigu jau yra toks vartotojas ir reikia pranesti, jog toks egzistuoja
        // jei egzistuoja, tai negali registruoti, nes toks jau yra (jei nors viena irasa gaunam, tai jis jau nebe null, todel netenkins)
        if(userRoleService.findByUsername(user.getUsername())!=null){
            // kuriam laukui irasom, tai prie "username", o "Warning.dublicate.username" yra errorCode
         errors.rejectValue("username", "Warning.dublicate.username");
        }
        // dabar patikrinam ilgi, nes buna, kad limitas 32 simboliai, o mes irasom ilgesni, tai serveris nukerpa ir palieka trumpesni
        // problema iskils, kai noresime logintis, o serveryje bus trumpesnis user ir nebus autentikacijos
        if(user.getUsername().length() <= 6 || user.getUsername().length() >= 32){
            errors.rejectValue("username", "Size.username");
        }

        // SU PASSWORD PARASOM:
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "EmptySpace");
        if(user.getPassword().length() <= 6 || user.getPassword().length() >= 32){
            errors.rejectValue("password", "Size.password");
        }
        // jei password ir confirm password keiciasi
        if(!user.getPasswordconfirm().equals(user.getPassword())){
            errors.rejectValue("passwordconfirm", "Diff.passwordConfirm");
        }
    }
}
