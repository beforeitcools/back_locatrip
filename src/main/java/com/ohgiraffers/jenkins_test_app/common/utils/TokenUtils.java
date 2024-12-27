package com.ohgiraffers.jenkins_test_app.common.utils;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private static String jwtSecretKey;
    private static long tokenValidateTime;

    @Value("${jwt.key}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }
    @Value("${jwt.time}")
    public void setTokenValidateTime(long tokenValidateTime) {
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    /**
     * header의 token 을 분리하는 메소드
     * @Param header: Authrization의 header값을 가져온다.
     * @return token : Authrization의 token을 반환한다.
     * */
    public static String splitHeader(String header){
        if(!header.equals("")){
            return header.split(" ")[1];  //BEARER를 제외한 토큰 값만 반환 해주는 메소드
        }else {
            return null;
        }

    }

    /**
     * 유효한 토큰인지 확인하는 메서드
     * @Param token : 토큰
     * @return boolean : 유효 여부
     * @throw ExpiredJwtException, {@link io.jsonwebtoken.JwtException} {@link NullPointerException}
     * */
    public static boolean isValidToken(String token){

/*        Claims claims = getClaimsFromToken(token); //payload 부분 - 실제 데이터를 넣는 부분 -> 복호화 시키기 위해 가져옴\
                        // 자체적으로 유효 검증 -> 토큰이 유효하지 않으면 복호화가 되지 않음*/

        try{
            Claims claims = getClaimsFromToken(token);
            return true;
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 토큰을 복호화 하는 메소드
     * @param token
     * @return Claims
     * */
    public static Claims getClaimsFromToken(String token){
//  Jwts.parser()와 parseClaimsJws(token)는 JWT의 유효성을 검증합니다. 만약 JWT가 유효하지 않으면 예외가 발생합니다.
        // JWT 파서를 사용하여 토큰을 파싱합니다. // 서명 검증을 위한 비밀 키 설정
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                // 토큰을 파싱하고 JWS(서명된 JWT)를 처리합니다.
                .parseClaimsJws(token).getBody(); // 토큰의 페이로드(Claims)를 반환합니다.
    }

    /**
     * token 을 생성하는 메소드
     * @param user userEntity
     * @return String token
     * */
    public static String generateJwtToken(Users user){
        // 토큰 만료 시간을 현재 시간에서 지정된 유효 시간(tokenValidateTime) 이후로 설정
        Date expireTime = new Date(System.currentTimeMillis()+tokenValidateTime);
        // JwtBuilder를 사용하여 JWT 토큰을 생성하는 빌더 객체를 초기화
        JwtBuilder builder = Jwts.builder()  // 토큰 생성 라이브러리 JwtBuilder
                .setHeader(createHeader()) // 토큰의 헤더 설정 (헤더에는 토큰의 타입 및 알고리즘 정보가 담김)
                .setClaims(createClaims(user)) // 토큰의 클레임 설정 (사용자 정보와 같은 페이로드 데이터를 담음)
                .setSubject("locat token : " + user.getUserId())  // 토큰의 설명 정보를 담아줌
                .signWith(SignatureAlgorithm.HS256,createSignature())  // 토큰 암호화 방식 정의
                // HS256은 HMAC (Hash-based Message Authentication Code) + SHA-256 해시 알고리즘을 조합한 방식으로,
                // 토큰의 무결성과 검증을 위해 사용됩니다.
                .setExpiration(expireTime); // 토큰 만료 시간 설정
        // 최종적으로 생성된 토큰을 문자열 형태로 반환
        return builder.compact();
    }

    /**
     * token의 header를 설정하는 부분이다.
     * @return Map<String, Object> header의 설정 정보
     * */
    private static Map<String, Object> createHeader(){
        Map<String,Object> header = new HashMap<>();
        // 헤더에 토큰의 타입을 JWT로 설정
        header.put("type","jwt");
        // 헤더에 토큰의 서명 알고리즘을 HS256으로 설정
        header.put("alg","HS256");
        // 헤더에 토큰 생성 시간을 밀리초 단위로 추가
        header.put("date",System.currentTimeMillis());
        // 토큰을 만든 설명
        return header;
    }

    /**
     * 사용자 정보를 기반으로 클레임을 생성해주는 메서드
     * @Param user 사용자 정보
     * @return Map<string,Object> Claims 정보
     * */
    private static Map<String,Object> createClaims(Users user){
        // 클레임(Claim)은 JWT 토큰의 정보 부분으로, 토큰에 포함되는 **데이터(payload)**를 의미합니다.
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getUserId());
        claims.put("Role",user.getRole());
//        claims.put("userEmail",user.getUserEmail());
        return claims;
    }

    // 비밀 키는 임의의 복잡한 문자열을 사용하여 설정하면 됩니다.
    // 중요한 점은 이 비밀 키가 예측 불가능하고 충분히 복잡해야 한다는 것입니다.
    /**
     * Jwt 서명을 발급해주는 메서드이다.
     * @return key
     * */
    private static Key createSignature(){
        // 1. 비밀 키 문자열을 Base64로 디코딩하여 바이트 배열로 변환
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);  //2진 데이터로 컨버트
        // 2. 변환된 바이트 배열을 HS256 알고리즘을 사용한 SecretKeySpec 객체로 생성
        //    생성된 SecretKeySpec은 서명 시 사용될 Key 객체로 반환됩니다.
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName()); // HS256으로 암호화 후 반환 - 복호화 시에도 찾을 수 없음
    }

}
