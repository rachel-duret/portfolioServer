package rd.portfolio.portfolioserver.service;

public interface AuthService {
    void  register();
    void login(String username, String password);
    void logout();

}
