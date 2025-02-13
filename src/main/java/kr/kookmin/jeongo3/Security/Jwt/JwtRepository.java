package kr.kookmin.jeongo3.Security.Jwt;

import kr.kookmin.jeongo3.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, String> {
    public Optional<Jwt> findTopByUser(User user);
    public void deleteByUser(User user);
    public boolean existsByUser(User user);

}
