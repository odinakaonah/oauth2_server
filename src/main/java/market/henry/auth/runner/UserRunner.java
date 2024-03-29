package market.henry.auth.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.henry.auth.enums.UserType;
import market.henry.auth.exceptions.AuthServerException;
import market.henry.auth.model.Role;
import market.henry.auth.model.User;
import market.henry.auth.repo.RoleRepo;
import market.henry.auth.repo.UserRepo;
import market.henry.auth.utils.CommonUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserRunner implements ApplicationRunner {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) {

        if(userRepo.getCount() == 0){

            Set<Role> roleSet = roleRepo.getAllBy(UserType.ADMIN.name());
//            System.out.println("Admin role "+roleSet);
            if(roleSet.isEmpty()){
                System.out.println("No role found for admin");
                createUser();
                return;
            }

            User admin  = new User();
            admin.setRoles(roleSet);
            admin.setEmail("deleogold@gmail.com");
            admin.setFirstName("Odinaka");
            admin.setAccountNumber("6756483746");
            admin.setAccountBalance(new BigDecimal("5000394"));
            admin.setLastName("Admin");
            admin.setBvn("76584759384");
            admin.setGender("M");
            admin.setPin(1234);
            admin.setToken(123456);
            try {
                admin.setDob(CommonUtils.dateFormat("1976-03-20"));
            } catch (AuthServerException e) {
                log.error("ERROR::: ",e);
            }
            userRepo.save(admin);
            createUser();
        }
    }

    public void createUser() {

        if(userRepo.count() < 2){

            Set<Role> roleSet = roleRepo.getAllBy(UserType.USER.name());

            if(roleSet.isEmpty()){
                System.out.println("No role found for User");
                return;
            }
            try {
                List<User> users = new ArrayList<>();
                users.add(User.builder()
                        .bvn("02342349853")
                        .accountNumber("2039485768")
                        .accountBalance(new BigDecimal("1000394"))
                        .email("deleogold@mailinator.com")
                        .firstName("Odinaka")
                        .gender("M")
                        .lastName("Onah")
                        .phoneNumber("08062864121")
                        .roles(roleSet)
                        .title("Ms")
                        .dob(CommonUtils.dateFormat("1990-02-21"))
                        .pin(4444)
                        .token(232323)
                        .build());

                users.add(User.builder()
                        .bvn("90987564323")
                        .accountNumber("3094857683")
                        .accountBalance(new BigDecimal("5000"))
                        .email("gift@mailinator.com")
                        .firstName("Gift")
                        .gender("F")
                        .lastName("Abobo")
                        .phoneNumber("07069659979")
                        .roles(roleSet)
                        .title("Ms")
                        .pin(8888)
                        .token(898989)
                        .dob(CommonUtils.dateFormat("1992-05-25"))
                        .build());

                users.add(User.builder()
                        .bvn("78576843212")
                        .accountNumber("0029384756")
                        .accountBalance(new BigDecimal("4092834"))
                        .email("ken@mailinator.com")
                        .firstName("Ken")
                        .gender("M")
                        .lastName("Tony")
                        .phoneNumber("08062864123")
                        .roles(roleSet)
                        .title("Ms")
                        .pin(3434)
                        .token(343434)
                        .dob(CommonUtils.dateFormat("1998-04-01"))
                        .build());

                users.forEach(user -> userRepo.save(user));
            }catch (AuthServerException ax){
                log.error("ERROR ;:::",ax);
            }
        }
    }
}
