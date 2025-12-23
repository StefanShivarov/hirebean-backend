package bg.uni.sofia.fmi.spring.hirebean.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends JpaRepository<T, ID> {

  @Override
  @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
  List<T> findAll();

  @Override
  @Query("SELECT e FROM #{#entityName} e WHERE e.id = ?1 AND e.deletedAt IS NULL")
  Optional<T> findById(ID id);

  @Override
  @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deletedAt IS NULL")
  long count();

  @Override
  @Transactional
  @Modifying
  @Query("update #{#entityName} e set e.deletedAt = CURRENT_TIMESTAMP where e.id = ?1")
  void deleteById(ID id);

  @Override
  @Transactional
  @Modifying
  @Query("update #{#entityName} e set e.deletedAt = CURRENT_TIMESTAMP where e.id = :#{#entity.id}")
  void delete(T entity);

  @Transactional
  @Modifying
  @Query("delete from #{#entityName} e where e.id = ?1")
  void hardDeleteById(ID id);
}
