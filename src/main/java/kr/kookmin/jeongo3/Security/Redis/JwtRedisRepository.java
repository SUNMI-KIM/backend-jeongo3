package kr.kookmin.jeongo3.Security.Redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRedisRepository extends CrudRepository<Jwt, String> {
    Optional<Jwt> findByLoginId(String loginId);
    void deleteByLoginId(String loginId);
}