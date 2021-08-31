class VacToken {
  VacToken({
    this.accessToken,
    this.expiresIn,
    this.roleName,
    this.tokenType,
    this.userId,
  });

  String accessToken;
  int expiresIn;
  String roleName;
  String tokenType;
  int userId;

  factory VacToken.fromJson(Map<String, dynamic> json) => VacToken(
    accessToken: json["access_token"],
    expiresIn: json["expires_in"],
    roleName: json["role_name"],
    tokenType: json["token_type"],
    userId: json["user_id"],
  );
}
