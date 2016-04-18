package Main;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInformationRepository extends JpaRepository<GameInformation, Integer> {
}