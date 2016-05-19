package Main;

/**
 * Created by Mick on 19-5-2016.
 */

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Integer> {

}
