[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]

## About The Project

SpringBoot, JPA를 활용한 REST API 예제 입니다.

REST API의 특성상 서버에 세션및 쿠키를 저장하지않는 방식으로 JWT를 사용하였습니다.

회원가입, 로그인, 프로필 조회, 수정 까지의 기능 및 테스트코드가 구현되어있습니다.

회원기능이 있는 SpringBoot 프로젝트를 진행시에 초반작업을 빠르게하기 위해 진행하였습니다.

Swagger를 통해 API의 사양을 확인할 수 있습니다.

## Skil

- Java
- SpringBoot
- Spring Security
- JPA
- JWT
- Swagger
- Junit
- Gmail SMTP

## Getting Started

### 프로젝트 실행 방법

1. 본인의 workspace에 git cloen 진행

```markdown
git clone https://github.com/DoHyeonJ/SpringBoot_RestApi_Example.git
```

1. 기본설정파일(properties) 값 설정 [resources/application.properties]

```markdown
# JPA
spring.jpa.hibernate.ddl-auto=create
```

- JPA ddl 옵션 설정 기존 ‘create’로 설정하여 db 생성후 운영시에는 ‘validation’ 또는 ‘update’으로 변경필요

```markdown
# DB
spring.datasource.url=jdbc:mysql://localhost:3306/{dbname}
spring.datasource.username={username}
spring.datasource.password={password}
```

- DB 정보 기입 (위 예제는 mysql 기준이며 다른 DB 사용시 해당 DB에 맞춰 설정해줘야합니다.)

```markdown
# SMTP
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username={email}
spring.mail.password={password}
```

- 인증 메일 전송용 SMTP 메일정보 설정 (위 예제는 gmail을 기준으로 작성되었으며 다른 email 사용시 해당 email기준에 맞추 설정해줘야합니다.)

1. 상수값 설정 [com/restapiform/config/ConstVariable.java]

```java
public final class ConstVariable {
    public static final String MAIN_URL = "localhost:8080"; // 메인 URL
    public static final String SERVICE_NAME = "테스트 서비스"; // 서비스 명
    public static final String TEST_EMAIL = "wyehgus@naver.com"; // 테스트 코드용 메일
}
```

- 테스트코드 호출시 사용되는 메인 URL
- 인증 메일 전송시 노출되는 서비스 명
- 테스트시 테스트진행할 메일정보

## Usage

1. 회원가입 /accounts [post] 호출 → 메일인증 토큰 가입한 메일로 전송됨
2. 메일인증 /auth/email/{token} [get] 호출
3. 로그인 /accounts/login [post] 호출 → 메일인증 하지않았을경우 로그인 불가
- 로그인 호출시 응답값으로 JWT 토큰을 응답합니다. 해당 토큰을 통해 프로필 조회가 가능해집니다.
1. 프로필 조회 /profile/{id} [get] 호출 → Header에 토큰 담아야 조회 가능
- ex) X-AUTH-TOKEN  {token}

- Swagger 연동을 통해 요청, 응답 파라미터를 확인할 수 있습니다.

(프로젝트 Run 상태에서 확인가능)

- [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Contact

Name : Dohyeon Jo

Email : jdh7693@naver.com

Blog : [https://do-hyeon.tistory.com/269?category=974947](https://do-hyeon.tistory.com/269?category=974947)
