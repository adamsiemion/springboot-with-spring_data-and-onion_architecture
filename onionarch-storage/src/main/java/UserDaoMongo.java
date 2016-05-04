import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Adam Siemion
 */
public interface UserDaoMongo extends MongoRepository<User, String> {
}
