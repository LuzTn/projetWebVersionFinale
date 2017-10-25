package BlogSport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;


@Controller    // This means that this class is a Controller
@RequestMapping(path = "/blog") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private PostsRepository postsRepository;


    /* POST the different components of post : title , content (image: later) */
    @GetMapping(path = "/posts") // Map ONLY GET Requests
    public @ResponseBody
    String addNewPost(@RequestParam String post, String title) {
        // @ResponseBody means the returned String is the response, not a view password
        // @RequestParam means it is a parameter from the GET or POST request
        Posts n = new Posts();
        n.setTitle(title);
        n.setContent(post);
        postsRepository.save(n);
        return "Saved";
    }

    /* GET the different components of a post. the latter are returned as a json file */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Posts> getAllUsers() {
        // This returns a JSON or XML with the users
        return postsRepository.findAll();
    }

    /* DELETE */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Posts user = new Posts(id);
            postsRepository.delete(user);
        } catch (Exception ex) {
            return "Error deleting the user:" + ex.toString();
        }
        return "User succesfully deleted!";
    }

    /* PUT (modify) */
    @RequestMapping("/put")
    @ResponseBody
    public String put(long id, String content) {
        try {
            Posts post = postsRepository.findOne(id);
            post.setContent(content);
            postsRepository.save(post);
        } catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User succesfully updated!";
    }
}
