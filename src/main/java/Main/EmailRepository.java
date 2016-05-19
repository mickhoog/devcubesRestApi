package Main;

/**
 * Created by Mick on 19-5-2016.
 */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Integer> {
    public List<Email> findByUserId(int id);
}
