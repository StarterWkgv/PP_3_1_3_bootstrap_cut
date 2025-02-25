package ru.kata.spring.boot_security.demo.util;

public class UserValidator{

}
//@Component
//public class UserValidator implements Validator {
//    private final UserService userService;
//
//    public UserValidator(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return UserDto.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        UserDto userDto = (UserDto) target;
//        Optional<User> o = userService.getUserByEmail(userDto.getEmail());
//        if (o.isPresent() && (userDto.getId() != o.get().getId() || userDto.getId() == 0)) {
//            errors.rejectValue("email", "", "The user with this email already exists");
//        }
//        if (userDto.getId() == 0 && userDto.getPassword().isBlank()) {
//            errors.rejectValue("password", "", "The password should not be empty");
//        }
//        if (userDto.getAge() < 0 || userDto.getAge() > 127) {
//            errors.rejectValue("age", "", "The age should be between 0 and 127");
//        }
//
//    }
//}
