# JPA_Study02

1. 프로젝트 환경설정 - 프로젝트 생성
  - Lombok 사용시 
    : Settings - Build, Execution, Deployment - Compiler - Annotation Processors - Enable annotation processing 체크하기

2. View 환경 설정
  - view 파일 수정 시 서버 재시작 없이 recomfile만 하면 즉시 화면에 적용 되도록 하는 법.
    : build.gradle -> implementation 'org.springframework.boot:spring-boot-devtools'

3. JPA와 DB설정, 동작확인
  - Ctrl + Shift + T : create test case
  - H2 1.4.200 버전부터 MVCC 옵션이 제거되었음. jdbc:h2:tcp://localhost/~/jpashop;MVCC=TRUE
  - 쿼리 파라미터로 남기기
    1) application.yml - logging.level: org.hibernate.type: trace
    2) 외부 라이브러리 : https://github.com/gavlyukovskiy/spring-boot-data-source-decorator

4. 엔티티 클래스 개발1
  - 주인이 되는 쪽이 @JoinColum, 반대쪽이 mappedBy

5. 엔티티 설계시 주의점
  - 엔티티에는 가급적 Setter를 사용하지 말자.
  - 모든 연관관계는 지연로딩으로 설정! (즉시로딩 EAGER은 예측이 어렵다. 어떤 SQL이 실행될지 추적하기 어렵다.)

17. @Transactional(readOnly = true) : JPA가 조회하는 성능에서 조금 더 최적화 된다.  
  <br/>
25.  
* Ctrl + Alt + v : 값 변수로 저장  
* Ctrl + Alt + m : 로직 메소드로 변경  
* Ctrl + Alt + P : 고정 값 파라미터로 변경  
    

# JPA_Study03
1. API 개발 기본 - 회원 등록 API
  - API 스펙을 위한 별도의 Data Transfer Object를 만들어야 한다. Entity를 그대로 사용하면 안된다.
  - 장점
    1) Entity를 변경해도 api 스펙이 바뀌지 않는다.
    2) api 스펙자체가 어떤 값이 필요한지 알 수 있음

2. API 개발 기본 - 회원 수정 API
  - Command와 Query를 분리하면 유지보수성이 증대된다 : 트레픽이 많이 발생하지 않는 api에서


