package Main;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameInformationRepository extends JpaRepository<GameInformation, Integer> {

    List<GameInformation> findByMoneyGreaterThan(int money);
}