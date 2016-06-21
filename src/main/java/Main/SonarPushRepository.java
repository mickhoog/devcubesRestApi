package Main;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SonarPushRepository extends JpaRepository<SonarPush, Integer>{
    List<SonarPush> findTop3ByUser_idOrderByDateDesc(int id);
    List<SonarPush> findByUser_idOrderByDateDesc(int id);
}
