package az.elixir.vin.repository;

import az.elixir.vin.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<UserEntity,Integer> {

    UserEntity findByEmailAndPassword(String email, String password);
}
