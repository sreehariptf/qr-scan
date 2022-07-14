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

   // public String verify = genarator.generateRandom(10);



    @Autowired
    public DetailsServices detailService;
   
    @CrossOrigin
    @PostMapping("/qr-generator")
            public String Test() {
        String verify = genarator.generateRandom(20);
        System.out.println(verify);
        i=verify;
        return verify;
    }

     @PostMapping("/save")
    public ResponseEntity AddUser( @RequestBody User1 user1) {

        System.out.println(i);
        if (i.equals(user1.para)) {

            if(userRepository1.existsByPara(user1.para))
            {
                
                
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);}
            else {
                userRepository1.save(user1);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//     @PostMapping("/save")
//     public ResponseEntity AddUser( @RequestBody User1 user1) {

//         System.out.println(i);
//         if (i.equals(user1.para)) {
//             userRepository1.save(user1);
//             return new ResponseEntity<>(HttpStatus.OK);
//         } else {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }
//     }

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
    
    @PostMapping("/recent1")
    public Iterable<User1> find1() {
        return userRepository1.getByDate(LocalDate.now());
    }
    
   @GetMapping("/delete-by-time")
public Iterable<User1> delete( @RequestParam String date) {
return userRepository1.deleteByDate(date);
}
    
//      @GetMapping("/get-by-month")
//     public Iterable<User1> findByMonth(@RequestBody String month,String email) {
//         return userRepository1.getByMonthAndEmail(month,email);
//     }

}
