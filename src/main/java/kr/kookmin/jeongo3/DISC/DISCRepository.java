package kr.kookmin.jeongo3.DISC;

import kr.kookmin.jeongo3.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DISCRepository extends JpaRepository<DISC, String> {

    public Optional<DISC> findByUser(User user);

    public long countByDiscCode(DISCCode discCode);

    public void deleteByUser(User user);

}
