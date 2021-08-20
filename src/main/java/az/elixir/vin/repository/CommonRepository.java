package az.elixir.vin.repository;

import az.elixir.vin.entity.CommonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository extends CrudRepository<CommonEntity,Integer> {
}
