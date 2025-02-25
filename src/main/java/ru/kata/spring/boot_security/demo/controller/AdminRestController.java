package ru.kata.spring.boot_security.demo.controller;

//@RestController
//@RequestMapping("/api/admin/users")
public class AdminRestController {
//    private final UserService userService;
//    private final UserDtoMapper userDtoMapper;
//
//    public AdminRestController(UserService userService, UserValidator userValidator, UserDtoMapper userDtoMapper) {
//        this.userService = userService;
//        this.userDtoMapper = userDtoMapper;
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
//        if (!userService.delete(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> findUser(@PathVariable("id") Long id) {
//        return new ResponseEntity<>(userService.getById(id)
//                .map(userDtoMapper::map)
//                .orElseThrow(() -> new UserNotFoundException("User not found")), HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        return new ResponseEntity<>(userService.findAll().stream()
//                .map(userDtoMapper::map)
//                .collect(Collectors.toList()), HttpStatus.OK);
//
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<HttpStatus> editUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto user, BindingResult br) {
//        userValidator.validate(user, br);
//        if (br.hasErrors()) {
//            throw new UserValidationException("user editing failed", br);
//        }
//        userService.update(user, id);
//        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping
//    public ResponseEntity<HttpStatus> addNewUser(@Valid @RequestBody User user, BindingResult br) {
//        userValidator.validate(user, br);
//        if (br.hasErrors()) {
//            throw new UserValidationException("couldn't add user", br);
//        }
//        userService.save(user);
//        return ResponseEntity.ok(HttpStatus.CREATED);
//    }
//
//    @ExceptionHandler(UserValidationException.class)
//    public ResponseEntity<Map<String, String>> notValidExceptionHandler(UserValidationException err) {
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put("isValidation", "true");
//        err.getBindingResult()
//                .getFieldErrors()
//                .forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
//        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> userNotFoundExceptionHandler(UserNotFoundException err) {
//        return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
//    }
}
