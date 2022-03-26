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
