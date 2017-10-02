package com.auth.service;

public interface SecurityService {
    // cia turim gauti vartotoja, kuris jau yra prisijunges
    String findLogginUsername();
    // prisijungiame
    void login(String username, String passw);
}
