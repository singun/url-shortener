# URL Shortener ([DEMO](http://ec2-54-92-196-132.compute-1.amazonaws.com:8080/))
URL shortener는 URL을 입력받아 단축 URL을 생성, 단축 URL을 입력하면 원래 URL로 리다이렉트
- webapp으로 개발, URL 입력 폼을 제공해주며 변환된 결과를 출력
- 단축 URL의 길이는 8글자 이내로 생성
- 단축 URL을 입력폼에 입력하면 원래 URL로 리다이렉트

## 문제해결 전략
### 기본 알고리즘
Original URL을 숫자 형태의 임의의 값으로 치환한 후에, 이 값을 문자52개로 인코딩해서 shorten code를 생성
1. Origianl URL > 임의의 숫자
  - 데이터베이스 사용 여부에 따라서 다르게 구현 가능
2. 임의의 숫자 > Shorten code 
  - 52진법을 사용하여, 0~51 숫자와 52개의 알파벳을 매핑하여 shorten code 제공

#### 버전1. 데이터베이스 미사용
- Original URL을 숫자와 매핑 시키기 위해서 [AtomicLong](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicLong.html) 사용
- 2개의 map 사용
  1. `{ key : original URL, value : shorten number }`
  2. `{ key : shorten number, value : original URL }`
- 개선 사항
  - 서버가 다수라면?
  - 서버가 재시작된다면?

#### 버전2. 데이터베이스 사용
- 데이터베이스에서 제공해주는 increment 사용
  - 본 서비스에서는 redis를 사용
- 3개의 { key , value } 
  1. `{ key : key, value : shorten number }`
  2. `{ key : original url, value : shorten number }`
  3. `{ key : shorten number, value : origianl url }`

## 개발 환경
- SpringBoot 2.0 
- JDK 1.8 
- Maven
- Thymleaf
- Redis 4.0.7
- IntelliJ 2018.1
- macOS 10.12.6

### 프로젝트 구조
```
* urlshortener
ㄴ* common	 : 공통 예외 처리
ㄴ* configuration : web mvc 설정, redis 설정
ㄴ* controller	 : 컨트롤러
ㄴ* interceptors	 : http request 정보 획득
ㄴ* utils	 : 진법 변환
ㄴ* v1		 : DB 미사용시 단축 URL 제공
ㄴ* v2		 : DB 사용시 단축 URL 제공
```
  

## 빌드 및 실행 방법
### maven
1. git clone
```
$ git clone https://github.com/singun/url-shortener.git
```

2. maven run
```
// 버전1
$ mvn spring-boot:run -Durl.shortener.version=1

// 버전2
$ mvn spring-boot:run -Durl.shortener.version=2
```

- 버전2 실행 시, `Redis` 필수
- Redis 환경 변수 
  - host : localhost
  - port : 6379
  - Redis [[설치 가이드](https://redis.io/download#installation)]

### jar
1. jar 파일 [다운로드](https://s3.amazonaws.com/elasticbeanstalk-us-east-1-410855085303/20181433K7-urlshortener-0.0.1-SNAPSHOT.jar)

2. excute jar
```
$ java -jar {경로}/20181433K7-urlshortener-0.0.1-SNAPSHOT.jar --url.shortener.version={1 or 2}
```

#### Request
![2018-05-24 2 55 10](https://user-images.githubusercontent.com/8944017/40442324-455d912a-5efe-11e8-85aa-eef1dcdf19b8.png)

#### Result
![2018-05-24 2 55 13](https://user-images.githubusercontent.com/8944017/40442326-477dd01e-5efe-11e8-9849-ae64df554f3d.png)


