import 'package:vaccom/src/api/fire_base_auth.dart';


class FirAuth {
  void signUp(String email, String pass, String name, String phone,
      Function onSuccess, Function(String) onRegisterError) {
        print("user: " + email);
      }

  void signIn(String email, String pass, Function onSuccess,
      Function(String) onSignInError) {
        print("user: " + email);
      }


}