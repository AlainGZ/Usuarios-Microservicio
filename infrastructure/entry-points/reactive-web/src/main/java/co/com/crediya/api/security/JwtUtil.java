package co.com.crediya.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXPIRATION_TIME = 1000 * 60 * 60;

	public static String generateToken(String correo, String rol, Long documentoIdentidad){
		return Jwts.builder()
				.setSubject(correo)
				.claim("rol", rol)
				.claim("documentoIdentidad", documentoIdentidad)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.signWith(key)
				.compact();
	}
	public static String getCorreoFromToken(String token){
		return Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	public static String getRolFromToken(String token){
		return (String) Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token)
				.getBody()
				.get("rol");
	}
	public static boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
