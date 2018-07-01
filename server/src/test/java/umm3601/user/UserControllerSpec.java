package umm3601.user;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * JUnit tests for the UserController.
 *
 * Created by mcphee on 22/2/17.
 */
public class UserControllerSpec
{
    private UserController userController;
    private ObjectId samsId;
    @Before
    public void clearAndPopulateDB() throws IOException {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> userDocuments = db.getCollection("users");
        userDocuments.drop();
        List<Document> testUsers = new ArrayList<>();
        testUsers.add(Document.parse("{\n" +
                "                    name: \"Chris\",\n" +
                "                    age: 25,\n" +
                "                    company: \"UMM\",\n" +
                "                    email: \"chris@this.that\"\n" +
                "                }"));
        testUsers.add(Document.parse("{\n" +
                "                    name: \"Pat\",\n" +
                "                    age: 37,\n" +
                "                    company: \"IBM\",\n" +
                "                    email: \"pat@something.com\"\n" +
                "                }"));
        testUsers.add(Document.parse("{\n" +
                "                    name: \"Jamie\",\n" +
                "                    age: 37,\n" +
                "                    company: \"Frogs, Inc.\",\n" +
                "                    email: \"jamie@frogs.com\"\n" +
                "                }"));

        samsId = new ObjectId();
        BasicDBObject sam = new BasicDBObject("_id", samsId);
        sam = sam.append("name", "Sam")
                .append("age", 45)
                .append("company", "Frogs, Inc.")
                .append("email", "sam@frogs.com");



        userDocuments.insertMany(testUsers);
        userDocuments.insertOne(Document.parse(sam.toJson()));

        // It might be important to construct this _after_ the DB is set up
        // in case there are bits in the constructor that care about the state
        // of the database.
        userController = new UserController(db);
    }

    // http://stackoverflow.com/questions/34436952/json-parse-equivalent-in-mongo-driver-3-x-for-java
    private BsonArray parseJsonArray(String json) {
        final CodecRegistry codecRegistry
                = CodecRegistries.fromProviders(Arrays.asList(
                new ValueCodecProvider(),
                new BsonValueCodecProvider(),
                new DocumentCodecProvider()));

        JsonReader reader = new JsonReader(json);
        BsonArrayCodec arrayReader = new BsonArrayCodec(codecRegistry);

        return arrayReader.decode(reader, DecoderContext.builder().build());
    }

    private static String getName(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("name")).getValue();
    }

    @Test
    public void getAllUsers() {
        Map<String, String[]> emptyMap = new HashMap<>();
        String jsonResult = userController.getUsers(emptyMap);
        BsonArray docs = parseJsonArray(jsonResult);

        assertEquals("Should be 4 users", 4, docs.size());
        List<String> names = docs
                .stream()
                .map(UserControllerSpec::getName)
                .sorted()
                .collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("Chris", "Jamie", "Pat", "Sam");
        assertEquals("Names should match", expectedNames, names);
    }

    @Test
    public void getUsersWhoAre37() {
        Map<String, String[]> argMap = new HashMap<>();
        argMap.put("age", new String[] { "37" });
        String jsonResult = userController.getUsers(argMap);
        BsonArray docs = parseJsonArray(jsonResult);

        assertEquals("Should be 2 users", 2, docs.size());
        List<String> names = docs
                .stream()
                .map(UserControllerSpec::getName)
                .sorted()
                .collect(Collectors.toList());
        List<String> expectedNames = Arrays.asList("Jamie", "Pat");
        assertEquals("Names should match", expectedNames, names);
    }

    @Test
    public void getSamById() {
        String jsonResult = userController.getUser(samsId.toHexString());
        Document sam = Document.parse(jsonResult);
        assertEquals("Name should match", "Sam", sam.get("name"));
        String noJsonResult = userController.getUser(new ObjectId().toString());
        assertNull("No name should match",noJsonResult);

    }

    @Test
    public void addUserTest(){
        String newId = userController.addNewUser("Brian",22,"umm", "brian@yahoo.com");

        assertNotNull("Add new user should return true when user is added,", newId);
        Map<String, String[]> argMap = new HashMap<>();
        argMap.put("age", new String[] { "22" });
        String jsonResult = userController.getUsers(argMap);
        BsonArray docs = parseJsonArray(jsonResult);

        List<String> name = docs
            .stream()
            .map(UserControllerSpec::getName)
            .sorted()
            .collect(Collectors.toList());
        assertEquals("Should return name of new user", "Brian", name.get(0));
    }

    @Test
    public void getUserByCompany(){
        Map<String, String[]> argMap = new HashMap<>();
        //Mongo in UserController is doing a regex search so can just take a Java Reg. Expression
        //This will search the company starting with an I or an F
        argMap.put("company", new String[] { "[I,F]" });
        String jsonResult = userController.getUsers(argMap);
        BsonArray docs = parseJsonArray(jsonResult);
        assertEquals("Should be 3 users", 3, docs.size());
        List<String> name = docs
            .stream()
            .map(UserControllerSpec::getName)
            .sorted()
            .collect(Collectors.toList());
        List<String> expectedName = Arrays.asList("Jamie","Pat","Sam");
        assertEquals("Names should match", expectedName, name);

    }



}
