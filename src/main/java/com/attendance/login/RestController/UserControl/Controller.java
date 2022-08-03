package com.attendance.login.RestController.UserControl;

import com.attendance.login.RestController.Genarator;
import com.attendance.login.RestController.model.Details;
import com.attendance.login.RestController.model.User1;
import com.attendance.login.RestController.repository.DetailRepository;
import com.attendance.login.RestController.repository.UserRepository1;
import com.attendance.login.RestController.services.DetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import javax.transaction.Transactional;


@Transactional
@CrossOrigin
@RestController
@RequestMapping("/api/rest")
public class Controller {

    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    public UserRepository1 userRepository1;

    @Autowired
    public Genarator genarator;

    public String i;
public int rsp;
   // public String verify = genarator.generateRandom(10);



    @Autowired
    public DetailsServices detailService;
    @GetMapping("/qr-generator")

            public String Test() {
        rsp=0;
        String verify = genarator.generateRandom(20);
        System.out.println(verify);
        i=verify;
        return verify;
    }

    @PostMapping("/save")
    public ResponseEntity AddUser(@RequestBody User1 user2) {

rsp=0;

//        LocalTime lc= LocalTime.parse(user2.time);

        if (i.equals(user2.para)) {
            System.out.println(user2);

            if (userRepository1.existsByPara(user2.para)) {


                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            } else if (user2.time.contains("AM")) {
                System.out.println(user2.time.contains("AM"));
                user2.forenoon = user2.time;
                userRepository1.save(user2);
                rsp=100;
                return new ResponseEntity<>(HttpStatus.OK);

            } else if (user2.time.contains("PM")) {
                LocalDate date = LocalDate.parse(String.valueOf(LocalDate.now()));

                if (userRepository1.existsByDateAndEmail(date, user2.getEmail())) {
                    User1 user3;
                    user3 = userRepository1.getByDateAndEmail(date, user2.getEmail());
                    System.out.println(user3);
                    int id = user3.getId();
                    user2.setId(id);
                    user2.forenoon = user3.forenoon;
                    user2.afternoon = user2.time;
                    userRepository1.save(user2);

                    rsp=100;
                    System.out.println(rsp);
                  return new ResponseEntity<>(HttpStatus.OK);


                } else {
                    user2.afternoon =user2.time;
                    userRepository1.save(user2);
                    rsp=100;
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }

        }
        return null;
    }


    @PostMapping("/response")
    public int respond() {
        return rsp;
    }


    @GetMapping("/get-user-details")
    public Iterable<User1> take( @RequestParam String email) {
        return userRepository1.findByEmail(email);
    }

    @GetMapping("/get-by-date")
    public Iterable<User1> findByDate(@RequestParam String date) {
        return userRepository1.getByDate(LocalDate.parse(date));
    }

    @PutMapping("/update-profile")
    public Details profile(@RequestBody Details details) {
        return detailRepository.save(details);
    }

    @GetMapping("/get-profile")
    public Details getProfileByEmail(@RequestParam String email) {
        return detailService.Find(email);
    }

    @GetMapping("/recent")
    public Iterable<User1> find() {
        return userRepository1.getByDate(LocalDate.now());
    }

    @GetMapping("/get-by-month")
    public Iterable<User1> findByMonth(@RequestParam String month,String email) {
        return userRepository1.getByMonthAndEmail(month,email);
    }


   
    @GetMapping("/delete-user")
    public Iterable<User1> deleteuser( @RequestParam String name) {
        return userRepository1.deleteByName(name);
    }


//       public ResponseEntity<?>waiter()
//       {
//           return ResponseEntity.status(HttpStatus.OK).location(URI.create("http://192.168.1.14:8080/")).build();
//
//       }

}
