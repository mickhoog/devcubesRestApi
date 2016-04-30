package Main;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SonarResultRepository extends JpaRepository<SonarResult, String> {
	Issue findIssueById(String id);
}
